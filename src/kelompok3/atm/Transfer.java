package kelompok3.atm;

public class Transfer extends Transaction {
    private Keypad keypad;
    private int receiver;
    private double amount;
    private static int CANCELED = 0;
    public Transfer(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
    }

    @Override
    public void execute() {
        Screen screen = getScreen();

        receiver = performGetReceiver();
        amount = performGetAmount();
        if (amount == CANCELED) {
            screen.displayMessageLine("Canceling transaction...");
        } else if (amount > 100.00) {
            screen.displayMessageLine("Transfer limit exceeded.");
        } else if (getBankDatabase().getAvailableBalance(getAccountNumber()) < amount) {
            screen.displayMessageLine("Insufficient amount of available balance.");
        } else if (getBankDatabase().transfer(getAccountNumber(), receiver, amount)) {
            screen.displayMessageLine("Your balance has been transfered succesfully.");
        } else {
            screen.displayMessageLine("Transaction failed.");
        }
    }

    public int performGetReceiver() {
        Screen screen = getScreen();

        screen.displayMessage("\nPlease specify receiver's account number: ");

        return keypad.getInput();
    }

    public double performGetAmount() {
        Screen screen = getScreen();

        int input;
        do {
            screen.displayMessage("Please input the transfer amount in CENTS (limit: ");
            screen.displayDollarAmount(100);
            screen.displayMessage(") [or 0 to cancel]: ");
            input = keypad.getInput();

        } while (input < 0);

        if (input == CANCELED) {
            return CANCELED;
        }
        else {
            return (double) input / 100;
        }
    }
}
