package suai.lab06.excpt;

public class FormattedInputException extends RuntimeException {

    private String errorStr;

    public FormattedInputException (String string) {
        this.errorStr = string;
        System.out.println("This is a FormattedInputException...");
    }

    public void getMassage() {
        System.err.println("Error: " + errorStr);
    }
}