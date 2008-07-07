/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.imports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class ImportDirectory
{
    List<ImportDirectoryEntry> entries = new ArrayList();
    List<String> names = new ArrayList();
    List<ImportDirectoryTable> nameTables = new ArrayList();
    List<ImportDirectoryTable> addressTables = new ArrayList();

    public static ImportDirectory read(IDataReader dr, int baseAddress)
            throws IOException {
        ImportDirectory id = new ImportDirectory();
        ImportDirectoryEntry ide = null;
        while ((ide = ImportDirectoryEntry.read(dr)) != null) {
            id.entries.add(ide);
        }

        for (ImportDirectoryEntry e : id.entries) {
            dr.jumpTo(e.getNameRVA() - baseAddress);
            id.names.add(dr.readUtf());
            dr.jumpTo(e.getImportLookupTableRVA() - baseAddress);
            id.nameTables.add(ImportDirectoryTable.read(dr, baseAddress));
            dr.jumpTo(e.getImportAddressTableRVA() - baseAddress);
            id.addressTables.add(ImportDirectoryTable.read(dr, baseAddress));
        }

        return id;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int size() {
        return entries.size();
    }

    public String getName(int index) {
        return names.get(index);
    }

    public ImportDirectoryTable getNameTable(int index) {
        return nameTables.get(index);
    }

    public ImportDirectoryTable getAddressTable(int index) {
        return addressTables.get(index);
    }

    public ImportDirectoryEntry getEntry(int index) {
        return entries.get(index);
    }
}
