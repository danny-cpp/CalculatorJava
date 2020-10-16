package calculator;

import calculator.exception.RuntimeError;

import java.util.*;

import static java.util.Map.entry;

/**
 * This interface show the operation precedence. i.e. multiply
 * and divide has higher precedence than add and subtract
 *
 */
interface operator_precedence {
    Map<String, Integer> precedence = Map.ofEntries(
            entry("add", 0),
            entry("minus", 0),
            entry("div", 1),
            entry("mult", 1),
            entry("exp", 2)
    );
}

/**
 * Calculate using shunting yard algorithm
 */
public class ShuntingYard implements operator_precedence {
    Stack<Token> operator_stack = new Stack<>();
    Queue<Token> output_queue = new LinkedList<>();
    HashMap<String, Double> var_map = new HashMap<>();

    protected void RPN(List<Token> input_list) throws RuntimeError {
        // Reading tokens
        for (Token t : input_list) {

            // If it is a number, add to the queue
            if (t.getType().equals("number")) {
                output_queue.add(t);
            }

            // If it is a variable, treat it like a number
            else if (t.getType().equals("var")) {
                output_queue.add(t);
            }

            // If it is a loader, push on to the stack
            else if (t.getType().equals("let")) {
                operator_stack.add(t);
            }

            // If it is an binary operator
            else if (t.getType().equals("bin_op")) {
                // while the stack is not empty
                while (!operator_stack.empty() &&

                        // while there is an operator on top of the stack
                        operator_stack.peek().getType().equals("bin_op") &&

                        // and the operator has higher precedence than the current
                        precedence.get(operator_stack.peek().getOperation()) >= precedence.get(t.getOperation()) &&

                        // and the operator on top of the stack is not an open bracket
                        !operator_stack.peek().getOperation().equals("open")
                        ) {

                    // Move the operator to the output queue
                    Token moving_temp = operator_stack.peek();
                    output_queue.add(moving_temp);
                    operator_stack.pop();
                }

                // Push it on top of the stack
                operator_stack.add(t);
            }

            // If it is an open bracket, push it on top of the stack
            else if (t.getOperation().equals("open")) {
                operator_stack.add(t);
            }

            // If it is a close bracket
            else if (t.getOperation().equals("close")) {

                // While the top of the stack is not a open bracket
                while (!operator_stack.peek().getOperation().equals("open")) {
                    // Move the operator to the output queue
                    Token moving_temp = operator_stack.peek();
                    output_queue.add(moving_temp);
                    operator_stack.pop();
                }

                // If we see an open bracket, pop it off
                if (operator_stack.peek().getOperation().equals("open")) {
                    operator_stack.pop();
                }

            }
        }

        // After done iterating, if there is any other operator on the stack,
        // move it to the output queue
        while (!operator_stack.empty()) {
            Token tmp = operator_stack.peek();
            output_queue.add(tmp);
            operator_stack.pop();
        }

        // for (Token t : output_queue) {
        //     System.out.println("value is: " + t.getOperation());
        // }
    }


    /**
     * This function evaluate a queue in RPN and produce the output
     * @return
     */
    public Double evaluate_RPN() throws RuntimeError {
        // Prepare the output stack
        Stack<Double> result = new Stack<>();

        // Evaluate the queue
        while (!output_queue.isEmpty()) {
            Token current_token = output_queue.peek();

            // If it is a number, put in it the stack
            if (current_token.getType().equals("number")) {
                result.add((Double) current_token.getOperation());
            }

            // If it is a variable, look it up, and beware null value, which indicates
            // an uninitialized variable.
            else if (current_token.getType().equals("var")) {
                // Check null
                if (var_map.get(current_token.getOperation()) == null) {
                    throw new RuntimeError((String) current_token.getOperation());
                }
                else {
                    Double tmp_val = var_map.get(current_token.getOperation());
                    result.add(tmp_val);
                }
            }

            // If it is a function (in this case, loader), load the stack top
            else if (current_token.getType().equals("let")) {
                Double num_retrieved = result.peek();
                var_map.put((String) current_token.getOperation(), num_retrieved);
                // result.pop();
            }

            // If it is a binary operator, pop 2 of the value and calculate
            else if (current_token.getType().equals("bin_op")) {
                Double value_left = result.peek();
                result.pop();
                Double value_right =result.peek();
                result.pop();

                // If it is add
                if (current_token.getOperation().equals("add")) {
                    Double tmp = value_left + value_right;
                    result.add(tmp);
                }

                // If it is subtract
                if (current_token.getOperation().equals("minus")) {
                    Double tmp = value_right - value_left;
                    result.add(tmp);
                }

                // If it is multiply
                if (current_token.getOperation().equals("mult")) {
                    Double tmp = value_left * value_right;
                    result.add(tmp);
                }

                // If it is division
                if (current_token.getOperation().equals("div")) {
                    Double tmp = value_right / value_left;
                    result.add(tmp);
                }

                // If it is exponent
                if (current_token.getOperation().equals("exp")) {
                    Double tmp = Math.pow(value_right, value_left);
                    result.add(tmp);
                }

            }

            // Remove the object from output queue
            output_queue.remove();
        }
        return result.peek();
    }



    public static void main(String[] args) {

    }
}


// while (
//         (there is an operator at the top of the operator stack)
//         and (
//                 (the operator at the top of the operator stack has greater precedence)
//                 or (the operator at the top of the operator stack has equal precedence and the token is left associative))
//         and (the operator at the top of the operator stack is not a left parenthesis))


// while (!output_queue.isEmpty()) {
//         System.out.print(output_queue.peek().getType() + " value is ");
//         System.out.println(output_queue.peek().getOperation());
//         output_queue.remove();
//         }