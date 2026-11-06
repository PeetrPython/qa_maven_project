package utils;

import exceptions.TestDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private final Properties properties = new Properties();
    private static final Logger log = LoggerFactory.getLogger(Config.class);

    public Config(String resourceName) {
        try(InputStream in = Config.class.getClassLoader().getResourceAsStream(resourceName)) {
            if(in == null) {
                throw new TestDataException("Не найден файл настроек: " + resourceName);
            }
            properties.load(in);
            } catch (IOException e) {
            throw new TestDataException("Не удалось прочитать " + resourceName, e);
        }

    }

    public String get(String key) {
        String fromSystem = System.getProperty(key);
        if (fromSystem != null) {
            return fromSystem;
        }
        String fromFile = properties.getProperty(key);
        if (fromFile == null) {
            throw new TestDataException("Нет ключа: " + key);
        }
        return fromFile;
    }

    public static Config load() {
        String env = System.getProperty("env", "dev");
        String file = "config-" + env + ".properties";
        log.info("Окружение: {}, файл настроек: {}", env, file);
        return new Config(file);
    }
}
