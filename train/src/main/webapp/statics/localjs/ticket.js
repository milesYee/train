
function orderTicket(trainNo){
	$.ajax({
		type: "POST",
		url: "orderTicket.html",
		data: {"trainNo":trainNo},
		dataType: "html",
		timeout:60000,
		error: function () {
			alert("订票失败，系统错误");
			window.location.href = "backend/trainList.html";
		},
		success: function(result){
			if(result == "success"){
				window.location.href = "backend/trainList.html";
				alert("订票成功了");
			}else if(result == "failed"){
				window.location.href = "backend/trainList.html";
				alert("订票失败");
			}
		},
	});
}





