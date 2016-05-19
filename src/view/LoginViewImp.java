package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/** An implementation of LoginView. */
public class LoginViewImp implements LoginView {
	
	private final JPanel myPanel;
	private final JTextField myCardNoField;
	private final JTextField myPassField;
	private final JButton mySubmitButton;
	private final JLabel myLoginFailMessage;

	
	public LoginViewImp() {
		myPanel = new JPanel(new GridBagLayout());		

		myCardNoField = new JTextField();
		myPassField = new JPasswordField();
		mySubmitButton = new JButton("Login");
		myLoginFailMessage = new JLabel();
		myLoginFailMessage.setForeground(Color.red);
		
		JPanel fields = new JPanel();
		fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
		fields.add(myCardNoField);
		fields.add(myPassField);
		fields.add(mySubmitButton);
		fields.add(myLoginFailMessage);
		fields.setBorder(BorderFactory.createTitledBorder("Login"));
		
		GridBagConstraints c = new GridBagConstraints();		
		// make contents fill width of panel:
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1; 
		c.weighty= 0;
		myPanel.add(fields, c);
		
		// Filler component to push login panel to top
		c.weighty= 1;
		c.gridy = 1;
		myPanel.add(new JPanel(new BorderLayout()), c);
	}
	
	@Override
	public JPanel getView() {
		return myPanel;
	}

	@Override
	public void addLoginButtonListener(AbstractAction theAction) {
		mySubmitButton.addActionListener(theAction);
	}

	@Override
	public String getCardNumber() {
		String cardNo = myCardNoField.getText();
		return cardNo != null ? cardNo : "";
	}

	@Override
	public String getPin() {
		String pin = myPassField.getText();
		return pin != null ? pin : "";
	}


	@Override
	public void addLoginFailText(String theText) {
		myLoginFailMessage.setText(theText);
	}
	
}
