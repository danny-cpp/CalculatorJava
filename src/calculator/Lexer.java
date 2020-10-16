package calculator;

import calculator.exception.RuntimeError;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Contains important regex code for fundamental building blocks
 */
interface fundamentals {
    String let = "(let\\s+[a-zA-Z]\\s*)(=)\\s*";
    String var = "[a-zA-Z]([^a-zA-Z]|$)";
    String open_br = "\\(\\s*";
    String close_br = "\\)\\s*";
    String operators = "[\\+, \\-, \\*, \\/, \\^]\\s*";
    String numbers = "([0-9]+)\\s*";
}


/**
 * This interface breaks down fundamental fields further to help user identify
 * errors
 */
interface errorHelpers {
    String let = "(let\\s+)";
    String var = "[a-zA-Z]{1}\\b";
    String open_br = "\\(\\s*";
    String close_br = "\\)\\s*";
    String operators = "[\\+, \\-, \\*, \\/, \\^]\\s*";
    String numbers = "([0-9]+)\\s*";
}


/**
 * Collection of lexer for mathematical expression
 */
public class Lexer implements fundamentals, errorHelpers {

    // Array of the regex fields
    Field[] fundamentalsFields = fundamentals.class.getFields();

    /**
     * The function read in a string mathematical equation and build a structure
     * around it
     * @param s The mathematical expression
     * @throws IllegalAccessException
     * @throws RuntimeError  Runtime error thrown if detect a meaningless expression
     */
    protected void analyze(String s) throws IllegalAccessException, RuntimeError {
        // The core structure is a parsed object
        Parser input = new Parser();

        s = s.trim();

        // The flag that tells that there is something wrong
        Boolean something_wrong;

        // Processing the math argument by consecutively checking its
        // component with regex
        String regex_identifier;
        while (s.length() > 0) {

            // Continuously raise the flag after each successful object check
            something_wrong = true;

            for (Field f : fundamentalsFields) {

                regex_identifier = (String) f.get(this);
                Pattern p = Pattern.compile("^" + regex_identifier);
                Matcher m = p.matcher(s);

                // If we find the legal object, stash it, and trim the math equation
                if (m.find()) {

                    System.out.println(s.substring(m.start(), m.end()));

                    input.Parsing(s.substring(m.start(), m.end()));
                    s = s.substring(m.end(), s.length() );
                    // As long as we find something, nothing's wrong
                    something_wrong = false;
                }
                else {
                    // If we can't find anything, something is wrong

                }

            }

            // If we find anything legal, this block cannot be reached
            if (something_wrong) {
                Field[] errorHelpers = errorHelpers.class.getFields();

                // What if we cannot find a legal object? It means that we hit an illegal
                // object. To help the user pinpoint the illegal object be keep looking
                // for the next legal object and tell the user the part they did wrong.
                // This block will not be executed unless something goes wrong
                for (Field f2 : errorHelpers) {
                    regex_identifier = (String) f2.get(this);
                    Pattern p2 = Pattern.compile(regex_identifier);
                    Matcher m2 = p2.matcher(s);
                    if (m2.find()) {
                        // Show the user what is wrong
                        String invalid_syntax_clause = s.substring(0, m2.start()).trim();
                        throw new RuntimeError(invalid_syntax_clause);
                    }

                }
                // If we cant find anything right, means everything else is wrong
                // Report it
                String invalid_syntax_clause = s.trim();
                throw new RuntimeError(invalid_syntax_clause);

            }

        }
        input.listAll();

    }

    public static void main(String[] args) throws RuntimeError, IllegalAccessException {
        Lexer a = new Lexer();
        // a.analyze("(let x = 2) ^ (let y =3) + x");
        // a.analyze("1+ (let x =1 ) + (let y =2 ) + ( 1+ x ) * ( 1+ y ) - (let x = 2 ) + 3");
        // a.analyze("1+ (let x =1 ) + (let y =2 ) + ( 1+ x ) * ( 1+ y ) - (let x = 2 ) - (let y = 1 ) - x" );
        a.analyze("5 - 1 -1");
    }

}
