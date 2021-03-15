$(document).ready(function(){
	$.ajax({
        url: "test2/one?back=0"
    }).then(function(data) {
    	drawCurveTypes(data);
    });
});

google.charts.load('current', {
	packages : [ 'corechart', 'line' ]
});
//google.charts.setOnLoadCallback(drawCurveTypes);

function drawCurveTypes(gData) {
	var data = new google.visualization.DataTable();
	data.addColumn('datetime', 'x');
	
	gData.lines.forEach(s => {
		data.addColumn('number', s);
	});
	
	
	var rows = Array();
	gData.points.forEach(p => {
		var row = Array();
		row.push(new Date(Date.parse(p.time)));
		p.data.forEach(d => {
			row.push(d.v);
		});
		
		rows.push(row);
	});
	
	data.addRows(rows);

	var options = {
		title: 'test',
		height: 2000,
		hAxis : {
			title : 'Time'
		},
		vAxis : {
			title : 'Popularity'
		},
		focusTarget: 'category',
		crosshair: {
	          color: '#000',
	          trigger: 'both',
	          orientation: 'vertical'
	        }
	};

	var chart = new google.visualization.LineChart(document
			.getElementById('chart_div'));
	chart.draw(data, options);
}
