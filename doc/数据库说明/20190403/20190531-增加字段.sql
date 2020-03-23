alter table view_exam_detail add pacs_req_code varchar(20)   -----申请单号
			update view_exam_detail set pacs_req_code=(select ei.pacs_req_code from pacs_summary ei where ei.id=pacs_id)