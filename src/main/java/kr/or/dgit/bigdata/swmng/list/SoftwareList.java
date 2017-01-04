package kr.or.dgit.bigdata.swmng.list;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import kr.or.dgit.bigdata.swmng.customer.SoftwareRegEdit;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;
import kr.or.dgit.bigdata.swmng.util.ReloadUpdateInterface;

public class SoftwareList extends JPanel implements ActionListener, DeleteInterface, ReloadUpdateInterface {
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDel;
	private JTable softwareList;

	public SoftwareList() {
		setLayout(new BorderLayout(0, 0));

		JPanel ListPanel = new JPanel();
		ListPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		add(ListPanel, BorderLayout.CENTER);
		ListPanel.setLayout(new BorderLayout(0, 0));

		JPanel BtnPanel = new JPanel();
		add(BtnPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_BtnPanel = new GridBagLayout();
		gbl_BtnPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_BtnPanel.rowHeights = new int[] { 0, 0 };
		gbl_BtnPanel.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_BtnPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		BtnPanel.setLayout(gbl_BtnPanel);

		btnAdd = new JButton("등록");
		btnAdd.addActionListener(this);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 0;
		BtnPanel.add(btnAdd, gbc_btnAdd);

		btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(this);
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUpdate.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate.gridx = 1;
		gbc_btnUpdate.gridy = 0;
		BtnPanel.add(btnUpdate, gbc_btnUpdate);

		btnDel = new JButton("삭제");
		btnDel.addActionListener(this);
		GridBagConstraints gbc_btnDel = new GridBagConstraints();
		gbc_btnDel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDel.gridx = 2;
		gbc_btnDel.gridy = 0;
		BtnPanel.add(btnDel, gbc_btnDel);

		softwareList = new JTable();
		List<Software> list = SoftwareService.getInstance().selectAll();
		String[] COL_NAMES = { "품목번호", "분류명", "품목명", "공급가격", "판매가격", "공급회사명" };
		String[][] data = new String[list.size()][COL_NAMES.length];
		int idx = 0;
		for (Software s : list) {
			data[idx][0] = s.getNo() + "";
			data[idx][1] = s.getCategory();
			data[idx][2] = s.getTitle();
			data[idx][3] = s.getSupPrice() + "";
			data[idx][4] = s.getSellPrice() + "";
			data[idx][5] = s.getCoName().getCoName();
			idx++;
		}
		ModelForTable mft = new ModelForTable(data, COL_NAMES);
		softwareList.setModel(mft);
		mft.tableCellAlignment(softwareList, SwingConstants.CENTER, 0, 1, 5);
		mft.tableCellAlignment(softwareList, SwingConstants.RIGHT, 3, 4);
		softwareList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mft.resizeColumnWidth(softwareList);
		ListPanel.add(new JScrollPane(softwareList));

	}

	@Override
	public void updateAction(int no) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(JPanel p) {
		removeAll();
		add(p);
		revalidate();
		repaint();

	}

	@Override
	public void deleteAction(int no) {
		if (JOptionPane.showConfirmDialog(null, "선택한 항목을 삭제하시겠습니까?") == 0) {
			SoftwareService.getInstance().deleteItem(no);
			JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다");
			refresh(new SoftwareList());
		} else {
			JOptionPane.showMessageDialog(null, "취소하였습니다");
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "등록":
			refresh(new SoftwareRegEdit(e.getActionCommand(), 0));
			break;
		case "수정":
			if (softwareList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "수정 할 항목을 선택해주세요");
			} else {
				int flag = Integer.parseInt(softwareList.getValueAt(softwareList.getSelectedRow(), 0) + "");
				refresh(new SoftwareRegEdit(e.getActionCommand(), flag));
			}
			break;
		case "삭제":

			if (softwareList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "삭제 할 항목을 선택해주세요");
			} else {
				deleteAction(Integer.parseInt(softwareList.getValueAt(softwareList.getSelectedRow(), 0) + ""));
			}
			break;
		}
	}

}
