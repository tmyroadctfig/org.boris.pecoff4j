/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.boris.pecoff4j.BoundImport;
import org.boris.pecoff4j.BoundImportDirectoryTable;
import org.boris.pecoff4j.COFFHeader;
import org.boris.pecoff4j.DOSHeader;
import org.boris.pecoff4j.DOSStub;
import org.boris.pecoff4j.DebugDirectory;
import org.boris.pecoff4j.ExportDirectoryTable;
import org.boris.pecoff4j.ImageDataDirectory;
import org.boris.pecoff4j.ImportDirectory;
import org.boris.pecoff4j.ImportDirectoryEntry;
import org.boris.pecoff4j.ImportDirectoryTable;
import org.boris.pecoff4j.ImportEntry;
import org.boris.pecoff4j.LoadConfigDirectory;
import org.boris.pecoff4j.OptionalHeader;
import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.PESignature;
import org.boris.pecoff4j.RVAConverter;
import org.boris.pecoff4j.ResourceDataEntry;
import org.boris.pecoff4j.ResourceDirectory;
import org.boris.pecoff4j.ResourceDirectoryEntry;
import org.boris.pecoff4j.ResourceLanguageDirectory;
import org.boris.pecoff4j.ResourceNameDirectory;
import org.boris.pecoff4j.ResourcePointer;
import org.boris.pecoff4j.ResourceTypeDirectory;
import org.boris.pecoff4j.SectionData;
import org.boris.pecoff4j.SectionHeader;
import org.boris.pecoff4j.SectionTable;

public class PEParser
{
    public static PE parse(InputStream is) throws IOException {
        return read(new DataReader(is), false);
    }

    public static PE parse(String filename) throws IOException {
        return parse(new File(filename));
    }

    public static PE parse(File file) throws IOException {
        return read(new DataReader(new FileInputStream(file)), false);
    }

    public static PE read(IDataReader dr, boolean parseSections)
            throws IOException {
        PE pe = new PE();
        pe.setDosHeader(readDos(dr));

        // Check if we have an old file type
        if (pe.getDosHeader().getAddressOfNewExeHeader() == 0 ||
                pe.getDosHeader().getAddressOfNewExeHeader() > 8192) {
            return pe;
        }

        pe.setStub(readStub(pe.getDosHeader(), dr));
        pe.setSignature(readSignature(dr));

        // Check signature to ensure we have a pe/coff file
        if (!pe.getSignature().isValid()) {
            return pe;
        }

        pe.setCoffHeader(readCOFF(dr));
        pe.setOptionalHeader(readOptional(dr));
        pe.setSectionTable(readSections(pe, dr));

        if (parseSections) {
            SectionParser.parse(pe);
        }

        return pe;
    }

    public static COFFHeader readCOFF(IDataReader dr) throws IOException {
        COFFHeader h = new COFFHeader();
        h.setMachine(dr.readWord());
        h.setNumberOfSections(dr.readWord());
        h.setTimeDateStamp(dr.readDoubleWord());
        h.setPointerToSymbolTable(dr.readDoubleWord());
        h.setNumberOfSymbols(dr.readDoubleWord());
        h.setSizeOfOptionalHeader(dr.readWord());
        h.setCharacteristics(dr.readWord());
        return h;
    }

    public static DOSHeader readDos(IDataReader dr) throws IOException {
        DOSHeader dh = new DOSHeader();
        dh.setMagic(dr.readWord());
        dh.setUsedBytesInLastPage(dr.readWord());
        dh.setFileSizeInPages(dr.readWord());
        dh.setNumRelocationItems(dr.readWord());
        dh.setHeaderSizeInParagraphs(dr.readWord());
        dh.setMinExtraParagraphs(dr.readWord());
        dh.setMaxExtraParagraphs(dr.readWord());
        dh.setInitialSS(dr.readWord());
        dh.setInitialSP(dr.readWord());
        dh.setChecksum(dr.readWord());
        dh.setInitialIP(dr.readWord());
        dh.setInitialRelativeCS(dr.readWord());
        dh.setAddressOfRelocationTable(dr.readWord());
        dh.setOverlayNumber(dr.readWord());
        int[] reserved = new int[4];
        for (int i = 0; i < reserved.length; i++) {
            reserved[i] = dr.readWord();
        }
        dh.setReserved(reserved);
        dh.setOemId(dr.readWord());
        dh.setOemInfo(dr.readWord());
        int[] reserved2 = new int[10];
        for (int i = 0; i < reserved2.length; i++) {
            reserved2[i] = dr.readWord();
        }
        dh.setReserved2(reserved2);
        dh.setAddressOfNewExeHeader(dr.readDoubleWord());

        // calc stub size
        int stubSize = dh.getFileSizeInPages() * 512 -
                (512 - dh.getUsedBytesInLastPage());
        if (stubSize > dh.getAddressOfNewExeHeader())
            stubSize = dh.getAddressOfNewExeHeader();
        stubSize -= dh.getHeaderSizeInParagraphs() * 16;
        dh.setStubSize(stubSize);

        return dh;
    }

