package com.hjw.Bean.HJWWX;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.hjw.Bean.ResMessage;

public class RetMessage {

	private String retCode;
	private String retMsg="";
	private String retCount="";
	private List<Topic> topicList=new ArrayList<>();
	
	public String getRetCode() {
		return retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public String getRetCount() {
		return retCount;
	}
	public List<Topic> getTopicList() {
		return topicList;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public void setRetCount(String retCount) {
		this.retCount = retCount;
	}
	public void setTopicList(List<Topic> topicList) {
		this.topicList = topicList;
	}
	
	public static void main(String[] args) {
		String str = "{\"topicList\":[{\"topic\":\"（1）您精力充沛吗？\",\"options\":\"2\"},{\"topic\":\"（2）您容易疲乏吗？*\",\"options\":\"2\"},{\"topic\":\"（3）您说话声音低弱无力吗？*\",\"options\":\"1\"},{\"topic\":\"（4）您感到闷闷不乐、情绪低沉吗？*\",\"options\":\"1\"},{\"topic\":\"（5）您比普通人耐受不了冰冷（冬天的寒冷，夏天的冷空调、电扇）吗？*\",\"options\":\"1\"},{\"topic\":\"（6）您能适应外界自然和社会环境的变化吗？\",\"options\":\"2\"},{\"topic\":\"（7）您容易失眠吗？*\",\"options\":\"1\"},{\"topic\":\"（8）您容易忘事（健忘）吗？*\",\"options\":\"2\"},{\"topic\":\"（1）您容易疲乏吗？\",\"options\":\"2\"},{\"topic\":\"（2）您容易气短（呼吸急促，接不上气）吗？\",\"options\":\"2\"},{\"topic\":\"（3）您容易心慌吗？\",\"options\":\"1\"},{\"topic\":\"（4）您容易头晕或站起时晕眩吗？\",\"options\":\"2\"},{\"topic\":\"（5）您比他人容易患感冒吗？\",\"options\":\"1\"},{\"topic\":\"（6）您喜欢安静、懒得说话吗？\",\"options\":\"2\"},{\"topic\":\"（7）您说话声音低弱无力吗？\",\"options\":\"1\"},{\"topic\":\"（8）您活动量稍大就容易出虚汗吗？\",\"options\":\"1\"},{\"topic\":\"（1）您手脚发凉吗？\",\"options\":\"2\"},{\"topic\":\"（2）您胃脘部、背部或腰膝部怕冷吗？\",\"options\":\"1\"},{\"topic\":\"（3）您感到怕冷，衣服比他人穿得多吗？\",\"options\":\"2\"},{\"topic\":\"（4）您容易头晕或站起时晕眩吗？\",\"options\":\"2\"},{\"topic\":\"（5）您比他人容易患感冒吗？\",\"options\":\"1\"},{\"topic\":\"（6）您吃（喝）凉的东西会感到不舒适或许怕吃（喝）凉东西吗？\",\"options\":\"1\"},{\"topic\":\"（7）你受凉或吃（喝）凉的东西后，容易腹泻（拉肚子）吗？\",\"options\":\"2\"},{\"topic\":\"（1）您感到手脚心发热吗？\",\"options\":\"1\"},{\"topic\":\"（2）您觉得身体、脸上发热吗？\",\"options\":\"1\"},{\"topic\":\"（3）您皮肤或口唇干吗？\",\"options\":\"2\"},{\"topic\":\"（4）您口唇的颜色比普通人红吗？\",\"options\":\"3\"},{\"topic\":\"（5）您容易便秘或大便干燥吗？\",\"options\":\"1\"},{\"topic\":\"（6）您面部两颧潮红或偏红吗？\",\"options\":\"1\"},{\"topic\":\"（7）您感到眼睛干涩吗？\",\"options\":\"2\"},{\"topic\":\"（8）您感到口干咽燥、总想喝水吗？\",\"options\":\"2\"},{\"topic\":\"（1）您感到胸闷或腹部胀满吗？\",\"options\":\"1\"},{\"topic\":\"（2）您感到身体不轻松或不爽快吗？\",\"options\":\"1\"},{\"topic\":\"（3）您腹部肥满松软吗？\",\"options\":\"1\"},{\"topic\":\"（4）您有额部油脂分泌多的现象吗？\",\"options\":\"3\"},{\"topic\":\"（5）您上眼睑比他人肿（上眼睑轻微隆起的景象）吗？\",\"options\":\"1\"},{\"topic\":\"（6）您嘴里有黏黏的感觉吗？\",\"options\":\"2\"},{\"topic\":\"（7）您平常痰多，特别是咽喉部总感到有痰堵着吗？\",\"options\":\"2\"},{\"topic\":\"（8）您舌苔厚腻或有舌苔厚厚的感觉吗？\",\"options\":\"1\"},{\"topic\":\"（1）您面部或鼻部有油腻感或者油亮发光吗？\",\"options\":\"3\"},{\"topic\":\"（2）你容易生痤疮或疮疖吗？\",\"options\":\"3\"},{\"topic\":\"（3）您感到口苦或嘴里有异味吗？\",\"options\":\"1\"},{\"topic\":\"（4）您大便黏滞不爽、有解不尽的觉得吗？\",\"options\":\"1\"},{\"topic\":\"（5）您小便时尿道有发热感、尿色浓（深）吗？\",\"options\":\"1\"},{\"topic\":\"（6）您带下色黄（白带颜色发黄）吗？（限女性答复）\",\"options\":\"3\"},{\"topic\":\"（1）您的皮肤在不知不觉中会呈现青紫瘀斑（皮下出血）吗？\",\"options\":\"1\"},{\"topic\":\"（2）您两颧部有纤细红丝吗？\",\"options\":\"1\"},{\"topic\":\"（3）您身体上有哪里疼痛吗？\",\"options\":\"2\"},{\"topic\":\"（4）您面色晦黯或容易出现褐斑吗？\",\"options\":\"2\"},{\"topic\":\"（5）您容易有黑眼圈吗？\",\"options\":\"1\"},{\"topic\":\"（6）您容易忘事（健忘）吗？\",\"options\":\"3\"},{\"topic\":\"（7）您口唇颜色偏黯吗？\",\"options\":\"2\"},{\"topic\":\"（1）您感到闷闷不乐、情绪低落吗？\",\"options\":\"2\"},{\"topic\":\"（2）您容易精神紧张、焦虑不安吗？\",\"options\":\"2\"},{\"topic\":\"（3）您多愁善感、感情脆弱吗？\",\"options\":\"2\"},{\"topic\":\"（4）您容易感到惧怕或受到惊吓吗？\",\"options\":\"2\"},{\"topic\":\"（5）您胁肋部或乳房胀痛吗？\",\"options\":\"2\"},{\"topic\":\"（6）您无缘无故叹息吗？\",\"options\":\"2\"},{\"topic\":\"（7）您咽喉部有异物感，且吐之不出、咽之不下吗？\",\"options\":\"1\"},{\"topic\":\"（1）您不感冒时也会打喷嚏吗？\",\"options\":\"1\"},{\"topic\":\"（2）您不感冒时也会鼻塞、流鼻涕吗？\",\"options\":\"1\"},{\"topic\":\"（3）您有因季节变化、温度变化或异味等缘由而咳喘的现象吗？\",\"options\":\"1\"},{\"topic\":\"（4）您容易过敏（对药物、食物、气味、花粉或在时节交替、气候变化时）吗？\",\"options\":\"1\"},{\"topic\":\"（5）您的皮肤容易起荨麻疹（风团、风疹块、风疙瘩）吗？\",\"options\":\"0\"},{\"topic\":\"（6）您的皮肤因过敏出现过紫癜（紫红色瘀点、瘀斑）吗？\",\"options\":\"0\"},{\"topic\":\"（7）您的皮肤一抓就红，并呈现抓痕吗？\",\"options\":\"2\"}],\"retCode\":\"0\",\"retMsg\":\"取得成功!\",\"retCount\":\"\"}";
		System.out.println(str);
		RetMessage ret = new Gson().fromJson(str, RetMessage.class);
//		jsonobject = JSONObject.fromObject(res.getResbody());
//		RetMessage ret = (RetMessage)JSONObject.toBean(jsonobject, RetMessage.class);
		
		System.out.println(ret.getTopicList().get(0).getTopic());
	}
}
