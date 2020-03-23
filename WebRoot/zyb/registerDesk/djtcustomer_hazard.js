/**
 * @author: 张瑞
 * @date: 2019年12月9日 下午8:32:41
 * @version V4.0.0.0
 *
 */
var form
layui.use('form', function() {
    form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
    var ys = zywhys()
    //头部已选按钮，添加事件
    form.on('checkbox(yxxm)', function(data) {
        zywhys_chebox(this)
    });
    for (var i = 0; i < ys.length; i++) {

        form.on('checkbox(c'+i+')', function(data) {
            //编码、名称、年限
            ys_chebox(this,$(this).data('hazard_code'),$(this).data('hazard_name'),$(this).data('hazard_year'))
        });
    }
    form.on('checkbox(y)', function(data) {
        //编码、名称、年限
        ys_chebox(this,$(this).data('hazard_code'),$(this).data('hazard_name'),$(this).data('hazard_year'))
    });
    form.render();
})
/**
 * 体检类别渲染dom
 */
$(function () {
    var tj = tj_type();
    for(var i=0; i< tj.length; i++){
        if(i == 0){
            var html = '<input type="radio" onclick="zywhys_html()" checked name="tj_lb"  value="'+tj[i].occuphyexaclassID+'" data-text="'+tj[i].title+'">'+tj[i].title
                +'&nbsp;&nbsp;&nbsp;&nbsp;'
        } else {
            var html = '<input type="radio" onclick="zywhys_html()" name="tj_lb"  value="'+tj[i].occuphyexaclassID+'" data-text="'+tj[i].title+'">'+tj[i].title
                +'&nbsp;&nbsp;&nbsp;&nbsp;'
        }
        $('#tjlb_type').append(html)
    }
    //职业危害类别树
    tj_type_tree()
    //体检人已保存的因素查询
    examhazardList()
    //因素数据在页面展示出来
    zywhys_html()
})
/**
 * 职业危害因素
 */
function zywhys(){
    var res = ''
    var hazardclassID = ''
    if($('#tt').tree('getSelected')!=null){
        hazardclassID = $('#tt').tree('getSelected').id
    }
    $.ajax({
        url:'getOccuHazardFactorsList.action',
        data:{
            pageSize:99999,
            hazard_name:$('#hazard_name').val(),
            hazardclassID:hazardclassID
        },
        type:'post',
        dataType:'json',
        async:false,
        success:function (text) {
            res = text.rows
        }
    })
    return res
}
/**
 * 职业危害类别树
 */
function tj_type_tree() {
    $('#tt').tree({
        url:'getZYB_OccuhazardClassList.action',
        loadFilter: function(t){
            var data = new Array()
            var obj = new Object()
            var res = t.rows
            obj.text = '全部'
            obj.id = 'qb'
            obj.iconCls='icon-shu'
            data.push(obj)
            for (var i = 0; i < res.length; i++) {
                obj = new Object()
                obj.text = res[i].hazardclass_name
                obj.id = res[i].hazardclassID
                obj.iconCls='icon-shu'
                data.push(obj)
            }
            return data
        },
        onClick: function(node){
            zywhys_html()
        },
        onLoadSuccess:function(node,data){
            $(".loading_div").remove();
            $("#tt li:eq(0)").find("div").addClass("tree-node-selected");   //设置第一个节点高亮
            var n = $("#tt").tree("getSelected");
            if(n!=null){
                $("#tt").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法
            }
        }
    })
}
/**
 * 体检类别数据
 * @returns {any[]}
 */
function tj_type(){
    var res = [];
    var data = new Array()
    var obj = new Object()
    $.ajax({
        url:'getZYB_OccuphyexaList.action',
        data:'',
        type:'post',
        async:false,
        dataType:'json',
        success:function (text) {
            res = text.rows
        }
    })
    for (var i = 0; i < res.length; i++) {
        obj = new Object()
        obj.title = res[i].occuphyexaclass_name
        obj.id = res[i].occuphyexaclassID
        obj.occuphyexaclassID = res[i].occuphyexaclassID
        obj.occuphyexaclass_name = res[i].occuphyexaclass_name
        data.push(obj)
    }
    return data
}
/**
 * 职业危害类别数据
 * @returns {any[]}
 */
