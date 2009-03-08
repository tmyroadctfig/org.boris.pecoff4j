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

public class ResourceNameDirectory
{
    private ResourceDirectoryEntry entry;
    private ArrayList languages = new ArrayList();

    public void setEntry(ResourceDirectoryEntry entry) {
        this.entry = entry;
    }

    public ResourceDirectoryEntry getEntry() {
        return entry;
    }

    public void add(ResourceLanguageDirectory language) {
        languages.add(language);
    }

    public int size() {
        return languages.size();
    }

    public int getEntryName(int index) {
        return entry.getEntries()[index].getName();
    }

    public ResourceLanguageDirectory getLanguage(int index) {
        return (ResourceLanguageDirectory) languages.get(index);
    }
}
