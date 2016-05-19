package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import model.ContestDatabaseManager;
import model.EntryDatabaseManager;
import model.User;
import model.UserDatabaseManager;
import view.LoginView;
import view.View;

/**
 * 
 * @author Tabi
 *
 */
public class MainController {
	

	/*   All the models:   */
	private final UserDatabaseManager myUserDBManager;
	private final ContestDatabaseManager myContestDBManager;
	private final EntryDatabaseManager myEntryDBManager;
	
	
	private final View myView;
	
	/**
	 * Creates a new controller that starts up and listens to a GUI using the given models.
	 */
	public MainController(UserDatabaseManager theUserDBManager, ContestDatabaseManager theContestDBManager,
							EntryDatabaseManager theEntryDBManager) {
		myUserDBManager = theUserDBManager;
		myContestDBManager = theContestDBManager;
		myEntryDBManager = theEntryDBManager;
		
		myView = new View();
		onLoginScreen();
	}
	
	/**
	 * Controlls user input and output from login screen. Upon succesful login, calls
	 * the {@link #onLogin(User)} method.
	 */
	private void onLoginScreen() {		
		LoginView aLoginView = myView.getLoginView();
		aLoginView.addLoginButtonListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aLoginView.addLoginFailText("");
				String cardNo = aLoginView.getCardNumber();
				String pass = aLoginView.getPin();				
				
				//check if both fields have stuff in 'em
				if (cardNo.length() < 1 || !tryParseInt(cardNo)) {
					aLoginView.addLoginFailText("Please enter a valid card number. (Digits only)");
				} else if (pass.length() < 1) { // username is ok. But is the password?
					aLoginView.addLoginFailText("Please enter a password.");
				} else { // both username and password are ok.
					User aUser = myUserDBManager.checkCredientals(Integer.parseInt(cardNo), pass);
					if (aUser != null) {
						onLogin(aUser);
					} else {
						aLoginView.addLoginFailText("Invalid credentials.");
					}
				}
				
			}			
		});
		myView.showPage(aLoginView);
	}
	
	/**Call this when a user successfully logs in. Right now it redirects all Users
	 * to the Contestant controller, but once we have different types of users
	 * we want to send them to their appropriate controller.*/
	private void onLogin(User theUser) {
		System.out.println("Success! Hello, " + theUser.getName() + "!");
		ContestantController aContestantController = new ContestantController(theUser, myContestDBManager, myEntryDBManager, myView);
		// Bad style, would be nice if we could use polymorphism. Ideas?
		/*if (theUser instanceof Contestant) {
			
		} else if (theUser instanceof Judge) {
		
		} else if (theUser.isAdmin()) {
		
		} else {
			// ????
		}*/
	}
	
	/**Returns true if tryToParse can be parsed into an integer.*/
	private boolean tryParseInt(String tryToParse) {
		try {
			Integer.parseInt(tryToParse);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
}