package org.boris.pecoff4j;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

public class SectionTable {
    private SectionHeader[] headers;

    public static SectionTable read(int numberOfSections, DataReader dr)
            throws IOException {
        SectionTable st = new SectionTable();
        st.readFrom(numberOfSections, dr);
        return st;
    }

    private void readFrom(int numberOfSections, DataReader dr)
            throws IOException {
        dr.readWord(); // padding to 8-byte boundary?
        headers = new SectionHeader[numberOfSections];
        for (int i = 0; i < numberOfSections; i++) {
            headers[i] = SectionHeader.read(dr);
        }
    }

    public int getNumberOfSections() {
        return headers.length;
    }

    public SectionHeader getSection(int index) {
        return headers[index];
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
