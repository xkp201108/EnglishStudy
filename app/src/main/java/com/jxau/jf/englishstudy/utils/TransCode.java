package com.jxau.jf.englishstudy.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TransCode {
    // 转码
    public String convertCodeAndGetText(String filePath) {
        BufferedReader reader = null;
        String text = "";
        FileInputStream fis = null;
        BufferedInputStream in = null;
        InputStreamReader isr = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            in = new BufferedInputStream(fis);
            in.mark(4);
            byte[] first3bytes = new byte[3];
            // 找到文档的前三个字节并自动判断文档编码类型
            in.read(first3bytes);
            in.reset();
            if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
                    && first3bytes[2] == (byte) 0xBF) {
                isr = new InputStreamReader(in, "utf-8");
                reader = new BufferedReader(isr);
            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFE) {
                isr = new InputStreamReader(in, "unicode");
                reader = new BufferedReader(isr);
            } else if (first3bytes[0] == (byte) 0xFE
                    && first3bytes[1] == (byte) 0xFF) {
                isr = new InputStreamReader(in, "utf-16be");
                reader = new BufferedReader(isr);
            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFF) {
                isr = new InputStreamReader(in, "utf-16le");
                reader = new BufferedReader(isr);
            } else {
                isr = new InputStreamReader(in, "GBK");
                reader = new BufferedReader(isr);
            }
            String str = reader.readLine();
            while (str != null) {
                text = text + str + "\n";
                str = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
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
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return text;
    }

}
