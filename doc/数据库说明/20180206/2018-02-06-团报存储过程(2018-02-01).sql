--1.��������ű�:��λ��Ϣ
if exists (select * from sys.objects where name='proc_peteam_report_teamInfo' and type='P')
  drop procedure proc_peteam_report_teamInfo;
go
create procedure proc_peteam_report_teamInfo(
/*******************************************
   ��������ű�:��λ��Ϣ
********************************************/
  @company_id int=305, --��λID
  @batch_id   varchar(200)='266' --��������ID(�����,����)
) 
as
	select com.com_name, MIN(convert(varchar(10),join_date,120)) as min_exam_date, 
	  MAX(convert(varchar(10),join_date,120)) as max_exam_date
	from exam_info e, company_info com
	where e.company_id=com.id and e.is_Active='Y' and e.status not in ('Y')
	and e.company_id=@company_id and e.batch_id in (@batch_id) 
	group by com.com_name
	
go

--2.��������ű�:����Ŀͳ��
if exists (select * from sys.objects where name='proc_peteam_report_examItem' and type='P')
  drop procedure proc_peteam_report_examItem;
go
create procedure proc_peteam_report_examItem(
/*******************************************
   ��������ű�:����Ŀͳ��
********************************************/
  @company_id int=305, --��λID
  @batch_id   varchar(200)='266' --��������ID(�����,����)
) 
as
    select ci.dep_id, d.dep_name, i.charge_item_id, ci.item_name, i.num 
    from charging_item ci, department_dep d, 
       (select eci.charge_item_id, count(eci.id) as num from exam_info e, examinfo_charging_item eci
        where e.id=eci.examinfo_id and e.is_Active='Y' and eci.isActive='Y' and eci.exam_indicator='T'
        and e.company_id=@company_id and e.batch_id in (@batch_id)
        group by eci.charge_item_id) as i
    where i.charge_item_id=ci.id and ci.dep_id=d.id
    order by d.seq_code, ci.item_seq
	
go

--3. ��������ű�:����������Ա�ͳ��
if exists (select * from sys.objects where name='proc_peteam_report_examNum' and type='P')
  drop procedure proc_peteam_report_examNum;
go
create procedure proc_peteam_report_examNum(
/*******************************************
   ��������ű�:����������Ա�ͳ��
********************************************/
  @company_id int=305, --��λID
  @batch_id   varchar(200)='266' --��������ID(�����,����)
)
as  
  select A.company_id, A.com_name, '������' as sex, sum(A.plan_num) as plan_num, sum(B.fact_num) as fact_num, 
    sum(A.plan_num)-sum(B.fact_num) as un_exam_num,
    cast(cast(sum(B.fact_num)/sum(A.plan_num*0.01) as decimal(6,2)) as varchar(10))+'%' as rate
  from (select e.company_id, com.com_name, c.sex, count(e.id) as plan_num 
        from exam_info e, customer_info c, company_info com
        where e.customer_id=c.id and e.company_id=com.id and e.company_id=@company_id and batch_id in (@batch_id)
          and e.is_Active='Y' and c.is_Active='Y'
        group by e.company_id, com.com_name, c.sex) as A
  left join 
    (select c.sex, count(e.id) as fact_num from exam_info e, customer_info c
     where e.customer_id=c.id and e.company_id=@company_id and batch_id in (@batch_id)
       and e.is_Active='Y' and c.is_Active='Y' and e.status not in ('Y', 'D')
     group by c.sex) as B on B.sex=A.sex
  group by A.company_id, A.com_name
  union all
  select A.company_id, A.com_name, A.sex+'��' as sex, A.plan_num, B.fact_num, A.plan_num-B.fact_num as un_exam_num,
    cast(cast(B.fact_num/(A.plan_num*0.01) as decimal(6,2)) as varchar(10))+'%'  as rate 
  from (select e.company_id, com.com_name, c.sex, count(e.id) as plan_num 
        from exam_info e, customer_info c, company_info com
        where e.customer_id=c.id and e.company_id=com.id and e.company_id=@company_id and batch_id in (@batch_id)
          and e.is_Active='Y' and c.is_Active='Y'
        group by e.company_id, com.com_name, c.sex) as A
  left join 
    (select c.sex, count(e.id) as fact_num from exam_info e, customer_info c
     where e.customer_id=c.id and e.company_id=@company_id and batch_id in (@batch_id)
       and e.is_Active='Y' and c.is_Active='Y' and e.status not in ('Y', 'D')
     group by c.sex) as B on B.sex=A.sex
	 
