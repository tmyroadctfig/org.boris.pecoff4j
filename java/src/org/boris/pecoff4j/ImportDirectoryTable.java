package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;

/**
 * The import directory table. Refer to section 6.4.1 of the spec.
 */
public class ImportDirectoryTable {
    private long importLookupTableRVA;
    private long timeDateStamp;
    private long forwarderChain;
    private long nameRVA;
    private long importAddressTableRVA;

    public void read(DataReader dis) throws IOException {
        importLookupTableRVA = dis.readDoubleWord();
        timeDateStamp = dis.readDoubleWord();
        forwarderChain = dis.readDoubleWord();
        nameRVA = dis.readDoubleWord();
        importAddressTableRVA = dis.readDoubleWord();
    }
}
