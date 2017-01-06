package kr.or.dgit.bigdata.swmng.customer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.list.BuyerList;
import kr.or.dgit.bigdata.swmng.service.BuyerService;

public class BuyerRegEdit extends JPanel implements ActionListener, RegEditInterface {
	private JTextField tfAddress;
	private JTextField tfNo;
	private JTextField tfShopName;
	private JTextField tfTel;
	private JButton btnAdd;
	private JButton btnCancel;
	private String selectedShopName;

	public BuyerRegEdit(String e, int flag) {
		setLayout(new BorderLayout(0, 0));

		JPanel AddPanel = new JPanel();
		add(AddPanel, BorderLayout.CENTER);
		AddPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		GridBagLayout gbl_AddPanel = new GridBagLayout();
		gbl_AddPanel.columnWidths = new int[] { 0, 0, 0, 40, 0, 0, 0, 0, 0, 76 };
		gbl_AddPanel.rowHeights = new int[] { 38, 0, 0, 0 };
		gbl_AddPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_AddPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		AddPanel.setLayout(gbl_AddPanel);

		JLabel lblTitle = new JLabel("고객 등록");
		lblTitle.setFont(new Font("돋움", Font.BOLD, 15));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 10;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		AddPanel.add(lblTitle, gbc_lblTitle);

		JLabel lbNo = new JLabel("고객번호 :");
		GridBagConstraints gbc_lbNo = new GridBagConstraints();
		gbc_lbNo.anchor = GridBagConstraints.EAST;
		gbc_lbNo.insets = new Insets(0, 0, 5, 5);
		gbc_lbNo.gridx = 0;
		gbc_lbNo.gridy = 1;
		AddPanel.add(lbNo, gbc_lbNo);
		
				tfNo = new JTextField();
				tfNo.setEditable(false);
				tfNo.setColumns(10);
				GridBagConstraints gbc_tfNo = new GridBagConstraints();
				gbc_tfNo.fill = GridBagConstraints.HORIZONTAL;
				gbc_tfNo.insets = new Insets(0, 0, 5, 5);
				gbc_tfNo.gridx = 1;
				gbc_tfNo.gridy = 1;
				AddPanel.add(tfNo, gbc_tfNo);

		JLabel lblShopName = new JLabel("상 호 명 :");
		GridBagConstraints gbc_lblShopName = new GridBagConstraints();
		gbc_lblShopName.anchor = GridBagConstraints.EAST;
		gbc_lblShopName.insets = new Insets(0, 0, 5, 5);
		gbc_lblShopName.gridx = 7;
		gbc_lblShopName.gridy = 1;
		AddPanel.add(lblShopName, gbc_lblShopName);

		tfShopName = new JTextField();
		tfShopName.setColumns(10);
		GridBagConstraints gbc_tfShopName = new GridBagConstraints();
		gbc_tfShopName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfShopName.gridwidth = 2;
		gbc_tfShopName.insets = new Insets(0, 0, 5, 0);
		gbc_tfShopName.gridx = 8;
		gbc_tfShopName.gridy = 1;
		AddPanel.add(tfShopName, gbc_tfShopName);

		JLabel lblAddress = new JLabel("주 소 :");
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.anchor = GridBagConstraints.EAST;
		gbc_lblAddress.insets = new Insets(0, 0, 0, 5);
		gbc_lblAddress.gridx = 0;
		gbc_lblAddress.gridy = 2;
		AddPanel.add(lblAddress, gbc_lblAddress);
		
				tfAddress = new JTextField();
				GridBagConstraints gbc_tfAddress = new GridBagConstraints();
				gbc_tfAddress.fill = GridBagConstraints.HORIZONTAL;
				gbc_tfAddress.gridwidth = 5;
				gbc_tfAddress.insets = new Insets(0, 0, 0, 5);
				gbc_tfAddress.gridx = 1;
				gbc_tfAddress.gridy = 2;
				AddPanel.add(tfAddress, gbc_tfAddress);
				tfAddress.setColumns(10);

		JLabel lblTel = new JLabel("전화번호 :");
		GridBagConstraints gbc_lblTel = new GridBagConstraints();
		gbc_lblTel.anchor = GridBagConstraints.EAST;
		gbc_lblTel.insets = new Insets(0, 0, 0, 5);
		gbc_lblTel.gridx = 7;
		gbc_lblTel.gridy = 2;
		AddPanel.add(lblTel, gbc_lblTel);

		tfTel = new JTextField();
		tfTel.setColumns(10);
		GridBagConstraints gbc_tfTel = new GridBagConstraints();
		gbc_tfTel.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfTel.gridwidth = 2;
		gbc_tfTel.gridx = 8;
		gbc_tfTel.gridy = 2;
		AddPanel.add(tfTel, gbc_tfTel);

		JPanel BtnPanel = new JPanel();
		add(BtnPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_BtnPanel = new GridBagLayout();
		gbl_BtnPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_BtnPanel.rowHeights = new int[] { 0, 0 };
		gbl_BtnPanel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_BtnPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		BtnPanel.setLayout(gbl_BtnPanel);

		btnAdd = new JButton("등록");
		btnAdd.addActionListener(this);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.EAST;
		gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 0;
		BtnPanel.add(btnAdd, gbc_btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 0;
		BtnPanel.add(btnCancel, gbc_btnCancel);

		if (e.equals("등록")) {
			tfNo.setText(BuyerService.getInstance().selectMaxNo().getNo() + "");
		} else if (e.equals("수정")) {
			updateAction(flag);
		}

	}

	@Override
	public void refresh(JPanel p) {
		removeAll();
		add(p);
		revalidate();
		repaint();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "등록":
			if (inputValidation() && duplicateValidation(e.getActionCommand())) {
				BuyerService.getInstance().insertItem(new Buyer(Integer.parseInt(tfNo.getText()),
						tfShopName.getText().trim(), tfAddress.getText().trim(), tfTel.getText().trim()));
				JOptionPane.showMessageDialog(null, "등록이 완료되었습니다.");
				refresh(new BuyerList());
			}

			break;
		case "수정":
			if (inputValidation() && duplicateValidation(e.getActionCommand())) {
				btnAdd.setText("등록");
				BuyerService.getInstance().updateItem(new Buyer(Integer.parseInt(tfNo.getText()),
						tfShopName.getText().trim(), tfAddress.getText().trim(), tfTel.getText().trim()));
				JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
				refresh(new BuyerList());
			}
			break;

		case "취소":
			refresh(new BuyerList());
			break;
		}

	}

	@Override
	public void updateAction(int no) {
		btnAdd.setText("수정");
		Buyer list = BuyerService.getInstance().selectByNo(no);
		tfNo.setText(list.getNo() + "");
		tfShopName.setText(list.getShopName());
		tfAddress.setText(list.getAddress());
		tfTel.setText(list.getTel());
		selectedShopName = list.getShopName();
		revalidate();
		repaint();
	}

	@Override
	public boolean inputValidation() {
		if (tfNo.getText().equals("") || tfShopName.getText().trim().equals("") || tfAddress.getText().trim().equals("")
				|| tfTel.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "빈칸없이 입력해 주세요");
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean duplicateValidation(String e) {
		List<Buyer> list = BuyerService.getInstance().selectShopName();
		boolean flag = true;

		switch (e) {

		case "등록":
			for (Buyer b : list) {
				if (tfShopName.getText().trim().equals(b.getShopName())) {
					flag = false;
					tfShopName.requestFocus();
					tfShopName.selectAll();
					JOptionPane.showMessageDialog(null, "입력하신 상호명이 중복되었습니다");
				}
			}
			break;

		case "수정":
			String[] shopName = new String[list.size()];
			int idx = 0;
			for (Buyer b : list) {
				if (b.getShopName().equals(selectedShopName) == false) {
					shopName[idx] = b.getShopName();
					idx++;
				}
			}
			for (int i = 0; i < list.size() - 1; i++) {
				if (shopName[i].equals(tfShopName.getText().trim())) {
					flag = false;
					tfShopName.requestFocus();
					tfShopName.selectAll();
					JOptionPane.showMessageDialog(null, "다른 상호명과 중복되었습니다");
				}
			}
			break;
		}
		return flag;
	}

}
