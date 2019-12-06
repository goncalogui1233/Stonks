package exceptions;

public class EmptyGoalListException extends Exception {

    public EmptyGoalListException() {
        super("The authenticated user doesn't have any goal");
    }
}
