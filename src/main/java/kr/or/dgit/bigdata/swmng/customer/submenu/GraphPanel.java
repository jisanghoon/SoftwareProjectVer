package kr.or.dgit.bigdata.swmng.customer.submenu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import kr.or.dgit.bigdata.swmng.service.SaleService;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class GraphPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public GraphPanel() {

		setLayout(new BorderLayout(0, 0));
		JFXPanel fxPanel = new JFXPanel();
		fxPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(fxPanel, BorderLayout.CENTER);

		JPanel pnBtn = new JPanel();

		add(pnBtn, BorderLayout.NORTH);
		pnBtn.setLayout(new BorderLayout(0, 0));

		JButton btn = new JButton("닫기");
		pnBtn.add(btn, BorderLayout.EAST);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				JLabel lblMainTitle = new JLabel(new ImageIcon("src/img/logo.gif"));
				lblMainTitle.setFont(new Font("굴림", Font.PLAIN, 20));
				lblMainTitle.setHorizontalAlignment(SwingConstants.CENTER);

				JPanel contentPane = (JPanel) GraphPanel.this.getParent();
				contentPane.add(lblMainTitle, BorderLayout.CENTER);
				JFrame mainframe = (JFrame) contentPane.getParent().getParent().getParent();
				mainframe.setSize( 600, 310);

			}
		});
		Platform.runLater(new Runnable() {
	
			public void run() {
				initFX(fxPanel);
			}
		});
	}

	private static void initFX(JFXPanel fxPanel) {
		// This method is invoked on the JavaFX thread
		Group root = new Group();
		Scene scene = new Scene(root, Color.TRANSPARENT);

		NumberAxis yAxis = new NumberAxis();
		CategoryAxis xAxis = new CategoryAxis();
		BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("주문현황");
		yAxis.setLabel("주문수량");
		xAxis.setLabel("상호명");

		XYChart.Series series1 = new XYChart.Series();
		bc.setLegendVisible(false);
		// xAxis.setTickMarkVisible(true);
		xAxis.setTickLabelRotation(360);

		List<Map<String, Object>> listmap = SaleService.getInstance().selectAllGroupByConame();

		for (int i = 0; i < listmap.size(); i++) {
			Map<String, Object> tempMap = listmap.get(i);
			series1.getData().add(new XYChart.Data(tempMap.get("shopName"), tempMap.get("totalCnt")));
		}

		root.getChildren().add(bc);
		bc.getData().addAll(series1);

		fxPanel.setScene(scene);
	}
}