go	

--4.��������ű�:�������������Ρ��Ա�ͳ��
if exists (select * from sys.objects where name='proc_peteam_report_age' and type='P')
  drop procedure proc_peteam_report_age;
go
create procedure proc_peteam_report_age(
/*******************************************
   ��������ű�:�������������Ρ��Ա�ͳ��
********************************************/
  @company_id int=305, --��λID
  @batch_id   varchar(200)='266' --��������ID(�����,����)
)
as
    declare @total_num int
    select @total_num=COUNT(e.id) from exam_info e 
    where e.company_id=@company_id and e.batch_id in (@batch_id) and e.is_Active='Y' and e.status not in ('Y')
    
	select @company_id as com_id, a.name, a.seqno, COUNT(e.id) as total_num,
	  woman_num=sum(case c.sex when 'Ů' then 1 else 0 end), man_num=sum(case c.sex when '��' then 1 else 0 end),
	  cast(cast(COUNT(e.id)/(0.01*@total_num) as decimal(6,2)) as varchar(10))+'%' as total_rate,
	  cast(cast(sum(case c.sex when 'Ů' then 1 else 0 end)/(0.01*@total_num)as decimal(6,2)) as varchar(10))+'%' as woman_rate,
	  cast(cast(sum(case c.sex when '��' then 1 else 0 end)/(0.01*@total_num)as decimal(6,2)) as varchar(10))+'%' as man_rate
	from exam_info e, statAge a, customer_info c 
	where e.company_id=@company_id and e.batch_id in (@batch_id) and e.age>=a.AgeMin and e.age<=a.AgeMax and e.customer_id=c.id
	and e.is_Active='Y' and e.status not in ('Y')
	group by a.name, a.seqno
	order by a.seqno

go 
--5. ��������ű�:������ͳ��ǰ10��
if exists (select * from sys.objects where name='proc_peteam_disease_top10' and type='P')
  drop procedure proc_peteam_disease_top10;
