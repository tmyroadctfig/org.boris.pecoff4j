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

import java.io.IOException;

import org.boris.pecoff4j.ExportDirectoryTable;
import org.boris.pecoff4j.ImageDataDirectory;
import org.boris.pecoff4j.ImportDirectory;
import org.boris.pecoff4j.OptionalHeader;
import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.ResourceDirectory;
import org.boris.pecoff4j.SectionData;
import org.boris.pecoff4j.SectionHeader;
import org.boris.pecoff4j.SectionTable;
import org.boris.pecoff4j.constant.ImageDataDirectoryType;

public class SectionParser
{
    public static void parse(PE pe) throws IOException {
        readExportTable(pe);
        readImportTable(pe);
        readResourceTable(pe);
    }

    private static void readResourceTable(PE pe) throws IOException {
        OptionalHeader oh = pe.getOptionalHeader();
        SectionTable st = pe.getSectionTable();
        ImageDataDirectory idd = oh
                .getDataDirectory(ImageDataDirectoryType.IMPORT_TABLE);
        SectionHeader sh = st.findHeader(SectionTable.IMPORT_TABLE);
        SectionData sd = st.findSection(SectionTable.IMPORT_TABLE);
        if (idd.getVirtualAddress() != sh.getVirtualAddress())
            return;
        if (idd.getSize() != sh.getVirtualSize())
            return;
        byte[] b = (byte[]) sd.getEntry(0).getValue();
        ImportDirectory id = PEParser.readImportDirectory(
                new ByteArrayDataReader(b), idd.getVirtualAddress());
        sd.clear();
        sd.add(ImageDataDirectoryType.IMPORT_TABLE, id);
    }

    private static void readImportTable(PE pe) throws IOException {
        OptionalHeader oh = pe.getOptionalHeader();
        SectionTable st = pe.getSectionTable();
        ImageDataDirectory idd = oh.getResourceTable();
        SectionHeader sh = st.findHeader(SectionTable.RESOURCE_TABLE);
        SectionData sd = st.findSection(SectionTable.RESOURCE_TABLE);
        if (idd.getVirtualAddress() != sh.getVirtualAddress())
            return;
        if (idd.getSize() != sh.getVirtualSize())
            return;
        byte[] b = (byte[]) sd.getEntry(0).getValue();
        ResourceDirectory id = PEParser.readResourceDirectory(b, idd
                .getVirtualAddress());
        sd.clear();
        sd.add(ImageDataDirectoryType.RESOURCE_TABLE, id);
    }

    private static void readExportTable(PE pe) throws IOException {
        OptionalHeader oh = pe.getOptionalHeader();
        SectionTable st = pe.getSectionTable();
        ImageDataDirectory idd = oh.getExportTable();
        SectionHeader sh = st.findHeader(SectionTable.EXPORT_TABLE);
        SectionData sd = st.findSection(SectionTable.EXPORT_TABLE);
        if (idd.getVirtualAddress() != sh.getVirtualAddress())
            return;
        if (idd.getSize() != sh.getVirtualSize())
            return;
        byte[] b = (byte[]) sd.getEntry(0).getValue();
        ExportDirectoryTable id = PEParser
                .readExportDirectoryTable(new ByteArrayDataReader(b));
        sd.clear();
        sd.add(ImageDataDirectoryType.EXPORT_TABLE, id);
    }
}
