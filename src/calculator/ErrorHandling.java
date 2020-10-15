package calculator;

public class ErrorHandling {
    // This function regulates the brackets' behavior
    protected static boolean bracketMatcher(String s) {
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
                return false;
            }
        }
        // After iterate through the string, total brackets should be 0
        if (open_bracket == 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String a = "())(";
        System.out.println(bracketMatcher(a));
    }
}
