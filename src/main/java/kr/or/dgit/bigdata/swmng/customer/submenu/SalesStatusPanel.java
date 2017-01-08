package kr.or.dgit.bigdata.swmng.customer.submenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.service.BuyerService;
import kr.or.dgit.bigdata.swmng.service.SaleService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;

@SuppressWarnings("serial")
public class SalesStatusPanel extends JPanel implements ItemListener, ActionListener {
	
	private final boolean CHECK = true;
	private final boolean UNCHECK = false;
	private JComboBox<String> combo;
	private JTable table;
	private ModelForTable mft;
	private JCheckBox totalCheck;
	private JButton btnExit;
	private JTextField txtTotalSales;
	private JTextField txtTotalUnpaid;

	/**
	 * Create the panel.
	 */
	public SalesStatusPanel() {
		setLayout(new BorderLayout(0, 0));

		JPanel pnForControl = new JPanel();
		pnForControl.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(pnForControl, BorderLayout.NORTH);
		pnForControl.setLayout(new BorderLayout(0, 0));

		JLabel lblTitle = new JLabel("고객별 판매현황 조회");
		lblTitle.setFont(new Font("굴림", Font.PLAIN, 18));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		pnForControl.add(lblTitle, BorderLayout.NORTH);

		JPanel subPnForControl = new JPanel();
		subPnForControl.setBorder(new LineBorder(Color.DARK_GRAY));
		pnForControl.add(subPnForControl, BorderLayout.CENTER);

		JLabel lblCombo = new JLabel("고객상호명 : ");
		subPnForControl.add(lblCombo);

		combo = new JComboBox<String>();
		List<Buyer> list = BuyerService.getInstance().selectAll();
		for (int i = 0; i < list.size(); i++) {
			combo.addItem(list.get(i).getShopName());
		}
		subPnForControl.add(combo);
		combo.addItemListener(this);

		totalCheck = new JCheckBox("전체");
		totalCheck.addActionListener(this);
		subPnForControl.add(totalCheck);

		btnExit = new JButton("닫기");
		btnExit.addActionListener(this);
		subPnForControl.add(btnExit);

		JPanel PnForTable = new JPanel();
		add(PnForTable, BorderLayout.CENTER);
		PnForTable.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		PnForTable.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel resultPanel = new JPanel();
		resultPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
		PnForTable.add(resultPanel, BorderLayout.SOUTH);
		resultPanel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JLabel lblTotalSales = new JLabel();
		lblTotalSales.setText("매출금 합계 : ");
		lblTotalSales.setHorizontalAlignment(SwingConstants.RIGHT);
		resultPanel.add(lblTotalSales);
		
		txtTotalSales = new JTextField();
		txtTotalSales.setEnabled(true);
		txtTotalSales.setEditable(false);
		txtTotalSales.setHorizontalAlignment(SwingConstants.RIGHT);
		resultPanel.add(txtTotalSales);
		txtTotalSales.setColumns(10);
		
		JLabel lblUnpaid = new JLabel();
		lblUnpaid.setText("미수금 합계 : ");
		lblUnpaid.setHorizontalAlignment(SwingConstants.RIGHT);
		resultPanel.add(lblUnpaid);
		
		txtTotalUnpaid = new JTextField();
		txtTotalUnpaid.setEditable(false);
		txtTotalUnpaid.setEnabled(true);
		txtTotalUnpaid.setHorizontalAlignment(SwingConstants.RIGHT);
		resultPanel.add(txtTotalUnpaid);
		txtTotalUnpaid.setColumns(10);
		
		refleshTable(UNCHECK);
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == combo) {
			refleshTable(UNCHECK);
		}
	}

	private void refleshTable(boolean totalCheck) {
		List<Sale> list;
		if (totalCheck == CHECK) {
			list = SaleService.getInstance().selectAllOrderByBuyer();
		} else {
			list = SaleService.getInstance().selectAllOrderByTitle();
		}

		String[] COL_NAMES = { "고객상호명", "품목명", "주문수량", "입금여부", "판매가격", "매출금", "미수금" };

		Object[][] temp = new Object[list.size()][COL_NAMES.length];
		int idx = 0;
		int rowCnt = 0;
		int sum1 = 0;// 매출금 합계
		int sum2 = 0;// 미수금 합계
		for (Sale c : list) {
			if (totalCheck == UNCHECK) {
				if (combo.getSelectedItem().equals(c.getShopName().getShopName())) {
					rowCnt++;
					temp[idx][0] = c.getShopName().getShopName();
					temp[idx][1] = c.getTitle().getTitle();
					temp[idx][2] = c.getOrderCount();
					temp[idx][3] = new Boolean(!c.isPayment());
					temp[idx][4] = c.getTitle().getSellPrice();
					temp[idx][5] = c.getOrderCount() * c.getTitle().getSellPrice();
					sum1+=c.getOrderCount() * c.getTitle().getSellPrice();
					if (c.isPayment()) {
						temp[idx][6] = c.getOrderCount() * c.getTitle().getSellPrice();
						sum2 += c.getOrderCount() * c.getTitle().getSellPrice();
					} else {
						temp[idx][6] = "";
					}
					idx++;
				} else {
					continue;
				}
			} else {
				rowCnt++;
				temp[idx][0] = c.getShopName().getShopName();
				temp[idx][1] = c.getTitle().getTitle();
				temp[idx][2] = c.getOrderCount();
				temp[idx][3] = new Boolean(!c.isPayment());
				temp[idx][4] = c.getTitle().getSellPrice();
				temp[idx][5] = c.getOrderCount() * c.getTitle().getSellPrice();
				sum1+=c.getOrderCount() * c.getTitle().getSellPrice();
				if (c.isPayment()) {
					temp[idx][6] = c.getOrderCount() * c.getTitle().getSellPrice();
					sum2 += c.getOrderCount() * c.getTitle().getSellPrice();
				} else {
					temp[idx][6] = "";
				}
				idx++;
			}

		}
		// 행 수 조정을 위한 처리
		
		Object[][] data = new Object[rowCnt][COL_NAMES.length];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = temp[i][0];
			data[i][1] = temp[i][1];
			data[i][2] = temp[i][2];
			data[i][3] = temp[i][3];
			data[i][4] = String.format("%,d", temp[i][4]);
			data[i][5] = String.format("%,d", temp[i][5]);
			if (temp[i][6].equals("")) {
				data[i][6]="";
			}else{
				data[i][6] = String.format("%,d", temp[i][6]);	
			}
			
		}
		mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);
		table.getColumnModel().getColumn(3).setCellRenderer(table.getDefaultRenderer(Boolean.class));
		mft.tableCellAlignment(table, SwingConstants.CENTER, 2);
		mft.tableCellAlignment(table, SwingConstants.RIGHT, 4, 5, 6);
		mft.resizeColumnWidth(table);
		table.setFont(table.getFont().deriveFont(11.0f));
		mft.tableHeaderAlignment(table);
		
		
		
		if (sum1 == 0) {
			txtTotalSales.setText(String.format("%s", "-"));
			txtTotalUnpaid.setText(String.format("%,d", sum2));
			txtTotalSales.setHorizontalAlignment(SwingConstants.CENTER);
		} else if (sum2 == 0) {
			txtTotalSales.setText(String.format("%,d", sum1));
			txtTotalUnpaid.setText(String.format("%s", "-"));
			txtTotalUnpaid.setHorizontalAlignment(SwingConstants.CENTER);
		} else {
			txtTotalSales.setText(String.format("%,d", sum1));
			txtTotalUnpaid.setText(String.format("%,d", sum2));
			txtTotalUnpaid.setHorizontalAlignment(SwingConstants.RIGHT);
			txtTotalSales.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		
	}

	public void actionPerformed(ActionEvent e) {

		if (totalCheck.isSelected()) {
			refleshTable(CHECK);
			combo.setEnabled(false);
		} else if (!totalCheck.isSelected()) {
			refleshTable(UNCHECK);
			combo.setEnabled(true);
		}
		if (e.getSource() == btnExit) {

			btnExitActionPerformed(e);
		}
	}

	protected void btnExitActionPerformed(ActionEvent e) {
		setVisible(false);
		JLabel lblMainTitle = new JLabel(new ImageIcon("src/img/logo.gif"));
		lblMainTitle.setFont(new Font("굴림", Font.PLAIN, 20));
		lblMainTitle.setHorizontalAlignment(SwingConstants.CENTER);
		this.getParent().add(lblMainTitle, BorderLayout.CENTER);
		revalidate();
	}
}
