package ru.eliseev.charm.plugin.check.model;

public record FileAnalysisResult(String fileName, long lineCount, boolean exceedsLimit) {

    @Override
    public String toString() {
        return String.format("%s: %d lines%s",
            fileName, lineCount, exceedsLimit ? " (EXCEEDS LIMIT)" : "");
    }
}