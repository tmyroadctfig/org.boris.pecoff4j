package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

/**
 * The MS-DOS 2.0 stub program.
 */
public class DOSStub {

    private byte[] stub;

    public static DOSStub read(DOSHeader header, DataReader dr)
            throws IOException {
        DOSStub ds = new DOSStub();
        ds.readFrom(header, dr);
        return ds;
    }

    private void readFrom(DOSHeader header, DataReader dr) throws IOException {
        int pos = dr.getPosition();
        int add = (int) header.getAddressOfNewExeHeader();
        stub = new byte[add - pos];
        dr.read(stub);
    }

    public String toString() {
        return Reflection.toString(this);
    }

}
