package com.hjw.mongo.action;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hjw.mongo.DTO.Columns;
import com.hjw.mongo.DTO.DataGruid;
import com.hjw.mongo.DTO.DataMongoConditionDTO;
import com.hjw.mongo.DTO.DataMongoDTO;
import com.hjw.mongo.DTO.SQLColumDTO;
import com.hjw.mongo.DTO.SearchScientificFactor;
import com.hjw.mongo.model.DataMongoModel;
import com.hjw.mongo.service.DataMongoService;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("rawtypes")
public class DataMongoAction extends BaseAction implements ModelDriven{
	
	private static final long serialVersionUID = 8217814910220881058L;
	private DataMongoModel model = new DataMongoModel();
	private String sort;
	private String order;
	private DataMongoService dataMongoService;		
	private InputStream excelStream;  //输出流变量  
    private String excelFileName; //下载文件名     
    
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public void setDataMongoService(DataMongoService dataMongoService) {
		this.dataMongoService = dataMongoService;
	}

	/**
	 * 获取科研数据页面
	     * @Title: getResearchDataPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String showesearchDataMongo() throws WebException{
		this.model.setTime1(DateTimeUtil.getPastDate(365));
		this.model.setTime2(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	
	/**
	 * 保存查询条件
	     * @Title: expmongoDatasave   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String expmongoDatasave() throws Exception{
		UserDTO user = (UserDTO) session.get("username");	
		String mongodata= this.dataMongoService.getResearchDatasave(model, user);
		this.outJsonStrResult(mongodata);
		return NONE;
	}
	
	/**
	 * 删除查询条件
	     * @Title: expmongoDatasave   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String expmongoDatadel() throws Exception{	
		String mongodata= this.dataMongoService.getResearchDatadel(model.getAdd_i());
		this.outJsonStrResult(mongodata);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: researchDataconditionsId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getResearchDataconditionsId() throws Exception{
		UserDTO user = (UserDTO) session.get("username");	
		SearchScientificFactor list=new SearchScientificFactor();
		list= this.dataMongoService.getResearchDataconditionsId(model.getAdd_i());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取查询条件列表
	     * @Title: researchDataconditions   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String researchDataconditions() throws Exception{
		UserDTO user = (UserDTO) session.get("username");	
		List<SearchScientificFactor> list=new ArrayList<SearchScientificFactor>();
		list= this.dataMongoService.getResearchDataconditions(user);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1955导出全部 
	     * @Title: expmongoDataListall   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String expmongoDataListall() throws Exception{
		UserDTO user = (UserDTO) session.get("username");
		try{
			String str = URLDecoder.decode(this.model.getConditions(),"UTF-8");
			JSONArray conditions = JSONArray.fromObject(str);
			List<DataMongoConditionDTO> condition = (List<DataMongoConditionDTO>) JSONArray.toCollection(conditions,DataMongoConditionDTO.class);
			this.model.setCondition(condition);
		}catch(Exception ex){
			
		}
		
		try{			
			String str1 = URLDecoder.decode(this.model.getConditions1(),"UTF-8");
			JSONArray conditions1 = JSONArray.fromObject(str1);
			List<DataMongoConditionDTO> condition1 = (List<DataMongoConditionDTO>) JSONArray.toCollection(conditions1,DataMongoConditionDTO.class);
		    this.model.setCondition1(condition1);
		}catch(Exception ex){
			
		}

		DataMongoDTO mongodata=new DataMongoDTO();
		mongodata= this.dataMongoService.getResearchDataList(model, user,this.pagesize,this.pageno,this.sort,this.order,"ALL");
		List<Columns> collist= new ArrayList<Columns>();
		collist = getCollist(this.model.getSearchlimit(),mongodata,this.model.getCondition(),this.model.getCondition1());        
		HSSFWorkbook wb = new HSSFWorkbook();
		wb=expDataTableToJson(collist,mongodata.getDatas());
	    try {
			// 客户端不缓存
			 response.reset();// 清空response  
			 String filename=this.model.getSearchType()+"-"+DateTimeUtil.getDateTimes()+".xls";
	         response.addHeader("Content-Disposition", "attachment;filename="+filename);// 设置response的Header   
	         response.setContentType("application/vnd.ms-excel;charset=utf-8");  
	         OutputStream os=null;
	         os = new BufferedOutputStream(response.getOutputStream());//输出流  
	         wb.write(os);  
	         os.flush();
		} catch (IOException e) {
		e.printStackTrace();
		}
		return NONE;
	}
	/**
	 *1951 根据查询条件查询科研数据
	     * @Title: getResearchDataList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String mongoDataList() throws Exception{
		DataGruid data= new DataGruid(); 
		UserDTO user = (UserDTO) session.get("username");		
		try{
			JSONArray conditions = JSONArray.fromObject(this.model.getConditions());
			List<DataMongoConditionDTO> condition = (List<DataMongoConditionDTO>) JSONArray.toCollection(conditions,DataMongoConditionDTO.class);
		    this.model.setCondition(condition);
		}catch(Exception ex){
			
		}
		
		try{			
			String str1 = URLDecoder.decode(this.model.getConditions1(),"UTF-8");
			JSONArray conditions1 = JSONArray.fromObject(str1);
			List<DataMongoConditionDTO> condition1 = (List<DataMongoConditionDTO>) JSONArray.toCollection(conditions1,DataMongoConditionDTO.class);
		    this.model.setCondition1(condition1);
		}catch(Exception ex){
			
		}
		
		DataMongoDTO mongodata=new DataMongoDTO();
		mongodata= this.dataMongoService.getResearchDataList(model, user,this.pagesize,this.pageno,this.sort,this.order,"ONE");
		if (mongodata.getPageDTO().getPageTotal() > 0) {
			List<Columns> collist = new ArrayList<Columns>();
			collist = getCollist(this.model.getSearchlimit(), mongodata, this.model.getCondition(),
					this.model.getCondition1());
			data.setPageDTO(mongodata.getPageDTO());
			String str = DataTableToJson(collist, mongodata.getDatas());
			data.setStr(str);
			data.setSearchflag(true);
		}else{
    	   data.setSearchflag(false);
       }
		this.outJsonResult(data);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getCollist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param searchlimit
	     * @param: @param mongodata
	     * @param: @param conditions
	     * @param: @param conditions1
	     * @param: @return      
	     * @return: List<Columns>      
	     * @throws
	 */
	private List<Columns> getCollist(String searchlimit,DataMongoDTO mongodata,List<DataMongoConditionDTO> conditions,List<DataMongoConditionDTO> conditions1){
		List<Columns> collist= new ArrayList<Columns>();
        Columns clo= new Columns();
        clo.setField("exam_num");
        clo.setUpload("exam_num");
        clo.setWidth(100);
        clo.setTitle("体检号");
        collist.add(clo);
        
        clo= new Columns();
        clo.setField("id_num");
        clo.setUpload("id_num");
        clo.setWidth(120);
        clo.setTitle("身份证号");
        collist.add(clo);
        
        clo= new Columns();
        clo.setField("user_name");
        clo.setUpload("user_name");
        clo.setWidth(100);
        clo.setTitle("姓名");
        collist.add(clo);
        
        clo= new Columns();
        clo.setField("sex");
        clo.setUpload("sex");
        clo.setWidth(60);
        clo.setTitle("性别");
        collist.add(clo);
        
        clo= new Columns();
        clo.setField("age");
        clo.setUpload("age");
        clo.setWidth(60);
        clo.setTitle("年龄");
        collist.add(clo);
        
        clo= new Columns();
        clo.setField("phone");
        clo.setUpload("phone");
        clo.setWidth(100);
        clo.setTitle("电话");
        collist.add(clo);
        
        clo= new Columns();
        clo.setField("join_date");
        clo.setUpload("join_date");
        clo.setWidth(150);
        clo.setTitle("体检日期");
        collist.add(clo);
        
        clo= new Columns();
        clo.setField("com_name");
        clo.setUpload("com_name");
        clo.setWidth(150);
        clo.setTitle("单位");
        collist.add(clo);
        
        clo= new Columns();
        clo.setField("exam_types");
        clo.setUpload("exam_types");
        clo.setWidth(80);
        clo.setTitle("类型");
        collist.add(clo);
        
        for(DataMongoConditionDTO condition: conditions){
        	if (condition.getDep_category().equals("21")) {// 影像按照收费项目查询
        		SQLColumDTO item=new SQLColumDTO();
        		item=this.dataMongoService.chargingItemForCode(condition.getCharging_id());				
			    collist = putcolnum(collist, item.getName(), item.getCode() + "_R");
        	}else{
        		SQLColumDTO item=new SQLColumDTO();
        		item=this.dataMongoService.examinfoItemForCode(condition.getItem_id());
        		collist = putcolnum(collist, item.getName(), item.getCode() + "_R");
        	}
        }
        	
        if("limit".equals(searchlimit)){
        	for(DataMongoConditionDTO condition: conditions1){
            	if (condition.getDep_category().equals("21")) {// 影像按照收费项目查询
            		SQLColumDTO item=new SQLColumDTO();
            		item=this.dataMongoService.chargingItemForCode(condition.getCharging_id());
            		collist = putcolnum(collist, item.getName(), item.getCode() + "_R");
            	}else{
            		SQLColumDTO item=new SQLColumDTO();
            		item=this.dataMongoService.examinfoItemForCode(condition.getItem_id());
            		collist = putcolnum(collist, item.getName(), item.getCode() + "_R");
            	}
            }
		} else {
			for (String s : mongodata.getDatas()) {
				JSONObject myJson = JSONObject.fromObject(s);
				@SuppressWarnings("unchecked")
				Iterator<String> it = myJson.keys();
				while (it.hasNext()) {
					String json_key = it.next();
					if (json_key.indexOf("_itnm") > 0) {
						String name = (String) myJson.get(json_key);
						String col = json_key.substring(0, json_key.indexOf("_itnm")) + "_R";
						collist = putcolnum(collist, name, col);
					}
				}
			}
		}
        return collist;
	}

