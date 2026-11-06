package learning;

public class ApiTest extends BaseTest {
    private String endpoint;

    public ApiTest(String testName, String endpoint) {
        super(testName);
        if (!endpoint.startsWith("/")) {
            throw new TestExecutionException("В апи отсутсвует символ '/' в начале " + endpoint);
        }
        this.endpoint = endpoint;
    }

    public void run() {
        System.out.println("Running API test: " + getTestName() + " calling " + endpoint);
        markAsPassed();
    }

    public String getEndpoint() {
        return endpoint;
    }
}
