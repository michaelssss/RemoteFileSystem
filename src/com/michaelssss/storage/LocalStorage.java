package com.michaelssss.storage;

import com.michaelssss.IOUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Created by apple on 2017/1/25.
 */
public class LocalStorage extends AbstractStorage {
    private static volatile Storage instance;

    private LocalStorage() {
        super();
    }

    public static Storage getInstance() {
        if (null == instance) {
            instance = new LocalStorage();
        }
        return instance;
    }

    @Override
    protected void actualPut(byte[] file, Path path) throws IOException {
        Files.createDirectories(path.getParent());
        FileChannel channel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        channel.lock();
        channel.write(ByteBuffer.wrap(file));
        IOUtils.closeQuietly(channel);
    }

    @Override
    protected void actualDelete(Path path) throws IOException {
        Files.deleteIfExists(path);
    }

    @Override
    protected byte[] actualGet(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("file not exsit");
        }
        return Files.readAllBytes(path);
    }

    @Override
    protected boolean isExist(Path path) throws IOException {
        return Files.exists(path);
    }
}
