package kelompok3.atm;

public class PeekDispenser extends Transaction {
    private CashDispenser cashDispenser;
    public PeekDispenser(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, CashDispenser atmCashDispenser) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        cashDispenser = atmCashDispenser;
    }

    @Override
    public void execute() {
        Screen screen = getScreen();
        screen.displayMessageLine("\nAvailable cash count within the cash dispenser: " + cashDispenser.getCashCount());
    }
}
