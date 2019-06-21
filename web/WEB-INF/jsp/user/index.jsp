<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登陆信息管理</title>
<style>
	body {width:100%}
	.table th, .table td {  
	text-align: center;
	height:30px;
	}
</style>
</head>
<body>

<div style="height:75px;line-height:75px;font-size:25px;vertical-align: middle;text-align: center" class="bg-primary">TTMS影院管理系统</div>

<div class="row" style="padding:10px 10px">

	<!-- 左导航栏 -->
	<%@include file="../nav.jsp" %>
    
	<div class="col-md-9" style="padding-top:3px">
	
		<!-- 查询块 -->
		<div class="search" >
			<form class="form-inline" name="myForm" action="../user/searchByEmpNo" method="post">
				<input type="text" class="form-control" name="empNo" value="${searchEmpNo}" placeholder="请输入员工编号"/>
				<input type="submit" class="btn btn-primary" value="查   询" />&nbsp;&nbsp;
				<input type="button" class="btn btn-danger" value="增   加" onclick="javascript:window.location='../user/openAdd'" />
				&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red; font-weight: bold;">${delResult}</span>
			</form>
		</div>
		
		<!-- 员工信息显示-->
		<div style="padding-top:10px;">
			<table class="table table-bordered table-hover">
		   		<tr class='success'>
		   			<th>人员</th>
		   			<th>姓名</th>
		   			<th>编号</th>
		   			<th>密码</th>
		   			<th>用户类型</th>
		   			<th colspan=2>操作</th>
		   		</tr>
		   		<c:forEach items="${requestScope.allUser}"  var="currentUser">
		   		<tr>
		   		    <th><img src="../${currentUser.head_path}" width="40px" height="40px" class="img-circle"></th>
		   			<th style="vertical-align: middle;">${currentUser.employee.emp_name}</th>
		   			<th style="vertical-align: middle;">${currentUser.emp_no}</th>
		   			<th style="vertical-align: middle;">******</th>
		   			<th style="vertical-align: middle;">${currentUser.type=="1"?"管理员":"普通用户"}</th>
		   			<th style="vertical-align: middle;">
		   				<H4>
		   			    	<a href="../user/openUpdate?empNo=${currentUser.emp_no}"><span class="glyphicon glyphicon-edit" aria-hidden="true" alt="修改"/></a>
		   			    </H4>
		   			</th>
		   			<!-- 如果是当前登录用户，则不能点击删除 -->
		   			<th style="vertical-align: middle;">
		   				<H4>
				   			<c:if  test="${currentUser.emp_no==sessionScope.user.emp_no}">
				   			     <span class="glyphicon glyphicon-remove" aria-hidden="true" alt="删除"/>
							</c:if>
							<c:if test="${currentUser.emp_no!=sessionScope.user.emp_no}">
								<a href="#" onclick="mycheck('${currentUser.emp_no}','${currentUser.employee.emp_name}')">
				   			    <span class="glyphicon glyphicon-remove" aria-hidden="true" alt="删除"/></a>
							</c:if>
						</H4>
		   			</th>
		   		</tr>
		   		</c:forEach>
			</table>
		</div>
		
	</div>
</div>

	<!-- 小模态框开始：提示选择员工再设置 -->
	<div class="modal fade bs-example-modal-sm" id="myModal"  tabindex="-1" role="dialog" >
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header bg-primary">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title">警告！</h4>
				</div>
				<div class="modal-body">
					<h5>
						<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true" /> 
						<span id="delEmpName" />
					</h5>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="delEmployee">确 定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal" data-dismiss="modal">取 消</button>
				</div>
			</div>
		</div>
		<!-- /.modal -->
	</div>
	<!-- 小模态框结束 -->

	<script type="text/javascript">
	var empNo="";
	//点击删除图标，打开模态框确认
    function mycheck(emp_no,emp_name)
    {
    	//将要删除的emp_id赋值给全局变量empId
    	empNo=emp_no;
    	$('#delEmpName').text("确定要删除<<" + emp_name + ">>吗？");
    	$('#myModal').modal({keyboard: true});
    }
    //点击模态框的确定按钮时，调用controller删除该员工
    $('#delEmployee').click(function(){
    	window.location="../user/delete?empNo="+empNo+"&headPath=${currentUser.head_path}&searchEmpNo=${searchEmpNo}"
    });
	</script>
</body>
</html>