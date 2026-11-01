package model;

public record Task(int id, String title) {
    public Task {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be blank");
        }
    }
}
