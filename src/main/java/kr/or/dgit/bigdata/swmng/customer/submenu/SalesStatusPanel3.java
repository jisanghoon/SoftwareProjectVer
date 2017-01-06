package kr.or.dgit.bigdata.swmng.customer.submenu;

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

public class SalesStatusPanel3 extends JPanel implements ActionListener  {
	private JTable table;
	private ModelForTable mft;
	private final boolean CHECK = true;
	private final boolean UNCHECK = false;
	private JButton btnExit;
	private JDatePickerImpl datePicker1;
	private JDatePickerImpl datePicker2;
	private JButton btnSearch;

	/**
	 * Create the panel.
	 */
	public SalesStatusPanel3() {

		setLayout(new BorderLayout(0, 0));

		JPanel pnForControl = new JPanel();
		pnForControl.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(pnForControl, BorderLayout.NORTH);
		pnForControl.setLayout(new BorderLayout(0, 0));

		JLabel lblTitle = new JLabel("날짜별 판매현황 조회");
		lblTitle.setFont(new Font("굴림", Font.PLAIN, 18));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		pnForControl.add(lblTitle, BorderLayout.NORTH);

		JPanel subPnForControl = new JPanel();
		subPnForControl.setBorder(new LineBorder(Color.DARK_GRAY));
		pnForControl.add(subPnForControl, BorderLayout.CENTER);

		JLabel lblCombo = new JLabel("날   짜 : ");
		subPnForControl.add(lblCombo);

		btnExit = new JButton("[닫기]");
		btnExit.addActionListener(this);
		
		
/*--------------------------------------------------------*/		
		
		UtilDateModel model1 = new UtilDateModel();
		Date today1 = new Date();
		model1.setValue(today1);
		model1.setSelected(true);
		Properties p1 = new Properties();
		p1.put("text.today", "오늘");

		JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p1);
		datePicker1 = new JDatePickerImpl(datePanel1, new DateFomatter());
		/*datePanel1.setSize(200, 100);*/
		subPnForControl.add(datePicker1);
		datePicker1.setPreferredSize(new Dimension(130, 27));
		datePanel1.getComponent(0).setPreferredSize(new Dimension(250, 190));
/*----------------------------------------------------------	*/	
		
		JLabel label = new JLabel("~");
		subPnForControl.add(label);
		
/*--------------------------------------------------	*/

		UtilDateModel model2 = new UtilDateModel();
		Date today2 = new Date();
		model2.setValue(today2);
		model2.setSelected(true);
		Properties p2 = new Properties();
		p2.put("text.today", "오늘");

		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
		datePicker2 = new JDatePickerImpl(datePanel2, new DateFomatter());
		subPnForControl.add(datePicker2);
		datePicker2.setPreferredSize(new Dimension(130, 27));
		datePanel2.getComponent(0).setPreferredSize(new Dimension(250, 190));
/*----------------------------------------------------------	*/		
		btnSearch = new JButton("[검색]");
		btnSearch.addActionListener(this);
		
		subPnForControl.add(btnSearch);
		
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

	@SuppressWarnings("deprecation")
	private void refleshTable(boolean isCheck) {
		Date former=createDate(datePicker1);
		Date latter=createDate(datePicker2);
		Date date;
		List<Sale> list = SaleService.getInstance().selectBetweenDates(former, latter);
		String[] COL_NAMES = { "주문번호", "고객상호", "품명", "주문수량", "입금여부", "주문일자"};
		String[][] data = new String[list.size()][COL_NAMES.length];
		int idx = 0;
		int rowCnt = 0;
		for (Sale c : list) {

				rowCnt++;
				data[idx][0] = c.getNo()+"";
				data[idx][1] = c.getShopName().getShopName();
				data[idx][2] = c.getTitle().getTitle();
				data[idx][3] = String.format("%,d",c.getOrderCount());
				data[idx][4] = !c.isPayment() + "";
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
				date=new Date(c.getDate().getYear(), c.getDate().getMonth()-1, c.getDate().getDay()); 						
				data[idx][5] = dateFormat.format(date);
				idx++;
		}
		

		mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);
		mft.resizeColumnWidth(table);
		mft.tableCellAlignment(table, SwingConstants.CENTER, 0,3,4,5);
		mft.tableHeaderAlignment(table);
		table.setFont(table.getFont().deriveFont(11.0f));
		
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
		refleshTable(UNCHECK);
	}

	private Date createDate(JDatePickerImpl datePicker) {
		String[]strDate=datePicker.getJFormattedTextField().getText().split("/");
		int[] numDateArr=new int[3];
		for (int i = 0; i < strDate.length; i++) {
			numDateArr[i]=Integer.parseInt(strDate[i]);
		}
		GregorianCalendar time = new GregorianCalendar(numDateArr[0],numDateArr[1],numDateArr[2]);
		return time.getTime();
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
