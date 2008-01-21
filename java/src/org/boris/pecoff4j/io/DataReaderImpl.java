package org.boris.pecoff4j.io;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataReaderImpl implements DataReader {
    private DataInputStream dis;
    int position = 0;

    public DataReaderImpl(InputStream is) {
        this.dis = new DataInputStream(new BufferedInputStream(is));
    }

    public int readByte() throws IOException {
        position += 1;
        return dis.read();
    }

    public int readWord() throws IOException {
        position += 2;
        return dis.read() | dis.read() << 8;
    }

    public int readDoubleWord() throws IOException {
        position += 4;
        return dis.read() | dis.read() << 8 | dis.read() << 16
                | dis.read() << 24;
    }

    public long readLong() throws IOException {
        return readDoubleWord() | ((long) readDoubleWord()) << 32;
    }

    public int getPosition() {
        return position;
    }

    public void skipBytes(int numBytes) throws IOException {
        position += numBytes;
        dis.skipBytes(numBytes);
    }

    public void close() throws IOException {
        dis.close();
    }

    public void read(byte[] b) throws IOException {
        position += b.length;
        dis.read(b);
    }

    public String readUtf(int size) throws IOException {
        position += size;
        byte b[] = new byte[size];
        dis.read(b);
        int i = 0;
        for (; i < size; i++) {
            if (b[i] == 0)
                break;
        }
        return new String(b, 0, i);
    }

    public void jumpTo(int position) {
        throw new RuntimeException("Not implemented: DataReader.jumpTo");
    }
}