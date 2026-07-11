package calculator.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalculatorService {

    private final Calculator calculator;

    private final HashMap<String, OperatorType> map;

    public CalculatorService(Calculator c1){

        this.calculator = c1;

        map = new HashMap<>();

        map.put("+", OperatorType.ADD);
        map.put("/", OperatorType.DIVIDE);
        map.put("*", OperatorType.MULTIPLY);
        map.put("-", OperatorType.SUBTRACT);

    }

    public void update(String digit){

        if(!calculator.displayValue().equals("ERR")) {

            if (map.containsKey(digit) && !calculator.hasOp() && !calculator.input().input().equals("")) {

                OperatorType operator = map.get(digit);
                calculator.defineOperatorType(operator);
                calculator.signalHandler(digit);

            } else if (digit.equals(".") && !calculator.input().hasDot()) {

                calculator.dotHandler(digit);

            } else if (digit.matches("[0-9]")) {

                if (calculator.nDigits() < 7) {

                    calculator.numberHandler(digit);

                }

            } else if (digit.equals("=")) {

                calculator.resultHandler();

            }else if (digit.equals("CA")){

                calculator.reset();

            }else if (digit.equals("C")){

                calculator.deleteLastDigit();
            }

        }else if(digit.equals("CA") || digit.equals("C")){

            calculator.reset();
        }
    }

    public String getCalculatorDisplayValue(){

        return calculator.displayValue();

    }


}