    public static DOSStub readStub(DOSHeader header, IDataReader dr)
            throws IOException {
        DOSStub ds = new DOSStub();
        int pos = dr.getPosition();
        int add = header.getAddressOfNewExeHeader();
        byte[] stub = new byte[add - pos];
        dr.read(stub);
        ds.setStub(stub);
        return ds;
    }

    public static PESignature readSignature(IDataReader dr) throws IOException {
        PESignature ps = new PESignature();
        byte[] signature = new byte[4];
        dr.read(signature);
        ps.setSignature(signature);
        return ps;
    }

    public static OptionalHeader readOptional(IDataReader dr)
            throws IOException {
        OptionalHeader oh = new OptionalHeader();
        oh.setMagic(dr.readWord());
        boolean is64 = oh.isPE32plus();
        oh.setMajorLinkerVersion(dr.readByte());
        oh.setMinorLinkerVersion(dr.readByte());
        oh.setSizeOfCode(dr.readDoubleWord());
        oh.setSizeOfInitializedData(dr.readDoubleWord());
        oh.setSizeOfUninitializedData(dr.readDoubleWord());
        oh.setAddressOfEntryPoint(dr.readDoubleWord());
        oh.setBaseOfCode(dr.readDoubleWord());

        if (!is64)
            oh.setBaseOfData(dr.readDoubleWord());

        // NT additional fields.
        oh.setImageBase(is64 ? dr.readLong() : dr.readDoubleWord());
        oh.setSectionAlignment(dr.readDoubleWord());
        oh.setFileAlignment(dr.readDoubleWord());
        oh.setMajorOperatingSystemVersion(dr.readWord());
        oh.setMinorOperatingSystemVersion(dr.readWord());
        oh.setMajorImageVersion(dr.readWord());
        oh.setMinorImageVersion(dr.readWord());
        oh.setMajorSubsystemVersion(dr.readWord());
        oh.setMinorSubsystemVersion(dr.readWord());
        oh.setWin32VersionValue(dr.readDoubleWord());
        oh.setSizeOfImage(dr.readDoubleWord());
        oh.setSizeOfHeaders(dr.readDoubleWord());
        oh.setCheckSum(dr.readDoubleWord());
        oh.setSubsystem(dr.readWord());
        oh.setDllCharacteristics(dr.readWord());
        oh.setSizeOfStackReserve(dr.readDoubleWord());
        oh.setSizeOfStackCommit(is64 ? dr.readLong() : dr.readDoubleWord());
        oh.setSizeOfHeapReserve(is64 ? dr.readLong() : dr.readDoubleWord());
        oh.setSizeOfHeapCommit(is64 ? dr.readLong() : dr.readDoubleWord());
        oh.setLoaderFlags(dr.readDoubleWord());
        oh.setNumberOfRvaAndSizes(dr.readDoubleWord());

        // Data directories
        oh.setExportTable(readImageDD(dr));
        oh.setImportTable(readImageDD(dr));
        oh.setResourceTable(readImageDD(dr));
        oh.setExceptionTable(readImageDD(dr));
        oh.setCertificateTable(readImageDD(dr));
        oh.setBaseRolocationTable(readImageDD(dr));
        oh.setDebug(readImageDD(dr));
        oh.setArchitecture(readImageDD(dr));
        oh.setGlobalPtr(readImageDD(dr));
        oh.setTlsTable(readImageDD(dr));
        oh.setLoadConfigTable(readImageDD(dr));
        oh.setBoundImport(readImageDD(dr));
        oh.setIat(readImageDD(dr));
        oh.setDelayImportDescriptor(readImageDD(dr));
        oh.setClrRuntimeHeader(readImageDD(dr));
        oh.setReserved(readImageDD(dr));

        return oh;
    }

