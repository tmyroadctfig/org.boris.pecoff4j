package org.boris.pecoff4j;
import java.io.IOException;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class PESignature {
    private byte[] signature;

    public static PESignature read(IDataReader dr) throws IOException {
        PESignature ps = new PESignature();
        ps.readFrom(dr);
        return ps;
    }

    private void readFrom(IDataReader dr) throws IOException {
        signature = new byte[4];
        dr.read(signature);
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
