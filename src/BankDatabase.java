public class BankDatabase {
    private Account[] accounts; // array of Accounts

    static final int ACCOUNT_NOTFOUND  = 0;
    static final int ACCOUNT_BLOCKED   = 1;
    static final int ACCOUNT_AVAILABLE = 2;

    public BankDatabase() {
        accounts = new Account[3]; // just 3 accounts for testing
        accounts[0] = new Account(12345, 54321, 1000.0, 1200.0, false);
        accounts[1] = new Account(8765, 5678, 200.0, 200.0, false);
        accounts[2] = new Account(0, 0, 200.0, 200.0, true);
    }

    private Account getAccount(int accountNumber) {
        for (Account i : accounts) {
            if (i.getAccountNumber() == accountNumber) {
                return i;
            }
        }
        return null; // if no matching account was found, return null
    }

    public int authenticateUser(int userAccountNumber, int userPIN) {
        // attempt to retrieve the account with the account number
        Account userAccount = getAccount(userAccountNumber);

        // if account exists, return result of Account method validatePIN
        if (userAccount != null) {
            if (userAccount.isBlocked()) {
                return ACCOUNT_BLOCKED;
            }

            if (userAccount.validatePIN(userPIN)) {
                return ACCOUNT_AVAILABLE;
            }
        }
        return ACCOUNT_NOTFOUND; // account number not found, so return false
    }

    public double getAvailableBalance(int userAccountNumber) {
        return getAccount(userAccountNumber).getAvailableBalance();
    }

    public double getTotalBalance(int userAccountNumber) {
        return getAccount(userAccountNumber).getTotalBalance();
    }

    public void credit(int userAccountNumber, double amount) {
        getAccount(userAccountNumber).credit(amount);
    }

    public void debit(int userAccountNumber, double amount) {
        getAccount(userAccountNumber).debit(amount);
    }

    public boolean changePin(int userAccountNumber, int newPin) {
        if (getAccount(userAccountNumber).getPin() == newPin) {
            return false;
        }
        getAccount(userAccountNumber).setPin(newPin);
        return true;
    }

    public boolean transfer(int userAccountNumber, int receiverAccountNumber, double amount) {
        if (userAccountNumber == receiverAccountNumber) {
            return false;
        }

        Account receiver = getAccount(receiverAccountNumber);
        if (receiver == null) {
            return false;
        }

        receiver.receiveTransfer(amount);
        getAccount(userAccountNumber).debit(amount);

        return true;
    }

    public boolean isAdmin(int userAccountNumber) {
        return getAccount(userAccountNumber).isAdmin();
    }

    public boolean unblock(int userAccountNumber, int targetAccountNumber) {
        Account account = getAccount(targetAccountNumber);
        if (account != null) {
            account.unblock();
            return true;
        }
        return false;
    }
}