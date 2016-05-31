package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Contest;
import model.Entry;
import model.EntryDatabaseManager;
import model.User;

/**A view for submitting an entry to a certain contest.
 * @author Casey
 */
public class ContestantContestViewImp implements ContestantContestView {
	
	private static final int SUBMIT_CHOICE = 100;
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
	private final JScrollPane myScrollPane;
	private File myEntryFile;
	private Boolean mySubMade;
	ArrayList<String> myImgTypes;
	
	public ContestantContestViewImp() {
		myEntryFile = null;
		mySubMade = false;
		myImgTypes = new ArrayList<>();
		myImgTypes.add("gif");
		myImgTypes.add("jpeg");
		myImgTypes.add("jpg");
		myImgTypes.add("png");
		
		myPanel = new JPanel();
		myBrowseButton = new JButton("Browse...");
		mySubmitButton = new JButton("Submit Entry");
		myEntryNamelbl = new JLabel("Entry Name");
		myContestName = new JLabel();
		myImagelbl = new JLabel();
		mySpacer1 = new JLabel("                                                 " +
				               "                                                    ");
		mySpacer2 = new JLabel("                 ");
		mySpacer3 = new JLabel("                      ");
		myEntryFilePath = new JTextField(25);		
		myEntryText = new JTextField(25);
		myScrollPane = new JScrollPane(myImagelbl);
		
		GUI();
	}
	
	private void GUI(){
		myEntryFilePath.setEditable(false);
		myImagelbl.setVisible(false);
		myScrollPane.setPreferredSize(new Dimension(230, 230));
		myScrollPane.setVisible(false);
		
		myContestName.setFont(new Font(myContestName.getName(), Font.PLAIN, 20));
		
		myPanel.add(myContestName);
		myPanel.add(mySpacer1);
		myPanel.add(myEntryNamelbl);
		myPanel.add(myEntryText);
		myPanel.add(mySpacer2);
		myPanel.add(myBrowseButton);
		myPanel.add(myEntryFilePath);
		myPanel.add(myScrollPane, BorderLayout.CENTER);
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
	public Boolean setEntryFileName() throws IOException {
		Boolean theFileSuccess = false;
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("Image files (*.gif, *.jpeg, *.jpg, *.png)", 
				"gif", "jpeg", "jpg", "png"));
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			myEntryFile = jfc.getSelectedFile();
			String theFileType = myEntryFile.getAbsolutePath();
			theFileType = theFileType.substring(theFileType.lastIndexOf('.') + 1);
			if (myImgTypes.contains(theFileType)) {
				myEntryFilePath.setText(myEntryFile.getAbsolutePath());
				myImagelbl.setIcon(new ImageIcon(ImageIO.read(new File(myEntryFilePath.getText()))));
				myScrollPane.setVisible(true);
				myImagelbl.setVisible(true);
				myPanel.revalidate();
				theFileSuccess = true;
			}
			else {
				JOptionPane.showMessageDialog(myPanel,
					    "Invalid File Type",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
		return theFileSuccess;
	}

	@Override
	public Boolean submitNewEntry(User theUser, EntryDatabaseManager theEntryDatabase,
			Contest theContest) {
		Boolean theSubmitSuccess = false;
		if (myEntryText.getText().isEmpty() || myEntryFilePath.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(myPanel,
				    "Entry Fields Required",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			int choice = SUBMIT_CHOICE;
			if (mySubMade){
				choice = JOptionPane.showConfirmDialog(myPanel,
					    "Previous Entry will be overwritten, continue?",
					    "Warning",
					    JOptionPane.YES_NO_OPTION);
				if (choice == 0) {
					for (Entry e : theUser.getEntries()){
						if (e.getContest() == theContest.getContestNumber()){
							Entry theEntry = new Entry(e.getEntryNumber(), 
									theUser.getCardNumber(), myEntryFilePath.getText(), 
									theContest.getContestNumber(), myEntryText.getText());
							try {
								theUser.updateEntry(theUser.getEntries().indexOf(e), theEntry, 
										theEntryDatabase);
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}						
					}					
					JOptionPane.showMessageDialog(myPanel,
						    "Entry Updated!");
					theSubmitSuccess = true;
				}
			}
			else{
				Entry e = new Entry(theEntryDatabase.getItemCount() + 1,
					theUser.getCardNumber(), myEntryFilePath.getText(), 
					theContest.getContestNumber(), myEntryText.getText());
			
				try {
					theUser.addEntry(e, theEntryDatabase);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(myPanel,
					    "Entry Submitted!");
				theSubmitSuccess = true;
			}			
		}
		return theSubmitSuccess;
	}

	@Override
	public void subMade(User theUser, Contest theContest) throws IOException {
		mySubMade = true;
		mySubmitButton.setText("Update Entry");
		for (Entry e : theUser.getEntries()){
			if (e.getContest() == theContest.getContestNumber())
			{
				myEntryText.setText(e.getEntryName());
				myEntryFilePath.setText(e.getFilePath());
				myImagelbl.setIcon(new ImageIcon(ImageIO.read(new File(myEntryFilePath.getText()))));
				myScrollPane.setVisible(true);
				myImagelbl.setVisible(true);
				myPanel.revalidate();
			}
		}		
	}	
}
