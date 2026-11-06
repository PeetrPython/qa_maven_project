package learning;

public class UiTest extends BaseTest {
    private String browserName;

    public UiTest (String testName, String browserName) {
        super(testName);
        this.browserName = browserName;
    }

    public void run() {
        System.out.println("Running UI test: " + getTestName() + " in " + browserName );
        markAsPassed();
    }

    public String getBrowserName() {
        return browserName;
    }

}
