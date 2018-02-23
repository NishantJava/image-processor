package com.looklet.test.image;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class Application {
    private static final String IMAGE_RESOURCE_FOLDER_NAME = "images";
    private static final int BYTE_COUNT = 1000;

    public static void main(String[] args) {
        try {
            Path imageFolderPath = Paths.get(ClassLoader.getSystemResource(IMAGE_RESOURCE_FOLDER_NAME).toURI());
            processImages(imageFolderPath, BYTE_COUNT);
        } catch (Exception e) {
            log.error("Error in image processing", e);
        }
    }

    public static void processImages(Path imageFolderPath, int byteCount) throws IOException {
        Map<Path, Integer> fileMap = new HashMap<>();

        try (Stream<Path> stream = Files.walk(imageFolderPath)) {
            stream.filter(Files::isRegularFile).forEach(path ->
                    fileMap.put(path, getByteTotal(path, byteCount)));
        }

        printMapSortedByValues(fileMap);
    }

    public static int getByteTotal(Path path, int byteCount) {
        byte[] fileBytes = new byte[byteCount];

        try (InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ)) {
            int bytesRead = inputStream.read(fileBytes, 0, byteCount);

            if (bytesRead < byteCount) {
                log.warn("Files Stream is {} smaller than {} ", path.getFileName(), byteCount);
            }
        } catch (IOException e) {
            log.error("Error in reading input file: " + path.getFileName(), e);
            return 0;
        }

        return IntStream.range(0, fileBytes.length).map(index -> fileBytes[index]).sum();
    }

    public static void printMapSortedByValues(Map<Path, Integer> fileMap) {
        if (!fileMap.isEmpty()) {
            fileMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> log.debug("{}: {}", entry.getValue(), entry.getKey().getFileName()));
        }
    }
}
