--检查项目表增加字段  结果类型 0表示结果型 1表示结论和描述型（为旧版本定死的 347检查项目）
alter table examination_item add item_result_type int not null default(0)

--疾病知识库表增加字段
alter table disease_knowloedge_lib add disease_system varchar(10) --疾病系统分类 数据字典维护疾病系统分类（如：心血管系统、消化系统、内分泌系统等）
                                      ,disease_statistics varchar(10) -- 病统计分类 数据字典维护病统计分类（用来解决如：舒张压偏高、收缩压偏高统计为血压偏高）
                                      ,disease_report int not null default(0)   --是否上报 0上报、1不上报
                                      ,disease_team_show int not null default(0) --团报是否显示 0显示、1不显示
                                      
create table disease_logic_single( --单项疾病逻辑主表
	id varchar(50) not null,            
	disease_num varchar(50) not null,   --疾病编码
	logic_name varchar(50) not null,    --疾病逻辑名称
	item_code varchar(50) not null,      --检查项目编码
	isActive varchar(10) not null,
	sex varchar(10) not null,           --适用性别
	age_max int not null,               --适用最大年龄
	age_min int not null,                --适用最小年龄
	logic_class int not null,            --单项阳性逻辑类型 0按检查项目结果，1按普通科室小结或影像检查按照科室保存的结果
	creater int,
	create_time datetime,
	updater int,
	update_time datetime
)

create table disease_logic_single_item (--单项疾病逻辑条件
	id varchar(50) not null,
	logic_single_id varchar(50) not null,   --单项主表ID
	logic_item_name varchar(50) not null,   --条件名称
	logic_index int not null,               --条件索引
	critical_flag int not null,
	isActive varchar(10) not null,
	creater int,
	create_time datetime,
	updater int,
	update_time datetime
)

create table disease_logic_single_item_condition (--单项疾病逻辑条件值
	id varchar(50) not null,
	logic_single_item_id varchar(50) not null,   --单项逻辑条件ID
	logic_index int not null,                    --条件值索引
	condition_value varchar(50) not null,        --条件值
	condition varchar(10) not null               --条件
)
 
create table disease_logic_composite( --复合疾病逻辑主表
	id varchar(50) not null,
	disease_num varchar(50) not null,   --疾病编码
	logic_name varchar(50) not null,    --疾病逻辑名称
	isActive varchar(10) not null,
	sex varchar(10) not null,           --适用性别
	age_max int not null,               --适用最大年龄
	age_min int not null,                --适用最小年龄
	creater int,
	create_time datetime,
	updater int,
	update_time datetime
)

create table disease_logic_composite_item (--复合疾病逻辑条件
	id varchar(50) not null,
	logic_composite_id varchar(50) not null,   --复合主表ID
	logic_item_name varchar(50) not null,   --条件名称
	logic_index int not null,               --条件索引
	critical_flag int not null,
	isActive varchar(10) not null,
	creater int,
	create_time datetime,
	updater int,
	update_time datetime
)

create table disease_logic_composite_item_condition (--复合疾病逻辑条件值
	id varchar(50) not null,
	logic_composite_item_id varchar(50) not null,   --复合逻辑条件ID
	diseaseOrItem_num varchar(50) not null,         --单项阳性编码或检查项目编码
	condition_type int not null,                 --0表示单项阳性、1表示检查项目
	logic_index int not null,                    --条件值索引
	condition_value varchar(50) not null,        --条件值
	condition varchar(10) not null               --条件
)

create table examinfo_disease_single(  --结果录入对应单项阳性表
	id varchar(50) not null,
	exam_num varchar(50) not null,      --体检编号
	dep_num varchar(50) not null,       --科室编码
	charging_item_code varchar(50) not null,     --收费项目编码
	disease_num varchar(50) not null,   --疾病编码
	item_code varchar(50) not null,      --检查项目编码
	disease_name varchar(50),           --疾病名称
	creater int,
	create_time datetime
)
