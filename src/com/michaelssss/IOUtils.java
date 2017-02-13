package com.michaelssss;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by apple on 2017/1/21.
 */
public class IOUtils {
    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            //do Nothing
        }
    }

    public static void copy(InputStream in, OutputStream op) throws IOException {
        copy(in, op, 1024);
    }

    private static void copy(InputStream in, OutputStream op, int length) throws IOException {
        byte[] bytes = new byte[length];
        int readLength;
        while ((readLength = in.read(bytes)) != -1) {
            op.write(bytes, 0, readLength);
        }
    }
}
