package calculator.presentation;

import calculator.application.CalculatorController;
import calculator.domain.CalculatorService;
import calculator.domain.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private void handleButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();

        String value = button.getText();

        String newDisplayValue = controller.pressDigit(value);

        display.setText(newDisplayValue);
    }
}