go
create procedure proc_peteam_disease_top10(
  /*******************************************
       ��������ű�:������ͳ��
  ********************************************/
   @company_id int=305, --��λID
   @batch_id   varchar(200)='266' --��������ID(�����,����)
)
as
  declare 
    @exam_man_num int, --�μ���������
    @exam_woman_num int, --�μ�Ů������
    @total_num int --�μ�������
    
    select @exam_man_num=isnull(count(e.id),0) from exam_info e, customer_info c
     where e.customer_id=c.id and e.company_id=@company_id and batch_id in (@batch_id)
       and e.is_Active='Y' and c.is_Active='Y' and e.status not in ('Y', 'D') and c.sex='��'

    select @exam_woman_num=isnull(count(e.id),0) from exam_info e, customer_info c
     where e.customer_id=c.id and e.company_id=@company_id and batch_id in (@batch_id)
       and e.is_Active='Y' and c.is_Active='Y' and e.status not in ('Y', 'D') and c.sex='Ů'
       
    set @total_num=@exam_man_num+@exam_woman_num  --�μ�������
    
    declare @disease_num int --��������   
    select @disease_num=count(d.id) from exam_info e, examinfo_disease d, disease_knowloedge_lib l
	 where e.id=d.exam_info_id and d.disease_id=l.id and e.is_Active='Y' and d.isActive='Y' 
	   and e.company_id=@company_id and batch_id in (@batch_id)
  
	select top 10 row_number() OVER( ORDER BY A.total_num desc)as seq_no, @company_id as com_id, A.disease_id, A.disease_name, A.total_num, 
	  cast(cast(A.total_num/(0.01*@total_num) as decimal(6,2)) as varchar(10))+'%' as total_rate,  --�ܷ�����(������/�μ�����)
	  cast(cast(A.total_num/(0.01*@disease_num) as decimal(6,2)) as varchar(10))+'%' as total_disease_rate,  --����ռ��(������/��������)
	  B.num as man_num, cast(cast(B.num/(0.01*@total_num) as decimal(6,2)) as varchar(10))+'%' as man_rate, --���Է�����
	  cast(cast(B.num/(0.01*@disease_num) as decimal(6,2)) as varchar(10))+'%' as man_disease_rate,  --���Լ���ռ��(������/��������)
	  C.num as woman_num, cast(cast(C.num/(0.01*@total_num) as decimal(6,2)) as varchar(10))+'%' as womman_rate, --Ů�Է�����
	  cast(cast(C.num/(0.01*@disease_num) as decimal(6,2)) as varchar(10))+'%' as woman_disease_rate  --���Լ���ռ��(������/��������) 
	from
	  (select d.disease_id, l.disease_name, count(d.id) as total_num  
	   from exam_info e, examinfo_disease d, disease_knowloedge_lib l
	   where e.id=d.exam_info_id and d.disease_id=l.id 
		 and e.is_Active='Y' and d.isActive='Y' 
		 and e.company_id=@company_id and batch_id in (@batch_id)
	   group by d.disease_id, l.disease_name) as A
	left join 
	  (select d.disease_id, count(d.id) as num 
	   from exam_info e, customer_info c, examinfo_disease d, disease_knowloedge_lib l
	   where e.customer_id=c.id and e.id=d.exam_info_id and d.disease_id=l.id 
		 and e.is_Active='Y' and d.isActive='Y' and c.sex='��'
		 and e.company_id=@company_id and batch_id in (@batch_id)
	   group by d.disease_id) as B on B.disease_id=A.disease_id
	left join 
	  (select d.disease_id, count(d.id) as num 
	   from exam_info e, customer_info c, examinfo_disease d, disease_knowloedge_lib l
	   where e.customer_id=c.id and e.id=d.exam_info_id and d.disease_id=l.id 
		 and e.is_Active='Y' and d.isActive='Y' and c.sex='Ů'
		 and e.company_id=@company_id and batch_id in (@batch_id)
	   group by d.disease_id) as C on C.disease_id=A.disease_id
	   
go

--6.��������ű�:�����������ͳ��
 if exists (select * from sys.objects where name='proc_peteam_disease_age_top10' and type='P')
  drop procedure proc_peteam_disease_age_top10;
