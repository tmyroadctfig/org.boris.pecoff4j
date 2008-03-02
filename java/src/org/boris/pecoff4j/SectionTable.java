package org.boris.pecoff4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

public class SectionTable {
    private SectionHeader[] headers;
    private Map sectionByName;

    public static SectionTable read(int numberOfSections, DataReader dr)
            throws IOException {
        SectionTable st = new SectionTable();
        st.readFrom(numberOfSections, dr);
        return st;
    }

    private void readFrom(int numberOfSections, DataReader dr)
            throws IOException {
        headers = new SectionHeader[numberOfSections];
        sectionByName = new HashMap();
        for (int i = 0; i < numberOfSections; i++) {
            headers[i] = SectionHeader.read(dr);
            sectionByName.put(headers[i].getName(), headers[i]);
        }
    }

    public int getNumberOfSections() {
        return headers.length;
    }

    public SectionHeader getSection(int index) {
        return headers[index];
    }

    public SectionHeader getSection(String name) {
        return (SectionHeader) sectionByName.get(name);
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
