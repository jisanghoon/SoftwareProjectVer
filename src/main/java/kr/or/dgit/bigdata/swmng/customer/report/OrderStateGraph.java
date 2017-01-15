package kr.or.dgit.bigdata.swmng.customer.report;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import kr.or.dgit.bigdata.swmng.service.SaleService;
import kr.or.dgit.bigdata.swmng.util.CreateDatabase;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class OrderStateGraph extends JPanel {

	private static List<Map<String, Object>> listmap;
	private static BarChart<String, Number> bc;
	private static NumberAxis yAxis;
	private static CategoryAxis xAxis;

	/**
	 * Create the panel.
	 */
	public OrderStateGraph() {
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(new BorderLayout(0, 0));
		JFXPanel fxPanel = new JFXPanel();
		fxPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
		add(fxPanel, BorderLayout.CENTER);
		JPanel pnBtn = new JPanel();
		add(pnBtn, BorderLayout.NORTH);
		pnBtn.setLayout(new BorderLayout(0, 0));

		Platform.runLater(new Runnable() {
			public void run() {
				initFX(fxPanel);
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void initFX(JFXPanel fxPanel) {
		// This method is invoked on the JavaFX thread
		Group root = new Group();
		Scene scene = new Scene(root, Color.TRANSPARENT);

		yAxis = new NumberAxis();
		xAxis = new CategoryAxis();
		bc = new BarChart<String, Number>(xAxis, yAxis);

		bc.setTitle("주문현황");
		yAxis.setLabel("주문수량");
		xAxis.setLabel("상호명");

		XYChart.Series series1 = new XYChart.Series();
		try {
			listmap = SaleService.getInstance().selectAllGroupByConame();
		} catch (Exception e) {
			if (JOptionPane.showConfirmDialog(null, "데이터베이스가 없습니다. 초기화 하시겠습니까?") == 0) {
				new CreateDatabase().resetDB();
				listmap = SaleService.getInstance().selectAllGroupByConame();
			} else {
				System.exit(0);
			}
		}

		for (int i = 0; i < listmap.size(); i++) {
			Map<String, Object> tempMap = listmap.get(i);
			series1.getData().add(new XYChart.Data(tempMap.get("shopName"), tempMap.get("totalCnt")));
		}
		root.getChildren().add(bc);
		bc.getData().addAll(series1);

		tableDesignSetting();
		fxPanel.setScene(scene);
	}

	private static void tableDesignSetting() {
		for (int i = 0; i < listmap.size(); i++) {
			bc.lookup(".data" + i + ".chart-bar").setStyle("-fx-background-color:#FF0051");
		}
		bc.lookup(".chart-content").setStyle("-fx-padding: 10");
		bc.lookup(".chart-title").setStyle("-fx-font-size: 1.8em");
		bc.setPrefSize(520, 390);
		xAxis.lookup(".axis-label").setStyle("-fx-label-padding:15 0 10 0");

		bc.setBarGap(0);
		bc.setCategoryGap(40);
		xAxis.setTickLength(5);
		xAxis.setTickLabelGap(5);
		bc.setLegendVisible(false);
		xAxis.setTickLabelRotation(360);

	}

}
