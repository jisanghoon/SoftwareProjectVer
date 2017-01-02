package kr.or.dgit.bigdata.swmng.customersubmenu;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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

public class GraphPanelTest extends JFrame{
    
    
    public GraphPanelTest() throws HeadlessException {
    	
    
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        JPanel pnGraph = new JPanel();
		add(pnGraph);

        JFXPanel fxPanel = new JFXPanel();
        pnGraph.add(fxPanel,BorderLayout.CENTER);
        
        
		JPanel pnBtn = new JPanel();
		JButton btn =new JButton("확인");
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
			}
		});
		pnBtn.add(btn);
		
		
		add(pnBtn,BorderLayout.SOUTH);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });
	}


	private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
    	 Group  root  =  new  Group();
    	 Scene  scene  =  new  Scene(root, Color.WHITE);
         
    	 NumberAxis yAxis = new NumberAxis();
         CategoryAxis xAxis = new CategoryAxis();
         BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
         
         bc.setTitle("주문현황");
         yAxis.setLabel("주문수량");
        XYChart.Series series1 = new XYChart.Series();
        
        
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


    