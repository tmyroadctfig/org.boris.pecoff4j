package org.boris.pecoff4j.resources;

import java.io.IOException;

import org.boris.pecoff4j.io.ByteArrayDataReader;
import org.boris.pecoff4j.io.IDataReader;

public class Manifest {
    public static Manifest read(byte[] data) throws IOException {
        return read(new ByteArrayDataReader(data), data.length);
    }

    private String str;

    public static Manifest read(IDataReader dr, int length) throws IOException {
        Manifest mf = new Manifest();
        mf.str = dr.readUtf(length);
        return mf;
    }

    public String toString() {
        return str;
    }

    public String get() {
        return str;
    }

    public void set(String str) {
        this.str = str;
    }
}
