package kelompok3.atm;

public class UnblockAccount extends Transaction {
    private Keypad keypad;
    public UnblockAccount(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
    }

    @Override
    public void execute() {
        Screen screen = getScreen();
        int targetAccountId = showUnblockAccountMenu();
        if (getBankDatabase().unblock(getAccountNumber(), targetAccountId)) {
            screen.displayMessageLine("Account with number " + targetAccountId + " has been unblocked.");
        } else {
            screen.displayMessageLine("Unable to unblock an account with number " + targetAccountId + ".");
        }
    }

    public int showUnblockAccountMenu() {
        Screen screen = getScreen();
        screen.displayMessage("Please insert an account number: ");
        return keypad.getInput();
    }
}
