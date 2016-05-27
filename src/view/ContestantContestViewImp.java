package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Contest;
import model.Entry;
import model.EntryDatabaseManager;
import model.User;

/**A view for submitting an entry to a certain contest.*/
public class ContestantContestViewImp implements ContestantContestView {
	
	private final JPanel myPanel;
	private final JButton myBrowseButton;
	private final JButton mySubmitButton;
	private final JLabel myContestName;
	private final JLabel myEntryNamelbl;
	private final JLabel myImagelbl;
	private final JLabel mySpacer1;
	private final JLabel mySpacer2;
	private final JLabel mySpacer3;
	private final JTextField myEntryFilePath;
	private final JTextField myEntryText;
	private File myEntryFile;
	
	public ContestantContestViewImp() {
		myPanel = new JPanel();
		myEntryFile = null;
		myBrowseButton = new JButton("Browse...");
		mySubmitButton = new JButton("Submit Entry");
		myEntryNamelbl = new JLabel("Entry Name");
		myContestName = new JLabel();
		myImagelbl = new JLabel();
		mySpacer1 = new JLabel("                                                                                     ");
		mySpacer2 = new JLabel("                 ");		
		mySpacer3 = new JLabel("                      ");
		myEntryFilePath = new JTextField(25);		
		myEntryText = new JTextField(25);
		
		
		GUI();
	}
	
	public void GUI(){
		myEntryFilePath.setEditable(false);
		myImagelbl.setVisible(false);
		
		myContestName.setFont(new Font(myContestName.getName(), Font.PLAIN, 20));
		
		myPanel.add(myContestName);
		myPanel.add(mySpacer1);
		myPanel.add(myEntryNamelbl);
		myPanel.add(myEntryText);
		myPanel.add(mySpacer2);
		myPanel.add(myBrowseButton);
		myPanel.add(myEntryFilePath);
		myPanel.add(myImagelbl);
		myPanel.add(mySpacer3);
		myPanel.add(mySubmitButton);
		
	}

	@Override
	public JPanel getView() {
		return myPanel;
	}



	@Override
	public void addBrowseButtonListener(AbstractAction theAction) {
		myBrowseButton.addActionListener(theAction);
	}
	
	
	@Override
	public void addSubmitButtonListener(AbstractAction theAction) {
		mySubmitButton.addActionListener(theAction);
		
	}


	@Override
	public void setContestName(String theContestName) {
		myContestName.setText(theContestName + " Contest");
		
	}
	
	
	@Override
	public void setEntryFileName() throws IOException {
		JFileChooser jfc = new JFileChooser();
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			myEntryFile = jfc.getSelectedFile();
			myEntryFilePath.setText(myEntryFile.getAbsolutePath());
		}
		myImagelbl.setIcon(new ImageIcon(ImageIO.read(new File(myEntryFilePath.getText()))));
		myImagelbl.setVisible(true);
	}

	@Override
	public Boolean submitNewEntry(User theUser, EntryDatabaseManager theEntryDataBaseManager,
			Contest theContest) {
		Boolean success = false;
		if (myEntryText.getText().isEmpty() || myEntryFilePath.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(myPanel,
				    "Entry Fields Required",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			Entry e = new Entry(theEntryDataBaseManager.getTotalEntries() + 1, 
				theUser.getCardNumber(), myEntryFilePath.getText(), 
				theContest.getContestNumber(), myEntryText.getText());
		
			theUser.addEntry(e, theEntryDataBaseManager);
			JOptionPane.showMessageDialog(myPanel,
				    "Entry Submitted!");
			success = true;
		}
		return success;
	}
}
