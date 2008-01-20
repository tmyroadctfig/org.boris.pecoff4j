package org.boris.pecoff4j.buftest;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;

public class ByteArrayDataReader implements DataReader {

    public void close() throws IOException {
    }

    public int getPosition() {
        return 0;
    }

    public void read(byte[] signature) throws IOException {
    }

    public int readByte() throws IOException {
        return 0;
    }

    public int readDoubleWord() throws IOException {
        return 0;
    }

    public long readLong() throws IOException {
        return 0;
    }

    public String readUtf(int size) throws IOException {
        return null;
    }

    public int readWord() throws IOException {
        return 0;
    }

    public void skipBytes(int numBytes) throws IOException {
    }

}
