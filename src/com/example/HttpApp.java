package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpApp {
    /**
     * InputStreamからStringへ変換する
     */
    public static String convertStreamToString(InputStream is)
            throws IOException {
        
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(
                        is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    public static void main(String args[]) {
        String url = "http://yahoo.co.jp";
        HttpClient objHttp = new DefaultHttpClient();  // (1)

        HttpGet objGet = new HttpGet(url);  // (2)
        HttpResponse objResponse;
        try {
            objResponse = objHttp.execute(objGet);  // (3)
            if (objResponse.getStatusLine().getStatusCode() == 200) {  //  (4)
                InputStream objStream = objResponse.getEntity()
                        .getContent();  // (5)
                String str = convertStreamToString(objStream); // (6)
                System.out.println("Server: " + str);          //(6)
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
