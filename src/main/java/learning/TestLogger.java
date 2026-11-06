package learning;

public class TestLogger {

    public void log(String message) {
        System.out.println("[INFO] " + message);
    }

    public void log(String message, String level) {
        System.out.println("[" + level + "] " + message);
    }

    public void log(String message, int attemptNumber) {
        System.out.println("[ATTEMPT " + attemptNumber + "] " + message);
    }
}
