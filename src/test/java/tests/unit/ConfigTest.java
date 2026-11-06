package tests.unit;

import exceptions.TestDataException;
import org.junit.jupiter.api.AfterEach;
import utils.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

private Config config;

    @BeforeEach
    void resetFlags() {
        config = new Config("test-config.properties");
        System.clearProperty("env");
        System.clearProperty("user.login");
    }

    @AfterEach
    void cleanFlags() {
        System.clearProperty("env");
        System.clearProperty("user.login");
    }

    @Test
    void getShouldReturnValueForExistingKey() {
        assertEquals("demo_user", config.get("test.login"));
    }

    @Test
    void getShouldThrowForMissingKey() {
        String missingKey = "no.such.key";
        TestDataException e = assertThrows(TestDataException.class, () -> {
            config.get(missingKey);});
        assertTrue(e.getMessage().contains(missingKey));
    }

    @Test
    void constructorShouldThrowForMissingFile() {
        String missingFile = "no-such-file.properties";
        TestDataException e = assertThrows(TestDataException.class, () -> {
            new Config(missingFile);});
        assertTrue(e.getMessage().contains(missingFile));
    }

    @Test
    void loadWithoutFlagShouldUseDevEnvironment() {
        String defaultEnvironment = Config.load().get("env.name");
        assertEquals("dev", defaultEnvironment);
    }

    @Test
    void loadWithEnvFlagShouldUseChosenEnvironment() {
        System.setProperty("env", "stage");
        String chosenEnviroment = Config.load().get("env.name");
        assertEquals("stage", chosenEnviroment);
    }

    @Test
    void systemFlagShouldOverrideFileValue() {
        System.setProperty("user.login", "override");
        String flagOverride = Config.load().get("user.login");
        assertEquals("override", flagOverride);
    }

    @Test
    void loadWithUnknownEnvironmentShouldThrowExceptionNamingFile() {
        System.setProperty("env", "nope");
        String expectedFile = "config-nope.properties";
        TestDataException e = assertThrows(TestDataException.class,
                () -> Config.load());
        assertTrue(e.getMessage().contains(expectedFile));
    }
}
