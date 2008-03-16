package org.boris.pecoff4j.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class VarFileInfo {
    private String key;
    private List<String> names = new ArrayList();
    private List<String> values = new ArrayList();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int size() {
        return names.size();
    }

    public String getName(int index) {
        return names.get(index);
    }

    public String getValue(int index) {
        return values.get(index);
    }

    public void add(String name, String value) {
        names.add(name);
        values.add(value);
    }

    public void clear() {
        names.clear();
        values.clear();
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public static VarFileInfo read(IDataReader dr) throws IOException {
        VarFileInfo vfi = new VarFileInfo();
        vfi.key = dr.readUnicode();
        String name = null;
        while ((name = dr.readUnicode()) != null) {
            vfi.names.add(name);
            if (name.length() % 2 == 1)
                dr.readWord();
            vfi.values.add(dr.readUnicode());
        }
        return vfi;
    }

}
