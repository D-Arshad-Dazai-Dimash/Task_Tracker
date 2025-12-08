# Task Tracker CLI (Java)

Project URL: https://roadmap.sh/projects/task-tracker

Simple command-line task tracker written in Java.  
Tasks are stored in a local `tasks.json` file in the current directory (no external libraries).

This project was created to practice:

- Java OOP (classes, enums)
- Working with the file system
- Manual JSON serialization
- Command-line arguments (`String[] args`)
- Basic separation of layers (model / repository / CLI)

***

## Features

- Add new tasks
- List tasks (all / by status)
- Update task description by `id`
- Delete tasks by `id`
- Mark task as `in-progress` or `done`
- Tasks are persisted between runs in `tasks.json`

Each task has:

- `id` – unique identifier  
- `description` – short text  
- `status` – `TODO`, `InProgress`, `DONE`  
- `createdAt` – creation time (`LocalDateTime`)  
- `updatedAt` – last update time (`LocalDateTime`)

***

## Build & Run

Compile:

```bash
javac Main.java
```

Run with arguments:

```bash
java Main mmand> [arguments...]
```

All data is stored in `tasks.json` in the same directory as the program.

***

## Commands

### 1. Add task

Add a new task with status `TODO`:

```bash
java Main add "Buy groceries"
```

Output example:

```text
Task is created with id: 1
```

***

### 2. List tasks

List all tasks:

```bash
java Main list
```

List tasks by status:

```bash
java Main list todo
java Main list done
java Main "list" "in progress"
```

Supported status filters (case-insensitive):

- `all` – all tasks  
- `todo` – status `TODO`  
- `done` – status `DONE`  
- `in progress` – status `InProgress`

***

### 3. Update task description

Update description of a task by `id`:

```bash
java Main update <id> "<new description>"
# example:
java Main update 1 "Buy groceries and cook dinner"
```

If task with given `id` is not found, a message is printed.

***

### 4. Delete task

Delete task by `id`:

```bash
java Main delete <id>
# example:
java Main delete 1
```

***

### 5. Change task status

Mark task as **in progress**:

```bash
java Main "mark-in-progress" <id>
# example:
java Main "mark-in-progress" 2
```

Mark task as **done**:

```bash
java Main "mark-done" <id>
# example:
java Main "mark-done" 2
```

On status change:

- `status` field is updated (`InProgress` or `DONE`)
- `updatedAt` is set to `LocalDateTime.now()`

***

## JSON storage format

Tasks are stored in `tasks.json` as a JSON array of objects:

```json
[
  {
    "id": 1,
    "description": "Buy groceries",
    "status": "TODO",
    "createdAt": "2025-12-06T18:30:00",
    "updatedAt": "2025-12-06T18:30:00"
  },
  {
    "id": 2,
    "description": "Learn Java",
    "status": "InProgress",
    "createdAt": "2025-12-06T19:00:00",
    "updatedAt": "2025-12-06T19:10:00"
  }
]
```

Serialization and deserialization are implemented manually (without JSON libraries).

***

## Implementation details

Main components:

- `Task` – model class:
  - fields: `id`, `description`, `status`, `createdAt`, `updatedAt`
  - methods: getters/setters, `toJson()`, `fromJson(String json)`, `toString()`

- `StatusOfProgress` – enum for task status:
  - `TODO`, `InProgress`, `DONE`

- `TaskRepository` – file-based storage:
  - `readTasks()` – reads `tasks.json` and returns `List<Task>`
  - `writeTasks(List<Task> tasks)` – writes list of tasks to `tasks.json`
  - uses `java.nio.file.Files` for reading/writing

- `TaskMatcher` – helper for filtering tasks by status in `list` command

- `StatusChanger` – helper for `mark-done` / `mark-in-progress`:
  - validates input (`id` present, tasks not empty)
  - finds task by `id`
  - changes status and `updatedAt`
  - prints messages
  - returns updated `List<Task>`

- `Main` – CLI entry point:
  - parses `String[] args`
  - routes commands: `add`, `list`, `update`, `delete`, `mark-in-progress`, `mark-done`
  - interacts with `TaskRepository`, `TaskMatcher`, `StatusChanger`

***

## Future improvements

Possible next steps for this project:

- Replace manual JSON parsing with a library (e.g. Jackson or Gson)
- Add unit tests for `TaskRepository`, `TaskMatcher`, `StatusChanger`
- Improve CLI parsing:
  - support multi-word descriptions more flexibly
  - better error messages and help/usage output
- Extract logic into a dedicated service layer (e.g. `TaskService`) and keep `Main` very thin
- Migrate from file storage to a database (e.g. H2/PostgreSQL)
- Expose the same functionality as a REST API using Spring Boot

***

