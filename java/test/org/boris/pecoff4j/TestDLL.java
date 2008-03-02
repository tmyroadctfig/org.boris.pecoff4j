package org.boris.pecoff4j;

import java.io.File;

import org.boris.pecoff4j.header.COFFHeader;
import org.boris.pecoff4j.header.DOSHeader;
import org.boris.pecoff4j.header.DOSStub;
import org.boris.pecoff4j.header.OptionalHeader;
import org.boris.pecoff4j.io.ByteArrayDataReader;
import org.boris.pecoff4j.io.DataReader;

public class TestDLL {

    public static final boolean dump = false;

    public static void main(String[] args) throws Exception {
        testDll("c:/windows/system32/user32.dll");
        /*
         * for (int i = 0; i < 20; i++) { testDll(FoundDLLs.DLL_FILES[i]); }
         */
    }

    public static void testDll(String file) throws Exception {
        DataReader dr = new ByteArrayDataReader(new File(file));
        System.out.println(file);
        DOSHeader header = DOSHeader.read(dr);
        if (dump)
            System.out.println(header);
        DOSStub stub = DOSStub.read(header, dr);
        if (dump)
            System.out.println(stub);
        long pe = dr.readDoubleWord();
        if (dump)
            System.out.println(pe);
        COFFHeader ch = COFFHeader.read(dr);
        if (dump)
            System.out.println(ch);
        OptionalHeader oh = OptionalHeader.read(dr);
        if (dump)
            System.out.println(oh);
        SectionTable st = SectionTable.read(ch.getNumberOfSections(), dr);
        if (dump)
            System.out.println(st);
        if (dump)
            for (int i = 0; i < st.getNumberOfSections(); i++) {
                System.out.println(st.getSection(i));
            }

        ExportDirectoryTable edt = ExportDirectoryTable.read(dr);
        System.out.println(edt);
    }
}
