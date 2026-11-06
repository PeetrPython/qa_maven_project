package learning;

public interface Retryable {
    void retry();
    int getMaxAttempts();

}
