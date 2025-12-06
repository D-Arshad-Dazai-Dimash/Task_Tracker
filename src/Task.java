import java.time.LocalDateTime;

public class Task {
    static int counter = 0;
    private int id;
    private String description;
    private StatusOfProgress status; // TODO, IN-PROGRESS, DONE
    private LocalDateTime createdAt; // ISO
    private LocalDateTime updatedAt;

    Task() {
        counter++;
        this.id = counter;
        this.status = StatusOfProgress.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    Task(String description, StatusOfProgress status) {
        counter++;
        this.id = counter;
        this.description = description;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusOfProgress getStatus() {
        return status;
    }

    public void setStatus(StatusOfProgress status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toJson() {
        return "{\n" +
                "  \"id\": " + getId() + ",\n" +
                "  \"description\": \"" + getDescription() + "\",\n" +
                "  \"status\": \"" + getStatus() + "\",\n" +
                "  \"createdAt\": \"" + getCreatedAt() + "\",\n" +
                "  \"updatedAt\": \"" + getUpdatedAt() + "\"\n" +
                "}";
    }

    public static Task fromJson(String json) {
        json = json.trim();
        // убираем { и }
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        String[] lines = json.split(",\n"); // делим по строкам-полям

        int id = 0;
        String description = null;
        StatusOfProgress status = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("\"id\"")) {
                String value = line.split(":")[1].trim();
                id = Integer.parseInt(value);
            } else if (line.startsWith("\"description\"")) {
                String value = line.split(":", 2)[1].trim();
                description = value.substring(1, value.length() - 1);
            } else if (line.startsWith("\"status\"")) {
                String value = line.split(":", 2)[1].trim();
                value = value.substring(1, value.length() - 1);
                status = StatusOfProgress.valueOf(value);
            } else if (line.startsWith("\"createdAt\"")) {
                String value = line.split(":", 2)[1].trim();
                value = value.substring(1, value.length() - 1);
                createdAt = LocalDateTime.parse(value);
            } else if (line.startsWith("\"updatedAt\"")) {
                String value = line.split(":", 2)[1].trim();
                value = value.substring(1, value.length() - 1);
                updatedAt = LocalDateTime.parse(value);
            }
        }

        Task task = new Task(description, status);
        task.setId(id);
        task.setCreatedAt(createdAt);
        task.setUpdatedAt(updatedAt);
        return task;
    }

}