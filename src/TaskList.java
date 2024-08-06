import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskList {

    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    // Метод для добавления задачи в список
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Метод для удаления задачи из списка
    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        } else {
            System.out.println("Задача с таким индексом не найдена.");
        }
    }

    // Метод для отображения всех задач в списке
    public void displayTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    // Метод для отображения выполненных задач в списке
    public void displayCompletedTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).isCompleted()) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    // Метод для пометки задачи как выполненной
    public void markTaskAsCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsCompleted();
        } else {
            System.out.println("Задача с таким индексом не найдена.");
        }
    }

    // Метод для сохранения списка задач в файл
    public void saveTasksToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.getDescription() + "," + task.isCompleted());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    // Метод для загрузки списка задач из файла
    public void loadTasksFromFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    Task task = new Task(parts[0]);
                    task.setCompleted(Boolean.parseBoolean(parts[1]));
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        TaskList taskList = new TaskList();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Добавить задачу");
            System.out.println("2. Удалить задачу");
            System.out.println("3. Отобразить все задачи");
            System.out.println("4. Отобразить выполненные задачи");
            System.out.println("5. Пометить задачу как выполненную");
            System.out.println("6. Сохранить список задач в файл");
            System.out.println("7. Загрузить список задач из файла");
            System.out.println("8. Выход");

            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Сбросить Enter

            switch (choice) {
                case 1:
                    System.out.print("Введите описание задачи: ");
                    String description = scanner.nextLine();
                    Task task = new Task(description);
                    taskList.addTask(task);
                    break;
                case 2:
                    System.out.print("Введите индекс задачи для удаления: ");
                    int index = scanner.nextInt() - 1;
                    taskList.removeTask(index);
                    break;
                case 3:
                    taskList.displayTasks();
                    break;
                case 4:
                    taskList.displayCompletedTasks();
                    break;
                case 5:
                    System.out.print("Введите индекс задачи для пометки как выполненной: ");
                    index = scanner.nextInt() - 1;
                    taskList.markTaskAsCompleted(index);
                    break;
                case 6:
                    System.out.print("Введите путь к файлу для сохранения: ");
                    String filePath = scanner.nextLine();
                    taskList.saveTasksToFile(filePath);
                    break;
                case 7:
                    System.out.print("Введите путь к файлу для загрузки: ");
                    filePath = scanner.nextLine();
                    taskList.loadTasksFromFile(filePath);
                    break;
                case 8:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неправильный выбор. Пожалуйста, попробуйте еще раз.");
            }
        }
    }
}