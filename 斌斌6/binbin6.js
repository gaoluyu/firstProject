var text = document.getElementById("input");
var inputSelect = document.getElementById("inputSelect");
var bt = document.getElementById("button");
var search = document.getElementById("search");
var numList = document.getElementById("numList");
var inputList = [];



function buttonClick(){
	var target = event.target;
	var value = verify(text.value);
	if(target.nodeName === "BUTTON"){
		switch(target.className){
			case "bt leftIn":
			   for(var i = 0; i < value.length; i++){
		          inputList.unshift(value[i]);
	            }
			 display(value);
			 break;
			case "bt rightIn":
			    for(var i = 0; i < value.length; i++){
			    	inputList.push(value[i]);
			    }
			 display(value);
			 break;
			case "bt leftOut":
             if(numList.children.length > 0){
			   var deleteNum = inputList.shift();
			   alert("您删除了" + deleteNum);
			   display();
			 }
			 else{
			 	alert("没有可供您删除的值！");
			 }
			 break;
			case "bt rightOut":
			if(numList.children.length > 0){
			   var deleteNum = inputList.pop();
			   alert("您删除了" + deleteNum);
			   display();
			 }
			 else{
			 	alert("没有可供您删除的值！");
			 }
			 break;
		}
	}
}
function searchInit(){
	var target = event.target;
	var keyWord= inputSelect.value;
	inputSelect.value = "";
	var arr = [];
	var reg = new RegExp(keyWord);
	for(var i = 0; i < numList.children.length; i++){
		console.log(numList.children[i].innerHTML);
		if(numList.children[i].innerHTML.match(reg)){
			numList.children[i].style.backgroundColor = "red";
		}else{
			numList.children[i].style.backgroundColor = "#00ffff";
		}

	}
}

//演示
function display(value){
	text.value = "";
	var i = 0;
	while(numList.children.length > 0){
		numList.removeChild(numList.children[0]);
	}
	var fragment = document.createDocumentFragment();
	for(var j = 0; j<inputList.length; j++){
		var li = document.createElement("li");
		li.innerHTML = inputList[j];
		fragment.appendChild(li);
	}
	numList.appendChild(fragment);

}



function init(){
	bt.addEventListener("click",buttonClick,false);
	search.addEventListener("click",searchInit,false);

}
function verify(value){
	var inputItem = value.replace(/[^0-9a-zA-Z\u4e00-\u9fa5]+/g,",").split(",");
		var arr = [];
        for(var i = 0; i < inputItem.length; i++){
        	if(inputItem[i]){
        		arr.push(inputItem[i]);
        	}
	    }
		return arr;
}


init();










































