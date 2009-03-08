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

import java.util.Map;

public class SectionTable
{
    private SectionHeader[] headers;
    private Map<String, SectionHeader> sectionByName;
    private Map<String, byte[]> sectionData;
    private RVAConverter rvaConverter;

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
        return rvaConverter;
    }

    public void setHeaders(SectionHeader[] headers) {
        this.headers = headers;
    }

    public void setRvaConverter(RVAConverter rvaConverter) {
        this.rvaConverter = rvaConverter;
    }
}
