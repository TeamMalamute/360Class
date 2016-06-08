package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;


import model.Contest;
import model.Entry;
import model.Judge;
import model.JudgeDatabaseManager;

/**
 * A concrete implementation of a entries view for Judge.
 * 
 * @author Tabi
 * @author Lan
 */
public class JudgeEntryListViewImp implements JudgeEntryListView {
	/** Panel for the judge view. */
	private final JPanel myPanel;
	/** Label for the contest. */
	private final JLabel myContestLabel;
	/** Label for the result. */
	private final JLabel myEntryCountandResultlabel;

	/** Holding the list of judged entries. */
	private final JList<Entry> myList;
	/** List for first place. */
	private JComboBox<String> my1stRankingPlace;
	/** List for second place. */
	private JComboBox<String> my2ndRankingPlace;
	/** List for third place. */
	private JComboBox<String> my3rdRankingPlace;
	/** Button used to submit judged entries. */
	private JButton myJudgeSubmissionButton;
	
	/** Pop up preview. */
	private JFrame myPreviewPopup ;
	/** Be able to scroll through entries. */
	private JScrollPane myScrollPane;
	/** The entries that are listed. */
	private Entry[] myContestEntries;



	/** 
	 * Constructor() for JudgeEntryListViewImp.
	 */
	public JudgeEntryListViewImp() {
		myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		myList = new JudgeEntryList();	
		myScrollPane = new JScrollPane();
		
		myPreviewPopup = new JFrame();
		myContestLabel = new JLabel();
		myEntryCountandResultlabel = new JLabel();
		myContestLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		myEntryCountandResultlabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		myJudgeSubmissionButton = new JButton("Submit");
		myJudgeSubmissionButton.setEnabled(false);
		
		my1stRankingPlace = new JComboBox<String>();
		my2ndRankingPlace = new JComboBox<String>();
		my3rdRankingPlace = new JComboBox<String>();
		my1stRankingPlace.setToolTipText("1st rank");
		my1stRankingPlace.setToolTipText("2st rank");
		my1stRankingPlace.setToolTipText("3st rank");
		
		JPanel listContainerPanel = new JPanel();
		JPanel judgingPanel = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		panel1.setLayout(new GridLayout(2,1));
		panel2.setLayout(new GridLayout(2,1));
		panel3.setLayout(new GridLayout(2,1));
		
		panel1.add(new JLabel("1st place"));
		panel1.add(my1stRankingPlace);
		panel2.add(new JLabel("2nd place"));
		panel2.add(my2ndRankingPlace);
		panel3.add(new JLabel("3rd place"));
		panel3.add(my3rdRankingPlace);
	
		judgingPanel.setLayout(new GridLayout(1,4));
		judgingPanel.add(panel1);
		judgingPanel.add(panel2);
		judgingPanel.add(panel3);
		judgingPanel.add(myJudgeSubmissionButton);
		
		
		listContainerPanel.setLayout(new BoxLayout(listContainerPanel, BoxLayout.Y_AXIS));	
		listContainerPanel.add(JudgeEntryList.getColumnTitleHeader());
		listContainerPanel.add(new JScrollPane(myList));
		myPanel.add(myContestLabel);
		myPanel.add(myEntryCountandResultlabel);
		myPanel.add(listContainerPanel);
		myPanel.add(judgingPanel);
	}
	
	@Override
	public JPanel getView() {
		return myPanel;
	}

	@Override
	public void addEntryListListener(ListSelectionListener theListener) {
		myList.addListSelectionListener(theListener);
	}
	
	@Override
	public void setEntryList(Entry[] theEntry, Contest theContest) {
		String subString = "Entries from ";
		myList.setListData(theEntry);
		myContestLabel.setToolTipText(subString + theContest.getName());
		if(theContest.getName().length() > 30){
			myContestLabel.setText(subString + theContest.getName().substring(0, 30) + "...");
		}else {
			myContestLabel.setText(subString + theContest.getName());
		}
		if(theEntry.length == 0){
			myEntryCountandResultlabel.setText("(No Entry)");
			myEntryCountandResultlabel.setForeground(Color.RED);
		}else if (theEntry.length == 1) {
			myEntryCountandResultlabel.setText(theEntry.length + " Entry");
		}
		else{
			myEntryCountandResultlabel.setText(theEntry.length + " Entries");
		}
		myContestLabel.setForeground( new Color(65, 40, 118));
		myContestLabel.setFont(myContestLabel.getFont().deriveFont (15.0f));
		myContestEntries = theEntry;
		setupDropdownSubmissionRanking();
	}

