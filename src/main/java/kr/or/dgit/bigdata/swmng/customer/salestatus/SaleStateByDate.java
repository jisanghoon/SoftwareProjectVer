package kr.or.dgit.bigdata.swmng.customer.salestatus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.service.SaleService;
import kr.or.dgit.bigdata.swmng.util.DateFomatter;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;

@SuppressWarnings("serial")
public class SaleStateByDate extends JPanel implements ActionListener {
	private JTable table;
	private ModelForTable mft;
	private final boolean CHECK = true;
	private final boolean UNCHECK = false;
	private JButton btnExit;
	private JDatePickerImpl datePicker1;
	private JDatePickerImpl datePicker2;
	private JButton btnSearch;
	private JButton btn2;
	private JButton btn;

	/**
	 * Create the panel.
	 */
	public SaleStateByDate() {

		JPanel pnForControl = new JPanel();
		JLabel lblTitle = new JLabel("날짜별 판매현황 조회");
		JPanel subPnForControl = new JPanel();
		JLabel lblCombo = new JLabel("날   짜 : ");
		btnExit = new JButton("[닫기]");
		btnSearch = new JButton("[검색]");
		JPanel PnForTable = new JPanel();
		JScrollPane scrollPane = new JScrollPane();
		table = new JTable();

		setLayout(new BorderLayout(0, 0));
		pnForControl.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnForControl.setLayout(new BorderLayout(0, 0));
		subPnForControl.setBorder(new LineBorder(Color.DARK_GRAY));
		PnForTable.setLayout(new BorderLayout(0, 0));

		lblTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

		btnExit.addActionListener(this);
		btnSearch.addActionListener(this);

		add(pnForControl, BorderLayout.NORTH);
		pnForControl.add(lblTitle, BorderLayout.NORTH);
		pnForControl.add(subPnForControl, BorderLayout.CENTER);
		subPnForControl.add(lblCombo);
		scrollPane.setViewportView(table);
		PnForTable.add(scrollPane, BorderLayout.CENTER);
		add(PnForTable, BorderLayout.CENTER);

		/*--------------------------------------------------------*/

		UtilDateModel leftModel = new UtilDateModel();
		GregorianCalendar time = new GregorianCalendar(2009, 0, 1);
		leftModel.setValue(time.getTime());
		leftModel.setSelected(true);
		Properties p1 = new Properties();
		p1.put("text.today", "오늘");
		JDatePanelImpl datePanel1 = new JDatePanelImpl(leftModel, p1);
		datePicker1 = new JDatePickerImpl(datePanel1, new DateFomatter());
		btn = (JButton) (datePicker1.getComponent(1));
		subPnForControl.add(datePicker1);
		datePicker1.setPreferredSize(new Dimension(130, 27));
		datePanel1.getComponent(0).setPreferredSize(new Dimension(250, 190));
		/*----------------------------------------------------------	*/

		JLabel label = new JLabel("~");
		subPnForControl.add(label);

		/*--------------------------------------------------	*/

		UtilDateModel rightModel = new UtilDateModel();
		Date today2 = new Date();
		rightModel.setValue(today2);
		rightModel.setSelected(true);
		Properties p2 = new Properties();
		p2.put("text.today", "오늘");

		JDatePanelImpl datePanel2 = new JDatePanelImpl(rightModel, p2);
		datePicker2 = new JDatePickerImpl(datePanel2, new DateFomatter());
		btn2 = (JButton) (datePicker2.getComponent(1));
		subPnForControl.add(datePicker2);
		datePicker2.setPreferredSize(new Dimension(130, 27));
		datePanel2.getComponent(0).setPreferredSize(new Dimension(250, 190));
		/*----------------------------------------------------------	*/
		subPnForControl.add(btnSearch);
		subPnForControl.add(btnExit);

		createList(UNCHECK);
	}

	@SuppressWarnings("deprecation")
	private void createList(boolean isCheck) {
		Date former = makeDate(datePicker1);
		Date latter = makeDate(datePicker2);

		List<Sale> list = SaleService.getInstance().selectBetweenDates(former, latter);
		String[] COL_NAMES = { "주문번호", "고객상호", "제품명", "주문수량", "입금여부", "주문일자" };
		Object[][] data = new Object[list.size()][COL_NAMES.length];

		int idx = 0;
		int rowCnt = 0;

		for (Sale c : list) {
			rowCnt++;
			data = getRowData(data, c, idx);
			idx++;
		}
		rowCnt = idx;

		mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);

		handleTableDesign();
	}

	private void handleTableDesign() {
		table.getColumnModel().getColumn(4).setCellRenderer(table.getDefaultRenderer(Boolean.class));
		mft.resizeColumnWidth(table);
		mft.tableCellAlignment(table, SwingConstants.CENTER, 0, 3, 5);
		mft.tableHeaderAlignment(table);
		table.setFont(table.getFont().deriveFont(11.0f));
	}

	private Object[][] getRowData(Object[][] data, Sale c, int idx) {
		Date date;
		data[idx][0] = c.getNo() + "";
		data[idx][1] = c.getShopName();
		data[idx][2] = c.getTitle();
		data[idx][3] = String.format("%,d", c.getOrderCount());
		data[idx][4] = new Boolean(!c.isPayment());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		date = new Date(c.getDate().getYear(), c.getDate().getMonth() - 1, c.getDate().getDay());
		data[idx][5] = dateFormat.format(date);

		return data;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnExit) {
			btnExitActionPerformed(e);
		}
		if (e.getSource() == btnSearch) {
			btnSearchActionPerformed(e);
		}
	}

	protected void btnSearchActionPerformed(ActionEvent e) {
		createList(UNCHECK);
	}

	private Date makeDate(JDatePickerImpl datePicker) {
		String[] strDate = datePicker.getJFormattedTextField().getText().split("/");
		int[] numDateArr = new int[3];
		for (int i = 0; i < strDate.length; i++) {
			numDateArr[i] = Integer.parseInt(strDate[i]);
		}
		GregorianCalendar time = new GregorianCalendar(numDateArr[0], numDateArr[1], numDateArr[2]);
		return time.getTime();
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
