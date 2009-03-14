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

import org.boris.pecoff4j.ImageDataDirectory;
import org.boris.pecoff4j.OptionalHeader;
import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.SectionData;
import org.boris.pecoff4j.SectionDataEntry;
import org.boris.pecoff4j.SectionHeader;
import org.boris.pecoff4j.SectionTable;

public class SectionParser
{
    public static void parse(PE pe) throws IOException {
        readExportTable(pe);
        readImportTable(pe);
        readResourceTable(pe);
    }

    private static void readResourceTable(PE pe) {
        OptionalHeader oh = pe.getOptionalHeader();
        SectionTable st = pe.getSectionTable();

        ImageDataDirectory idd = oh.getResourceTable();
        if (idd == null)
            return;
        int address = idd.getVirtualAddress();
        int size = idd.getSize();
        if (size == 0)
            return;

        SectionHeader sh = st.getHeader(".rsrc");
        if (sh == null)
            return;

        if (sh.getVirtualAddress() != address)
            return;

        if (sh.getVirtualSize() != size)
            return;

        SectionData sd = st.getData(".rsrc");
        if (sd == null)
            return;

        SectionDataEntry sde = sd.getEntry(0);

    }

    private static void readImportTable(PE pe) {
    }

    private static void readExportTable(PE pe) {
    }
}
