package org.boris.pecoff4j.util;

public class HexDump {
    private static final int WIDTH = 20;

    public static void dump(byte[] data) {
        int numRows = data.length / WIDTH;
        for (int i = 0; i < numRows; i++) {
            dumpRow(data, i * WIDTH, WIDTH);
        }
        int leftover = data.length % WIDTH;
        if (leftover > 0) {
            dumpRow(data, data.length - leftover, leftover);
        }
    }

    private static void dumpRow(byte[] data, int start, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String s = Integer.toHexString(data[start + i] & 0x00ff);
            if (s.length() == 1) {
                sb.append("0");
            }
            sb.append(s);
            sb.append(" ");
        }
        if (length < WIDTH) {
            for (int i = 0; i < WIDTH - length; i++) {
                sb.append("   ");
            }
        }
        for (int i = 0; i < length; i++) {
            byte b = data[start + i];
            if (Character.isLetterOrDigit(b)) {
                sb.append(String.valueOf((char) b));
            } else {
                sb.append(".");
            }
        }
        System.out.println(sb.toString());
    }
}
