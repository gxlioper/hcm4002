package com.hjw.wst.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import com.synjones.framework.web.action.BaseAction;

/*
 * Title:圈存查询系统
 * Description: 新中新集团
 * 创建日期：Jul 18, 2006
 * 项目名称：screenObject
 * 所在包名：com.synjones.touchscreen.web.action
 *
 * Copyright: Copyright (c) 2005
 * Company: synjones
 * author yangm
 * version 2.0
 */

public class GetBar3PicAction extends BaseAction {

    private static final long serialVersionUID = -4395254787469394208L;

    private String strQty;

    private String strDay;

    private String inputObjectname;

    private static DefaultCategoryDataset data = new DefaultCategoryDataset();

    /**
     * @return Returns the strDay.
     */
    public String getStrDay() {
        return strDay;
    }

    /**
     * @param strDay
     *            The strDay to set.
     */
    public void setStrDay(String strDay) {
        this.strDay = strDay;
    }

    /**
     * @return Returns the strQty.
     */
    public String getStrQty() {
        return strQty;
    }

    /**
     * @param strQty
     *            The strQty to set.
     */
    public void setStrQty(String strQty) {
        this.strQty = strQty;
    }

    private void genData() {
        if ((this.strQty != null) && (!this.strQty.equals(""))) {
            String[] strQ = this.strQty.split(":");
            String[] strD = this.strDay.split(":");
            data.clear();
            for (int i = 0; i < strQ.length; i++) {
                // 生成统计图数据
                data.addValue(Double.valueOf(strQ[i]), strD[i], "");
            }
        }
    }

    /**
     * 统计图框架
     * 
     * @param dataset
     * @param sGroupBy
     */
    @SuppressWarnings("deprecation")
	public String getBar3pic() {
        genData();
        String name = "用卡情况统计图";
        if ((this.inputObjectname != null)
                && (!this.inputObjectname.equals(""))) {
            name = this.inputObjectname + "情况统计图";
        }
        JFreeChart jfreechart = ChartFactory.createBarChart3D(name, "月份", "金额",
                data, PlotOrientation.VERTICAL, true, true, false);
        jfreechart.setBackgroundPaint(new Color(0xbbbbdd));
        CategoryPlot categoryplot = jfreechart.getCategoryPlot();
        CategoryAxis domainAxis = categoryplot.getDomainAxis();
        // domainAxis.set.setVerticalCategoryLabels(false);
        domainAxis.setLowerMargin(0.1);
        domainAxis.setUpperMargin(0.1);
        // domainAxis.setSkipCategoryLabelsToFit(true);
        categoryplot.setDomainAxis(domainAxis);
        BarRenderer3D renderer3d = new BarRenderer3D();
        renderer3d.setBaseOutlinePaint(Color.BLACK);

        // 设置每个平行柱之间的距离
        renderer3d.setItemMargin(0.1);

        // 设置每种柱的颜色
        renderer3d.setSeriesPaint(0, new Color(0, 0, 255));
        renderer3d.setSeriesPaint(1, Color.GREEN);
        // 设置每种柱的 Outline 颜色
        renderer3d.setSeriesOutlinePaint(0, Color.BLACK);
        renderer3d.setSeriesOutlinePaint(1, Color.BLACK);

        // 显示每个柱的数值，并修改该数值的字体属性
        renderer3d
                .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

        ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.CENTER,
                TextAnchor.BASELINE_CENTER);
        renderer3d.setPositiveItemLabelPositionFallback(p);

        renderer3d.setItemLabelFont(new Font("黑体", Font.PLAIN, 12));
        renderer3d.setItemLabelsVisible(true);

        categoryplot.setRenderer(renderer3d);

        // 设置柱的透明度
        categoryplot.setForegroundAlpha(0.6f);

