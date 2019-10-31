<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>AJAX练习</title>

<script type="text/javascript" src="/js/jquery-3.4.1.js"></script>

<script type="text/javascript">   
//页面加载完成之后运行js
$(function(){
	//alert("jquery调用正确");
	//常见ajax方式  $.get(url,data,callback,datatype) get post
	 $.get("/findAll-ajax",function(result){
		//alert(result);
		//遍历
		$(result).each(function(index,user){
			//alert(index);
			//var user = result[index];
			var id = user.id;
			var name = user.name;
			var age =user.age;
			var sex =user.sex;
			//alert(name);

			var tr = "<tr align='center'><td>"+id+"</td><td>"+name+"</td><td>"+age+"</td><td>"+sex+"</td></tr>"
			$("#tab").append(tr);	
			});
		}); 

//ajax 基础版本

/* $.ajax({
	type: "get",
	url: "/findAll-ajax",
    // data:"{id:100}",
    dataType: "json",
    asysc: true
    success: function(result){
        alert("成功");
        },
    error: function(result){
        alert("失败");
        }
}); */
/* 
$.getJSON(1,2,3) */

/*
 * data 2种格式
 */

		
})

</script>
</head>
<body>
	<table id="tab" border="1px" width="65%" align="center">
		<tr>
			<td colspan="6" align="center"><h3>学生信息</h3></td>
		</tr>
		<tr>
			<th>编号</th>
			<th>姓名</th>
			<th>年龄</th>
			<th>性别</th>
			<th></th>
		</tr>
	
	
	
	</table>
</body>

</html>