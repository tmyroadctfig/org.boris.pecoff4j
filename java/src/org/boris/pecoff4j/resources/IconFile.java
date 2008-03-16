package org.boris.pecoff4j.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.io.IDataWriter;
import org.boris.pecoff4j.util.Reflection;

public class IconFile {
    private IconDirectory directory;
    private IconImage[] images;

    public static IconFile parse(File file) throws IOException {
        return read(new DataReader(new FileInputStream(file)));
    }

    public static IconFile read(IDataReader dr) throws IOException {
        IconFile ic = new IconFile();
        ic.directory = IconDirectory.read(dr);
        ic.images = new IconImage[ic.directory.getCount()];
        for (int i = 0; i < ic.directory.getCount(); i++) {
            dr.jumpTo(ic.directory.getEntry(i).getOffset());
            ic.images[i] = IconImage.read(dr, ic.directory.getEntry(i).getBytesInRes());
        }
        return ic;
    }

    public void write(IDataWriter dw) throws IOException {
        int offset = directory.sizeOf();
        for (int i = 0; i < images.length; i++) {
            directory.getEntry(i).setOffset(offset);
            offset += images[i].sizeOf();
        }
        directory.write(dw);
        for (int i = 0; i < images.length; i++) {
            images[i].write(dw);
        }
    }

    public String toString() {
        return Reflection.toString(this);
    }

    public IconDirectory getDirectory() {
        return directory;
    }

    public void setDirectory(IconDirectory directory) {
        this.directory = directory;
    }

    public IconImage[] getImages() {
        return images;
    }

    public void setImages(IconImage[] images) {
        this.images = images;
    }
}
