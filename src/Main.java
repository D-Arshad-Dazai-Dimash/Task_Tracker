import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("No command provided");
            return;
        }

        TaskRepository taskRepository = new TaskRepository();
        List<Task> allTasks = taskRepository.readTasks();
        String command = args[0];

        switch (command) {
            case "add" -> {
                if (args.length < 2) {
                    System.out.println("Description is required");
                    return;
                }
                String description = args[1];
                Task task = new Task(description, StatusOfProgress.TODO);
                allTasks.add(task);
                taskRepository.writeTasks(allTasks);
                System.out.println("Task is created with id: " + task.getId());
            }
            case "list" -> {
                if (allTasks.isEmpty()) {
                    System.out.println("No tasks found!");
                    return;
                }

                String status = args.length >= 2 ? args[1] : "all";

                for (Task t : allTasks) {
                    if (TaskMatcher.statusMatches(t, status)) {
                        System.out.println(t);
                    }
                }
            }
            case "update" -> {
                if (allTasks.isEmpty()) {
                    System.out.println("No tasks found!");
                    return;
                }
                if (args.length < 3) {
                    System.out.println("Usage: update <id> <description>");
                    return;
                }
                int id = Integer.parseInt(args[1]);
                String newDescription = args[2];

                boolean found = false;
                for (Task t : allTasks) {
                    if (t.getId() == id) {
                        t.setDescription(newDescription);
                        t.setUpdatedAt(LocalDateTime.now());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Task with id " + id + " not found!");
                    return;
                }

                taskRepository.writeTasks(allTasks);
                System.out.println("Task updated!");
            }
            case "delete" -> {
                if (allTasks.isEmpty()) {
                    System.out.println("No tasks found!");
                    return;
                }
                if (args.length < 2) {
                    System.out.println("Usage: delete <id>");
                    return;
                }

                int id = Integer.parseInt(args[1]);

                boolean deleted = false;
                Iterator<Task> it = allTasks.iterator();
                while (it.hasNext()) {
                    Task t = it.next();
                    if (t.getId() == id) {
                        it.remove();
                        deleted = true;
                        break;
                    }
                }


                if (!deleted) {
                    System.out.println("Task with id " + id + " not found!");
                    return;
                }


                taskRepository.writeTasks(allTasks);
                System.out.println("Task deleted!");


            }
            default -> System.out.println("Unknown command");
        }
    }
}