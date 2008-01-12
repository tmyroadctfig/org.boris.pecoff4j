package org.boris.pecoff4j.io;

import java.io.IOException;

/**
 * A wrapper interface for reading blocks of data.
 */
public interface OldDataReader {
    int readByte() throws IOException;

    long readDoubleWord() throws IOException;

    int readWord() throws IOException;

    long readLong() throws IOException;

    long read4or8bytes(boolean read4) throws IOException;

    String readUtf(int length) throws IOException;

    void read(byte[] b) throws IOException;
}
