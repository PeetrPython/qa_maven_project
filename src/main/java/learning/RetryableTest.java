package learning;

public class RetryableTest extends BaseTest implements Retryable, Loggable {
    private int maxRetries;

    public RetryableTest(String testName, int maxRetries) {
        super(testName);
        if (maxRetries < 0) {
            throw new TestExecutionException("maxRetries не может быть отрицательным");
        }
        this.maxRetries = maxRetries;
    }

    public int getMaxRetries() {
        return maxRetries;
    }


    @Override
    public void run() {
        TestLogger logger = new TestLogger();
        logger.log("Starting: " + getTestName());
        logger.log("Config loaded", "DEBUG");
        logger.log("Retry attempt", maxRetries);
        markAsPassed();
    }


    @Override
    public void retry() {
        System.out.println("Повторяюсь");
    }

    @Override
    public int getMaxAttempts() {
        return maxRetries;
    }

    @Override
    public void logResult() {
        System.out.println("[RESULT] " + getTestName() +" " + getStatus());
    }


}