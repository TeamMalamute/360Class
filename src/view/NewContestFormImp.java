package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Contest;


public class NewContestFormImp implements NewContestForm {
	
	private final JPanel myPanel;
	private JTextField myNameField;
	private JTextField myDescriptionField;
	private JTextField myStartDateField;
	private JTextField myEndDateField;
	private JButton mySubmitButton;
	private JLabel myErrorMsgLabel;
	
	public NewContestFormImp() {
		myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		myNameField = new JTextField();	
		myDescriptionField = new JTextField();
		myStartDateField = new JTextField();
		myEndDateField = new JTextField();
		
		mySubmitButton = new JButton("Create contest");
		mySubmitButton.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		myErrorMsgLabel = new JLabel("");
		myErrorMsgLabel.setForeground(Color.RED);
		
		myPanel.add(makeFieldPanel());
		myPanel.add(mySubmitButton);
		myPanel.add(myErrorMsgLabel);
	}
	
	/**
	 * Creates and returns the panel containing all the text fields with labels before
	 * of them.
	 * @return
	 */
	private JPanel makeFieldPanel() {
		JLabel nameLabel = new JLabel("Name", JLabel.RIGHT);
		JLabel descLabel = new JLabel("Description", JLabel.RIGHT);
		JLabel sDateLabel = new JLabel("Start Date (mm/dd/yyyy)", JLabel.RIGHT);
		JLabel eDateLabel = new JLabel("End Date (mm/dd/yyyy)", JLabel.RIGHT);
		
		JTextField[] fields = new JTextField[] {myNameField, myDescriptionField, myStartDateField, myEndDateField};
		JLabel[] labels = new JLabel[] {nameLabel, descLabel, sDateLabel, eDateLabel};
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		
		for (int i = 0; i < fields.length; i++) {
			c.weightx = 0; c.gridx = 0; c.gridy = i; c.anchor = GridBagConstraints.LINE_END;
			panel.add(labels[i], c);
			c.gridx = 1; c.weightx = 1; c.anchor = GridBagConstraints.LINE_START;
			panel.add(fields[i], c);
		}

		panel.setBorder(BorderFactory.createTitledBorder("Create a new Contest"));
		return panel;
	}


	@Override
	public JPanel getView() {
		return myPanel;
	}

	@Override
	public String[] getFormInfo() {
		return new String[]{myNameField.getText(), myDescriptionField.getText(),
							myStartDateField.getText(), myEndDateField.getText()};
	}

	@Override
	public void addSubmitListener(AbstractAction theAction) {
		mySubmitButton.addActionListener(theAction);
	}

	@Override
	public void setErrorMessage(String theString) {
		myErrorMsgLabel.setText(theString);
	}


}