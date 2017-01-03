package kr.or.dgit.bigdata.swmng.customersubmenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.service.BuyerService;
import kr.or.dgit.bigdata.swmng.service.SaleService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;

public class SalesStatusPanel extends JPanel implements ItemListener, ActionListener {
	private JComboBox combo;
	private JTable table;
	private ModelForTable mft;
	private JCheckBox totalCheck;
	private final boolean CHECK = true;
	private final boolean UNCHECK = false;
	private JButton btnExit;

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

		combo = new JComboBox();

		List<Buyer> list = BuyerService.getInstance().selectAll();
		for (int i = 0; i < list.size(); i++) {
			combo.addItem(list.get(i).getShopName());
		}
		subPnForControl.add(combo);
		combo.addItemListener(this);

		totalCheck = new JCheckBox("전체");
		totalCheck.addActionListener(this);
		subPnForControl.add(totalCheck);

		btnExit = new JButton("[닫기]");
		btnExit.addActionListener(this);
		
		subPnForControl.add(btnExit);

		JPanel PnForTable = new JPanel();
		add(PnForTable, BorderLayout.CENTER);
		PnForTable.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		PnForTable.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
		refleshTable(UNCHECK);
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == combo) {
			refleshTable(UNCHECK);
		}
	}

	private void refleshTable(boolean isCheck) {
		List<Sale> list = SaleService.getInstance().selectAll();
		String[] COL_NAMES = { "고객상호명", "품목명", "주문수량", "입금여부", "판매가격", "매출금", "미수금" };

		String[][] temp = new String[list.size()][COL_NAMES.length];
		int idx = 0;
		int rowCnt = 0;
		for (Sale c : list) {
			if (isCheck == UNCHECK) {
				if (combo.getSelectedItem().equals(c.getShopName().getShopName())) {
					rowCnt++;
					temp[idx][0] = c.getShopName().getShopName();
					temp[idx][1] = c.getTitle().getTitle();
					temp[idx][2] = c.getOrderCount() + "";
					temp[idx][3] = !c.isPayment() + "";
					temp[idx][4] = c.getTitle().getSellPrice() + "";
					temp[idx][5] = (c.getOrderCount() * c.getTitle().getSellPrice()) + "";
					if (c.isPayment()) {
						temp[idx][6] = (c.getOrderCount() * c.getTitle().getSellPrice()) + "";
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
				temp[idx][2] = c.getOrderCount() + "";
				temp[idx][3] = !c.isPayment() + "";
				temp[idx][4] = c.getTitle().getSellPrice() + "";
				temp[idx][5] = (c.getOrderCount() * c.getTitle().getSellPrice()) + "";
				if (c.isPayment()) {
					temp[idx][6] = (c.getOrderCount() * c.getTitle().getSellPrice()) + "";
				} else {
					temp[idx][6] = "";
				}
				idx++;
			}
		}
		// 행 수 조정을 위한 처리
		String[][] data = new String[rowCnt][COL_NAMES.length];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = temp[i][0];
			data[i][1] = temp[i][1];
			data[i][2] = temp[i][2];
			data[i][3] = temp[i][3];
			data[i][4] = temp[i][4];
			data[i][5] = temp[i][5];
			data[i][6] = temp[i][6];
		}

		mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);
		mft.tableCellAlignment(table, SwingConstants.CENTER, 0, 3);
	}

	public void actionPerformed(ActionEvent e) {
		
		if (totalCheck.isSelected()) {
			refleshTable(CHECK);
			combo.setEnabled(false);
		}else if(!totalCheck.isSelected()){
			refleshTable(UNCHECK);
			combo.setEnabled(true);
		}
		if(e.getSource() == btnExit){
			
			btnExitActionPerformed(e);
		}
	}

	protected void btnExitActionPerformed(ActionEvent e) {
		setVisible(false);
		JLabel lblMainTitle = new JLabel(new ImageIcon("src/img/logo.gif"));
		lblMainTitle.setFont(new Font("굴림", Font.PLAIN, 20));
		lblMainTitle.setHorizontalAlignment(SwingConstants.CENTER);
		this.getParent().add(lblMainTitle,BorderLayout.CENTER);
		revalidate();
	}
}
