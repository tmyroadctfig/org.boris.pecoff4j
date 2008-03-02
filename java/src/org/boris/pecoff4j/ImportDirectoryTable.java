package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

/**
 * The import directory table. Refer to section 6.4.1 of the spec.
 */
public class ImportDirectoryTable {
    private long importLookupTableRVA;
    private long timeDateStamp;
    private long forwarderChain;
    private long nameRVA;
    private long importAddressTableRVA;

    public static ImportDirectoryTable read(DataReader dis) throws IOException {
        ImportDirectoryTable idt = new ImportDirectoryTable();
        idt.importLookupTableRVA = dis.readDoubleWord();
        idt.timeDateStamp = dis.readDoubleWord();
        idt.forwarderChain = dis.readDoubleWord();
        idt.nameRVA = dis.readDoubleWord();
        idt.importAddressTableRVA = dis.readDoubleWord();
        return idt;
    }

    public long getImportLookupTableRVA() {
        return importLookupTableRVA;
    }

    public long getTimeDateStamp() {
        return timeDateStamp;
    }

    public long getForwarderChain() {
        return forwarderChain;
    }

    public long getNameRVA() {
        return nameRVA;
    }

    public long getImportAddressTableRVA() {
        return importAddressTableRVA;
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
