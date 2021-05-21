package suai.lab06.excpt;

public class EncodingConverterException extends RuntimeException {

    private String errorStr;

    public EncodingConverterException (String string) {
        this.errorStr = string;
        System.out.println("This is a EncodingConverterException...");
    }

    public void getMassage() {
        System.err.println("Error: " + errorStr);
    }
}