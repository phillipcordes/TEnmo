package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.view.ConsoleService;

import java.lang.reflect.AccessibleObject;
import java.math.BigDecimal;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    AccountService accountService = new AccountService(API_BASE_URL);
    TransferService transferService = new TransferService(API_BASE_URL);

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
		String token = currentUser.getToken();  //give this to the server
		BigDecimal balance = accountService.viewCurrentBalance(token);
		System.out.println("****************************************");
		System.out.println("Your current balance is: " + balance);
		System.out.println("****************************************");
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
		//Request to view transaction history
		System.out.println("*********************************************************************************");
		System.out.println("Please enter the transfer ID of the transaction you wish to view details of: " );
		System.out.println("*********************************************************************************");
		//show log of completed transactions
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		//Request to view pending requests
		System.out.println("*********************************************************************************");
		System.out.println("Please enter the transfer ID of the transaction you wish to view details of: " );
		System.out.println("*********************************************************************************");
		//show log of pending requests


	}

	private void sendBucks() {
		// TODO Auto-generated method stub
		//call method and loop through user list and present list of all users
		System.out.println("*************************************************************************");
		transferService.listUsers();
		System.out.println("");
		System.out.println("*************************************************************************");
		System.out.println("Please enter the userID of the person you wish to send TE Bucks to: " ); //enter in userId
		System.out.println("*************************************************************************");
		//enter in how many TE Bucks to send
		System.out.println("*************************************************************************");
		System.out.println("Please enter the amount of TE Bucks you wish to send: " );
		System.out.println("*************************************************************************");
		//process of decreasing sender's account by specified TE Bucks amount and increasing receiver's account by that amount
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		//call method and loop through user list//call method and loop through user list and present list of all users
		System.out.println("*************************************************************************");
		transferService.listUsers();
		System.out.println("*************************************************************************");
		System.out.println("Please enter the userID of the person you wish to request TE bucks from: ");
		System.out.println("*************************************************************************");
		//enter in userId

		//enter in how many TE Bucks to request
		System.out.println("*************************************************************************");
		System.out.println("Please enter the amount of TE Bucks you wish to request: " );
		System.out.println("*************************************************************************");
		//process of increasing sender's account by specified TE Bucks amount and decreasing receiver's account by that amount...
		//Or do we put this in as a pending request?

	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}





}
