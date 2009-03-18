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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.boris.pecoff4j.BoundImport;
import org.boris.pecoff4j.BoundImportDirectoryTable;
import org.boris.pecoff4j.COFFHeader;
import org.boris.pecoff4j.DOSHeader;
import org.boris.pecoff4j.DOSStub;
import org.boris.pecoff4j.ImageData;
import org.boris.pecoff4j.ImageDataDirectory;
import org.boris.pecoff4j.OptionalHeader;
import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.PESignature;
import org.boris.pecoff4j.SectionData;
import org.boris.pecoff4j.SectionHeader;
import org.boris.pecoff4j.SectionTable;

public class PEAssembler
{
    public static byte[] toBytes(PE pe) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        write(pe, bos);
        return bos.toByteArray();
    }

    public static void write(PE pe, String filename) throws IOException {
        write(pe, new FileOutputStream(filename));
    }

    public static void write(PE pe, File file) throws IOException {
        write(pe, new FileOutputStream(file));
    }

    public static void write(PE pe, OutputStream os) throws IOException {
        DataWriter dw = new DataWriter(os);
        write(pe, dw);
        dw.flush();
    }

    public static void write(PE pe, IDataWriter dw) throws IOException {
        write(pe.getDosHeader(), dw);
        write(pe.getStub(), dw);
        write(pe.getSignature(), dw);
        write(pe.getCoffHeader(), dw);
        write(pe.getOptionalHeader(), dw);
        writeSectionHeaders(pe, dw);
        writeImageDataPreSections(pe, dw);
        writeSections(pe, dw);
        writeImageDataPostSections(pe, dw);
    }

    private static void writeSectionHeaders(PE pe, IDataWriter dw)
            throws IOException {
        SectionTable st = pe.getSectionTable();
        int ns = st.getNumberOfSections();
        for (int i = 0; i < ns; i++) {
            SectionHeader sh = st.getHeader(i);
            write(sh, dw);
        }
    }

    private static void writeImageDataPreSections(PE pe, IDataWriter dw)
            throws IOException {
        // Write out bound import table if present
        ImageData id = pe.getImageData();
        if (id.getBoundImports() != null) {
            // System.out.println(Integer.toHexString(dw.getPosition()));
            write(pe, id.getBoundImports(), dw);
            // System.out.println(Integer.toHexString(dw.getPosition()));
        }

        // Write out header padding if present - TODO replace with real structs
        byte[] padding = id.getHeaderPadding();
        if (padding != null) {
            dw.writeBytes(padding);
            // System.out.println(Integer.toHexString(dw.getPosition()));
        }
    }

    private static void writeSections(PE pe, IDataWriter dw) throws IOException {
        SectionTable st = pe.getSectionTable();
        SectionHeader[] headers = st.getHeadersPointerSorted();

        for (int i = 0; i < headers.length; i++) {
            SectionHeader sh = headers[i];
            int pr = sh.getPointerToRawData();
            int pc = dw.getPosition();
            if (pr > pc) {
                dw.writeByte(0, pr - pc);
            }
            SectionData sd = st.getSection(i);
            if (sd != null && sd.size() > 0) {
                byte[] b = (byte[]) sd.getEntry(0).getValue();
                dw.writeBytes(b);
            }
        }
    }

    private static void writeImageDataPostSections(PE pe, IDataWriter dw) {
        ImageData id = pe.getImageData();
        byte[] certificateData = id.getCertificateTable();
        ImageDataDirectory cdd = pe.getOptionalHeader().getCertificateTable();
        if (certificateData != null && cdd != null && cdd.getSize() > 0) {
            SectionHeader lsh = pe.getSectionTable()
                    .getLastSectionRawPointerSorted();
            int offset = lsh.getPointerToRawData() + lsh.getVirtualSize();
            if (cdd.getVirtualAddress() >= offset) {

            }
        }
    }

    private static void write(PE pe, BoundImportDirectoryTable bidt,
            IDataWriter dw) throws IOException {
        int pos = dw.getPosition();
        List<BoundImport> bil = new ArrayList();

        for (int i = 0; i < bidt.size(); i++) {
            BoundImport bi = bidt.get(i);
            bil.add(bi);
            dw.writeDoubleWord((int) bi.getTimestamp());
            dw.writeWord(bi.getOffsetToModuleName());
            dw.writeWord(bi.getNumberOfModuleForwarderRefs());
        }

        Collections.sort(bil, new Comparator<BoundImport>() {
            public int compare(BoundImport o1, BoundImport o2) {
                return o1.getOffsetToModuleName() - o2.getOffsetToModuleName();
            }
        });

        // Now write out empty block
        dw.writeDoubleWord(0);
        dw.writeDoubleWord(0);

        // Now write out module names
        Set names = new HashSet();
        for (int i = 0; i < bil.size(); i++) {
            String s = bil.get(i).getModuleName();
            if (!names.contains(s))
                dw.writeUtf(s);
            names.add(s);
        }

        // Check for empty block at end - padding for alignment
        int dpos = dw.getPosition() - pos;
        int bis = pe.getOptionalHeader().getBoundImports().getSize();
        if (bis > dpos) {
            dw.writeByte(0, bis - dpos);
        }
    }

    private static void write(SectionHeader sh, IDataWriter dw)
            throws IOException {
        dw.writeUtf(sh.getName(), 8);
        dw.writeDoubleWord(sh.getVirtualSize());
        dw.writeDoubleWord(sh.getVirtualAddress());
        dw.writeDoubleWord(sh.getSizeOfRawData());
        dw.writeDoubleWord(sh.getPointerToRawData());
        dw.writeDoubleWord(sh.getPointerToRelocations());
        dw.writeDoubleWord(sh.getPointerToLineNumbers());
        dw.writeWord(sh.getNumberOfRelocations());
        dw.writeWord(sh.getNumberOfLineNumbers());
        dw.writeDoubleWord(sh.getCharacteristics());
    }

    private static void write(OptionalHeader oh, IDataWriter dw)
            throws IOException {
        boolean is64 = oh.isPE32plus();

        dw.writeWord(oh.getMagic());
        dw.writeByte(oh.getMajorLinkerVersion());
        dw.writeByte(oh.getMinorLinkerVersion());
        dw.writeDoubleWord(oh.getSizeOfCode());
        dw.writeDoubleWord(oh.getSizeOfInitializedData());
        dw.writeDoubleWord(oh.getSizeOfUninitializedData());
        dw.writeDoubleWord(oh.getAddressOfEntryPoint());
        dw.writeDoubleWord(oh.getBaseOfCode());
        if (!is64)
            dw.writeDoubleWord(oh.getBaseOfData());

        // NT additional fields.
        if (is64)
            dw.writeLong(oh.getImageBase());
        else
            dw.writeDoubleWord((int) oh.getImageBase());
        dw.writeDoubleWord(oh.getSectionAlignment());
        dw.writeDoubleWord(oh.getFileAlignment());
        dw.writeWord(oh.getMajorOperatingSystemVersion());
        dw.writeWord(oh.getMinorOperatingSystemVersion());
        dw.writeWord(oh.getMajorImageVersion());
        dw.writeWord(oh.getMinorImageVersion());
        dw.writeWord(oh.getMajorSubsystemVersion());
        dw.writeWord(oh.getMinorSubsystemVersion());
        dw.writeDoubleWord(oh.getWin32VersionValue());
        dw.writeDoubleWord(oh.getSizeOfImage());
        dw.writeDoubleWord(oh.getSizeOfHeaders());
        dw.writeDoubleWord(oh.getCheckSum());
        dw.writeWord(oh.getSubsystem());
        dw.writeWord(oh.getDllCharacteristics());
        dw.writeDoubleWord(oh.getSizeOfStackReserve());
        if (is64) {
            dw.writeLong(oh.getSizeOfStackCommit());
            dw.writeLong(oh.getSizeOfHeapReserve());
            dw.writeLong(oh.getSizeOfHeapCommit());
        } else {
            dw.writeDoubleWord((int) oh.getSizeOfStackCommit());
            dw.writeDoubleWord((int) oh.getSizeOfHeapReserve());
            dw.writeDoubleWord((int) oh.getSizeOfHeapCommit());
        }

        dw.writeDoubleWord(oh.getLoaderFlags());
        dw.writeDoubleWord(oh.getNumberOfRvaAndSizes());

        // Data directories
        write(oh.getExportTable(), dw);
        write(oh.getImportTable(), dw);
        write(oh.getResourceTable(), dw);
        write(oh.getExceptionTable(), dw);
        write(oh.getCertificateTable(), dw);
        write(oh.getBaseRolocationTable(), dw);
        write(oh.getDebug(), dw);
        write(oh.getArchitecture(), dw);
        write(oh.getGlobalPtr(), dw);
        write(oh.getTlsTable(), dw);
        write(oh.getLoadConfigTable(), dw);
        write(oh.getBoundImports(), dw);
        write(oh.getIat(), dw);
        write(oh.getDelayImportDescriptor(), dw);
        write(oh.getClrRuntimeHeader(), dw);
        write(oh.getReserved(), dw);
    }

    private static void write(ImageDataDirectory idd, IDataWriter dw)
            throws IOException {
        dw.writeDoubleWord(idd.getVirtualAddress());
        dw.writeDoubleWord(idd.getSize());
    }

    private static void write(COFFHeader ch, IDataWriter dw) throws IOException {
        dw.writeWord(ch.getMachine());
        dw.writeWord(ch.getNumberOfSections());
        dw.writeDoubleWord(ch.getTimeDateStamp());
        dw.writeDoubleWord(ch.getPointerToSymbolTable());
        dw.writeDoubleWord(ch.getNumberOfSymbols());
        dw.writeWord(ch.getSizeOfOptionalHeader());
        dw.writeWord(ch.getCharacteristics());
    }

    private static void write(PESignature s, IDataWriter dw) throws IOException {
        dw.writeBytes(s.getSignature());
    }

    private static void write(DOSStub stub, IDataWriter dw) throws IOException {
        dw.writeBytes(stub.getStub());
    }

    private static void write(DOSHeader dh, IDataWriter dw) throws IOException {
        dw.writeWord(dh.getMagic());
        dw.writeWord(dh.getUsedBytesInLastPage());
        dw.writeWord(dh.getFileSizeInPages());
        dw.writeWord(dh.getNumRelocationItems());
        dw.writeWord(dh.getHeaderSizeInParagraphs());
        dw.writeWord(dh.getMinExtraParagraphs());
        dw.writeWord(dh.getMaxExtraParagraphs());
        dw.writeWord(dh.getInitialSS());
        dw.writeWord(dh.getInitialSP());
        dw.writeWord(dh.getChecksum());
        dw.writeWord(dh.getInitialIP());
        dw.writeWord(dh.getInitialRelativeCS());
        dw.writeWord(dh.getAddressOfRelocationTable());
        dw.writeWord(dh.getOverlayNumber());
        int[] res = dh.getReserved();
        for (int i = 0; i < res.length; i++) {
            dw.writeWord(res[i]);
        }
        dw.writeWord(dh.getOemId());
        dw.writeWord(dh.getOemInfo());
        int[] res2 = dh.getReserved2();
        for (int i = 0; i < res2.length; i++) {
            dw.writeWord(res2[i]);
        }
        dw.writeDoubleWord(dh.getAddressOfNewExeHeader());
    }
}
