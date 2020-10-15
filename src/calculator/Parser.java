package calculator;

import calculator.exception.RuntimeError;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser class turns math object to a structured expression. The algorithm is Shunting-
 * Yard with RPN (Reverse Polish Notation)
 */
public class Parser {

    List<Token> input_tokens = new LinkedList<>();
    ShuntingYard RPN_structure = new ShuntingYard();

    /**
     * This function accepting string object that is checked by the lexer, parse
     * and store them into input token stack
     * @param s The mathematical object
     */
    protected void Parsing(String s) {
        s = s.trim();

        // System.out.println("input s is: " + s);

        // If it is a bracket
        if (s.equals("(")) {
            Token tmp = new Token("bracket", "open");
            input_tokens.add(tmp);
            return;
        }
        if (s.equals(")")) {
            Token tmp = new Token("bracket", "close");
            input_tokens.add(tmp);
            return;
        }

        // If it is a number
        try {
            Integer tmp = Integer.parseInt(s);
            Double value = Double.valueOf(tmp);
            Token tmp_num = new Token("number", value);
            input_tokens.add(tmp_num);
            return;
        }
        catch (Exception e) {}

        // If it is a load object (let _ = )
        if (s.charAt(0) == 'l' && s.charAt(1) == 'e') {
            String[] variable_storage = s.split("\\s+");
            Token tmp = new Token("let", variable_storage[1]);
            input_tokens.add(tmp);
            return;
        }

        // If it is a binary operator (+, - , *, /)
        if (s.equals("+")) {
            Token tmp = new Token("bin_op", "add");
            input_tokens.add(tmp);
            return;
        }
        if (s.equals("-")) {
            Token tmp = new Token("bin_op", "minus");
            input_tokens.add(tmp);
            return;
        }
        if (s.equals("*")) {
            Token tmp = new Token("bin_op", "mult");
            input_tokens.add(tmp);
            return;
        }
        if (s.equals("/")) {
            Token tmp = new Token("bin_op", "div");
            input_tokens.add(tmp);
            return;
        }

        // If it is exponent
        if (s.equals("^")) {
            Token tmp = new Token("bin_op", "exp");
            input_tokens.add(tmp);
            return;
        }

        // Check if it is a variable
        Pattern p = Pattern.compile("^[a-zA-Z]$");
        Matcher m = p.matcher(s);
        if (m.find()) {
            Token tmp = new Token("var", s);
            input_tokens.add(tmp);
            return;
        }
    }

    protected void listAll() throws RuntimeError {
        RPN_structure.RPN(input_tokens);

    }

    public static void main(String[] args) {
        Parser a = new Parser();
        a.Parsing("      let           a        =");
        String test = "x";
        System.out.println(test.charAt(0));
        HashMap<String, Double> h = new HashMap<>();
        System.out.println(h.get(0));


    }
}
