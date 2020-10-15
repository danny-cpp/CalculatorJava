package calculator;


import java.util.Map;

import static java.util.Map.entry;


/**
 * Token objects hold the type of an object in parsed from a mathematical
 * equation. The object can have type: <br>
 *     <ul>Binary operator</ul>
 *     <ul>Unary operator</ul>
 *     <ul>Number</ul>
 *
 */
public class Token {
    private String type;
    private Object operation;
    Token(String in_type, Object in_operation) {
        type = in_type;
        operation = in_operation;
    }

    /**
     * Get token type
     * @return Token indicator: <br>
     *     <ul>Binary operator</ul>
     *     <ul>Unary operator</ul>
     *     <ul>Number</ul>
     */
    protected String getType() {
        return type;
    }

    /**
     * Return the operation of a operational symbol, or the value of
     * a number
     * @return
     */
    protected Object getOperation() {
        return operation;
    }
}
