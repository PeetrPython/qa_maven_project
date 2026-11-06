package learning;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ApiTestTest {

    private ApiTest test;

    @BeforeEach
    void setUp() {
        test = new ApiTest("test1", "/xxxx/xxxx/");
    }

    @Test
    void newTestShouldHaveStatusNotRun() {
        assertEquals("NOT_RUN", test.getStatus());
    }

    @Test
    void afterTestShouldHaveStatusPassed() {
        test.run();
        assertEquals("PASSED", test.getStatus());
    }

    @Test
    void apiTestShouldThrowExceptionForInvalidEndpoint() {
        assertThrows(TestExecutionException.class, () -> {
            new ApiTest("Bad test", "no-slash");
        });
    }

    // Новый параметризованный тест — добавляется сюда же, рядом с остальными:
    @ParameterizedTest
    @ValueSource(strings = {"no-slash", "api/users", "another-bad-one"})
    void invalidEndpointsShouldThrowException(String badEndpoint) {
        assertThrows(TestExecutionException.class, () -> {
            new ApiTest("Bad test", badEndpoint);
        });
    }

    @ParameterizedTest
    @CsvSource({"test1, testBrowser1", "test2, testBrowser2" })
    void validationBrowserTest(String testName,String browser) {
            UiTest test =  new UiTest(testName,browser);
        assertEquals(testName, test.getTestName());
        assertEquals(browser, test.getBrowserName());

    }
}
