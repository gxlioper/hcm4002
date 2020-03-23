<%@ page contentType="text/html;charset=utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">

</script>
			<div class="formdiv">
			<input type="hidden" name="id" id="id" value="<s:property value='id'/>"   />
				<dl>
	    	       <dt>职业危害类别</dt>
	    	       <dd><input style="width:300px;" id="hazardclass_name"  value="<s:property value='hazardclass_name'/>" required='true'  ></dd>
	    	    </dl>
				<dl>
	    	       <dt>职业危害名称</dt>
	    	       <dd><input style="width:300px;" id="hazard_name" value="<s:property value='hazard_name'/>"  required='true' ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>
	    	       		职业体检类别</dt>
	    	       <dd><input style="width:300px;" id="occuphyexaclass_name"  value="<s:property value='occuphyexaclass_name'/>" required='true' ></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>目标疾病与职业禁忌证</dt>
	    	       <dd><textarea style="width:80%;resize:vertical;" cols="60" rows="3" name="diseaseandtaboo" id="diseaseandtaboo" ><s:property value="diseaseandtaboo"/></textarea></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>检查内容</dt>
	    	       <dd><textarea style="width:80%;resize:vertical;" cols="60" rows="3" name="checkcontent" id="checkcontent" ><s:property value="checkcontent"/></textarea></dd>
	    	    </dl>
	    	    <dl>
	    	       <dt>检查依据</dt>
	    	       <dd><textarea style="width:80%;resize:vertical;" cols="60" rows="3" name="checkcriterion" id="checkcriterion" ><s:property value="checkcriterion"/></textarea></dd>
	    	    </dl>
	    	    <dl>
	    	    	<dt>检查周期</dt>
	    	       <dd><textarea style="width:80%;resize:vertical;" cols="60" rows="3" name="examperiod" id="examperiod" ><s:property value="examperiod"/></textarea></dd>
	    	    </dl>
	    	    <dl>
	    	    	<dt>疾病跟踪</dt>
	    	       <dd><textarea style="width:80%;resize:vertical;" cols="60" rows="3" name="followdisease" id="followdisease" ><s:property value="followdisease"/></textarea></dd>
	    	    </dl>
	    	    <dl>
	    	    	<dt>备注</dt>
	    	       <dd><textarea style="width:80%;resize:vertical;" cols="60" rows="4" name="remark" id="remark" ><s:property value="remark"/></textarea></dd>
	    	    </dl>
	    	    </div>