        try {
            ChartUtilities.writeChartAsPNG(getout(), jfreechart, 700, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return NONE;
    }
    
    
    /**
     * 生成随机图
     * 
     * @param dataset
     * @param sGroupBy
     */
    @SuppressWarnings("unchecked")
	public String getCheckpic() {
//      在内存中创建图象
        int iWidth=45,iHeight=18;
        BufferedImage image=new BufferedImage(iWidth,iHeight,BufferedImage.TYPE_INT_RGB);
        //获取图形上下文
        Graphics g=image.getGraphics();
        //设定背景色
        g.setColor(Color.white);
        g.fillRect(0,0,iWidth,iHeight);
        //画边框
        g.setColor(Color.black);
        g.drawRect(0,0,iWidth-1,iHeight-1);
        //取随机产生的认证码(4位数字)
        String rand = Math.random()*10000+"";
        rand = rand.substring(0, rand.indexOf("."));
        switch(rand.length())
        {
         case 1:rand="000"+rand;break;
         case 2:rand="00"+rand;break;
         case 3:rand="0"+rand;break;
         default:rand=rand.substring(0,4);break;
        }
        //将认证码存入SESSION
        session.put("Rand",rand);
        //将认证码显示到图象中
        g.setColor(Color.black);
        g.setFont(new Font("Times New Roman",Font.PLAIN,18));
        g.drawString(rand,2,15);
        //随机产生88个干扰点,使图象中的认证码不易被其它程序探测到
        Random random=new Random();
        for(int iIndex=0;iIndex<88;iIndex++)
        {
         int x=random.nextInt(iWidth);
         int y=random.nextInt(iHeight);
         g.drawLine(x,y,x,y);
        }
        //图象生效
        g.dispose();
        //输出图象到页面
        //ImageIO.write(image,"JPEG",response.getOutputStream());
        try {
//          输出图象到页面
            ImageIO.write(image,"JPEG",getout());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String getRandomValidateCode(){
    	Random random=new Random();
    	String[] opeStrList = {"+", "-"};
    	Map map=new HashMap();
    	map.put(0, "0");
    	map.put(1, "1");
    	map.put(2, "2");
    	map.put(3, "3");
    	map.put(4, "4");
    	map.put(5, "5");
    	map.put(6, "6");
    	map.put(7, "7");
    	map.put(8, "8");
    	map.put(9, "9");
    	map.put(10, "零");
    	map.put(11, "壹");
    	map.put(12, "貳");
    	map.put(13, "叁");
    	map.put(14, "肆");
    	map.put(15, "伍");
    	map.put(16, "陸");
    	map.put(17, "柒");
    	map.put(18, "捌");
    	map.put(19, "玖");
    	int width=66;
    	int height=20;
    	BufferedImage image=new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR);
    	Graphics gd=image.getGraphics();
    	gd.fillRect(0, 0, width, height);
    
    	// 绘制干扰线
		gd.setColor(Color.BLACK);
        for(int iIndex=0;iIndex<88;iIndex++)
        {
         int x=random.nextInt(width);
         int y=random.nextInt(height);
         gd.drawLine(x,y,x,y);
        }
    	//绘制随机字符
        String rand="";
        gd.setFont(new Font("Fixedsys", Font.CENTER_BASELINE, 14));
       gd.setColor(Color.BLACK);
       //随机第一个运算数的位置
       int first=random.nextInt(20);
       //根据位置得第一个运算数
       String firstStr=(String) map.get(first);
       //随机运算符
       String operate=opeStrList[random.nextInt(opeStrList.length)];
       //随机第二个运算数的位置
       int seconde=random.nextInt(20);
       //根据位置得第二个运算数
       String secodeStr=(String) map.get(seconde);
       rand=firstStr+" "+operate+" "+secodeStr+"=?";
      	gd.drawString(rand,2, 16);
      	//计算结果
      	int rest=getResult(first, seconde, operate);
//      将认证码存入SESSION
      	session.remove("Rand");
      	session.remove("rand");
        session.put("Rand", String.valueOf(rest));
      	this.session.put("rand", String.valueOf(rest));
      	gd.dispose();
      	try {
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 将内存中的图片通过流动形式输出到客户端
       return NONE;
    }
    private int getResult(int first,int seconde,String operate){
    	if("+".equals(operate)){
    		return getValidateCode(first)+getValidateCode(seconde);
    	}else if("-".equals(operate)){
    		return getValidateCode(first)-getValidateCode(seconde);
    	}else{
    		return 0;
    	}
    }
    /**
     * 根据随机得到实际运算值
     * @param first
     * @return
     */
    private int getValidateCode(int index){
    	if(index>=10){
    		return index-10;
    	}else{
    		return index;
    	}
    }
    private OutputStream getout() {
        response.reset();
        response.setContentType("image/jpeg");

        ServletOutputStream sos = null;
        try {
            sos = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sos;
    }

    /**
     * @return Returns the inputObjectname.
     */
    public String getInputObjectname() {
        return inputObjectname;
    }

    /**
     * @param inputObjectname
     *            The inputObjectname to set.
     */
    public void setInputObjectname(String inputObjectname) {
        this.inputObjectname = inputObjectname;
    }

}
