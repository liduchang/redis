/**
 * @author liduchang
 * @version 1.0
 * @data 2023/8/3 17:54
 */
import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;
    private double result;

    public Calculator() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        result = 0.0;
    }

    public void add(double x, double y) {
        double res = x + y;
        Command command = new AddCommand(x, y);
        executeCommand(command, res);
    }

    public void subtract(double x, double y) {
        double res = x - y;
        Command command = new SubtractCommand(x, y);
        executeCommand(command, res);
    }

    public void multiply(double x, double y) {
        double res = x * y;
        Command command = new MultiplyCommand(x, y);
        executeCommand(command, res);
    }

    public void divide(double x, double y) {
        if (y != 0) {
            double res = x / y;
            Command command = new DivideCommand(x, y);
            executeCommand(command, res);
        } else {
            throw new IllegalArgumentException("param is invaliable");
        }
    }

    /**
     * 撤销
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            redoStack.push(command);
            result = command.undo(result);
            System.out.println("Undo: " + command);
            System.out.println("Result: " + result);
        } else {
            System.out.println("Nothing to undo!");
        }
    }

    /**
     * 重做
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            undoStack.push(command);
            result = command.execute(result);
            System.out.println("Redo: " + command);
            System.out.println("Result: " + result);
        } else {
            System.out.println("Nothing to redo!");
        }
    }

    private void executeCommand(Command command, double res) {
        undoStack.push(command);
        redoStack.clear();
        result = res;
        System.out.println("Execute: " + command);
        System.out.println("Result: " + result);
    }

    private abstract class Command {
        protected double value;

        public Command(double value) {
            this.value = value;
        }

        public abstract double execute(double currentValue);

        public abstract double undo(double currentValue);
    }

    private class AddCommand extends Command {
        private double operand;

        public AddCommand(double value, double operand) {
            super(value);
            this.operand = operand;
        }

        @Override
        public double execute(double currentValue) {
            return currentValue + operand;
        }

        @Override
        public double undo(double currentValue) {
            return currentValue - operand;
        }

        @Override
        public String toString() {
            return "Add " + operand;
        }
    }

    private class SubtractCommand extends Command {
        private double operand;

        public SubtractCommand(double value, double operand) {
            super(value);
            this.operand = operand;
        }

        @Override
        public double execute(double currentValue) {
            return currentValue - operand;
        }

        @Override
        public double undo(double currentValue) {
            return currentValue + operand;
        }

        @Override
        public String toString() {
            return "Subtract " + operand;
        }
    }

    private class MultiplyCommand extends Command {
        private double operand;

        public MultiplyCommand(double value, double operand) {
            super(value);
            this.operand = operand;
        }

        @Override
        public double execute(double currentValue) {
            return currentValue * operand;
        }

        @Override
        public double undo(double currentValue) {
            return currentValue / operand;
        }

        @Override
        public String toString() {
            return "Multiply by " + operand;
        }
    }

    private class DivideCommand extends Command {
        private double operand;

        public DivideCommand(double value, double operand) {
            super(value);
            this.operand = operand;
        }

        @Override
        public double execute(double currentValue) {
            return currentValue / operand;
        }

        @Override
        public double undo(double currentValue) {
            return currentValue * operand;
        }

        @Override
        public String toString() {
            return "Divide by " + operand;
        }
    }

}
