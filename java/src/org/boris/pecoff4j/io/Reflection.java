package org.boris.pecoff4j.io;

import java.lang.reflect.Field;

public class Reflection {

    public static String toString(Object o) {
        StringBuilder sb = new StringBuilder();
        Field[] f = o.getClass().getDeclaredFields();
        for (int i = 0; i < f.length; i++) {
            f[i].setAccessible(true);
            sb.append(f[i].getName());
            sb.append(": ");
            try {
                sb.append(f[i].get(o));
            } catch (Exception e) {
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
