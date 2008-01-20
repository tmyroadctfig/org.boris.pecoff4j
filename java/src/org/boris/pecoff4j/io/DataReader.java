package org.boris.pecoff4j.io;

import java.io.IOException;

public interface DataReader {

    public abstract int readByte() throws IOException;

    public abstract int readWord() throws IOException;

    public abstract int readDoubleWord() throws IOException;

    public abstract long readLong() throws IOException;

    public abstract int getPosition();

    public abstract void skipBytes(int numBytes) throws IOException;

    public abstract void close() throws IOException;

    public abstract void read(byte[] buffer) throws IOException;

    public String readUtf(int size) throws IOException;

}