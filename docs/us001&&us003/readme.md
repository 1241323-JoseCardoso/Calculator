# US01 - Current Number && US03 - Clicking in the Entry Pad

## 1. Context

As a User, I want to see a display showing the current number entered or the result of the last operation, so that I can see what I am doing.

As a User, I want to enter numerical sequences of up to 8 digits, so that I can perform calculations across different whole number place values.

---

## 2. Requirements

**US01** As a User, I want to see a display showing the current number entered or the result of the last operation, so that I can see what I am doing.

**US03** As a User, I want to enter numerical sequences of up to 8 digits, so that I can perform calculations across different whole number place values.

**Acceptance Criteria:**

- US01.1 When the calculator starts, the display must show 0.
- US01.2 When the user presses a numeric button (0-9), the display must show the entered digit.
- US01.3 The display must accept numbers with a maximum of 8 digits. Additional digits must be ignored.
- US01.4 After an arithmetic operation is completed, the display must show the calculated result.

- US03.1 Entry of any digits more than 8 will be ignored.

---

## 3. Analysis

The calculator is modelled around a single aggregate, `Calculator`, which encapsulates the entire calculation state and guarantees the consistency of every operation.

The aggregate maintains the information required to process user input and update the displayed value throughout a calculation.

### Business Rules

- The calculator starts in the `READY` state.
- When the application starts, the display shows `0`.
- Every numeric input updates the current `CalculatorInput`.
- The `DisplayValue` always reflects either:
  - the number currently being entered by the user; or
  - the result of the last completed calculation.
- A number may contain a maximum of 8 digits.
- Any additional digits beyond the limit are ignored.
- After a calculation is completed, the display is updated with the resulting value.
- If a calculation produces a value that exceeds the supported limit, the calculator enters the `ERR` state and the display shows `ERR`.

### System Sequence Diagram (SSD)

![US01 SSD](analysis/US01_SSD.png)

---

## 4. Design

### 4.1 Realization

The functionality is realised through the interaction between the user, the calculator aggregate and its value objects.

The `Calculator` aggregate coordinates all state changes, validates user input, updates the current `CalculatorInput`, and refreshes the `DisplayValue` after each valid action.

**Sequence Diagram (SD):**

![US01 SD](design/US01_SD.png)

**Class Diagram (CD):**

![US01 CD](design/US01_CD.png)

---

## 5. Implementation

The implementation centres around the `Calculator` aggregate.

- `Calculator` manages the calculator's current state.
- `CalculatorInput` stores the number currently being entered.
- `DisplayValue` represents the value shown on the calculator display.
- `OperatorType` represents the supported arithmetic operators.
- `CalculatorStatus` indicates whether the calculator is in a normal (`READY`) or error (`ERR`) state.

Whenever the user presses a numeric button, the input is validated against the maximum length (8 digits). If the input is valid, both the current input and the display are updated accordingly.

After an arithmetic operation is completed, the calculated result replaces the previous display value.

---

## 6. Integration / Demonstration

This user story integrates with:

- Numeric keypad (digits `0`–`9`).
- Arithmetic operations (`+`, `-`, `×`, `÷`), whose results are displayed.
- `C` (Clear), which updates the display after clearing the current input.
- `AC` (All Clear), which resets the calculator and restores the display to `0`.

---

## 7. Observations

- The display is the primary feedback mechanism of the calculator.
- Only one value is displayed at a time.
- Every valid user interaction immediately updates the display.
- The display always represents either the current input or the result of the last completed calculation.
- Error situations are represented by the `ERR` status and displayed accordingly.