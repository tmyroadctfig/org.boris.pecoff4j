package org.boris.pecoff4j;

import java.io.DataInputStream;
import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;

import com.sun.org.apache.bcel.internal.util.ByteSequence;

/**
 * The PE signature block.
 */
public class PESignature {
    private byte[] signature;

    public static PESignature read(DataReader dr) throws IOException {
        PESignature s = new PESignature();
        s.readFrom(dr);
        return s;
    }

    public void readFrom(DataReader dr) throws IOException {
        signature = new byte[4];
        dr.read(signature);
    }

    public String toString() {
        try {
            return DataInputStream.readUTF(new ByteSequence(signature));
        } catch (IOException e) {
            return null;
        }
    }
}
