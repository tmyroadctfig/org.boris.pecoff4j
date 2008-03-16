package org.boris.pecoff4j.imports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class ImportDirectoryTable {
    private List<ImportEntry> imports;

    public static ImportDirectoryTable read(IDataReader dr, int baseAddress) throws IOException {
        ImportDirectoryTable idt = new ImportDirectoryTable();
        idt.imports = new ArrayList();
        ImportEntry ie = null;
        while ((ie = ImportEntry.read(dr)) != null) {
            idt.imports.add(ie);
        }

        for (ImportEntry iee : idt.imports) {
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

    public int size() {
        return imports.size();
    }

    public ImportEntry getEntry(int index) {
        return imports.get(index);
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
