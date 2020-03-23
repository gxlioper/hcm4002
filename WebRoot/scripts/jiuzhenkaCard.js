/*--------------------------------------------------|
|就诊卡                							             |
|---------------------------------------------------|
| zr                                             |
|--------------------------------------------------|
|	J001黄冈就诊卡										|
|--------------------------------------------------|
*/
var comtype;
function readjiuzhenka(comtype){
	if(comtype=="J001"){
		return readCard_HG();
	}
}

//黄冈就诊卡
function readCard_HG(){
	var data=GWI_IC20AProj.Get_VisitNo();
	data=JSON.parse(data);
	return data;
}