function zywh_type() {
    var res = [];
    var data = new Array()
    var obj = new Object()
    $.ajax({
        url:'getZYB_OccuhazardClassList.action',
        data:'',
        type:'post',
        async:false,
        dataType:'json',
        success:function (text) {
            res = text.rows
        }
    })
    obj.title = '全部'
    obj.id = ''
    for (var i = 0; i < res.length; i++) {
        obj = new Object()
        obj.title = res[i].hazardclass_name
        obj.id = res[i].hazardclassID
        data.push(obj)
    }
    return data
}
/**
 * 因素和职业危害类别数据封装
 * @returns {Object}
 */
function yinsu_leibie() {
    var yinsu = zywhys()
    var leibie = zywh_type()
    var yinsu_class = new Array()
    var obj = new Object()
    var obj_Array = new Array()
    //子
    var obj_children = new Object()
    var obj_children_Array = new Array()
    //职业危害类别
    for (var i = 0; i < leibie.length; i++) {
        obj = new Object()
        obj.id = leibie[i].id
        obj.title = leibie[i].title
        obj.obj_children = new Array()

        //因素
        obj_children_Array = new Array()
        for (var j = 0; j < yinsu.length; j++) {
            if(leibie[i].id === yinsu[j].hazardclassID){
                obj_children = new Object()
                obj_children.hazard_code = yinsu[j].hazard_code
                obj_children.hazardclassID = yinsu[j].hazardclassID
                obj_children.hazard_name = yinsu[j].hazard_name
                obj_children.hazard_year = yinsu[j].hazard_year
                obj_children.occuphyexaclassid = yinsu[j].occuphyexaclassid
                obj_children.occuphyexaclass_name = yinsu[j].occuphyexaclass_name
                obj_children_Array.push(obj_children)
            }
        }
        obj.children = obj_children_Array
        obj_Array.push(obj)
    }
    return obj_Array
}
var hazard = new Array()    //缓存因素
/**
 * 因素职业危害类别数据封装显示
 */
function zywhys_html(){
    var yl = yinsu_leibie()
    var row_html = '<form  class="layui-form" id="yinsu_form" lay-filter="yinsu_form" action=""><table>'
    for (var j = 0; j < yl.length; j++) {
        if(yl[j].children.length <= 0){
            continue
        }
        //职业危害类别渲染dom
        row_html += '<tr><td colspan="4"><fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">'
                +'<legend>'+yl[j].title+'</legend></fieldset></td></tr><tr>'

        //因素数据渲染dom
        var t = yl[j].children
        for(var i = 0 ; i<t.length ; i++){
            var fla = 0
            for(var k = 0 ; k < hazard.length ;k++){
                if(hazard[k].hazard_code == t[i].hazard_code && hazard[k].occuphyexaclassid == $('input:radio[name="tj_lb"]:checked').val()){
                    fla = 1
                    if(hazard[k].status == 1){
                        fla = 2
                    }
                }
            }
            if(fla == 1){//人员已选中因素以选中状态显示
                row_html += '<td style="width:300px"  >'
                row_html +='<input type="checkbox" id="c'+i+'" checked  lay-skin="primary" lay-filter="c'+i+'" data-hazard_code="'+t[i].hazard_code+'"' +
                    ' data-hazard_name="'+t[i].hazard_name+'" data-hazard_year="'+t[i].hazard_year+'"     >'+t[i].hazard_name+'</td>'
            } else if(fla == 2){//人员已近保存元素禁止点击操作
                row_html += '<td style="width:300px"  >'
                row_html +='<input type="checkbox" id="c'+i+'"  disabled checked  lay-skin="primary" lay-filter="c'+i+'" data-hazard_code="'+t[i].hazard_code+'"' +
                    ' data-hazard_name="'+t[i].hazard_name+'" data-hazard_year="'+t[i].hazard_year+'"     >'+t[i].hazard_name+'</td>'
            } else {//因素以为选中状态显示
                row_html += '<td style="width:300px"  >'
                row_html +='<input type="checkbox" id="c'+i+'"  lay-skin="primary" lay-filter="c'+i+'" data-hazard_code="'+t[i].hazard_code+'"' +
                    ' data-hazard_name="'+t[i].hazard_name+'" data-hazard_year="'+t[i].hazard_year+'"     >'+t[i].hazard_name+'</td>'
            }
            if((i+1)%4==0){
                row_html += '</tr><tr>'
            }
        }
    }
    row_html += '</tr></table></form>'
    $('#zywhys_data').html(row_html)
    $('#yxxm').attr('checked',false)
    //layui.form.render({elem:'#yinsu_form'})
    //layuiform.render()
    layui.form.render('checkbox')
    //layui.form.render('select','form"] .layui-form-item[lay-filter="yinsu_form')
}

