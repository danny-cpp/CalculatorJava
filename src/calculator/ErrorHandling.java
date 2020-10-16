package calculator;

import calculator.exception.RuntimeError;
import calculator.exception.SyntaxError;


public class ErrorHandling implements errorHelpers {
    /**
     * This function regulates the brackets' behavior
     * @param s math expression
     * @return Bracket syntax approval
     * @throws SyntaxError when there is mismatched bracket pairs
     */
    protected static boolean bracketMatcher(String s) throws SyntaxError {
        // Start with no open bracket
        int open_bracket = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                open_bracket++;
            }
            // If we see a close bracket, reduce the open bracket number
            if (s.charAt(i) == ')') {
                open_bracket--;
            }
            // If the count drops below 0, there is a mismatch
            if (open_bracket < 0) {
                throw new SyntaxError("mismatched bracket");
            }
        }
        // After iterate through the string, total brackets should be 0
        if (open_bracket > 0) {
            throw new SyntaxError("')' expected");
        }
        return true;
    }

    protected static boolean structureFSM(String s) {

    }


    public static void main(String[] args) throws SyntaxError {
        String a = "1+ (2*3";
        System.out.println(bracketMatcher(a));
    }
}
