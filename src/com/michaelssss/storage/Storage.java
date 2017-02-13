package com.michaelssss.storage;

import java.nio.file.Path;

/**
 * Created by apple on 2017/1/25.
 */
public interface Storage {
    boolean put(byte[] file, Path path);

    boolean delete(Path path);

    byte[] get(Path path);

    boolean Exist(Path path);
}
