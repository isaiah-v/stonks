const charTemplate = `
	<div class="c_chart">
		<div>
			<div class="c_chartArea">
				A
			</div>
			<div class="y_scale">
				B
			</div>
		</div>
		<div>
			<div class="x_scale">
				C
			</div>
		</div>
	</div>
`;

class Graph {
	constructor(parent, data) {
		
		var divHeight = 1 / data.series.length;
		
		data.series.forEach(s => {
			var div = document.createElement("DIV");
			div.width = '100%';
			div.height = parent.offsetHeight * divHeight;
			
			parent.appendChild(div);
			
			var canvas = document.createElement("CANVAS");
			
			var width = div.offsetWidth - 400;
			var height = parent.offsetHeight * divHeight;
			
			canvas.width = width;
			canvas.height = height;
			
			div.appendChild(canvas);

			this._drawCanvas(canvas, data, s);
		});
		
	}
	
	_drawCanvas(canvas, data, series) {
		var width = canvas.offsetWidth;
		var height = canvas.offsetHeight;
		
		var yRange = this._getRange(series, data);
		var xMax = this._getCount(data);
		
		var calc = new PixelGraphCalculator(width, height, 0, xMax, yRange.min, yRange.max)
		var scale = this._getVerticalScale(yRange);
		
		var ctx = canvas.getContext("2d");
		
		this._preDrawGraphType(series, calc, ctx, xMax, yRange);
		
		// draw indicator lines
		ctx.beginPath();
		ctx.strokeStyle = "#DBDBDB";
		var start = Math.round((scale - (yRange.min % scale)) + yRange.min);
		while(start<yRange.max) {
			var pixStart = calc.getPixelPoint({x: 0, y: start});
			var pixEnd = calc.getPixelPoint({x: xMax, y: start});
			
			ctx.moveTo(pixStart.x, pixStart.y);
			ctx.lineTo(pixEnd.x, pixEnd.y);
			
			start += scale;
		}
		ctx.stroke();
		
		// draw graph lines
		ctx.beginPath();
		ctx.strokeStyle = "#000000";
		series.lines.forEach(l => {
			
			var lineData = data.lines[l].data;
			lineData.forEach( (d, index) => {
				var pix = calc.getPixelPoint({x: index, y: d});
				
				if(index==0) {
					ctx.moveTo(pix.x, pix.y);
				} else {
					ctx.lineTo(pix.x, pix.y);
				}
			});
		});
		ctx.stroke();
		
		// draw graph notes
		
		for (const [key, value] of Object.entries(data.notes)) {
			ctx.beginPath();
			ctx.lineWidth = 5;
			
			var pixBottom = calc.getPixelPoint({x: key, y: yRange.min});
			var pixTop = calc.getPixelPoint({x: key, y: yRange.max});
			
			if(value["BUY"]) {
				ctx.strokeStyle = 'rgba(0, 255, 0, 0.2)';
			} else if (value["SELL"]) {
				ctx.strokeStyle = 'rgba(255, 0, 0, 0.2)';
			} else {
				ctx.strokeStyle = 'rgba(0, 0, 0, 0.2)';
			}
			
			ctx.moveTo(pixBottom.x, pixBottom.y);
			ctx.lineTo(pixTop.x, pixTop.y);
			
			ctx.stroke();
		}
	}
	
