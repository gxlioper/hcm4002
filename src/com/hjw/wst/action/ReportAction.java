package com.hjw.wst.action;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Rotation;

import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebXtgncdDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.MenuGnService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.web.action.BaseAction;
/**
 * 
     * @Title:  圈存查询一体机系统3.0   
     * @Package com.synjones.wst.action   
     * @Description:    报表测试 
     * @author: yujia     
     * @date:   2015-8-10 上午8:47:19   
     * @version V3.0.0.0
 */
public class ReportAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private int page = 1;
	private int rp = 10;
	private JFreeChart chart;
	private List<WebXtgncdDTO> reportls = new ArrayList<WebXtgncdDTO>();
    private CustomerInfoService customerInfoService;
	private MenuGnService menuGnService;
	private SyslogService syslogService;
    private String father_id;
    
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public String getFather_id() {
		return father_id;
	}

	public void setFather_id(String father_id) {
		this.father_id = father_id;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setMenuGnService(MenuGnService menuGnService) {
		this.menuGnService = menuGnService;
	}

	public JFreeChart getChart() {
		return chart;
	}
	public void setChart(JFreeChart chart)
	{
		this.chart = chart;
	}
	public int getPage()
	{
		return page;
	}
	public void setPage(int page)
	{
		this.page = page;
	}
	public int getRp()
	{
		return rp;
	}
	public void setRp(int rp)
	{
		this.rp = rp;
	}
	
	public List<WebXtgncdDTO> getReportls() {
		return reportls;
	}
	public void setReportls(List<WebXtgncdDTO> reportls) {
		this.reportls = reportls;
	}
	
	/**
	 * 
	     * @Title: reportServermain   
	     * @Description: 13
	     * @param: @return
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	 public String reportServermain() throws Exception{
		List<WebXtgncdDTO> ls = new ArrayList<WebXtgncdDTO>();
		UserDTO user = (UserDTO) session.get("username");
		String sPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
		+ request.getServletPath().substring(0, request.getServletPath().lastIndexOf("/") + 1);
		ls = this.menuGnService.findWebReportList(user.getRolestr(), user.getReportid(), sPath);
		int i=0;
		for (WebXtgncdDTO wx:ls) {
            if((wx.getFather_id()==null)||("null".equals(wx.getFather_id()))||("0".equals(wx.getFather_id()))){
            	int ico=50+i;
            	wx.setOther_url("reportonemain.action?father_id="+wx.getId());
            	wx.setIcon_url("icon-b ico-b-"+ico);
            	i++;
            	reportls.add(wx);			
            }
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("6");//0 查询，1插入 2修改 3 删除 4导入 5 登录 6 报表查询  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("13");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	 
	 /**
	  * 
	      * @Title: reportonemain   
	      * @Description: 499   
	      * @param: @return
	      * @param: @throws Exception      
	      * @return: String      
	      * @throws
	  */
	 public String reportonemain() throws Exception{
			List<WebXtgncdDTO> ls = new ArrayList<WebXtgncdDTO>();
			UserDTO user = (UserDTO) session.get("username");
			String sPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
			+ request.getServletPath().substring(0, request.getServletPath().lastIndexOf("/") + 1);
			ls = this.menuGnService.findWebReportList(user.getRolestr(), user.getReportid(), sPath);
			String FINAREPORT_ULR="";
			try{
				FINAREPORT_ULR = this.customerInfoService.getCenterconfigByKey("FINAREPORT_ULR", user.getCenter_num()).getConfig_value().trim();
			}catch(Exception ex){}
			int i=0;
			for (WebXtgncdDTO wx:ls) {	          
	            if(this.father_id.equals(wx.getFather_id()))
				{
	            	int ico=40+i;
				String otherurl=FINAREPORT_ULR+"ReportServer?reportlet="+wx.getOther_url()+"&userId="+user.getUserid()+"&op=view";
				wx.setOther_url(otherurl);
            	wx.setIcon_url("icon-b ico-b-"+ico);
            	i++;
            	reportls.add(wx);	
				}
			}
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());		
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("6");//0 查询，1插入 2修改 3 删除 4导入 5 报表查询  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("499");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return SUCCESS;
		}
	/**
	 * @throws Exception 
	 * 
	     * @Title: zhuzhuangtu   
	     * @Description: 柱状图测试
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
    public String zhuzhuangtu() throws Exception
    {
       String fileName = getZhuZhuangTu(request.getSession());
       request.setAttribute("fileName", fileName);
       outJsonStrResult(fileName);
	   return null;
    }
	/**
	 * @throws Exception 
	 * 
	     * @Title: zhuzhuangtu   
	     * @Description: 饼状图测试
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
    public String bingzhuangtu() throws Exception
    {
    	String fileName =  getBingZhuangTu(request.getSession());
    	request.setAttribute("fileName", fileName);
        outJsonStrResult(fileName);
 	   	return null;
    }
	/**
	 * @throws Exception 
	 * 
	     * @Title: zhuzhuangtu   
	     * @Description: 折线图测试
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
    public String zhexiantu() throws Exception
    {
    	String fileName =  getZheXianTu(request.getSession());
    	request.setAttribute("fileName", fileName);
        outJsonStrResult(fileName);
 	   	return null;
    }
    public String list()
    {
    	return "list";
    }
	private String getZheXianTu(HttpSession session) throws Exception
	{
		// 访问量统计
		TimeSeries timeSeries=new TimeSeries("tmall", Year.class);
		TimeSeries timeSeries1=new TimeSeries("taobao", Year.class);
		// 添加数据
		//timeSeries.add(new Month(1,2013), 100);
		//timeSeries.add(new Month(2,2013), 200);
		timeSeries.add(new Year(2008),430);
		timeSeries.add(new Year(2009),380);
		timeSeries.add(new Year(2010),250);
		timeSeries.add(new Year(2011),400);
		timeSeries.add(new Year(2012),600);
		timeSeries.add(new Year(2013),759);
		timeSeries.add(new Year(2014),920);
		
		timeSeries1.add(new Year(2008),100);
		timeSeries1.add(new Year(2009),150);
		timeSeries1.add(new Year(2010),220);
		timeSeries1.add(new Year(2011),380);
		timeSeries1.add(new Year(2012),790);
		timeSeries1.add(new Year(2013),930);
		timeSeries1.add(new Year(2014),1100);
		// 定义时间序列的集合
		TimeSeriesCollection lineDataset=new TimeSeriesCollection();
		lineDataset.addSeries(timeSeries);
		lineDataset.addSeries(timeSeries1);
		
		chart=ChartFactory.createTimeSeriesChart("访问量统计时间折线图", "年份", "销售金额(单位:万)", lineDataset, true, true, true);
		
		//设置主标题
		chart.setTitle(new TextTitle("网站销售统计对比", new Font("隶书", Font.ITALIC, 15))); 
		//设置子标题
		TextTitle subtitle = new TextTitle("2008-2014年", new Font("黑体", Font.BOLD, 12));
		chart.addSubtitle(subtitle); 
		chart.setAntiAlias(true); 
		
		//设置时间轴的范围。
		XYPlot plot = (XYPlot) chart.getPlot(); 
		DateAxis dateaxis = (DateAxis)plot.getDomainAxis();
		dateaxis.setDateFormatOverride(new java.text.SimpleDateFormat("yyyy年"));
		dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.YEAR,1)); 
		
		//设置曲线是否显示数据点
		XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer)plot.getRenderer();
		xylinerenderer.setBaseShapesVisible(true); 
		
		//设置曲线显示各数据点的值
		XYItemRenderer xyitem = plot.getRenderer(); 
		xyitem.setBaseItemLabelsVisible(true);
		xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER)); 
		xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 12)); 
		plot.setRenderer(xyitem);
		
		
		String fileName=ServletUtilities.saveChartAsPNG(chart, 700, 500, null, session);
		
		return fileName;
		
	}
	/**
	 * 
	     * @Title: getZhuZhuangTu   
	     * @Description: 生成饼状图
	     * @param: @param session
	     * @param: @return
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	private String getBingZhuangTu(HttpSession session) throws Exception
	{
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("华中", 990);
		dataset.setValue("华北", 800);
		dataset.setValue("西北", 400);
		dataset.setValue("东北", 300);
		dataset.setValue("西南", 100);
		dataset.setValue("华南", 100);
		dataset.setValue("华东", 100);

		chart=ChartFactory.createPieChart3D("人数分布图", dataset, true, true, true);
		
		// 副标题
		chart.addSubtitle(new TextTitle("2015年度"));
		
		PiePlot pieplot=(PiePlot)chart.getPlot();
		pieplot.setLabelFont(new Font("宋体",0,11));
		// 设置饼图是圆的（true），还是椭圆的（false）；默认为true  
		pieplot.setCircular(true);
		// 没有数据的时候显示的内容
		pieplot.setNoDataMessage("无数据显示");
		//获得StandardPieSectionLabelGenerator对象,生成的格式，{0}表示section名，{1}表示section的值，{2}表示百分比。可以自定义  
		StandardPieSectionLabelGenerator standarPieIG = new StandardPieSectionLabelGenerator("{0}:{1}({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.0%"));  
		//设置饼图显示百分比  
		pieplot.setLabelGenerator(standarPieIG);  
		//设置突出显示(没有效果)
		//pieplot.setExplodePercent("华北", 0.29);
		
		PiePlot3D pieplot3d = (PiePlot3D)chart.getPlot(); 
		
		//设置开始角度  
		pieplot3d.setStartAngle(120D);  
		//设置方向为”顺时针方向“  
		pieplot3d.setDirection(Rotation.CLOCKWISE);  
		//设置透明度，0.5F为半透明，1为不透明，0为全透明  
		pieplot3d.setForegroundAlpha(0.6F); 
		
		String fileName=ServletUtilities.saveChartAsPNG(chart, 700, 500, null, session);
		
		return fileName;
	}
	/**
	 * 
	     * @Title: getZhuZhuangTu   
	     * @Description: 生成柱状图
	     * @param: @param session
	     * @param: @return
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
    private String getZhuZhuangTu(HttpSession session) throws Exception
    {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(906, "apple", "BeiJing");
		dataset.addValue(378, "huawei", "BeiJing");
		dataset.addValue(456, "lenovo", "BeiJing");
		
		dataset.addValue(889, "apple", "NewYork");
		dataset.addValue(345, "huawei", "NewYork");
		dataset.addValue(286, "lenovo", "NewYork");
		
		dataset.addValue(695, "apple", "Paris");
		dataset.addValue(596, "huawei", "Paris");
		dataset.addValue(620, "lenovo", "Paris");
		
    	chart=ChartFactory.createBarChart3D("销售统计表", "phone", "sale", dataset,PlotOrientation.VERTICAL, true, true, true);
		
		CategoryPlot plot=chart.getCategoryPlot();
		// 设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);
		// 设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		
		// 显示每个柱的数值，并修改该数值的字体属性
		BarRenderer3D renderer=new BarRenderer3D();
		//设置每个系列柱子的颜色
		renderer.setSeriesPaint(0, Color.decode("#7979FF"));
		renderer.setSeriesPaint(1, Color.decode("#00BF00"));
		renderer.setSeriesPaint(2, Color.decode("#FF5555"));
		
		
		
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		//renderer.setBaseItemLabelsVisible(true, false);
		
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);  
		
		// 设置平行柱的之间距离
		renderer.setItemMargin(0.3);
		
		plot.setRenderer(renderer);
		
		String fileName=ServletUtilities.saveChartAsPNG(chart, 700, 500, null,session);
		return fileName;
    }
}