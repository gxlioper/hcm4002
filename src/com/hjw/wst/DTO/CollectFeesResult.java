package com.hjw.wst.DTO;

public class CollectFeesResult {

	private long user_id;
	private String account_num;
	private String req_num;
	private String flag;
	private String info;
	
	private int id;
	private String exam_num;
	private String invoice_head;
	private String invoice_num;
	private long summary_id;
	private String status;
	private WebSocketPosSendDTO webSocketPosSendDTO;
	
	public long getSummary_id() {
		return summary_id;
	}
	public void setSummary_id(long summary_id) {
		this.summary_id = summary_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getInvoice_head() {
		return invoice_head;
	}
	public void setInvoice_head(String invoice_head) {
		this.invoice_head = invoice_head;
	}
	public String getInvoice_num() {
		return invoice_num;
	}
	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getAccount_num() {
		return account_num;
	}
	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getReq_num() {
		return req_num;
	}
	public void setReq_num(String req_num) {
		this.req_num = req_num;
	}
	public WebSocketPosSendDTO getWebSocketPosSendDTO() {
		return webSocketPosSendDTO;
	}
	public void setWebSocketPosSendDTO(WebSocketPosSendDTO webSocketPosSendDTO) {
		this.webSocketPosSendDTO = webSocketPosSendDTO;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
