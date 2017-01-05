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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.list.SoftwareList;
import kr.or.dgit.bigdata.swmng.service.CompanyService;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;

public class SoftwareRegEdit extends JPanel implements ActionListener, RegEditInterface {
	private JTextField tfSupPrice;
	private JTextField tfNo;
	private JTextField tfTitle;
	private JTextField tfSellPrice;
	private JComboBox cmbCategory;
	private JComboBox cmbCoName;
	private JButton btnAdd;
	private JButton btnCancel;
	private String selectedTitle;
	private List<Software> softwareList = SoftwareService.getInstance().selectCategory();
	private List<Company> companyList = CompanyService.getInstance().selectCoName();

	public SoftwareRegEdit(String e, int flag) {
		setLayout(new BorderLayout(0, 0));

		JPanel AddPanel = new JPanel();
		add(AddPanel, BorderLayout.CENTER);
		AddPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		GridBagLayout gbl_AddPanel = new GridBagLayout();
		gbl_AddPanel.columnWidths = new int[] { 40, 0, 0, 0, 0, 0, 2 };
		gbl_AddPanel.rowHeights = new int[] { 38, 0, 0, 0, 0 };
		gbl_AddPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 1.0 };
		gbl_AddPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		AddPanel.setLayout(gbl_AddPanel);

		JLabel lblTitle = new JLabel("소프트웨어 등록");
		lblTitle.setFont(new Font("돋움", Font.BOLD, 15));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 7;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		AddPanel.add(lblTitle, gbc_lblTitle);

		JLabel lblCategory = new JLabel("분 류 명 :");
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.anchor = GridBagConstraints.EAST;
		gbc_lblCategory.gridx = 1;
		gbc_lblCategory.gridy = 1;
		AddPanel.add(lblCategory, gbc_lblCategory);

		cmbCategory = new JComboBox();
		GridBagConstraints gbc_cmbCategory = new GridBagConstraints();
		gbc_cmbCategory.insets = new Insets(0, 0, 5, 5);
		gbc_cmbCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCategory.gridx = 2;
		gbc_cmbCategory.gridy = 1;
		AddPanel.add(cmbCategory, gbc_cmbCategory);

		JLabel lblCoName_1 = new JLabel("공급회사명 :");
		GridBagConstraints gbc_lblCoName_1 = new GridBagConstraints();
		gbc_lblCoName_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoName_1.anchor = GridBagConstraints.EAST;
		gbc_lblCoName_1.gridx = 4;
		gbc_lblCoName_1.gridy = 1;
		AddPanel.add(lblCoName_1, gbc_lblCoName_1);

		cmbCoName = new JComboBox();
		GridBagConstraints gbc_cmbCoName = new GridBagConstraints();
		gbc_cmbCoName.insets = new Insets(0, 0, 5, 5);
		gbc_cmbCoName.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCoName.gridx = 5;
		gbc_cmbCoName.gridy = 1;
		AddPanel.add(cmbCoName, gbc_cmbCoName);

		JLabel lbNo = new JLabel("품목번호 :");
		GridBagConstraints gbc_lbNo = new GridBagConstraints();
		gbc_lbNo.anchor = GridBagConstraints.EAST;
		gbc_lbNo.insets = new Insets(0, 0, 5, 5);
		gbc_lbNo.gridx = 1;
		gbc_lbNo.gridy = 2;
		AddPanel.add(lbNo, gbc_lbNo);

		tfNo = new JTextField();
		tfNo.setEditable(false);
		tfNo.setColumns(10);
		GridBagConstraints gbc_tfNo = new GridBagConstraints();
		gbc_tfNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNo.insets = new Insets(0, 0, 5, 5);
		gbc_tfNo.gridx = 2;
		gbc_tfNo.gridy = 2;
		AddPanel.add(tfNo, gbc_tfNo);

		JLabel lblCoName = new JLabel("품 목 명 :");
		GridBagConstraints gbc_lblCoName = new GridBagConstraints();
		gbc_lblCoName.anchor = GridBagConstraints.EAST;
		gbc_lblCoName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoName.gridx = 4;
		gbc_lblCoName.gridy = 2;
		AddPanel.add(lblCoName, gbc_lblCoName);

		tfTitle = new JTextField();
		tfTitle.setColumns(10);
		GridBagConstraints gbc_tfCoName = new GridBagConstraints();
		gbc_tfCoName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfCoName.insets = new Insets(0, 0, 5, 5);
		gbc_tfCoName.gridx = 5;
		gbc_tfCoName.gridy = 2;
		AddPanel.add(tfTitle, gbc_tfCoName);

		JLabel lblSupPrice = new JLabel("공급가격 :");
		GridBagConstraints gbc_lblSupPrice = new GridBagConstraints();
		gbc_lblSupPrice.anchor = GridBagConstraints.EAST;
		gbc_lblSupPrice.insets = new Insets(0, 0, 0, 5);
		gbc_lblSupPrice.gridx = 1;
		gbc_lblSupPrice.gridy = 3;
		AddPanel.add(lblSupPrice, gbc_lblSupPrice);

