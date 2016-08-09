package com.movitech.mobilesafe.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhangjingjing on 8/6/16.
 */
public class StreamUtil {

    /*

     */
    public static String readFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];

        while ((len = is.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }

        String result = out.toString();
        is.close();
        out.close();

        return result;
    }
}
