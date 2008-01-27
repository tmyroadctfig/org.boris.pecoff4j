package org.boris.pecoff4j.resource;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

public class BitmapFileHeader {
    private int type;
    private int size;
    private int reserved1;
    private int reserved2;
    private int offBits;

    public static BitmapFileHeader read(DataReader dr) throws IOException {
        BitmapFileHeader bfh = new BitmapFileHeader();
        bfh.type = dr.readWord();
        bfh.size = dr.readDoubleWord();
        bfh.reserved1 = dr.readWord();
        bfh.reserved2 = dr.readWord();
        bfh.offBits = dr.readDoubleWord();

        return bfh;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getReserved1() {
        return reserved1;
    }

    public int getReserved2() {
        return reserved2;
    }

    public int getOffBits() {
        return offBits;
    }
}
