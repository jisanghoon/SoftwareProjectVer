package kr.or.dgit.bigdata.swmng.customer.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.service.BuyerService;
import kr.or.dgit.bigdata.swmng.service.SaleService;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;
import kr.or.dgit.bigdata.swmng.util.DateFomatter;

public class SoftwareOrderPanel extends JPanel implements ActionListener {
	private JTextField tfNo;
	private JTextField tfDate;
	private JTextField tfOrderCount;
	private JCheckBox ckbPayment;
	private JComboBox cmbShopName;
	private JComboBox cmbTitle;
	private JDatePickerImpl datePicker;
	private UtilDateModel model = new UtilDateModel();
	private Date today = new Date();
	private JDatePanelImpl datePanel;
	private JPanel orderPanel;
	private GridBagConstraints gbc_tfDate;

	public SoftwareOrderPanel() {
		setLayout(new BorderLayout(0, 0));

		orderPanel = new JPanel();
		orderPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(orderPanel);
		GridBagLayout gbl_orderPanel = new GridBagLayout();
		gbl_orderPanel.columnWidths = new int[] { 18, 0, 0, 0, 38, 0 };
		gbl_orderPanel.rowHeights = new int[] { 38, 0, 0, 142, 0, 0, 0, 0, 0, 0 };
		gbl_orderPanel.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_orderPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		orderPanel.setLayout(gbl_orderPanel);

		JLabel lblPanelTitle = new JLabel("소프트웨어 주문");
		lblPanelTitle.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		GridBagConstraints gbc_lblPanelTitle = new GridBagConstraints();
		gbc_lblPanelTitle.gridwidth = 5;
		gbc_lblPanelTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblPanelTitle.gridx = 0;
		gbc_lblPanelTitle.gridy = 0;
		orderPanel.add(lblPanelTitle, gbc_lblPanelTitle);

		JLabel lblNo = new JLabel("주문번호 : ");
		GridBagConstraints gbc_lblNo = new GridBagConstraints();
		gbc_lblNo.anchor = GridBagConstraints.EAST;
		gbc_lblNo.insets = new Insets(0, 0, 5, 5);
		gbc_lblNo.gridx = 1;
		gbc_lblNo.gridy = 2;
		orderPanel.add(lblNo, gbc_lblNo);

		tfNo = new JTextField();
		tfNo.setEditable(false);
		GridBagConstraints gbc_tfNo = new GridBagConstraints();
		gbc_tfNo.insets = new Insets(0, 0, 5, 5);
		gbc_tfNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNo.gridx = 3;
		gbc_tfNo.gridy = 2;
		orderPanel.add(tfNo, gbc_tfNo);
		tfNo.setColumns(10);

		JLabel lblDate = new JLabel("주문일자 : ");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.EAST;
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 1;
		gbc_lblDate.gridy = 3;
		orderPanel.add(lblDate, gbc_lblDate);

		gbc_tfDate = new GridBagConstraints();
		gbc_tfDate.gridheight = 2;
		gbc_tfDate.insets = new Insets(0, 0, 5, 5);
		gbc_tfDate.fill = GridBagConstraints.BOTH;
		gbc_tfDate.gridx = 3;
		gbc_tfDate.gridy = 3;

		JLabel lblShopName = new JLabel("고객상호명 : ");
		GridBagConstraints gbc_lblShopName = new GridBagConstraints();
		gbc_lblShopName.anchor = GridBagConstraints.EAST;
		gbc_lblShopName.insets = new Insets(0, 0, 5, 5);
		gbc_lblShopName.gridx = 1;
		gbc_lblShopName.gridy = 5;
		orderPanel.add(lblShopName, gbc_lblShopName);

		cmbShopName = new JComboBox();
		GridBagConstraints gbc_cmbShopName = new GridBagConstraints();
		gbc_cmbShopName.insets = new Insets(0, 0, 5, 5);
		gbc_cmbShopName.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbShopName.gridx = 3;
		gbc_cmbShopName.gridy = 5;
		orderPanel.add(cmbShopName, gbc_cmbShopName);

		JLabel lblTitle = new JLabel("품 목 명 : ");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.EAST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 6;
		orderPanel.add(lblTitle, gbc_lblTitle);

		cmbTitle = new JComboBox();
		GridBagConstraints gbc_cmbTitle = new GridBagConstraints();
		gbc_cmbTitle.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTitle.gridx = 3;
		gbc_cmbTitle.gridy = 6;
		orderPanel.add(cmbTitle, gbc_cmbTitle);

		JLabel lblOrderCount = new JLabel("주문수량 : ");
		GridBagConstraints gbc_lblOrderCount = new GridBagConstraints();
		gbc_lblOrderCount.anchor = GridBagConstraints.EAST;
		gbc_lblOrderCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblOrderCount.gridx = 1;
		gbc_lblOrderCount.gridy = 7;
		orderPanel.add(lblOrderCount, gbc_lblOrderCount);

		tfOrderCount = new JTextField();
		GridBagConstraints gbc_tfOrderCount = new GridBagConstraints();
		gbc_tfOrderCount.insets = new Insets(0, 0, 5, 5);
		gbc_tfOrderCount.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfOrderCount.gridx = 3;
		gbc_tfOrderCount.gridy = 7;
		orderPanel.add(tfOrderCount, gbc_tfOrderCount);
		tfOrderCount.setColumns(10);

		JLabel lblPayment = new JLabel("입금여부 : ");
		GridBagConstraints gbc_lblPayment = new GridBagConstraints();
		gbc_lblPayment.anchor = GridBagConstraints.EAST;
		gbc_lblPayment.insets = new Insets(0, 0, 0, 5);
		gbc_lblPayment.gridx = 1;
		gbc_lblPayment.gridy = 8;
		orderPanel.add(lblPayment, gbc_lblPayment);

		ckbPayment = new JCheckBox("미입금");
		ckbPayment.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (ckbPayment.isSelected()) {
					ckbPayment.setText("입금");
				} else {
					ckbPayment.setText("미입금");
				}

			}
		});
		GridBagConstraints gbc_ckbPayment = new GridBagConstraints();
		gbc_ckbPayment.insets = new Insets(0, 0, 0, 5);
		gbc_ckbPayment.gridx = 3;
		gbc_ckbPayment.gridy = 8;
		orderPanel.add(ckbPayment, gbc_ckbPayment);

		JPanel btnPanel = new JPanel();
		add(btnPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_btnPanel = new GridBagLayout();
		gbl_btnPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_btnPanel.rowHeights = new int[] { 0, 0 };
		gbl_btnPanel.columnWeights = new double[] { 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_btnPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		btnPanel.setLayout(gbl_btnPanel);

		JButton btnOrder = new JButton("주문");
		btnOrder.addActionListener(this);
		GridBagConstraints gbc_btnOrder = new GridBagConstraints();
		gbc_btnOrder.insets = new Insets(0, 0, 0, 5);
		gbc_btnOrder.gridx = 1;
		gbc_btnOrder.gridy = 0;
		btnPanel.add(btnOrder, gbc_btnOrder);

		JButton btnReset = new JButton("리셋");
		btnReset.addActionListener(this);
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.insets = new Insets(0, 0, 0, 5);
		gbc_btnReset.gridx = 2;
		gbc_btnReset.gridy = 0;
		btnPanel.add(btnReset, gbc_btnReset);

		JButton btnClose = new JButton("닫기");
		btnClose.addActionListener(this);
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.insets = new Insets(0, 0, 0, 5);
		gbc_btnClose.gridx = 3;
		gbc_btnClose.gridy = 0;
		btnPanel.add(btnClose, gbc_btnClose);

		cmbShopName.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				createTitleList();
			}
		});
		createTitleList();
		resetAction();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "주문":
			insertAction();
			break;
		case "리셋":
			resetAction();
			break;
		case "닫기":
			cancelAction();
			break;
		}
	}

	private void cancelAction() {
		setVisible(false);
		JLabel lblMainTitle = new JLabel(new ImageIcon("src/img/logo.gif"));
		lblMainTitle.setFont(new Font("굴림", Font.PLAIN, 20));
		lblMainTitle.setHorizontalAlignment(SwingConstants.CENTER);
		this.getParent().add(lblMainTitle,BorderLayout.CENTER);
		revalidate();
	}

	private void resetAction() {
		List<Buyer> shopNameList = BuyerService.getInstance().selectShopName();
		for (Buyer s : shopNameList) {
			cmbShopName.addItem(s.getShopName());
		}
		cmbShopName.setSelectedIndex(0);
		tfNo.setText(SaleService.getInstance().selectMaxNo().getNo() + "");
		tfOrderCount.setText("");
		ckbPayment.setSelected(false);
		model.setValue(today);
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "오늘");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateFomatter());
		datePicker.getJFormattedTextField().setFont(new Font("굴림", Font.PLAIN, 12));
		datePanel.getComponent(0).setPreferredSize(new Dimension(250, 190));
		orderPanel.add(datePicker, gbc_tfDate);
	}

	private void insertAction() {
		boolean payment;
		if (tfOrderCount.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "주문수량을 확인해주세요");
			tfOrderCount.requestFocus();
			tfOrderCount.selectAll();
		} else if (tfOrderCount.getText().trim().matches(".*\\D+.*")) {
			JOptionPane.showMessageDialog(null, "숫자만 입력해 주세요");
			tfOrderCount.requestFocus();
			tfOrderCount.selectAll();
		} else {
			String[] date = datePicker.getJFormattedTextField().getText().split("/");
			if (ckbPayment.isSelected()) {
				payment = true;
			} else {
				payment = false;
			}
			SaleService.getInstance()
					.insertItem(new Sale(Integer.parseInt(tfNo.getText()), cmbShopName.getSelectedItem() + "",
							cmbTitle.getSelectedItem() + "", Integer.parseInt(tfOrderCount.getText().trim()), payment,
							new Date(Integer.parseInt(date[0]) - 1900, Integer.parseInt(date[1]) - 1,
									Integer.parseInt(date[2]))));
			JOptionPane.showMessageDialog(null, "주문이 완료 되었습니다");
		}

	}

	void createTitleList() {
		if (cmbTitle.getItemCount() > 0) {
			cmbTitle.removeAllItems();
		} else {
			List<Software> titleList = SoftwareService.getInstance()
					.selectTitleJoinSale(cmbShopName.getSelectedItem() + "");
			for (Software sw : titleList) {
				cmbTitle.addItem(sw.getTitle());
			}
		}
	}
}