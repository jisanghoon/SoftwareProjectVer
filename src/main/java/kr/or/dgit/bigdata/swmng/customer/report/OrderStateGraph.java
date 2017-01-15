package kr.or.dgit.bigdata.swmng.customer.report;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import kr.or.dgit.bigdata.swmng.service.SaleService;

@SuppressWarnings("serial")
public class OrderStateGraph extends JPanel {

	private static List<Map<String, Object>> listmapForbc;
	private static List<Map<String, Object>> listmapForPie;

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
		fxPanel.setBackground(new java.awt.Color(255, 255, 255));
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
		AnchorPane anchorPane = new AnchorPane();

		Scene scene = new Scene(anchorPane, Color.BEIGE);
		HBox box = new HBox();

		yAxis = new NumberAxis();
		xAxis = new CategoryAxis();
		bc = new BarChart<String, Number>(xAxis, yAxis);
		PieChart pie = new PieChart();
		box.getChildren().addAll(bc, pie);
		anchorPane.getChildren().addAll(box);

		bc.setTitle("주문현황");
		yAxis.setLabel("주문수량");
		xAxis.setLabel("상호명");

		listmapForbc = SaleService.getInstance().selectAllGroupByConame();
		listmapForPie = SaleService.getInstance().selectSalesOfEach();

		XYChart.Series series1 = new XYChart.Series();
		for (int i = 0; i < listmapForbc.size(); i++) {
			Map<String, Object> tempMap = listmapForbc.get(i);
			series1.getData().add(new XYChart.Data(tempMap.get("shopName"), tempMap.get("totalCnt")));
		}
		bc.getData().addAll(series1);
		
		BigInteger result = BigInteger.valueOf(0);
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		for (int i = 0; i < listmapForPie.size(); i++) {
			Map<String, Object> tempMap = listmapForPie.get(i);
			BigDecimal bigdecimal = (BigDecimal) tempMap.get("result");
			result = result.add(bigdecimal.toBigInteger());
		}
		for (int i = 0; i < listmapForPie.size(); i++) {
			Map<String, Object> tempMap = listmapForPie.get(i);
			BigDecimal bigdecimal=(BigDecimal)tempMap.get("result");
			
			//pieChartData.add(new PieChart.Data((String) tempMap.get("shopName"),0);
		}
		
		// pieChartData.add(new PieChart.Data("Grapefruit", 25));
		pieChartData.forEach(
				data -> data.nameProperty().bind(Bindings.concat(data.getName(), " ", data.pieValueProperty(), " %")));

		pie.setData(pieChartData);
		pie.setTitle("주문현황");

		tableDesignSetting();
		fxPanel.setScene(scene);
	}

	private static void tableDesignSetting() {
		for (int i = 0; i < listmapForbc.size(); i++) {
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
