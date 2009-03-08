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
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

public class IO
{
    public static byte[] toBytes(File f) throws IOException {
        byte[] b = new byte[(int) f.length()];
        FileInputStream fis = new FileInputStream(f);
        fis.read(b);
        return b;
    }

    public static void findFiles(File dir, FilenameFilter filter,
            FindFilesCallback callback) {
        File[] f = dir.listFiles();
        for (File fs : f) {
            if (fs.isDirectory()) {
                findFiles(fs, filter, callback);
            } else if (filter == null || filter.accept(dir, fs.getName())) {
                callback.fileFound(fs);
            }
        }
    }
}
