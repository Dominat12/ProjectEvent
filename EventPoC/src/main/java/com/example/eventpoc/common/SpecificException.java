package com.example.eventpoc.common;


/**
 *
 *
 */
public class SpecificException extends  RuntimeException{

    /**
     * Ist der Grund für die Exception lösbar, sodass keine Probleme dadurch entstehen
     */
    private boolean treatable;

    /**
     * Relevanz &Tragweite der Exception
     */
    private ExceptionLevel level;

    public static class UserNotFoundException extends SpecificException
    {
        private static final long serialVersionUID = 4235225697094262603L;
        public UserNotFoundException(String msg) {
            super(msg);
        }
    }

    public static class UserNotPersistedException extends SpecificException
    {
        private static final long serialVersionUID = 4235225693454262603L;
        public UserNotPersistedException(String msg) {
            super(msg);
        }
    }

    public SpecificException(String message){
        super(message);
    }

    public boolean isTreatable() {
        return treatable;
    }

    public void setTreatable(boolean treatable) {
        this.treatable = treatable;
    }

    public ExceptionLevel getLevel() {
        return level;
    }

    public void setLevel(ExceptionLevel level) {
        this.level = level;
    }
}