/**
 *缓存因素数据到hazard
 * @param ys_value 因素编码
 * @param ys_lb_value2 因素名称
 */
function ys_chebox(e,hazard_code,hazard_name,hazard_year){
    if($(e).is(':checked')){
        var obj  = new Object()
        var fla = 0
        debugger
        obj.id = createRandomId()//唯一标示
        obj.hazard_code = hazard_code   //职业危害因素编码
        obj.hazard_name = hazard_name   //职业危害因素名称
        obj.hazard_year = hazard_year   //危害年龄
        obj.status = 0                  //1代表以拥有的因素   0已选择的因素
        obj.occuphyexaclassid = $('input:radio[name="tj_lb"]:checked').val();  //职业体检类别id
        obj.occuphyexaclass_name = $('input:radio[name="tj_lb"]:checked').data('text') //体检类别名称
        for (var i = 0; i < hazard.length; i++) {
            if(hazard[i].hazard_code == hazard_code && hazard[i].occuphyexaclassid == $('input:radio[name="tj_lb"]:checked').val()){
                fla = 1
                break
            }
        }
        if(fla != 1){
            hazard.push(obj)
        }
    } else {
        for(var i = 0 ; i < hazard.length ; i++){
            if(hazard[i].hazard_code === hazard_code){
                hazard.splice(i,1)
            }
        }
    }
    console.log(hazard)
}

/**
 * 显示已选因素&所有因素
 * yx_zywhys_html已选因素
 * zywhys_html所有因素
 */
function zywhys_chebox(e) {
    if($(e).is(':checked')){
        yx_zywhys_html()
        //$.parser.parse() 重新解析easyui组件
    } else {
        zywhys_html()
        form.render()
    }
}
/**
 * 已选职业危害因素渲染dom
 */
function yx_zywhys_html(){
    //已选危害因素
    var t = hazard
    var row_html = '<table><tr><td><div style="float: left ;margin-right:330px">危害因素</div><div style="float: left ;margin-right:50px">危害年限</div>' +
        '<div style="float: left ;">体检类别</div></td><tr>'
    for(var i = 0 ; i<t.length ; i++){
        if(t[i].status == 1){
            row_html += '<td style="" ><div style="width:390px;float: left;padding-top:10px;margin-left: 20px" > ' +
                t[i].hazard_name+'&nbsp;&nbsp;&nbsp;&nbsp;</div>' +
                '<div style="float: left;">'+t[i].hazard_year+'&nbsp;&nbsp;年</div>' +
                '<div style="width:500px;float: left;margin-left: 50px" >'+t[i].occuphyexaclass_name+'</div></td>'
            row_html += '</tr><tr>'
        } else {
            row_html += '<td style="" ><div style="width:400px;float: left;padding-top:10px" ><i onclick="yx_ys_sc(\''+t[i].hazard_code+'\',\''+t[i].occuphyexaclassid+'\')" ' +
                'class="layui-icon layui-icon-close layui-bg-red" style="font-size:15px;"></i>  ' +
                t[i].hazard_name+'&nbsp;&nbsp;&nbsp;&nbsp;</div>' +
                '<div style="float: left;"><input type="text" style="width:20px;height:28px;" ' +
                'value="'+t[i].hazard_year+'" onchange="update_hazard_year(\''+t[i].hazard_code+'\',\''+t[i].occuphyexaclassid+'\',this,\''+t[i].id+'\')" class="textinput">年</div>' +
                '<div style="width:500px;float: left;margin-left: 50px" ' +
                '>'+yx_ys_select(''+t[i].hazard_code+'',''+t[i].occuphyexaclassid+'',this,t[i])+'</div></td>'
            row_html += '</tr><tr>'
        }
    }
    row_html += '</tr></table>'
    $('#zywhys_data').html(row_html)
    form.render()
    $.parser.parse()
}
var tj_lb_data = tj_type()
/**
 * 体检类别下拉框渲染dom
 */
