<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <meta charset="UTF-8">
    <title>体检人员基本信息</title>

<link href="../css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../css/demo.css">
<link href="../css/component-chosen.css" rel="stylesheet">
<script type="text/javascript" src="../../html5/js/jquery.min.js"></script>
<script type="text/javascript" src="../js/bootbox.js"></script>
<script type="text/javascript" src="../js/chosen.jquery.js"></script>
<script type="text/javascript" src="../js/bootbox.js"></script>

<link rel='stylesheet' type='text/css' href='../../html5/plus/table/bootstrap-table.min.css'>
<script type="text/javascript" src="../../html5/js/html5shiv.min.js"></script>
<script type="text/javascript" src="../js/bootstrap-table.js"></script>
	
	
<script type="text/javascript" src="../js/bootstrap.js"></script>
<script type="text/javascript" src="group.js"></script>



</head>

<style type="text/css">
.col-lg-3 {
    width: 50%;
    float: left;
}
.btn-info {
    background-color: #359bcc!important;
    }
.btn-primary {
     color: #fff;
     background-color: #359bcc; 
     border-color: #359bcc; 
}
</style>

<body>
  <nav class="navbar navbar-expand-lg navbar-dark fixed-top navbar-shrink" style="background-color: #359bcc" id="mainNav">
    <div class="container">
<!--       <a class="navbar-brand js-scroll-trigger" href="#page-top">火箭蛙  &nbsp; &nbsp; <span id="time" style="text-align:left;font-size:16px;"></span></a> -->
      <div class="collapse navbar-collapse" id="navbarResponsive">
      <div style="border:0px solid red;margin-top: 20px; float: left;margin-left: 0px;height: 50px;width: 200px;background-image: url(../img/logo.png);background-repeat:no-repeat;background-size:60%;">
  		
  	  </div>
        <div style="width: 20%; float: right; margin-top: 3%;"><a class="nav-link js-scroll-trigger" style="color: #000;;" href="padLogin.action">退出</a></div>
      </div>
    </div>
  </nav>
<input type="hidden" id="exam_id" value="<s:property value="model.exam_id"/>">
<input type="hidden" id="w_exam_num" value="<s:property value="model.exam_num"/>">
<input type="hidden" id="user_uuid"/>  
<input type="hidden" id="exam_indicator"/>  

<!--     <form class="" role="form" > -->
<!--     <form> -->
        <div class="container">
            <div class="row" style="padding: 20px 0">
                <h3>体检人员基本信息</h3>
            </div>
           <div class="row">
<!--                 <div class="form-group col-lg-3"> -->
<!--                     <div class="input-group" id="inputcomanme"> -->
<!--                         <span class="input-group-addon">任务</span> -->
<!--                         <select class="form-control" id="comname1" name="comname" onchange="gradeChange()"> -->
<!--                       <option value="">请选择</option> -->
<!-- <!--  							<option value="test2">test2</option> --> 
<!--                         </select> -->
<!--                     </div> -->
<!--                 </div> -->
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">任务</span>
                        <input class="form-control" name="comname" readonly="readonly"  id="comname1"  type="text" style="width: 69%;">&nbsp;
                        <input class="form-control" name="comname" readonly="readonly"  id="comname3"  type="hidden" style="width: 69%;">
                    	<button class="btn btn-primary" onclick="update_info(8)" data-toggle="modal" data-target="#myModal">选择</button>
                    </div>
                </div>
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon ">公司</span>
                        <select class="form-control" id="company_id" name="company_id">
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <!--前缀-->
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">体检编号</span>
                        <input class="form-control" readonly="readonly" name="exam_num" id="exam_num" type="text" style="ime-mode: disabled;">
                    </div>
                </div>
               <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">姓名<font color="red">*</font></span>
                        <input class="form-control" name="custname" maxlength="20" id="custname"  type="text">
                    </div>
                </div>
            </div>
            <div class="row">
               <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">身份证号<font color="red">*</font></span>
                        <input class="form-control" name="idNum" id="idNum"  onchange="IdCard1()" type="text">
                    </div>
                </div>
                
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">年龄 <font color="red">*</font></span>
                        <input class="form-control" name="age" id="age" type="text" onKeyUp="if(this.value.length>2){this.value=this.value.substr(0,2)};this.value=this.value.replace(/[^\d]/g,'');">
                    </div>
                </div>
            </div>
            <div class="row">
             <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">出生日期</span>
                        <input class="form-control" readonly="readonly" type="text" id="csrq">
                    </div>
                </div>
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">人员类别</span>
                        <select class="form-control" id="rylb"  name="rylb">
                        </select>
                    </div>
                </div>
                
            </div>
            <div class="row">
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">性别<font color="red">*</font></span>
                        <select class="form-control" id="sex" name="sex">
                        </select>
                    </div>
                </div>
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">体检类别</span>
                        <select class="form-control" id="tjlx" name="tjlx">
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">婚否</span>
                        <select class="form-control" id="is_Marriage" name="is_Marriage">
                        </select>
                    </div>
                </div>
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">费别</span>
                        <select class="form-control" id="sftype" name="sftype">
                        </select>
                    </div>
                </div>
            </div>
             <div class="row">
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">民族</span>
                        <select class="form-control"  id="minzhu" name="minzhu"	>
                        </select>
                    </div>
                </div>
