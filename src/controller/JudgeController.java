package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Contest;
import model.ContestDatabaseManager;
import model.Entry;
import model.EntryDatabaseManager;
import model.Judge;
import model.JudgeDatabaseManager;
import view.JudgeContestListView;
import view.JudgeEntryListView;
import view.View;
import view.Viewable;


/**
 * An Judge can view contests and pick the winner(s).
 * 
 * @author Tabi
 * @author Lan
 * @author Casey
 */
public class JudgeController {
	
	/** Judge View. */
	private final View myView;
	/** The Judge. */
	private final Judge myJudge;
	/** The Contest database. */
	private final ContestDatabaseManager myContestDBManager; 
	/** The Entry database. */
	private final EntryDatabaseManager myEntryDBManager;
	/** The Judge database. */
	private final JudgeDatabaseManager myJudgeDBManager;
	/** The Judge flag. */
	private boolean myContestJudged = false;
	
	/** 
	 * List of all views that have been displayed to this user since this controller
	 * was created.
	 */
	private final LinkedList<Viewable> viewHistory;
	
	/** 
	 * Constructor() for the JudgeController.
	 * 
	 * @param theJudge - The judge logged in.
	 * @param theContestDatabaseManager - The Contest database.
	 * @param theEntryDatabaseManager - The Entry database.
	 * @param theJudgeDatabaseManager - The Judge database.
	 * @param theView - The view to use.
	 */
	public JudgeController(Judge theJudge, ContestDatabaseManager theContestDatabaseManager,
							EntryDatabaseManager theEntryDatabaseManager,
						    JudgeDatabaseManager theJudgeDatabaseManager,
						    View theView) {
		myView = theView;
		myJudge = theJudge;
		myContestDBManager = theContestDatabaseManager;
		myEntryDBManager = theEntryDatabaseManager;
		myJudgeDBManager = theJudgeDatabaseManager;
		viewHistory = new LinkedList<>();
		setupBackFunctionality();
		setupListView();
	}
	
	/**
	 * Setup Back button.
	 */
	@SuppressWarnings("serial")
	private void setupBackFunctionality() {
		myView.addBackButtonListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!viewHistory.isEmpty() && viewHistory.getLast() != null) {
					myView.showPage(viewHistory.pop());	
				}
				if (viewHistory.isEmpty()) {
					myView.setBackButtonEnabled(false);
				}
			}			
		});
		if (viewHistory.isEmpty()) {
			myView.setBackButtonEnabled(false);
		}
	}

	/** 
	 * Updates history.
	 * 
	 * @param theViewable View that has been used.
	 */
	private void addToHistory(Viewable theViewable) {
		viewHistory.add(theViewable);
		myView.setBackButtonEnabled(true);
	}

	/** 
	 * Creates the view.
	 */
	private void setupListView() {
		final JudgeContestListView ClistView = myView.getJugdgeContestListView();
		ClistView.setContestList(allContests());
		ClistView.addContestListListener(new ListSelectionListener() {

			// Show the entries from the contest
			@SuppressWarnings("serial")
			@Override
			public void valueChanged(ListSelectionEvent Event) {
				try {
					if (!Event.getValueIsAdjusting()) {
						JudgeEntryListView ElistView = myView.getJugdgeEntryListView();
						@SuppressWarnings("unchecked")
						Contest selectedContest = ((JList<Contest>) Event.getSource()).getSelectedValue();
						if (selectedContest != null) {
							if (myJudge.getContestsJudged().get(selectedContest.getContestNumber()) != null) {
								Entry[] theEntries = getEntries(selectedContest.getContestNumber());
								ElistView.setJudgedContest(myJudge, selectedContest, theEntries);
								myContestJudged = true;
							}
							else {
								myContestJudged = false;
							}
							ElistView.setEntryList(getEntries(selectedContest.getContestNumber()), selectedContest);
							ElistView.addEntryListListener(new ListSelectionListener() {
								@Override
								public void valueChanged(ListSelectionEvent Event) {
									if (!Event.getValueIsAdjusting()) {
										@SuppressWarnings("unchecked")
										Entry selectedEntry = ((JList<Entry>) Event.getSource()).getSelectedValue();
										ElistView.addPreview(selectedEntry);
									}
								}
							});
							ElistView.addSubmitButtonListener(new AbstractAction() {

								@Override
								public void actionPerformed(ActionEvent e) {
									myJudge.setMyContestNumber(selectedContest.getContestNumber());
									try {
										if (myContestJudged) {
											ElistView.updateJudged(myJudge, myJudgeDBManager, selectedContest);
										} else {
											ElistView.addJudged(myJudge, myJudgeDBManager, selectedContest);
										}
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							});
							myView.showPage(ElistView);
							addToHistory(ClistView);
							ClistView.clearSelection();
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		myView.showPage(ClistView);
	}

	/** 
	 * Gets all the contest that have been submitted.
	 * 
	 * @return the contests.
	 */
	private Contest[] allContests() {
		List<Contest> contests = myContestDBManager.getAllItems();
		return contests.toArray(new Contest[contests.size()]);
	}

	/** 
	 * What entries have been submitted.
	 * 
	 * @param theContestNumber 
	 * @return gets all entries that have been submitted.
	 */
    private Entry[] getEntries(int theContestNumber) {
        List<Entry> entries = new ArrayList<>();
        for(int index = 0; index < myEntryDBManager.getAllItems().size(); index++){
            if(myEntryDBManager.getAllItems().get(index).getContest() == theContestNumber){
                entries.add(myEntryDBManager.getAllItems().get(index));
            }
        }
        return entries.toArray(new Entry[entries.size()]);
    }
}

		
		
	