function yx_ys_select(hazard_code,occuphyexaclassid,e,t_objet) {
    var ti_lb = tj_lb_data
    var html = '<select class="easyui-combobox" data-id="'+t_objet.id+'" data-occuphyexaclassid="'+occuphyexaclassid+'"' +
        ' data-hazard_code="'+hazard_code+'" data-occuphyexaclass_name="'+t_objet.occuphyexaclass_name+'"' +
        ' data-options="editable:false,onSelect:ys_ys_select_onSelect,' +
        'onLoadSuccess:ys_ys_select_success"' +
        ' name="state'+i+'" style="width:100%;height:35px">'
    for (var i = 0; i < ti_lb.length; i++) {
        html+=' <option value="'+ti_lb[i].id+'">'+ti_lb[i].title+'</option>'
    }
    return html+='</select>  '
}

/**
 * 体检类别发生变化后，更新hazard
 * @param newValue
 * @param oldValue
 */
function ys_ys_select_onSelect(newValue,oldValue) {
    var hazard_code = $(this).data('hazard_code')
    var id = $(this).data('id')
    var occuphyexaclass_name = $(this).data('occuphyexaclass_name')
    for (var i = 0; i < hazard.length; i++) {
        if(hazard[i].id == id){
            hazard[i].occuphyexaclassid = newValue.value
            hazard[i].occuphyexaclass_name = $(this).combobox('getText')
            //alert($(this).combobox('getText'))
        }
    }
    console.log(hazard)
}
function ys_ys_select_success() {
    $(this).combobox('setValue',$(this).data('occuphyexaclassid'))
}
/**
 * 删除已选择的因素
 * @param hazard_code
 */
function yx_ys_sc(hazard_code,occuphyexaclassid) {
    for(var  i = 0 ; i < hazard.length ; i++){
        if(hazard[i].hazard_code == hazard_code && hazard[i].occuphyexaclassid == occuphyexaclassid){
            hazard.splice(i,1)
            break
        }
    }
    yx_zywhys_html()
}
function createRandomId() {
    return (Math.random()*10000000).toString(16).substr(0,4)+'-'+(new Date()).getTime()+'-'+Math.random().toString().substr(2,5);
}
/**
 * 获取人员已有的因素
 */
function examhazardList() {
    $.ajax({
        url:'examoccuhazardfactorslist.action',
        data:{rows:99999,exam_num:$('#zyb_exam_num').val()},
        type:'post',
        dataType:'json',
        async:false,
        success:function (text) {
            res = text.rows
            for (var j = 0; j < res.length; j++) {
                var obj  = new Object()
                var fla = 0
                obj.hazard_code = res[j].hazard_code               //职业危害因素编码
                obj.hazard_name = res[j].hazard_name               //职业危害因素名称
                obj.hazard_year = res[j].hazard_year               //危害年龄
                obj.occuphyexaclassid = res[j].occuphyexaclassid               //职业体检类别ID
                obj.occuphyexaclass_name = res[j].occuphyexaclass_name//职业体检名称
                obj.status = 1
                obj.id = createRandomId()  //生成唯一标示
                for (var i = 0; i < hazard.length; i++) {
                    if(hazard.hazard_code === res[j].hazard_code){
                        fla = 1
                        break
                    }
                }
                if(fla != 1){
                    hazard.push(obj)
                }
            }
        }
    })
    console.log("人员已有因素",hazard)
}

