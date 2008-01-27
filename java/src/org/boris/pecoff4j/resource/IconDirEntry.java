package org.boris.pecoff4j.resource;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

public class IconDirEntry {
    private int width;
    private int height;
    private int colorCount;
    private int reserved;
    private int planes;
    private int bitCount;
    private int bytesInRes;
    private int imageOffset;

    public static IconDirEntry read(DataReader dr) throws IOException {
        IconDirEntry ide = new IconDirEntry();
        ide.width = dr.readByte();
        ide.height = dr.readByte();
        ide.colorCount = dr.readByte();
        ide.reserved = dr.readByte();
        ide.planes = dr.readWord();
        ide.bitCount = dr.readWord();
        ide.bytesInRes = dr.readDoubleWord();
        ide.imageOffset = dr.readDoubleWord();

        return ide;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getColorCount() {
        return colorCount;
    }

    public int getReserved() {
        return reserved;
    }

    public int getPlanes() {
        return planes;
    }

    public int getBitCount() {
        return bitCount;
    }

    public int getBytesInRes() {
        return bytesInRes;
    }

    public int getImageOffset() {
        return imageOffset;
    }
}
