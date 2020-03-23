package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;
public class LoginDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	private List<UserCenterDepListDTO> listCenter = new ArrayList<UserCenterDepListDTO>();
	private List<DepUserDTO> depUserList=new ArrayList<DepUserDTO>();
	
	
	public List<UserCenterDepListDTO> getListCenter() {
		return listCenter;
	}
	public void setListCenter(List<UserCenterDepListDTO> listCenter) {
		this.listCenter = listCenter;
	}
	public List<DepUserDTO> getDepUserList() {
		return depUserList;
	}
	public void setDepUserList(List<DepUserDTO> depUserList) {
		this.depUserList = depUserList;
	}
	
	
}
