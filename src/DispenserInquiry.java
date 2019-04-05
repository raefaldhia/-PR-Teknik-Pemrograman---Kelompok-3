public class DispenserInquiry extends Transaction {
    private CashDispenser cashDispenser;

    public DispenserInquiry(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, CashDispenser atmCashDispenser) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        cashDispenser = atmCashDispenser;
    }

    @Override
    public void execute() {
        Screen screen = getScreen();

        screen.displayMessageLine("\nCash Dispenser Information:");
        screen.displayMessageLine(cashDispenser.getCashCount() + " sheets of $20 bills");
    }
}
