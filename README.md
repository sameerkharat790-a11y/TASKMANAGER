# Daily Task Manager (Java)

A simple console program to manage a daily to-do list, written in core Java.
Tasks are saved to a text file, so they are still there the next time you run it.

College project, third-year B.Tech (AI and Data Science).

## Features

1. Add a task (title, priority, due date)
2. View all tasks
3. View today's tasks
4. View pending tasks
5. Mark a task as done
6. Delete a task
7. Search tasks by keyword
8. Sort by priority (then by due date)
9. Sort by due date
0. Exit

## How to run

You need Java (JDK 8 or newer). Open a terminal in this folder and run:

```
javac *.java
java Main
```

The program creates `tasks.txt` in the folder you run it from.

## Project structure

| File | Job |
| --- | --- |
| `Task.java` | Model: the data of one task (id, title, priority, due date, done). |
| `FileHandler.java` | Storage: loads tasks from `tasks.txt` and saves them back. |
| `TaskManager.java` | Logic: add, find, mark done, delete, search and sort. Never prints or reads input. |
| `Main.java` | Screen: the menu loop, keyboard input and printing tables. |

`Main` calls `TaskManager`, which uses `Task` and `FileHandler`.

## How data is stored

One task per line, values separated by `|`:

```
id|title|priority|dueDate|done
1|Study Java|1|2026-11-10|false
```

Priority: 1 = High, 2 = Medium, 3 = Low.

## Design choices

- Every change is saved straight away, so closing the program never loses data.
- New ids are the largest saved id + 1, so deleting a task never causes duplicate ids.
- Sorting works on a copy of the list, so the saved order does not change.
- Wrong input (letters instead of numbers, bad dates) asks again instead of crashing.

## Possible improvements

- Edit an existing task
- Show overdue tasks and a progress line (done out of total)
- A Swing or JavaFX window, or a MySQL database instead of a text file
