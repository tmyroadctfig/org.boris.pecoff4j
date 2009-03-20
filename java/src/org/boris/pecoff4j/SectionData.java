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
import java.util.List;

import org.boris.pecoff4j.constant.ImageDataDirectoryType;

public class SectionData
{
    private List<SectionDataEntry> entries = new ArrayList();
    private byte[] preamble;

    public void add(SectionDataEntry entry) {
        entries.add(entry);
    }

    public void clear() {
        entries.clear();
    }

    public void add(int type, Object value) {
        add(new SectionDataEntry(type, value));
    }

    public void add(byte[] data) {
        add(ImageDataDirectoryType.RAW, data);
    }

    public void add(ResourceDirectory rd) {
        add(ImageDataDirectoryType.RESOURCE_TABLE, rd);
    }

    public int size() {
        return entries.size();
    }

    public SectionDataEntry getEntry(int index) {
        return entries.get(index);
    }

    public byte[] getPreamble() {
        return preamble;
    }

    public void setPreamble(byte[] preamble) {
        this.preamble = preamble;
    }
}
