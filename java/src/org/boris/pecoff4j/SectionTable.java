/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.boris.pecoff4j.io.DataWriter;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class SectionTable
{
    private SectionHeader[] headers;
    private Map<String, SectionHeader> sectionByName;
    private Map<String, byte[]> sectionData;
    private RVAConverter rvaConverter;

    public static SectionTable read(int numberOfSections, IDataReader dr)
            throws IOException {
        SectionTable st = new SectionTable();
        st.readFrom(numberOfSections, dr);
        return st;
    }

    private void readFrom(int numberOfSections, IDataReader dr)
            throws IOException {
        headers = new SectionHeader[numberOfSections];
        sectionByName = new LinkedHashMap();
        for (int i = 0; i < numberOfSections; i++) {
            headers[i] = SectionHeader.read(dr);
            sectionByName.put(headers[i].getName(), headers[i]);
        }

        // Now sort on section address and load
        List<SectionHeader> sections = Arrays.asList(headers);
        Collections.sort(sections, new Comparator<SectionHeader>() {
            public int compare(SectionHeader o1, SectionHeader o2) {
                return o1.getPointerToRawData() - o2.getPointerToRawData();
            }
        });

        sectionData = new HashMap();
        for (SectionHeader sh : sections) {
            if (sh.getPointerToRawData() != 0) {
                dr.jumpTo(sh.getPointerToRawData());
                byte[] data = new byte[sh.getSizeOfRawData()];
                dr.read(data);
                sectionData.put(sh.getName(), data);
            }
        }

        // Store sorted sections based on virtual address
        SectionHeader[] sorted = Arrays.asList(headers).toArray(
                new SectionHeader[0]);
        Arrays.sort(sorted, new Comparator<SectionHeader>() {
            public int compare(SectionHeader o1, SectionHeader o2) {
                return o1.getVirtualAddress() - o2.getVirtualAddress();
            }
        });
        int[] virtualAddress = new int[sorted.length];
        int[] pointerToRawData = new int[sorted.length];
        for (int i = 0; i < sorted.length; i++) {
            virtualAddress[i] = sorted[i].getVirtualAddress();
            pointerToRawData[i] = sorted[i].getPointerToRawData();
        }

        this.rvaConverter = new RVAConverter(virtualAddress, pointerToRawData);
    }

    public int getNumberOfSections() {
        return headers.length;
    }

    public SectionHeader getSection(int index) {
        return headers[index];
    }

    public SectionHeader getSection(String name) {
        return sectionByName.get(name);
    }

    public String[] getSectionNames() {
        return sectionByName.keySet().toArray(new String[0]);
    }

    public byte[] getSectionData(String name) {
        return sectionData.get(name);
    }

    public void putSection(String name, byte[] data) {
        sectionData.put(name, data);
    }

    public RVAConverter getRVAConverter() {
        return this.rvaConverter;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public void write(DataWriter dw) {
    }
}
