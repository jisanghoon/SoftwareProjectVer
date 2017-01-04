package kr.or.dgit.bigdata.swmng.list;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.border.EmptyBorder;

import kr.or.dgit.bigdata.swmng.customer.BuyerRegEdit;
import kr.or.dgit.bigdata.swmng.customer.CompanyRegEdit;
import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.service.BuyerService;
import kr.or.dgit.bigdata.swmng.service.CompanyService;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;
import kr.or.dgit.bigdata.swmng.util.ReloadUpdateInterface;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class BuyerList extends JPanel implements ActionListener, DeleteInterface, ReloadUpdateInterface  {
	private JTable buyerList;

	public BuyerList() {
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

		JButton btnAdd = new JButton("등록");
		btnAdd.addActionListener(this);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 0;
		BtnPanel.add(btnAdd, gbc_btnAdd);

		JButton btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(this);
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUpdate.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate.gridx = 1;
		gbc_btnUpdate.gridy = 0;
		BtnPanel.add(btnUpdate, gbc_btnUpdate);

		JButton btnDel = new JButton("삭제");
		btnDel.addActionListener(this);
		GridBagConstraints gbc_btnDel = new GridBagConstraints();
		gbc_btnDel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDel.gridx = 2;
		gbc_btnDel.gridy = 0;
		BtnPanel.add(btnDel, gbc_btnDel);

		buyerList = new JTable();
		List<Buyer> list = BuyerService.getInstance().selectAll();
		String[] COL_NAMES = { "등록번호", "상호", "주소", "전화번호" };
		String[][] data = new String[list.size()][COL_NAMES.length];
		int idx = 0;
		for (Buyer b : list) {
			data[idx][0] = b.getNo() + "";
			data[idx][1] = b.getShopName();
			data[idx][2] = b.getAddress();
			data[idx][3] = b.getTel();
			idx++;
		}
		ModelForTable mft = new ModelForTable(data, COL_NAMES);
		buyerList.setModel(mft);
		mft.tableCellAlignment(buyerList, SwingConstants.CENTER, 0, 3);
		buyerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mft.resizeColumnWidth(buyerList);
		ListPanel.add(new JScrollPane(buyerList));

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
			BuyerService.getInstance().deleteItem(no);
			JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다");
			refresh(new BuyerList());
		} else {
			JOptionPane.showMessageDialog(null, "취소하였습니다");
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "등록":
			refresh(new BuyerRegEdit(e.getActionCommand(), 0));
			break;
		case "수정":
			if (buyerList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "수정 할 항목을 선택해주세요");
			} else {
				int flag = Integer.parseInt(buyerList.getValueAt(buyerList.getSelectedRow(), 0) + "");
				refresh(new BuyerRegEdit(e.getActionCommand(), flag));
			}
			break;
		case "삭제":

			if (buyerList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "삭제 할 항목을 선택해주세요");
			} else {
				deleteAction(Integer.parseInt(buyerList.getValueAt(buyerList.getSelectedRow(), 0) + ""));
			}
			break;
		}
		
	}
	

	@Override
	public void updateAction(int no) {
		// TODO Auto-generated method stub
		
	}

}