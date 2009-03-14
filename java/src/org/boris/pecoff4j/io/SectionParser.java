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
import org.boris.pecoff4j.constant.SectionDataType;

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
        ImageDataDirectory idd = oh.getImportTable();
        SectionHeader sh = st.getHeader(SectionTable.IMPORT_TABLE);
        SectionData sd = st.getData(SectionTable.IMPORT_TABLE);
        if (idd.getVirtualAddress() != sh.getVirtualAddress())
            return;
        if (idd.getSize() != sh.getVirtualSize())
            return;
        byte[] b = (byte[]) sd.getEntry(0).getValue();
        ImportDirectory id = PEParser.readImportDirectory(
                new ByteArrayDataReader(b), idd.getVirtualAddress());
        sd = new SectionData();
        sd.add(SectionDataType.IMPORT_TABLE, id);
        st.putData(SectionTable.IMPORT_TABLE, sd);
    }

    private static void readImportTable(PE pe) throws IOException {
        OptionalHeader oh = pe.getOptionalHeader();
        SectionTable st = pe.getSectionTable();
        ImageDataDirectory idd = oh.getResourceTable();
        SectionHeader sh = st.getHeader(SectionTable.RESOURCE_TABLE);
        SectionData sd = st.getData(SectionTable.RESOURCE_TABLE);
        if (idd.getVirtualAddress() != sh.getVirtualAddress())
            return;
        if (idd.getSize() != sh.getVirtualSize())
            return;
        byte[] b = (byte[]) sd.getEntry(0).getValue();
        ResourceDirectory id = PEParser.readResourceDirectory(b, idd
                .getVirtualAddress());
        sd = new SectionData();
        sd.add(SectionDataType.RESOURCE_TABLE, id);
        st.putData(SectionTable.RESOURCE_TABLE, sd);
    }

    private static void readExportTable(PE pe) throws IOException {
        OptionalHeader oh = pe.getOptionalHeader();
        SectionTable st = pe.getSectionTable();
        ImageDataDirectory idd = oh.getExportTable();
        SectionHeader sh = st.getHeader(SectionTable.EXPORT_TABLE);
        SectionData sd = st.getData(SectionTable.EXPORT_TABLE);
        if (idd.getVirtualAddress() != sh.getVirtualAddress())
            return;
        if (idd.getSize() != sh.getVirtualSize())
            return;
        byte[] b = (byte[]) sd.getEntry(0).getValue();
        ExportDirectoryTable id = PEParser
                .readExportDirectoryTable(new ByteArrayDataReader(b));
        sd = new SectionData();
        sd.add(SectionDataType.EXPORT_TABLE, id);
        st.putData(SectionTable.EXPORT_TABLE, sd);
    }
}
