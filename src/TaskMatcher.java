public class TaskMatcher {
    public static boolean statusMatches(Task t, String status) {
        return switch (status) {
            case "all", "ALL", "All" -> true;
            case "todo", "TODO", "Todo" -> t.getStatus() == StatusOfProgress.TODO;
            case "DONE", "Done", "done" -> t.getStatus() == StatusOfProgress.DONE;
            case "In progress", "IN PROGRESS", "in progress" -> t.getStatus() == StatusOfProgress.InProgress;
            default -> false;
        };
    }
}
