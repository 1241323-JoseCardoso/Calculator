package calculator;

import calculator.application.CalculatorController;
import calculator.domain.Calculator;
import calculator.domain.CalculatorService;
import calculator.domain.CalculatorStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void newCalculatorShouldDisplayZero(){

        Calculator calculator = new Calculator();

        String display = calculator.displayValue();

        assertEquals("0", display);

    }


    @Test
    void displayValueUpdateOneTime(){

        Calculator calculator = new Calculator();
        CalculatorService service = new CalculatorService(calculator);
        CalculatorController controller = new CalculatorController(service);

        controller.pressDigit("2");

        String displayedValue = calculator.displayValue();

        assertEquals("2", displayedValue);

    }

    @Test
    void displayValueUpdatedHigherThanOneTime(){

        Calculator calculator = new Calculator();
        CalculatorService service = new CalculatorService(calculator);
        CalculatorController controller = new CalculatorController(service);

        controller.pressDigit("5");
        controller.pressDigit("0");
        controller.pressDigit("0");

        String displayValue = calculator.displayValue();

        assertEquals("500", displayValue);

    }

    @Test
    void afterOperationDisplayValue(){

        Calculator calculator = new Calculator();
        CalculatorService service = new CalculatorService(calculator);
        CalculatorController controller = new CalculatorController(service);

        controller.pressDigit("5");
        controller.pressDigit("1");
        controller.pressDigit("0");
        controller.pressDigit("+");
        controller.pressDigit("1");
        controller.pressDigit(".");
        controller.pressDigit("1");
        controller.pressDigit("2");
        controller.pressDigit("=");

        String displayValue = calculator.displayValue();

        assertEquals("511.12", displayValue);

    }

    @Test
    void moreThan8DigitsMustBeIgnored(){

        Calculator calculator = new Calculator();
        CalculatorService service = new CalculatorService(calculator);
        CalculatorController controller = new CalculatorController(service);

        controller.pressDigit("5");
        controller.pressDigit("1");
        controller.pressDigit("0");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("1");


        String displayValue = calculator.displayValue();

        assertEquals("5101111", displayValue);

    }

    @Test
    void resultHaveMoreThan8Digits(){

        Calculator calculator = new Calculator();
        CalculatorService service = new CalculatorService(calculator);
        CalculatorController controller = new CalculatorController(service);

        controller.pressDigit("5");
        controller.pressDigit("1");
        controller.pressDigit("0");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("*");
        controller.pressDigit("5");
        controller.pressDigit("1");
        controller.pressDigit("0");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("=");

        String displayValue = calculator.displayValue();

        assertEquals("ERR", displayValue);
    }

    @Test
    void divisionPer0(){

        Calculator c = new Calculator();
        CalculatorService service = new CalculatorService(c);
        CalculatorController controller = new CalculatorController(service);

        controller.pressDigit("1");
        controller.pressDigit("/");
        controller.pressDigit("0");
        controller.pressDigit("=");

        String value = c.displayValue();

        assertEquals("ERR", value);

    }

    @Test

    void Cbutton(){

        Calculator c = new Calculator();
        CalculatorService service = new CalculatorService(c);
        CalculatorController controller = new CalculatorController(service);

        controller.pressDigit("1");
        controller.pressDigit("/");
        controller.pressDigit("0");
        controller.pressDigit("C");
        controller.pressDigit("1");
        controller.pressDigit("=");

        String value = c.displayValue();

        assertEquals("1", value);

    }

    @Test

    void CAbutton(){

        Calculator c = new Calculator();
        CalculatorService service = new CalculatorService(c);
        CalculatorController controller = new CalculatorController(service);

        controller.pressDigit("1");
        controller.pressDigit("/");
        controller.pressDigit("4");
        controller.pressDigit("CA");
        controller.pressDigit("=");

        String value = c.displayValue();

        assertEquals("0", value);

    }


    @Test
    void testDecimalPart(){

        Calculator c = new Calculator();
        CalculatorService service = new CalculatorService(c);
        CalculatorController controller = new CalculatorController(service);

        controller.pressDigit("5");
        controller.pressDigit(".");
        controller.pressDigit("1");
        controller.pressDigit("0");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("+");
        controller.pressDigit("5");
        controller.pressDigit(".");
        controller.pressDigit("1");
        controller.pressDigit("0");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("1");
        controller.pressDigit("=");

        String displayValue = c.displayValue();

        assertEquals("10.20222", displayValue);


    }


}
