package calculator.exception;

public class RuntimeError extends Exception {
    public RuntimeError(String message) {
        super("'" + message + "' undefined");
    }
}
