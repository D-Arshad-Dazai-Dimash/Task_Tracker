import java.time.LocalDateTime;
import java.util.List;

class StatusChanger {
    public static List<Task> changeStatus(List<Task> allTasks, String[] args) {
        if (allTasks.isEmpty()) {
            System.out.println("No tasks found!");
            return allTasks;
        }
        if (args.length < 2) {
            System.out.println("Usage: <mark-done or mark-in-progress> <id>");
            return allTasks;
        }

        int id = Integer.parseInt(args[1]);

        boolean found = false;
        for (
                Task t : allTasks) {
            if (t.getId() == id) {
                if (args[0].equals("mark-done")) {
                    t.setStatus(StatusOfProgress.DONE);
                    System.out.println("Task's status changed to DONE!");
                } else if (args[0].equals("mark-in-progress")) {
                    t.setStatus(StatusOfProgress.InProgress);
                    System.out.println("Task's status changed to IN PROGRESS!");
                }
                t.setUpdatedAt(LocalDateTime.now());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Task with id " + id + " not found!");
            return allTasks;
        }

        return allTasks;

    }
}
