import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private static final String PATH = "tasks.json";

    public List<Task> readTasks() {
        File file = new File(PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<Task> taskList = new ArrayList<>();
        try {
            String json = Files.readString(Path.of(PATH)).trim();
            if (json.equals("[]")) return new ArrayList<>();

            if (json.startsWith("[")) json = json.substring(1);
            if (json.endsWith("]")) json = json.substring(0, json.length() - 1);

            String[] parts = json.split("},\\s*\\{");
            for (String part : parts) {
                String obj = part.trim();
                if (!obj.startsWith("{")) obj = "{" + obj;
                if (!obj.endsWith("}")) obj = obj + "}";
                taskList.add(Task.fromJson(obj));
            }
            return taskList;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }


    public void writeTasks(List<Task> tasks) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(tasks.get(i).toJson());
            if (i < tasks.size() - 1) {
                sb.append(",\n");
            }
        }
        sb.append("\n]");

        Files.writeString(Path.of("tasks.json"), sb.toString());
    }


}
