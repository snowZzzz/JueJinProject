package com.example.goldlibrary.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    private static final String ALGORITHM = "MD5";
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 得到文件的MD5
     *
     * @param filePath
     * @return
     */
    public static String getFileMD5String(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        }
        return getFileMD5String(file);
    }

    /**
     * 得到文件的MD5
     *
     * @param file
     * @return
     */
    public static String getFileMD5String(File file) {
        FileInputStream in = null;
        FileChannel ch = null;
        try {
            in = new FileInputStream(file);
            ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY,
                    0, file.length());
            MessageDigest messagedigest = null;
            messagedigest = MessageDigest.getInstance(ALGORITHM);
            messagedigest.update(byteBuffer);

            return bufferToHex(messagedigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ch != null) {
                try {
                    ch.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getName();
    }

    /**
     * 得到字符串的MD5
     *
     * @param s
     * @return
     */
    public static String getMD5String(String s) {
        try {
            return getMD5String(s.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 得到byte数组的MD5
     *
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5String(byte[] bytes)
            throws NoSuchAlgorithmException {
        MessageDigest messagedigest = MessageDigest.getInstance(ALGORITHM);
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}