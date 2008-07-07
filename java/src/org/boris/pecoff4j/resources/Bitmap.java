/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.resources;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.util.Reflection;

public class Bitmap
{
    private BitmapFileHeader fileHeader;
    private BitmapInfoHeader infoHeader;
    private byte[] colors;
    private byte[] bitmapBits;

    public static Bitmap read(DataReader dr) throws IOException {
        Bitmap bm = new Bitmap();
        bm.fileHeader = BitmapFileHeader.read(dr);
        bm.infoHeader = BitmapInfoHeader.read(dr);

        return bm;
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public BitmapFileHeader getFileHeader() {
        return fileHeader;
    }

    public BitmapInfoHeader getInfoHeader() {
        return infoHeader;
    }

    public byte[] getColors() {
        return colors;
    }

    public byte[] getBitmapBits() {
        return bitmapBits;
    }
}