		tfSupPrice = new JTextField();
		GridBagConstraints gbc_tfSupPrice = new GridBagConstraints();
		gbc_tfSupPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSupPrice.insets = new Insets(0, 0, 0, 5);
		gbc_tfSupPrice.gridx = 2;
		gbc_tfSupPrice.gridy = 3;
		AddPanel.add(tfSupPrice, gbc_tfSupPrice);
		tfSupPrice.setColumns(10);

		JLabel lblSellPrice = new JLabel("판매가격 :");
		GridBagConstraints gbc_lblSellPrice = new GridBagConstraints();
		gbc_lblSellPrice.anchor = GridBagConstraints.EAST;
		gbc_lblSellPrice.insets = new Insets(0, 0, 0, 5);
		gbc_lblSellPrice.gridx = 4;
		gbc_lblSellPrice.gridy = 3;
		AddPanel.add(lblSellPrice, gbc_lblSellPrice);

		tfSellPrice = new JTextField();
		tfSellPrice.setColumns(10);
		GridBagConstraints gbc_tfSellPrice = new GridBagConstraints();
		gbc_tfSellPrice.insets = new Insets(0, 0, 0, 5);
		gbc_tfSellPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSellPrice.gridx = 5;
		gbc_tfSellPrice.gridy = 3;
		AddPanel.add(tfSellPrice, gbc_tfSellPrice);

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

		for (Software s : softwareList) {
			cmbCategory.addItem(s.getCategory());
		}
		for (Company c : companyList) {
			cmbCoName.addItem(c.getCoName());
		}

		if (e.equals("등록")) {
			tfNo.setText(SoftwareService.getInstance().selectMaxNo().getNo() + "");
		} else if (e.equals("수정")) {
			updateAction(flag);
		}

	}

	@Override
	public void updateAction(int no) {
		btnAdd.setText("수정");
		Software list = SoftwareService.getInstance().selectByNo(no);
		tfNo.setText(list.getNo() + "");
		tfTitle.setText(list.getTitle());
		tfSupPrice.setText(list.getSupPrice() + "");
		tfSellPrice.setText(list.getSellPrice() + "");
		selectedTitle = list.getTitle();
		for (Software s : softwareList) {
			cmbCategory.addItem(s.getCategory());
		}
		for (Company c : companyList) {
			cmbCoName.addItem(c.getCoName());
		}
		cmbCategory.setSelectedItem(list.getCategory());
		cmbCoName.setSelectedItem(list.getCoName().getCoName());
		revalidate();
		repaint();
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
				SoftwareService.getInstance()
						.insertItem(new Software(Integer.parseInt(tfNo.getText()), cmbCategory.getSelectedItem() + "",
								tfTitle.getText().trim(), Integer.parseInt(tfSupPrice.getText().trim()),
								Integer.parseInt(tfSellPrice.getText().trim()), cmbCoName.getSelectedItem() + ""));
				JOptionPane.showMessageDialog(null, "등록이 완료되었습니다.");
				refresh(new SoftwareList());
			}
			break;
		case "수정":
			if (inputValidation() && duplicateValidation(e.getActionCommand())) {
				btnAdd.setText("등록");
				SoftwareService.getInstance()
						.updateItem(new Software(Integer.parseInt(tfNo.getText()), cmbCategory.getSelectedItem() + "",
								tfTitle.getText().trim(), Integer.parseInt(tfSupPrice.getText().trim()),
								Integer.parseInt(tfSellPrice.getText().trim()), cmbCoName.getSelectedItem() + ""));
				JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
				refresh(new SoftwareList());
			}
			break;

		case "취소":
			refresh(new SoftwareList());
			break;
		}

	}

	@Override
	public boolean inputValidation() {
		if (tfNo.getText().equals("") || tfTitle.getText().trim().equals("") || tfSupPrice.getText().trim().equals("")
				|| tfSellPrice.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "빈칸없이 입력해 주세요");
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean duplicateValidation(String e) {
		List<Software> list = SoftwareService.getInstance().selectTitle();
		boolean flag = true;

		switch (e) {

		case "등록":
			for (Software s : list) {
				if (tfTitle.getText().trim().equals(s.getTitle())) {
					flag = false;
					tfTitle.requestFocus();
					tfTitle.selectAll();
					JOptionPane.showMessageDialog(null, "입력하신 품목이름이 중복되었습니다");
				}
			}
			break;

		case "수정":
			String[] title = new String[list.size()];
			int idx = 0;
			for (Software s : list) {
				if (s.getTitle().equals(selectedTitle) == false) {
					title[idx] = s.getTitle();
					idx++;
				}
			}
			for (int i = 0; i < list.size() - 1; i++) {
				if (title[i].equals(tfTitle.getText().trim())) {
					flag = false;
					tfTitle.requestFocus();
					tfTitle.selectAll();
					JOptionPane.showMessageDialog(null, "다른 품목이름과 중복되었습니다");
				}
			}
			break;
		}
		return flag;
	}

}
