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

public class ResourceEntry
{
    private byte[] data;
    private int name;
    private int language;

    public ResourceEntry(byte[] data, int name, int language) {
        this.data = data;
        this.name = name;
        this.language = language;
    }

    public byte[] getData() {
        return data;
    }

    public int getName() {
        return name;
    }

    public int getLanguage() {
        return language;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setName(int name) {
        this.name = name;
    }

    public void setLanguage(int language) {
        this.language = language;
    }
}
