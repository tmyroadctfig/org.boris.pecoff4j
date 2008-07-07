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

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class ImportDirectoryEntry
{
    private int importLookupTableRVA;
    private int timeDateStamp;
    private int forwarderChain;
    private int nameRVA;
    private int importAddressTableRVA;

    public static ImportDirectoryEntry read(IDataReader dr) throws IOException {
        ImportDirectoryEntry id = new ImportDirectoryEntry();
        id.importLookupTableRVA = dr.readDoubleWord();
        id.timeDateStamp = dr.readDoubleWord();
        id.forwarderChain = dr.readDoubleWord();
        id.nameRVA = dr.readDoubleWord();
        id.importAddressTableRVA = dr.readDoubleWord();

        // The last entry is null
        if (id.importLookupTableRVA == 0) {
            return null;
        }

        return id;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getImportLookupTableRVA() {
        return importLookupTableRVA;
    }

    public int getTimeDateStamp() {
        return timeDateStamp;
    }

    public int getForwarderChain() {
        return forwarderChain;
    }

    public int getNameRVA() {
        return nameRVA;
    }

    public int getImportAddressTableRVA() {
        return importAddressTableRVA;
    }
}
