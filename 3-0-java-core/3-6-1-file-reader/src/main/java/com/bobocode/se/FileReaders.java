package com.bobocode.se;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * {@link FileReaders} provides an API that allow to read whole file into a {@link String} by file name.
 */
public class FileReaders {

    /**
     * Returns a {@link String} that contains whole text from the file specified by name.
     *
     * @param fileName a name of a text file
     * @return string that holds whole file content
     */
    public static String readWholeFile(String fileName) {
        Path filePath = createPath(fileName);
        try (Stream<String> fileLinesStream = openFile(filePath)) {
            return fileLinesStream.collect(joining("\n"));
        }
    }

    private static Path createPath(String filename) {
        Objects.requireNonNull(filename);
        URL fileUrl = FileReaders.class.getClassLoader().getResource(filename);
        try {
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new FileReaderException("Can't create path ", e);
        }
    }

    private static Stream<String> openFile(Path filePath) {
        try {
            return Files.lines(filePath);
        } catch (IOException e) {
            throw new FileReaderException("Couldn't open file ", e);
        }
    }
}
