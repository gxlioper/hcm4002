package com.hjw.echartsSearch.DTO;

import java.util.ArrayList;
import java.util.List;

public class MonthlyAmtAll{
	private List<MonthlyAmt> list = new ArrayList<MonthlyAmt>();
	private MonthlyULAmt gulamt=new MonthlyULAmt();
	private MonthlyULAmt tulamt=new MonthlyULAmt();
	
	public List<MonthlyAmt> getList() {
		return list;
	}
	public void setList(List<MonthlyAmt> list) {
		this.list = list;
	}
	public MonthlyULAmt getGulamt() {
		return gulamt;
	}
	public void setGulamt(MonthlyULAmt gulamt) {
		this.gulamt = gulamt;
	}
	public MonthlyULAmt getTulamt() {
		return tulamt;
	}
	public void setTulamt(MonthlyULAmt tulamt) {
		this.tulamt = tulamt;
	}
	
	
}
