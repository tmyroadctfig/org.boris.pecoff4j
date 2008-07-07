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

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.util.Reflection;

public class ResourceNameDirectory
{
    private ResourceDirectoryEntry entry;
    private ResourceLanguageDirectory[] languages;

    public static ResourceNameDirectory read(IDataReader dr, int baseAddress)
            throws IOException {
        ResourceNameDirectory rn = new ResourceNameDirectory();
        rn.entry = ResourceDirectoryEntry.read(dr);
        ResourcePointer[] pointers = rn.entry.getEntries();
        rn.languages = new ResourceLanguageDirectory[pointers.length];
        for (int i = 0; i < pointers.length; i++) {
            dr.jumpTo(pointers[i].getOffsetToData());
            rn.languages[i] = ResourceLanguageDirectory.read(dr, baseAddress);
        }
        return rn;
    }

    public int getLanguageCount() {
        return languages.length;
    }

    public int getEntryName(int index) {
        return entry.getEntries()[index].getName();
    }

    public ResourceLanguageDirectory getLanguage(int index) {
        return languages[index];
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
