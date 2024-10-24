package ru.eliseev.charm.back.service;

import jakarta.servlet.ServletOutputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentService {
    private static final ContentService INSTANCE = new ContentService();
    
    public static final String BASE_PATH = "/Users/andrey.s.eliseev/Downloads";

    public static ContentService getInstance() {
        return INSTANCE;
    }

    public void upload(String contentPath, InputStream inputStream) throws IOException {
        Path contentFullPath = Path.of(BASE_PATH, contentPath);
        Files.createDirectories(contentFullPath.getParent());
        OutputStream outputStream = Files.newOutputStream(contentFullPath, CREATE, TRUNCATE_EXISTING);
        writeContent(inputStream, outputStream);
    }

    public void download(String contentPath, ServletOutputStream outputStream) throws IOException {
        InputStream inputStream;
        if (contentPath.startsWith("/app/")) {
            String appPath = "/WEB-INF" + contentPath.replaceFirst("/app", "");
            inputStream = ContentService.class.getClassLoader().getResourceAsStream(appPath);
        } else {
            Path contentFullPath = Path.of(BASE_PATH, contentPath);
            inputStream = Files.newInputStream(contentFullPath);
        }
        if (inputStream == null) {
            throw new FileNotFoundException();
        }
        writeContent(inputStream, outputStream);
    }

    private void writeContent(InputStream inputStream, OutputStream outputStream) throws IOException {
        try (inputStream; outputStream) {
            int currentByte;
            while ((currentByte = inputStream.read()) != -1) {
                outputStream.write(currentByte);
            }
        }
    }
}
