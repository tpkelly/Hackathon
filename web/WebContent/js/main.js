$(function() {
	var self = this;
   
    var source   = $("#template").html();
    var template = Handlebars.compile(source);
    
    function getData() {
    	$.getJSON("getResult")
    		.done(function(data) {
    			$("#content").append(template(data));
    			
    			//add chart
    			var chart = new self.chartData();
    			chart.series = data.chartData;
    			$('#chart').highcharts(chart);
	    	}, this);
    };
    
    
    getData();
    
    
    this.chartData = function() {
    	return {
    
            title: {
                text: 'Investments',
            },
            yAxis: [{
	            	title: {
	                    text: 'Close price',
	                },
                    min: 0
                }, {
	                title: {
	                    text: 'Investments',
	                },
                    min: 0,
	                opposite: true
            }],
            tooltip: {
                enabled: false
            },
            plotOptions: {
            	line: {
            		marker: {
            			enabled: false
            		},
		            enableMouseTracking: false
            	}
            },
            chart: {
            	backgroundColor:'transparent'
            }
    	};
    };
});

function formatMoney (amount) {
	amount = amount + "";
    var j = (j = amount.length) > 3 ? j % 3 : 0;
    return "\xA3" + (j ? amount.substr(0, j) + ',' : "") + amount.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + ',');
}

Handlebars.registerHelper('formatMoney', formatMoney);

