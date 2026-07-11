package calculator.application;

import calculator.domain.CalculatorService;

public class CalculatorController {

    private final CalculatorService service;

    public CalculatorController(CalculatorService service){

        this.service = service;

    }

    public String pressDigit(String digit){

        service.update(digit);

        return service.getCalculatorDisplayValue();

    }

}
