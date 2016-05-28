package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
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
 * @author Casey (setupEntryView method only)*/
public class ContestantController {
	
	private static final Dimension ORIGINAL_SIZE = new Dimension(500, 300);
	private static final int ORIGINAL_WIDTH = 500;
	private static final int IMAGE_BUFFER = 180;
	private final User myUser;
	private final ContestDatabaseManager myContestDBManager;
	private final EntryDatabaseManager myEntryDBManager;
	private final View myView;
	private Dimension myImageSize;
	
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
		myImageSize = ORIGINAL_SIZE;
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
		ContestantContestListView cclv = myView.getContestantContestListView();
		
		List<Contest> allSubmittedTo = new ArrayList<>();
		List<Contest> allNotSubmittedTo = new ArrayList<>();
		distributeContests(allSubmittedTo, allNotSubmittedTo);		

		cclv.setNoSubmissionMadeList(allNotSubmittedTo.toArray(new Contest[allNotSubmittedTo.size()]));
		cclv.addNoSubmissionMadeListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // http://stackoverflow.com/questions/12975460/listselectionlistener-invoked-twice
					Contest selected = cclv.getNoSubmissionMadeSelectedEntry();
					if (selected != null) {
						try {
							setupEntryView(selected, false);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						addToHistory(cclv);
					}
					cclv.clearNoSubmissionMadeSelection(); // so the user can re-select if desired
				}
				
			}
			
		});
		
		cclv.setSubmissionMadeList(allSubmittedTo.toArray(new Contest[allSubmittedTo.size()]));
		cclv.addSubmissionMadeListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					Contest selected = cclv.getSubmissionMadeSelectedEntry();
					if (selected != null) {
						try {
							setupEntryView(selected, true);
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
    	Map<Integer,Contest> allContests = myContestDBManager.getContestMap();    	

		// get all of User's entries
    	List<Entry> testUsersEntries = myUser.getEntries();
    	
    	// Put all contests into contestsNotSubmtitedTo
    	allNotSubmittedTo.addAll(myContestDBManager.getContestMap().values());   
    	
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
	
	
	@SuppressWarnings("serial")
	private void setupEntryView(Contest theContest, Boolean theSubMade) throws IOException {
		String theEntryName = null;
		String theEntryFilePath = null;
		ContestantContestView ccv = myView.getContestantContestView();
		ccv.setContestName(theContest.getName());
		ccv.addBrowseButtonListener(new AbstractAction() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					myImageSize = ccv.setEntryFileName();
					if (!myImageSize.equals(ORIGINAL_SIZE)) myView.reSize(myImageSize);
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
					setupListView();
					if (!myImageSize.equals(ORIGINAL_SIZE)) myView.reSize(ORIGINAL_SIZE);
				}
			}
			
		});
		myView.showPage(ccv);
		if (theSubMade) {
			for (Entry e : myUser.getEntries()){
				if (e.getContest() == theContest.getContestNumber())
				{
					theEntryName = e.getEntry();
					theEntryFilePath = e.getFilePath();
				}
			}	
			ImageIcon theImageIcon = new ImageIcon(ImageIO.read(new File(theEntryFilePath)));
			myImageSize = new Dimension(ORIGINAL_WIDTH,
					theImageIcon.getIconHeight() + IMAGE_BUFFER);
			if (!myImageSize.equals(ORIGINAL_SIZE)) myView.reSize(myImageSize);
			ccv.subMade(theEntryName, theEntryFilePath);
		}
	}
	
}
