package org.boris.pecoff4j.io;

import java.io.DataInputStream;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.util.ByteSequence;

public class DataInputStreamDataReader implements OldDataReader {
    private DataInputStream dis;

    public DataInputStreamDataReader(DataInputStream dis) {
        this.dis = dis;
    }

    public long readDoubleWord() throws IOException {
        return dis.readInt();
    }

    public long read4or8bytes(boolean readDouble) throws IOException {
        return readDouble ? readDoubleWord() : readWord();
    }

    public int readWord() throws IOException {
        return dis.readUnsignedShort();
    }

    public String readUtf(int length) throws IOException {
        byte[] b = new byte[length];
        dis.read(b);
        ByteSequence bs = new ByteSequence(b);
        return DataInputStream.readUTF(bs);
    }

    public void read(byte[] b) throws IOException {
        dis.read(b);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.boris.pecoff4j.io.OldDataReader#readByte()
     */
    public int readByte() throws IOException {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.boris.pecoff4j.io.OldDataReader#readLong()
     */
    public long readLong() throws IOException {
        return 0;
    }
}