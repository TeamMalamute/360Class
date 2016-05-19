package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BoxLayout;
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
		myFrame.setVisible(true);
	}
	
	
	/**Adds a listener for when the back button is clicked.*/
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
	
	
	/**Displays the given Page in the pageRegion.*/
	public void showPage(Viewable thePage) {
		pageRegion.removeAll();
		pageRegion.add(thePage.getView(), BorderLayout.CENTER);
		pageRegion.revalidate();
		myFrame.pack();
	}
	
	
	/**Returns a LoginView object so the controller may interact with it.*/
	public LoginView getLoginView() {
		return new LoginViewImp();
	}
	
	public ContestantContestListView getContestantContestListView() {
		return new ContestantContestListViewImp();
	}
	
	public ContestantContestView getContestantContestView() {
		return new ContestantContestViewImp();
	}

	
	/**Sets up a panel that persists at the top of the view with a back button
	 * for viewing previous pages visited.*/
	private void setupHeader() {
		JLabel appBanner = new JLabel("Clark County Libraries Contest App");
		appBanner.setHorizontalAlignment(SwingConstants.CENTER);
		headRegion.add(appBanner, BorderLayout.CENTER);
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		JLabel logoLabel = new JLabel("LOGO");
		westPanel.add(logoLabel);
		backButton.setEnabled(false);
		westPanel.add(backButton);
		headRegion.add(westPanel, BorderLayout.WEST);
	}
	
}