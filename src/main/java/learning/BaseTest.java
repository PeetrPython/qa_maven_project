package learning;

public abstract class BaseTest {

    private String testName;
    private String status;

    public BaseTest (String testName) {
        this.testName = testName;
        this.status = "NOT_RUN";
    }

    public void markAsPassed() {
        this.status = "PASSED";
    }

    public String getTestName() {
        return testName;
    }

    public String getStatus() {
        return status;
    }

    public abstract void run();
}
