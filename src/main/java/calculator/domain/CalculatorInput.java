package calculator.domain;

public class CalculatorInput {

    private String input;
    private boolean dot;

    protected CalculatorInput(String value){

        this.input = value;
        this.dot = false;

    }

    protected CalculatorInput(String value, boolean dot){

        this.input = value;
        this.dot = dot;

    }

    public int nDigits(){

        return input.replaceAll("[^0-9]", "").length();
    }


    public boolean hasDot(){

        return dot;

    }

    public void setDot(){

        dot = true;
    }

    public String input(){

        return input;

    }
}
