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

public class ResourcePointer
{
    private int name;
    private int offsetToData;

    public static ResourcePointer read(IDataReader dr) throws IOException {
        ResourcePointer rp = new ResourcePointer();
        rp.name = dr.readDoubleWord();
        rp.offsetToData = dr.readDoubleWord() & 0x7fffffff; // high bit
                                                            // indicates a
                                                            // directory
        return rp;
    }

    public int getName() {
        return name;
    }

    public int getOffsetToData() {
        return offsetToData;
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
