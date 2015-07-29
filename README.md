# org.boris.pecoff4j
Copy of the Java pecoff4j library: http://pecoff4j.sourceforge.net

##PE/COFF 4J
###Java Engineering Library for Portable Executables

PE/COFF 4J is a java engineering library for portable executables, the format used by Windows. It has the following features:
* Parser for Windows executables and DLLs.
* Assembler for creating and modifying executables and DLLs.
* Resource directory parser - understands version info, icons.

PE/COFF 4J is licensed under the <a href="http://www.eclipse.org/legal/cpl-v10.html">Common Public License (CPL).

###Usage
The following snippet shows how to parse an executable.

    package org.boris.pecoff4j;
    
    import org.boris.pecoff4j.io.PEParser;
    
    /**
     * An example demonstrating parsing an executable.
     */
    public class ParseExecutableTest {
    public static void main(String[] args) throws Exception {
            PE pe = PEParser.parse("c:/windows/system32/notepad.exe");
            System.out.println(pe);
        }
    }

###Other forks
* https://github.com/kichik/pecoff4j
* https://github.com/jonnyzzz/PE