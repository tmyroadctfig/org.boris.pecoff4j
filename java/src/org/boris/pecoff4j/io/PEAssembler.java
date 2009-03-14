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

import org.boris.pecoff4j.DOSHeader;
import org.boris.pecoff4j.PE;

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