go
create procedure proc_peteam_disease_age_top10(
  /*******************************************
       ��������ű�:�����������ͳ��
  ********************************************/
   @company_id int=305, --��λID
   @batch_id   varchar(200)='266' --��������ID(�����,����)
)
as
  declare 
    @exam_man_num int, --�μ���������
    @exam_woman_num int, --�μ�Ů������
    @total_num int --�μ�������
    
    select @exam_man_num=isnull(count(e.id),0) from exam_info e, customer_info c
     where e.customer_id=c.id and e.company_id=@company_id and batch_id in (@batch_id)
       and e.is_Active='Y' and c.is_Active='Y' and e.status not in ('Y', 'D') and c.sex='��'

    select @exam_woman_num=isnull(count(e.id),0) from exam_info e, customer_info c
     where e.customer_id=c.id and e.company_id=@company_id and batch_id in (@batch_id)
       and e.is_Active='Y' and c.is_Active='Y' and e.status not in ('Y', 'D') and c.sex='Ů'
       
    set @total_num=@exam_man_num+@exam_woman_num  --�μ�������
    
    declare @disease_num int --��������   
    select @disease_num=count(d.id) from exam_info e, examinfo_disease d, disease_knowloedge_lib l
	 where e.id=d.exam_info_id and d.disease_id=l.id and e.is_Active='Y' and d.isActive='Y' 
	   and e.company_id=@company_id and batch_id in (@batch_id)
  
	select top 10 row_number() OVER( ORDER BY A.total_num desc)as seq_no, @company_id as com_id, A.disease_id, A.disease_name, A.name, A.total_num, 
	  cast(cast(A.total_num/(0.01*@total_num) as decimal(6,2)) as varchar(10))+'%' as total_rate,  --�ܷ�����(������/�μ�����)
	  cast(cast(A.total_num/(0.01*@disease_num) as decimal(6,2)) as varchar(10))+'%' as total_disease_rate,  --����ռ��(������/��������)
	  B.num as man_num, cast(cast(B.num/(0.01*@total_num) as decimal(6,2)) as varchar(10))+'%' as man_rate, --���Է�����
	  cast(cast(B.num/(0.01*@disease_num) as decimal(6,2)) as varchar(10))+'%' as man_disease_rate,  --���Լ���ռ��(������/��������)
	  C.num as woman_num, cast(cast(C.num/(0.01*@total_num) as decimal(6,2)) as varchar(10))+'%' as womman_rate, --Ů�Է�����
	  cast(cast(C.num/(0.01*@disease_num) as decimal(6,2)) as varchar(10))+'%' as woman_disease_rate  --���Լ���ռ��(������/��������) 
	from
	  (select d.disease_id, l.disease_name, s.name, count(d.id) as total_num  
	   from exam_info e, examinfo_disease d, disease_knowloedge_lib l, statAge s
	   where e.id=d.exam_info_id and d.disease_id=l.id and e.age>=s.AgeMin and e.age<=s.AgeMax
		 and e.is_Active='Y' and d.isActive='Y' 
		 and e.company_id=@company_id and batch_id in (@batch_id)
	   group by d.disease_id, l.disease_name, s.name) as A
	left join 
	  (select d.disease_id, s.name, count(d.id) as num 
	   from exam_info e, customer_info c, examinfo_disease d, disease_knowloedge_lib l, statAge s
	   where e.customer_id=c.id and e.id=d.exam_info_id and d.disease_id=l.id and e.age>=s.AgeMin and e.age<=s.AgeMax
		 and e.is_Active='Y' and d.isActive='Y' and c.sex='��'
		 and e.company_id=@company_id and batch_id in (@batch_id)
	   group by d.disease_id, s.name) as B on B.disease_id=A.disease_id and B.name=A.name
	left join 
	  (select d.disease_id, s.name, count(d.id) as num 
	   from exam_info e, customer_info c, examinfo_disease d, disease_knowloedge_lib l, statAge s
	   where e.customer_id=c.id and e.id=d.exam_info_id and d.disease_id=l.id and e.age>=s.AgeMin and e.age<=s.AgeMax
		 and e.is_Active='Y' and d.isActive='Y' and c.sex='Ů'
		 and e.company_id=@company_id and batch_id in (@batch_id)
	   group by d.disease_id, s.name) as C on C.disease_id=A.disease_id and C.name=A.name

--7.��������ű�:��������ϸͳ��
if exists (select * from sys.objects where name='proc_peteam_disease_list' and type='P')
  drop procedure proc_peteam_disease_list;
go
create procedure proc_peteam_disease_list(
  /*******************************************
       ��������ű�:��������ϸͳ��
  ********************************************/
   @company_id int=305, --��λID
   @batch_id   varchar(200)='266' --��������ID(�����,����)
)
as 
  select d.disease_id, l.disease_name, row_number() OVER(PARTITION BY d.disease_id ORDER BY e.join_date) as seq_no, 
   e.exam_num, c.user_name as name, c.sex, e.age, c.arch_num, convert(varchar(10), e.join_date,120) as exam_date, c.birthday
  from exam_info e, customer_info c, examinfo_disease d, disease_knowloedge_lib l
  where e.id=d.exam_info_id and e.customer_id=c.id and d.disease_id=l.id 
    and e.is_Active='Y' and d.isActive='Y' 
    and e.company_id=@company_id and batch_id in (@batch_id)

