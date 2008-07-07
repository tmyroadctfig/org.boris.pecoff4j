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

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Encapsulates the Debug Directory (Image Only). Section 6.1.1 of the PE/COFF
 * spec v8.
 */
public class DebugDirectory
{
    private int characteristics;
    private int timeDateStamp;
    private int majorVersion;
    private int type;
    private int sizeOfData;
    private int addressOfRawData;
    private int pointerToRawData;

    public void read(DataInputStream dis) throws IOException {
        characteristics = dis.readInt();
        timeDateStamp = dis.readInt();
        majorVersion = dis.readUnsignedShort();
        majorVersion = dis.readUnsignedShort();
        type = dis.readInt();
        sizeOfData = dis.readInt();
        addressOfRawData = dis.readInt();
        pointerToRawData = dis.readInt();
    }

    public int getCharacteristics() {
        return characteristics;
    }

    public int getTimeDateStamp() {
        return timeDateStamp;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getType() {
        return type;
    }

    public int getSizeOfData() {
        return sizeOfData;
    }

    public int getAddressOfRawData() {
        return addressOfRawData;
    }

    public int getPointerToRawData() {
        return pointerToRawData;
    }
}
