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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.boris.pecoff4j.COFFHeader;
import org.boris.pecoff4j.DOSHeader;
import org.boris.pecoff4j.DOSStub;
import org.boris.pecoff4j.ImageDataDirectory;
import org.boris.pecoff4j.OptionalHeader;
import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.PESignature;
import org.boris.pecoff4j.SectionTable;

public class PEAssembler
{
    public static void write(PE pe, String filename) throws IOException {
        write(pe, new FileOutputStream(filename));
    }

    public static void write(PE pe, File file) throws IOException {
        write(pe, new FileOutputStream(file));
    }

    public static void write(PE pe, OutputStream os) throws IOException {
        write(pe, new DataWriter(os));
    }

    public static void write(PE pe, IDataWriter dw) throws IOException {
        write(pe.getDosHeader(), dw);
        write(pe.getStub(), dw);
        write(pe.getSignature(), dw);
        write(pe.getCoffHeader(), dw);
        write(pe.getOptionalHeader(), dw);
        write(pe.getSectionTable(), dw);
    }

    private static void write(SectionTable st, IDataWriter dw) {
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
        write(oh.getBoundImport(), dw);
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
        dw.writeWord(dh.getOverlayNumber());
        dw.writeWord(dh.getInitialSS());
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
