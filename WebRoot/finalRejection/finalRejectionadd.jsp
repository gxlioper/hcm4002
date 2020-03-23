<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">

</script>

	<fieldset style="margin:5px;">
	<legend><strong> 添加驳回意见</strong></legend>
   	    <dl>
   	       <dt><input type="hidden" name="id" id="id" value="<s:property value="id"/>"/>
   	       </dt>
   	       		<dt  style="padding-top:60px;"><strong class="red">*</strong>驳回意见：</dt>
		<dd>  <textarea style="width:500px;resize:none;height:150px;" id="rejectcontext"><s:property value="model.reject_context"/></textarea> 
		</dd>
   	    </dl>
	    	   <!--  </form> -->
	    	<div class="dialog-button-box">
	<div class="inner-button-box">
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="f_rejectsave();">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:$('#reject_edit').dialog('close')">关闭</a>
	</div>
</div>
	 </fieldset>

