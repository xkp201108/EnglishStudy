package com.jxau.jf.englishstudy.thread;

import com.jxau.jf.englishstudy.utils.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TxtHttpThread extends Thread {
    //传入文件地址
    private String txtUrl;


    //传出TXT文本内容
    private String txtContent;

    public TxtHttpThread(String txtUrl){
       this .txtUrl  = txtUrl;
    }
    @Override
    public void run() {
        String urlStr = HttpUtils.URL + HttpUtils.READ + txtUrl;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60 * 1000);
            conn.setReadTimeout(60 * 1000);
            // 取得inputStream，并进行读取
            InputStream input = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input,"utf-8"));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            setTxtContent(sb.toString());
            System.out.print(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setTxtUrl(String txtUrl) {
        this.txtUrl = txtUrl;
    }

    public String getTxtUrl() {
        return txtUrl;
    }


    public void setTxtContent(String txtContent) {
        this.txtContent = txtContent;
    }

    public String getTxtContent() {
        return txtContent;
    }

}
