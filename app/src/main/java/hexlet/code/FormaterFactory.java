package hexlet.code;

public class FormaterFactory {
    public static Formater create(String format) {
        if (format.equals("stylish")) {
            return  new StylishFormater();
        } else {
            return  new StylishFormater();
        }
    }

}