    public static SectionHeader readSectionHeader(IDataReader dr)
            throws IOException {
        SectionHeader sh = new SectionHeader();
        sh.setName(dr.readUtf(8));
        sh.setVirtualSize(dr.readDoubleWord());
        sh.setVirtualAddress(dr.readDoubleWord());
        sh.setSizeOfRawData(dr.readDoubleWord());
        sh.setPointerToRawData(dr.readDoubleWord());
        sh.setPointerToRelocations(dr.readDoubleWord());
        sh.setPointerToLineNumbers(dr.readDoubleWord());
        sh.setNumberOfRelocations(dr.readWord());
        sh.setNumberOfLineNumbers(dr.readWord());
        sh.setCharacteristics(dr.readDoubleWord());
        return sh;
    }

    public static ImageDataDirectory readImageDD(IDataReader dr)
            throws IOException {
        ImageDataDirectory idd = new ImageDataDirectory();
        idd.setVirtualAddress(dr.readDoubleWord());
        idd.setSize(dr.readDoubleWord());
        return idd;
    }

    public static SectionTable readSections(PE pe, IDataReader dr)
            throws IOException {
        SectionTable st = new SectionTable();
        List<SectionHeader> sections = new ArrayList();
        for (int i = 0; i < pe.getCoffHeader().getNumberOfSections(); i++) {
            SectionHeader sh = readSectionHeader(dr);
            st.add(sh);
            sections.add(sh);
        }

        // Now sort on section address and load
        Collections.sort(sections, new Comparator<SectionHeader>() {
            public int compare(SectionHeader o1, SectionHeader o2) {
                return o1.getPointerToRawData() - o2.getPointerToRawData();
            }
        });

        // Check for bound imports
        ImageDataDirectory bi = pe.getOptionalHeader().getBoundImport();
        if (bi.getSize() > 0) {
            SectionHeader sh1 = sections.get(0);
            if (sh1 == null ||
                    sh1.getPointerToRawData() > bi.getVirtualAddress()) {
                // Need to read the bound imports directly
                dr.jumpTo(bi.getVirtualAddress());
                byte[] bib = new byte[bi.getSize()];
                dr.read(bib);
                IDataReader bidr = new ByteArrayDataReader(bib);
                pe.setBoundImports(readBoundImportDirectoryTable(bidr));
            }
        }

        for (SectionHeader sh : sections) {
            if (sh.getPointerToRawData() != 0) {
                dr.jumpTo(sh.getPointerToRawData());
                byte[] data = new byte[sh.getSizeOfRawData()];
                dr.read(data);
                SectionData sd = new SectionData();
                sd.add(data);
                st.putData(sh.getName(), sd);
            }
        }

        // Store sorted sections based on virtual address
        SectionHeader[] sorted = sections.toArray(new SectionHeader[0]);
        Arrays.sort(sorted, new Comparator<SectionHeader>() {
            public int compare(SectionHeader o1, SectionHeader o2) {
                return o1.getVirtualAddress() - o2.getVirtualAddress();
            }
        });
        int[] virtualAddress = new int[sorted.length];
        int[] pointerToRawData = new int[sorted.length];
        for (int i = 0; i < sorted.length; i++) {
            virtualAddress[i] = sorted[i].getVirtualAddress();
            pointerToRawData[i] = sorted[i].getPointerToRawData();
        }

        st.setRvaConverter(new RVAConverter(virtualAddress, pointerToRawData));
        return st;
    }

    private static BoundImportDirectoryTable readBoundImportDirectoryTable(
            IDataReader dr) throws IOException {
        int pos = dr.getPosition();
        BoundImportDirectoryTable bidt = new BoundImportDirectoryTable();
        BoundImport bi = null;
        while ((bi = readBoundImport(dr)) != null) {
            bidt.add(bi);
        }
        for (int i = 0; i < bidt.size(); i++) {
            bi = bidt.get(i);
            dr.jumpTo(pos + bi.getOffsetToModuleName());
            bi.setModuleName(dr.readUtf());
        }
        return bidt;
    }

    private static BoundImport readBoundImport(IDataReader dr)
            throws IOException {
        BoundImport bi = new BoundImport();
        bi.setTimestamp(dr.readDoubleWord());
        bi.setOffsetToModuleName(dr.readWord());
        bi.setNumberOfModuleForwarderRefs(dr.readWord());

        if (bi.getTimestamp() == 0)
            return null;

        return bi;
    }

