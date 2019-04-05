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
            screen.displayMessageLine("\nCanceling transaction...");
        } else if (amount > 100.00) {
            screen.displayMessageLine("\nTransfer limit exceeded.");
        } else if (getBankDatabase().getAvailableBalance(getAccountNumber()) < amount) {
            screen.displayMessageLine("\nInsufficient amount of the available balance.");
        } else if (getBankDatabase().transfer(getAccountNumber(), receiver, amount)) {
            screen.displayMessageLine("\nYour balance has been transferred successfully.");
        } else {
            screen.displayMessageLine("\nFailed to perform transfer...");
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
        screen.displayMessage("\n");
        do {
            screen.displayMessage("Please input the transfer amount in CENTS (or 0 to cancel)[LIMIT: ");
            screen.displayDollarAmount(100);
            screen.displayMessage("]: ");
            input = keypad.getInput();

        } while (input < 0);

        return (double) input / 100;
    }
}
