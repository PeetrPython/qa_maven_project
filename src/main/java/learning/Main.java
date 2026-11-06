package learning;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {

    // Счётчик ошибок создания тестов — общий для всех вызовов tryAdd
    private static int errorCount = 0;

    public static void main(String[] args) {

        // === 1. СОЗДАНИЕ ТЕСТОВ через learning.TestSuite ===
        // learning.TestSuite инкапсулирует List<learning.BaseTest> и логику запуска/статистики —
        // main больше не работает со списком напрямую, а вызывает методы suite
        TestSuite suite = new TestSuite();

        suite.addTest(new UiTest("Проверка окна", "Chrome"));
        suite.addTest(new UiTest("Новая проверка", "Opera"));

        // tryAdd безопасно создаёт тест: если конструктор бросает learning.TestExecutionException,
        // тест просто не добавляется в suite, а ошибка логируется и считается
        tryAdd(suite, () -> new ApiTest("Ответ 404", "api/apiv1/getVolium")); // невалидный endpoint (без "/")
        suite.addTest(new ApiTest("Ответ 200", "/api/apiv1/getVolium"));
        suite.addTest(new RetryableTest("Количество", 4));
        tryAdd(suite, () -> new RetryableTest("Количество", -1)); // невалидный maxRetries (отрицательный)

        // Сохраняем список один раз, чтобы не дёргать suite.getTests() многократно
        List<BaseTest> tests = suite.getTests();

        // === 2. ЗАПУСК ВСЕХ ТЕСТОВ И СТАТИСТИКА через learning.TestSuite ===
        // Раньше это было: ручной цикл for + Map<String,String> + ручной подсчёт passedCount.
        // Теперь learning.TestSuite делает то же самое внутри себя через Stream API.
        suite.runAll();
        long passedCount = suite.countByStatus("PASSED");
        System.out.println("Passed: " + passedCount);

        // === 3. УНИКАЛЬНЫЕ БРАУЗЕРЫ через Set + instanceof ===
        // Set автоматически отбрасывает дубликаты браузеров
        Set<String> uniqueBrowsers = new HashSet<>();
        for (BaseTest test : tests) {
            if (test instanceof UiTest) {
                UiTest uiTest = (UiTest) test;
                uniqueBrowsers.add(uiTest.getBrowserName());
            }
        }
        System.out.println("Уникальные браузеры: " + uniqueBrowsers);

        // === 4. ВЫВОД СТАТУСОВ ВСЕХ ТЕСТОВ через Map.Entry ===
        Map<String, String> testStatuses = new HashMap<>();
        for (BaseTest test : tests) {
            testStatuses.put(test.getTestName(), test.getStatus());
        }
        for (Map.Entry<String, String> entry : testStatuses.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // === 5. GENERICS: learning.TestContainer<T extends learning.BaseTest> ===
        // Контейнер, ограниченный только классами-наследниками learning.BaseTest
        UiTest myTest = new UiTest("Login test", "Chrome");
        TestContainer<UiTest> container = new TestContainer<>(myTest);
        container.runAndLog();
        System.out.println(container.getLogs());

        // === 6. GENERICS: learning.TestResult<T> ===
        // Универсальный контейнер "данные + признак успеха"
        TestResult<String> result = new TestResult<>("Тест прошёл успешно", true);
        System.out.println(result.getData() + " -> " + result.isSuccess());

        // === 7. LAMBDA: Predicate / Supplier / Consumer ===
        Predicate<BaseTest> isFailed = test -> !test.getStatus().equals("PASSED");
        Supplier<String> greeting = () -> "Тесты запущены!";
        Consumer<BaseTest> printTestInfo = test ->
                System.out.println(test.getTestName() + " -> " + test.getStatus());

        System.out.println(greeting.get());
        printTestInfo.accept(tests.get(0));

        // === 8. STREAM API: filter / map / count / toList ===
        long failedCount = tests.stream().filter(isFailed).count();
        List<String> testNames = tests.stream().map(BaseTest::getTestName).toList();

        System.out.println("Failed count: " + failedCount);
        System.out.println("Все имена тестов: " + testNames);

        // === 9. РАБОТА С ФАЙЛАМИ: config.properties ===
        Properties config = new Properties();
        try (FileInputStream inputStream = new FileInputStream("config-stage.properties")) {
            config.load(inputStream);
            System.out.println("Base URL: " + config.getProperty("base.url"));
        } catch (IOException e) {
            System.out.println("Не удалось прочитать конфиг: " + e.getMessage());
        }

        // === 10. РАБОТА С ФАЙЛАМИ: tests.csv ===
        // Тесты из CSV собираем в отдельный learning.TestSuite, чтобы не мешать со статистикой выше
        TestSuite csvSuite = new TestSuite();
        try (BufferedReader reader = new BufferedReader(new FileReader("tests.csv"))) {
            reader.readLine(); // пропускаем заголовок
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                csvSuite.addTest(new UiTest(parts[0], parts[1]));
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения CSV: " + e.getMessage());
        }

        csvSuite.runAll();
        csvSuite.getTests().forEach(printTestInfo::accept);

        // === ИТОГ ===
        System.out.println("Всего ошибок при создании тестов: " + errorCount);
    }

    // Рефакторинг дублирующихся try/catch: раньше на КАЖДЫЙ потенциально невалидный
    // тест писался отдельный try/catch блок с одинаковым телом. Теперь вся эта
    // логика в одном месте: Supplier<learning.BaseTest> откладывает создание объекта
    // до момента вызова creator.get() — именно тогда, внутри try, и может
    // произойти исключение из конструктора.
    private static void tryAdd(TestSuite suite, Supplier<BaseTest> creator) {
        try {
            suite.addTest(creator.get());
        } catch (TestExecutionException e) {
            errorCount++;
            System.out.println("Не удалось создать тест: " + e.getMessage());
        }
    }
}