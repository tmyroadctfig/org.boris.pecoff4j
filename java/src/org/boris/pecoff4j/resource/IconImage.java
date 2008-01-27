package org.boris.pecoff4j.resource;

import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;

public class IconImage {

    private BitmapInfoHeader icHeader;

    public static IconImage read(DataReader dr) throws IOException {
        IconImage ii = new IconImage();
        ii.icHeader = BitmapInfoHeader.read(dr);

        return ii;
    }
}
