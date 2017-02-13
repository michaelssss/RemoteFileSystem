package com.michaelssss.storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by apple on 2017/1/25.
 */
public class LocalStorageTest {
    private Storage storage;

    public static void main(String[] s) throws Exception {
        LocalStorageTest test = new LocalStorageTest();
        test.setUp();
        test.testPut();
    }

    public void setUp() throws Exception {
        this.storage = LocalStorage.getInstance();
        Files.deleteIfExists(Paths.get(LocalStorageTest.class.getResource("/").getPath().toString() + "/testFile"));
    }

    public void testPut() {

        try {
            byte[] fileBytes = "Hello this is test Word".getBytes("UTF-8");
            Path testPath = Paths.get(LocalStorageTest.class.getResource("/").getPath().toString() + "/testFile");
            if (!storage.put(fileBytes, testPath)) {
                System.out.println("not right");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
