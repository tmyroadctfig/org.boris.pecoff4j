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


public class ResourceDirectoryEntry
{
    private int characteristics;
    private int timeDateStamp;
    private int majorVersion;
    private int minorVersion;
    private int numNamedEntries;
    private int numIdEntries;
    private ResourcePointer[] entries;

    public int getCharacteristics() {
        return characteristics;
    }

    public int getTimeDateStamp() {
        return timeDateStamp;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getNumNamedEntries() {
        return numNamedEntries;
    }

    public int getNumIdEntries() {
        return numIdEntries;
    }

    public ResourcePointer[] getEntries() {
        return entries;
    }

    public void setCharacteristics(int characteristics) {
        this.characteristics = characteristics;
    }

    public void setTimeDateStamp(int timeDateStamp) {
        this.timeDateStamp = timeDateStamp;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    public void setNumNamedEntries(int numNamedEntries) {
        this.numNamedEntries = numNamedEntries;
    }

    public void setNumIdEntries(int numIdEntries) {
        this.numIdEntries = numIdEntries;
    }

    public void setEntries(ResourcePointer[] entries) {
        this.entries = entries;
    }
}
