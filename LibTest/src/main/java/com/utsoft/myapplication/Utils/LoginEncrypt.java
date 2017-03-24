package com.utsoft.myapplication.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangzhu on 2017/3/8.
 * func:
 */

public class LoginEncrypt {
    public LoginEncrypt() {
    }

    public static String Enc(String plainText) {
        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(plainText.getBytes());
            byte[] b = e.digest();
            StringBuffer buf = new StringBuffer("");

            for (int offset = 0; offset < b.length; ++offset) {
                int i = b[offset];
                if (i < 0) {
                    i += 256;
                }

                if (i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
            return null;
        }
    }

}
