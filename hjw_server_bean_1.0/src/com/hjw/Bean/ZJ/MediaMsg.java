package com.hjw.Bean.ZJ;

public class MediaMsg {
	private String organ_id="";//":"所属的组织id",
	private String organ_name="";//":"组织名称",
	private String file_extension="";//":"媒体文件的扩展名,用扩展名表示媒体的类型,如jpg、bmp、png、wav、mp3",
	private String media="";//":"媒体内容,BASE64编码"
	public String getOrgan_id() {
		return organ_id;
	}
	public void setOrgan_id(String organ_id) {
		this.organ_id = organ_id;
	}
	public String getOrgan_name() {
		return organ_name;
	}
	public void setOrgan_name(String organ_name) {
		this.organ_name = organ_name;
	}
	public String getFile_extension() {
		return file_extension;
	}
	public void setFile_extension(String file_extension) {
		this.file_extension = file_extension;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	
	

}
