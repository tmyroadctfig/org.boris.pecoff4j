package org.boris.pecoff4j.resource;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

public class Bitmap {
    private BitmapFileHeader fileHeader;
    private BitmapInfoHeader infoHeader;
    private byte[] colors;
    private byte[] bitmapBits;

    public static Bitmap read(DataReader dr) throws IOException {
        Bitmap bm = new Bitmap();
        bm.fileHeader = BitmapFileHeader.read(dr);
        bm.infoHeader = BitmapInfoHeader.read(dr);

        return bm;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public BitmapFileHeader getFileHeader() {
        return fileHeader;
    }

    public BitmapInfoHeader getInfoHeader() {
        return infoHeader;
    }

    public byte[] getColors() {
        return colors;
    }

    public byte[] getBitmapBits() {
        return bitmapBits;
    }
}
