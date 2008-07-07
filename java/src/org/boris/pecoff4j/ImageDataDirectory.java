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

public class ImageDataDirectory
{
    private int virtualAddress;
    private int size;

    public static ImageDataDirectory read(IDataReader dr) throws IOException {
        ImageDataDirectory idd = new ImageDataDirectory();
        idd.readFrom(dr);
        return idd;
    }

    private void readFrom(IDataReader dr) throws IOException {
        virtualAddress = dr.readDoubleWord();
        size = dr.readDoubleWord();
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public int getVirtualAddress() {
        return virtualAddress;
    }

    public int getSize() {
        return size;
    }
}
