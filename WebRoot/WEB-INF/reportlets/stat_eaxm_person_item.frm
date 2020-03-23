<?xml version="1.0" encoding="UTF-8"?>
<Form xmlVersion="20141222" releaseVersion="7.1.1">
<TableDataMap>
<TableData name="dict_dept" class="com.fr.data.impl.DBTableData">
<Parameters/>
<Attributes maxMemRowCount="-1"/>
<Connection class="com.fr.data.impl.NameDatabaseConnection">
<DatabaseName>
<![CDATA[peis]]></DatabaseName>
</Connection>
<Query>
<![CDATA[select id as dep_id, dep_name from department_dep 
where id not in (52, 56) and isActive='Y'
order by seq_code]]></Query>
</TableData>
<TableData name="dict_item" class="com.fr.data.impl.DBTableData">
<Parameters>
<Parameter>
<Attributes name="layer1"/>
<O>
<![CDATA[22]]></O>
</Parameter>
</Parameters>
<Attributes maxMemRowCount="-1"/>
<Connection class="com.fr.data.impl.NameDatabaseConnection">
<DatabaseName>
<![CDATA[peis]]></DatabaseName>
</Connection>
<Query>
<![CDATA[select id as charge_item_id, item_name from charging_item 
where dep_id=${layer1} and isActive='Y'
order by item_seq]]></Query>
</TableData>
<TableData name="ds_stat_data" class="com.fr.data.impl.DBTableData">
<Parameters>
<Parameter>
<Attributes name="company_id"/>
<O>
<![CDATA[]]></O>
</Parameter>
<Parameter>
<Attributes name="p1"/>
<O>
<![CDATA[11]]></O>
</Parameter>
<Parameter>
<Attributes name="endDate"/>
<O>
<![CDATA[2015-12-11]]></O>
</Parameter>
<Parameter>
<Attributes name="beginDate"/>
<O>
<![CDATA[2015-12-06]]></O>
</Parameter>
</Parameters>
<Attributes maxMemRowCount="-1"/>
<Connection class="com.fr.data.impl.NameDatabaseConnection">
<DatabaseName>
<![CDATA[peis]]></DatabaseName>
</Connection>
<Query>
<![CDATA[select * from (
select c.arch_num, e.exam_num, c.user_name as name, c.sex, c.birthday, e.age, 
  IsNull(v.company_id,0) as company_id, com_name=(case when v.com_name is null then '个人' else v.com_name end), 
  dbo.fun_GetSetNameByExamInfoId(e.id) as SetName, e.phone, e.address, e.introducer
from exam_info e
inner join customer_info c on c.id=e.customer_id
left join v_comp_batch_group v on v.group_id=e.group_id 
where e.is_Active='Y' and e.is_Active='Y'
  and e.join_date>='${beginDate}' and e.join_date<='${endDate}'
  and exists (select eci.charge_item_id from examinfo_charging_item eci 
   where eci.examinfo_id=e.id and eci.charge_item_id=${p1} and eci.exam_status='Y')
  ${if(len(sex)=0, "", " and c.sex='" + sex + "'")}
) as A where 1=1 
${if(len(company_id)=0, "", " and company_id in (" + company_id + ")")}]]></Query>
</TableData>
<TableData name="dict_company" class="com.fr.data.impl.DBTableData">
<Parameters/>
<Attributes maxMemRowCount="-1"/>
<Connection class="com.fr.data.impl.NameDatabaseConnection">
<DatabaseName>
<![CDATA[peis]]></DatabaseName>
</Connection>
<Query>
<![CDATA[select 0 as com_id, '个人' as com_name 
union all
select id as com_id, com_name from company_info where is_Active='Y']]></Query>
</TableData>
</TableDataMap>
<Layout class="com.fr.form.ui.container.WBorderLayout">
<WidgetName name="form"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="0" left="0" bottom="0" right="0"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<LCAttr vgap="0" hgap="0" compInterval="0"/>
<NorthAttr size="78"/>
<North class="com.fr.form.ui.container.WParameterLayout">
<WidgetName name="para"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="1" left="1" bottom="1" right="1"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<Background name="ColorBackground" color="-1247236"/>
<LCAttr vgap="0" hgap="0" compInterval="0"/>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.Label">
<WidgetName name="label2"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<widgetValue>
<O>
<![CDATA[性别：]]></O>
</widgetValue>
<LabelAttr verticalcenter="true" textalign="0" autoline="true"/>
<FRFont name="SimSun" style="0" size="72"/>
<border style="0" color="-723724"/>
</InnerWidget>
<BoundsAttr x="432" y="11" width="54" height="21"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.ComboBox">
<WidgetName name="sex"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Dictionary class="com.fr.data.impl.CustomDictionary">
<CustomDictAttr>
<Dict key="" value="全部"/>
<Dict key="男" value="男"/>
<Dict key="女" value="女"/>
</CustomDictAttr>
</Dictionary>
<widgetValue>
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[=]]></Attributes>
</O>
</widgetValue>
</InnerWidget>
<BoundsAttr x="489" y="11" width="80" height="21"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.parameter.FormSubmitButton">
<WidgetName name="Search"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[查询]]></Text>
<Hotkeys>
<![CDATA[enter]]></Hotkeys>
</InnerWidget>
<BoundsAttr x="606" y="12" width="68" height="37"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.Label">
<WidgetName name="LabelbeginDate"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<widgetValue>
<O>
<![CDATA[开始时间:]]></O>
</widgetValue>
<LabelAttr verticalcenter="true" textalign="0" autoline="true"/>
<FRFont name="SimSun" style="0" size="72"/>
<border style="0" color="-723724"/>
</InnerWidget>
<BoundsAttr x="39" y="11" width="80" height="21"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.DateEditor">
<WidgetName name="beginDate"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<DateAttr/>
<widgetValue>
<O t="Date">
<![CDATA[1449331200000]]></O>
</widgetValue>
</InnerWidget>
<BoundsAttr x="119" y="11" width="99" height="21"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.Label">
<WidgetName name="LabelendDate"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<widgetValue>
<O>
<![CDATA[结束时间:]]></O>
</widgetValue>
<LabelAttr verticalcenter="true" textalign="0" autoline="true"/>
<FRFont name="SimSun" style="0" size="72"/>
<border style="0" color="-723724"/>
</InnerWidget>
<BoundsAttr x="232" y="11" width="80" height="21"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.DateEditor">
<WidgetName name="endDate"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<DateAttr/>
<widgetValue>
<O t="Date">
<![CDATA[1449763200000]]></O>
</widgetValue>
</InnerWidget>
<BoundsAttr x="312" y="11" width="99" height="21"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.Label">
<WidgetName name="Labelcompany_id"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<widgetValue>
<O>
<![CDATA[单位名称:]]></O>
</widgetValue>
<LabelAttr verticalcenter="true" textalign="0" autoline="true"/>
<FRFont name="SimSun" style="0" size="72"/>
<border style="0" color="-723724"/>
</InnerWidget>
<BoundsAttr x="40" y="48" width="80" height="21"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.ComboCheckBox">
<WidgetName name="company_id"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Dictionary class="com.fr.data.impl.TableDataDictionary">
<FormulaDictAttr kiName="com_id" viName="com_name"/>
<TableDataDictAttr>
<TableData class="com.fr.data.impl.NameTableData">
<Name>
<![CDATA[dict_company]]></Name>
</TableData>
</TableDataDictAttr>
</Dictionary>
<widgetValue>
<O>
<![CDATA[]]></O>
</widgetValue>
<RAAttr/>
</InnerWidget>
<BoundsAttr x="120" y="48" width="192" height="21"/>
</Widget>
<Sorted sorted="false"/>
<Display display="true"/>
<DelayDisplayContent delay="true"/>
<Position position="0"/>
<Design_Width design_width="960"/>
<MobileWidgetList>
<Widget widgetName="beginDate"/>
<Widget widgetName="endDate"/>
<Widget widgetName="sex"/>
<Widget widgetName="company_id"/>
</MobileWidgetList>
<WidgetNameTagMap>
<NameTag name="company_id" tag="company_id:"/>
<NameTag name="sex" tag="性别："/>
<NameTag name="endDate" tag="endDate:"/>
<NameTag name="beginDate" tag="beginDate:"/>
</WidgetNameTagMap>
</North>
<Center class="com.fr.form.ui.container.WFitLayout">
<WidgetName name="body"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="4" left="2" bottom="3" right="2"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="宋体" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Background name="ColorBackground" color="-2299652"/>
<Alpha alpha="1.0"/>
</Border>
<Background name="ColorBackground" color="-2299652"/>
<LCAttr vgap="0" hgap="0" compInterval="3"/>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.TreeEditor">
<WidgetName name="p1"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<TreeAttr selectLeafOnly="true"/>
<TreeNodeAttr>
<Dictionary class="com.fr.data.impl.TableDataDictionary">
<FormulaDictAttr kiName="dep_id" viName="dep_name"/>
<TableDataDictAttr>
<TableData class="com.fr.data.impl.NameTableData">
<Name>
<![CDATA[dict_dept]]></Name>
</TableData>
</TableDataDictAttr>
</Dictionary>
</TreeNodeAttr>
<TreeNodeAttr>
<Dictionary class="com.fr.data.impl.TableDataDictionary">
<FormulaDictAttr kiName="charge_item_id" viName="item_name"/>
<TableDataDictAttr>
<TableData class="com.fr.data.impl.NameTableData">
<Name>
<![CDATA[dict_item]]></Name>
</TableData>
</TableDataDictAttr>
</Dictionary>
</TreeNodeAttr>
<isAutoBuild autoBuild="false"/>
<widgetValue/>
</InnerWidget>
<BoundsAttr x="0" y="0" width="135" height="540"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.container.WTitleLayout">
<WidgetName name="report0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="0" left="0" bottom="0" right="0"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<LCAttr vgap="0" hgap="0" compInterval="0"/>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.ElementCaseEditor">
<WidgetName name="report0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="1" left="1" bottom="1" right="1"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<FormElementCase>
<ReportPageAttr>
<HR/>
<FR/>
<HC/>
<FC/>
</ReportPageAttr>
<ColumnPrivilegeControl/>
<RowPrivilegeControl/>
<RowHeight defaultValue="723900">
<![CDATA[952500,914400,723900,723900,723900,723900,723900,723900,723900,723900,723900]]></RowHeight>
<ColumnWidth defaultValue="2743200">
<![CDATA[2095500,3238500,2743200,2743200,2743200,3962400,4076700,4343400,2743200,7810500,2743200]]></ColumnWidth>
<CellElementList>
<C c="0" r="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="1" r="0" cs="9" s="0">
<O>
<![CDATA[体检人数（按项目）统计]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="0" r="1" s="1">
<O>
<![CDATA[序号]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="1" r="1" s="2">
<O>
<![CDATA[档案号]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="2" r="1" s="2">
<O>
<![CDATA[姓名]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="3" r="1" s="2">
<O>
<![CDATA[性别]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="4" r="1" s="2">
<O>
<![CDATA[年龄]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="5" r="1" s="2">
<O>
<![CDATA[单位]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="6" r="1" s="2">
<O>
<![CDATA[套餐]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="7" r="1" s="2">
<O>
<![CDATA[电话]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="8" r="1" s="2">
<O>
<![CDATA[介绍人]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="9" r="1" s="2">
<O>
<![CDATA[地址]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="0" r="2" cs="2" s="3">
<O>
<![CDATA[合计（人次）：]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="2" r="2" cs="8" s="4">
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[=count(B4)]]></Attributes>
</O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="0" r="3" s="5">
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[=seq()]]></Attributes>
</O>
<PrivilegeControl/>
<Expand leftParentDefault="false" left="B4"/>
</C>
<C c="1" r="3" s="5">
<O t="DSColumn">
<Attributes dsName="ds_stat_data" columnName="arch_num"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
<C c="2" r="3" s="5">
<O t="DSColumn">
<Attributes dsName="ds_stat_data" columnName="name"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
<C c="3" r="3" s="5">
<O t="DSColumn">
<Attributes dsName="ds_stat_data" columnName="sex"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
<C c="4" r="3" s="5">
<O t="DSColumn">
<Attributes dsName="ds_stat_data" columnName="age"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
<C c="5" r="3" s="6">
<O t="DSColumn">
<Attributes dsName="ds_stat_data" columnName="com_name"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
<C c="6" r="3" s="5">
<O t="DSColumn">
<Attributes dsName="ds_stat_data" columnName="SetName"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
<C c="7" r="3" s="5">
<O t="DSColumn">
<Attributes dsName="ds_stat_data" columnName="phone"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
<C c="8" r="3" s="6">
<O t="DSColumn">
<Attributes dsName="ds_stat_data" columnName="introducer"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
<C c="9" r="3" s="6">
<O t="DSColumn">
<Attributes dsName="ds_stat_data" columnName="address"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
</CellElementList>
<ReportAttrSet>
<ReportSettings headerHeight="0" footerHeight="0">
<PaperSetting/>
</ReportSettings>
</ReportAttrSet>
</FormElementCase>
<StyleList>
<Style horizontal_alignment="0" imageLayout="1">
<FRFont name="SimSun" style="1" size="96"/>
<Background name="NullBackground"/>
<Border/>
</Style>
<Style horizontal_alignment="0" imageLayout="1">
<FRFont name="SimSun" style="1" size="88"/>
<Background name="ColorBackground" color="-1181953"/>
<Border>
<Top style="1"/>
<Bottom style="1"/>
<Left style="1"/>
<Right style="1"/>
</Border>
</Style>
<Style horizontal_alignment="0" imageLayout="1">
<FRFont name="SimSun" style="1" size="88"/>
<Background name="ColorBackground" color="-1181956"/>
<Border>
<Top style="1"/>
<Bottom style="1"/>
<Left style="1"/>
<Right style="1"/>
</Border>
</Style>
<Style horizontal_alignment="4" imageLayout="1">
<FRFont name="SimSun" style="1" size="80" foreground="-65536"/>
<Background name="NullBackground"/>
<Border>
<Top style="1"/>
<Bottom style="1"/>
<Left style="1"/>
<Right style="1"/>
</Border>
</Style>
<Style horizontal_alignment="2" imageLayout="1">
<FRFont name="SimSun" style="1" size="80" foreground="-65536"/>
<Background name="NullBackground"/>
<Border>
<Top style="1"/>
<Bottom style="1"/>
<Left style="1"/>
<Right style="1"/>
</Border>
</Style>
<Style horizontal_alignment="0" imageLayout="1">
<FRFont name="SimSun" style="0" size="80"/>
<Background name="NullBackground"/>
<Border>
<Top style="1"/>
<Bottom style="1"/>
<Left style="1"/>
<Right style="1"/>
</Border>
</Style>
<Style imageLayout="1">
<FRFont name="SimSun" style="0" size="80"/>
<Background name="NullBackground"/>
<Border>
<Top style="1"/>
<Bottom style="1"/>
<Left style="1"/>
<Right style="1"/>
</Border>
</Style>
</StyleList>
<showToolbar showtoolbar="true"/>
<IM>
<![CDATA[eQR;^Pkj(*7^FkVZu%-=LCUA*<[o"rkTK1.+m29_cIEbj;km/<P)9ml;HCpSK?-ljZkQ;.c8
6uiWCb+'_9Df#:sCf2C:DO$IGX_mms(GmiO\LT]A!(FKS2</^&4J3=_I,&p_d2"K?ne:$0GrS
d!?D$VCO>k]A(eiJuE4atuTXNfSUUZmNadk'U,kkX]Ah=!cIA_[qsXfQlZAf6$k:6@%gS)r9')
7rY8h_hW,%:3ig@?Xl"h*AEnhn&JTn_mpe4*S?.+o<nEL!\=N?,qhS?*-Q/?5bH.\>#:B9j(
:.'R\hD$MR%9"n&_^mE95h?hj0a#_X4<F+;QX90%uWqUabnX94Ou_j]A5R1"<jC01^mJ"1mik
b/q<j8&3_^N:i!P\$6=A!#))#f:C(ORU$R\Z6akF_XW3VmbF"("N!q)el$:;-R$lII0Y`f1>
^b$@;Q(-#%e-AceE$%rBZF(]A,3\.o>kq9QP=8BP]A\aNAKA6cpu?s0]A\1pFX>mnZ6QAo&$AYl
XF9%.j1>;DsK@/1P:KY752dX%sac_>N!7.`FJrSF3=-$n[0Vt2$m$Rs7BBSdt3UiNjiiua<`
%iTU8%(Y-QK4lF.IMa0r+/8I&u%iT"sFm,-UrY_.0:]ALdd,>'5B6++Dhc[^R<k#h[KtV"Xgo
CTVCaZTp"!1I*-G-nJ22,*<&<AQ<\pqp*WM:h@OuG2e]A;]AWn7GaG^!kCkV]ABOkN:SMPb$hDO
h+k#\\Jl>CV4^!"\$r=!"Z=4FFj+3SK<\?(S0EM'YCW`84]Ae2hcH=6K>]ADJq6M7%o;2a&=81
Y)dH2$M=%5.A4_:l?4W91e[L.kJHU3nuX_DUr#`,S6)8fi2"=6'9NMjJjI!.XfcG8\t$R>_l
4_6Di`_2#N^JUcTq!AdjakUOgs)B3d.-i,fhN@q\dVd.OTm-&<1dt*))rnuZ?T4&sT"3Y7[*
>fUG<NS-EMJT-^cK[2=[?W\3O_Z=_c3#k4,7HkOGrq<IEC%ZKSX#q`\ZSVD_jJ-Hf6E/$b#\
`-q7OC()@FIJ'<5d5@Nr3c>BE6c2Q;uo?.K=5Ri0<[kF4M\&^/BiDQ"/e',"@0*ebW:BLB(2
'0o=D^GHcoN=:)C'G5.Yh-k1u+@5HT<)%)A"W(GLN"4STIlP%:^!UtDUADDgdtSP)mpDi-EO
1XLm#t3<<K='RXZmup#YDqIOBF7W8tG]Ac!7<.%&N1`QoZU#IDnB0q]AaEk4XJ\[)T4n]A1_<?T
hR;(0("C:Z)cF`lg-:dgH\/.9m<S6Je+S7Oj\^oO(*=-LNiEdBOD3CPV9IraM''7qfaKpGRd
;2&.?L_6HHiN4Od>GgV/r0;D[S9fX]AS.XR5]A[:nJYQV5$"<KSIT6ScR.?a!R^.T6&O\A:Js,
LhSIm;seqC]AQgdBrK5DFpljaH@bHaCtJL"HN><eg2AD@b<InkM;+HHT10#o!lS%-DZmDLJZU
TMZZ)$YZQqZ+5/Il1s[iaTWBPQSm&!<7k7ci1dt(&]AaPY?u#DP4XdM,k05obB[t,e[[@QcG1
PTeeP(0^RLSjT((*dNg>Rpm#n2<tH8Yjti2P&V/Tf[!#'X;BP&_j_bWC1?4%d`PBnP?4oLI>
u\fjSVKR$]AH:4K[uiac_LGBE]A(Z.5^?#HsDb<80eUoiosd:kC9UB+G@>dLM+,PGUE$9=?C#l
nim9S1;KT/kW7?(+?aYRSHTok1uVuYJ(`R:@J'VbQ8V?D%2Xg[%8+lLaW[Te..=.?,o\"QG"
Qs$2Y/.VRHAZ(V&6<]A/=cmmKIbE@p<\f3eolSb+:d90blV>T##Dk4$Eh;/KFU*.t^t$[["Jr
Yad:^:/,$R*f4kLW[/j>$-76-#g@YnLn%*_O8VZ[S"@:*p7X[l>cOkEFp2%7ePJ$Sp>mn$bh
2E+g!cGqpPK'TPZ&rJD2D+nJgR/:4\iF(=K"F$3mGLEePX(RTQ2NVijsHbSli/_WfcM/hVYm
,pSNqI\c\k5fs,ALqBBr=*4U)Z<;Ma!)_?:4`Dop_>cat!c+(:YjPk]A\1+l+fO&I[oZ3cosJ
W*9=2Z@8<3\1Lgbn1n1h'+;3L1L#7%VFfL'HhCTcd..b#4<f14?,_t&1I9*VCeo,C[)V35O>
F'_EYM$B@u9K73O@#3;jY-(36!l2;XO,EH(0.m3`fgO4l5@-[Ku7ddi@b7C_#?,]Aa)'d2rT(
o,uUgh2.I'T$fOMEGWN/gUrl>.aYRW-E<;:5n6-N<<OQLgM3]A@:;0N0la6;]A?u$V6bURp#I^
#noEnfbCm!e(6.SQcbb:2_>h)LS3lcHmf`Q9<+k6oC6155F<E2T<P0VpXii4$k&9dEk&@X7`
Nl^sq0NS@<h/I7<;W)E^WC)%JdRd7Ko8;S2<*:cb[YBNXt@3^>mY+"3^Q"\8T[Vo?d:3/TeE
?Z]A`bA'cMGo`A\31c?]A42JuKYTS(D*!qV(cPLVtBM/mP]A1e,uL5fP:"PUa>Cu_!:Wk6eP\I-
,u1>S4'OfC:K_u>@f<a"l,?mNa8gtBO&(:GCTrj$Lbr3]A7nFUDlWicIglg0W>?b=BB_KrN+a
iJB>-48h#'1$/0om3oHD"Xg/losLC7+`RU_@mA"Gs!`]AGC2P$fj1WS),jOLYdpfgYad8?\s0
;L<mV,7";q`jUHZ5ht0]AGT.OM:Ccd67:F6j5\q:l<!2Zl;ArU'mC;Q571aF#T@\!-7q9UDl<
QheD$0n/Es;qn1(62+,[QdC[s*'mZ0eCeOu^]AKJ\rTiTZ$,.jY#+J@(/Beod(TRe4*Hc"&kT
`?4'+p^l&G#C?K+Kn&r0Ui!N_F/0K\u;X#W&#l#fO:VeWaX,Fo78P0gS_/f?\1-(U_O2-:Kk
.bqW3R_-fL)mpWF#oKVV,1Mh=]AcpX8-4Y]AH&`GZNT<SR$8GkF_UPc*6':;542XeYZ\A4&^8f
K)Y,(^K!aQY()a2ONOTX_)Zb`Z0RdUbkdh$hmJ!iDO#G;4+).V\u+#M>%t"@el;@9E#*6\9!
C$^3D-BHJ@BO3Bc9K"3g'im1>%d71.PB!^P\o(_[87:?#qbM)nR0d<XdS[%*4&&@=67&p".)
k&l`D$(<G&;e2Il9!lVpO6dm*[Qf1LLcDJ40H+1"LiI`t4)tWnqG_FPqL'LXha4U?I$_HMKH
P3ODo/g!_.(r[7*^T"VW*d<jgEBD'PUW,T1FFL2#uOhuWeun^1fTqK:5g&Rd/?oPpEVr=l]A?
("FLaiP]AMs]AEG;_@+$/O5*[,F^!RB61-?2-3J#ZgM*Bf)B<^k@H'7Mi7U*[+7MWRu>Vqp(gp
o#5^"ho^,82/+<P=cnZ?f2.KYWWF?QChCY?LW/!f$EtN1pLig1n>&=?75p`CWUsF<M:6)Srd
TlQDY7FGdt@'`StfMd9;i=YF/?e?&%(GFGVNo.E!_=.jlF,-,kj/B,3IM#3r#KRW&f=:!YFE
TXoQD>?gHo`9DohpFVF6FBbeY1Ul-s)!Bo6]A)6YI9PNOiP(ei_;a21AZq=R>?o[]Ao[NTm_+M
NAH?/sjE+!I%[QTeWDOq74dKPEF0..>9AmQDBANZ;[+QF8sJSX30tMgphZa3g'BIf)>9eOqj
LT6P2;sk6p"q!OP\T`oe62@/'(6=B(9K5e9:!CTg@G5,.;c_'qggE,Ed<k=(GuG_$kH-KRKb
Wl6h=#QZZ8nWj@@%AkrW%*k9C,HEa8UQN&JU(-HoFEeItJ&LU>NOD_t;Cg@Sm+acb_+nrE\(
g(>5+\->i-fPrRXQdF;9r1UA`XZNYgk/WMbW_\Yi."O""%`I$$M#g;q;K;e6th%@Oh<dL[AB
r=X9g!h?c(tYX:."s%GraU6)ldp.M`!1X::eqCtD'#^XiEc;N-f#ZlH190e_"Be4SgI$Jm60
`-rcHE95:FN33$U`NLPM\3$FbYY&S\+RG&fRR=h`-%cbWZB#Yjh"hu!>6U<hGiPt7J4=MJ-U
:VK6P$BcB7Zf)H`La$7hT(*B;5I[;n=@4R87D_r11'\%/$=&G@I?`d(=T*nc8`b9LGNK4qrH
M$pgAGVJlTk'[RZA[^bW]A1=\b4)O.MS68c28?.Jj.'"<T&1h(d*p-FF_g)9M2Wer(EngA*_u
.V#J:]A)9mep9HQ8TDM\uEI:fghB[`3hLqf.FbdCTSDh.LWPaq!!Kjj>'AL;TLnl]A3D%3\>AO
.Sba'9&%?nQ?F1;f]A".RXj:OM'Wtaa<%rh%s]A)ADnUjL/-W44'S[j:=9VnKQbY0AK7`g?E11
u.B.EK)jos/\CXr4udG>[?baZF/-(53%Shc&YBmrQil!;<)ieB9BbWoY[OC]A/$$r>$mEJ\$\
.aeXg;CDL5hee<rQ\ocm)ZD4SOq%]A\2qaj_g@qK9nlK6=<<R,07W`qUp+,5[FIOLIVNr3VR=
ABFr+e*e4aCsYD4V4g6MQQ3WGgXaIlUsO5rH]A&?2I`AE6V2'6SQ_^tta"Q;h#*,;#_9.f'1X
Et<lPknYQq[HkL-7'0`l6L0RH_k4J")I+gP*i=*9_QpF2uVjU<QsTJR<#lN-*B6el81sgr]A7
]A'(q)(^?Lu33]AQM'@i.g5dF%klWNI2I:(\/QLV["gVfZ]A1G\lsH,IiZ/,9jFp0ld&YIDLVi9
rE.;rNq3JW`<<AdZcIDhN/E2W(&dl^Xe"D4R<(^UME9aEZVECEUA?bne&m#<M;5jjkn7@WC>
bM\h>fPS#o878["WSC_W40V4/am8N[q0nTR-_F,&hFiFA*TrH]AlBI:&9!8oSSlMRRiN)4#U_
lO/EB25=1WZo2-O;Ccsh.&ghU;sk>HQGs>ha/'\`U>.Y(Zu'JB@j/o1$+g`r%hh_:l'g#`9]A
dA*UPCP#:2G7GCp(OX1X''9=FU,C]A)E^N-hX+p.?Uf2m;DB(Tl".ZQXg9Wk@3e4%mI^<1>)9
2TghDT;kJDU`eE%(EC5Q[ao+T)_U&58-o.$k]AS/JJU=t9r%ZJ-W5GN]AbHXBUdqU\TjHR8#JF
=fg$jtpF^M1@C'a37S,7-HUp$l3*\1Z0tTGCD4gfI+G8FM`,!aeirNhN_/VM/cGP2YQf=^1/
_G0p:4i)qV@_[Kl>ui&QGF*9qQdppLB,a=$08Vu3I6l(u_:\>W=6:Y*`ldl2>@CM"i9BgZ5.
>N.#ol*JUlO[pPnro#"Okd`>fs5^`04cCS-<1V=3n1@qMf,&mB6'Z.Y.]A+uFM:Y!c\UPXG-0
EmELABX#]AK'l%>PYooYWQk"9e?%?)p\%UPY_S<nX4&$DP9Pkg4^S]AC/!bOPm.+tE`DEE$(HA
B!5J+ni[PH?ofR$4?+)LK&jC\ZO"k8?KsTAe&`=hC)O_$sf>b7i,;mD3]ANTs4Go$e]A0a.?Q;
'5Ps#9tKsb6*W]AAG1[,i^1.i^=,LfoP1C$A`-e)R[up=QNfNP<_rAnk=ibHs1ChtPAO>9!KX
G)_ZU*\5"Jh;>^<F]AE$<AgRmX>G0(+'1%Cm%65f=mW^]Aqn*[KkgOoG75+\/<hS3uqY?ijAW%
-ZnN[!]A96HJC_5.T>;&7^`]AIjrt%CE_'hpYJ+ApMoCjtX0]Ao'p;$`f)duE]Af"(B%N[Le@M*l
i^Pj;%Rl(RYt9nN19_j!5.GC@QD`7p(eT\I!PW^,9bN?r:Q#LHX_oIiL8%,q83IWD@GQ?01E
*/$04Vi3=fKFZr`/F1Ls0f)U/Ri>mAE^1LX-W&96F-h`#ErG91ZG]AqlOXD4fS$T(<ZW02\j0
K-X&`#2GRd'^pA*ngP7\<!=!6*uJgVe@0U2i@g+>?M%L`7^TH4%Qf58)M@p^fM$jg4XPH^Ld
pm0.B@jguD>,X9/Z^K?)0M3_u8O]A42"8#F-nIer5guP[32K3S/BUP&kk>/Zo/C"&JAGO*j-W
2f*BDDb_=bmu]AZ-$:'m1C%`ea!XRbRG"(?TX&IAl?>,#K'BJ*OUf9>`Xq9XU&(j&9_cI1CC;
t&!2Us%[Y2p;R9&k4)gh`J?;E/nh2%ZJ-]AWF[TqLq-0'#?qg>D(^*IXKBdDZ'hDlVIbr<#a>
I8)N)7ne&V6.0d1hT+&[.4jqL.g?-Cg:kFai=(q(Ldt-8dc:>X0?!)6-FTMUTX$ACgi#QkN,
V(:k*dgiJQ4178rY">I?rj)K@5HY@k=B3fY@TEGg7aFV[f;!2oliCA+n_OO4\'p]Aen%:7M0<
=Y/;+e+qXAj3U2$S8[&45;BI6AY.UgtqGLO).7`&()6+.R)]A13iYA)gSU1oA[I?nAZes.tm=
KWs.mC1FDrZIT%]AP;t/q@YTf'p<_f/HjfkBcObmkkTp-`9mJJI&3HAY2@.1JkU3[Id$EQSl'
oaE_a,`@C2k:(6$E!_<d?-sCZ^@h.;2e;^Ja`?Q[&;-g;q$63'(rd+'oB(!+iKA^Srh.,Wce
ES2G67ftJ:+MCtb0HkN&k9<s;^7fQ\MHiK6i?HVCRR;+r5/5uU5>Sq#3-5?&g9X=.X<-W>X[
<f/,1V^fjqil["#q1tG[=I[f!'c^.;,r!$E2sPNlEe&">77]AUcg_a";i,50mo>4O)7G&63YW
cj#1Jn<RZ$\]A-kc7/Y>LBR'rNG25S="6oP3!<1?#2l*=CT_;u@[,Y\">1s&*=r0?gg!DMaXX
)bu!,Za>>o%*/*@:h;lt'?4;+:uk9c8GGm,PUT=d0/'-g8NdM9c2!k2!eI'(`"89OCL,0Ji;
[]Ag!Hg%7>&me(9a^HdcE?HY"TRsG^5=`"Z/A7TcL"$G_<S``eh:>;@KoB`6&HNX(kl7tT5WF
jp-Egn(O:"?7+5G-FtkI%+dg@cp['<)'mW-6SNB!ciud0HPMAeA)i)X`2Pk74Rl>h'IYCL1a
5P_qeX1hQn6ab!T-JT>ir;$T?\YE]AQrnaFHDLmZ4T?U48bgF?"G-'8i!AE>W>4W8\jGJu>ij
E[(#MHH>ENB"QHkEp5uYf`,b._I,f%rf=)G_3ADX$E'1W2*CQRjopkb(V/Kj$:$<!:Xl&H;>
s#Y6n(I=Enp%CUMkSl>2h-\Hh81%Sg\/*d2>/pA,r4Hm?+oJ+"UFXNqq<RV.:IdhA:(0<>3M
$$(_<p2Pk1aX^SmYTqFet-@K`:0BAhjM7k(/^or59*F*Qb+U5Zu73]A`8ZI9SPIt!Ll3<M=B,
0>#F\XOPdt$T).BJH"6*5?as"neIR$MIC[JG;'phj*Y+8X%oc+3%A4=th(]A`_+lI[>Q(B9fa
4^gCUolu%H6jKKm%$eY3AR9K7M*e:NPN:I5#X\Q)Yjo^6\ulAn\8=p+&o68pI_Y10;-mWl#%
`pRB@bgS5KEI3+StV\?2\E<Q.a$hBC5k`0:Pg;mTrI`hLZSb>q#T:)Psg\@M4WnUttH^ErRq
>!-?>3eO;Na2$a,6+fgj`'DKCU_DgsB)ftF0S."*BJ3B3G)paldB4"$J@<.WEs;[c^Dk#SM%
_9h6RWY*',^k"_/G00(UV@(3-nUkRGXG-\9GD$']AmZYA`V%T_H1dok^?#8c/GMtrU=:h9HAc
6G!kNFdL*V0?]AjFti7^AS,CP$IY%CE;hXM^L%Fd^7iMjUQ!?BM'"Dd4/Qt>mKpON:4$+_!OV
L63\B_eogC,;d!j.RN40p6"FC!?$g2#8*5I"WZki0b'mg/\QknItHhQb^!ls*C,iJWaOp)QH
H/iM(Og>[%-q^]As=fp"p3::kHptFA$gJREc)Q7_GBC]AW/"4!Ae3[@)M>$q`3o9@RnQNJ3kI9
Sd*HI&+c<PQlQAcgG(,U%t)`j0D&BdFQ7`#TG_`1/b\%9J_J?&>,iOMr@8eo&]AM+J'PNfQ_Z
ZKT1K7TV3!+X<*hDZJ6;;)&8c:/9nU"1qq>V71'Lb=82+eZ%_)$QMM4:XZk@6m5.0Q>X<C9r
<#*]A>W>*gtBXo!m4rrlU0):]ALMQ]AGC;U_m+A!,LiUI7IoX9UhHQQ_9s93N'=(\fEW6K0Tf"g
D:)%4&?Vf=n!U71QkYfEf!!pr/R^2q-?*t:*kI@8$&1sSGu9mH@Im4ROO12QotZf,20=+`5R
0R/D:\/@efi9d3Of@07R^4E#ML-,@Ju=-f%OkhF`KOUT!r]AdHCg/03%aechrlp*Ii+E^DlSp
h4CPE>NGea~
]]></IM>
</InnerWidget>
<BoundsAttr x="135" y="0" width="825" height="540"/>
</Widget>
<body class="com.fr.form.ui.ElementCaseEditor">
<WidgetName name="report0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="1" left="1" bottom="1" right="1"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<FormElementCase>
<ReportPageAttr>
<HR/>
<FR/>
<HC/>
<FC/>
</ReportPageAttr>
<ColumnPrivilegeControl/>
<RowPrivilegeControl/>
<RowHeight defaultValue="723900">
<![CDATA[723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900]]></RowHeight>
<ColumnWidth defaultValue="2743200">
<![CDATA[2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200]]></ColumnWidth>
<CellElementList/>
<ReportAttrSet>
<ReportSettings headerHeight="0" footerHeight="0">
<PaperSetting/>
</ReportSettings>
</ReportAttrSet>
</FormElementCase>
<StyleList/>
<showToolbar showtoolbar="false"/>
</body>
</InnerWidget>
<BoundsAttr x="135" y="0" width="825" height="540"/>
</Widget>
<Sorted sorted="false"/>
<WidgetZoomAttr compState="0"/>
<Size width="960" height="540"/>
<MobileWidgetList>
<Widget widgetName="p1"/>
<Widget widgetName="report0"/>
</MobileWidgetList>
</Center>
</Layout>
<DesignerVersion DesignerVersion="HBB"/>
<PreviewType PreviewType="0"/>
</Form>
