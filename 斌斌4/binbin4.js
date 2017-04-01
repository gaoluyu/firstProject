var list = document.getElementById("numList");
var bt = document.getElementById("button");
var input = document.getElementById("number");

init();


function init(){ 
	initClickButton();
}

var inputList = [];
function initClickButton(){
	bt.addEventListener("click",clickButton,false);
}

function clickButton(){
	var target = event.target;
	var value = input.value;
	// console.log(value);
	if(target.nodeName === "BUTTON"){
		input.value = "";
		switch(target.className){
			case "bt leftIn":
			     if(!Number(value) || value ===""){

			     	// var newValue = document.createElement('li');
			     	alert("请输入正确的数字！");
			     	
			     	// console.log(value;)
			     	break;
			     }
			     inputList.unshift(Number(value));
			     displayIn(inputList);
			     break;


			case "bt rightIn":
			 if(!Number(value) || value ===""){
			     	// var newValue = document.createElement('li');
			     	alert("请输入正确的数字！");
			     	break;
			     }
			 inputList.push(Number(value));
			 displayIn(inputList);
			 break;
			case "bt leftOut":
			var shiftNum = inputList.shift();
			if(shiftNum){
				displayOut(inputList);
				alert("您删除了"+shiftNum);

			}else{
				alert("已经没有可以删除的数了！");
			}
			break;
			case "bt rightOut":
			var popNum = inputList.pop();
			if(popNum){
				 displayOut(inputList);
			     alert("您删除了"+popNum);


			}else{
				alert("已经没有可以删除的数了！");
			}
			break;

		}
	}
}

function displayIn(input){
	var fragment = document.createDocumentFragment();
	var len = input.length;
	var listLen = list.children.length;
	for(var j = 0; j<listLen;j++){
		console.log(list.children[0]);
		list.removeChild(list.children[0]);
	}
	console.log(list);
	for(var i = 0; i<len; i++){
		var newItem = document.createElement('li')
		newItem.innerHTML = input[i];
		fragment.appendChild(newItem);
	}
	list.appendChild(fragment);
}

function displayOut(input){
	var len = list.children.length;
	var fragment = document.createDocumentFragment();

	for(var i = 0; i<len; i++){
		list.removeChild(list.children[0]);
	}
	for(var j = 0; j<inputList.length; j++){
		var newItem = document.createElement('li');
		newItem.innerHTML = inputList[j];
		fragment.appendChild(newItem);
	}
	list.appendChild(fragment);

}






//快排

// function quickSort(arr){
// 	var len = arr.length;
// 	if(len<=1){
// 		return arr;
// 	}
// 	var base = Math.floor(len/2);
// 	var baseNum = arr[base-1];
// 	arr.splice(base-1,1);
// 	var left = [];
// 	var right = [];
// 	var i;
// 	for(i in arr){
// 		if(arr[i]<=baseNum){
// 			left.push(arr[i]);
// 		} else{
// 			right.push(arr[i]);
// 		}
// 	}
// 	return quickSort(left).concat(baseNum,quickSort(right));
	
// }
// var list = [43,2,45,23,4,1,9];
// quickSort(list);






















