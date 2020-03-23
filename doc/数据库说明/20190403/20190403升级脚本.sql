insert into WEB_RESOURCE values ('RS049','操作人员是否可根据资源权限来看折扣率及折扣后金额','2','1','操作人员是否可根据资源权限来看折扣率及折扣后金额','输入：1','表示操作员有此功能','','','Y')

insert into center_configuration values('xxxxx','IS_DJT_ZYB','N','Y','在登记台是否可操作职业病体检','NULL')

insert into center_configuration values('xxxxx','IS_FLOW_S0_S1','N','Y','整单室获取报告是否一起上传','NULL')


insert  into  WEB_XTGNB  values(991,'getPricing.action','划价页面','1','','325',1,1,1)

insert  into  WEB_XTGNB  values(998,'getTeamPrepayment.action','团体预付费页面','1','','998',1,1,1)

insert  into  WEB_XTGNB  values('1730',	'getExamInfoAuditPageLog.action'	,'检查结果、总检结果审计页面',	'1'	,NULL	,1730	,1,	1,	1)

alter table exam_info add ziqu_report_time datetime; --增加自取报告时间



 

INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1199', N'getHealthReportPath.action', N'获取健康管理报告', N'2', NULL, N'392', N'2', N'1', N'0')

INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1200', N'flowEndRecovery.action', N'流程-导检单核收', N'1', NULL, N'1200', N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1201', N'flowEndRecoveryexam.action', N'流程-导检单核收-获取体检项目信息', N'2', NULL, N'1200', N'2', N'2', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1202', N'flowh0ExamInfo.action', N'流程-导检单核收-导检单核收 1202', N'2', NULL, N'1200', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1203', N'flowdeleteExam.action', N'流程-导检单核收-放弃1203', N'2', NULL, N'1200', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1204', N'flowupdateExam.action', N'流程-导检单核收 恢复1204', N'2', NULL, N'1200', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1205', N'flowrevocationDYD.action', N'流程-导检单核收 撤销导引单', N'2', NULL, N'1200', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1206', N'flowgetyanqi.action', N'导引单延期页面1206', N'2', NULL, N'1200', N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1207', N'flowupdateyanqi.action', N'导引单批量延期1207', N'2', NULL, N'1200', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1208', N'flowexampList.action', N'查询上传信息1208', N'2', NULL, N'1200', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1209', N'flowexamh1insert.action', N'流程-导检单核收 上传信息', N'2', NULL, N'1200', N'2', N'1', N'1')

INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1210', N'flowexamh1delete.action', N'流程-导检单核收 上传取消', N'2', NULL, N'1200', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1211', N'flow_s_show.action', N'流程-整单室接收导检单', N'1', NULL, N'1211', N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1212', N'flows0ExamInfo.action', N'整单室 接受导检单', N'2', NULL, N'1211', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1213', N'flows0ExamInfoun.action', N'整单室 取消接受导检单', N'2', NULL, N'1211', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1214', N'flowexams1insert.action', N'整单室-上传信息', N'2', NULL, N'1211', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1215', N'flowexams1delete.action', N'整单室-取消上传', N'2', NULL, N'1211', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1217', N'flowexampLists.action', N'整单室 取消接受导检单', N'2', NULL, N'1211', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1218', N'flow_p_show.action', N'流程 打印室核收导检单主功能', N'1', NULL, N'1218', N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1219', N'flowp0ExamInfo.action', N'打印室-接收导检单', N'2', NULL, N'1218', N'2', N'1', N'1')

INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1220', N'flowp0ExamInfoun.action', N'打印室-取消接收导检单', N'2', NULL, N'1218', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1221', N'flowexampListp.action', N'打印室 取消接受导检单列表', N'2', NULL, N'1218', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1222', N'flowexamp1insert.action', N'打印室上传信息', N'2', NULL, N'1218', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1223', N'flowexamp1delete.action', N'打印室 取消上传信息', N'2', NULL, N'1218', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1224', N'flow_e_show.action', N'流程-发送室核收导检单主功能', N'1', NULL, N'1224', N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1225', N'flowe0ExamInfo.action', N'发送室-接收导检单', N'2', NULL, N'1224', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1226', N'flowe0ExamInfoun.action', N'发送室-取消接收导检单', N'2', NULL, N'1224', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1227', N'flowexameListe.action', N'发送室 取消接受导检单列表', N'2', NULL, N'1224', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1228', N'flowexame1insert.action', N'发送室 上传信息', N'2', NULL, N'1224', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1229', N'flowexame1delete.action', N'发送室 取消上传信息', N'2', NULL, N'1224', N'2', N'1', N'1')

INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1230', N'flow_z_show.action', N'流程-总检主页面', N'1', NULL, N'1230', N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1231', N'flowexameListz.action', N'总检查询页面', N'2', NULL, N'1230', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1232', N'flowzExamInfo.action', N'终检室-接收导检单', N'2', NULL, N'1230', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1233', N'flowzExamInfoun.action', N'终检室-取消接收导检单', N'2', NULL, N'1230', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1234', N'flow_m_show.action', N'流程-解读室主页面', N'1', NULL, N'1234', N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1235', N'flowexamListm.action', N'报告解读综合查询', N'2', NULL, N'1234', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1236', N'flowmmessageshow.action', N'报告解读获取内容', N'2', NULL, N'1234', N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1237', N'flowmmessageUpdate.action', N'报告解读保存内容', N'2', NULL, NULL, N'2', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1238', N'termreportManager.action', N' 团体报告管理', N'1', NULL, N'1238', N'1', N'1', N'1')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1239', N'termReportUserList.action', N'团体报告查询人员', N'2', NULL, N'1238', N'2', N'1', N'1')

INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1240', N'djtTpanel.action', N'团体备单操作台', N'1', NULL, N'1238', N'1', N'1', N'0')

-----------------------------------------------------------

INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1308', N'flow_v_show.action', N'回访主页面', N'1', NULL, N'1308', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1309', N'flowexampListv.action', N'待分配/待回访/已回访列表', N'2', NULL, N'1308', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1310', N'batch_allot.action', N'回访分配页面', N'2', NULL, N'1308', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1311', N'batchAllotDoctor.action', N'批量分配回访给医生', N'2', NULL, N'1308', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1312', N'addVisitPage.action', N'添加回访记录页面', N'2', NULL, N'1308', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1313', N'saveVisit.action', N'保存回访计划和回访记录', N'2', NULL, N'1308', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1314', N'allot_random.action', N'回访-随机分配一个回访对象', N'2', NULL, N'1308', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1315', N'getStatisticsData.action', N'回访-获取统计数据', N'2', NULL, N'1308', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1316', N'editVisitPage.action', N'修改回访记录页面', N'2', NULL, N'1308', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1317', N'flow_zg_show.action', N'招工体检管理主页面', N'1', NULL, N'1317', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1318', N'getVisitUserList.action', N'回访-获取可进行回访的用户', N'2', NULL, N'1308', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1319', N'getPre_receive.action', N'发送室 获取待接受导检单列表', N'2', NULL, N'1224', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1320', N'getPre_receive_details.action', N'发送室 获取待接受导检单详细信息', N'2', NULL, N'1224', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1321', N'flowe0ExamInfo_batch.action', N'发送室 导检单批量核收', N'2', NULL, N'1224', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1322', N'batch_transfer.action', N'批量转移页面', N'2', NULL, N'1211', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1323', N'flowexamtransfer.action', N'指引单转移', N'2', NULL, N'1211', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1324', N'flow_show.action', N'报告追踪查看页面', N'1', NULL, N'1324', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1325', N'flowloglist.action', N'查询导检单流程日志和内容备注', N'2', NULL, N'1324', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1326', N'batch_sendchk.action', N'批量发送审核页面', N'2', NULL, N'1211', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1327', N'flowexamsendchk.action', N'报告批量发送审核', N'2', NULL, N'1211', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1328', N'filmflows0ExamInfo.action', N'整单室 胶片批量核收', N'2', NULL, N'1211', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1329', N'flowexampLists_film.action', N'整单室 胶片上传列表', N'2', NULL, N'1211', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1330', N'flowexams1insert_film.action', N'胶片批量上传', N'2', NULL, N'1211', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1331', N'getPre_receive_film.action', N'发送室 获取待接受胶片列表', N'2', NULL, N'1224', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1332', N'getPre_receive_details_film.action', N'发送室 获取待接受胶片详细信息', N'2', NULL, N'1224', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1333', N'flowe0ExamInfo_batch_film.action', N'发送室 胶片批量核收', N'2', NULL, N'1224', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1334', N'flowexameListe_film.action', N'发送室 胶片上传列表', N'2', NULL, N'1224', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1335', N'flowexame1insert_film.action', N'发送室 胶片领取', N'2', NULL, N'1224', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1336', N'getPre_receive_zongjian.action', N'总检室 获取待接受导检单列表', N'2', NULL, N'1230', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1337', N'getPre_receive_details_zongjian.action', N'总检室 获取待接受导检单详细信息', N'2', NULL, N'1230', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1338', N'flowz0ExamInfo_batch.action', N'总检室 导检单批量核收', N'2', NULL, N'1230', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1339', N'flowexams1delete_film.action', N'整单室 胶片批量取消上传', N'2', NULL, N'1211', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1340', N'flowexame1delete_film.action', N'发送室 批量取消胶片上传', N'2', NULL, N'1224', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1348', N'flowexampListzg.action', N'招工体检待处理列表', N'2', NULL, N'1317', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1349', N'getReportZg.action', N'招工体检-报告领取页面', N'2', NULL, N'1317', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1350', N'saveZgExamResult.action', N'保存招工体检结果记录', N'2', NULL, N'1317', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1351', N'flowexampListzg_report.action', N'招工体检报告领取列表', N'2', NULL, N'1317', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1352', N'saveManagerReportReceive_zg.action', N'保存报告发送方式', N'2', NULL, N'1317', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1353', N'upd_getReportWay.action', N'更新取体检报告方式', N'2', NULL, N'1200', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1354', N'upd_reportAddress.action', N'更新报告邮寄地址', N'2', NULL, N'1200', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1355', N'saveEdesc.action', N'保存收发室说明', N'2', NULL, N'1224', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1358', N'edit_zg_result.action', N'批量录入招工结果页面', N'2', NULL, N'1317', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1360', N'check_print.action', N'打印室-检查导检单是否已打印', N'2', NULL, N'1317', N'1', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1361', N'reedit_zg_result.action', N'重新录入招工结果页面', N'2', NULL, N'2', N'2', N'1', N'0')
INSERT [dbo].[WEB_XTGNB] ([ID], [URL], [NAME], [TYPE], [REMARK], [FATHERACTION], [ACTIONTYPE], [ADMINTYPE], [apptype]) VALUES (N'1362', N'flowexameListe_allflow.action', N'发送室-全流程查询列表', N'2', NULL, N'1224', N'2', N'1', N'0')

insert into center_configuration values('XXXXX','SENDREPORT_MSG_COMPANY',16,'Y','收发室接收报告时，需要发送短信的单位&&需要回访的单位',null);
insert into center_configuration values('XXXXX','IS_NOT_VISIT_COMPANY','','Y','不需要回访单位ID配置，多个单位以,隔开',null);
insert into center_configuration values('XXXXX','IS_VIP_CHARGITEM','','Y','VIP的收费项目id，已小写逗号隔开',null);
insert  into  center_configuration  values('XXXXX','IS_PRINT_SMS','N','Y','收发室接收报告是否发送短信',null);


CREATE TABLE [dbo].[exam_health](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[exam_id] [int] NOT NULL,--体检编号
	[creater] [int] NULL,--创建人
	[create_time] [datetime] NULL,--创建时间
	[updater] [int] NULL,--修改人
	[update_time] [datetime] NULL,--修改时间
	print_status varchar(1) not null default('N'),--打印状态(N:未打印; Y:已打印)
	print_date datetime,  --打印时间
	print_userid  int, --打印人
 CONSTRAINT [PK_exam_health] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO