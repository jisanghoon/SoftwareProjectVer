package kr.or.dgit.bigdata.swmng.customer.submenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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

public class SalesStatusPanel3 extends JPanel implements ItemListener, ActionListener {
	private JTable table;
	private ModelForTable mft;
	private final boolean CHECK = true;
	private final boolean UNCHECK = false;
	private JButton btnExit;
	private JTextField textField;
	private JTextField textField_1;
	private JDatePickerImpl datePicker1;
	private JDatePickerImpl datePicker2;

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
		

/*----------------------------------------------------------	*/		
		JButton btnNewButton = new JButton("[검색]");
		subPnForControl.add(btnNewButton);
		
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
	}

	private void refleshTable(boolean isCheck) {
		List<Sale> list = SaleService.getInstance().selectAll();
		String[] COL_NAMES = { "주문번호", "고객상호", "품명", "주문수량", "입금여부", "주문일자"};

		String[][] temp = new String[list.size()][COL_NAMES.length];
		int idx = 0;
		int rowCnt = 0;
		for (Sale c : list) {

				rowCnt++;
				temp[idx][0] = c.getShopName().getShopName();
				temp[idx][1] = c.getTitle().getTitle();
				temp[idx][2] = c.getOrderCount() + "";
				temp[idx][3] = !c.isPayment() + "";
				temp[idx][4] = c.getTitle().getSellPrice() + "";
				temp[idx][5] = (c.getOrderCount() * c.getTitle().getSellPrice()) + "";
				idx++;
			
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
		}

		mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);
		mft.tableCellAlignment(table, SwingConstants.CENTER, 0, 3);
	}

	public void actionPerformed(ActionEvent e) {

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