<!--                 <div class="form-group col-lg-3"> -->
<!--                     <div class="input-group"> -->
<!--                         <span class="input-group-addon">年龄</span> -->
<!--                         <input class="form-control" name="minzhu" id="minzhu" type="hidden"> -->
<!--                         <input class="form-control" name="minzhu1" id="minzhu1" type="text"> -->
<!--                         <button class="btn btn-primary" onclick="update_info(8)" data-toggle="modal" data-target="#myModal1">编辑</button> -->
<!--                     </div> -->
<!--                 </div> -->
<!--                 <div class="form-group col-lg-3"> -->
<!--                     <div class="input-group"> -->
<!--                         <span class="input-group-addon">身份类型</span> -->
<!--                         <select class="form-control" id="customerType" name="customerType"> -->
<!--                         </select> -->
<!--                     </div> -->
<!--                 </div> -->
<!--             </div> -->
<!--              <div class="row"> -->
<!--                 <div class="form-group col-lg-3"> -->
<!--                     <div class="input-group"> -->
<!--                         <span class="input-group-addon">电话</span> -->
<!--                         <input class="form-control" name="addtel"  id="addtel"  type="text"> -->
<!--                     </div> -->
<!--                 </div> -->
                <div class="form-group col-lg-3">
                    <input id="chkItem" name="is_after_pay" checked="true" type="radio" value="N"/>前付费
					<input id="chkItem" name="is_after_pay"  type="radio" value="Y"/>后付费
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">既往史</span>
                        <input class="form-control" id="past_medical_history" maxlength="20" type="text">
                    </div>
                </div>
                <div class="input-group">
                        <span class="input-group-addon">选择分组</span>
                        <select class="form-control" id="group_id"  name="group_id" onclick="getgroups();">
                        </select>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon">备注</span>
                        <input class="form-control" name="remark" maxlength="20" id="remark" type="text">
                    </div>
                </div>
            </div>
            <button type="button" id="btnRegister"  onclick="fnewbutton();" class="btn btn-primary">下一步</button>
	        <button class="btn btn-primary"  id="btn-search1" onclick="javascript:window.close();">关闭</button>
        	<button type="reset" class="btn btn-default" id="btnRegister">重置</button>
        </div>
        
       
        <input type="hidden" id="batch_id" />
        



<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">体检任务</h4>
            </div>
<!--             <form id="form_data"> -->
            <div class="input-group col-md-3" style="float:left;margin-top: 10px;margin-left: 10px;width:34% ">
				<input type="text" class="form-control" style="width: 95%;margin-left: 5px;" id="comname2" name="comname2" onchange="reload()"  onkeyup="reload()"  placeholder="请输入体检任务" /> 
<%-- 				 <span class="input-group-btn" style=" width: 34%;"> --%>
<!-- 					<button class="btn btn-info btn-search"  id="btn-search1" onclick="reload();">查找</button> -->
<%-- 				</span> --%>
				
			</div>
			<button type="button" class=" btn-info btn btn-default"  style="float:right;margin-top: 10px; " data-dismiss="modal">关闭
                </button>
            <div class="tab-pane fade in active" style="margin-top: 10px;margin-left: 10px " id="tab2">
				<table class="table table-striped table-hover" id="reportTable"></table>
			</div>
<!--             </form> -->
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


</body>
</html>
