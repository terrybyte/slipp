//답변하기 버튼 클릭
$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
	e.preventDefault();
	console.log("addAnswer!");
	var queryString = $(".answer-write").serialize();
	console.log("queryString: " + queryString);
	
	var url = $(".answer-write").attr("action");
	console.log("url: " + url);
	
	$.ajax({
		type: 'post',
		url: url,
		data: queryString,
		dataType: 'json',
		error: onError,
		success: onSuccess
	});
}

function onError(){
	
}

function onSuccess(data, status) {
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(data.writer.userID, data.createDate, data.contents, data.question.id, data.id);
	$(".qna-comment-slipp-articles").prepend(template);
	$("textarea[name=contents]").val("");
}

$("a.link-delete-article").click(deleteAnswer);

function deleteAnswer(e) {
	e.preventDefault();
	var delBtn = $(this);
	var url = delBtn.attr("href");
	
	console.log(url);
	
	if (!confirm("del?")) {
		return;
	}
	
	$.ajax({
		type: 'delete',
		url: url,
		dataType: 'json',
		error: function(xhr, status){
			console.log(xhr);
			console.log(status);
		},
		success: function(data, status){
			console.log(data);
			console.log(status);
			if (data.valid) {
				delBtn.closest("article").remove();
			} else {
				alert(data.errorMessage);
			}
		}
	});
	
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};