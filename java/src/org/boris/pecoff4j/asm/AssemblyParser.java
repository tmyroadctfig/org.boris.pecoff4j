/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.asm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.boris.pecoff4j.util.Reflection;

public class AssemblyParser
{
    public static AbstractInstruction[] parseAll(int offset, InputStream is) throws IOException {
        List<AbstractInstruction> instructions = new ArrayList();
        AbstractInstruction ins = null;
        while ((ins = parse(is)) != null) {
            ins.setOffset(offset);
            offset += ins.size();
            instructions.add(ins);
        }
        return instructions.toArray(new AbstractInstruction[instructions.size()]);
    }

    public static AbstractInstruction parse(InputStream is) throws IOException {
        int opcode = is.read() & 0xff;
        int highop = opcode & 0xf0;
        int imm32;
        int disp32;
        ModRM modrm = null;
        switch (highop) {
        case 0x0:
            switch (opcode) {
            case 0x0f:
                modrm = new ModRM(is.read());
                imm32 = readDoubleWord(is);
                return new JNZ(modrm, imm32);
            }
        case 0x50:
            if (opcode < 0x58) {
                return new PUSH(opcode & 0xf);
            } else {
                return new POP(opcode & 0xf);
            }
        case 0x80:
            modrm = new ModRM(is.read());
            switch (opcode) {
            case 0x8b:
                switch (modrm.mod) {
                case 1:
                    imm32 = is.read();
                    return new MOV(modrm, (byte) imm32);
                }
                return new MOV(modrm);
            case 0x81:
                imm32 = readDoubleWord(is);
                return new SUB(modrm, imm32);
            case 0x85:
                return new TEST(modrm);
            case 0x8d:
                imm32 = readDoubleWord(is);
                return new LEA(modrm, imm32);
            }
            print(modrm);
        case 0xc0:
            switch (opcode) {
            case 0xc7:
                modrm = new ModRM(is.read());
                disp32 = readDoubleWord(is);
                imm32 = readDoubleWord(is);
                return new MOV(modrm, disp32, imm32);
            }
        case 0xf0:
            switch (opcode) {
            case 0xff:
                modrm = new ModRM(is.read());
                imm32 = readDoubleWord(is);
                return new CALL(modrm, imm32);
            }
        }
        println(opcode);
        return null;
    }

    public static int readDoubleWord(InputStream is) throws IOException {
        return is.read() | is.read() << 8 | is.read() << 16 | is.read() << 24;
    }

    public static void print(Object o) {
        System.out.print(Reflection.toString(o));
    }

    public static void println(Object o) {
        System.out.println(Reflection.toString(o));
    }
}
