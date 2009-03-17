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

import java.io.File;

import org.boris.pecoff4j.io.PEAssembler;
import org.boris.pecoff4j.io.PEParser;
import org.boris.pecoff4j.util.Diff;
import org.boris.pecoff4j.util.IO;

public class TestParseAssemble
{
    public static void main(String[] args) throws Exception {
        File[] files = TestParseDLLs.findPEs();
        for (int i = 0; i < files.length; i++) {
            // System.out.println(files[i]);
            byte[] b1 = IO.toBytes(files[i]);
            PE pe = PEParser.parse(files[i]);
            byte[] b2 = PEAssembler.toBytes(pe);
            // Diff.findDiff(b1, b2);
            if (!Diff.equals(b1, b2))
                System.out.println(files[i]);
        }
    }
}
