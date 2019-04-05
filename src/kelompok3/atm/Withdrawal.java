// Withdrawal.java
// Represents a withdrawal ATM transaction
package kelompok3.atm;

public class Withdrawal extends Transaction {
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser

   // constant corresponding to menu option to cancel
   private final static int CANCELED = 7;
   private final static int OK = 1;
   // Withdrawal constructor
   public Withdrawal(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      CashDispenser atmCashDispenser) {

      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);

      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   }

   // perform transaction
   @Override
   public void execute() {
      Screen screen = getScreen();
      int userChoice = displayMenuOfAmounts();
      if (userChoice != CANCELED) {
         if ((getBankDatabase().getAvailableBalance(getAccountNumber()) >= amount) && (getBankDatabase().getTotalBalance(getAccountNumber()) >= amount)) {
            if (cashDispenser.isSufficientCashAvailable(amount)) {
               getBankDatabase().debit(getAccountNumber(), amount);
               cashDispenser.dispenseCash(amount);
               screen.displayMessageLine("Your cash has been dispensed. Please take your cash now.");
            } else {
               // TODO
            }
         } else {
            screen.displayMessageLine("Insufficient funds in your account. Please choose a smaller amount.");
         }
      } else {
         screen.displayMessageLine("Canceling transaction...");
      }
   }

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts() {
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference

      // array of amounts to correspond to menu numbers
      int[] amounts = {0, 20, 40, 60, 100, 200};

      // loop while no valid choice has been made
      while (userChoice == 0) {
         // display the withdrawal menu
         screen.displayMessageLine("\nWithdrawal Menu:");
         screen.displayMessageLine("1 - $20");
         screen.displayMessageLine("2 - $40");
         screen.displayMessageLine("3 - $60");
         screen.displayMessageLine("4 - $100");
         screen.displayMessageLine("5 - $200");
         screen.displayMessageLine("6 - Other");
         screen.displayMessageLine("7 - Cancel transaction");
         screen.displayMessage("\nChoose a withdrawal amount: ");

         int input = keypad.getInput(); // get user input through keypad
         // determine how to proceed based on the input value
         switch (input) {
            case 1: // if the user chose a withdrawal amount 
            case 2: // (i.e., chose option 1, 2, 3, 4 or 5), return the
            case 3: // corresponding amount from amounts array
            case 4:
            case 5:
               amount = amounts[input]; // save user's choice
               userChoice = OK;
               break;
            case 6:
               int otherA;
               do {
                  do {
                     screen.displayMessage("Please specify the amount of withdrawal in CENTS (or 0 to cancel)[LIMIT: $200.00]: ");
                     otherA = keypad.getInput();
                  } while (otherA < 0);

                  if (otherA != 0) {
                     otherA /= 100;
                  }
               } while (otherA > 200.00);

               if (otherA == 0) {
                  userChoice = CANCELED;
               } else {
                  amount = otherA;
                  userChoice = OK;
               }
               break;
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-6
               screen.displayMessageLine(
                  "\nInvalid selection. Try again.");
         } 
      } 

      return userChoice; // return withdrawal amount or CANCELED
   }
} 