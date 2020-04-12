package exceptions;

public class ExistingLessonException extends Exception {

    public ExistingLessonException() {
    }

    public ExistingLessonException(String message) {
        super(message);
    }

    public ExistingLessonException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistingLessonException(Throwable cause) {
        super(cause);
    }

    public ExistingLessonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
