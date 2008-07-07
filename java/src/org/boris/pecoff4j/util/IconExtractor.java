/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.boris.pecoff4j.GroupIconDirectory;
import org.boris.pecoff4j.GroupIconDirectoryEntry;
import org.boris.pecoff4j.PEFile;
import org.boris.pecoff4j.ResourceDirectory;
import org.boris.pecoff4j.ResourceEntry;
import org.boris.pecoff4j.io.ByteArrayDataReader;
import org.boris.pecoff4j.io.DataWriter;
import org.boris.pecoff4j.resources.IconDirectory;
import org.boris.pecoff4j.resources.IconDirectoryEntry;
import org.boris.pecoff4j.resources.IconFile;
import org.boris.pecoff4j.resources.IconImage;
import org.boris.pecoff4j.resources.ResourceType;

public class IconExtractor {
    public static void extract(File pecoff, File outputDir) throws IOException {
        PEFile pe = PEFile.parse(pecoff);
        ResourceDirectory rd = pe.getResourceDirectory();
        if(rd == null) return;
        ResourceEntry[] entries = rd.findResources(ResourceType.RT_GROUP_ICON);
        for (int i = 0; i < entries.length; i++) {
            GroupIconDirectory gid = GroupIconDirectory.read(entries[i].getData());
            IconFile icf = new IconFile();
            IconDirectory icd = new IconDirectory();
            icd.setCount(gid.getCount());
            icd.setType(1);
            icd.setReserved(0);
            icf.setDirectory(icd);
            IconDirectoryEntry[] dentries = new IconDirectoryEntry[gid.getCount()];
            IconImage[] images = new IconImage[gid.getCount()];
            icd.setEntries(dentries);
            icf.setImages(images);

            for (int j = 0; j < gid.getCount(); j++) {
                GroupIconDirectoryEntry gide = gid.getEntry(j);
                dentries[j] = new IconDirectoryEntry();
                dentries[j].copyFrom(gide);
                ResourceEntry[] icos = rd.findResources(ResourceType.RT_ICON, gide.getId());
                if (icos == null || icos.length != 1) {
                    throw new IOException("Unexpected icons in resource file");
                }
                byte[] d = icos[0].getData();
                dentries[j].setBytesInRes(d.length);
                // Check for PNG data
                if(gide.getWidth() == 0 && gide.getHeight() == 0) {
                    IconImage ii = IconImage.readPNG(d);
                    images[j] = ii;
                } else {
                    IconImage ii = IconImage.read(new ByteArrayDataReader(d), gide.getBytesInRes());
                    images[j] = ii;
                }
            }

            File outFile = new File(outputDir, pecoff.getName() + "-icon" + i + ".ico");
            DataWriter dw = new DataWriter(new FileOutputStream(outFile));
            icf.write(dw);
            dw.close();
        }
    }
}
