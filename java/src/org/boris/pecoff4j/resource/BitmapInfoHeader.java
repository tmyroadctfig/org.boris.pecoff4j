package org.boris.pecoff4j.resource;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

public class BitmapInfoHeader {
    private int size;
    private int width;
    private int height;
    private int planes;
    private int bitCount;
    private int compression;
    private int sizeImage;
    private int xPelsPerMeter;
    private int yPelsPerMeter;
    private int clrUsed;
    private int clrImportant;

    public static BitmapInfoHeader read(DataReader dr) throws IOException {
        BitmapInfoHeader bih = new BitmapInfoHeader();
        bih.size = dr.readDoubleWord();
        bih.width = dr.readDoubleWord();
        bih.height = dr.readDoubleWord();
        bih.planes = dr.readWord();
        bih.bitCount = dr.readWord();
        bih.compression = dr.readDoubleWord();
        bih.sizeImage = dr.readDoubleWord();
        bih.xPelsPerMeter = dr.readDoubleWord();
        bih.yPelsPerMeter = dr.readDoubleWord();
        bih.clrUsed = dr.readDoubleWord();
        bih.clrImportant = dr.readDoubleWord();

        return bih;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getSize() {
        return size;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPlanes() {
        return planes;
    }

    public int getBitCount() {
        return bitCount;
    }

    public int getCompression() {
        return compression;
    }

    public int getSizeImage() {
        return sizeImage;
    }

    public int getXPelsPerMeter() {
        return xPelsPerMeter;
    }

    public int getYPelsPerMeter() {
        return yPelsPerMeter;
    }

    public int getClrUsed() {
        return clrUsed;
    }

    public int getClrImportant() {
        return clrImportant;
    }
}
