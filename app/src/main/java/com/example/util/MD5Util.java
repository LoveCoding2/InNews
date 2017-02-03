package com.example.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * ����MD5���ܽ���
 *
 * @author tfq
 * @datetime 2011-10-13
 */
public class MD5Util {

    private static Key initKeyForAES(String key)
            throws NoSuchAlgorithmException {
        if (null == key || key.length() == 0) {
            throw new NullPointerException("key not is null");
        }
        SecretKeySpec key2 = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key2 = new SecretKeySpec(enCodeFormat, "AES");
        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException();
        }
        return key2;

    }

    public static String encryptAES(String content, String key) {
        try {
            SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(key);
            Cipher cipher = Cipher.getInstance("AES");// ����������
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);// ��ʼ��
            byte[] result = cipher.doFinal(byteContent);
            return asHex(result); // ����
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptAES(String content, String key) {
        try {
            SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(key);
            Cipher cipher = Cipher.getInstance("AES");// ����������
            cipher.init(Cipher.DECRYPT_MODE, secretKey);// ��ʼ��
            byte[] result = cipher.doFinal(asBytes(content));
            return new String(result); // ����
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String asHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if ((buf[i] & 0xff) < 0x10)
                strbuf.append("0");
            strbuf.append(Long.toString(buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    public static byte[] asBytes(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public final static String MD5(String s)
    {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);//通过update处理数据
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptMD5(String inStr)
    {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++)
        {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }


    public static void main(String[] args) {
         String a=MD5Util.MD5("F2");
        System.out.print(a);
    }
}