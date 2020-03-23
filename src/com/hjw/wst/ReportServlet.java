package com.hjw.wst;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hjw.ireport.DTO.DjdbeanList;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.WebConfigService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    doPost(request, response);
}
@SuppressWarnings({ "rawtypes", "unchecked" })
public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	CustomerInfoService customerInfoService = (CustomerInfoService)context.getBean("customerInfoService");
	WebConfigService webConfigService = (WebConfigService)context.getBean("webConfigService");
	//UserDTO user = (UserDTO)request.getSession().getAttribute("username");
    try {
         String fileName = "/applets/djd.jasper";//报表文件名称
         ServletContext strcontext = this.getServletConfig().getServletContext();
         String mdfile = strcontext.getRealPath(fileName);  
         String strIDES = request.getParameter("strIDES");
         List<DjdbeanList> list = new ArrayList<DjdbeanList>();
         if((strIDES!=null)&&(strIDES.length()>0))
         {
        	 String hsurl = webConfigService.findWebConfig("006").getTypes().trim();
             list=customerInfoService.getPrintDjd(strIDES, hsurl, 14);
         }
         try
         {
             /**
              * 以下是根源所在
              * 可能由于路径的问题
              * 也可能是引擎与设计器版本的不同引起
              * 得不到.jasper格式的报表对像
              */
             JRDataSource dataSource = new JRBeanCollectionDataSource(list);              
             JasperReport jasperReport =(JasperReport) JRLoader.loadObjectFromFile(mdfile);
             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

           
             
            /* final String CONTENTTYPE = "application/octet-stream";                
             response.setContentType(CONTENTTYPE);   
             ServletOutputStream ouputStream = response.getOutputStream();             
             try {     
                 ObjectOutputStream oos = new ObjectOutputStream(ouputStream);    
                 oos.writeObject(jasperPrint);     
                 oos.flush();     
                 oos.close();     
             } catch (Exception e) {     
                 //TODO:处理     
             }   */
             
             JRHtmlExporter exporter = new JRHtmlExporter();			
 			exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
 			exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER,response.getWriter());
 			exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);
 			exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "utf-8");
 			exporter.exportReport();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }       
       
    }
      
    public void init() throws ServletException {
        System.err.println("初始化方法");
        // Put your code here
    }

    public ReportServlet() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
    }
    
    
}
