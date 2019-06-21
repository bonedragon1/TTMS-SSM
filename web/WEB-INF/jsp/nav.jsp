<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>员工信息</title>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/bootstrap-theme.min.css" rel="stylesheet">
<style>
	.modal.fade.in { top:180px; }
</style>
<script src="../js/jquery-2.1.3.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
</head>
<body>
	<div class="col-md-2">
		<div class="panel panel-primary">
				<div  class="panel-heading">
					<img src="../${sessionScope.user.head_path}" width="50px" height="50px" class="img-circle">&nbsp;
					<a href="../user/openUpdate?empNo=${sessionScope.user.employee.emp_no}" style="color:#FFFFFF;">${sessionScope.user.employee.emp_name}</a>
					您好！
				</div>
				<div class="panel-body">
				<table class="table table-hover" style="text-align:center">
					<c:if  test="${sessionScope.user.type==1}">
						<tr><td><img src="../images/renyuan.png" width="24px">&nbsp;&nbsp;<a href="../employee/link">员工管理</a></td></tr>
						<tr><td><img src="../images/denglu.png" width="24px">&nbsp;&nbsp;<a href="../user/link">登陆管理</a></td></tr>
					</c:if>
					<tr><td><img src="../images/yingting.png" width="24px">&nbsp;&nbsp;<a href="#" id="mytip1">影厅管理</a></td></tr>
					<tr><td><img src="../images/zuowei.png" width="24px">&nbsp;&nbsp;<a href="#" id="mytip2">座位管理</a></td></tr>
					<tr><td><img src="../images/yingpian.png" width="24px">&nbsp;&nbsp;<a href="#" id="mytip3">影片管理</a></td></tr>
					<tr><td><img src="../images/paipianjihua.png" width="24px">&nbsp;&nbsp;<a href="#" id="mytip4">排片计划</a></td></tr>
					<tr><td><img src="../images/shoupiao.png" width="24px">&nbsp;&nbsp;<a href="#" id="mytip5">售票管理</a></td></tr>
					<tr><td><img src="../images/tongji.png" width="24px">&nbsp;&nbsp;<a href="#" id="mytip6">票房统计</a></td></tr>
					<tr><td><img src="../images/tuichu.png" width="24px">&nbsp;&nbsp;<a href="../exit">退出系统</a></td></tr>
					<tr><td></td></tr>
				</table>
			  </div>
		</div>
	</div>
	
	<!-- 小模态框开始：提示选择员工再设置 -->
	<div class="modal fade bs-example-modal-sm" id="navModal" tabindex="-1" role="dialog">
	  <div class="modal-dialog modal-sm">
	   <div class="modal-content">
	     <div class="modal-header bg-primary"  style="text-align:left">
	      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	      <h4 class="modal-title">警告！</h4>
	     </div>
	     <div class="modal-body">
	       	<h5 style="">
	       		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
	       		你们自己做！</h5>
	     </div>
	     <div class="modal-footer">
	     <button type="button" class="btn btn-primary" data-dismiss="modal">关    闭</button>
	     </div>
	   </div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>
	<!-- 小模态框结束 -->
	
	<script type="text/javascript">
    	$("a[id^='mytip']").click(function(){
			$('#navModal').modal({keyboard: true});
		});
	</script>
</body>
</html>