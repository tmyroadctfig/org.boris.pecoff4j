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

public class ResourceDataEntry
{
    private int offsetToData;
    private int size;
    private int codePage;
    private int reserved;

    public static ResourceDataEntry read(IDataReader dr) throws IOException {
        ResourceDataEntry rde = new ResourceDataEntry();
        rde.offsetToData = dr.readDoubleWord();
        rde.size = dr.readDoubleWord();
        rde.codePage = dr.readDoubleWord();
        rde.reserved = dr.readDoubleWord();
        return rde;
    }

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

    public String toString() {
        return Reflection.toString(this);
    }
}
