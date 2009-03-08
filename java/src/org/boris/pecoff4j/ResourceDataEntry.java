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

public class ResourceDataEntry
{
    private int offsetToData;
    private int size;
    private int codePage;
    private int reserved;

    public int getOffsetToData() {
        return offsetToData;
    }

    public int getSize() {
        return size;
    }

    public int getCodePage() {
        return codePage;
    }

    public int getReserved() {
        return reserved;
    }

    public void setOffsetToData(int offsetToData) {
        this.offsetToData = offsetToData;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCodePage(int codePage) {
        this.codePage = codePage;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }
}
