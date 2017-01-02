package kr.or.dgit.bigdata.swmng.customersubmenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.service.SaleService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;

public class ReportPanle extends JPanel implements  ActionListener {
	private JRadioButton rdTotalSale;
	private JRadioButton rdTradeDetail;
	private JScrollPane scrollPane;
	private JTable table;
	private JPanel panelForTable;
	
	/**
	 * Create the panel.
	 */
	public ReportPanle() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel PanelForOption = new JPanel();
		PanelForOption.setBorder(new TitledBorder(new TitledBorder(new EmptyBorder(0, 40, 0, 40), "\uBCF4\uACE0\uC11C \uC120\uD0DD", TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, new Color(64, 64, 64)), "\uD310\uB9E4\uD604\uD669 \uBCF4\uACE0\uC11C", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(64, 64, 64)));
		add(PanelForOption, BorderLayout.NORTH);
		PanelForOption.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		rdTotalSale = new JRadioButton("S/W 전체 판매현황");
		rdTotalSale.addActionListener(this);
		PanelForOption.add(rdTotalSale);
		
		rdTradeDetail = new JRadioButton("거래명세서");
		rdTradeDetail.addActionListener(this);
		PanelForOption.add(rdTradeDetail);
		
		ButtonGroup  group = new ButtonGroup(); //라디오버튼 그룹화를 위한 버튼그룹 설정
        //같은 그룹끼리는 그룹중에 1개만 선택된다.

		group.add(rdTotalSale);
		group.add(rdTradeDetail);
		
		panelForTable = new JPanel();
		table = new JTable();
		//table.setTableHeader(null);
		scrollPane = new JScrollPane();
		
		panelForTable.add(scrollPane);
		add(panelForTable, BorderLayout.CENTER);
		
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == rdTradeDetail) {
			rdTradeDetailActionPerformed(arg0);
		}
		if (arg0.getSource() == rdTotalSale) {
			rdTotalSaleActionPerformed(arg0);
		}
	}
	protected void rdTotalSaleActionPerformed(ActionEvent arg0) {
		List<Sale> reportlist=SaleService.getInstance().selectAllSortDate();

		
		String[] COL_NAMES = { "년월", "분류", "품목명", "주문번호","주문수량","판매금액"};
		String[][] data = new String[reportlist.size()+2][6];
		int idx = 0;
		int total=0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy년MM월dd");
		for (Sale c : reportlist) {
			data[idx][0] = format.format(c.getDate());
			data[idx][1] = c.getTitle().getCategory();
			data[idx][2] = c.getTitle().getTitle();
			data[idx][3] = c.getNo()+"";
			data[idx][4] = String.format("%,d",c.getOrderCount());
			data[idx][5] = String.format("%,d",(c.getOrderCount()*c.getTitle().getSellPrice()));
			total+=c.getOrderCount()*c.getTitle().getSellPrice();
			idx++;
		}
		idx++;
		data[idx][0] = new String("총 합 계");
		data[idx][5]=String.format("%,d", total);
		
		ModelForTable mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		resizeColumnWidth(table);
		table.setPreferredScrollableViewportSize(new Dimension(600, 500)); // 테이블 크기
		revalidate();
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		 
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		 
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule = table.getColumnModel();
		 
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
		tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
	}


	protected void rdTradeDetailActionPerformed(ActionEvent arg0) {
		List<Sale> reportlist=SaleService.getInstance().selectAllSortSupplier();
		String[] COL_NAMES = { "공급회사명", "주문일자", "고객상호", "품명","수량","단가","금액","세금","총납품금액"};
		String[][] data = new String[reportlist.size()+2][9];
		int idx = 0;
		int total=0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (Sale c : reportlist) {
			data[idx][0] = c.getTitle().getCoName().getCoName();
			data[idx][1] = format.format(c.getDate());
			data[idx][2] = c.getShopName().getShopName();
			data[idx][3] = c.getTitle().getTitle();
			int orderCnt=c.getOrderCount();
			int supPrice=c.getTitle().getSupPrice();
			int cost=orderCnt*supPrice;
			int tax=(int) (cost*0.1);
			data[idx][4] = String.format("%,d",orderCnt);
			data[idx][5] = String.format("%,d",supPrice);
			data[idx][6] = String.format("%,d",(cost));
			data[idx][7] = String.format("%,d",(tax));
			data[idx][8] = String.format("%,d",(cost+tax));
			total+=cost+tax;
			idx++;
		}
		idx++;
		data[idx][7] = new String("총 납품금액 합계 :");
		data[idx][8]=String.format("%,d", total);
		ModelForTable mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false); 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setPreferredScrollableViewportSize(new Dimension(750, 500)); // 테이블 크기
		resizeColumnWidth(table);
		revalidate();
		repaint();
		
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		 
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		 
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule = table.getColumnModel();
		 
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
		tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		
		
	}
	public void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 50; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
}
