package com.epam;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("Select option:");
            System.out.println("1. Display on console all tasks");
            System.out.println("2. Display overdue tasks");
            System.out.println("3. Display all tasks with a specific category (query parameter");
            System.out.println("4. Display all subtasks related to tasks with a specific category");
            System.out.println("5. insert/update/delete of the task");
            System.out.println("6. Perform insert/update/delete all subtasks of a given task (query parameter)");
            System.out.println("7. Support full-text search by word in the task description.");
            System.out.println("8. Full-text search by a sub-task name.");
            System.out.println("0. Exit");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> MongoClientHandler.displayAllTasks();
                case 2 -> MongoClientHandler.displayOverdueTasks();
                case 3 -> {
                    System.out.println("Enter the category for the tasks:");
                    String category = scanner.next();
                    MongoClientHandler.displayTasksByCategory(category);
                }
                case 4 -> {
                    System.out.println("Enter the category to display subtasks:");
                    String category = scanner.next();
                    MongoClientHandler.displaySubtasksByCategory(category);
                }
                case 5 -> {
                    System.out.println("Select option:");
                    System.out.println("1. Insert new task");
                    System.out.println("2. Update task");
                    System.out.println("3. Delete task");
                    var insertUpdateDelete = scanner.nextInt();
                    switch (insertUpdateDelete) {
                        case 1 -> MongoClientHandler.addNewTask();
                        case 2 -> {
                            System.out.println("Enter task id");
                            var id = scanner.next();
                            MongoClientHandler.editTask(id);
                        }
                        case 3 -> {
                            System.out.println("Enter task id");
                            var id = scanner.next();
                            MongoClientHandler.deleteTask(id);
                        }
                    }
                }
                case 7 -> {
                    System.out.println("Enter word for full-text search in task description:");
                    String word = scanner.next();
                    MongoClientHandler.fullTextSearch(word);
                }
                case 8 -> {
                    System.out.println("Enter word for full-text search in sub-task name:");
                    String word = scanner.next();
                    MongoClientHandler.fullTextSearchBySubtaskName(word);
                }
            }

        } while (option != 0);
    }
}
