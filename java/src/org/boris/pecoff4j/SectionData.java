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

import org.boris.pecoff4j.constant.SectionDataType;

public class SectionData
{
    private List<SectionDataEntry> entries = new ArrayList();

    public void add(SectionDataEntry entry) {
        entries.add(entry);
    }

    public void add(int type, Object value) {
        SectionDataEntry sde = new SectionDataEntry();
        sde.setType(type);
        sde.setValue(value);
        add(sde);
    }

    public void add(byte[] data) {
        add(SectionDataType.RAW, data);
    }

    public void add(ResourceDirectory rd) {
        add(SectionDataType.RESOURCES, rd);
    }

    public int size() {
        return entries.size();
    }

    public SectionDataEntry getEntry(int index) {
        return entries.get(index);
    }
}
