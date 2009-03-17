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
import java.io.IOException;
import java.util.Arrays;

public class Diff
{
    public static boolean equals(File f1, File f2) throws IOException {
        return equals(IO.toBytes(f1), IO.toBytes(f2));
    }

    public static boolean equals(byte[] b1, byte[] b2) {
        return Arrays.equals(b1, b2);
    }

    public static void findDiff(byte[] b1, byte[] b2) {
        if (b1.length != b2.length)
            System.out.println("Different lengths: " + b1.length + ", " +
                    b2.length);
        for (int i = 0; i < b1.length && i < b2.length; i++) {
            if (b1[i] != b2[i]) {
                int p = i - 30;
                if (p < 0)
                    p = 0;
                System.out.println("Diff at " + i);
                HexDump.dump(b1, p, 100);
                System.out.println("-----");
                HexDump.dump(b2, p, 100);
                break;
            }
        }
    }
}
