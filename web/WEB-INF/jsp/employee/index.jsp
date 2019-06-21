<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>员工信息-分页</title>
<style>
body {
	width: 100%
}

.table th, .table td {
	text-align: center;
	height: 30px;
}

#mydiv {
	height: 75px;
	line-height: 75px;
	font-size: 25px;
	vertical-align: middle;
	text-align: center
}
</style>
</head>

<body>

	<div id="mydiv" class="bg-primary">TTMS影院管理系统</div>

	<div class="row" style="padding: 10px 10px">

		<!-- 左导航栏 -->
		<%@include file="../nav.jsp"%>

		<div class="col-md-9" style="padding-top: 3px">

			<!-- 查询块 -->
			<div class="search">
				<form class="form-inline" name="myForm" action="../employee/searchByPage" method="post">
					<input type="text" class="form-control" name="employeeName" value="${searchEmployeeName}" /> <input type="hidden"
						class="form-control" name="currentPage" value="1" /> <input type="submit" class="btn btn-primary" value="查   询" />&nbsp;&nbsp;
					<input type="button" class="btn btn-danger" value="增   加"
						onclick="javascript:window.location='../employee/openAdd'" /> &nbsp;&nbsp;&nbsp;&nbsp;<span
						style="color: red; font-weight: bold;">${delResult}</span>
				</form>
			</div>

			<!-- 员工信息显示-->
			<div style="padding-top: 10px;">
				<table class="table table-bordered table-hover" style="text-align: center;">
					<tr class='success'>
						<th>用户ID</th>
						<th>编号</th>
						<th>姓名</th>
						<th>电话</th>
						<th>地址</th>
						<th>邮箱</th>
						<th colspan=2>操作</th>
					</tr>
					<c:forEach items="${requestScope.allEmployee }" var="list">
						<tr>
							<th style="vertical-align: middle;">${list.emp_id}</th>
							<th style="vertical-align: middle;">${list.emp_no}</th>
							<th style="vertical-align: middle;">${list.emp_name}</th>
							<th style="vertical-align: middle;">${list.emp_tel_num}</th>
							<th style="vertical-align: middle;">${list.emp_addr}</th>
							<th style="vertical-align: middle;">${list.emp_email}</th>
							<th>
								<H4>
									<a href="../employee/openUpdate?empId=${list.emp_id}">
										<span class="glyphicon glyphicon-edit" aria-hidden="true" />
									</a>
								</H4>
							</th>
							<th>
								<H4>
									<!-- 不能删除当前用户 -->
									<c:if test="${list.emp_no==sessionScope.user.emp_no}">
										<span class="glyphicon glyphicon-remove" aria-hidden="true" />
									</c:if>
									<c:if test="${list.emp_no!=sessionScope.user.emp_no}">
										<a href="#" onclick="mycheck('${list.emp_id}','${list.emp_name}')">
											<span class="glyphicon glyphicon-remove" aria-hidden="true" />
										</a>
									</c:if>
								</H4>
							</th>
						</tr>
					</c:forEach>
				</table>
			</div>

			<!-- 分页 -->
			<div class="text-center">
				<ul class="pagination">
					<li><a href="../employee/searchByPage?currentPage=1&employeeName=${searchEmployeeName}">首页</a></li>
					<li><a
						href="../employee/searchByPage?currentPage=${currentPage<2?1:currentPage-1}&employeeName=${searchEmployeeName}">上一页</a></li>
					<li><a
						href="../employee/searchByPage?currentPage=${(currentPage+1)>allPageCount?allPageCount:(currentPage+1)}&employeeName=${searchEmployeeName}">下一页</a></li>
					<li><a href="../employee/searchByPage?currentPage=${allPageCount}&employeeName=${searchEmployeeName}">末页</a></li>
				</ul>
			</div>
		</div>
	</div>

	<!-- 小模态框开始：提示选择员工再设置 -->
	<div class="modal fade bs-example-modal-sm" id="myModal" tabindex="-1" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header bg-primary">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">警告！</h4>
				</div>
				<div class="modal-body">
					<h5 style="">
						<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true" /> 
						<span id="delEmpName" />
					</h5>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="delEmployee">确 定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal" data-dismiss="modal">取 消</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- 小模态框结束 -->

	<script type="text/javascript">
	var empId="";
	//点击删除图标，打开模态框确认
    function mycheck(emp_id,emp_name)
    {
    	//将要删除的emp_id赋值给全局变量empId
    	empId=emp_id;
    	$('#delEmpName').text("确定要删除<<" + emp_name + ">>吗？");
    	$('#myModal').modal({keyboard: true});
    }
    //点击模态框的确定按钮时，调用controller删除该员工
    $('#delEmployee').click(function(){
    	window.location="../employee/delete?empId="+empId+"&employeeName=${searchEmployeeName}&currentPage=${currentPage}";
    });
	</script>

</body>
</html>