package com.michaelssss.storage;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by apple on 2017/1/25.
 */
public abstract class AbstractStorage implements Storage {


    protected AbstractStorage() {
    }


    protected abstract void actualPut(byte[] file, Path path) throws IOException;

    protected abstract void actualDelete(Path path) throws IOException;

    protected abstract byte[] actualGet(Path path) throws IOException;

    protected abstract boolean isExist(Path path) throws IOException;

    @Override
    public boolean put(byte[] file, Path path) {
        try {
            actualPut(file, path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Path path) {
        try {
            actualDelete(path);
            return true;
        } catch (IOException e) {

        }
        return false;
    }

    @Override
    public byte[] get(Path path) {
        try {
            return actualGet(path);
        } catch (IOException e) {

        }
        return new byte[0];
    }

    @Override
    public boolean Exist(Path path) {
        try {
            return isExist(path);
        } catch (IOException e) {

        }
        return false;
    }
}