    public static ImportDirectory readImportDirectory(IDataReader dr,
            int baseAddress) throws IOException {
        ImportDirectory id = new ImportDirectory();
        ImportDirectoryEntry ide = null;
        while ((ide = readImportDirectoryEntry(dr)) != null) {
            id.add(ide);
        }

        for (int i = 0; i < id.size(); i++) {
            ImportDirectoryEntry e = id.getEntry(i);
            dr.jumpTo(e.getNameRVA() - baseAddress);
            String name = dr.readUtf();
            dr.jumpTo(e.getImportLookupTableRVA() - baseAddress);
            ImportDirectoryTable nt = readImportDirectoryTable(dr, baseAddress);
            dr.jumpTo(e.getImportAddressTableRVA() - baseAddress);
            ImportDirectoryTable at = null; // readImportDirectoryTable(dr,
            // baseAddress);
            id.add(name, nt, at);
        }

        return id;
    }

    public static ImportDirectoryEntry readImportDirectoryEntry(IDataReader dr)
            throws IOException {
        ImportDirectoryEntry id = new ImportDirectoryEntry();
        id.setImportLookupTableRVA(dr.readDoubleWord());
        id.setTimeDateStamp(dr.readDoubleWord());
        id.setForwarderChain(dr.readDoubleWord());
        id.setNameRVA(dr.readDoubleWord());
        id.setImportAddressTableRVA(dr.readDoubleWord());

        // The last entry is null
        if (id.getImportLookupTableRVA() == 0) {
            return null;
        }

        return id;
    }

    public static ImportDirectoryTable readImportDirectoryTable(IDataReader dr,
            int baseAddress) throws IOException {
        ImportDirectoryTable idt = new ImportDirectoryTable();
        ImportEntry ie = null;
        while ((ie = readImportEntry(dr)) != null) {
            idt.add(ie);
        }

        for (int i = 0; i < idt.size(); i++) {
            ImportEntry iee = idt.getEntry(i);
            if ((iee.getVal() & 0x80000000) != 0) {
                iee.setOrdinal(iee.getVal() & 0x7fffffff);
            } else {
                dr.jumpTo(iee.getVal() - baseAddress);
                dr.readWord(); // FIXME this is an index into the export table
                iee.setName(dr.readUtf());
            }
        }
        return idt;
    }

    public static ImportEntry readImportEntry(IDataReader dr)
            throws IOException {
        ImportEntry ie = new ImportEntry();
        ie.setVal(dr.readDoubleWord());
        if (ie.getVal() == 0) {
            return null;
        }

        return ie;
    }

    public static ExportDirectoryTable readExportDirectoryTable(IDataReader dr)
            throws IOException {
        ExportDirectoryTable edt = new ExportDirectoryTable();
        edt.setExportFlags(dr.readDoubleWord());
        edt.setTimeDateStamp(dr.readDoubleWord());
        edt.setMajorVersion(dr.readWord());
        edt.setMinorVersion(dr.readWord());
        edt.setNameRVA(dr.readDoubleWord());
        edt.setOrdinalBase(dr.readDoubleWord());
        edt.setAddressTableEntries(dr.readDoubleWord());
        edt.setNumberOfNamePointers(dr.readDoubleWord());
        edt.setExportAddressTableRVA(dr.readDoubleWord());
        edt.setNamePointerRVA(dr.readDoubleWord());
        edt.setOrdinalTableRVA(dr.readDoubleWord());
        return edt;
    }

    public static LoadConfigDirectory readLoadConfigDirectory(IDataReader dr)
            throws IOException {
        LoadConfigDirectory lcd = new LoadConfigDirectory();
        lcd.setCharacteristics(dr.readDoubleWord());
        lcd.setTimeDateStamp(dr.readDoubleWord());
        lcd.setMajorVersion(dr.readWord());
        lcd.setMinorVersion(dr.readWord());
        lcd.setGlobalFlagsClear(dr.readDoubleWord());
        lcd.setGlobalFlagsSet(dr.readDoubleWord());
        lcd.setCriticalSectionDefaultTimeout(dr.readDoubleWord());
        lcd.setDeCommitFreeBlockThreshold(dr.readLong());
        lcd.setDeCommitTotalFreeThreshold(dr.readLong());
        lcd.setLockPrefixTable(dr.readLong());
        lcd.setMaximumAllocationSize(dr.readLong());
        lcd.setVirtualMemoryThreshold(dr.readLong());
        lcd.setProcessAffinityMask(dr.readLong());
        lcd.setProcessHeapFlags(dr.readDoubleWord());
        lcd.setCsdVersion(dr.readWord());
        lcd.setReserved(dr.readWord());
        lcd.setEditList(dr.readLong());
        lcd.setSecurityCookie(dr.readDoubleWord());
        lcd.setSeHandlerTable(dr.readDoubleWord());
        lcd.setSeHandlerCount(dr.readDoubleWord());

        return lcd;
    }

