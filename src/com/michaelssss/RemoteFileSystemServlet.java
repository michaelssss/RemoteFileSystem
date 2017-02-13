package com.michaelssss;

import com.michaelssss.database.DatabaseController;
import com.michaelssss.database.UserProfileDatabaseController;
import com.michaelssss.storage.LocalStorage;
import com.michaelssss.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by apple on 2017/1/21.
 */
public class RemoteFileSystemServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger("main");
    private final Storage storage = LocalStorage.getInstance();
    private final Guardian guardian = Guardian.getInstance();
    private static final DatabaseController databaseController = UserProfileDatabaseController.getInstance();
    private static String rootPath = databaseController.getValue("FileRootPath");


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        Writer writer = resp.getWriter();
        writer.append("do not use getMethod");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Writer writer = resp.getWriter();
        if (!guardian.isLogin(req)) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            writer.append("Not Login ");
            writer.flush();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(req.getContentLength());
        InputStream inputStream = req.getInputStream();
        try {
            IOUtils.copy(inputStream, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            PathGenerator generator = new PathGenerator(bytes);
            Path path = Paths.get(rootPath + "/" + generator.getPath());
            if (storage.put(byteArrayOutputStream.toByteArray(), path)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                writer.append(generator.getURI());
            }
        } finally {
            IOUtils.closeQuietly(byteArrayOutputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private class PathGenerator {
        private String path;

        private String bytes2md5(byte[] file) {
            byte[] bytes = null;
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                bytes = messageDigest.digest(file);
            } catch (NoSuchAlgorithmException e) {
                logger.log(Level.WARNING, e.getMessage());
                return "";
            }
            StringBuilder stringBuffer = new StringBuilder();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            return stringBuffer.toString();
        }

        PathGenerator(byte[] bytes) {
            path = bytes2md5(bytes);
        }

        String getPath() {
            return path;
        }

        String getURI() {
            return "RemoteFileSystem/GetObject/" + path;
        }
    }
}