	/** 
	 * Creates the drop down option for judging.
	 * 
	 * @param theEntry - the entries that will be judged.
	 */
	@SuppressWarnings("unchecked")
	private void setupDropdownSubmissionRanking() {
		@SuppressWarnings("rawtypes")
		JComboBox[] dropdownGroup = new JComboBox[]{my1stRankingPlace, 
				                                    my2ndRankingPlace, 
				                                    my3rdRankingPlace};
		String none = "None";
		for (int count = 0; count < dropdownGroup.length; count++) {
			dropdownGroup[count].addItem(none);
			for (int index = 0; index < myContestEntries.length; index++) {
				dropdownGroup[count].addItem(myContestEntries[index].getEntryName());
			}

			dropdownGroup[count].addActionListener(new ActionListener() {
				 
			    @Override
				public void actionPerformed(ActionEvent event) {
					myJudgeSubmissionButton.setEnabled(true);
					if (my1stRankingPlace.getSelectedItem().equals(my2ndRankingPlace.getSelectedItem())
							&& my2ndRankingPlace.getSelectedItem().equals(my3rdRankingPlace.getSelectedItem())
							&& my1stRankingPlace.getSelectedItem() .equals("None")) {
						myJudgeSubmissionButton.setEnabled(false);
					}
				}
			});
		}

		myJudgeSubmissionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (my1stRankingPlace.getSelectedItem().equals(none)) {
					JOptionPane.showMessageDialog(myPanel, "1st place cannot be empty", "Warning", JOptionPane.WARNING_MESSAGE);
				} else if (my1stRankingPlace.getSelectedItem().equals(my2ndRankingPlace.getSelectedItem())
						|| my2ndRankingPlace.getSelectedItem().equals(my3rdRankingPlace.getSelectedItem())
						|| my1stRankingPlace.getSelectedItem().equals(my3rdRankingPlace.getSelectedItem())) {
					if (my2ndRankingPlace.getSelectedItem().equals(none)) {
						if (my3rdRankingPlace.getSelectedItem().equals(none)) {
							myEntryCountandResultlabel.setText( myEntryCountandResultlabel.getText() + " JUDGED");
						} else {
							JOptionPane.showMessageDialog(myPanel, "One entry can't have two rankings", "Warning", JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(myPanel, "One entry can't have two rankings", "Warning", JOptionPane.WARNING_MESSAGE);
					}
				} else if (my2ndRankingPlace.getSelectedItem().equals(none)
						&& !(my3rdRankingPlace.getSelectedItem().equals(none))
						&& !(my1stRankingPlace.getSelectedItem() .equals(none))) {
					JOptionPane.showMessageDialog(myPanel, "2nd place cannot be empty while 3rd place is not empty", "Warning", JOptionPane.WARNING_MESSAGE);
				} else {
					myEntryCountandResultlabel.setText( myEntryCountandResultlabel.getText() + " JUDGED");
				}
			}
		});
	}

	@Override
	public void addPreview(Entry theEntry) {
		ImageIcon imageIcon = new ImageIcon(theEntry.getFilePath());
		JLabel logoLabel = new JLabel(imageIcon);
		myScrollPane.setViewportView(logoLabel);
		myPreviewPopup.setMinimumSize(new Dimension(500,500));
		myPreviewPopup.add(myScrollPane);
		myPreviewPopup.setVisible(true);
	}


	@Override
	public void addSubmitButtonListener(AbstractAction theAction) {
		myJudgeSubmissionButton.addActionListener(theAction);
	}

	/**
	 * Add the contest entries to the Judge profile and Judge Database.
	 * 
	 * @author Casey
	 * @param theJudge The Judge for this Contest.
	 * @param theJudgeDB The Judge Database.
	 * @param theContest The Contest to be Judged.
	 * @throws Exception.
     */
	@Override
	public void addJudged(Judge theJudge, JudgeDatabaseManager theJudgeDB, Contest theContest) throws Exception {
		setSelectedValues(theJudge);
		theJudge.addContestJudged(theJudgeDB);
		myJudgeSubmissionButton.setEnabled(false);
	}

	/**
	 * Update the judged contest.
	 * 
	 * @author Casey
	 * @param theJudge The Judge for this Contest.
	 * @param theJudgeDB The Judge Database.
	 * @param theContest The Contest to be Judged.
     */
	public void updateJudged(Judge theJudge, JudgeDatabaseManager theJudgeDB, Contest theContest) throws Exception {
		setSelectedValues(theJudge);
		theJudge.updateContestJudged(theJudgeDB);
	}


	/**
	 * Setup the Judged Contest.
	 * 
	 * @author Casey
	 * @param theJudge Sets the Judge values from previous judged Contest.
	 * @param theContest The Contest to be Judged.
	 * @param theEntries List of Entries for this contest.
     */
	public void setJudgedContest(Judge theJudge, Contest theContest, Entry[] theEntries) throws Exception {
		myJudgeSubmissionButton.setText("Update");
		myContestEntries = theEntries;
		List<Integer> theJudgedEntries = theJudge.getContestsJudged().get(theContest.getContestNumber());
		for (int i : theJudgedEntries) {
			for (Entry e : myContestEntries) {
				if (i == e.getEntryNumber()) {
					switch(i) {
						case 0:
							my1stRankingPlace.setSelectedItem(e.getEntryName());
							break;
						case 1:
							my2ndRankingPlace.setSelectedItem(e.getEntryName());
							break;
						case 2:
							my3rdRankingPlace.setSelectedItem(e.getEntryName());
							break;
						default:
							break;
					}
				}
			}
		}
	}

	/**
	 * Set the values for myJudge
	 * 
	 * @author Casey
	 * @param theJudge The judge.
	 */
	private void setSelectedValues(Judge theJudge) {
		// Set -1 for "None"
		theJudge.setMyFirst(-1);
		theJudge.setMySecond(-1);
		theJudge.setMyThird(-1);

		// Find the index for selected item
		for (Entry e : myContestEntries) {
			if (e.getEntryName().equals(my1stRankingPlace.getSelectedItem())) {
				theJudge.setMyFirst(e.getEntryNumber());
			}
			if (e.getEntryName().equals(my2ndRankingPlace.getSelectedItem())) {
				theJudge.setMySecond(e.getEntryNumber());
			}
			if (e.getEntryName().equals(my3rdRankingPlace.getSelectedItem())) {
				theJudge.setMyThird(e.getEntryNumber());
			}
		}
	}

}