	_preDrawGraphType(series, calc, ctx, xMax, yRange) {
		var type = series.properties["type"];
		
		if(type==="MinMax") {
			var min = series.properties["min"];
			if(min) {
				min = parseFloat(min);
				
				var pixStart = calc.getPixelPoint({x: 0, y: min});
				var pixEnd = calc.getPixelPoint({x: xMax, y: min});
				
				ctx.save();
				ctx.beginPath();
				ctx.fillStyle = 'rgba(255, 0, 0, 0.075)';
				ctx.fillRect(pixStart.x, pixStart.y, pixEnd.x, pixEnd.y);
				ctx.stroke();
				ctx.restore();
				
				ctx.save();
				ctx.beginPath();
				ctx.setLineDash([5, 5]);
				ctx.strokeStyle = 'rgba(0, 0, 0, 0.5)';
				ctx.lineWidth = 2;
				ctx.moveTo(pixStart.x, pixStart.y);
				ctx.lineTo(pixEnd.x, pixEnd.y);
				ctx.stroke();
				ctx.restore();
			}
			
			var max = series.properties["max"];
			if(max) {
				max = parseFloat(max);
				var pixStart = calc.getPixelPoint({x: 0, y: max});
				var pixEnd = calc.getPixelPoint({x: xMax, y: max});
				
				ctx.save();
				ctx.beginPath();
				ctx.fillStyle = 'rgba(0, 255, 0, 0.075)';
				ctx.fillRect(0, 0, pixEnd.x, pixEnd.y);
				ctx.stroke();
				ctx.restore();
				
				ctx.save();
				ctx.beginPath();
				ctx.setLineDash([5, 5]);
				ctx.strokeStyle = 'rgba(0, 0, 0, 0.5)';
				ctx.lineWidth = 2;
				ctx.moveTo(pixStart.x, pixStart.y);
				ctx.lineTo(pixEnd.x, pixEnd.y);
				ctx.stroke();
				ctx.restore();
			}
		}
		
		if(type==="Zero") {
			var pixStart = calc.getPixelPoint({x: 0, y: 0});
			var pixEnd = calc.getPixelPoint({x: xMax, y: 0});
			
			ctx.save();
			ctx.setLineDash([5, 5]);
			ctx.strokeStyle = 'rgba(0, 0, 0, 0.5)';
			ctx.lineWidth = 2;
			ctx.moveTo(pixStart.x, pixStart.y);
			ctx.lineTo(pixEnd.x, pixEnd.y);
			ctx.stroke();
			ctx.restore();
		}
	}
	
	_getRange(series, data) {
		var min = Number.MAX_SAFE_INTEGER;
		var max = Number.MIN_SAFE_INTEGER;
		
		series.lines.forEach(l => {
			var lineData = data.lines[l].data;
			
			lineData.forEach( d => {
				min = Math.min(min, d);
				max = Math.max(max, d);
			});
		});
		
		return {min: min, max: max};
	}
	
	_getCount(data) {
		return data.times.length;
	}
	
	_getVerticalScale(range) {
		var diff = range.max - range.min;
		
		var x = diff/5.0;
		if(x >= 1) {
			var tens = 1;
			while(x >= 10) {
				tens = tens * 10;
				x = x / 10.0;
			}
			
			x = Math.round(x);
			x = x * tens;
			
			return x;
		} else {
			var tens = 1;
			while(x <= 1) {
				tens = tens / 10;
				x = x * 10.0;
			}
			
			x = Math.round(x);
			x = x * tens;
			
			return x;
		}
	}
}

class LinearEquation {
	constructor(p1, p2) {
		var m = (p2.y - p1.y) / (p2.x - p1.x);
		var b = p1.y - (m * p1.x);
		
		this.m = m;
		this.b = b;
	}
	
	/**
	 * y = mx + b
	 */
	findY(x) {
		return (this.m * x) + this.b
	}
	
	/**
	 * x = (y-b)/m
	 */
	findX(y) {
		return (y - this.b) / this.m
	}
}

/**
 * Converts a pixle location to graph location and vice versa 
 */
class PixelGraphCalculator {
	
	/**
	 * @param width: the area width in pixels
	 * @param height: the area width in pixels
	 * @param xMin: the graph's minimum value along the x-axis (most left graph value)
	 * @param xMax: the graph's maximum value along the x-axis (most right graph value)
	 * @param yMin: the graph's minimum value along the y-axis (most bottom graph value)
	 * @param yMax: the graph's maximum value along the y-axis (most top graph value)
	 */
	constructor(width, height, xMin, xMax, yMin, yMax) {
		// x = pixel value; y = graph value
		var left = {x: 0, y: xMin};
		var right = {x: width, y: xMax};
		this.xEq = new LinearEquation(left, right);
		
		// x = pixel value; y = graph value
		var top = {x: 0, y: yMax};
		var bottom = {x: height, y: yMin};
		this.yEq = new LinearEquation(top, bottom);
	}
	
	getGraphPoint(pixlePoint) {
		return {
			x: this.xEq.findY(pixlePoint.x),
			y: this.yEq.findY(pixlePoint.y)
		}
	}
	
	getPixelPoint(graphPoint) {
		return {
			x: this.xEq.findX(graphPoint.x),
			y: this.yEq.findX(graphPoint.y),
		}
	}
}