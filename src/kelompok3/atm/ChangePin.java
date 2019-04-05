package kelompok3.atm;

public class ChangePin extends Transaction {
    private Keypad keypad;

    public ChangePin(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad) {
        super(userAccountNumber, atmScreen, atmBankDatabase);

        keypad = atmKeypad;
    }

    @Override
    public void execute() {
        Screen screen = getScreen();

        while (true) {
            if (getBankDatabase().changePin(getAccountNumber(), promptChangePin()) == false) {
                screen.displayMessageLine("PIN cannot be the same as the old one.");
            } else {
                break;
            }
        }
        screen.displayMessageLine("Your PIN has been changed.");
    }

    private int promptChangePin() {
        Screen screen = getScreen();

        screen.displayMessage("\nPlease insert your new PIN: ");

        return keypad.getInput();
    }
}
