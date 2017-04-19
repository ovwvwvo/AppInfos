package com.ovwvwvo.appinfos.util;

import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Copyright ©2017 by rawer
 */

public class PackageUtil {

    /**
     * 获取签名的MD5摘要
     */
    public static String getSignatureDigest(PackageInfo pkgInfo) {
        int length = pkgInfo.signatures.length;
        if (length <= 0) {
            return "";
        }

        Signature signature = pkgInfo.signatures[0];
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Should not occur
        }
        byte[] digest = md5.digest(signature.toByteArray()); // get digest with md5 algorithm
        return toHexString(digest);
    }

    public static String getHashKey(PackageInfo info) {
        String hashKey = "";
        for (Signature signature : info.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hashKey = Base64.encodeToString(md.digest(), Base64.DEFAULT);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return hashKey;
    }

    public static String getSHA1(PackageInfo packageInfo) {
        String hexString = "";
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        InputStream input = new ByteArrayInputStream(cert);
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate c = (X509Certificate) cf.generateCertificate(input);
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(c.getEncoded());
            hexString = byte2HexFormatted(publicKey);
        } catch (CertificateException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }

    /**
     * 将字节数组转化为对应的十六进制字符串
     */
    private static String toHexString(byte[] rawByteArray) {
        char[] chars = new char[rawByteArray.length * 2];
        for (int i = 0; i < rawByteArray.length; ++i) {
            byte b = rawByteArray[i];
            chars[i * 2] = HEX_CHAR[(b >>> 4 & 0x0F)];
            chars[i * 2 + 1] = HEX_CHAR[(b & 0x0F)];
        }
        return new String(chars);
    }

    private static final char[] HEX_CHAR = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };
}
