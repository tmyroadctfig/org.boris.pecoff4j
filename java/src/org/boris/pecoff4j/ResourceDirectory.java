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

import org.boris.pecoff4j.util.DataObject;

public class ResourceDirectory extends DataObject
{
    private ResourceDirectoryEntry entry;
    private List<ResourceTypeDirectory> types = new ArrayList();

    public void setEntry(ResourceDirectoryEntry entry) {
        this.entry = entry;
    }

    public ResourceDirectoryEntry getEntry() {
        return entry;
    }

    public void add(ResourceTypeDirectory type) {
        types.add(type);
    }

    public ResourceEntry[] findResources(int type) {
        return findResources(type, -1);
    }

    public ResourceEntry[] findResources(int type, int name) {
        List<ResourceEntry> entries = new ArrayList();
        for (int i = 0; i < types.size(); i++) {
            if (getEntryName(i) == type) {
                for (int j = 0; j < types.get(i).size(); j++) {
                    ResourceNameDirectory rn = types.get(i).getName(j);
                    int nameId = types.get(i).getEntryName(j);
                    if (name == -1 || name == nameId) {
                        for (int k = 0; k < rn.size(); k++) {
                            int language = types.get(i).getName(j)
                                    .getEntryName(k);
                            byte[] data = rn.getLanguage(k).getData();
                            entries.add(new ResourceEntry(data, nameId,
                                    language));
                        }
                    }
                }
            }
        }

        return entries.toArray(new ResourceEntry[0]);
    }

    public int size() {
        return types.size();
    }

    public ResourceTypeDirectory getType(int index) {
        return types.get(index);
    }

    public int getEntryName(int index) {
        return entry.getEntries()[index].getName();
    }
}