    public static ResourceDirectory readResourceDirectory(byte[] data,
            int baseAddress) throws IOException {
        ResourceDirectory rd = new ResourceDirectory();
        IDataReader dr = new ByteArrayDataReader(data);
        rd.setEntry(readResourceDirectoryEntry(dr));

        ResourcePointer[] pointers = rd.getEntry().getEntries();
        for (int i = 0; i < pointers.length; i++) {
            dr.jumpTo(pointers[i].getOffsetToData());
            rd.add(readResourceTypeDirectory(dr, baseAddress));
        }

        return rd;
    }

    public static ResourceDirectoryEntry readResourceDirectoryEntry(
            IDataReader dr) throws IOException {
        ResourceDirectoryEntry rd = new ResourceDirectoryEntry();
        rd.setCharacteristics(dr.readDoubleWord());
        rd.setTimeDateStamp(dr.readDoubleWord());
        rd.setMajorVersion(dr.readWord());
        rd.setMinorVersion(dr.readWord());
        rd.setNumNamedEntries(dr.readWord());
        rd.setNumIdEntries(dr.readWord());
        ResourcePointer[] rp = new ResourcePointer[rd.getNumIdEntries()];
        for (int i = 0; i < rp.length; i++) {
            rp[i] = readResourcePointer(dr);
        }
        rd.setEntries(rp);

        return rd;
    }

    public static ResourceNameDirectory readResourceNameDirectory(
            IDataReader dr, int baseAddress) throws IOException {
        ResourceNameDirectory rn = new ResourceNameDirectory();
        rn.setEntry(readResourceDirectoryEntry(dr));
        ResourcePointer[] pointers = rn.getEntry().getEntries();
        for (int i = 0; i < pointers.length; i++) {
            dr.jumpTo(pointers[i].getOffsetToData());
            rn.add(readResourceLanguageDirectory(dr, baseAddress));
        }
        return rn;
    }

    public static ResourcePointer readResourcePointer(IDataReader dr)
            throws IOException {
        ResourcePointer rp = new ResourcePointer();
        rp.setName(dr.readDoubleWord());
        // high bit indicates a directory
        rp.setOffsetToData(dr.readDoubleWord() & 0x7fffffff);
        return rp;
    }

    public static ResourceTypeDirectory readResourceTypeDirectory(
            IDataReader dr, int baseAddress) throws IOException {
        ResourceTypeDirectory rt = new ResourceTypeDirectory();
        rt.setEntry(readResourceDirectoryEntry(dr));
        ResourcePointer[] pointers = rt.getEntry().getEntries();
        for (int i = 0; i < pointers.length; i++) {
            dr.jumpTo(pointers[i].getOffsetToData());
            rt.add(readResourceNameDirectory(dr, baseAddress));
        }
        return rt;

    }

    public static ResourceLanguageDirectory readResourceLanguageDirectory(
            IDataReader dr, int baseAddress) throws IOException {
        ResourceLanguageDirectory rl = new ResourceLanguageDirectory();
        rl.setEntry(readResourceDataEntry(dr));
        int offset = rl.getEntry().getOffsetToData();
        int size = rl.getEntry().getSize();
        if (offset != 0 && size != 0) {
            byte[] data = new byte[size];
            dr.jumpTo(offset - baseAddress);
            dr.read(data);
            rl.setData(data);
        }
        return rl;
    }

    public static ResourceDataEntry readResourceDataEntry(IDataReader dr)
            throws IOException {
        ResourceDataEntry rde = new ResourceDataEntry();
        rde.setOffsetToData(dr.readDoubleWord());
        rde.setSize(dr.readDoubleWord());
        rde.setCodePage(dr.readDoubleWord());
        rde.setReserved(dr.readDoubleWord());
        return rde;
    }

    public static DebugDirectory readDebugDirectory(IDataReader dr)
            throws IOException {
        DebugDirectory dd = new DebugDirectory();
        dd.setCharacteristics(dr.readDoubleWord());
        dd.setTimeDateStamp(dr.readDoubleWord());
        dd.setMajorVersion(dr.readWord());
        dd.setMajorVersion(dr.readWord());
        dd.setType(dr.readDoubleWord());
        dd.setSizeOfData(dr.readDoubleWord());
        dd.setAddressOfRawData(dr.readDoubleWord());
        dd.setPointerToRawData(dr.readDoubleWord());
        return dd;
    }
}
