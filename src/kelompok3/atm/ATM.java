package kelompok3.atm;

public class ATM {
   private int userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser
   private DepositSlot depositSlot;

   private BankDatabase bankDatabase; // account information database

   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int DEPOSIT = 3;
   private static final int CHANGE_PIN = 4;
   private static final int TRANSFER = 5;
   private static final int EXIT = 6;
   private static final int UNBLOCK = 7;
   private static final int PEEK_DISPENSER = 8;
   private static final int ADD_DISPENSER  = 9;

   // no-argument ATM constructor initializes instance variables
   public ATM() {
      userAuthenticated = BankDatabase.ACCOUNT_NOTFOUND; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad 
      cashDispenser = new CashDispenser(); // create cash dispenser
      bankDatabase = new BankDatabase(); // create acct info database
      depositSlot = new DepositSlot();
   }

   // start ATM 
   public void run() {
      // welcome and authenticate user; perform transactions
      while (true) {
         // loop while user is not yet authenticated
         while (userAuthenticated != BankDatabase.ACCOUNT_AVAILABLE) {
            screen.displayMessageLine("\nWelcome!");
            authenticateUser(); // authenticate user
         }

         performTransactions(); // user is now authenticated
         userAuthenticated = BankDatabase.ACCOUNT_NOTFOUND; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         screen.displayMessageLine("\nThank you! Goodbye!");
      }
   }

   // attempts to authenticate user against database
   private void authenticateUser() {
      screen.displayMessage("\nPlease enter your account number: ");
      int accountNumber = keypad.getInput(); // input account number
      screen.displayMessage("\nEnter your PIN: "); // prompt for PIN
      int pin = keypad.getInput(); // input PIN
      
      // set userAuthenticated to boolean value returned by database
      userAuthenticated = 
         bankDatabase.authenticateUser(accountNumber, pin);

      // check whether authentication succeeded
      switch (userAuthenticated) {
         case BankDatabase.ACCOUNT_NOTFOUND:
            screen.displayMessageLine(
                    "Invalid account number or PIN. Please try again.");
            break;
         case BankDatabase.ACCOUNT_BLOCKED:
            screen.displayMessageLine("Your account is blocked.");
            break;
         case BankDatabase.ACCOUNT_AVAILABLE:
            currentAccountNumber = accountNumber; // save user's account #
            break;
      }
   } 

   // display the main menu and perform transactions
   private void performTransactions() {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      
      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while (!userExited) {
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();

         // decide how to proceed based on user's menu selection
         switch (mainMenuSelection) {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY:
            case WITHDRAWAL:
            case DEPOSIT:
            case CHANGE_PIN:
            case TRANSFER:
               // initialize as new object of chosen type
               currentTransaction = 
                  createTransaction(mainMenuSelection);

               currentTransaction.execute(); // execute transaction
               break;
            case EXIT: // user chose to terminate session
               screen.displayMessageLine("\nExiting the system...");

               userExited = true; // this ATM session should end
               break;
            case UNBLOCK:
            case PEEK_DISPENSER:
            case ADD_DISPENSER:
               if (bankDatabase.isAdmin(currentAccountNumber)) {
                  currentTransaction =
                          createTransaction(mainMenuSelection);

                  currentTransaction.execute(); // execute transaction
                  break;
               }
            default: // 
               screen.displayMessageLine(
                  "\nYou did not enter a valid selection. Try again.");
               break;
         }
      } 
   } 

   // display the main menu and return an input selection
   private int displayMainMenu() {
      screen.displayMessageLine("\nMain menu:");
      screen.displayMessageLine("1 - View my balance");
      screen.displayMessageLine("2 - Withdraw cash");
      screen.displayMessageLine("3 - Deposit funds");
      screen.displayMessageLine("4 - Change pin");
      screen.displayMessageLine("5 - Transfer");
      screen.displayMessageLine("6 - Exit\n");

      if (bankDatabase.isAdmin(currentAccountNumber)) {
         screen.displayMessageLine("Admin Panel:");
         screen.displayMessageLine("7 - Unblock Nasabah");
         screen.displayMessageLine("8 - Peek cash dispenser");
         screen.displayMessageLine("9 - Insert to cash dispenser");
      }

      screen.displayMessage("\nEnter a choice: ");
      return keypad.getInput(); // return user's selection
   } 
         
   private Transaction createTransaction(int type) {
      Transaction temp = null; 
          
      switch (type) {
         case BALANCE_INQUIRY: 
            temp = new BalanceInquiry(
               currentAccountNumber, screen, bankDatabase);
            break;
         case WITHDRAWAL:
            temp = new Withdrawal(
               currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
            break;
         case DEPOSIT:
            temp = new Deposit(
               currentAccountNumber, screen, bankDatabase, keypad, depositSlot);
            break;
         case CHANGE_PIN:
            temp = new ChangePin(
               currentAccountNumber, screen, bankDatabase, keypad);
            break;
         case TRANSFER:
            temp = new Transfer(
               currentAccountNumber, screen, bankDatabase, keypad);
            break;
         case UNBLOCK:
            temp = new UnblockAccount(
               currentAccountNumber, screen, bankDatabase, keypad);
            break;
         case PEEK_DISPENSER:
            temp = new PeekDispenser(
               currentAccountNumber, screen, bankDatabase, cashDispenser);
            break;
         case ADD_DISPENSER:
            temp = new AddToCashDispenser(
               currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
            break;
      }
      return temp;
   } 
}