function update_hazard_year(hazard_code,occuphyexaclassid,e,id) {
    for(var  i = 0 ; i < hazard.length ; i++){
        if(hazard[i].id == id){
            hazard[i].hazard_year = $(e).val()
            break
        }
    }
}
/**
 * 人员保存职业危害
 * @returns {*}
 */
function hazaard_save() {
    debugger
    var obj = new Object()
    var hazard_list = new Array()
    for(var i = 0 ; i < hazard.length ; i++){
        if(hazard[i].status != 1){
            //obj = new Object()
            // obj.hazard_code = hazard[i].hazard_code   //职业危害因素编码
            // obj.hazard_name = hazard[i].hazard_name   //职业危害因素名称
            // obj.hazard_year = hazard[i].hazard_year   //危害年龄
            // obj.occuphyexaclassid = hazard[i].occuphyexaclassid //体检类别
            // obj.occuphyexaclass_name = hazard[i].occuphyexaclass_name
            hazard_list.push(hazard[i])
        }
    }
    console.log(hazard_list)
    if(hazard_list.length<=0){
        layer.open({
            title: '提示信息'
            ,content: '请选择职业危害因素'
        });
        return
    }
    var hazard_list_f = hazard
    var fla = 0
    var msg=''
    for (var i = 0; i < hazard_list_f.length-1; i++) {
        if(hazard_list_f[i].hazard_code == hazard_list_f[i+1].hazard_code &&
            hazard_list_f[i].occuphyexaclassid == hazard_list_f[i+1].occuphyexaclassid){
            fla = 1
            //alert('内容重复')
            msg = hazard_list_f[i].hazard_name+'--'+hazard_list_f[i].occuphyexaclass_name
            break
        }
    }
    if(fla == 1){
        layer.open({
            title: '重复记录'
            ,content: msg
        })
        return
    }
    var msg = "";
    for(var i=0;i<hazard_list_f.length;i++){
        if(i < hazard_list_f.length-1 &&
            hazard_list_f[i].occuphyexaclass_name!=hazard_list_f[i+1].occuphyexaclass_name
        ){
            msg+=hazard_list_f[i].occuphyexaclass_name+"</br>"
        }
        if(i == hazard_list_f.length-2 &&
            hazard_list_f[i].occuphyexaclass_name!=hazard_list_f[i+1].occuphyexaclass_name)
        {
            msg+=hazard_list_f[i+1].occuphyexaclass_name+"</br>"
        }
    }
    if(msg){
        layer.open({
            title: '错误提示',
            content: '出现多个体检类别<br/>'+msg+'</br>'+'只允许选择一种体检类别,请核对！'
        })
        return
    }
    $.ajax({
        url:'zyboccwhyslbadddo.action',
        type:'post',
        data:{hazard_list:JSON.stringify(hazard_list),
              arch_num:$('#zyb_arch_num').val(),
              exam_num:$('#zyb_exam_num').val()
        },
        success:function (text) {
            if(text.split("-")[0]=="ok"){
                layer.msg(text.split("-")[1]);
                hazard = new Array()
                examhazardList()
                if($('#yxxm').is(':checked')){
                    yx_zywhys_html()
                } else {
                    zywhys_html()
                    form.render()
                }
                var _parentWin =  window.opener ;
                _parentWin.zybsetselectListGrid();
                _parentWin.djtcustChangItemListGrid();
                _parentWin.gettotalinfo();
                _parentWin.getoccuhazardfactorsGrid();
            }else{
            }
        },
        error:function () {
            layer.open({
                title: '提示信息',
                content:'操作失败'
            })
        }
    })
}

