package calculator.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private CalculatorInput input;
    private OperatorType opType;
    private DisplayValue displayValue;
    private List<Calculation> calculationList;
    private CalculatorStatus status;
    boolean hasOp;

    private String storedValue;

    public Calculator(){

        this.input = new CalculatorInput("");
        this.opType = null;
        displayValue = new DisplayValue("0");
        this.calculationList = new ArrayList<>();
        this.status = CalculatorStatus.READY;
        this.storedValue = "";
        this.hasOp = false;

    }

    public void resultHandler(){
        if (storedValue.isEmpty() || input.input().isEmpty() || opType == null) {
            return;
        }

        double firstNumber = Double.parseDouble(storedValue);
        double secondNumber = Double.parseDouble(input.input());

        if(secondNumber == 0){

            displayValue = new DisplayValue("ERR");
            status = CalculatorStatus.ERR;

            return;

        }

        Calculation c = new Calculation(firstNumber, secondNumber, opType);

        BigDecimal resultDecimal = BigDecimal.valueOf(c.getResult());

        resultDecimal = resultDecimal.stripTrailingZeros();

        int nDigitsInteiros = resultDecimal.precision() - resultDecimal.scale();
        if (nDigitsInteiros < 0) nDigitsInteiros = 1;

        if (nDigitsInteiros > 8) {
            status = CalculatorStatus.ERR;
            displayValue = new DisplayValue("ERR");
        } else {
            int casasDecimaisPermitidas = 8 - nDigitsInteiros;

            if (resultDecimal.scale() > casasDecimaisPermitidas) {
                resultDecimal = resultDecimal.setScale(casasDecimaisPermitidas, RoundingMode.HALF_UP);
            }

            String resultadoFinal = resultDecimal.toPlainString();

            calculationList.add(c);
            input = new CalculatorInput("");
            storedValue = resultadoFinal;
            hasOp = false;
            status = CalculatorStatus.READY;
            displayValue = new DisplayValue(resultadoFinal);
        }
    }

    public CalculatorInput input(){

        return input;

    }

    public String displayValue(){

        return displayValue.getValue();

    }

    public void defineOperatorType(OperatorType op){

        opType = op;
        hasOp = true;

    }

    public boolean hasOp(){

        return hasOp;

    }

    public void signalHandler(String digit){

        displayValue = new DisplayValue(digit);

        storedValue = input.input();
        input = new CalculatorInput("");

    }

    public void dotHandler(String digit){

        input = new CalculatorInput(input.input() + digit);
        input.setDot();

        displayValue = new DisplayValue(input.input());

    }

    public void numberHandler(String digit){

        input = new CalculatorInput(input().input() + digit, input.hasDot());

        displayValue = new DisplayValue(input.input());

    }

    public int nDigits(){

        return input.nDigits();
    }

    public void reset(){

        input = new CalculatorInput("");
        opType = null;
        displayValue = new DisplayValue("0");
        calculationList = new ArrayList<>();
        status = CalculatorStatus.READY;
        storedValue = "";
        hasOp = false;

    }

    public void deleteLastDigit(){

        if(displayValue.equals(OperatorType.ADD) || displayValue.equals(OperatorType.DIVIDE) || displayValue.equals(OperatorType.MULTIPLY) || displayValue.equals(OperatorType.SUBTRACT)){

            opType = null;
            hasOp = false;
            displayValue = new DisplayValue(input().input());

        }else{

            String inputA = input().input();
            inputA = inputA.substring(0, inputA.length() - 1);

            displayValue = new DisplayValue(inputA);
            input = new CalculatorInput(inputA);

        }
    }

}
