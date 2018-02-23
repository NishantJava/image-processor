package com.looklet.test.image;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {
    private static final String IMAGE_RESOURCE_FOLDER_NAME = "images";
    private static final int BYTE_COUNT = 1000;

    @Test
    public void processImages() throws Exception {
        Map<Integer, String> expectedFileMap = new LinkedHashMap<>();
        expectedFileMap.put(48000, "test3.txt");
        expectedFileMap.put(65000, "test1.txt");
        expectedFileMap.put(66000, "test2.txt");
        assertEquals(expectedFileMap, Application.processImages(IMAGE_RESOURCE_FOLDER_NAME, 1000));
    }

    @Test
    public void getByteTotal() throws Exception {
        Path imageFolderPath = Paths.get(ClassLoader.getSystemResource(IMAGE_RESOURCE_FOLDER_NAME + "/test2.txt").toURI());
        assertEquals(66000, Application.getByteTotal(imageFolderPath, 1000));
    }
}