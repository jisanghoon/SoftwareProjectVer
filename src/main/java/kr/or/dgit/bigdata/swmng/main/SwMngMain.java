package kr.or.dgit.bigdata.swmng.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.sun.org.apache.regexp.internal.recompile;

import kr.or.dgit.bigdata.swmng.customersubmenu.BtnPanel;
import kr.or.dgit.bigdata.swmng.customersubmenu.ListPanel;
import kr.or.dgit.bigdata.swmng.customersubmenu.RegisterPanel;

public class SwMngMain extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JMenuItem listCompany;
	private JMenuItem listSoftware;
	private JMenuItem listCustomer;
	ListPanel lp = new ListPanel();
	private JLabel lblMainTitle;
	RegisterPanel rp = new RegisterPanel();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwMngMain frame = new SwMngMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SwMngMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 400, 556, 300);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// 고객관리 메뉴
		JMenu mnCustomer = new JMenu("고객관리");
		menuBar.add(mnCustomer);
		// 고갠관리 하위 메뉴
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

		// 현황관리 메뉴
		JMenu mnStatement = new JMenu("현황관리");
		menuBar.add(mnStatement);

		// 보고서 메뉴
		JMenu mnReport = new JMenu("보고서");
		menuBar.add(mnReport);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		lblMainTitle = new JLabel("소프트웨어 판매관리 프로그램");
		lblMainTitle.setFont(new Font("굴림", Font.PLAIN, 20));
		lblMainTitle.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblMainTitle, BorderLayout.CENTER);
	}

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		contentPane.removeAll();
	
		if (e.getActionCommand() == "등록" && lp.getTitle().equals("공급회사")) {
			rp.createRegisterPanel(lp.getTitle());
			rp.setVisible(true);
		} else if (e.getActionCommand() == "등록" && lp.getTitle().equals("소프트웨어")) {
			rp.createRegisterPanel(lp.getTitle());
			rp.setVisible(true);
		} else if (e.getActionCommand() == "등록" && lp.getTitle().equals("고객")) {
			rp.createRegisterPanel(lp.getTitle());
			rp.setVisible(true);
		} else {
			showList(e.getActionCommand());
		}
		contentPane.add(new BtnPanel(), BorderLayout.SOUTH);
		revalidate();
	}

	void showList(String e) {

		switch (e) {
		case "공급회사 목록":
			lp.ListPanel(e);
			break;
		case "소프트웨어 목록":
			lp.ListPanel(e);
			break;
		case "고객목록":
			lp.ListPanel(e);
			break;
		}
		contentPane.add(lp, BorderLayout.CENTER);
	}

}
