package ru.eliseev.charm.back.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static ru.eliseev.charm.back.utils.UrlUtils.BASE_CONTENT_PATH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentService {
    private static final ContentService INSTANCE = new ContentService();

    public static ContentService getInstance() {
        return INSTANCE;
    }

    public void upload(String contentPath, InputStream inputStream) throws IOException {
        Path contentFullPath = Path.of(BASE_CONTENT_PATH, contentPath);
        Files.createDirectories(contentFullPath.getParent());
        OutputStream outputStream = Files.newOutputStream(contentFullPath, CREATE, TRUNCATE_EXISTING);
        writeContent(inputStream, outputStream);
    }

    public void download(String contentPath, OutputStream outputStream) throws IOException {
        String decodedPath = URLDecoder.decode(contentPath, StandardCharsets.UTF_8);
        Path contentFullPath = Path.of(BASE_CONTENT_PATH, decodedPath);
        InputStream inputStream = Files.newInputStream(contentFullPath);
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
