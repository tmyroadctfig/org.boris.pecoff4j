package org.boris.pecoff4j.resources;

import java.io.IOException;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.io.IDataWriter;
import org.boris.pecoff4j.util.Reflection;

public class RGBQuad {
    private int blue;
    private int green;
    private int red;
    private int reserved;

    public static RGBQuad read(IDataReader dr) throws IOException {
        RGBQuad r = new RGBQuad();
        r.blue = dr.readByte();
        r.green = dr.readByte();
        r.red = dr.readByte();
        r.reserved = dr.readByte();
        return r;
    }

    public void write(IDataWriter dw) throws IOException {
        dw.writeByte(blue);
        dw.writeByte(green);
        dw.writeByte(red);
        dw.writeByte(reserved);
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

    public int getRed() {
        return red;
    }

    public int getReserved() {
        return reserved;
    }
}
