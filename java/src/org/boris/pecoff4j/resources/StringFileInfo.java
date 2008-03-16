package org.boris.pecoff4j.resources;

import java.io.IOException;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class StringFileInfo {

    private String key;

    public static StringFileInfo read(IDataReader dr, int length) throws IOException {
        StringFileInfo sfi = new StringFileInfo();

        return sfi;
    }

    public String toString() {
        return Reflection.toString(this);
    }

}
