package calculator.presentation;

import calculator.application.CalculatorController;
import calculator.domain.CalculatorService;
import calculator.domain.Calculator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CalculatorViewController {

    @FXML
    private TextField display;

    private CalculatorController controller;

    @FXML
    public void initialize() {
        Calculator calculator = new Calculator();
        CalculatorService service = new CalculatorService(calculator);
        controller = new CalculatorController(service);

        display.setText("0");
    }

    @FXML
    private void handleButtonClick(javafx.event.ActionEvent event) {
        javafx.scene.control.Button button =
                (javafx.scene.control.Button) event.getSource();

        String value = button.getText();

        String newDisplayValue = controller.pressDigit(value);

        display.setText(newDisplayValue);
    }
}