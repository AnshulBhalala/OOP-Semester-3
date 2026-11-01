package app;

import service.TaskService;
import java.util.Scanner;

public class LayeredApp {
    public static void main(String[] args) {
        TaskService service = new TaskService();
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n1. Add task  2. List tasks  0. Exit");
                System.out.print("Choice: ");
                String choice = sc.nextLine().trim();
                try {
                    switch (choice) {
                        case "1" -> {
                            System.out.print("Task title: ");
                            service.performAction("ADD", sc.nextLine());
                            System.out.println("Task added.");
                        }
                        case "2" -> service.performAction("LIST", "");
                        case "0" -> {
                            System.out.println("Goodbye.");
                            return;
                        }
                        default -> service.performAction(choice, "");
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
