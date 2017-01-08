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
	private JCheckBox ckboxForAll;
	private JButton btnExit;
	private JTextField txtTotalSales;
	private JTextField txtTotalUnpaid;
	private int totalSales;
	private int totalUnpaid;

	/**
	 * Create the panel.
	 */

	public SalesStatusPanel() {
		
		//컴포넌트 생성s
		JPanel panelForControl = new JPanel();
		JPanel subPanelForControl = new JPanel();
		JPanel panelForTable = new JPanel();
		JPanel resultPanel = new JPanel();
		
		JLabel lblTitle = new JLabel("고객별 판매현황 조회");
		JLabel lblCombo = new JLabel("고객상호명 : ");
		JLabel lblTotalSales = new JLabel();
		JLabel lblUnpaid = new JLabel();
		
		JScrollPane scrollPane = new JScrollPane();
		table = new JTable();
		
		combo = new JComboBox<String>();
		ckboxForAll = new JCheckBox("전체");
		btnExit = new JButton("닫기");
		txtTotalSales = new JTextField();
		txtTotalUnpaid = new JTextField();
		
		
		//컴포넌트 레이아웃s
		setLayout(new BorderLayout(0, 0));
		panelForControl.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelForControl.setLayout(new BorderLayout(0, 0));
		subPanelForControl.setBorder(new LineBorder(Color.DARK_GRAY));
		panelForTable.setLayout(new BorderLayout(0, 0));
		resultPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
		resultPanel.setLayout(new GridLayout(0, 4, 0, 0));
		
		
		//컴포넌트 기타 디자인 설정s
		lblTitle.setFont(new Font("굴림", Font.PLAIN, 18));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalSales.setText("매출금 합계 : ");
		lblUnpaid.setText("미수금 합계 : ");
		lblTotalSales.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUnpaid.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalSales.setEnabled(true);
		txtTotalUnpaid.setEnabled(true);
		txtTotalSales.setEditable(false);
		txtTotalUnpaid.setEditable(false);
		txtTotalSales.setColumns(10);
		txtTotalUnpaid.setColumns(10);
		txtTotalSales.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalUnpaid.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		//이벤트s
		ckboxForAll.addActionListener(this);
		btnExit.addActionListener(this);
		makeUpComList();//콤보박스 채우기
		combo.addItemListener(this);

		
		//컴포넌트 뿌리기s
		
		//--NORTH
		subPanelForControl.add(lblCombo);
		subPanelForControl.add(combo);
		subPanelForControl.add(ckboxForAll);
		subPanelForControl.add(btnExit);
		panelForControl.add(subPanelForControl, BorderLayout.CENTER);
		panelForControl.add(lblTitle, BorderLayout.NORTH);
		add(panelForControl, BorderLayout.NORTH);

		
		//--CENTER
		scrollPane.setViewportView(table);
		panelForTable.add(scrollPane, BorderLayout.CENTER);
		add(panelForTable, BorderLayout.CENTER);

		//--SOUTH
		resultPanel.add(lblTotalSales);
		resultPanel.add(txtTotalSales);
		resultPanel.add(lblUnpaid);
		resultPanel.add(txtTotalUnpaid);
		panelForTable.add(resultPanel, BorderLayout.SOUTH);

		//테이블 데이터s 뿌리기
		makeUpTableList(UNCHECK);
	}

	
	//콤보박스 load
	private void makeUpComList() {
		
		List<Buyer> list = BuyerService.getInstance().selectAll();
		
		for (int i = 0; i < list.size(); i++) {
			combo.addItem(list.get(i).getShopName());
		}
	}

	//테이블 데이터 load
	private void makeUpTableList(boolean totalCheck) {

		List<Sale> list = getListFromDB(totalCheck);
		String[] COL_NAMES = { "고객상호명", "품목명", "주문수량", "입금여부", "판매가격", "매출금", "미수금" };
		Object[][] temp = new Object[list.size()][COL_NAMES.length];

		int idx = 0;
		int rowCnt = 0;
		totalSales = 0;// 매출금 합계
		totalUnpaid = 0;// 미수금 합계

		for (Sale c : list) {
			if (totalCheck == UNCHECK) {
				if (combo.getSelectedItem().equals(c.getShopName().getShopName())) {
					temp = getRowData(temp, c, idx);
					idx++;
				} else
					continue;
			} else {
				temp = getRowData(temp, c, idx);
				idx++;
			}
		}

		// 빈행 처리 
		rowCnt = idx;
		Object[][] data = adjustEmptyRow(temp, new Object[rowCnt][COL_NAMES.length]);
		mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);

		handleTableDesign();// 테이블 정렬 및 기타 테이블 작업
		handleSumData();// 합계 데이터 뿌리기
	}

	
	/*-----------------------------------Table Setting------------------------------------*/
	/*-----------------------------------Table Setting------------------------------------*/

	//DB에서 데이터 가져오기
	private List<Sale> getListFromDB(boolean totalCheck) {
		List<Sale> list;
		if (totalCheck == CHECK)
			list = SaleService.getInstance().selectAllOrderByBuyer();
		else
			list = SaleService.getInstance().selectAllOrderByTitle();
		return list;
	}

	//1행 만들기
	private Object[][] getRowData(Object[][] temp, Sale c, int idx) {
		temp[idx][0] = c.getShopName().getShopName();
		temp[idx][1] = c.getTitle().getTitle();
		temp[idx][2] = c.getOrderCount();
		temp[idx][3] = new Boolean(!c.isPayment());
		temp[idx][4] = c.getTitle().getSellPrice();
		temp[idx][5] = c.getOrderCount() * c.getTitle().getSellPrice();
		totalSales += c.getOrderCount() * c.getTitle().getSellPrice();

		if (c.isPayment()) {
			temp[idx][6] = c.getOrderCount() * c.getTitle().getSellPrice();
			totalUnpaid += c.getOrderCount() * c.getTitle().getSellPrice();
		} else {
			temp[idx][6] = "";
		}

		return temp;
	}

	//동적으로 생성된 행의 수에 맞게 빈행 제거 
	private Object[][] adjustEmptyRow(Object[][] temp, Object[][] newArr) {
		Object[][] data = newArr;
		for (int i = 0; i < data.length; i++) {
			data[i][0] = temp[i][0];
			data[i][1] = temp[i][1];
			data[i][2] = temp[i][2];
			data[i][3] = temp[i][3];
			data[i][4] = String.format("%,d", temp[i][4]);
			data[i][5] = String.format("%,d", temp[i][5]);
			if (temp[i][6].equals("")) {
				data[i][6] = "";
			} else {
				data[i][6] = String.format("%,d", temp[i][6]);
			}
		}
		return data;
	}
	
	//기타 테이블 디자인 관련 처리 메소드
	private void handleTableDesign() {
		table.getColumnModel().getColumn(3).setCellRenderer(table.getDefaultRenderer(Boolean.class));
		table.setFont(table.getFont().deriveFont(11.0f));
		mft.tableCellAlignment(table, SwingConstants.CENTER, 2);
		mft.tableCellAlignment(table, SwingConstants.RIGHT, 4, 5, 6);
		mft.resizeColumnWidth(table);
		mft.tableHeaderAlignment(table);
	}

	
	/*-----------------------------Result_Panel------------------------------------*/
	/*-----------------------------Result_Panel------------------------------------*/

	//해당하는 열의 합계 데이터를 결과패널에 INPUT
	private void handleSumData() {
		if (totalSales == 0) {
			txtTotalSales.setText(String.format("%s", "-"));
			txtTotalUnpaid.setText(String.format("%,d", totalUnpaid));
			txtTotalSales.setHorizontalAlignment(SwingConstants.CENTER);
		} else if (totalUnpaid == 0) {
			txtTotalSales.setText(String.format("%,d", totalSales));
			txtTotalUnpaid.setText(String.format("%s", "-"));
			txtTotalUnpaid.setHorizontalAlignment(SwingConstants.CENTER);
		} else {
			txtTotalSales.setText(String.format("%,d", totalSales));
			txtTotalUnpaid.setText(String.format("%,d", totalUnpaid));
			txtTotalUnpaid.setHorizontalAlignment(SwingConstants.RIGHT);
			txtTotalSales.setHorizontalAlignment(SwingConstants.RIGHT);
		}

	}
	
	/*-----------------------------------event 처리------------------------------------*/
	/*-----------------------------------event 처리------------------------------------*/

	public void itemStateChanged(ItemEvent e) {
		//콤보박스 변화에 따라 테이블 데이터 변경
		if (e.getSource() == combo)
			makeUpTableList(UNCHECK);
	}

	
	public void actionPerformed(ActionEvent e) {
		//전체 선택 처리
		if (ckboxForAll.isSelected()) {
			makeUpTableList(CHECK);
			combo.setEnabled(false);
		
		} else if (!ckboxForAll.isSelected()) {
			makeUpTableList(UNCHECK);
			combo.setEnabled(true);
		}
		
		if (e.getSource() == btnExit) {
			btnExitActionPerformed(e);
		}
	}

	// EXIT 처리
	protected void btnExitActionPerformed(ActionEvent e) {

		setVisible(false);

		JLabel lblMainTitle = new JLabel(new ImageIcon("src/img/logo.gif"));
		lblMainTitle.setFont(new Font("굴림", Font.PLAIN, 20));
		lblMainTitle.setHorizontalAlignment(SwingConstants.CENTER);

		this.getParent().add(lblMainTitle, BorderLayout.CENTER);
		revalidate();

	}
}
