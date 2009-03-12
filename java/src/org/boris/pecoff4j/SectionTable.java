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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionTable
{
    private List<SectionHeader> headers = new ArrayList();
    private Map<String, SectionHeader> sectionByName = new HashMap();
    private Map<String, SectionData> sectionData = new HashMap();
    private RVAConverter rvaConverter;

    public void add(SectionHeader header) {
        headers.add(header);
        sectionByName.put(header.getName(), header);
    }

    public int getNumberOfSections() {
        return headers.size();
    }

    public SectionHeader getHeader(int index) {
        return headers.get(index);
    }

    public SectionHeader getHeader(String name) {
        return sectionByName.get(name);
    }

    public String[] getSectionNames() {
        return sectionByName.keySet().toArray(new String[0]);
    }

    public SectionData getData(String name) {
        return sectionData.get(name);
    }

    public void putData(String name, SectionData data) {
        sectionData.put(name, data);
    }

    public RVAConverter getRVAConverter() {
        return rvaConverter;
    }

    public void setRvaConverter(RVAConverter rvaConverter) {
        this.rvaConverter = rvaConverter;
    }
}
