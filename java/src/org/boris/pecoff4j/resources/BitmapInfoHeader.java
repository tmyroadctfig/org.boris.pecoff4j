package org.boris.pecoff4j.resources;

import java.io.IOException;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.io.IDataWriter;
import org.boris.pecoff4j.util.Reflection;

public class BitmapInfoHeader {
    private int size;
    private int width;
    private int height;
    private int planes;
    private int bitCount;
    private int compression;
    private int sizeImage;
    private int xpelsPerMeter;
    private int ypelsPerMeter;
    private int clrUsed;
    private int clrImportant;

    public static BitmapInfoHeader read(IDataReader dr) throws IOException {
        BitmapInfoHeader bh = new BitmapInfoHeader();
        bh.size = dr.readDoubleWord();
        bh.width = dr.readDoubleWord();
        bh.height = dr.readDoubleWord();
        bh.planes = dr.readWord();
        bh.bitCount = dr.readWord();
        bh.compression = dr.readDoubleWord();
        bh.sizeImage = dr.readDoubleWord();
        bh.xpelsPerMeter = dr.readDoubleWord();
        bh.ypelsPerMeter = dr.readDoubleWord();
        bh.clrUsed = dr.readDoubleWord();
        bh.clrImportant = dr.readDoubleWord();

        return bh;

    }

    public void write(IDataWriter dw) throws IOException {
        dw.writeDoubleWord(size);
        dw.writeDoubleWord(width);
        dw.writeDoubleWord(height);
        dw.writeWord(planes);
        dw.writeWord(bitCount);
        dw.writeDoubleWord(compression);
        dw.writeDoubleWord(sizeImage);
        dw.writeDoubleWord(xpelsPerMeter);
        dw.writeDoubleWord(ypelsPerMeter);
        dw.writeDoubleWord(clrUsed);
        dw.writeDoubleWord(clrImportant);
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

    public int getXpelsPerMeter() {
        return xpelsPerMeter;
    }

    public int getYpelsPerMeter() {
        return ypelsPerMeter;
    }

    public int getClrUsed() {
        return clrUsed;
    }

    public int getClrImportant() {
        return clrImportant;
    }
}
