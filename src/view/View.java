package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * @author Tabi
 *
 */
public class View {
	
	
	private final JFrame myFrame;
	/**A panel containing everything.*/
	private final JPanel contentPanel;
	/**A panel displaying persisting information at the top of the screen.*/
	private final JPanel headRegion;
	/**A button for page navigation that goes in the headRegion.*/
	private final JButton backButton;
	/**A button for logging out that goes in the headregion.*/
	private final JButton logoutButton;
	/**The panel where the current page is displayed.*/
	private final JPanel pageRegion;

	/**
	 * 
	 * @param topBannerText The text to display in the banner.
	 */
	public View() {		
		myFrame = new JFrame();
		contentPanel = new JPanel(new BorderLayout());
		headRegion = new JPanel(new BorderLayout());
		backButton = new JButton("Back");
		logoutButton = new JButton("Log out");
		pageRegion = new JPanel(new BorderLayout());
		createGui();
	}
	
	
	private void createGui() {		
		myFrame.setLocationRelativeTo(null);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPanel.add(headRegion, BorderLayout.NORTH);
		setupHeader();
		contentPanel.add(pageRegion, BorderLayout.CENTER);
		myFrame.setContentPane(contentPanel);		
		myFrame.setPreferredSize(new Dimension(500,300));
		myFrame.setResizable(false);
		myFrame.setTitle("Library Contest App");
		myFrame.setVisible(true);
	}
	
	public void reSize(Dimension theDimension){		
		myFrame.setResizable(true);
		myFrame.setSize(theDimension);
		myFrame.setResizable(false);
	}
	
	
	/**Adds a listener for when the back button is clicked, and enables the button.*/
	public void addBackButtonListener(Action theAction) {
		backButton.addActionListener(theAction);
		backButton.setEnabled(true);
	}
	
	
	/**Removes the listeners for when the back button is clicked.*/
	public void removeBackButtonListeners() {
		for (ActionListener theAction : backButton.getActionListeners()) {
			backButton.removeActionListener(theAction);
		}
		backButton.setEnabled(false);
	}
	
	/**Enables/Disables the back button.*/
	public void setBackButtonEnabled(boolean enable) {
		backButton.setEnabled(enable);
	}
	
	/**Adds a listener for when logout button is clicked, and enables the button, and disables the button.*/
	public void addLogoutButtonListener(Action theAction) {
		logoutButton.addActionListener(theAction);
		logoutButton.setEnabled(true);
	}
	
	/**Removes the listeners for when logout button is clicked, and disables the button.*/
	public void removeLogoutButtonListeners() {
		for (ActionListener theAction : logoutButton.getActionListeners()) {
			logoutButton.removeActionListener(theAction);
		}
		logoutButton.setEnabled(false);
	}
	
	
	/**Displays the given Page in the pageRegion.*/
	public void showPage(Viewable thePage) {
		pageRegion.removeAll();
		//pageRegion.revalidate();
		pageRegion.add(thePage.getView(), BorderLayout.CENTER);
		pageRegion.revalidate();
		pageRegion.repaint();
		myFrame.pack();
	}
	
	
	/**Returns a LoginView object so the controller may interact with it.*/
	public LoginView getLoginView() {
		return new LoginViewImp();
	}
	
	/** Returns a ContestantContestListView object so the controller may interact with it. */
	public ContestantContestListView getContestantContestListView() {
		return new ContestantContestListViewImp();
	}
	
	/** Returns a ContestantContestView object so the controller may interact with it. */
	public ContestantContestView getContestantContestView() {
		return new ContestantContestViewImp();
	}
	

	
	/**Sets up a panel that persists at the top of the view with a back button
	 * for viewing previous pages visited.*/
	private void setupHeader() {
		JLabel appBanner = new JLabel("Clark County Libraries Contest App");
		appBanner.setHorizontalAlignment(SwingConstants.CENTER);
		// Set font size
		appBanner.setFont(appBanner.getFont().deriveFont (20.0f));
		headRegion.add(appBanner, BorderLayout.CENTER);
		
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		
		JLabel logoLabel = new JLabel();
		// Create and resize the logo
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("ex_logo.jpg").getImage().getScaledInstance(62, 62, Image.SCALE_DEFAULT));
		logoLabel.setIcon(imageIcon);
		westPanel.add(logoLabel);
		backButton.setEnabled(false);
		westPanel.add(backButton);
		logoutButton.setEnabled(false);
		eastPanel.add(logoutButton);
		headRegion.add(westPanel, BorderLayout.WEST);
		headRegion.add(eastPanel, BorderLayout.EAST);
	}
	
}
