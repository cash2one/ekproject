package qtone.xxt.video;

/* XXTEA.java
 *
 * Author:       Ma Bingyao <andot@ujn.edu.cn>
 * Copyright:    CoolCode.CN
 * Version:      1.6
 * LastModified: 2006-08-09
 * This library is free.  You can redistribute it and/or modify it.
 * http://www.coolcode.cn/?p=169
 */

//package org.phprpc.util;


public class XXTEA {
    private XXTEA() {}

    /** 
     * @param args 
     */ 
    public static void main(String[] args) { 
        String s = "http://192.168.101.38:8080/vod/course/cp2/cs10072/preview/10072.flv"; 
		String a = byte2hex(s.getBytes());
		byte[] b = hex2byte(a.getBytes());
        System.out.println("直接byte2hex: "+a);
        System.out.println("直接byte2hex: "+new String(b));
        System.out.println("直接byte2hex: "+s);
//        byte[] k = "ZJXXTEncry201112".getBytes(); 
//        byte[] e = encrypt(s.getBytes(), k);
//        byte[] d = decrypt(e, k); 
//		
//		a = byte2hex(e);
//		b = hex2byte(a.getBytes());
//		
//		System.out.println("encrypt: "+new String(e));
//		System.out.println("decrypt: "+new String(d));
//		
//		System.out.println("byte2hex: "+a);
//		System.out.println("hex2byte: "+new String(b));
		
		
        
    } 

    /**
     * Encrypt data with key.
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        if (data.length == 0) {
            return data;
        }
        return toByteArray(
                encrypt(toIntArray(data, true), toIntArray(key, false)), false);
    }

    /**
     * Decrypt data with key.
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        if (data.length == 0) {
            return data;
        }
        return toByteArray(
                decrypt(toIntArray(data, false), toIntArray(key, false)), true);
    }

    /**
     * Encrypt data with key.
     * 
     * @param v
     * @param k
     * @return
     */
    public static int[] encrypt(int[] v, int[] k) {
        int n = v.length - 1;

        if (n < 1) {
            return v;
        }
        if (k.length < 4) {
            int[] key = new int[4];

            System.arraycopy(k, 0, key, 0, k.length);
            k = key;
        }
        int z = v[n], y = v[0], delta = 0x9E3779B9, sum = 0, e;
        int p, q = 6 + 52 / (n + 1);

        while (q-- > 0) {
            sum = sum + delta;
            e = sum >>> 2 & 3;
            for (p = 0; p < n; p++) {
                y = v[p + 1];
                z = v[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4)
                        ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
            }
            y = v[0];
            z = v[n] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4)
                    ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
        }
        return v;
    }

    /**
     * Decrypt data with key.
     * 
     * @param v
     * @param k
     * @return
     */
    public static int[] decrypt(int[] v, int[] k) {
        int n = v.length - 1;

        if (n < 1) {
            return v;
        }
        if (k.length < 4) {
            int[] key = new int[4];

            System.arraycopy(k, 0, key, 0, k.length);
            k = key;
        }
        int z = v[n], y = v[0], delta = 0x9E3779B9, sum, e;
        int p, q = 6 + 52 / (n + 1);

        sum = q * delta;
        while (sum != 0) {
            e = sum >>> 2 & 3;
            for (p = n; p > 0; p--) {
                z = v[p - 1];
                y = v[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4)
                        ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
            }
            z = v[n];
            y = v[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4)
                    ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
            sum = sum - delta;
        }
        return v;
    }

    /**
     * Convert byte array to int array.
     * 
     * @param data
     * @param includeLength
     * @return
     */
    private static int[] toIntArray(byte[] data, boolean includeLength) {
        int n = (((data.length & 3) == 0)
                ? (data.length >>> 2)
                : ((data.length >>> 2) + 1));
        int[] result;

        if (includeLength) {
            result = new int[n + 1];
            result[n] = data.length;
        } else {
            result = new int[n];
        }
        n = data.length;
        for (int i = 0; i < n; i++) {
            result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
        }
        return result;
    }

    /**
     * Convert int array to byte array.
     * 
     * @param data
     * @param includeLength
     * @return
     */
    private static byte[] toByteArray(int[] data, boolean includeLength) {
        int n = data.length << 2;

        ;
        if (includeLength) {
            int m = data[data.length - 1];

            if (m > n) {
                return null;
            } else {
                n = m;
            }
        }
        byte[] result = new byte[n];

        for (int i = 0; i < n; i++) {
            result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
        }
        return result;
    }
    
    protected static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException("des conver error!");
		}

		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) (Integer.parseInt(item, 16) ^ 2);
		}
		return b2;

	}

	// 字节数组转换成十六进制字符串
	protected static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (byte bt : b) {
			stmp = (Integer.toHexString(bt & 0XFF ^ 2));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;

			}
		}
		return hs.toUpperCase();
	}
	
}