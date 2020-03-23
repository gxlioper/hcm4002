package com.hjw.Bean.ZJ;

import java.util.ArrayList;
import java.util.List;

public class RptsMsg extends  BodyExamMsg{	
	private List<OrganMsg> organs=new ArrayList<OrganMsg>();//报告组织主体	object	此处主要描述此人此次体检所涉及的科室、项目组以及它们的关系，具体见下方表格organs部分
	private List<ItemMsg> items=new ArrayList<ItemMsg>();//报告项目结果主体	此处描述项目结果以及项目相关属性，具体见下方表格items部分
	private List<SummaryMsg> summarys=new ArrayList<SummaryMsg>();//报告项目组小结主体	此处描述小结，小结可以下在项目组上，也可下在科室上，依不同的体检系统而异，具体见下方表格summarys部分
	private List<AdviceMsg> advices=new ArrayList<AdviceMsg>();//报告终检建议主体	此处描述终检建议，具体见下方表格advices部分
	private List<RawDataMsgt> raw_datas=new ArrayList<RawDataMsgt>();//文件数据主体(暂未使用)	为以后扩展使用，目前先不需要
	private List<MediaMsg> medias=new ArrayList<MediaMsg>();//报告媒体主体	此处描述报告中媒体部分，如心电图等，可无，具体见下方表格medias部分
	
	public List<OrganMsg> getOrgans() {
		return organs;
	}
	public void setOrgans(List<OrganMsg> organs) {
		this.organs = organs;
	}
	public List<ItemMsg> getItems() {
		return items;
	}
	public void setItems(List<ItemMsg> items) {
		this.items = items;
	}
	public List<SummaryMsg> getSummarys() {
		return summarys;
	}
	public void setSummarys(List<SummaryMsg> summarys) {
		this.summarys = summarys;
	}
	public List<AdviceMsg> getAdvices() {
		return advices;
	}
	public void setAdvices(List<AdviceMsg> advices) {
		this.advices = advices;
	}
	public List<RawDataMsgt> getRaw_datas() {
		return raw_datas;
	}
	public void setRaw_datas(List<RawDataMsgt> raw_datas) {
		this.raw_datas = raw_datas;
	}
	public List<MediaMsg> getMedias() {
		return medias;
	}
	public void setMedias(List<MediaMsg> medias) {
		this.medias = medias;
	}
	
	
	
}
