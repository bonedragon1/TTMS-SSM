<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 下面两个css设置复选框、单选框样式 -->
<link href="../css/font-awesome.4.6.0.css" rel="stylesheet">
<link href="../css/build.css" rel="stylesheet">
<title>修改用户信息</title>
<style>
.table th, .table td {
	text-align: center;
	height: 30px;
}
</style>
</head>
<body>

	<div style="height: 75px; line-height: 75px; font-size: 25px; vertical-align: middle; text-align: center"
		class="bg-primary">TTMS影院管理系统</div>
	<div class="row" style="padding: 10px 10px">

		<!-- 左导航栏 -->
		<%@include file="../nav.jsp"%>

		<!-- 更新员工信息 -->
		<div class="col-md-8" style="padding-top: 10px">
			<form class="form-horizontal" role="form" action="../user/update" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label for="emp_no" class="col-sm-2 control-label">用户编号</label>
					<div class="col-sm-6">
						<!-- 用户编号不能修改，只读形式提交-->
						<input type="text" class="form-control" name="emp_no" value="${user.emp_no}" readonly="readonly">
					</div>
				</div>
				<div class="form-group">
					<label for="emp_name" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-6">
						<!-- 用户不需要提交-->
						<input type="text" class="form-control" name="emp_no" value="${user.employee.emp_name}" disabled="disabled">
					</div>
				</div>
				<div class="form-group">
					<label for="emp_pass" class="col-sm-2 control-label">密码</label>
					<div class="col-sm-6">
						<input type="password" class="form-control" id="emp_pass" name="emp_pass" pattern="[a-zA-Z0-9]{6,20}"
							required="required" oninvalid="setCustomValidity('请输入大小写字母和数字,长度6-20位!')" oninput="setCustomValidity('')"
							value="${user.emp_pass}">
					</div>
				</div>
				<div class="form-group">
					<label for="emp_addr" class="col-sm-2 control-label">类型</label>
					<div class="col-sm-6">
						<!-- 如果当前登录用户是管理员，可以修改所有用户类型-->
						<c:if test="${sessionScope.user.type==1}">
							<!-- 需根据返回结果设置缺省值 -->
							<div class="radio radio-success radio-inline">
								<input type="radio" name="type" id="type0" value="0" ${user.type==0?"checked":""}>
								<label for="type0" style="padding: 0px">普通用户</label>
							</div>
							<div class="radio radio-success radio-inline">
								<input type="radio" name="type" id="type1" value="1" ${user.type==1?"checked":""}>
								<label for="type1" style="padding: 0px">管理员</label>
							</div>
						 </c:if>
						 <!-- 如果当前登录用户不是管理员，类型不能修改，以隐藏框形式提交-->
						 <c:if test="${sessionScope.user.type!=1}">
						 	<input type="text" class="form-control" name="notSubmit" value="${user.type==1?'管理员':'普通用户'}" disabled="disabled">
						 	<input type="hidden" class="form-control" name="type" value="${user.type}"  readonly="readonly">
						 </c:if>
					</div>
				</div>
				<div class="form-group">
					<label for="emp_email" class="col-sm-2 control-label">头像</label>
					<div class="col-sm-6">
					   <input type="hidden" name="head_path" value="${user.head_path}" readonly="readonly">
					   <img id="myImg" src="../${user.head_path}" width="100px" height="100px" class="img-circle" onclick="$('#i-file').click();">
				       <div class="input-group" style="padding-top:5px">
					       <input id='location' class="form-control" onclick="$('#i-file').click();" readonly="readonly">
				           <label class="input-group-btn">
				               <input type="button" id="i-check" value="请选择新头像" class="btn btn-primary" onclick="$('#i-file').click();">
				           </label>
				       </div>
   						<input type="file" name="headPic" id='i-file'  accept=".jpg, .png, .gif" onchange="$('#location').val($('#i-file').val());" style="display: none">
				   </div>
				</div>
				<div class="form-group" style="padding-top:10px">
					<div class="col-sm-2 control-label" style="color: red; font-weight: bold;">${message}</div>
					<div class="col-sm-6" >
						<button type="submit" class="btn btn-primary">修 改</button>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>