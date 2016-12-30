package kr.or.dgit.bigdata.swmng.customersubmenu;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.main.SwMngMain;
import kr.or.dgit.bigdata.swmng.service.CompanyService;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterPanel extends JFrame implements ActionListener {
	private JLabel[] regForm = new JLabel[4];
	private JTextField[] regTf = new JTextField[4];
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblTItle;
	private JPanel confirmPanel;
	private JButton btnOk;
	private JButton btnCancel;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 400, 445, 168);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 4, 0, 0));

		lblTItle = new JLabel("공급회사 등록");
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
		createRegisterPanel();
	}

	private void createRegisterPanel() {
		String[] addCo = { "회사번호", "회 사 명", "주 소", "전화번호" };
		for (int i = 0; i < regForm.length; i++) {
			regForm[i] = new JLabel(addCo[i]);
			regTf[i] = new JTextField();
			panel.add(regForm[i]);
			panel.add(regTf[i]);
		}
		regTf[0].setEditable(false);
		regTf[0].setText(CompanyService.getInstance().selectMaxNo().getNo() + "");

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {
			actionPerformedBtnOk(e);
		}
		if(e.getSource()==btnCancel){
			setVisible(false);
		
		}
	}

	protected void actionPerformedBtnOk(ActionEvent e) {
		if (regTf[1].getText().trim().equals("") || regTf[2].getText().trim().equals("")
				|| regTf[3].getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "값을 입력하세요");
		} else {
			CompanyService.getInstance().insertItem(
					new Company(regTf[1].getText().trim(), regTf[2].getText().trim(), regTf[3].getText().trim()));
			JOptionPane.showMessageDialog(null, "등록이 완료되었습니다");
		
		}

	}
}
