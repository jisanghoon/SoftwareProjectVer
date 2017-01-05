package kr.or.dgit.bigdata.swmng.customer.submenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.service.SaleService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;

public class ReportPanel_version2 extends JPanel implements ActionListener {
	private JRadioButton rdTotalSale;
	private JRadioButton rdTradeDetail;
	private JPanel panelForTable;
	private JTable table;
	private JScrollPane scrollPane;
	private ModelForTable mft;

	public ReportPanel_version2() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel title = new JLabel("판매현황 보고서");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("굴림", Font.BOLD, 18));
		panel.add(title, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(64, 64, 64)), "\uBCF4\uACE0\uC11C \uC120\uD0DD",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.add(panel_1, BorderLayout.CENTER);

		rdTotalSale = new JRadioButton("S/W 전체 판매현황");
		rdTradeDetail = new JRadioButton("거래명세서");
		panel_1.add(rdTotalSale);
		panel_1.add(rdTradeDetail);

		ButtonGroup group = new ButtonGroup(); // 라디오버튼 그룹화를 위한 버튼그룹 설정
		group.add(rdTotalSale);
		group.add(rdTradeDetail);

		rdTotalSale.addActionListener(this);
		rdTradeDetail.addActionListener(this);

		panelForTable = new JPanel();
		panelForTable.setBorder(new EmptyBorder(10, 0, 10, 0));
		add(panelForTable, BorderLayout.CENTER);
		panelForTable.setLayout(new BorderLayout(0, 0));
		table = new JTable();

		scrollPane = new JScrollPane();
		panelForTable.add(scrollPane, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == rdTradeDetail) {
			rdTradeDetailActionPerformed(e);
		} else if (e.getSource() == rdTotalSale) {
			rdTotalSaleActionPerformed(e);
		}
		scrollPane.setViewportView(table);
	/*	table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);*/
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


		revalidate();
		repaint();
	}

	protected void rdTotalSaleActionPerformed(ActionEvent e) {
		List<Sale> reportlist = SaleService.getInstance().selectAllSortDate();

		String[] COL_NAMES = { "년월", "분류", "품목명", "주문번호", "주문수량", "판매금액" };
		String[][] data = new String[reportlist.size() + 2][6];
		int idx = 0;
		int total = 0;
		String beforeDate = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy년MM월dd");
		for (Sale c : reportlist) {

			String date = format.format(c.getDate());
			if (beforeDate.equals(date)) {
				data[idx][0] = "\"";
			} else {
				data[idx][0] = date;
				beforeDate = date;
			}

			// data[idx][0] = format.format(c.getDate());
			data[idx][1] = c.getTitle().getCategory();
			data[idx][2] = c.getTitle().getTitle();
			data[idx][3] = c.getNo() + "";
			data[idx][4] = String.format("%,d", c.getOrderCount());
			data[idx][5] = String.format("%,d", (c.getOrderCount() * c.getTitle().getSellPrice()));
			total += c.getOrderCount() * c.getTitle().getSellPrice();
			idx++;
		}
		idx++;
		data[idx][0] = new String("총 합 계");
		data[idx][5] = String.format("%,d", total);

		mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);
		table.setPreferredScrollableViewportSize(new Dimension(600, 500)); // 테이블
		mft.tableCellAlignment(table, SwingConstants.CENTER, 0, 1, 2, 3, 4);
		mft.tableCellAlignment(table, SwingConstants.RIGHT, 5);
		mft.resizeColumnWidth(table);
		
	}

	protected void rdTradeDetailActionPerformed(ActionEvent e) {
		List<Sale> reportlist = SaleService.getInstance().selectAllSortSupplier();
		String[] COL_NAMES = { "공급회사명", "주문일자", "고객상호", "품명", "수량", "단가", "금액", "세금", "총납품금액" };
		String[][] data = new String[reportlist.size() + 2][9];
		int idx = 0;
		int total = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String beforeConame = "";
		for (Sale c : reportlist) {
			int orderCnt = c.getOrderCount();
			int supPrice = c.getTitle().getSupPrice();
			int cost = orderCnt * supPrice;
			int tax = (int) (cost * 0.1);

			String coname = c.getTitle().getCoName().getCoName();
			if (beforeConame.equals(coname)) {
				data[idx][0] = "\"";
			} else {
				data[idx][0] = coname;
				beforeConame = coname;
			}

			data[idx][1] = format.format(c.getDate());
			data[idx][2] = c.getShopName().getShopName();
			data[idx][3] = c.getTitle().getTitle();
			data[idx][4] = String.format("%,d", orderCnt);
			data[idx][5] = String.format("%,d", supPrice);
			data[idx][6] = String.format("%,d", (cost));
			data[idx][7] = String.format("%,d", (tax));
			data[idx][8] = String.format("%,d", (cost + tax));
			total += cost + tax;
			idx++;
		}
		idx++;
		data[idx][7] = new String("총 납품금액 합계 :");
		data[idx][8] = String.format("%,d", total);

		ModelForTable mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);
		table.setPreferredScrollableViewportSize(new Dimension(750, 500)); // 테이블
		mft.tableCellAlignment(table, SwingConstants.CENTER, 0, 1, 2, 3, 4);
		mft.tableCellAlignment(table, SwingConstants.RIGHT, 5, 6, 7, 8);
		mft.resizeColumnWidth(table);

	}

}
