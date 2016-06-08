package view;

import javax.swing.AbstractAction;
import javax.swing.event.ListSelectionListener;

import model.Contest;
import model.Entry;
import model.Judge;
import model.JudgeDatabaseManager;

/**
 * Interface of a entries view for Judge.
 * 
 * @author Lan
 * @author Casey
 */
public interface JudgeEntryListView extends Viewable {

	/** Adds a listener for the list of entries. */
	public void addEntryListListener(ListSelectionListener theListener);

	/**
	 * @param theEntry The entry that will be added
	 * @param theContest The contest that the entry will be added to. */
	public void setEntryList(Entry[] theEntry, Contest theContest);

	/**
	 * @param theSeclectedEntry The entry that is selected. */
	public void addPreview(Entry theSeclectedEntry);

	/** Set the action for then the submit entry button is clicked. */
	public void addSubmitButtonListener(AbstractAction theAction);

	/** Add the contest judged. */
	public void addJudged(Judge theJudge, JudgeDatabaseManager theJudgeDB, Contest theContest) throws Exception;

	/** Add the contest judged. */
	public void updateJudged(Judge theJudge, JudgeDatabaseManager theJudgeDB, Contest theContest) throws Exception;

	/** If a Contest has already been judged by theJudge set values. */
	void setJudgedContest(Judge theJudge, Contest theContest, Entry[] theEntries) throws Exception;
}
