
public class AddCash extends Transaction {
    private Keypad keypad;
    private CashDispenser cashDispenser;

    private static final int CANCEL = 0;

    public AddCash(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad, CashDispenser atmCashDispenser) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        cashDispenser = atmCashDispenser;
        keypad = atmKeypad;
    }

    @Override
    public void execute() {
        Screen screen = getScreen();

        int count;
        screen.displayMessage("\n");
        do {
            screen.displayMessage("Please enter the amount of $20.00 bills (or 0 to cancel): ");
            count = keypad.getInput();
        } while (count < CANCEL);
        if (count == CANCEL) {
            screen.displayMessageLine("\nCanceling...");
        } else {
            cashDispenser.addCashCount(count);
            screen.displayMessageLine("\nCash has been added.");
        }
    }
}
