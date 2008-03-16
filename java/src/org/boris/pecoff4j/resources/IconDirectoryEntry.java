package org.boris.pecoff4j.resources;

import java.io.IOException;

import org.boris.pecoff4j.GroupIconDirectoryEntry;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.io.IDataWriter;
import org.boris.pecoff4j.util.Reflection;

public class IconDirectoryEntry {
    private int width;
    private int height;
    private int colorCount;
    private int reserved;
    private int planes;
    private int bitCount;
    private int bytesInRes;
    private int offset;

    public void copyFrom(GroupIconDirectoryEntry gide) {
        width = gide.getWidth();
        height = gide.getHeight();
        colorCount = gide.getColorCount();
        reserved = 0;
        planes = gide.getPlanes();
        bitCount = gide.getBitCount();
        bytesInRes = gide.getBitCount();
        offset = 0;
    }

    public static IconDirectoryEntry read(IDataReader dr) throws IOException {
        IconDirectoryEntry ge = new IconDirectoryEntry();
        ge.width = dr.readByte();
        ge.height = dr.readByte();
        ge.colorCount = dr.readByte();
        ge.reserved = dr.readByte();
        ge.planes = dr.readWord();
        ge.bitCount = dr.readWord();
        ge.bytesInRes = dr.readDoubleWord();
        ge.offset = dr.readDoubleWord();

        return ge;
    }

    public static int sizeOf() {
        return 16;
    }

    public void write(IDataWriter dw) throws IOException {
        dw.writeByte(width);
        dw.writeByte(height);
        dw.writeByte(colorCount);
        dw.writeByte(reserved);
        dw.writeWord(planes);
        dw.writeWord(bitCount);
        dw.writeDoubleWord(bytesInRes);
        dw.writeDoubleWord(offset);
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

    public int getOffset() {
        return offset;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setColorCount(int colorCount) {
        this.colorCount = colorCount;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public void setPlanes(int planes) {
        this.planes = planes;
    }

    public void setBitCount(int bitCount) {
        this.bitCount = bitCount;
    }

    public void setBytesInRes(int bytesInRes) {
        this.bytesInRes = bytesInRes;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
