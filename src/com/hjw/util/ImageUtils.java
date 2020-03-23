package com.hjw.util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;


public class ImageUtils {

	public static String createImage(int width, int height, List<SummaryImage> list, String filename, String title){
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			if (list.size() > 0) {
				for (SummaryImage sum : list) {
					dataset.addValue(sum.getValue(), sum.getName(), "");
				}
				StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
				standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 60));
				// 设置图例的字体
				standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 35));
				// 设置轴向的字体
				standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 35));
				// 应用主题样式
				ChartFactory.setChartTheme(standardChartTheme);
				JFreeChart barChart = ChartFactory.createBarChart(title, "", "", dataset, PlotOrientation.VERTICAL,
						true, false, false);
				// 参数 1：表图标题 2：目录轴的显示标签 3：数值轴的显示标签 4：柱子显示的参数 5：图表的方向（垂直/水平） 6：
				// 是否显示图例(对于简单的柱状图必须是false)7：是否生成工具 8：是否生成URL链接
				CategoryPlot plot = barChart.getCategoryPlot();
				BarRenderer render = (BarRenderer) plot.getRenderer();
				Color co = new Color(220, 220, 220);// 设置背景颜色
				plot.setBackgroundPaint(co);
				render.setMaximumBarWidth(0.06);// 设置柱子最大宽度

				// 把数字写到柱状图上 设置大小格式
				render.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
				render.setBaseItemLabelsVisible(true);
				render.setItemLabelFont(new Font("宋书", Font.PLAIN, 20));

				plot.setRenderer(render);

				File BarChart = new File(filename);
				File fileParent = BarChart.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				BarChart.createNewFile();

				ChartUtilities.saveChartAsJPEG(BarChart, barChart, width, height);
				return "ok-成功";
			} else {
				return "error-list无效";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "error-操作错误";
		}
	}

	public static void main(String[] args) throws Exception {
		List<SummaryImage> list = new ArrayList<>();
		list.add(new SummaryImage("平均患病率", 22.3));
		list.add(new SummaryImage("实际患病率", 57d));
		// list.add(new SummaryImage("1前年", 8855d));
		// list.add(new SummaryImage("1去年", 4562d));
		// list.add(new SummaryImage("2前年", 8855d));
		// list.add(new SummaryImage("2去年", 4562d));
		// list.add(new SummaryImage("3前年", 98555d));
		// list.add(new SummaryImage("3去年", 7562d));

		String filename = "d:/32/test3d.jpg";
		String title = "十年卒中风险评估对比图";
		String str = ImageUtils.createImage(1500, 900, list, filename, title);
		System.out.println(str);
	}
}
