// Withdrawal.java
// Represents a withdrawal ATM transaction
public class Withdrawal extends Transaction {
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser

   // constant corresponding to menu option to cancel
   private final static int CANCELED = 0;

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
      amount = displayMenuOfAmounts();
      if (amount == CANCELED) {
         screen.displayMessageLine("\nCanceling transaction...");
      } else if (getBankDatabase().getAvailableBalance(getAccountNumber()) < amount) {
         screen.displayMessageLine("\nInsufficient funds in your account. Please choose a smaller amount.");
      } else if (cashDispenser.isSufficientCashAvailable(amount)) {
         getBankDatabase().debit(getAccountNumber(), amount);
         cashDispenser.dispenseCash(amount);
         screen.displayMessageLine("\nYour cash has been dispensed. Please take your cash now.");
      } else {
         // TODO
      }
   }

   // display a menu of withdrawal amounts and the option to cancel;
   // return the amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts() {
      Screen screen = getScreen(); // get screen reference

      // array of amounts to correspond to menu numbers
      int[] amounts = {0, 20, 40, 60, 100, 200};

      // loop while no valid choice has been made
      while (true) {
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
               return amounts[input];
            case 6:
               screen.displayMessage("\n");
               do {
                  screen.displayMessage("Please specify the amount of withdrawal in CENTS (or 0 to cancel)[LIMIT: $200.00]: ");
                  input = keypad.getInput();
               } while ((input < 0) || (input > 20000)); // $200.00

               return (input / 100);
            case 7: // the user chose to cancel
               return CANCELED;
            default: // the user did not enter a value from 1-6
               screen.displayMessageLine(
                  "\nInvalid selection. Try again.");
         } 
      }
   }
} 