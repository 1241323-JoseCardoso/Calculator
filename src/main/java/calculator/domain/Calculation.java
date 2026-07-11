package calculator.domain;

public class Calculation {

    private double firstNumber;
    private double secondNumber;
    private OperatorType opType;
    private double result;

    public Calculation(double firstNumber, double secondNumber, OperatorType opType){

        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.opType = opType;
        this.result = result();

    }

    private double result(){

        if(opType == OperatorType.MULTIPLY){

            return firstNumber * secondNumber;

        }else if(opType == OperatorType.ADD){

            return firstNumber + secondNumber;

        }else if(opType == OperatorType.SUBTRACT){

            return firstNumber - secondNumber;

        }else if(opType == OperatorType.DIVIDE){

            return firstNumber / secondNumber;

        }

        return 0;

    }

    protected double getResult(){

        return result;

    }
}
