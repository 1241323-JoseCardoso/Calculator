package calculator.presentation;

import calculator.application.CalculatorController;
import calculator.domain.Calculator;
import calculator.domain.CalculatorService;

import java.util.Scanner;

public class ConsoleApp {

    static Scanner ler = new Scanner(System.in);

    public static void main(String[] args){

        Calculator calculator = new Calculator();
        CalculatorService service = new CalculatorService(calculator);
        CalculatorController controller = new CalculatorController(service);

        while (true){

            System.out.println("========== Calculadora Cardosiana ==========");
            System.out.println("Display Value (Press K to leave):  " + controller.value());

            String value = ler.nextLine();

            if(value.equalsIgnoreCase("K")){

                return;

            }

            controller.pressDigit(value);

        }
    }
}
