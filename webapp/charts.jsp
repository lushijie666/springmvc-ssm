<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>

<div id="chart1" style="min-width:200px;height:250px"></div>
<div id="chart2" style="min-width:200px;height:300px"></div>
<div id="chart3" style="min-width:200px;height:300px"></div>


</body>
</html>

<script>
var colors = ['#7cb5ec', '#434348', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#2b908f', '#f45b5b', '#91e8e1']

$(function(){
	initChart();
})

function initChart(){
	$.ajax({
        url: _basePath + "/charts/data.do",
        dataType: "json",
        success: function (result) {
        	if(result.code == 0){
        		var totalCount = 0;
            	var data = result.data;  
            	for (var key in data) {
            		totalCount += data[key];
                }
        		var arr1 = [];
            	for(var key in data){
            		var temp = [];
            		temp.push(key);
            		temp.push(data[key] / totalCount * 100);
            		arr1.push(temp);
            	}
            	var chart1 = $('#chart1').highcharts();
            	chart1.series[0].setData(arr1);

            	var arr2 = [];
            	var i = 0;
            	for(var key in data){
            		var temp = {};
            		temp.color = colors[i];
            		temp.y = data[key]
            		arr2.push(temp);
            		i++;
            	}
            	var chart2 = $('#chart2').highcharts();
            	chart2.series[0].setData(arr2);
            	
            	var arr3 = [];
            	var i = 0;
            	for(var key in data){
            		var temp = {};
            		temp.y = data[key]
            		arr3.push(temp);
            		i++;
            	}
            	var chart3 = $('#chart3').highcharts();
            	chart3.series[0].setData(arr3);
        	}
        }
    });
}

$('#chart1').highcharts({
	title: {
		text: '扇形图'
	},
	tooltip: {
		headerFormat: '{series.name}<br>',
		pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
	},
	plotOptions: {
		pie: {
			allowPointSelect: true,
			cursor: 'pointer',
			dataLabels: {
				enabled: true,
				format: '<b>{point.name}</b>: {point.percentage:.1f} %',
				style: {
					color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
				}
			},
			states: {
				hover: {
					enabled: false
				}  
			},
			slicedOffset: 20,
			point: { 
				events: {
					mouseOver: function() {
						this.slice();
					},
					mouseOut: function() {
						this.slice();
					},
					click: function() {
						return false;
					}
				}
			}
		}
	},
	series: [{
		type: 'pie',
		name: '年龄分布占比',
		data: []
	}]
});

$('#chart2').highcharts({
	title: {
		text: '柱状图'
	},
	tooltip: {
		headerFormat: '{series.name}<br>',
		pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
	},
	xAxis: {
	        categories: ['<20', '20-40', '>40']
	},
	plotOptions: {
		column:{
		}
	},
	series: [{
		type: 'column',
		name: '年龄分布',
		data: []
	}]
});

$('#chart3').highcharts({
	title: {
		text: '折线图'
	},
	tooltip: {
		headerFormat: '{series.name}<br>',
		pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
	},
	xAxis: {
	        categories: ['<20', '20-40', '>40']
	},
	plotOptions: {
		line:{
		}
	},
	series: [{
		type: 'line',
		name: '年龄走势',
		data: [],
		color: '#FF0000'
	}]
});


</script>