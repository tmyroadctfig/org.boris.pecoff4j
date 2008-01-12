package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;

/**
 * The export directory table. See section 6.3.1 of the PE/COFF specification
 * v8.
 */
public class ExportDirectoryTable {
    private long exportFlags;
    private long timeDateStamp;
    private int majorVersion;
    private int minorVersion;
    private long nameRVA;
    private long ordinalBase;
    private long addressTableEntries;
    private long numberOfNamePointers;
    private long exportAddressTableRVA;
    private long namePointerRVA;
    private long ordinalTableRVA;

    public void read(DataReader dis) throws IOException {
        exportFlags = dis.readDoubleWord();
        timeDateStamp = dis.readDoubleWord();
        majorVersion = dis.readWord();
        minorVersion = dis.readWord();
        nameRVA = dis.readDoubleWord();
        ordinalBase = dis.readDoubleWord();
        addressTableEntries = dis.readDoubleWord();
        numberOfNamePointers = dis.readDoubleWord();
        exportAddressTableRVA = dis.readDoubleWord();
        namePointerRVA = dis.readDoubleWord();
        ordinalTableRVA = dis.readDoubleWord();
    }
}
