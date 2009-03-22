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

public class ResourcePointer
{
    private int name;
    private int offsetToData;
    private boolean isDirectory;

    public int getName() {
        return name;
    }

    public int getOffsetToData() {
        return offsetToData;
    }

    public void setName(int name) {
        this.name = name;
    }

    public void setOffsetToData(int offsetToData) {
        this.offsetToData = offsetToData;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }
}
