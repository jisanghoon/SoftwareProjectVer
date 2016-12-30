package kr.or.dgit.bigdata.swmng.customersubmenu;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.service.BuyerService;
import kr.or.dgit.bigdata.swmng.service.CompanyService;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;

public class ListPanel extends JPanel {
	private JTable companyList = new JTable();
	private int idx;
	String[][] data;

	public ListPanel() {
	}

	public void CompanyListPanel(String e) {
		if (e.equals("공급회사 목록")) {
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
			ModelForTable mft = new ModelForTable(data, COL_NAMES);
			companyList.setModel(mft);
		} else if (e.equals("소프트웨어 목록")) {
			List<Software> list = SoftwareService.getInstance().selectAll();
			String[] COL_NAMES = { "품목번호", "분류명", "품목명", "공급가격", "판매가격", "공급회사명" };
			String[][] data = new String[list.size()][COL_NAMES.length];
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
			ModelForTable mft = new ModelForTable(data, COL_NAMES);
			companyList.setModel(mft);
		} else {
			List<Buyer> list = BuyerService.getInstance().selectAll();
			String[] COL_NAMES = { "등록번호", "상호", "주소", "전화번호" };
			String[][] data = new String[list.size()][COL_NAMES.length];
			idx = 0;
			for (Buyer b : list) {
				data[idx][0] = b.getNo() + "";
				data[idx][1] = b.getShopName();
				data[idx][2] = b.getAddress();
				data[idx][3] = b.getTel();
				idx++;
			}
			ModelForTable mft = new ModelForTable(data, COL_NAMES);
			companyList.setModel(mft);
		}
		companyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(companyList));
	}

}