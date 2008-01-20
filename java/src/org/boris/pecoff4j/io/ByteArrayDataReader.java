package org.boris.pecoff4j.io;

import java.io.IOException;


public class ByteArrayDataReader implements DataReader {
    private byte[] buffer;
    private int position = 0;

    public ByteArrayDataReader(byte[] buffer) {
        this.buffer = buffer;
    }

    public void jumpTo(int position) {
        this.position = position;
    }

    public int readByte() throws IOException {
        return read();
    }

    public int readWord() throws IOException {
        return read() | read() << 8;
    }

    public int readDoubleWord() throws IOException {
        return read() | read() << 8 | read() << 16 | read() << 24;
    }

    public long readLong() throws IOException {
        return readDoubleWord() | ((long) readDoubleWord()) << 32;
    }

    public int getPosition() {
        return position;
    }

    public void skipBytes(int numBytes) throws IOException {
        position += numBytes;
    }

    public void read(byte[] b) throws IOException {
        position += b.length;
        System.arraycopy(buffer, position, b, 0, b.length);
    }

    public String readUtf(int size) throws IOException {
        byte b[] = new byte[size];
        read(b);
        int i = 0;
        for (; i < size; i++) {
            if (b[i] == 0)
                break;
        }
        return new String(b, 0, i);
    }

    private int read() {
        return buffer[position++];
    }

    public void close() throws IOException {
    }
}
