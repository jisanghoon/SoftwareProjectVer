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

import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.list.CompanyList;
import kr.or.dgit.bigdata.swmng.main.SwMngMain;
import kr.or.dgit.bigdata.swmng.service.CompanyService;

public class CompanyRegEdit extends JPanel implements ActionListener, RegEditInterface {
	private JTextField tfAddress;
	private JTextField tfNo;
	private JTextField tfCoName;
	private JTextField tfTel;
	private JButton btnAdd;
	private JButton btnCancel;
	private String selectedCoName;

	public CompanyRegEdit(String e, int flag) {
		setLayout(new BorderLayout(0, 0));

		JPanel AddPanel = new JPanel();
		add(AddPanel, BorderLayout.CENTER);
		AddPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		GridBagLayout gbl_AddPanel = new GridBagLayout();
		gbl_AddPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 40, 0, 0, 0, 0, 0, 2 };
		gbl_AddPanel.rowHeights = new int[] { 38, 0, 0, 0 };
		gbl_AddPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_AddPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		AddPanel.setLayout(gbl_AddPanel);

		JLabel lblTitle = new JLabel("공급회사 등록");
		lblTitle.setFont(new Font("돋움", Font.BOLD, 15));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 12;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		AddPanel.add(lblTitle, gbc_lblTitle);

		JLabel lbNo = new JLabel("회사번호 :");
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
		gbc_tfNo.gridwidth = 3;
		gbc_tfNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNo.insets = new Insets(0, 0, 5, 5);
		gbc_tfNo.gridx = 1;
		gbc_tfNo.gridy = 1;
		AddPanel.add(tfNo, gbc_tfNo);

		JLabel lblCoName = new JLabel("회 사 명 :");
		GridBagConstraints gbc_lblCoName = new GridBagConstraints();
		gbc_lblCoName.anchor = GridBagConstraints.EAST;
		gbc_lblCoName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoName.gridx = 7;
		gbc_lblCoName.gridy = 1;
		AddPanel.add(lblCoName, gbc_lblCoName);

		tfCoName = new JTextField();
		tfCoName.setColumns(10);
		GridBagConstraints gbc_tfCoName = new GridBagConstraints();
		gbc_tfCoName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfCoName.gridwidth = 4;
		gbc_tfCoName.insets = new Insets(0, 0, 5, 0);
		gbc_tfCoName.gridx = 8;
		gbc_tfCoName.gridy = 1;
		AddPanel.add(tfCoName, gbc_tfCoName);

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
		gbc_tfTel.gridwidth = 4;
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
			tfNo.setText(CompanyService.getInstance().selectMaxNo().getNo() + "");
		} else if (e.equals("수정")) {
			updateAction(flag);
		}

	}

	@Override
	public void updateAction(int no) {

		btnAdd.setText("수정");
		Company list = CompanyService.getInstance().selectByNo(no);
		tfNo.setText(list.getNo() + "");
		tfCoName.setText(list.getCoName());
		tfAddress.setText(list.getAddress());
		tfTel.setText(list.getTel());
		selectedCoName = list.getCoName();
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "등록":
			if (inputValidation() && duplicateValidation(e.getActionCommand())) {
				CompanyService.getInstance().insertItem(new Company(Integer.parseInt(tfNo.getText()),
						tfCoName.getText().trim(), tfAddress.getText().trim(), tfTel.getText().trim()));
				JOptionPane.showMessageDialog(null, "등록이 완료되었습니다.");
				refresh(new CompanyList());
			}

			break;
		case "수정":
			if (inputValidation() && duplicateValidation(e.getActionCommand())) {
				btnAdd.setText("등록");
				CompanyService.getInstance().updateItem(new Company(Integer.parseInt(tfNo.getText()),
						tfCoName.getText().trim(), tfAddress.getText().trim(), tfTel.getText().trim()));
				JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
				refresh(new CompanyList());
			}

			break;

		case "취소":
			refresh(new CompanyList());
			break;
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
	public boolean inputValidation() {
		if (tfNo.getText().equals("") || tfCoName.getText().trim().equals("") || tfAddress.getText().trim().equals("")
				|| tfTel.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "빈칸없이 입력해 주세요");
			return false;
		} else {
			return true;
		}

	}

	@Override
	public boolean duplicateValidation(String e) {
		List<Company> list = CompanyService.getInstance().selectCoName();
		boolean flag = true;

		switch (e) {

		case "등록":
			for (Company c : list) {
				if (tfCoName.getText().trim().equals(c.getCoName())) {
					flag = false;
					tfCoName.requestFocus();
					tfCoName.selectAll();
					JOptionPane.showMessageDialog(null, "입력하신 회사이름이 중복되었습니다");
				}
			}
			break;

		case "수정":
			String[] coNames = new String[list.size()];
			int idx = 0;
			for (Company c : list) {
				if (c.getCoName().equals(selectedCoName) == false) {
					coNames[idx] = c.getCoName();
					idx++;
				}
			}
			for (int i = 0; i < list.size() - 1; i++) {
				if (coNames[i].equals(tfCoName.getText().trim())) {
					flag = false;
					tfCoName.requestFocus();
					tfCoName.selectAll();
					JOptionPane.showMessageDialog(null, "다른 회사이름과 중복되었습니다");
				}
			}
			break;
		}
		return flag;
	}
}
