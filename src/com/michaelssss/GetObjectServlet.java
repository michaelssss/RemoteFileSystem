package com.michaelssss;

import com.michaelssss.database.DatabaseController;
import com.michaelssss.database.UserProfileDatabaseController;
import com.michaelssss.storage.LocalStorage;
import com.michaelssss.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by michaelssss on 2017/2/10.
 */
public class GetObjectServlet extends HttpServlet {
    private static final DatabaseController databaseController = UserProfileDatabaseController.getInstance();
    private static String rootPath = databaseController.getValue("FileRootPath");
    private final Storage storage = LocalStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String filename = uri.substring(uri.lastIndexOf('/') + 1);
        Path path = Paths.get(rootPath + "/" + filename);

        if (storage.Exist(path)) {
            byte[] filebytes = storage.get(path);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentLength(filebytes.length);
            resp.setContentType("application/octet-stream");
            try (OutputStream outputStream = resp.getOutputStream()) {
                outputStream.write(filebytes);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        Writer writer = resp.getWriter();
        writer.append("do not use Post Method");
        writer.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        Writer writer = resp.getWriter();
        writer.append("do not use Put Method");
        writer.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        Writer writer = resp.getWriter();
        writer.append("do not use Delete Method");
        writer.flush();
    }
}
