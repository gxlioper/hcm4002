$(document).ready(function () {
showgntree();
//showmercdepttree();
//showresourcetree();
});

function showgntree()
{
   var roleid = $("#roleid").val();
    $("#dv_system_menus").treeview({            
       url:"rolegnshow.action?id="+roleid,  
       showcheck: true,          
       animate:true,             
       onnodeclick:false
    }); 
}

function showmercdepttree()
{
   var roleid = $("#roleid").val();
   $("#dv_mercacc").treeview({            
       url:"getMerccdept.action?roleid="+roleid,           
       showcheck: true,          
       animate:true
    }); 
}

function showresourcetree()
{
   var roleid = $("#roleid").val();
   $("#dv_resources").treeview({            
       url:"getZyzbtree.action?roleid="+roleid,          
       showcheck: true,          
       animate:true,             
       onnodeclick:false
    }); 
}

function load_Menus() {
    change_Menus($("#system_menus"));
}


function change_Menus(selectID) {
    $(".switch").children(".active").removeClass();
    $(".scroll").children("div").hide();
    $(selectID).removeClass();
    $(selectID).addClass("active");
    $("#dv_" + $(selectID).attr("id")).show();
}