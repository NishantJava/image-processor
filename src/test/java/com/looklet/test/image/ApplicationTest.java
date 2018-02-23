package com.looklet.test.image;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {
    private static final String IMAGE_RESOURCE_FOLDER_NAME = "images";
    private static final int BYTE_COUNT = 1000;

    @Test
    public void processImages() throws Exception {
        Map<String, Integer> expectedFileMap = new LinkedHashMap<>();
        expectedFileMap.put("test3.txt", 48000);
        expectedFileMap.put("test1.txt", 65000);
        expectedFileMap.put("test2.txt", 66000);
        assertEquals(expectedFileMap, Application.processImages(IMAGE_RESOURCE_FOLDER_NAME, 1000));
    }

    @Test
    public void getByteTotal() throws Exception {
        Path imageFolderPath = Paths.get(ClassLoader.getSystemResource(IMAGE_RESOURCE_FOLDER_NAME + "/test2.txt").toURI());
        assertEquals(66000, Application.getByteTotal(imageFolderPath, 1000));
    }

    @Test
    public void sortMapByValues() {
        Map<String, Integer> inputMap = new HashMap<>();

        assertEquals(new HashMap<String, Integer>(), Application.sortMapByValues(inputMap));

        inputMap.put("test1.txt", 2);
        inputMap.put("test2.txt", 3);
        inputMap.put("test3.txt", 1);

        Map<String, Integer> expectedFileMap = new LinkedHashMap<>();
        expectedFileMap.put("test3.txt", 1);
        expectedFileMap.put("test1.txt", 2);
        expectedFileMap.put("test2.txt", 3);

        assertEquals(expectedFileMap, Application.sortMapByValues(inputMap));
    }
}