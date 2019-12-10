$(document).ready(function(){
  $(".day-wrapper").slick({
  infinite: false,
  speed: 300,
  slidesToShow: 7,
  nextArrow: $("#next"),
  prevArrow: $("#prev")
  });
});

var dayNArray = new Array("Sun","Mon","Tue","Wed","Thu","Fri","Sat");
var monthArray = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");

var currDate = new Date();
var auxDate = new Date();
var date = new Date();

var forward = 0;
var ifForwardLast = false;


for (var i=0;i<dayNArray.length;i++){

	date.setDate(currDate.getDate() + i);
	var currDay = date.getDay();
	var currMonth = date.getMonth();

	$('#dayN'+i).html(dayNArray[currDay]);
	$('#day'+i).html(date.getDate());
	$('#month'+i).html(monthArray[currMonth]);
}

function moveForward(){

	if(forward < 52){
		if(ifForwardLast==false){
			forward++;
		}

	

		for (var i=0;i<dayNArray.length;i++){

		var d =   new Date();

	 	var dd = d.getDate();
	    var mm = d.getMonth();
	    var yyyy = d.getFullYear();
	    var auxDate= new Date(yyyy, mm, dd + (7*forward) + i);

		var currDay = auxDate.getDay();
		var currMonth = auxDate.getMonth();

		$('#dayN'+i).html(dayNArray[currDay]);
		$('#day'+i).html(auxDate.getDate());
		$('#month'+i).html(monthArray[currMonth]);
		}

		forward++;
		ifForwardLast = true;
	}
}

function moveBackward(){

	if(forward>0){

		if(ifForwardLast == true){
			forward--;
		}

		forward --;

		for (var i=0;i<dayNArray.length;i++){
			var d =   new Date();

			var dd = d.getDate();
			var mm = d.getMonth();
			var yyyy = d.getFullYear();
			var auxDate= new Date(yyyy, mm, dd + (7*forward ) + i);
			var currDay = auxDate.getDay();
			var currMonth = auxDate.getMonth();

			$('#dayN'+i).html(dayNArray[currDay]);
			$('#day'+i).html(auxDate.getDate());
			$('#month'+i).html(monthArray[currMonth]);
			ifForwardLast= false;
		}
		
	}

}

