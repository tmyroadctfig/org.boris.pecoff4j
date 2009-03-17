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

public class TestPAProblems
{
    static String P1 = "C:\\windows\\system32\\makecab.exe";

    public static void main(String[] args) throws Exception {
        test(new File(P1));
    }

    public static void test(File f) throws Exception {
        System.out.println(f);
        byte[] b1 = IO.toBytes(f);
        PE pe = PEParser.parse(f);
        byte[] b2 = PEAssembler.toBytes(pe);
        Diff.findDiff(b1, b2);
    }
}
