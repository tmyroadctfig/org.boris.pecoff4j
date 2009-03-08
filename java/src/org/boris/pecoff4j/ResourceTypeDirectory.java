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

public class ResourceTypeDirectory
{
    private ResourceDirectoryEntry entry;
    private ArrayList names = new ArrayList();

    public void setEntry(ResourceDirectoryEntry entry) {
        this.entry = entry;
    }

    public ResourceDirectoryEntry getEntry() {
        return entry;
    }

    public void add(ResourceNameDirectory name) {
        names.add(name);
    }

    public int size() {
        return names.size();
    }

    public ResourceNameDirectory getName(int index) {
        return (ResourceNameDirectory) names.get(index);
    }

    public int getEntryName(int index) {
        return entry.getEntries()[index].getName();
    }
}
