## US02 - Entry Pad

As a user, I want to see an entry pad containing buttons, so that I can choose numbers and operations.

### Purpose

The entry pad represents the visual input area of the calculator.  
It allows the user to interact with the calculator without typing commands manually.

This user story belongs mainly to the presentation layer. The buttons do not contain calculation logic; they only send user actions to the application layer.

### Acceptance Criteria

- AC1: The entry pad contains digit buttons from `0` to `9`.
- AC2: The entry pad contains arithmetic operator buttons: `+`, `-`, `*`, `/`.
- AC3: The entry pad contains an equals button `=`.
- AC4: The entry pad contains a clear button `C`.
- AC5: The entry pad contains a clear all button `AC`.

### UI Behaviour

When the user clicks a button, the UI sends the button value to the application layer.

Example:

```text
User clicks "5"
        ↓
UI sends "5" to CalculatorController
        ↓
Controller updates the calculator state
        ↓
UI receives the updated display value
        ↓
Display is refreshed
```
