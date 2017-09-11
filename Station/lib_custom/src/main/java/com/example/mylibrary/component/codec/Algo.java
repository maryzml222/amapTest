package com.example.mylibrary.component.codec;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Algo {
    private static char[] base64EncodeChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static byte[] base64DecodeChars = new byte[]{(byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)62, (byte)-1, (byte)-1, (byte)-1, (byte)63, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)58, (byte)59, (byte)60, (byte)61, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8, (byte)9, (byte)10, (byte)11, (byte)12, (byte)13, (byte)14, (byte)15, (byte)16, (byte)17, (byte)18, (byte)19, (byte)20, (byte)21, (byte)22, (byte)23, (byte)24, (byte)25, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)26, (byte)27, (byte)28, (byte)29, (byte)30, (byte)31, (byte)32, (byte)33, (byte)34, (byte)35, (byte)36, (byte)37, (byte)38, (byte)39, (byte)40, (byte)41, (byte)42, (byte)43, (byte)44, (byte)45, (byte)46, (byte)47, (byte)48, (byte)49, (byte)50, (byte)51, (byte)-1, (byte)-1, (byte)-1, (byte)-1, (byte)-1};

    public Algo() {
    }

    public static String md5(byte[] data) {
        if(data != null) {
            try {
                MessageDigest e = MessageDigest.getInstance("MD5");
                byte[] result = e.digest(data);
                return Convert.bcd2Str(result);
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }

        return null;
    }

    public static String base64Encode(byte[] bytes) throws Exception {
        if(bytes != null && bytes.length != 0) {
            StringBuffer sb = new StringBuffer();
            int len = bytes.length;
            int i = 0;

            while(i < len) {
                int b1 = bytes[i++] & 255;
                if(i == len) {
                    sb.append(base64EncodeChars[b1 >>> 2]);
                    sb.append(base64EncodeChars[(b1 & 3) << 4]);
                    sb.append("==");
                    break;
                }

                int b2 = bytes[i++] & 255;
                if(i == len) {
                    sb.append(base64EncodeChars[b1 >>> 2]);
                    sb.append(base64EncodeChars[(b1 & 3) << 4 | (b2 & 240) >>> 4]);
                    sb.append(base64EncodeChars[(b2 & 15) << 2]);
                    sb.append("=");
                    break;
                }

                int b3 = bytes[i++] & 255;
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 3) << 4 | (b2 & 240) >>> 4]);
                sb.append(base64EncodeChars[(b2 & 15) << 2 | (b3 & 192) >>> 6]);
                sb.append(base64EncodeChars[b3 & 63]);
            }

            return sb.toString();
        } else {
            return new String();
        }
    }

    public static byte[] base64Decode(String base64) throws Exception {
        if(base64 == null) {
            return new byte[0];
        } else {
            StringBuffer sb = new StringBuffer();
            byte[] data = base64.getBytes("US-ASCII");
            int len = data.length;
            if(len % 4 != 0) {
                return new byte[0];
            } else {
                int i = 0;

                while(i < len) {
                    byte b1;
                    do {
                        b1 = base64DecodeChars[data[i++]];
                    } while(i < len && b1 == -1);

                    if(b1 == -1) {
                        break;
                    }

                    byte b2;
                    do {
                        b2 = base64DecodeChars[data[i++]];
                    } while(i < len && b2 == -1);

                    if(b2 == -1) {
                        break;
                    }

                    sb.append((char)(b1 << 2 | (b2 & 48) >>> 4));

                    byte b3;
                    do {
                        b3 = data[i++];
                        if(b3 == 61) {
                            return sb.toString().getBytes("ISO-8859-1");
                        }

                        b3 = base64DecodeChars[b3];
                    } while(i < len && b3 == -1);

                    if(b3 == -1) {
                        break;
                    }

                    sb.append((char)((b2 & 15) << 4 | (b3 & 60) >>> 2));

                    byte b4;
                    do {
                        b4 = data[i++];
                        if(b4 == 61) {
                            return sb.toString().getBytes("ISO-8859-1");
                        }

                        b4 = base64DecodeChars[b4];
                    } while(i < len && b4 == -1);

                    if(b4 == -1) {
                        break;
                    }

                    sb.append((char)((b3 & 3) << 6 | b4));
                }

                return sb.toString().getBytes("ISO-8859-1");
            }
        }
    }

    public static byte[] des(byte[] key, byte[] data, int cipherMode) {
        try {
            SecureRandom e = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(cipherMode, securekey, e);
            return cipher.doFinal(data);
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public static byte[] tdes(byte[] key, byte[] data, int cipherMode) {
        String Algorithm = "DESede";

        try {
            SecretKeySpec e = new SecretKeySpec(key, "DESede");
            Cipher cipher = Cipher.getInstance("desede/ECB/NoPadding");
            cipher.init(cipherMode, e);
            return cipher.doFinal(data);
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }
}