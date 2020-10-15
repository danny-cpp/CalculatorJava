package calculator;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.*;

/**
 * Contains important regex code for fundamental building blocks
 */
interface fundamentals {
    String let = "(let)";
    String optional_blank = "\\s*";
    String equal_sign = "(=)";
    String var = "[a-zA-Z]" + optional_blank;
    String open_br = "\\("  + optional_blank;
    String close_br = "\\)" + optional_blank;
    String operators = "[\\+, \\-, \\*, \\/, \\^]" + optional_blank;
    String numbers = "([0-9]+)" + optional_blank;
}


/**
 * Collection of lexer and parser of mathematical expression
 */
public class Parser implements fundamentals {
    Stack<Token> operation_stack = new Stack<>();
    Queue<Token> output_queue = new ArrayDeque<>();
    List<Token> input_tokens = new LinkedList<>();
    Field[] fundamentalsFields = fundamentals.class.getFields();
    protected void mathParse(String s) throws IllegalAccessException {
        // Remove blanks at the head
        s = s.trim();
        for (Field f : fundamentalsFields) {
            String x = (String) f.get(this);
            System.out.println(x);
        }

        while (m.find()) {
            System.out.print(text.substring(m.start(), m.end()) + " - ");
        }
    }

    public static void main(String[] args) throws IllegalAccessException {
        Parser a = new Parser();
        a.mathParse("sdsd");
    }
}
