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

import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.io.IDataWriter;
import org.boris.pecoff4j.util.Reflection;

public class IconImage
{
    private BitmapInfoHeader header;
    private RGBQuad[] colors;
    private byte[] xorMask;
    private byte[] andMask;
    private byte[] pngData;

    public static IconImage readPNG(byte[] data) {
        IconImage ii = new IconImage();
        ii.pngData = data;
        return ii;
    }

    public static IconImage read(IDataReader dr, int bytesInRes)
            throws IOException {
        IconImage ii = new IconImage();
        int quadSize = 0;
        ii.header = BitmapInfoHeader.read(dr);
        if (ii.header.getClrUsed() != 0) {
            quadSize = ii.header.getClrUsed();
        } else {
            if (ii.header.getBitCount() <= 8) {
                quadSize = 1 << ii.header.getBitCount();
            } else {
                quadSize = 0;
            }
        }

        int numBytesPerLine = ((((ii.header.getWidth() * ii.header.getPlanes() * ii.header
                .getBitCount()) + 31) >> 5) << 2);
        int xorSize = numBytesPerLine * ii.header.getHeight() / 2;
        int andSize = bytesInRes - (quadSize * 4) - ii.header.getSize() -
                xorSize;

        if (quadSize > 0) {
            ii.colors = new RGBQuad[quadSize];
            for (int i = 0; i < quadSize; i++) {
                ii.colors[i] = RGBQuad.read(dr);
            }
        }

        ii.xorMask = new byte[xorSize];
        dr.read(ii.xorMask);

        ii.andMask = new byte[andSize];
        dr.read(ii.andMask);

        return ii;
    }

    public void write(IDataWriter dw) throws IOException {
        if (header != null) {
            header.write(dw);
            if (colors != null) {
                for (int i = 0; i < colors.length; i++) {
                    colors[i].write(dw);
                }
            }
            dw.writeBytes(xorMask);
            dw.writeBytes(andMask);
        } else {
            dw.writeBytes(pngData);
        }
    }

    public int sizeOf() {
        return header == null ? pngData.length : header.getSize() +
                (colors == null ? 0 : colors.length * 4) + xorMask.length +
                andMask.length;
    }

    public BitmapInfoHeader getHeader() {
        return header;
    }

    public RGBQuad[] getColors() {
        return colors;
    }

    public byte[] getXorMask() {
        return xorMask;
    }

    public byte[] getAndMask() {
        return andMask;
    }

    public byte[] getPNG() {
        return pngData;
    }

    public String toString() {
        return Reflection.toString(this);
    }
}
