package kr.or.dgit.bigdata.swmng.customersubmenu;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableColumnModel;

import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.service.BuyerService;
import kr.or.dgit.bigdata.swmng.service.CompanyService;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;
import sun.swing.table.DefaultTableCellHeaderRenderer;

@SuppressWarnings("serial")
public class ListPanel extends JPanel {
	private static JTable companyList = new JTable();
	private int idx;
	String[][] data;
	private static String title;
	private JLabel listTitle = new JLabel();
	private ModelForTable mft;

	public static String getTitle() {
		return title;
	}

	public ListPanel() {
		try {

			UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createList(String e) {

		add(listTitle);
		listTitle.setText(e);
		if (e.equals("공급회사 목록")) {
			title = "공급회사";
			List<Company> list = CompanyService.getInstance().selectAll();
			String[] COL_NAMES = { "회사번호", "회사명", "주소", "전화번호" };
			data = new String[list.size()][COL_NAMES.length];
			idx = 0;
			for (Company c : list) {
				data[idx][0] = c.getNo() + "";
				data[idx][1] = c.getCoName();
				data[idx][2] = c.getAddress();
				data[idx][3] = c.getTel();
				idx++;
			}
			mft = new ModelForTable(data, COL_NAMES);
			companyList.setModel(mft);
			mft.tableCellAlignment(companyList, SwingConstants.CENTER, 0, 3);
		} else if (e.equals("소프트웨어 목록")) {
			title = "소프트웨어";
			List<Software> list = SoftwareService.getInstance().selectAll();
			String[] COL_NAMES = { "품목번호", "분류명", "품목명", "공급가격", "판매가격", "공급회사명" };
			data = new String[list.size()][COL_NAMES.length];
			idx = 0;
			for (Software s : list) {
				data[idx][0] = s.getNo() + "";
				data[idx][1] = s.getCategory();
				data[idx][2] = s.getTitle();
				data[idx][3] = s.getSupPrice() + "";
				data[idx][4] = s.getSellPrice() + "";
				data[idx][5] = s.getCoName().getCoName();
				idx++;
			}
			mft = new ModelForTable(data, COL_NAMES);
			companyList.setModel(mft);
			mft.tableCellAlignment(companyList, SwingConstants.CENTER, 0, 1,5);
			mft.tableCellAlignment(companyList, SwingConstants.RIGHT, 3, 4);
		} else {
			title = "고객";
			List<Buyer> list = BuyerService.getInstance().selectAll();
			String[] COL_NAMES = { "등록번호", "상호", "주소", "전화번호" };
			data = new String[list.size()][COL_NAMES.length];
			idx = 0;
			for (Buyer b : list) {
				data[idx][0] = b.getNo() + "";
				data[idx][1] = b.getShopName();
				data[idx][2] = b.getAddress();
				data[idx][3] = b.getTel();
				idx++;
			}
			mft = new ModelForTable(data, COL_NAMES);
			companyList.setModel(mft);
			mft.tableCellAlignment(companyList, SwingConstants.CENTER, 0, 3);

		}
		companyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mft.resizeColumnWidth(companyList);
		add(new JScrollPane(companyList));
	}

	public void deleteAction() {
		if (companyList.getSelectedRowCount() == 1) {
			if (JOptionPane.showConfirmDialog(null, "정말 삭제 하시겠습니까?") == 0) {
				switch (title) {
				case "공급회사":
					CompanyService.getInstance().deleteItem(
							Integer.parseInt((companyList.getValueAt(companyList.getSelectedRow(), 0) + "")));
					break;
				case "소프트웨어":
					SoftwareService.getInstance().deleteItem(
							Integer.parseInt((companyList.getValueAt(companyList.getSelectedRow(), 0) + "")));
					break;
				case "고객":
					BuyerService.getInstance().deleteItem(
							Integer.parseInt((companyList.getValueAt(companyList.getSelectedRow(), 0) + "")));
					break;
				}
				JOptionPane.showMessageDialog(null, "삭제되었습니다");
			} else {
				JOptionPane.showMessageDialog(null, "취소하였습니다");
			}
		} else {
			JOptionPane.showMessageDialog(null, "삭제 할 항목을 선택해 주세요");
		}
	}

	public void updateAction() {
		RegisterEditPanel rp = new RegisterEditPanel();
		rp.setBtnOk("수정");
		if (companyList.getSelectedRowCount() == 1) {
			rp.createUpdatePanel(title,
					Integer.parseInt((companyList.getValueAt(companyList.getSelectedRow(), 0) + "")));
		} else {
			JOptionPane.showMessageDialog(null, "수정 할 항목을 선택해 주세요");
		}

	}
}
