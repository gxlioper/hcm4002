<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%> 


<div class="formdiv"style="padding-top:50px;padding-left:40px;font-size:18px;">
	<dl>
		<dt style="width:60px;"><input type="hidden" name="n_id" id="n_id" value="<s:property value="n_id"/>"/>时间</dt>
	    <dd ><input id="notes_date" style="width:240px;height:30px;" name="notes_date" value="<s:property value="notes_date"/>" class="easyui-datebox" data-options="prompt:'时间'" ></dd>
	</dl>
	<dl>
	    <dt style="width:60px;">内容</dt>
	    <dd ><textarea  cols="26" rows="5" name="notes_content" id="notes_content"  ><s:property value="notes_content"/></textarea>
	    </dd>
	    <dt  style="position: absolute; height: 25px; width: 100px; margin-left: 310px;"><span  id="message" class="red"></span></dt>
	</dl>
</div>
<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;"onclick="saveNotesData();" >保存</a>
	</div>
</div>
