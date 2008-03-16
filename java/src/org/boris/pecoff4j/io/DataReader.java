package org.boris.pecoff4j.io;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataReader implements IDataReader {
    private DataInputStream dis;
    private int position = 0;

    public DataReader(InputStream is) {
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

    public long readLong() throws IOException {
        return readDoubleWord() | ((long) readDoubleWord()) << 32;
    }

    public int readDoubleWord() throws IOException {
        position += 4;
        return dis.read() | dis.read() << 8 | dis.read() << 16 | dis.read() << 24;
    }

    public int getPosition() {
        return position;
    }

    public void jumpTo(int location) throws IOException {
        if (location < position)
            throw new IOException("DataReader does not support scanning backwards (" + location
                    + ")");
        skipBytes(location - position);
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
        for (; i < b.length; i++) {
            if (b[i] == 0)
                break;
        }
        return new String(b, 0, i);
    }

    public String readUtf() throws IOException {
        StringBuilder sb = new StringBuilder();
        char c = 0;
        while ((c = (char) readByte()) != 0) {
            sb.append(c);
        }
        return sb.toString();
    }

    public String readUnicode() throws IOException {
        StringBuilder sb = new StringBuilder();
        char c = 0;
        while ((c = (char) readWord()) != 0) {
            sb.append(c);
        }
        if (sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    public String readUnicode(int size) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append((char) readWord());
        }
        return sb.toString();
    }
}