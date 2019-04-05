public class UnblockAccount extends Transaction {
    private Keypad keypad;

    public UnblockAccount(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
    }

    @Override
    public void execute() {
        Screen screen = getScreen();

        screen.displayMessage("Please insert an account number: ");
        int targetAccountId = keypad.getInput();
        if (getBankDatabase().unblock(getAccountNumber(), targetAccountId)) {
            screen.displayMessageLine("Account with number " + targetAccountId + " has been unblocked.");
        } else {
            screen.displayMessageLine("Unable to unblock an account with number " + targetAccountId + ".");
        }
    }
}
