package learning;

import java.util.ArrayList;
import java.util.List;

public class TestSuite extends Main {
    private List<BaseTest> tests = new ArrayList<>();

    public void addTest(BaseTest test) {
        tests.add(test);
    }

    public void runAll() {
        tests.stream().forEach(BaseTest::run);
    }

    public long countByStatus(String status) {
        return tests.stream()
                .filter(test -> test.getStatus().equals(status))
                .count();
    }

    public List<BaseTest> getTests() {
        return tests;
    }
}