   private List<Columns> putcolnum(List<Columns> collist,String name,String colone){
	   boolean md=false;
	   for(Columns col:collist){
		   if(col.getField().equals(colone)){
			 md=true;
			 break;
		   }
	   }
	   if(!md){
		   Columns clo= new Columns();
	        clo.setField(colone);
	        clo.setUpload(colone);
	        clo.setWidth(100);
	        clo.setTitle(name);
	        collist.add(clo);
	   }
	   return collist;
   }
   
   public String DataTableToJson(List<Columns> collist,List<String> datas){
	     StringBuffer  jsonBuilder = new StringBuffer();
         jsonBuilder.append("{\"rows");
         jsonBuilder.append("\":[");

         for (String sdata:datas)
         {
        	 JSONObject myJson = JSONObject.fromObject(sdata);
             jsonBuilder.append("{");
             for (Columns col:collist)
             {
				try {
					String colbuffer= "";
					colbuffer="\"";
					colbuffer=colbuffer+col.getField();
					colbuffer=colbuffer+"\":\"";
					if("join_date".equals(col.getField())){
						try {
							JSONObject jsonObj = (JSONObject)myJson.get(col.getField());							
							//正常读取对象, 没有返回 null, 不产生异常
							long tranDate = (long)jsonObj.get("$date");
							Date datetime = new Date(tranDate);
							colbuffer = colbuffer + DateTimeUtil.shortFmt3(datetime);
						}catch(Exception ex){
							colbuffer = colbuffer +"";
						}
					}else{
						colbuffer=colbuffer+myJson.get(col.getField()).toString().replaceAll("\r|\n", " ").replaceAll("\n", " ").replaceAll("\"", "“");
					}
					colbuffer=colbuffer+"\",";
					jsonBuilder.append(colbuffer);
				}catch(Exception ex){
					String colbuffer= "\""+col.getField()+"\":\"\",";
					jsonBuilder.append(colbuffer);
            	 }
             }
             jsonBuilder.deleteCharAt(jsonBuilder.length()-1);
             jsonBuilder.append("},");
         }
         jsonBuilder.deleteCharAt(jsonBuilder.length()-1);
         jsonBuilder.append("],");
         jsonBuilder.append("\"title");
         //jsonBuilder.append(dt.TableName);
         jsonBuilder.append("\":[");
         //这是循环获取列名称
         for (Columns col:collist)
         {
             jsonBuilder.append("{");
             jsonBuilder.append("\"field");
             jsonBuilder.append("\":\"");
             jsonBuilder.append(col.getField());
             jsonBuilder.append("\",");
             jsonBuilder.append("\"title");
             jsonBuilder.append("\":\"");
             jsonBuilder.append(col.getTitle());
             jsonBuilder.append("\"");
             jsonBuilder.append("},");
         }
         //jsonBuilder.deleteCharAt(jsonBuilder.length()-1);
         //jsonBuilder.append("},");
  
         jsonBuilder.deleteCharAt(jsonBuilder.length()-1);
         jsonBuilder.append("]");
         jsonBuilder.append("}");
         return jsonBuilder.toString();
     }
   
