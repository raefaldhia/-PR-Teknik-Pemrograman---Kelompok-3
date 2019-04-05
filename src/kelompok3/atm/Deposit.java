package kelompok3.atm;

public class Deposit extends Transaction {
   private double amount; // amount to deposit
   private Keypad keypad; // reference to keypad
   private DepositSlot depositSlot; // reference to deposit slot
   private final static int CANCELED = 0; // constant for cancel option

   // Deposit constructor
   public Deposit(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      DepositSlot atmDepositSlot) {

      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);

      keypad = atmKeypad;
      depositSlot = atmDepositSlot;
   } 

   // perform transaction
   @Override
   public void execute() {
      Screen screen = getScreen(); // get reference to screen

      amount = promptForDepositAmount();
      if (amount == CANCELED) {
         screen.displayMessageLine("Canceling transaction...");
      } else {
         screen.displayMessage("\nPlease insert a deposit envelope containing ");
         screen.displayDollarAmount(amount);
         screen.displayMessageLine(".");
         // TODO: ?
         if (depositSlot.isEnvelopeReceived() == true) {
            screen.displayMessageLine("Your envelope has been received.");
            screen.displayMessageLine("NOTE: The money just deposited will not be available until we verify the amount of any enclosed cash and your checks clear.");
            getBankDatabase().credit(getAccountNumber(), amount);
         } else {
            // TODO: ?
         }
      }
   }

   // prompt user to enter a deposit amount in cents 
   private double promptForDepositAmount() {
      Screen screen = getScreen(); // get reference to screen

      int input; // receive input of deposit amount
      do {
         // display the prompt
         screen.displayMessage("\nPlease enter a deposit amount in " +
                 "CENTS (or 0 to cancel): ");

         input = keypad.getInput();
      } while (input < 0);

      // check whether the user canceled or entered a valid amount
      if (input == CANCELED) {
         return CANCELED;
      }
      else {
         return (double) input / 100; // return dollar amount
      }
   }
} 
