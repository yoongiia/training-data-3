import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Клас BasicDataOperationUsingQueue надає методи для виконання основних операцiй з даними типу String.
 * 
 * <p>Цей клас зчитує данi з файлу "list/String.data", сортує їх у зворотному порядку
 * та виконує пошук значення в масивi та черзi.</p>
 */
public class BasicDataOperationUsingQueue {
    static final String PATH_TO_DATA_FILE = "list/String.data";

    String stringValueToSearch;
    String[] stringArray;
    Queue<String> stringQueue;

    public static void main(String[] args) {
        BasicDataOperationUsingQueue basicDataOperationUsingQueue = new BasicDataOperationUsingQueue(args);
        basicDataOperationUsingQueue.doDataOperation();
    }

    /**
     * Конструктор, який iнiцiалiзує об'єкт з значенням для пошуку.
     * 
     * @param args Аргументи командного рядка, де перший аргумент - значення для пошуку.
     */
    BasicDataOperationUsingQueue(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку");
        }

        String valueToSearch = args[0];
        this.stringValueToSearch = valueToSearch;

        stringArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);

        stringQueue = new PriorityQueue<>(Arrays.asList(stringArray));
    }

    /**
     * Виконує основнi операцiї з даними.
     */
    private void doDataOperation() {
        // операцiї з масивом
        searchArray();
        findMinAndMaxInArray();

        sortArrayReverse();

        searchArray();
        findMinAndMaxInArray();

        // операцiї з Queue
        sortQueueReverse();
        searchQueue();
        findMinAndMaxInQueue();
        peekAndPollQueue();

        // записати вiдсортований масив в окремий файл
        Utils.writeArrayToFile(stringArray, PATH_TO_DATA_FILE + ".reverse_sorted");
    }

    /**
     * Сортує масив об'єктiв String у зворотному порядку та виводить результат.
     */
    private void sortArrayReverse() {
        long startTime = System.nanoTime();

        Arrays.sort(stringArray, Collections.reverseOrder());

        Utils.printOperationDuration(startTime, "сортування масиву у зворотному порядку");
    }

    /**
     * Сортує чергу String у зворотному порядку.
     */
    private void sortQueueReverse() {
        long startTime = System.nanoTime();

        List<String> sortedList = new ArrayList<>(stringQueue);
        Collections.sort(sortedList, Collections.reverseOrder());
        stringQueue = new PriorityQueue<>(sortedList);

        Utils.printOperationDuration(startTime, "сортування Queue у зворотному порядку");
    }

    /**
     * Метод для пошуку значення в масивi типу String.
     */
    private void searchArray() {
        long startTime = System.nanoTime();

        int index = Arrays.binarySearch(this.stringArray, stringValueToSearch, Collections.reverseOrder());

        Utils.printOperationDuration(startTime, "пошук в масивi");

        if (index >= 0) {
            System.out.println("Значення '" + stringValueToSearch + "' знайдено в масивi за iндексом: " + index);
        } else {
            System.out.println("Значення '" + stringValueToSearch + "' в масивi не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в масивi String.
     */
    private void findMinAndMaxInArray() {
        if (stringArray == null || stringArray.length == 0) {
            System.out.println("Масив порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();

        String min = stringArray[stringArray.length - 1]; // мінімальне в кінці
        String max = stringArray[0]; // максимальне на початку

        Utils.printOperationDuration(startTime, "пошук мінімального і максимального значення в масиві");

        System.out.println("Мінімальне значення в масиві: " + min);
        System.out.println("Максимальне значення в масиві: " + max);
    }

    /**
     * Метод для пошуку значення в черзi типу String.
     */
    private void searchQueue() {
        long startTime = System.nanoTime();

        boolean isFound = this.stringQueue.contains(stringValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в Queue");

        if (isFound) {
            System.out.println("Значення '" + stringValueToSearch + "' знайдено в Queue");
        } else {
            System.out.println("Значення '" + stringValueToSearch + "' в Queue не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в черзi String.
     */
    private void findMinAndMaxInQueue() {
        if (stringQueue == null || stringQueue.isEmpty()) {
            System.out.println("Queue порожній або не ініціалізований.");
            return;
        }

        long startTime = System.nanoTime();

        List<String> sortedList = new ArrayList<>(stringQueue);
        Collections.sort(sortedList);
        String min = sortedList.get(0);
        String max = sortedList.get(sortedList.size() - 1);

        Utils.printOperationDuration(startTime, "пошук мінімального і максимального значення в Queue");

        System.out.println("Мінімальне значення в Queue: " + min);
        System.out.println("Максимальне значення в Queue: " + max);
    }

    /**
     * Виконує операцiї peek та poll з чергою String.
     */
    private void peekAndPollQueue() {
        if (stringQueue == null || stringQueue.isEmpty()) {
            System.out.println("Queue порожнiй або не iнiцiалiзований.");
            return;
        }

        String firstElement = stringQueue.peek();
        System.out.println("Перший елемент у черзi: " + firstElement);

        firstElement = stringQueue.poll();
        System.out.println("Забрати перший елемент у черзi: " + firstElement);

        firstElement = stringQueue.peek();
        System.out.println("Перший елемент у черзi: " + firstElement);
    }
}

/**
 * Клас Utils мiститить допомiжнi методи для роботи з даними типу String.
 */
class Utils {
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("\n>>>>>>>>>> Час виконання операцiї '" + operationName + "': " + duration + " наносекунд");
    }

    static String[] readArrayFromFile(String pathToFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            return reader.lines().toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    static void writeArrayToFile(String[] dataArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (String data : dataArray) {
                writer.write(data);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
