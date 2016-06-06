package view;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import model.Contest;

public class JudgeContestListViewImp implements JudgeContestListView {
	private final JPanel myPanel;
	private final JList<Contest> myList;
	
	public JudgeContestListViewImp() {
		myPanel = new JPanel();
		myList = new ContestList();	
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		
		JPanel listContainerPanel = new JPanel();
		listContainerPanel.setLayout(new BoxLayout(listContainerPanel, BoxLayout.Y_AXIS));	
		listContainerPanel.add(ContestList.getColumnTitleHeader());
		listContainerPanel.add(new JScrollPane(myList));
		listContainerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		myPanel.add(listContainerPanel);
	}
	
	@Override
	public JPanel getView() {
		return myPanel;
	}

	@Override
	public void addContestListListener(ListSelectionListener theListener) {
		myList.addListSelectionListener(theListener);
	}

	@Override
	public void setContestList(Contest[] theContest) {
		myList.setListData(theContest);
	}

	@Override
	public void clearSelection() {
		myList.clearSelection();
		
	}
}
