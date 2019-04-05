package kelompok3.atm;

public class Account {
   private int accountNumber; // account number
   private int pin; // PIN for authentication
   private double availableBalance; // funds available for withdrawal
   private double totalBalance; // funds available & pending deposits
   private boolean blocked;
   private int tryCount;
   private boolean admin;

   // Account constructor initializes attributes
   public Account(int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance, boolean _isAdmin) {
      accountNumber = theAccountNumber;
      pin = thePIN;
      availableBalance = theAvailableBalance;
      totalBalance = theTotalBalance;
      blocked = false;
      tryCount = 0;
      admin = _isAdmin;
   }

   // determines whether a user-specified PIN matches PIN in Account
   public boolean validatePIN(int userPIN) {
      if (userPIN == pin) {
         tryCount = 0;
         return true;
      }
      else {
         ++tryCount;
         if (tryCount == 3) {
            block();
            tryCount = 0;
         }
         return false;
      }
   } 

   // returns available balance
   public double getAvailableBalance() {
      return availableBalance;
   } 

   // returns the total balance
   public double getTotalBalance() {
      return totalBalance;
   } 

   public void credit(double amount) {
      totalBalance += amount;
   }

   public void receiveTransfer(double amount) {
      availableBalance += amount;
      credit(amount);
   }

   public void debit(double amount) {
      availableBalance -= amount;
      totalBalance -= amount;
   }

   public int getAccountNumber() {
      return accountNumber;  
   }

   public void setPin(int newPin) {
      pin = newPin;
   }

   public int getPin() {
      return pin;
   }

   public boolean isBlocked() {
      return blocked;
   }

   public void block() {
      blocked = true;
   }

   public void unblock() {
      blocked = false;
   }

   public boolean isAdmin() {
      return admin;
   }
}