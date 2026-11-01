package service;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public Task addTask(String title) {
        Task task = new Task(nextId++, title);
        tasks.add(task);
        return task;
    }

    public int count() {
        return tasks.size();
    }

    public List<Task> getAll() {
        return List.copyOf(tasks);
    }

    public void performAction(String action, String value) {
        if ("ADD".equalsIgnoreCase(action)) {
            addTask(value);
        } else if ("LIST".equalsIgnoreCase(action)) {
            getAll().forEach(System.out::println);
        } else {
            throw new IllegalArgumentException("Invalid action: " + action);
        }
    }
}
