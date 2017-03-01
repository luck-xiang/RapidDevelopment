package com.kxiang.quick.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加解密
 *
 * @author luomin
 */
public final class MD5Util {
    /**
     * MD5大写16未加密
     *
     * @param pwd
     * @return
     */

    public static String MD5ToBig16(String pwd) {
        String md5 = MD5ToBig32(pwd);
        if (md5 != null) {
            return md5.substring(8, 24);
        }
        return null;
    }

    /**
     * MD5小写16位加密
     *
     * @param pwd
     * @return
     */
    public static String MD5ToLittle16(String pwd) {
        String md5 = MD5ToLittle32(pwd);
        if (md5 != null) {
            return md5.substring(8, 24);
        }
        return null;
    }


    /**
     * MD5大写32位加密
     *
     * @param pwd
     * @return
     */
    public static String MD5ToBig32(String pwd) {
        // 用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = pwd.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = md5String[byte0 >>> 4 & 0xf];
                str[k++] = md5String[byte0 & 0xf];
            }

            return new String(str);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MD5小写32位加密
     *
     * @param sourceStr
     * @return
     */

    public static String MD5ToLittle32(String sourceStr) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

}