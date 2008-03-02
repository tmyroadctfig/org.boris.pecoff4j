package org.boris.pecoff4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.Reflection;

public class SectionTable {
    private SectionHeader[] headers;
    private Map sectionByName;
    private Map sectionData;

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
        sectionData = new HashMap();
        for (int i = 0; i < numberOfSections; i++) {
            headers[i] = SectionHeader.read(dr);
            sectionByName.put(headers[i].getName(), headers[i]);
        }

        // Sort sections based on pointer to raw data
        SectionHeader[] cp = new SectionHeader[headers.length];
        System.arraycopy(headers, 0, cp, 0, headers.length);
        Arrays.sort(cp, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((SectionHeader) o1).getPointerToRawData() -
                        ((SectionHeader) o2).getPointerToRawData();
            }
        });

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

    public byte[] getSectionData(String name) {
        return (byte[]) sectionData.get(name);
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
