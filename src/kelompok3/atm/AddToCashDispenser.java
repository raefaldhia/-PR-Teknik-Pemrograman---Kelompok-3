package kelompok3.atm;

public class AddToCashDispenser extends Transaction {
    private Keypad keypad;
    private CashDispenser cashDispenser;

    private static final int CANCEL = 0;

    public AddToCashDispenser(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad, CashDispenser atmCashDispenser) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        cashDispenser = atmCashDispenser;
        keypad = atmKeypad;
    }

    @Override
    public void execute() {
        Screen screen = getScreen();

        screen.displayMessage("Please insert count of $20.00 bills [0 to cancel]: ");
        int count;
        do {
            count = keypad.getInput();
        } while (count < CANCEL);
        if (count == CANCEL) {
            screen.displayMessageLine("Canceling transaction ...");
        } else {
            cashDispenser.addCashCount(count);
        }
    }
}
