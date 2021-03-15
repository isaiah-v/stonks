if(qs['data']) {
	$(document).ready(function(){
		$.ajax({
			url:'service/simulation/graph',
			type:"POST",
			contentType:"application/json; charset=utf-8",
			dataType:"json",
			data: Base64Url.decode(qs['data'])
	    }).then(function(data) {
	    	new Graph($("#graph")[0], data);
	    });
	});	
} else if(qs['symbol']) {
	$(document).ready(function(){
		$.ajax({
			url:'service/simulation/graph?symbol=' + qs['symbol'],
			type:"GET",
			dataType:"json"
	    }).then(function(data) {
	    	new Graph($("#graph")[0], data);
	    });
	});
}