   /**
    * 导出数据
        * @Title: expDataTableToJson   
        * @Description: TODO(这里用一句话描述这个方法的作用)   
        * @param: @param collist
        * @param: @param datas
        * @param: @throws Exception      
        * @return: void      
        * @throws
    */
   public HSSFWorkbook expDataTableToJson(List<Columns> collist,List<String> datas) throws Exception{
	   HSSFWorkbook wb = new HSSFWorkbook();
       //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet
	   //DateTimeUtil.getDateTime()
       HSSFSheet sheet = wb.createSheet("科研数据导出");       
        //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制
       HSSFRow row = sheet.createRow(0);
       //第四步，创建单元格样式：居中
       HSSFCellStyle style = wb.createCellStyle();
       style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
       //第五步，创建表头单元格，并设置样式
       HSSFCell cell;       
       for (int i=0;i<collist.size();i++)
       {
    	   cell = row.createCell(i);
    	   Columns col=collist.get(i);    	   
    	   cell.setCellValue(col.getTitle());
           cell.setCellStyle(style);
           
       }
      
       for (int i=0;i<datas.size();i++)
       {
    	   row = sheet.createRow(i + 1);
    	   String sdata=datas.get(i);
      	   JSONObject myJson = JSONObject.fromObject(sdata);
           for (int j=0;j<collist.size();j++)
           {
        	   Columns col=collist.get(j);        	   
				try {
					if("join_date".equals(col.getField())){
						try {
							JSONObject jsonObj = (JSONObject)myJson.get(col.getField());							
							//正常读取对象, 没有返回 null, 不产生异常
							long tranDate = (long)jsonObj.get("$date");
							Date datetime = new Date(tranDate);
							row.createCell(j).setCellValue(DateTimeUtil.shortFmt3(datetime));
						}catch(Exception ex){
							row.createCell(j).setCellValue("");
						}
					}else{
						row.createCell(j).setCellValue(myJson.get(col.getField()).toString().replaceAll("\r|\n", " ").replaceAll("\n", " ").replaceAll("\"", "“"));
					}
				}catch(Exception ex){
					row.createCell(j).setCellValue("");
          	   }
           }
       }
       
    	return wb;
    	 
   }

