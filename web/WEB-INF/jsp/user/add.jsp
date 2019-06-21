<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String contextPath=request.getContextPath();
    request.setAttribute("contextPath", contextPath);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="../css/bootstrap.min.css" rel="stylesheet">
		<link href="../css/bootstrap-theme.min.css" rel="stylesheet">
		<!-- 下面两个css设置复选框、单选框样式 -->
		<link href="../css/font-awesome.4.6.0.css" rel="stylesheet">
		<link href="../css/build.css" rel="stylesheet">
		<title>增加用户信息</title>
			<style>
			.table th, .table td {
				text-align: center;
				height: 30px;
			}
			</style><!--修改模态框 出现的位置-->
</style>
	</head>
<body>

	<div style="height: 75px; line-height: 75px; font-size: 25px; vertical-align: middle; text-align: center"
		class="bg-primary">TTMS影院管理系统</div>

	<div class="row" style="padding: 10px 10px">

		<!-- 左导航栏 -->
		<%@include file="../nav.jsp"%>

		<!-- 增加用户信息 -->
		<div class="col-md-10" style="padding-top: 3px">
			<form class="form-horizontal" role="form" action="../user/setUserType" method="post">
				<div class="form-group" style="margin-left:20px">
					<!-- 设置块 -->
					<div class="search" class="col-sm-8">
							<div class="radio radio-success radio-inline">
								<input type="radio" name="type" id="type0" value="0" checked="checked">
								<label for="type0" style="padding:0px">普通用户</label>
							</div>
							<div class="radio radio-success radio-inline">
								<input type="radio" name="type" id="type1" value="1">
								<label for="type1" style="padding:0px">管理员</label>
							</div>
							<input type="submit" class="btn btn-primary" id="submit" value="设   置"  style="margin-left:20px"  data-target="#myModal"/>&nbsp;&nbsp;
							<button type="button" class="btn btn-primary" onclick="javascript:window.location='../user/link'" >返 回</button>
                            <span style="color: red; font-weight: bold;">${setResult}</span>
					</div>
				</div>
				
				<!-- 员工信息显示-->
				<div style="padding-top:10px;"  class="col-md-4">
					<table class="table table-bordered table-hover">
				   		<tr>
				   			<th style="width:100px">
				   				<div class="checkbox checkbox-success">
									<input type="checkbox" id="checkall" name="checkall"  class="styled">
									<label for="checkall"></label>
								</div>
				   			</th>
				   			<th>编号</th>
				   			<th>姓名</th>
				   		</tr>
                        <c:forEach items="${requestScope.allEmployee }" var="user"  varStatus="status">
					   		<tr>
					   		    <th style="vertical-align: middle;">
									<div class="checkbox checkbox-success">
										<input type="checkbox" id="checkbox${status.index+1}" name="emp_no_list"  class="styled"  value="${user.emp_no}">
										<label for="checkbox${status.index+1}"></label>
									</div>
					   		    </th>
					   			<th style="vertical-align: middle;"><label for="checkbox${status.index+1}">${user.emp_no}</label></th>
					   			<th style="vertical-align: middle;"><label for="checkbox${status.index+1}">${user.emp_name}</label></th>
					   		</tr>
				   		</c:forEach>
					</table>
				</div>
			</form>
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
	       		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
	       		请选择员工，再授权！</h5>
	     </div>
	     <div class="modal-footer">
	     <button type="button" class="btn btn-primary" data-dismiss="modal">关    闭</button>
	     </div>
	   </div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>
	<!-- 小模态框结束 -->
	
	<script type="text/javascript">
		$("#checkall").click(function(){
				$("[name='emp_no_list']").prop("checked",this.checked);
		});
		
		$("#submit").click(function(){
			if($("[name='emp_no_list']").prop("checked")==false)
			{
				$('#myModal').modal({keyboard: true});
				return false;
			}
			else
				return true;
		});
	</script>


</body>
</html>