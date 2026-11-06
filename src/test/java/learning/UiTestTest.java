package learning;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UiTestTest {

    private UiTest test;

    @BeforeEach
    void setUp() {
        test = new UiTest("Login test", "Chrome");
    }

    @Test
    void newTestShouldHaveStatusNotRun() {
        assertEquals("NOT_RUN", test.getStatus());
    }

    @Test
    void afterRunStatusShouldBePassed() {
        test.run();
        assertEquals("PASSED", test.getStatus());
    }

    @Test
    void browserNameShouldBeCorrect() {
        assertEquals("Chrome", test.getBrowserName());
    }
}