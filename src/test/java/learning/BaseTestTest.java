package learning;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BaseTestTest {

    @Test
    void newTestShouldHaveStatusNotRun() {
        UiTest test = new UiTest("Login test", "Chrome");
        assertEquals("NOT_RUN", test.getStatus());
    }

    @Test
    void afterRunStatusShouldBePassed() {
        UiTest test = new UiTest("Login test", "Chrome");
        test.run();
        assertEquals("PASSED", test.getStatus());
    }

}