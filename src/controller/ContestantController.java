package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Contest;
import model.ContestDatabaseManager;
import model.Entry;
import model.EntryDatabaseManager;
import model.User;
import view.ContestantContestListView;
import view.ContestantContestView;
import view.View;
import view.Viewable;

/** Controls a session where a Contestant is logged in. 
 * @author Tabi
 * @author Casey (setupEntryView method only)
 */
public class ContestantController {
	
	protected static final Dimension IMAGE_SIZE = new Dimension(500, 500);
	private static final Dimension ORIGINAL_SIZE = new Dimension(500, 300);
	private final User myUser;
	private final ContestDatabaseManager myContestDBManager;
	private final EntryDatabaseManager myEntryDBManager;
	private final View myView;
	
	/**List of all views that have been displayed to this user since this controller
	 * was created.*/
	private final LinkedList<Viewable> viewHistory;
	

	/**
	 * Calling this constructor triggers all the appropriate listeners to be added to the view,
	 * so it is not necessary to save a reference to it in the calling class. By swapping out
	 * to a different view that this controller does not control and removing all listeners to
	 * the view's back button, all references to this class will be gone and it will be garbage
	 * collected as desired.
	 * @param theUser
	 * @param theContestDBManager	
	 * @param theEntryDBManager		
	 * @param theView				
	 */	
	public ContestantController(User theUser, ContestDatabaseManager theContestDBManager, EntryDatabaseManager theEntryDBManager, View theView) {
		myUser = theUser;
		myContestDBManager = theContestDBManager;
		myEntryDBManager = theEntryDBManager;
		myView = theView;
		viewHistory = new LinkedList<>();
		setupBackFunctionality();
		setupListView();		
	}
	
	@SuppressWarnings("serial")
	private void setupBackFunctionality() {
		myView.addBackButtonListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked back.");
				if (!viewHistory.isEmpty() && viewHistory.getLast() != null) {
					myView.showPage(viewHistory.pop());	
					System.out.println("Swapped page.");
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
	
	private void addToHistory(Viewable theViewable) {
		viewHistory.add(theViewable);
		myView.setBackButtonEnabled(true);
	}
	
	private void setupListView() {
		final ContestantContestListView cclv = myView.getContestantContestListView();
		refreshLists(cclv);

		cclv.addNoSubmissionMadeListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // http://stackoverflow.com/questions/12975460/listselectionlistener-invoked-twice
					Contest selected = cclv.getNoSubmissionMadeSelectedEntry();
					if (selected != null) {
						try {
							setupEntryView(selected, false, cclv);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						addToHistory(cclv);
					}
					cclv.clearNoSubmissionMadeSelection(); // so the user can re-select if desired
				}
				
			}
			
		});

		cclv.addSubmissionMadeListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					Contest selected = cclv.getSubmissionMadeSelectedEntry();
					if (selected != null) {
						try {
							setupEntryView(selected, true, cclv);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						addToHistory(cclv);
					}
					cclv.clearSubmissionMadeSelection(); // so the user can re-select if desired
				}
			}
			
		});
		
		
		myView.showPage(cclv);
	}
	
	/**Re-distributes the lists of contests submitted/contests not submitted to, 
	 * updating the given ContestantContestListView.*/
	private void refreshLists(ContestantContestListView cclv) {
		List<Contest> allSubmittedTo = new ArrayList<>();
		List<Contest> allNotSubmittedTo = new ArrayList<>();
		distributeContests(allSubmittedTo, allNotSubmittedTo);	
		cclv.setNoSubmissionMadeList(allNotSubmittedTo.toArray(new Contest[allNotSubmittedTo.size()]));
		cclv.setSubmissionMadeList(allSubmittedTo.toArray(new Contest[allSubmittedTo.size()]));
	}
	
	/**
	 * Distributes the contests that the user has submitted to to the submittedTo
	 * list, and those he hasn't to the notSubmittedTo list.
	 * @param allSubmittedTo
	 * @param allNotSubmittedTo
	 */
	private void distributeContests(List<Contest> allSubmittedTo, List<Contest> allNotSubmittedTo) {
    	allSubmittedTo.clear();
		allNotSubmittedTo.clear();
    	
    	// store ref to all contests
    	Map<Integer,Contest> allContests = myContestDBManager.getMap();

		// get all of User's entries
    	List<Entry> testUsersEntries = myUser.getEntries();
    	
    	// Put all contests into contestsNotSubmtitedTo
    	allNotSubmittedTo.addAll(myContestDBManager.getMap().values());
    	
    	// add all contests whose key matches that in the User's entries to list, removing
    	// from not submitted to list.
    	for (Entry e : testUsersEntries) {
    		Contest submittedTo = allContests.get(e.getContest());
    		if (submittedTo != null) {
    			allSubmittedTo.add(submittedTo);
    			allNotSubmittedTo.remove(submittedTo);
    		}
    	}
	}
	
	/**
	 * @author Casey
	 * @param theContest	The Contest to make/update a submission for.
	 * @param theSubMade	True if the user has already made a submission to theContest; false otherwise.
	 * @param cclv			The Contest list, so it can be refreshed when an entry is made/updated
	 * @throws IOException
	 */
	@SuppressWarnings("serial")
	private void setupEntryView(final Contest theContest, Boolean theSubMade, final ContestantContestListView cclv) throws IOException {
		final ContestantContestView ccv = myView.getContestantContestView();
		ccv.setContestName(theContest.getName());
		ccv.addBrowseButtonListener(new AbstractAction() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Boolean fileSuccess = ccv.setEntryFileName();
					if (fileSuccess) myView.reSize(IMAGE_SIZE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		ccv.addSubmitButtonListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean submitSuccess = ccv.submitNewEntry(myUser, myEntryDBManager, theContest);
				if (submitSuccess == true){
					refreshLists(cclv);
					myView.showPage(cclv);
					myView.reSize(ORIGINAL_SIZE);
				}
			}			
		});
		
		myView.showPage(ccv);
		if (theSubMade) {
			ccv.subMade(myUser, theContest);
			myView.reSize(IMAGE_SIZE);
		}
	}
	
}
