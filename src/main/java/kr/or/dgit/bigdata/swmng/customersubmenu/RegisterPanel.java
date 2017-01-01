package kr.or.dgit.bigdata.swmng.customersubmenu;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.service.BuyerService;
import kr.or.dgit.bigdata.swmng.service.CompanyService;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;

public class RegisterPanel extends JFrame implements ActionListener {
	private JLabel[] regForm = new JLabel[4];
	private JTextField[] regTf = new JTextField[4];
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblTItle;
	private JPanel confirmPanel;
	private JButton btnOk;
	private JButton btnCancel;
	JComboBox cmbCategory = new JComboBox();
	JComboBox cmbCoName = new JComboBox();
	int titleArray = 0;
	private String[][] col = { { "회사번호", "회 사 명", "주 소", "전화번호" }, { "품목번호", "품 목 명", "공급가격", "판매가격" },
			{ "고객번호", "상 호 명", "주 소", "전화번호" } };

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterPanel frame = new RegisterPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RegisterPanel() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(700, 400, 445, 168);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 4, 0, 0));

		lblTItle = new JLabel();
		lblTItle.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTItle, BorderLayout.NORTH);

		confirmPanel = new JPanel();
		contentPane.add(confirmPanel, BorderLayout.SOUTH);

		btnOk = new JButton("확인");
		btnOk.addActionListener(this);
		confirmPanel.add(btnOk);

		btnCancel = new JButton("취소");
		confirmPanel.add(btnCancel);
		btnCancel.addActionListener(this);

	}

	// 받는 title값에 따라 등록 화면 셋팅
	public void createRegisterPanel(String title) {
		String no = null;
		if (title.equals("공급회사")) {
			lblTItle.setText("공급회사 등록");
			titleArray = 0;
			no = CompanyService.getInstance().selectMaxNo().getNo() + "";
		} else if (title.equals("소프트웨어")) {
			lblTItle.setText("소프트웨어 등록");
			titleArray = 1;
			no = SoftwareService.getInstance().selectMaxNo().getNo() + "";
			List<Software> softwareList = SoftwareService.getInstance().selectCategory();
			List<Company> companyList = CompanyService.getInstance().selectCoName();
			for (Software s : softwareList) {
				cmbCategory.addItem(s.getCategory());
			}
			for (Company c : companyList) {
				cmbCoName.addItem(c.getCoName());
			}
			panel.add(new JLabel("분 류 명"));
			panel.add(cmbCategory);
			panel.add(new JLabel("공급회사명"));
			panel.add(cmbCoName);
		} else {
			lblTItle.setText("고객 등록");
			titleArray = 2;
			no = BuyerService.getInstance().selectMaxNo().getNo() + "";
		}

		for (int i = 0; i < regForm.length; i++) {
			regForm[i] = new JLabel(col[titleArray][i]);
			regTf[i] = new JTextField();
			panel.add(regForm[i]);
			panel.add(regTf[i]);
		}
		regTf[0].setText(no);
		regTf[0].setEditable(false);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {
			actionPerformedBtnOk(e);
		}
		if (e.getSource() == btnCancel) {
			setVisible(false);
		}
	}

	protected void actionPerformedBtnOk(ActionEvent e) {
		String msg = "등록이 완료되었습니다";
		if (regTf[1].getText().trim().equals("") || regTf[2].getText().trim().equals("")
				|| regTf[3].getText().trim().equals("")) {
			msg = "값을 입력하세요";
		} else if (titleArray == 0) {
			CompanyService.getInstance().insertItem(new Company(Integer.parseInt(regTf[0].getText().trim()),
					regTf[1].getText().trim(), regTf[2].getText().trim(), regTf[3].getText().trim()));
		} else if (titleArray == 1) {
			SoftwareService.getInstance()
					.insertItem(new Software(Integer.parseInt(regTf[0].getText().trim()),
							cmbCategory.getSelectedItem() + "", regTf[1].getText().trim(),
							Integer.parseInt(regTf[2].getText().trim()), Integer.parseInt(regTf[3].getText().trim()),
							cmbCoName.getSelectedItem() + ""));
		} else {
			BuyerService.getInstance().insertItem(new Buyer(Integer.parseInt(regTf[0].getText().trim()),
					regTf[1].getText().trim(), regTf[2].getText().trim(), regTf[3].getText().trim()));
		}
		JOptionPane.showMessageDialog(null, msg);

	}
}
