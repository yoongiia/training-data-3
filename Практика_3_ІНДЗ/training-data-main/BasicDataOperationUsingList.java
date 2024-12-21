import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList надає методи для виконання основних операцiй з даними типу LocalDateTime.
 * 
 * <p>Цей клас зчитує данi з файлу "list/LocalDateTime.data", сортує їх та виконує пошук значення в масивi та списку.</p>
 * 
 * <p>Основнi методи:</p>
 * <ul>
 *   <li>{@link #main(String[])} - Точка входу в програму.</li>
 *   <li>{@link #doDataOperation()} - Виконує основнi операцiї з даними.</li>
 *   <li>{@link #sortArray()} - Сортує масив LocalDateTime.</li>
 *   <li>{@link #searchArray()} - Виконує пошук значення в масивi LocalDateTime.</li>
 *   <li>{@link #findMinAndMaxInArray()} - Знаходить мiнiмальне та максимальне значення в масивi LocalDateTime.</li>
 *   <li>{@link #sortList()} - Сортує список LocalDateTime.</li>
 *   <li>{@link #searchList()} - Виконує пошук значення в списку LocalDateTime.</li>
 *   <li>{@link #findMinAndMaxInList()} - Знаходить мiнiмальне та максимальне значення в списку LocalDateTime.</li>
 * </ul>
 * 
 * <p>Конструктор:</p>
 * <ul>
 *   <li>{@link #BasicDataOperationUsingList(String[])} - iнiцiалiзує об'єкт з значенням для пошуку.</li>
 * </ul>
 * 
 * <p>Константи:</p>
 * <ul>
 *   <li>{@link #PATH_TO_DATA_FILE} - Шлях до файлу з даними.</li>
 * </ul>
 * 
 * <p>Змiннi екземпляра:</p>
 * <ul>
 *   <li>{@link #dateTimeValueToSearch} - Значення LocalDateTime для пошуку.</li>
 *   <li>{@link #dateTimeArray} - Масив LocalDateTime.</li>
 *   <li>{@link #dateTimeList} - Список LocalDateTime.</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperationUsingList "2024-03-16T00:12:38Z"
 * }
 * </pre>
 */
public class BasicDataOperationUsingList {
    static final String PATH_TO_DATA_FILE = "list/LocalDateTime.data";

    LocalDateTime dateTimeValueToSearch;
    LocalDateTime[] dateTimeArray;
    List<LocalDateTime> dateTimeList;

    public static void main(String[] args) {  
        BasicDataOperationUsingList basicDataOperationUsingList = new BasicDataOperationUsingList(args);
        basicDataOperationUsingList.doDataOperation();
    }

    /**
     * Конструктор, який iнiцiалiзує об'єкт з значенням для пошуку.
     * 
     * @param args Аргументи командного рядка, де перший аргумент - значення для пошуку.
     */
    BasicDataOperationUsingList(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку");
        }

        String searchValue = args[0];
        dateTimeValueToSearch = LocalDateTime.parse(searchValue, DateTimeFormatter.ISO_DATE_TIME);

        dateTimeArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        dateTimeList = new ArrayList<>(Arrays.asList(dateTimeArray));
    }

    /**
     * Виконує основнi операцiї з даними.
     * 
     * Метод зчитує масив та список об'єктiв LocalDateTime з файлу, сортує їх та виконує пошук значення.
     */
    void doDataOperation() {
        // операцiї з масивом дати та часу
        searchArray();
        findMinAndMaxInArray();

        sortArray();
        
        searchArray();
        findMinAndMaxInArray();

        // операцiї з ArrayList
        searchList();
        findMinAndMaxInList();

        sortList();

        searchList();
        findMinAndMaxInList();

        // записати вiдсортований масив в окремий файл
        Utils.writeArrayToFile(dateTimeArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв LocalDateTime та виводить початковий i вiдсортований масиви.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    void sortArray() {
        long startTime = System.nanoTime();

        Arrays.sort(dateTimeArray);

        Utils.printOperationDuration(startTime, "сортування масиву дати i часу");
    }

    /**
     * Метод для пошуку значення в масивi дати i часу.
     */
    void searchArray() {
        long startTime = System.nanoTime();

        int index = Arrays.binarySearch(this.dateTimeArray, dateTimeValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в масивi дати i часу");

        if (index >= 0) {
            System.out.println("Значення '" + dateTimeValueToSearch + "' знайдено в масивi за iндексом: " + index);
        } else {
            System.out.println("Значення '" + dateTimeValueToSearch + "' в масивi не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в масивi дати i часу.
     */
    void findMinAndMaxInArray() {
        if (dateTimeArray == null || dateTimeArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        LocalDateTime min = dateTimeArray[0];
        LocalDateTime max = dateTimeArray[0];

        for (LocalDateTime dateTime : dateTimeArray) {
            if (dateTime.isBefore(min)) {
                min = dateTime;
            }
            if (dateTime.isAfter(max)) {
                max = dateTime;
            }
        }

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної дати i часу в масивi");

        System.out.println("Мiнiмальне значення в масивi: " + min);
        System.out.println("Максимальне значення в масивi: " + max);
    }

    /**
     * Шукає задане значення дати i часу в ArrayList дати i часу.
     */
    void searchList() {
        long startTime = System.nanoTime();

        int index = Collections.binarySearch(this.dateTimeList, dateTimeValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в ArrayList дати i часу");        

        if (index >= 0) {
            System.out.println("Значення '" + dateTimeValueToSearch + "' знайдено в ArrayList за iндексом: " + index);
        } else {
            System.out.println("Значення '" + dateTimeValueToSearch + "' в ArrayList не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в ArrayList дати i часу.
     */
    void findMinAndMaxInList() {
        if (dateTimeList == null || dateTimeList.isEmpty()) {
            System.out.println("ArrayList порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        LocalDateTime min = Collections.min(dateTimeList);
        LocalDateTime max = Collections.max(dateTimeList);

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної дати i часу в ArrayList");

        System.out.println("Мiнiмальне значення в ArrayList: " + min);
        System.out.println("Максимальне значення в ArrayList: " + max);
    }

    /**
     * Сортує ArrayList об'єктiв LocalDateTime та виводить початковий i вiдсортований списки.
     * Вимiрює та виводить час, витрачений на сортування списку в наносекундах.
     */
    void sortList() {
        long startTime = System.nanoTime();

        Collections.sort(dateTimeList);

        Utils.printOperationDuration(startTime, "сортування ArrayList дати i часу");
    }
}

/**
 * Клас Utils мiститить допомiжнi методи для роботи з даними типу LocalDateTime.
 */
class Utils {
    /**
     * Виводить час виконання операцiї в наносекундах.
     * 
     * @param startTime Час початку операцiї в наносекундах.
     * @param operationName Назва операцiї.
     */
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n>>>>>>>>> Час виконання операцiї '" + operationName + "': " + duration + " наносекунд");
    }

    /**
     * Зчитує масив об'єктiв LocalDateTime з файлу.
     * 
     * @param pathToFile Шлях до файлу з даними.
     * @return Масив об'єктiв LocalDateTime.
     */
    static LocalDateTime[] readArrayFromFile(String pathToFile) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime[] tempArray = new LocalDateTime[1000];
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                LocalDateTime dateTime = LocalDateTime.parse(line, formatter);
                tempArray[index++] = dateTime;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocalDateTime[] finalArray = new LocalDateTime[index];
        System.arraycopy(tempArray, 0, finalArray, 0, index);

        return finalArray;
    }

    /**
     * Записує масив об'єктiв LocalDateTime у файл.
     * 
     * @param dateTimeArray Масив об'єктiв LocalDateTime.
     * @param pathToFile Шлях до файлу для запису.
     */
    static void writeArrayToFile(LocalDateTime[] dateTimeArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (LocalDateTime dateTime : dateTimeArray) {
                writer.write(dateTime.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}