package org.boris.pecoff4j;

import org.boris.pecoff4j.util.Reflection;

public class ResourceEntry {
    private byte[] data;
    private int name;
    private int language;

    public ResourceEntry(byte[] data, int name, int language) {
        this.data = data;
        this.name = name;
        this.language = language;
    }

    public byte[] getData() {
        return data;
    }

    public int getName() {
        return name;
    }

    public int getLanguage() {
        return language;
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
