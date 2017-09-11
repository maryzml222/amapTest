package com.example.mylibrary.component.codec;

public class Convert {
    public Convert() {
    }

    public static String bcd2Str(byte[] b) {
        char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder sb = new StringBuilder(b.length * 2);

        for(int i = 0; i < b.length; ++i) {
            sb.append(HEX_DIGITS[(b[i] & 240) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 15]);
        }

        return sb.toString();
    }

    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if(mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        byte[] abt = new byte[len];
        if(len >= 2) {
            len /= 2;
        }

        byte[] bbt = new byte[len];
        abt = asc.getBytes();

        for(int p = 0; p < asc.length() / 2; ++p) {
            int j;
            if(abt[2 * p] >= 97 && abt[2 * p] <= 122) {
                j = abt[2 * p] - 97 + 10;
            } else if(abt[2 * p] >= 65 && abt[2 * p] <= 90) {
                j = abt[2 * p] - 65 + 10;
            } else {
                j = abt[2 * p] - 48;
            }

            int k;
            if(abt[2 * p + 1] >= 97 && abt[2 * p + 1] <= 122) {
                k = abt[2 * p + 1] - 97 + 10;
            } else if(abt[2 * p + 1] >= 65 && abt[2 * p + 1] <= 90) {
                k = abt[2 * p + 1] - 65 + 10;
            } else {
                k = abt[2 * p + 1] - 48;
            }

            int a = (j << 4) + k;
            byte b = (byte)a;
            bbt[p] = b;
        }

        return bbt;
    }

    public static void int2ByteArray(int i, byte[] to, int offset) {
        to[offset] = (byte)(i >>> 24 & 255);
        to[offset + 1] = (byte)(i >>> 16 & 255);
        to[offset + 2] = (byte)(i >>> 8 & 255);
        to[offset + 3] = (byte)(i & 255);
    }

    public static void int2ByteArrayLittleEndian(int i, byte[] to, int offset) {
        to[offset] = (byte)(i & 255);
        to[offset + 1] = (byte)(i >>> 8 & 255);
        to[offset + 2] = (byte)(i >>> 16 & 255);
        to[offset + 3] = (byte)(i >>> 24 & 255);
    }

    public static void short2ByteArray(short s, byte[] to, int offset) {
        to[offset] = (byte)(s >>> 8 & 255);
        to[offset + 1] = (byte)(s & 255);
    }

    public static void short2ByteArrayLittleEndian(short s, byte[] to, int offset) {
        to[offset] = (byte)(s & 255);
        to[offset + 1] = (byte)(s >>> 8 & 255);
    }

    public static int byteArray2Int(byte[] from, int offset) {
        return from[offset] << 24 & -16777216 | from[offset + 1] << 16 & 16711680 | from[offset + 2] << 8 & '\uff00' | from[offset + 3] & 255;
    }

    public static int byteArray2IntLittleEndian(byte[] from, int offset) {
        return from[offset + 3] << 24 & -16777216 | from[offset + 2] << 16 & 16711680 | from[offset + 1] << 8 & '\uff00' | from[offset] & 255;
    }

    public static short byteArray2Short(byte[] from, int offset) {
        return (short)(from[offset] << 8 & '\uff00' | from[offset + 1] & 255);
    }

    public static short byteArray2ShortLittleEndian(byte[] from, int offset) {
        return (short)(from[offset + 1] << 8 & '\uff00' | from[offset] & 255);
    }
}