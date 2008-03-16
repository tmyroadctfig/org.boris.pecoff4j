package org.boris.pecoff4j.io;

import java.io.IOException;

public interface IDataWriter {
    void writeByte(int b) throws IOException;

    void writeWord(int w) throws IOException;

    void writeDoubleWord(int dw) throws IOException;

    void writeBytes(byte[] b) throws IOException;
}
