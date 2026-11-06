package learning;

public class TestResult <T> {

    private boolean success;
    private T data;

    public TestResult (T data, boolean success){
         this.success = success;
         this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData(){
        return data;
    }

}
