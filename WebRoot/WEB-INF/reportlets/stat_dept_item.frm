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
<TableData name="ds1" class="com.fr.data.impl.DBTableData">
<Parameters>
<Parameter>
<Attributes name="p1"/>
<O>
<![CDATA[]]></O>
</Parameter>
<Parameter>
<Attributes name="endDate"/>
<O>
<![CDATA[2015-12-12]]></O>
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
<![CDATA[select dep_id, dep_name, charge_item_id, item_name, exam_status, COUNT(exam_num) as num,
 status=(case exam_status when 'Y' then '已检（人次）' when 'C' then '在检（人次）' 
      when 'N' then '未检（人次）' 
      when 'G' then '弃检（人次）' when 'D' then '延期（人次）' else exam_status end) 
from v_stat_exam_general
where join_date>='${beginDate}' and join_date<='${endDate}'
${if(len(p1)=0, "", " and dep_id=" + p1)}
group by dep_id, dep_name, charge_item_id, item_name, exam_status
order by exam_status desc]]></Query>
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
<NorthAttr size="57"/>
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
<Background name="ColorBackground"/>
<LCAttr vgap="0" hgap="0" compInterval="0"/>
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
<FRFont name="宋体" style="0" size="88"/>
<border style="0" color="-723724"/>
</InnerWidget>
<BoundsAttr x="36" y="18" width="80" height="21"/>
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
<BoundsAttr x="116" y="18" width="110" height="21"/>
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
<BoundsAttr x="558" y="9" width="80" height="34"/>
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
<FRFont name="宋体" style="0" size="88"/>
<border style="0" color="-723724"/>
</InnerWidget>
<BoundsAttr x="272" y="18" width="80" height="21"/>
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
<![CDATA[1449849600000]]></O>
</widgetValue>
</InnerWidget>
<BoundsAttr x="352" y="18" width="119" height="21"/>
</Widget>
<Sorted sorted="false"/>
<Display display="true"/>
<DelayDisplayContent delay="true"/>
<Position position="0"/>
<Design_Width design_width="960"/>
<MobileWidgetList>
<Widget widgetName="beginDate"/>
<Widget widgetName="endDate"/>
</MobileWidgetList>
<WidgetNameTagMap>
<NameTag name="endDate" tag="endDate:"/>
<NameTag name="beginDate" tag="beginDate:"/>
</WidgetNameTagMap>
</North>
<Center class="com.fr.form.ui.container.WFitLayout">
<WidgetName name="body"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="3" left="3" bottom="3" right="3"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="宋体" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Background name="ColorBackground" color="-3348752"/>
<Alpha alpha="1.0"/>
</Border>
<Background name="ColorBackground" color="-3348752"/>
<LCAttr vgap="0" hgap="0" compInterval="3"/>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.TreeEditor">
<WidgetName name="p1"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<fontSize>
<![CDATA[13]]></fontSize>
<TreeAttr async="false" selectLeafOnly="true"/>
<TreeNodeAttr>
<Dictionary class="com.fr.data.impl.CustomDictionary">
<CustomDictAttr>
<Dict key="科" value="科室"/>
</CustomDictAttr>
</Dictionary>
</TreeNodeAttr>
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
<isAutoBuild autoBuild="false"/>
<widgetValue/>
</InnerWidget>
<BoundsAttr x="0" y="0" width="159" height="540"/>
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
<border style="1" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="宋体" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<FormElementCase>
<ReportPageAttr>
<HR F="0" T="1"/>
<FR/>
<HC/>
<FC/>
</ReportPageAttr>
<ColumnPrivilegeControl/>
<RowPrivilegeControl/>
<RowHeight defaultValue="723900">
<![CDATA[723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900]]></RowHeight>
<ColumnWidth defaultValue="2743200">
<![CDATA[2095500,2743200,6362700,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200]]></ColumnWidth>
<CellElementList>
<C c="0" r="0" cs="5" s="0">
<O>
<![CDATA[项目检查情况统计]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="0" r="1" s="1">
<O>
<![CDATA[序号]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="1" r="1" s="1">
<O>
<![CDATA[科室]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="2" r="1" s="1">
<O>
<![CDATA[项目名称]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="3" r="1" s="2">
<O>
<![CDATA[合计]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="4" r="1" s="1">
<O t="DSColumn">
<Attributes dsName="ds1" columnName="status"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="1"/>
</C>
<C c="0" r="2" s="3">
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[=seq()]]></Attributes>
</O>
<PrivilegeControl/>
<Expand leftParentDefault="false" left="C3"/>
</C>
<C c="1" r="2" s="4">
<O t="DSColumn">
<Attributes dsName="ds1" columnName="dep_name"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
<C c="2" r="2" s="4">
<O t="DSColumn">
<Attributes dsName="ds1" columnName="item_name"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<HighlightList>
<Highlight class="com.fr.report.cell.cellattr.highlight.DefaultHighlight">
<Name>
<![CDATA[条件属性1]]></Name>
<Condition class="com.fr.data.condition.FormulaCondition">
<Formula>
<![CDATA[row()%2=0]]></Formula>
</Condition>
<HighlightAction class="com.fr.report.cell.cellattr.highlight.BackgroundHighlightAction">
<Background name="ColorBackground" color="-1"/>
</HighlightAction>
</Highlight>
</HighlightList>
<Expand dir="0"/>
</C>
<C c="3" r="2" s="3">
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[=sum(E3)]]></Attributes>
</O>
<PrivilegeControl/>
<HighlightList>
<Highlight class="com.fr.report.cell.cellattr.highlight.DefaultHighlight">
<Name>
<![CDATA[条件属性1]]></Name>
<Condition class="com.fr.data.condition.FormulaCondition">
<Formula>
<![CDATA[row()%2=0]]></Formula>
</Condition>
<HighlightAction class="com.fr.report.cell.cellattr.highlight.BackgroundHighlightAction">
<Background name="ColorBackground" color="-1"/>
</HighlightAction>
</Highlight>
</HighlightList>
<Expand/>
</C>
<C c="4" r="2" s="3">
<O t="DSColumn">
<Attributes dsName="ds1" columnName="num"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<HighlightList>
<Highlight class="com.fr.report.cell.cellattr.highlight.DefaultHighlight">
<Name>
<![CDATA[条件属性1]]></Name>
<Condition class="com.fr.data.condition.FormulaCondition">
<Formula>
<![CDATA[row()%2=0]]></Formula>
</Condition>
<HighlightAction class="com.fr.report.cell.cellattr.highlight.BackgroundHighlightAction">
<Background name="ColorBackground" color="-1"/>
</HighlightAction>
</Highlight>
</HighlightList>
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
<Background name="ColorBackground" color="-1640961"/>
<Border>
<Top style="1"/>
<Bottom style="1"/>
<Left style="1"/>
<Right style="1"/>
</Border>
</Style>
<Style horizontal_alignment="0" imageLayout="1">
<FRFont name="SimSun" style="0" size="88"/>
<Background name="ColorBackground" color="-1640961"/>
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
<showToolbar showtoolbar="false"/>
<IM>
<![CDATA[buo<o'A'jnTrBmt<[FD=8?4rY5s_>9Q3O4\,#CR&"G)(R"Hfo6"9Arj<f;QcZm&L@#U#j1Jn
S\%gd4uKi'Lr\aI45UJH-,jE[Qu\7`B_HHg0#)J!2[0ju`$W^\<)iPB/g&/EW_A7O]A`6bdZB
A`D/]Abn\0SV_Z!12p^8+GFB1[n44\GqO*co=G4VuReid14`L@5_'eN,4Qd%8^?PHPTrOUoC7
?(5oo_Y)k[iQo#=!.C[ji_,I_UiB:HM1+inE3<rY@MU4f0&u7%_q")-KE"WadCSmrfas!Q+3
C*4589?gmiR\kLJcR1gW*lOgp/3!a[8j,+WC,O(M*4T+Wt>gG.'"g7na:nFsmdW2Zc8b!oD#
]A#`TH(F(+J]ANu^+D&+E7*Nk=+\\O4kH]Asq_UEWsB._PE/ZEjDj6JBT64Rph$qJQnuQtcOf-0
1YlIG'd1281&;h3<hAY0_H0>b;/_8c?',l*cgSikVTu@WtEW^i:sLccIqgOKEMKNNPohaFhG
4(.@Jh!QX4PBqBD:9$Zf8S985t*Zis18bOdH>?!D&jm0IA(@CEJ>$Tof0%Ffqb7/u7P3ksjO
QV?AdXffrr"Qq:gtRi%Ku\YF`C5W@3nOD2o^XRh=<)*Obu.4?FLYjN2o)K+*_D7%Y)Z$'.^R
WYK1_pRagX*Jl<_KAG2-Zs;VNL=lD`k?+AC]A$.$:gn)"#_&L"]Ae^!Arp)qR>X2Jkr-2S\DAA
#0i[eaglg',HFroU$V\JRQ<X,`k/:fj6R637LFW]Aj,WJ"k9B"'5'd*8WcI=/r-Oco!a!$.=o
M&_>N?ipg]A7mHi*qkIWu>[]Af/W+/2cg0dH!D/uOL;Xr^otiI2c&49.6Xn$LMLu-h%gXr@io^
<mm`^hd6]A@D:U/tXPHWs96;B83Fj5dYRQn'D)4WN>+7$E02dEM%1&P)TICE5:_m[sc2Wr,pI
XZb'mk+a=I@O#?/#@_)o(3iqqE2X8#JS1t=_:rQ>F%:<#S=hMepn<Jocj#t%s[/hrq-D^-[&
No-cTs-_kVVdEZmpVc`oDgY92rihJkmIhn#Hns0fSm5[`_'&[,YVMb91`afmXF\`UT>-DCQp
qXM0`Q<RWNJH[Fc^P*H.;K_ZB.6DKIF5#tmE1Hecq0S#44AGQ*19tYD862bcX3R_ejS4XN9$
ZuTkKDV\9BYgF'9K4:"LjM=2<ViH#'YCeN/+)tD\0;8!9;J1Kk9"4n#S5im3D@kO=@WFbaS;
-j;u;dE'$e(.+Y(2d[23WQpH-a[Q[+G5H`f9`6BoZ.B-gn6CT%.iJdqOQd)Mj's1k!)3_!5$
SY*lZnR;K[!$9CrDd>b_`b^V=1Yk'@C+WWju@q0YmlA^HpqjiO_Q'%?j!sS2<C=0YuYTeX&S
R%=U!rZ9,^Wp^F]Ab!GJ*"%Z5?;R<TP*X$E]A*3d?'-lj=>5e9t:,4^$!Z.rct2Z:5tcjn"Th/
Y\?Z6QZ+>f(j<l"KnP,g%9#.&NcEl?Pgo1s6(m<\L-"T]A0Voe-W8i</2/A16.!h=Ff-LT&^c
*LVWXHj*nj<U6QAq8D,b%I]ANHkehpM&r2NZ8F8V%;:MNj3>9h-+tJP]ABJu+FP/6k6,B"C[nl
SS`p($q5-nqoWop=^CLaRnLFpJhY/lp]AuG4pE/G*IRr[5PjHu`nqsVmVZ,/:p.3nlcr]A,8@\
#j;uR,6jkcA(\=7MB[36/mQtTM[6XLE")Ajku'\7)5lOgPW=OiBg=3-m<kg[*sW@@YK<_[G]A
EJi>7OnV3dGrn]AehVqE(!#7Q#omYmLOgWFUfi6Z&0,<i7^09q[RI?sdMZC85V[lE7N0j40fj
HT=O%f^u8skfP-*q0N#h<I6!L]AVW97nXdArb5n6_rfnOBATHR8&VUq!lZ89KFklQ<_rGb5DY
%*?!*aL8.^Y=;VuA.J_07B@JdEFh8^jHR+0gcfIQ!=k_7QYWI((3k:c7l?'IJWcQ+U']A#&gi
Fj._B!&,!>#d3qn*>37!..:8LmDO,+Gcl^Ig[N;nTg05g8#>8>NpT4mWS]A>qPR?DWu,dI)\:
AsC5G)WK?^AJt.<5nICCMi;8#I3<XqtU5AbLY#g'DW>H[CEo^+7?K_N"\8LS</"BjK9,!Enq
u7hXdo%!.cuP]AVB]AfamIJaK"jR>CiLCkprn2/ON_\-H@4>h5AKR0AF3WJ6q!q;12l&!k;,-9
g"XNi5Q]Ae`0't;U;,Grs)opIE*hq*K/)6$+YXN+LDC*K&ekR6``qqN_[\beGMqDqo,/LHp"X
ap$-<TW'%Rfi#!?'cM72ZP(aLnu#!_XifXqZ2ZO7=Bc_(?.dHO#0/61[Y^IUth:_833p^uPM
E4h*<kb+YftLk^=#6f7`95K[bnIfr\pl>pJi)aT\AY8d#<7ueE1K+B%bhN%u3A2"oJH@k31l
N5RB4i<6a(_-[59A8a\ZMD?Fa]AA`&WV"EZmV_lKaX-(e7YE:a#1a2l@2Pa9o1Co&+;[<g-:F
.UW>4E6bb+4I,hLFdBR]A*D6@q`%.[=nE1<4QZYF3>H20/4-5VK)(;N$G%.7Q09a0>!FI8d;8
m'U*im\!tkDXdKD0?I9i"X_i@6]A3Sh@:$t@,$B%h*Tq)P`oF!4`L`eIE$8Q\Yp[7O?3Po.3C
_Q?/'8n'mb1mL\RjqX=Kr4&q%*I$`LC_d\IAs+I!.7\_%65tnA-.0E<J/l:K\tTd=&/D!H&'
kTWs/A&l?O?K<aaPRX&6C[l/[MhQrM#r]A/M"lI\.ke\XG@2D!s_1WB?B'oJ:4n(Jj[k<ETiX
UgLLW[t/bf\X'F[lo.+poPL-N?/NKDt[OI*'9;Si/l?Ff"p=J4CKfW/W=TD@I'61pm_):-Vp
^iQ!rES)QO\A=siL<P)`DZ3_&u&<17_@?ptCE&`Q$OJjZ!i5QEi@823fR/HmOk.^@X8J9R5a
_u7=/!9R:a\9LA03QAZW6Q2/C(';^<&c:X'DTA\,<0Xc/XOjP2d_1u`:"JDa\`E(a^FHZ^j5
nBB!['uYIRI+L"%(EpJG#X.)pHl?&oTg)&cE\o@7f2mQgeIKaKfK_\FE)\LnI3Zqo4m7i0'D
j8i$9_2@IqL(lOC9i=ou.(faaW7)^r8[.(@;-d/]AEl'kX4L)$8$]A9fq1BYV=9@?1kb<bM;$K
K+JVp9nd-#++Cs&%S*co/fs*\Hr?BhCWNTRgpZ5'JOgMIBAkUI4&/.ZN#0i_A'f0\l;X_&tR
q$>?ASf`5Cot5[n5K@s4:CA!>NME6mDlM+tnSK#'!Ep=iG-+3LDaNk%`R>dAVK:a5:/hpsM.
T*hpPp4Us.2N`C4b5<KTWo_R%>\eNm"Wpi2m`1IhGo4(Kj85';!I##g-Y&gZf:DOVEDU#LaA
8n%`@n[Ga,jJK+!&[h.l!D-\:?TZddrL8='Zd``Uhnl>n#Ond;dY5FkIQsYLU?@;TFu`q_G5
(MGGfW"u6BYnk@>pnZm;)fnUtig65K9NqGQmqd$BM@j'ap=K-fd'<@FXX5-gq,NA$m+G9,FO
&QeD3;3g3EQoF)Q'=[d$$Kq]AE\QBf[);[J&#2Lo.ZAiJG%1rJeJkAL\^Rm"q.Ngi),+EL<MT
8n7LdntZg(0@>`1(eN#R9PKjNs90XCsO<kaM&7,\5%]AlHmjMBN2sY"CP1Q!QEsD"h?K&g$7s
IJ`ME>tKT;FY2./1=r6.6bZQSe9!4Rjf`j$YfK#((`VV+_CEE<8n-u,>oHY#bSs`/;ao]A_4.
kHiPY(4^%<`i*B2Z,9pT_ZRkU=o.5H^;98oc,imG-d*-@G*BRAB0$C!%0Lp!Y"ka_PBO1SV+
!.DE`2@>XYM[4bLr,f_s((+8t2:"GI!(R=rMKk,f"9(/HobCc=;mJ_,R!ZLT!3KL_]AnIl[3Y
[2MU8QW&>SM-*MI`;$#^RdSM:B2;!_+u:NlV`dRS(R7(N7I!U6]AEi/iOpj+;3pD9*32h$]AZY
WsSbmOPJmHB.bH#jBdb'EN**sk'G'8i:Xl-,<(Xb8^Wrp't0cl7gh&a963(Fr&]A!tG@)N_.u
4#%W]ApJJ7rfj%\,1,V+h`/)e=))Spc-[oMcK*HaM!Z*&<V2k<(23/Lb+g31ZgU*kB#C[2R?,
!Z6!kj1fVlDsAipHl)U3tKE(%S9L)=K]A,mOdAi+."T&A:s$_&DR0ofHNSpNFL_U!IC*3]A.7[
,#<*2A+Y&nK1*&n[8-Xn5&gt^8b8Q:c>*JQT5UR"+OZE]A5Tt[#7PS]Au<.cB?L=08CO\/1$Ah
R2h1#l?5cGsNON?$=[/(:0<3#UVf%YoLraiqJoPEB4)T!e_hs;-Vk"+a?HQAh%skGk1M,`fp
9*Rd2S>OMQ4pC^huaoTk;(<3U/-_C,R!W8_<8+U_Et70oMQETt[L7C6q-FPsH6"Jo;Xl[@6c
GH0X)Rr2"V%Esn_&qL=$WIcJolK"Le^2E*%'9'lq[k'9>Kj3[]ASZ.j"T9p"cB>JA1c>2^l6(
>tFdLLn!579ZgXC%I0e)/DX+)I9>A&fu^5Igt=-`W/o"grdL'6XdYcj3OIl'VUOIHLpuK'Q\
r_-B0m:Z*S67l"&d8!AK?KOSf(bP_Bk@6aWsm&/QJBL01gjjQRN1G.3sSIS^;\tpgB1rpIg6
O73c/e^;BC_lMZrl=Pj-)9%e3lReHmGC8;!A\?s>\Y3qc($D#WaLI(&3`$PA[T'F;/uE!$jL
7e^5gtKlci9us%6tQo3]Al)E'_?7B[YO>Eh>og6%O,URGL]Alk'hD:M:A+$<eYbLXadgcaq2+0
@N/95bjt_]AF9<2"9c$YA]AErf%.LF89%$<nL>!A&5Y`0-^ncO>SEM7OGA'r/nY_O8YC,Gm7T8
F&:\S<Hrf<WLoqo*XX3hKs<a$W%cc!?N2X_3q&/\]Am*gmXTa%;=IO[Wk+iL5N4Qg2LQX?'so
_4J2i*`E,ATLZ?h!<apXs9H@,Aoo@>\Q*Z@n#*c'?P"#Mb-Lc?&<bA<CVjOs9j?obVrQho"&
Oj":3ER1'/\<:_&-BBoOTSl0G1WSTeUa#=9e689cdQ!,.F+_L-F1o2&#i&P!h8\3mQB]AN1i-
edWD$-r=_8[u0qbPF)KB=lB;ZEH/8#UK;eR=EZFAk=\4gO28#YJ@g*_YrXN4fLq'kDp3j!Q)
E6?F=i?c,0h]AN^$PnIdV3aNJW6Cu5'ED,XlAcpOUiUA!=@k.8ZP<h`?o$9Fe?MIN57[QJHpM
9o;ibnm1^eZ&L[Y2eFK_M"jWEAO)9!I!NWH@.T0UN]AX#ic(b/7HNeR1cK6&0d:M2ABjbgk,j
49cm>Iq'#2p]AIG!<E7nC4</M`Hf)a9#aKDCmF(uqY-DP#K.QdB[6OuZ=5'^6/dmpZUmR.tk]A
GtXl:k=cT'tYp0IhZ4HH3&R&<fP*\19D",VU_Rc5mp-!&6!YL8;)!c<lT"PB9GX/@:$:>2UR
#<g5'g96<?q'(@7mkA0$C'f$d1?#do:s8k>Pg9aF^jU&/?o+d9#TRHtZCi.l91TsK6K4oAY%
^0@r$*rZ29??.M:3t'm7@@"^b5pQ.?XD:M_#DE?Un,T8,G(@!>T.kk>C;M$6-.M@3RP$Ba/N
^>3g=s>kZ8c-\;+dj2\E.$*k\tJ^:^8eiGLU&l[77ln7H\`QlqE4O'Lmkq_bU?,Sns,pKk^B
+F_9I]AYh*hU@\Q#Y;DW>p-X5UoP6[@-D*9MD#rD?%63u'G1G:W6TrFj:QL+^D^rg?c[T>ii^
Y]A(4,A=6OfUM>4^<rI^<#?9d^W2"dhphRF:.&de1Mna/&!haVh<u@Z%D`#Z4iVYN%;ZRkB%!
?O*3p1d(T=Ib`C*a15#uB+SO^K^GEOjl-[G[rfU5\?/pB!^oK4sN-oE#sW#?]A&FIV,\-O0#V
`!#\Y8trI3#[B>9T]AZW2J>?OS$-hX/Q>rfc*t95kEtr<%N#Nt#$TIds?jJ'f<O_V9>S#DJ&O
pbjWpuVW47FFXF:;c=,2iEQXi',lX>-)JY+dNN18MVPNne\kVET,LO/<!+1EG)^LH2.5#ACU
=i,Uun;!gW1[!_/_9",rPeo%%rW;[J;'Dh!9K.cAHDAEFrcB1!,O=g5A3&<G^Ya*l>4lEOm2
()f*`N7P'ZgY-]A9EqBiR22.(RMICKAK%h(.KoHV$r.DcJ0'G38AF\JT9]ATp[#rW]A4$$X]ATuV
YTC*@1kK'[;a.aI20D]AtQLKVE*T@(@P^M/ePjKF%bF7k/fP*([[2Y;Lc9$f-6b]A`iA=*0?*:
++;:m0NNa]Ar!M*03;2Ebr9BY\9^)3J>K_B$?C\,UoSgWh8&,`G4AB5H(0$9U'UVq[SjSP_,#
j7!*%]A_,iAHPc,Kp95K:=a>l#G6hAlGQ#s&1BVF5q,Yi#@udK+$Me@9LqkZ[L0hLgfC/a0#I
pmqUMQ0;,WM.JCtn:uqY=:Mod>f,PEBMWqA=UYBH)>6T2oeIZ%/@Kc!rV5/ImmV^1.<1cUQG
5S99c[s?3YYMO7h1`OnV$TMC4O[/Jg/7-sq.YY9!]A=h_ag>NSh>5J9cFf1'73>0T(TMtC5[/
A7_E)_`8j8#K$<]A9I1Dh$[Ao=D]AZ/\[bn?UhaZ\>r8s/H#&~
]]></IM>
</InnerWidget>
<BoundsAttr x="159" y="0" width="801" height="540"/>
</Widget>
<body class="com.fr.form.ui.ElementCaseEditor">
<WidgetName name="report0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="1" left="1" bottom="1" right="1"/>
<Border>
<border style="1" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="宋体" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<FormElementCase>
<ReportPageAttr>
<HR F="0" T="1"/>
<FR/>
<HC/>
<FC/>
</ReportPageAttr>
<ColumnPrivilegeControl/>
<RowPrivilegeControl/>
<RowHeight defaultValue="723900">
<![CDATA[723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900]]></RowHeight>
<ColumnWidth defaultValue="2743200">
<![CDATA[2095500,2743200,6362700,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200,2743200]]></ColumnWidth>
<CellElementList>
<C c="0" r="0" cs="5" s="0">
<O>
<![CDATA[项目检查情况统计]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="0" r="1" s="1">
<O>
<![CDATA[序号]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="1" r="1" s="1">
<O>
<![CDATA[科室]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="2" r="1" s="1">
<O>
<![CDATA[项目名称]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="3" r="1" s="2">
<O>
<![CDATA[合计]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="4" r="1" s="1">
<O t="DSColumn">
<Attributes dsName="ds1" columnName="status"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="1"/>
</C>
<C c="0" r="2" s="3">
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[=seq()]]></Attributes>
</O>
<PrivilegeControl/>
<Expand leftParentDefault="false" left="C3"/>
</C>
<C c="1" r="2" s="4">
<O t="DSColumn">
<Attributes dsName="ds1" columnName="dep_name"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
<C c="2" r="2" s="4">
<O t="DSColumn">
<Attributes dsName="ds1" columnName="item_name"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<HighlightList>
<Highlight class="com.fr.report.cell.cellattr.highlight.DefaultHighlight">
<Name>
<![CDATA[条件属性1]]></Name>
<Condition class="com.fr.data.condition.FormulaCondition">
<Formula>
<![CDATA[row()%2=0]]></Formula>
</Condition>
<HighlightAction class="com.fr.report.cell.cellattr.highlight.BackgroundHighlightAction">
<Background name="ColorBackground" color="-1"/>
</HighlightAction>
</Highlight>
</HighlightList>
<Expand dir="0"/>
</C>
<C c="3" r="2" s="3">
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[=sum(E3)]]></Attributes>
</O>
<PrivilegeControl/>
<HighlightList>
<Highlight class="com.fr.report.cell.cellattr.highlight.DefaultHighlight">
<Name>
<![CDATA[条件属性1]]></Name>
<Condition class="com.fr.data.condition.FormulaCondition">
<Formula>
<![CDATA[row()%2=0]]></Formula>
</Condition>
<HighlightAction class="com.fr.report.cell.cellattr.highlight.BackgroundHighlightAction">
<Background name="ColorBackground" color="-1"/>
</HighlightAction>
</Highlight>
</HighlightList>
<Expand/>
</C>
<C c="4" r="2" s="3">
<O t="DSColumn">
<Attributes dsName="ds1" columnName="num"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<HighlightList>
<Highlight class="com.fr.report.cell.cellattr.highlight.DefaultHighlight">
<Name>
<![CDATA[条件属性1]]></Name>
<Condition class="com.fr.data.condition.FormulaCondition">
<Formula>
<![CDATA[row()%2=0]]></Formula>
</Condition>
<HighlightAction class="com.fr.report.cell.cellattr.highlight.BackgroundHighlightAction">
<Background name="ColorBackground" color="-1"/>
</HighlightAction>
</Highlight>
</HighlightList>
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
<Background name="ColorBackground" color="-1640961"/>
<Border>
<Top style="1"/>
<Bottom style="1"/>
<Left style="1"/>
<Right style="1"/>
</Border>
</Style>
<Style horizontal_alignment="0" imageLayout="1">
<FRFont name="SimSun" style="0" size="88"/>
<Background name="ColorBackground" color="-1640961"/>
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
<showToolbar showtoolbar="false"/>
<IM>
<![CDATA[buo<o'A'jnTrBmt<[FD=8?4rY5s_>9Q3O4\,#CR&"G)(R"Hfo6"9Arj<f;QcZm&L@#U#j1Jn
S\%gd4uKi'Lr\aI45UJH-,jE[Qu\7`B_HHg0#)J!2[0ju`$W^\<)iPB/g&/EW_A7O]A`6bdZB
A`D/]Abn\0SV_Z!12p^8+GFB1[n44\GqO*co=G4VuReid14`L@5_'eN,4Qd%8^?PHPTrOUoC7
?(5oo_Y)k[iQo#=!.C[ji_,I_UiB:HM1+inE3<rY@MU4f0&u7%_q")-KE"WadCSmrfas!Q+3
C*4589?gmiR\kLJcR1gW*lOgp/3!a[8j,+WC,O(M*4T+Wt>gG.'"g7na:nFsmdW2Zc8b!oD#
]A#`TH(F(+J]ANu^+D&+E7*Nk=+\\O4kH]Asq_UEWsB._PE/ZEjDj6JBT64Rph$qJQnuQtcOf-0
1YlIG'd1281&;h3<hAY0_H0>b;/_8c?',l*cgSikVTu@WtEW^i:sLccIqgOKEMKNNPohaFhG
4(.@Jh!QX4PBqBD:9$Zf8S985t*Zis18bOdH>?!D&jm0IA(@CEJ>$Tof0%Ffqb7/u7P3ksjO
QV?AdXffrr"Qq:gtRi%Ku\YF`C5W@3nOD2o^XRh=<)*Obu.4?FLYjN2o)K+*_D7%Y)Z$'.^R
WYK1_pRagX*Jl<_KAG2-Zs;VNL=lD`k?+AC]A$.$:gn)"#_&L"]Ae^!Arp)qR>X2Jkr-2S\DAA
#0i[eaglg',HFroU$V\JRQ<X,`k/:fj6R637LFW]Aj,WJ"k9B"'5'd*8WcI=/r-Oco!a!$.=o
M&_>N?ipg]A7mHi*qkIWu>[]Af/W+/2cg0dH!D/uOL;Xr^otiI2c&49.6Xn$LMLu-h%gXr@io^
<mm`^hd6]A@D:U/tXPHWs96;B83Fj5dYRQn'D)4WN>+7$E02dEM%1&P)TICE5:_m[sc2Wr,pI
XZb'mk+a=I@O#?/#@_)o(3iqqE2X8#JS1t=_:rQ>F%:<#S=hMepn<Jocj#t%s[/hrq-D^-[&
No-cTs-_kVVdEZmpVc`oDgY92rihJkmIhn#Hns0fSm5[`_'&[,YVMb91`afmXF\`UT>-DCQp
qXM0`Q<RWNJH[Fc^P*H.;K_ZB.6DKIF5#tmE1Hecq0S#44AGQ*19tYD862bcX3R_ejS4XN9$
ZuTkKDV\9BYgF'9K4:"LjM=2<ViH#'YCeN/+)tD\0;8!9;J1Kk9"4n#S5im3D@kO=@WFbaS;
-j;u;dE'$e(.+Y(2d[23WQpH-a[Q[+G5H`f9`6BoZ.B-gn6CT%.iJdqOQd)Mj's1k!)3_!5$
SY*lZnR;K[!$9CrDd>b_`b^V=1Yk'@C+WWju@q0YmlA^HpqjiO_Q'%?j!sS2<C=0YuYTeX&S
R%=U!rZ9,^Wp^F]Ab!GJ*"%Z5?;R<TP*X$E]A*3d?'-lj=>5e9t:,4^$!Z.rct2Z:5tcjn"Th/
Y\?Z6QZ+>f(j<l"KnP,g%9#.&NcEl?Pgo1s6(m<\L-"T]A0Voe-W8i</2/A16.!h=Ff-LT&^c
*LVWXHj*nj<U6QAq8D,b%I]ANHkehpM&r2NZ8F8V%;:MNj3>9h-+tJP]ABJu+FP/6k6,B"C[nl
SS`p($q5-nqoWop=^CLaRnLFpJhY/lp]AuG4pE/G*IRr[5PjHu`nqsVmVZ,/:p.3nlcr]A,8@\
#j;uR,6jkcA(\=7MB[36/mQtTM[6XLE")Ajku'\7)5lOgPW=OiBg=3-m<kg[*sW@@YK<_[G]A
EJi>7OnV3dGrn]AehVqE(!#7Q#omYmLOgWFUfi6Z&0,<i7^09q[RI?sdMZC85V[lE7N0j40fj
HT=O%f^u8skfP-*q0N#h<I6!L]AVW97nXdArb5n6_rfnOBATHR8&VUq!lZ89KFklQ<_rGb5DY
%*?!*aL8.^Y=;VuA.J_07B@JdEFh8^jHR+0gcfIQ!=k_7QYWI((3k:c7l?'IJWcQ+U']A#&gi
Fj._B!&,!>#d3qn*>37!..:8LmDO,+Gcl^Ig[N;nTg05g8#>8>NpT4mWS]A>qPR?DWu,dI)\:
AsC5G)WK?^AJt.<5nICCMi;8#I3<XqtU5AbLY#g'DW>H[CEo^+7?K_N"\8LS</"BjK9,!Enq
u7hXdo%!.cuP]AVB]AfamIJaK"jR>CiLCkprn2/ON_\-H@4>h5AKR0AF3WJ6q!q;12l&!k;,-9
g"XNi5Q]Ae`0't;U;,Grs)opIE*hq*K/)6$+YXN+LDC*K&ekR6``qqN_[\beGMqDqo,/LHp"X
ap$-<TW'%Rfi#!?'cM72ZP(aLnu#!_XifXqZ2ZO7=Bc_(?.dHO#0/61[Y^IUth:_833p^uPM
E4h*<kb+YftLk^=#6f7`95K[bnIfr\pl>pJi)aT\AY8d#<7ueE1K+B%bhN%u3A2"oJH@k31l
N5RB4i<6a(_-[59A8a\ZMD?Fa]AA`&WV"EZmV_lKaX-(e7YE:a#1a2l@2Pa9o1Co&+;[<g-:F
.UW>4E6bb+4I,hLFdBR]A*D6@q`%.[=nE1<4QZYF3>H20/4-5VK)(;N$G%.7Q09a0>!FI8d;8
m'U*im\!tkDXdKD0?I9i"X_i@6]A3Sh@:$t@,$B%h*Tq)P`oF!4`L`eIE$8Q\Yp[7O?3Po.3C
_Q?/'8n'mb1mL\RjqX=Kr4&q%*I$`LC_d\IAs+I!.7\_%65tnA-.0E<J/l:K\tTd=&/D!H&'
kTWs/A&l?O?K<aaPRX&6C[l/[MhQrM#r]A/M"lI\.ke\XG@2D!s_1WB?B'oJ:4n(Jj[k<ETiX
UgLLW[t/bf\X'F[lo.+poPL-N?/NKDt[OI*'9;Si/l?Ff"p=J4CKfW/W=TD@I'61pm_):-Vp
^iQ!rES)QO\A=siL<P)`DZ3_&u&<17_@?ptCE&`Q$OJjZ!i5QEi@823fR/HmOk.^@X8J9R5a
_u7=/!9R:a\9LA03QAZW6Q2/C(';^<&c:X'DTA\,<0Xc/XOjP2d_1u`:"JDa\`E(a^FHZ^j5
nBB!['uYIRI+L"%(EpJG#X.)pHl?&oTg)&cE\o@7f2mQgeIKaKfK_\FE)\LnI3Zqo4m7i0'D
j8i$9_2@IqL(lOC9i=ou.(faaW7)^r8[.(@;-d/]AEl'kX4L)$8$]A9fq1BYV=9@?1kb<bM;$K
K+JVp9nd-#++Cs&%S*co/fs*\Hr?BhCWNTRgpZ5'JOgMIBAkUI4&/.ZN#0i_A'f0\l;X_&tR
q$>?ASf`5Cot5[n5K@s4:CA!>NME6mDlM+tnSK#'!Ep=iG-+3LDaNk%`R>dAVK:a5:/hpsM.
T*hpPp4Us.2N`C4b5<KTWo_R%>\eNm"Wpi2m`1IhGo4(Kj85';!I##g-Y&gZf:DOVEDU#LaA
8n%`@n[Ga,jJK+!&[h.l!D-\:?TZddrL8='Zd``Uhnl>n#Ond;dY5FkIQsYLU?@;TFu`q_G5
(MGGfW"u6BYnk@>pnZm;)fnUtig65K9NqGQmqd$BM@j'ap=K-fd'<@FXX5-gq,NA$m+G9,FO
&QeD3;3g3EQoF)Q'=[d$$Kq]AE\QBf[);[J&#2Lo.ZAiJG%1rJeJkAL\^Rm"q.Ngi),+EL<MT
8n7LdntZg(0@>`1(eN#R9PKjNs90XCsO<kaM&7,\5%]AlHmjMBN2sY"CP1Q!QEsD"h?K&g$7s
IJ`ME>tKT;FY2./1=r6.6bZQSe9!4Rjf`j$YfK#((`VV+_CEE<8n-u,>oHY#bSs`/;ao]A_4.
kHiPY(4^%<`i*B2Z,9pT_ZRkU=o.5H^;98oc,imG-d*-@G*BRAB0$C!%0Lp!Y"ka_PBO1SV+
!.DE`2@>XYM[4bLr,f_s((+8t2:"GI!(R=rMKk,f"9(/HobCc=;mJ_,R!ZLT!3KL_]AnIl[3Y
[2MU8QW&>SM-*MI`;$#^RdSM:B2;!_+u:NlV`dRS(R7(N7I!U6]AEi/iOpj+;3pD9*32h$]AZY
WsSbmOPJmHB.bH#jBdb'EN**sk'G'8i:Xl-,<(Xb8^Wrp't0cl7gh&a963(Fr&]A!tG@)N_.u
4#%W]ApJJ7rfj%\,1,V+h`/)e=))Spc-[oMcK*HaM!Z*&<V2k<(23/Lb+g31ZgU*kB#C[2R?,
!Z6!kj1fVlDsAipHl)U3tKE(%S9L)=K]A,mOdAi+."T&A:s$_&DR0ofHNSpNFL_U!IC*3]A.7[
,#<*2A+Y&nK1*&n[8-Xn5&gt^8b8Q:c>*JQT5UR"+OZE]A5Tt[#7PS]Au<.cB?L=08CO\/1$Ah
R2h1#l?5cGsNON?$=[/(:0<3#UVf%YoLraiqJoPEB4)T!e_hs;-Vk"+a?HQAh%skGk1M,`fp
9*Rd2S>OMQ4pC^huaoTk;(<3U/-_C,R!W8_<8+U_Et70oMQETt[L7C6q-FPsH6"Jo;Xl[@6c
GH0X)Rr2"V%Esn_&qL=$WIcJolK"Le^2E*%'9'lq[k'9>Kj3[]ASZ.j"T9p"cB>JA1c>2^l6(
>tFdLLn!579ZgXC%I0e)/DX+)I9>A&fu^5Igt=-`W/o"grdL'6XdYcj3OIl'VUOIHLpuK'Q\
r_-B0m:Z*S67l"&d8!AK?KOSf(bP_Bk@6aWsm&/QJBL01gjjQRN1G.3sSIS^;\tpgB1rpIg6
O73c/e^;BC_lMZrl=Pj-)9%e3lReHmGC8;!A\?s>\Y3qc($D#WaLI(&3`$PA[T'F;/uE!$jL
7e^5gtKlci9us%6tQo3]Al)E'_?7B[YO>Eh>og6%O,URGL]Alk'hD:M:A+$<eYbLXadgcaq2+0
@N/95bjt_]AF9<2"9c$YA]AErf%.LF89%$<nL>!A&5Y`0-^ncO>SEM7OGA'r/nY_O8YC,Gm7T8
F&:\S<Hrf<WLoqo*XX3hKs<a$W%cc!?N2X_3q&/\]Am*gmXTa%;=IO[Wk+iL5N4Qg2LQX?'so
_4J2i*`E,ATLZ?h!<apXs9H@,Aoo@>\Q*Z@n#*c'?P"#Mb-Lc?&<bA<CVjOs9j?obVrQho"&
Oj":3ER1'/\<:_&-BBoOTSl0G1WSTeUa#=9e689cdQ!,.F+_L-F1o2&#i&P!h8\3mQB]AN1i-
edWD$-r=_8[u0qbPF)KB=lB;ZEH/8#UK;eR=EZFAk=\4gO28#YJ@g*_YrXN4fLq'kDp3j!Q)
E6?F=i?c,0h]AN^$PnIdV3aNJW6Cu5'ED,XlAcpOUiUA!=@k.8ZP<h`?o$9Fe?MIN57[QJHpM
9o;ibnm1^eZ&L[Y2eFK_M"jWEAO)9!I!NWH@.T0UN]AX#ic(b/7HNeR1cK6&0d:M2ABjbgk,j
49cm>Iq'#2p]AIG!<E7nC4</M`Hf)a9#aKDCmF(uqY-DP#K.QdB[6OuZ=5'^6/dmpZUmR.tk]A
GtXl:k=cT'tYp0IhZ4HH3&R&<fP*\19D",VU_Rc5mp-!&6!YL8;)!c<lT"PB9GX/@:$:>2UR
#<g5'g96<?q'(@7mkA0$C'f$d1?#do:s8k>Pg9aF^jU&/?o+d9#TRHtZCi.l91TsK6K4oAY%
^0@r$*rZ29??.M:3t'm7@@"^b5pQ.?XD:M_#DE?Un,T8,G(@!>T.kk>C;M$6-.M@3RP$Ba/N
^>3g=s>kZ8c-\;+dj2\E.$*k\tJ^:^8eiGLU&l[77ln7H\`QlqE4O'Lmkq_bU?,Sns,pKk^B
+F_9I]AYh*hU@\Q#Y;DW>p-X5UoP6[@-D*9MD#rD?%63u'G1G:W6TrFj:QL+^D^rg?c[T>ii^
Y]A(4,A=6OfUM>4^<rI^<#?9d^W2"dhphRF:.&de1Mna/&!haVh<u@Z%D`#Z4iVYN%;ZRkB%!
?O*3p1d(T=Ib`C*a15#uB+SO^K^GEOjl-[G[rfU5\?/pB!^oK4sN-oE#sW#?]A&FIV,\-O0#V
`!#\Y8trI3#[B>9T]AZW2J>?OS$-hX/Q>rfc*t95kEtr<%N#Nt#$TIds?jJ'f<O_V9>S#DJ&O
pbjWpuVW47FFXF:;c=,2iEQXi',lX>-)JY+dNN18MVPNne\kVET,LO/<!+1EG)^LH2.5#ACU
=i,Uun;!gW1[!_/_9",rPeo%%rW;[J;'Dh!9K.cAHDAEFrcB1!,O=g5A3&<G^Ya*l>4lEOm2
()f*`N7P'ZgY-]A9EqBiR22.(RMICKAK%h(.KoHV$r.DcJ0'G38AF\JT9]ATp[#rW]A4$$X]ATuV
YTC*@1kK'[;a.aI20D]AtQLKVE*T@(@P^M/ePjKF%bF7k/fP*([[2Y;Lc9$f-6b]A`iA=*0?*:
++;:m0NNa]Ar!M*03;2Ebr9BY\9^)3J>K_B$?C\,UoSgWh8&,`G4AB5H(0$9U'UVq[SjSP_,#
j7!*%]A_,iAHPc,Kp95K:=a>l#G6hAlGQ#s&1BVF5q,Yi#@udK+$Me@9LqkZ[L0hLgfC/a0#I
pmqUMQ0;,WM.JCtn:uqY=:Mod>f,PEBMWqA=UYBH)>6T2oeIZ%/@Kc!rV5/ImmV^1.<1cUQG
5S99c[s?3YYMO7h1`OnV$TMC4O[/Jg/7-sq.YY9!]A=h_ag>NSh>5J9cFf1'73>0T(TMtC5[/
A7_E)_`8j8#K$<]A9I1Dh$[Ao=D]AZ/\[bn?UhaZ\>r8s/H#&~
]]></IM>
</body>
</InnerWidget>
<BoundsAttr x="159" y="0" width="801" height="540"/>
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
