package com.hjw.util;

import java.util.Locale;
import java.util.ResourceBundle;

import com.opensymphony.xwork2.ActionContext;

public class Language {
	public static void setLanguage(String language){
		if(language.equals("zh")){
			Locale locale = new Locale("zh", "CN");
			ActionContext context = ActionContext.getContext(); 
			context.setLocale(locale);
		}
		if(language.equals("en")){
			Locale locale = new Locale("en", "US");
			ActionContext context = ActionContext.getContext(); 
			context.setLocale(locale);
		}
	}
	
	//根据相应key和languge方式，获取语言
	public static String getI18nValue(String key, String language){
		Locale locale = null;
		
		if("zh".equals(language)){
			locale = new Locale("zh","CN");
		}else if("en".equals(language)){
			locale = new Locale("en","US");
		}else{
			locale = Locale.getDefault();
		}
		
		ResourceBundle bundle = ResourceBundle.getBundle("language.language", locale);
		return bundle.getString(key);
	}
}
