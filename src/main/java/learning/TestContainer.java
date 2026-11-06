package learning;

import java.util.ArrayList;
import java.util.List;

public class TestContainer <T extends BaseTest> {

    private T test;
    private List<String> logs = new ArrayList<>();

    public TestContainer(T test) {
        this.test = test;
    }

    public TestContainer(String loginTest) {
    }

    public void runAndLog() {
        test.run();
        logs.add("Executed: " + test.getStatus());
    }

    public T getTest() {
        return test;
    }

    public List<String> getLogs() {
        return logs;
    }
}

