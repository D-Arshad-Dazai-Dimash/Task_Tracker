import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("No command provided");
            return;
        }

        TaskRepository taskRepository = new TaskRepository();
        List<Task> tasks = taskRepository.readTasks();
        String command = args[0];

        switch (command) {
            case "add" -> {
                if (args.length < 2) {
                    System.out.println("Description is required");
                    return;
                }
                String description = args[1];
                Task task = new Task(description, StatusOfProgress.TODO);
                tasks.add(task);
                taskRepository.writeTasks(tasks);
                System.out.println("Task is created with id: " + task.getId());
            }
            case "list" -> {
            }
            case "update" -> {
            }
            case "delete" -> {
            }
            default -> System.out.println("Unknown command");
        }
    }
}