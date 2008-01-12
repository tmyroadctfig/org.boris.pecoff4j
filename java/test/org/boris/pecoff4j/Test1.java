package org.boris.pecoff4j;

import java.io.FileInputStream;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.DataReaderImpl;

public class Test1 {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            testExe(FindExe.FILES[i]);
        }
    }

    public static void testExe(String file) throws Exception {
        DataReader dr = new DataReaderImpl(new FileInputStream(file));
        System.out.println(file);
        DOSHeader header = DOSHeader.read(dr);
        System.out.println(header);
        DOSStub stub = DOSStub.read(header, dr);
        System.out.println(stub);
        PESignature pe = PESignature.read(dr);
        System.out.println(pe);
        COFFHeader ch = COFFHeader.read(dr);
        System.out.println(ch);
        OptionalHeader oh = OptionalHeader.read(dr);
        System.out.println(oh);
        SectionTable st = SectionTable.read(ch.getNumberOfSections(), dr);
        System.out.println(st);
    }
}
