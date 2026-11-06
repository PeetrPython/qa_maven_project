package learning;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RetryableTestTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, -5, -100})
    void invalidEndpointsShouldThrowException(int badMaxRetries) {
        assertThrows(TestExecutionException.class, () -> {
            new RetryableTest("bad maxretries", badMaxRetries);
        });
    }
}