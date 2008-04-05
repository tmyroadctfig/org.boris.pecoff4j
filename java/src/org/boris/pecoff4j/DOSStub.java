package org.boris.pecoff4j;
import java.io.IOException;

import org.boris.pecoff4j.io.DataWriter;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

/**
 * Used to store the stub program.
 */
public class DOSStub {
    private byte[] stub;

    public static DOSStub read(DOSHeader header, IDataReader dr) throws IOException {
        DOSStub ds = new DOSStub();
        ds.readFrom(header, dr);
        return ds;
    }

    private void readFrom(DOSHeader header, IDataReader dr) throws IOException {
        int pos = dr.getPosition();
        int add = header.getAddressOfNewExeHeader();
        stub = new byte[add - pos];
        dr.read(stub);
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public void write(DataWriter dw) {
    }
}
