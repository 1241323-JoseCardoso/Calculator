# Project Calculator

## 1. Project Vision

Calculators are not only one of the most useful tools available, but they are also a great way to understand UI and event processing in an application. In this problem you will create a calculator that supports basic arithmetic calculations on numbers.

The aim of this project is to review and apply concepts covered in the software engineering and application courses, in order to simulate a real-world development environment and bridge the gap between academic theory and professional practice. To this end, a simple calculator will be developed.

## 2. MVP (Minimum Viable Product)

The basic version can:

- enter numbers;
- perform +, -, *, /;
- display the result;
- clear the input;
- clear the state;
- display an error.

## 3. Out of Scope

Operations involving trigonometric functions, probabilities and complex numbers are outside the scope.

## 4. User Stories

### US01 - Current Number

As a User, I want to see a display showing the current number entered or the result of the last operation, so that I can see what I am doing.

### US02 - Entry Pad

As a User, I want to see an entry pad containing buttons, so that I can choose the numbers and the operations.

Acceptance Criteria:

- AC1 : The digits are in the range 0-9
- AC2 : The operators can be: '+', '-', '/', '=' and '*'
- AC3 : There's also a 'C' button to clear the current number/operator and AC button for clear all.

### US03 - Clicking in the Entry Pad

As a user, I want to enter numerical sequences of up to 8 digits, so that I can perform calculations across different whole number place values.

Acceptance Criteria:

- AC1 : Entry of any digits more than 8 will be ignored.

### US04 -  Operation Result

As a user, I want to click on an operation button to display the result of that operation on the result of the preceding operation and the last number entered, the last two numbers entered or the last number entered, so that I can track the past operations.

Clarification:
This calculator follows sequential execution and does not evaluate full expressions.

### US05 - 'C' Button

As a user, I want to click the 'C' button to clear the last number or the last operation, so that I can correct any mistake.

Acceptance Criteria:

- AC1 : If user's last entry was an operation the display will be updated to the value that preceded it.

### US06 - 'AC' Button

As a user, I want to click the 'AC' button to clear all internal work areas and to set the display to 0, so that I can restart any long operation.

### US07 - 'ERR'

As a user, I want to see 'ERR' displayed if any operation would exceed the 8 digit maximum.

## Technologies

- Java
- JUnit
- JavaFX, optional/future UI

## 5. Project Scope Clarifications

- Operation mode: Sequential execution. Operations are processed immediately as entered. It does not evaluate full expressions.
- Digit limit: Applies to both. Input and results are limited to 8 digits max.
- Division by zero: Returns an error. The screen displays "ERR" or "E".
- After an error: All functions lock. You must press AC or C to reset.
- Number types: Accepts decimals. It is not limited to integers only.

## 6. Learning Goals

- Practice Requirement Analysis
- Practice user stories and acceptance criteria
- Apply basic object-oriented design
- Separate UI concerns from calculation logic
- Write unit tests for core behaviors.