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

@SuppressWarnings("serial")
public class GraphPanel extends JPanel {

	private static List<Map<String, Object>> listmap;
	private static BarChart<String, Number> bc;
	private static NumberAxis yAxis;
	private static CategoryAxis xAxis;

	/**
	 * Create the panel.
	 */
	public GraphPanel() {
		setLayout(new BorderLayout(0, 0));
		JFXPanel fxPanel = new JFXPanel();
		fxPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(fxPanel, BorderLayout.CENTER);
		/*fxPanel.setBackground(java.awt.Color.BLACK);*/
		
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

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void initFX(JFXPanel fxPanel) {
		// This method is invoked on the JavaFX thread
		Group root = new Group();
		Scene scene = new Scene(root,Color.TRANSPARENT);
		
		yAxis = new NumberAxis();
		xAxis = new CategoryAxis();
		bc = new BarChart<String, Number>(xAxis, yAxis);

		bc.setTitle("주문현황");
		yAxis.setLabel("주문수량");
		xAxis.setLabel("상호명");
		
	
		XYChart.Series series1 = new XYChart.Series();
		listmap = SaleService.getInstance().selectAllGroupByConame();
		
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
			bc.lookup(".data"+i+".chart-bar").setStyle("-fx-background-color:#FF0051");
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
		//bc.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
		/*bc.lookup(".chart-alternative-row-fill").setStyle(" -fx-fill: #99bcfd; -fx-stroke-width: 5");*/
		/*bc.lookup(".axis").setStyle("-fx-font-size: 1.2em; -fx-tick-label-fill: red; ");
		bc.setStyle("-fx-border-color:black");*/
	}
	
}
