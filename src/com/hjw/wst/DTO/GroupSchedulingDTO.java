package com.hjw.wst.DTO;



/**
 * 排期
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: zr     
     * @date:   2017年7月6日 上午11:33:18   
     * @version V2.0.0.0
 */
public class GroupSchedulingDTO {
	
	 private long id;
	
     private String scheduling_content;
	
     private long creater;
	
     private String create_time;
	
     private long updater;
	
     private String update_time;
	
     private String scheduling_time;
	
     private String scheduling_end_time;
     
     
     private String  title;//": "会议已经结束",'     	//标题
     private String  start;//: "2017-07-06T14:30:00",'	//开始时间
     private String   end;// "2017-07-06T19:30:00",'	//结束时间
     private String   confname;//:"呵呵呵",'//重要程度	//重要程度
     private String    description;//: "内容",'			//内容
     private String    allDay;//: "false",'				//状态
     private String    fullname;//:"会议已经结束1"'		//主页页面显示标题
     
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getScheduling_content() {
		return scheduling_content;
	}
	public void setScheduling_content(String scheduling_content) {
		this.scheduling_content = scheduling_content;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public long getUpdater() {
		return updater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getScheduling_time() {
		return scheduling_time;
	}
	public void setScheduling_time(String scheduling_time) {
		this.scheduling_time = scheduling_time;
	}
	public String getScheduling_end_time() {
		return scheduling_end_time;
	}
	public void setScheduling_end_time(String scheduling_end_time) {
		this.scheduling_end_time = scheduling_end_time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
		this.setFullname(title);
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getConfname() {
		return confname;
	}
	public void setConfname(String confname) {
		this.confname = confname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAllDay() {
		return allDay;
	}
	public void setAllDay(String allDay) {
		this.allDay = allDay;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
    
	
     
}
