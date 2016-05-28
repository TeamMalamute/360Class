package view;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.AbstractAction;
import model.Contest;
import model.EntryDatabaseManager;
import model.User;

public interface ContestantContestView extends Viewable {
	
	/**Set the action for when the browse for files button is clicked.*/
	public void addBrowseButtonListener(AbstractAction theAction);
	
	/** Set the action for then the submit entry button is clicked. */
	public void addSubmitButtonListener(AbstractAction theAction);
	
	/**Sets a name for the contest to display.*/
	public void setContestName(String theContestName);	
	
	/** Opening and getting input from file chooser for entry submission 
	 * @return 
	 * @throws IOException */
	public Dimension setEntryFileName() throws IOException;
	
	/** Submit Entry to Contest 
	 * @return */
	public Boolean submitNewEntry(User theUser, EntryDatabaseManager theEntryDataBaseManager,
			Contest theContest);

	/** If submission has been made for contest update CCV with pertinent info. 
	 * @throws IOException */
	public void subMade(String theEntryName, String theEntryFilePath) throws IOException;
	
}
