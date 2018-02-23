package com.looklet.test.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Application {
    private static final String IMAGE_RESOURCE_FOLDER_NAME = "images";
    private static final int BYTE_COUNT = 1000;

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            Map<String, Integer> processedData = processImages(IMAGE_RESOURCE_FOLDER_NAME, BYTE_COUNT);

            if (!processedData.isEmpty()) {
                processedData.forEach((key, value) -> LOGGER.debug("{}: {}", value, key));
            } else {
                LOGGER.warn("No processing data to display");
            }
        } catch (Exception e) {
            LOGGER.error("Error in image processing", e);
        }
    }

    public static Map<String, Integer> processImages(String imageFolderName, int byteCount) throws IOException, URISyntaxException {
        Map<String, Integer> fileMap = new HashMap<>();

        Path imageFolderPath = Paths.get(ClassLoader.getSystemResource(imageFolderName).toURI());

        try (Stream<Path> stream = Files.walk(imageFolderPath)) {
            stream.filter(Files::isRegularFile).forEach(path ->
                    fileMap.put(path.getFileName().toString(), getByteTotal(path, byteCount)));
        }

        return sortMapByValues(fileMap);
    }

    public static int getByteTotal(Path path, int byteCount) {
        byte[] fileBytes = new byte[byteCount];

        try (InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ)) {
            int bytesRead = inputStream.read(fileBytes, 0, byteCount);

            if (bytesRead < byteCount) {
                LOGGER.warn("Files Stream is {} smaller than {} ", path.getFileName(), byteCount);
            }
        } catch (IOException e) {
            LOGGER.error("Error in reading input file: " + path.getFileName(), e);
            return 0;
        }

        return IntStream.range(0, fileBytes.length).map(index -> fileBytes[index]).sum();
    }

    public static Map<String, Integer> sortMapByValues(Map<String, Integer> fileMap) {
        if (fileMap.isEmpty()) {
            return fileMap;
        }

        return fileMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
