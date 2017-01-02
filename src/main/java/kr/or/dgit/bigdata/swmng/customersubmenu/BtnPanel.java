package kr.or.dgit.bigdata.swmng.customersubmenu;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import kr.or.dgit.bigdata.swmng.main.SwMngMain;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BtnPanel extends JPanel implements ActionListener {
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDel;
	private JButton btnClose;

	/**
	 * Create the panel.
	 */
	public BtnPanel() {
		
		setLayout(new BorderLayout(0, 0));

		JPanel btnPanel = new JPanel();
		add(btnPanel, BorderLayout.SOUTH);

		btnAdd = new JButton("등록");
		btnAdd.addActionListener(this);
		btnPanel.add(btnAdd);

		btnEdit = new JButton("수정");
		btnEdit.addActionListener(this);
		btnPanel.add(btnEdit);

		btnDel = new JButton("삭제");
		btnDel.addActionListener(this);
		btnPanel.add(btnDel);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnDel) {
			new ListPanel().deleteAction();
		}
		if (e.getSource() == btnEdit) {
		
			new ListPanel().updateAction();
		}
		if (e.getSource() == btnAdd) {
			new SwMngMain().actionPerformed(e);
		}
	}

}
