/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.boris.pecoff4j.COFFHeader;
import org.boris.pecoff4j.DOSHeader;
import org.boris.pecoff4j.DOSStub;
import org.boris.pecoff4j.ImageDataDirectory;
import org.boris.pecoff4j.LoadConfigDirectory;
import org.boris.pecoff4j.OptionalHeader;
import org.boris.pecoff4j.PEFile;
import org.boris.pecoff4j.PESignature;
import org.boris.pecoff4j.RVAConverter;
import org.boris.pecoff4j.ResourceDirectory;
import org.boris.pecoff4j.SectionHeader;
import org.boris.pecoff4j.SectionTable;
import org.boris.pecoff4j.imports.ImportDirectory;
import org.boris.pecoff4j.io.ByteArrayDataReader;
import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.IDataReader;

public class PEParser
{
    public static PEFile parse(File file) throws IOException {
        return read(new DataReader(new FileInputStream(file)));
    }

    public static PEFile read(IDataReader dr) throws IOException {
        PEFile pf = new PEFile();
        pf.setDosHeader(readDos(dr));
        pf.setStub(readStub(pf.getDosHeader(), dr));
        pf.setSignature(readSignature(dr));
        pf.setCoffHeader(readCOFF(dr));
        pf.setOptionalHeader(readOptional(dr));
        pf.setSectionTable(readSections(pf.getCoffHeader()
                .getNumberOfSections(), dr));

        // Parse resources if present
        // TODO: ensure the resource table covers the .rsrc section
        byte[] res = pf.getSectionTable().getSectionData(".rsrc");
        if (res != null &&
                pf.getOptionalHeader().getResourceTable().getSize() > 0) {
            pf.setResourceDirectory(ResourceDirectory
                    .parse(res, pf.getOptionalHeader().getResourceTable()
                            .getVirtualAddress()));
        }

        // Parse import directory table
        res = pf.getSectionTable().getSectionData(".rdata");
        if (res != null) {
            IDataReader drr = new ByteArrayDataReader(res);
            int itva = pf.getOptionalHeader().getImportTable()
                    .getVirtualAddress();
            int rdva = pf.getSectionTable().getSection(".rdata")
                    .getVirtualAddress();
            int offset = itva - rdva;
            // Sanity check
            if (offset > 0 && offset < res.length) {
                drr.jumpTo(itva - rdva);
                pf.setImportDirectory(ImportDirectory.read(drr, pf
                        .getSectionTable().getSection(".rdata")
                        .getVirtualAddress()));
            }

            if (pf.getOptionalHeader().getLoadConfigTable().getSize() > 0) {
                drr.jumpTo(pf.getOptionalHeader().getLoadConfigTable()
                        .getVirtualAddress() -
                        pf.getSectionTable().getSection(".rdata")
                                .getVirtualAddress());
                pf.setLoadConfigDirectory(LoadConfigDirectory.read(drr));
            }
        }

        return pf;
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
        oh.setMajorLinkerVersion(dr.readByte());
        oh.setMinorLinkerVersion(dr.readByte());
        oh.setSizeOfCode(dr.readDoubleWord());
        oh.setSizeOfInitializedData(dr.readDoubleWord());
        oh.setSizeOfUninitializedData(dr.readDoubleWord());
        oh.setAddressOfEntryPoint(dr.readDoubleWord());
        oh.setBaseOfCode(dr.readDoubleWord());
        oh.setBaseOfData(dr.readDoubleWord());

        // NT additional fields.
        oh.setImageBase(dr.readDoubleWord());
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
        oh.setSizeOfStackCommit(dr.readDoubleWord());
        oh.setSizeOfHeapReserve(dr.readDoubleWord());
        oh.setSizeOfHeapCommit(dr.readDoubleWord());
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

    public static ImageDataDirectory readImageDD(IDataReader dr)
            throws IOException {
        ImageDataDirectory idd = new ImageDataDirectory();
        idd.setVirtualAddress(dr.readDoubleWord());
        idd.setSize(dr.readDoubleWord());
        return idd;
    }

    public static SectionTable readSections(int numberOfSections, IDataReader dr)
            throws IOException {
        SectionTable st = new SectionTable();
        SectionHeader[] headers = new SectionHeader[numberOfSections];
        Map sectionByName = new LinkedHashMap();
        for (int i = 0; i < numberOfSections; i++) {
            headers[i] = SectionHeader.read(dr);
            sectionByName.put(headers[i].getName(), headers[i]);
        }

        // Now sort on section address and load
        List<SectionHeader> sections = Arrays.asList(headers);
        Collections.sort(sections, new Comparator<SectionHeader>() {
            public int compare(SectionHeader o1, SectionHeader o2) {
                return o1.getPointerToRawData() - o2.getPointerToRawData();
            }
        });

        Map sectionData = new HashMap();
        for (SectionHeader sh : sections) {
            if (sh.getPointerToRawData() != 0) {
                dr.jumpTo(sh.getPointerToRawData());
                byte[] data = new byte[sh.getSizeOfRawData()];
                dr.read(data);
                sectionData.put(sh.getName(), data);
            }
        }

        // Store sorted sections based on virtual address
        SectionHeader[] sorted = Arrays.asList(headers).toArray(
                new SectionHeader[0]);
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
}
