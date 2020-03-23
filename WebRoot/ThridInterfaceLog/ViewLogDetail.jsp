<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/style.css" />
<link type="text/css" rel="stylesheet"	href="<%=request.getContextPath()%>/themes/default/dtreeck.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/common.js?randomId=<%=Math.random()%>"></script>
<style>
	#view_detail dl dt{
		font-weight: 600;
		text-align:right;
		color:#3692c6;
	}
	.tableLog tr th{
		border-color: #ccc;
		font-size:15px;
		width:12%;
		color:#3692c6;
	}
	.tableLog tr td{
		padding: 8px;
		border-color: #ccc;
		font-size:14px;
	}
</style>
	<input type="hidden" id="id" value="<s:property value="model.id"/>">
	<div class="easyui-tabs">
        <div title="日志详情" style="padding:10px;">
    		<fieldset style="padding-right: 20px;">
				<div id="view_detail" class="user-query">
					<dl>
						<dd style="margin-left: -9px;">
							<a href="javascript:expTxtContent('<s:property value="model.id"/>');" class="easyui-linkbutton c6" style="width:100px;">导出txt文档</a>
				    		<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:window.close()">关闭</a>
						</dd>
					</dl>
					<div class="user-query" style="background: #f2fff2;  margin-top:8px;" >
						<table id="tableLogMenu" class="tableLog" border="1" style="border-collapse:collapse; padding: 15px;border-color: #ccc; margin-top: -5px;">
						  <tr>
						    <th>接口名称</th>
						    <td><s:property value="model.message_name"/></td>
						  </tr>
						  <tr>
						    <th>申请号</th>
						    <td><s:property value="model.req_no"/></td>
						  </tr>
						  <tr>
						    <th>体检编号</th>
						    <td><s:property value="model.exam_no"/></td>
						  </tr>
						  <tr>
						    <th>消息标识</th>
						    <td><s:property value="model.message_id"/></td>
						  </tr>
						  <tr>
						    <th>消息时间</th>
						    <td><s:property value="model.message_startDate"/></td>
						  </tr>
						  <tr>
						    <th>消息发送者</th>
						    <td><s:property value="model.sender"/></td>
						  </tr>
						  <tr>
						    <th>消息接收者</th>
						    <td><s:property value="model.receiver"/></td>
						  </tr>
						  <tr>
						    <th>消息请求原文</th>
						    <td><s:property value="model.message_request"/></td>
						  </tr>
						  <tr>
						    <th>消息应答原文</th>
						    <td><s:property value="model.message_response"/></td>
						  </tr>
						  <tr>
						    <th>系统请求数据</th>
						    <td><s:property value="model.sys_request"/></td>
						  </tr>
						  <tr>
						    <th>系统应答数据</th>
						    <td><s:property value="model.sys_respones"/></td>
						  </tr>
						</table>
					</div>
				</div>
			</fieldset>
        </div>
        <div title="LIS日志"  style="overflow:auto;padding:10px;">
    		<fieldset style="padding-right: 20px;">
				<div id="view_detail" class="user-query">
					<dl>
						<dd style="margin-left: -9px;">
				    		<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:window.close()">关闭</a>
						</dd>
					</dl>
					<div class="user-query" style="background: #f9faff; margin-top:8px;" >
						<table class="tableLog" border="1" style="border-collapse:collapse; padding: 15px;border-color: #ccc; margin-top: -5px;">
						  <tr>
						    <th>体检号</th>
						    <td><s:property value="model.exam_num"/></td>
						  </tr>
						  <tr>
						    <th>条码号</th>
						    <td><s:property value="model.sample_barcode"/></td>
						  </tr>
						  <tr>
						    <th>LIS组合项目代码</th>
						    <td><s:property value="model.lis_item_code"/></td>
						  </tr>
						  <tr>
						    <th>LIS组合项目名称</th>
						    <td><s:property value="model.lis_item_name"/></td>
						  </tr>
						  <tr>
						    <th>LIS报告细项代码</th>
						    <td><s:property value="model.report_item_code"/></td>
						  </tr>
						  <tr>
						    <th>LIS报告细项名称</th>
						    <td><s:property value="model.report_item_name"/></td>
						  </tr>
						  <tr>
						    <th>检查时间</th>
						    <td><s:property value="model.exam_date"/></td>
						  </tr>
						  <tr>
						    <th>项目结果</th>
						    <td><s:property value="model.item_result"/></td>
						  </tr>
						  <tr>
						    <th>项目单位</th>
						    <td><s:property value="model.item_unit"/></td>
						  </tr>
						  <tr>
						    <th>高低标志（H：高 L：低 N：正常 HH：偏高报警 LL：偏低报警 C：危急）</th>
						    <td><s:property value="model.lis_flag"/></td>
						  </tr>
						  <tr>
						    <th>参考范围</th>
						    <td><s:property value="model.ref"/></td>
						  </tr>
						  <tr>
						    <th>检查医生</th>
						    <td><s:property value="model.doctor"/></td>
						  </tr>
						  <tr>
						    <th>审核医生</th>
						    <td><s:property value="model.sh_doctor"/></td>
						  </tr>
						  <tr>
						    <th>备注</th>
						    <td><s:property value="model.note"/></td>
						  </tr>
						  <tr>
						    <th>读取标志（默认0，读取1，无匹配2，错误3）</th>
						    <td><s:property value="model.ref"/></td>
						  </tr>
						</table>
					</div>
				</div>
			</fieldset>
        </div>
        <div title="PACS日志" style="padding:10px;">
    		<fieldset style="padding-right: 20px;">
				<div id="view_detail" class="user-query">
					<dl>
						<dd style="margin-left: -9px;">
				    		<a href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;" onclick="javascript:window.close()">关闭</a>
						</dd>
					</dl>
					<div class="user-query" style="background: #f9faff; margin-top: 8px;" >
						<table class="tableLog" border="1" style="border-collapse:collapse; padding: 15px;border-color: #ccc; margin-top: -5px;">
						  <tr>
						    <th>申请单号</th>
						    <td><s:property value="model.req_no"/></td>
						  </tr>
						  <tr>
						    <th>PACS报告ID</th>
						    <td><s:property value="model.pacs_checkno"/></td>
						  </tr>
						  <tr>
						    <th>体检号</th>
						    <td><s:property value="model.exam_num"/></td>
						  </tr>
						  <tr>
						    <th>项目名称</th>
						    <td><s:property value="model.item_name"/></td>
						  </tr>
						  <tr>
						    <th>PACS检查项目代码</th>
						    <td><s:property value="model.pacs_item_code"/></td>
						  </tr>
						  <tr>
						    <th>检查类别（US：超声  MR：核磁  CT：CT检查  XX：射线检查）</th>
						    <td><s:property value="model.study_type"/></td>
						  </tr>
						  <tr>
						    <th>检查部位</th>
						    <td><s:property value="model.study_body_part"/></td>
						  </tr>
						  <tr>
						    <th>检查结论</th>
						    <td><s:property value="model.clinic_diagnose"/></td>
						  </tr>
						  <tr>
						    <th>检查描述</th>
						    <td><s:property value="model.clinic_symptom"/></td>
						  </tr>
						  <tr>
						    <th>医师意见</th>
						    <td><s:property value="model.clinic_advice"/></td>
						  </tr>
						  <tr>
						    <th>阳性状态（N：正常 Y：阳性报告 C：危急）</th>
						    <td><s:property value="model.is_abnormal"/></td>
						  </tr>
						  <tr>
						    <th>报告图片路径</th>
						    <td><s:property value="model.report_img_path"/></td>
						  </tr>
						  <tr>
						    <th>检查图片</th>
						    <td><s:property value="model.img_path"/></td>
						  </tr>
						  <tr>
						    <th>检查状态（0：默认；1：登记取消；2：已登记；3：已检查完 4：已报告完 5：已审核完；）</th>
						    <td><s:property value="model.study_state"/></td>
						  </tr>
						  <tr>
						    <th>记录医生姓名</th>
						    <td><s:property value="model.reg_doc"/></td>
						  </tr>
						  <tr>
						    <th>检查医生姓名</th>
						    <td><s:property value="model.check_doc"/></td>
						  </tr>
						  <tr>
						    <th>检查时间</th>
						    <td><s:property value="model.check_date"/></td>
						  </tr>
						  <tr>
						    <th>报告医生</th>
						    <td><s:property value="model.report_doc"/></td>
						  </tr>
						  <tr>
						    <th>报告时间</th>
						    <td><s:property value="model.report_date"/></td>
						  </tr>
						  <tr>
						    <th>审核医师</th>
						    <td><s:property value="model.audit_doc"/></td>
						  </tr>
						  <tr>
						    <th>审核时间</th>
						    <td><s:property value="model.audit_date"/></td>
						  </tr>
						  <tr>
						    <th>备注</th>
						    <td><s:property value="model.note"/></td>
						  </tr>
						  <tr>
						    <th>报告读取标志（0：默认 1：读取完 2：无匹配 3：其他错误）</th>
						    <td><s:property value="model.status"/></td>
						  </tr>
						  <tr>
						    <th>读取日期时间</th>
						    <td><s:property value="model.trans_date"/></td>
						  </tr>
						  <tr>
						    <th>是否取图（0 ：不取 1 ： 取）</th>
						    <td><s:property value="model.is_tran_image"/></td>
						  </tr>
						  <tr>
						    <th>是否为报告图（0 ：图片不包含文字报告 1 ：图片为整个报告）</th>
						    <td><s:property value="model.is_report_image"/></td>
						  </tr>
						</table>
					</div>
				</div>
			</fieldset>
        </div>
    </div>

	
	<script type="text/javascript">
		$(document).ready(function () {
			
			$.ajax({
				url : 'queryViewDetailListLog.action',
				data : {id:$("#id").val()},
				type : "post",//数据发送方式   
				success : function(data) {
					var obj=eval('('+data+')');
					if(obj.length>0){
						for(var i=0;i<obj.length;i++){
							var div1 = 
								  "<tr>"+
								    "<th style='color:#ca0c16;'>日志时间 "+obj[i].ldate+" </th>"+
								    "<td>"+obj[i].lmessage+"</td>"+
								  "</tr>";
								/* "<dl>"+
									"<dt>日志时间：</dt>"+
									"<dd style='color:#ca0c16;'>"+obj[i].ldate+"</dd>"+			
								"</dl>"+
								"<dl>"+
									"<dd>&nbsp;&nbsp;&nbsp;&nbsp;"+obj[i].lmessage+"</dd>"+			
								"</dl>"+
								"<br/>"; */
							$("#tableLogMenu").append(div1);	
						}
					}
					
				},
				error : function() {
					$.messager.alert("操作提示", "操作错误", "error");					
				}
			});
		});
		
		/**
		 * 导出文本文件
		 */
		function expTxtContent(id){
			window.location.href = 'expTxtContentFile.action?id='+id;
		}
	</script>

