package com.hjw.wst.DTO;

import java.util.List;

public class HeaderDTO {
	private List<Colmuns> colmuli;
	private List<ExamResultDetailDTO>  li;
	private List<ExamCountDTO> tj;
	
	
	public List<ExamCountDTO> getTj() {
		return tj;
	}
	public void setTj(List<ExamCountDTO> tj) {
		this.tj = tj;
	}
	public List<Colmuns> getColmuli() {
		return colmuli;
	}
	public void setColmuli(List<Colmuns> colmuli) {
		this.colmuli = colmuli;
	}
	public List<ExamResultDetailDTO> getLi() {
		return li;
	}
	public void setLi(List<ExamResultDetailDTO> li) {
		this.li = li;
	}
	
}
