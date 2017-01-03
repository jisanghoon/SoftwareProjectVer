package kr.or.dgit.bigdata.swmng.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import kr.or.dgit.bigdata.swmng.customersubmenu.BtnPanel;
import kr.or.dgit.bigdata.swmng.customersubmenu.GraphPanel;
import kr.or.dgit.bigdata.swmng.customersubmenu.ListPanel;
import kr.or.dgit.bigdata.swmng.customersubmenu.RegisterEditPanel;
import kr.or.dgit.bigdata.swmng.customersubmenu.ReportPanel_version2;

public class SwMngMain extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JMenuItem listCompany;
	private JMenuItem listSoftware;
	private JMenuItem listCustomer;
	private JMenuItem softwareOrder;
	private JMenuItem salesReportOrderByBuyer;
	private JMenuItem salesReportOrderBySW;
	private JMenuItem salesReportOrderByDate;
	private JMenuItem mnReportList;
	private JMenuItem orderStatusGraph;
	ReportPanel_version2 reportPanel = new ReportPanel_version2();
	GraphPanel graphFrame = new GraphPanel();
	ListPanel lp = new ListPanel();
	private JLabel lblMainTitle;
	RegisterEditPanel rp = new RegisterEditPanel();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwMngMain frame = new SwMngMain();
					UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
					SwingUtilities.updateComponentTreeUI(frame);
					frame.setVisible(true);
					frame.setTitle("소프트웨어 매니지먼트");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SwMngMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 400, 500, 300);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// 고객관리 메뉴
		JMenu mnCustomer = new JMenu("고객관리");
		menuBar.add(mnCustomer);
		// 고객관리 하위 메뉴
		listCompany = new JMenuItem("공급회사 목록");
		mnCustomer.add(listCompany);
		listSoftware = new JMenuItem("소프트웨어 목록");
		mnCustomer.add(listSoftware);
		listCustomer = new JMenuItem("고객목록");
		mnCustomer.add(listCustomer);
		listCompany.addActionListener(this);
		listSoftware.addActionListener(this);
		listCustomer.addActionListener(this);

		// 주문관리 메뉴
		JMenu mnOrder = new JMenu("주문관리");
		menuBar.add(mnOrder);
		// 주문관리 하위 메뉴
		softwareOrder = new JMenuItem("소프트웨어 주문");
		mnOrder.add(softwareOrder);

		// 현황관리 메뉴
		JMenu mnStatement = new JMenu("현황관리");
		menuBar.add(mnStatement);

		// 현황관리 하위 메뉴
		salesReportOrderByBuyer = new JMenuItem("고객별 판매현황");
		salesReportOrderBySW = new JMenuItem("SW별 판매현황");
		salesReportOrderByDate = new JMenuItem("날짜별 판매현황");
		mnStatement.add(salesReportOrderByBuyer);
		mnStatement.add(salesReportOrderBySW);
		mnStatement.add(salesReportOrderByDate);

		// 보고서 메뉴
		JMenu mnReport = new JMenu("보고서");
		menuBar.add(mnReport);

		mnReportList = new JMenuItem("판매현황 보고서");
		mnReportList.addActionListener(this);
		mnReport.add(mnReportList);

		orderStatusGraph = new JMenuItem("주문현황");
		orderStatusGraph.addActionListener(this);
		mnReport.add(orderStatusGraph);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		lblMainTitle = new JLabel(new ImageIcon("src/img/logo.gif"));
		lblMainTitle.setFont(new Font("굴림", Font.PLAIN, 20));
		lblMainTitle.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblMainTitle, BorderLayout.CENTER);
	}

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		contentPane.removeAll();
		setBounds(700, 400, 500, 300);
		if (e.getActionCommand() == "등록" && lp.getTitle().equals("공급회사")) {
			rp.createRegisterPanel(lp.getTitle());
			rp.setVisible(true);
		} else if (e.getActionCommand() == "등록" && lp.getTitle().equals("소프트웨어")) {
			rp.createRegisterPanel(lp.getTitle());
			rp.setVisible(true);
		} else if (e.getActionCommand() == "등록" && lp.getTitle().equals("고객")) {
			rp.createRegisterPanel(lp.getTitle());
			rp.setVisible(true);
		} else if (e.getActionCommand() == "판매현황 보고서") {
			contentPane.add(reportPanel, BorderLayout.CENTER);
			setSize(900, 700);
			setLocationRelativeTo(null);

		} else if (e.getActionCommand() == "주문현황") {
			GraphPanel graphFrame = new GraphPanel();
			contentPane.add(graphFrame, BorderLayout.CENTER);
			graphFrame.setVisible(true);
			setSize(600, 600);
			setLocationRelativeTo(null);
		} else {
			showList(e.getActionCommand());
		}

		if (e.getActionCommand() != "판매현황 보고서" && e.getActionCommand() != "주문현황") {
			contentPane.add(new BtnPanel(), BorderLayout.SOUTH);
		}
		revalidate();

	}

	void showList(String e) {

		switch (e) {
		case "공급회사 목록":
			lp.createList(e);
			break;
		case "소프트웨어 목록":
			lp.createList(e);
			break;
		case "고객목록":
			lp.createList(e);
			break;
		}
		contentPane.add(lp, BorderLayout.CENTER);
	}

}