   public static String escapeExprSpecialWord(String keyword) {    
	   if (keyword != "") {    
	       String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|","\n" };    
	       for (String key : fbsArr) {    
	           if (keyword.contains(key)) {    
	               keyword = keyword.replace(key, "\\" + key);    
	           } 
	       }    
	   }    
	   return keyword;  
	}    
	
	/**
	 * 1756 查询收费项目列表
	     * @Title: getChargingItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String mongoChargingItemListByq() throws WebException{
		List<ChargingItemDTO> list = this.dataMongoService.getChargingItemListByq(this.model.getQ(),this.model.getAdd_i());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 1757 查询检查项目列表
	     * @Title: getExaminationItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String mongoExaminationItemListByq() throws WebException{
		List<ExaminationItemDTO> list = this.dataMongoService.getExaminationItemListByq(this.model.getCharging_id(), this.model.getQ(),this.model.getAdd_i());
		this.outJsonResult(list);
		return NONE;
	}
	
	public String mongocompanychangshow() throws WebException, Exception {
		UserDTO user = new UserDTO();
		user = (UserDTO) session.get("username");
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		tr = this.dataMongoService.getCompanyForNameIsactive(this.model.getName(),user);
		this.outJsonResult(tr);
		return NONE;

	}
	
	public DataMongoModel getModel() {
		return model;
	}
	public void setModel(DataMongoModel model) {
		this.model = model;
	}
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
