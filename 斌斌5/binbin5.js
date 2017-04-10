var list = document.getElementById("numList");
var bt = document.getElementById("button");
var sort = document.getElementById("Sort");
var input = document.getElementById("number");

init();


function init(){ 
	initClickButton();
}

var inputList = [];
function initClickButton(){
	bt.addEventListener("click",clickButton,false);
	sort.addEventListener("click",clickButton,false);
}

//验证数据
function verify(value){
	var verf = Number(value);

	if(inputList.length == 50){
		alert("数量上限为50个，本次输入无法完成！");
		return false;
	} else if(!verf){
	    alert("请输入正确的数字！");
		return false;
	} else if (verf<10 || verf>100) {
		alert("请输入10-100以内的数字！");
		return false;
	} 
	return true;

}

//选择添加事件
function clickButton(){
	var target = event.target;
	var value = input.value;
	// console.log(value);
	if(target.nodeName === "BUTTON"){
		input.value = "";
		switch(target.className){
			case "bt leftIn":
			// console.log(22);
			  if(verify(value)) {
			     inputList.unshift(Number(value));
			     displayIn(inputList);
			     }
			     break;
			case "bt rightIn":
			 if(verify(value)) {
			        inputList.push(Number(value));
			        displayIn(inputList);
			     }
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
			case "sort random":
			random();
			break;
			case "sort quickSort":
			if(!list.children.length){
				alert("没有可排序的数组！")
			}
            else{
			// console.log(inputList)
			inputList = quickSort(inputList);
			// console.log(inputList);
			displayIn(inputList);
		    }
			break;
			case "sort bubbleSort":
			if(!list.children.length){
				alert("没有可排序的数组！")
			}else{
			inputList = bubbleSort(inputList);
			displayIn(inputList);
			}
			break;

		}
	}
}

//添加数据
function displayIn(input){
	var fragment = document.createDocumentFragment();
	var len = input.length;
	var listLen = list.children.length;
	for(var j = 0; j<listLen;j++){
		// console.log(list.children[0]);
		list.removeChild(list.children[0]);
	}
	// console.log(list);
	for(var i = 0; i<len; i++){
		var newItem = document.createElement('li')
		newItem.innerHTML = input[i];
		newItem.style.height = inputList[i]/3 + "em";
		newItem.style.lineHeight = inputList[i]/3 + "em";
		fragment.appendChild(newItem);
	}
	list.appendChild(fragment);
}

//删除数据
function displayOut(input){
	var len = list.children.length;
	var fragment = document.createDocumentFragment();

	for(var i = 0; i<len; i++){
		list.removeChild(list.children[0]);
	}
	for(var j = 0; j<inputList.length; j++){
		var newItem = document.createElement('li');
		newItem.innerHTML = inputList[j];
		newItem.style.height = inputList[j]/3 + "em";
		newItem.style.lineHeight = inputList[j]/3 + "em";
		fragment.appendChild(newItem);
	}
	list.appendChild(fragment);

}

//随机产生50个数字
function random(){
	while(list.children.length){
		list.removeChild(list.children[0]);
	}
	// console.log(112);
	// inputList = [];
	while(inputList.length != 0){
		inputList.pop();
	}
	var max = 100;
	var min = 10;
	var fragment = document.createDocumentFragment();
	for(var i = 0; i<50; i++){
		var ranNum = min + Math.ceil(Math.random()*(max-min));
	   inputList.push(ranNum);
	   // console.log(ranNum);
	   var newItem = document.createElement("li");
	   newItem.innerHTML = ranNum;
	   newItem.style.height = ranNum/3 + "em";
	   newItem.style.lineHeight = ranNum/3 + "em";
	   fragment.appendChild(newItem);
	} 
	list.appendChild(fragment);
	// console.log(list);
	// return arr;  	
}

//快速排序
function quickSort(arr){
	var len = arr.length;
	if(len <= 1){
		return arr;
	}
	var index = Math.floor(len/2);
	var base = arr[index];
	arr.splice(index,1);
	var right = [];
	var left = [];
	for(var i=0; i<len-1; i++){
		if(arr[i]>base){
			right.push(arr[i]);
		}else{
			left.push(arr[i]);
		}
	}
	return quickSort(left).concat(base,quickSort(right));

}

//冒泡排序
function bubbleSort(arr){
	var len = arr.length;
	var i;
	for(var i = 0; i<len; i++){
		  for(var j = 0; j<len-i-1; j++ ){
			var tem;
			if(arr[j] > arr[j+1]){
				tem = arr[j];
				arr[j] = arr[j+1];
				arr[j+1] = tem;
				setTimeout(displayIn(arr),5000);
				list.children[j].className = "red";
			}else{
				setTimeout(displayIn(arr),5000);
			}
		}
	}
	return arr;
}
// var list = [43,2,45,23,4,1,9];
// bubbleSort(list);
















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

function getNthFibonacci(count) {
	return fi[count];
	
}
function fibonacci(arr){
	var fi = arr.sort(compare);
	function compare(arg1,arg2){
		return arg1 - arg2;
	}
}


function obj(name){
     if(name){ 
     	this.name = name;
     }
     return this;  
 }
obj. prototype.name = "name2";
var a = obj("name1");
var b = new obj;
console.log(a.name);
console.log(b.name);

您的答案 :
Areturn this.name = name;






// var n;
// var num = [];
// while(n = read_line()){
// 	num = n.split(" ");
// 	var arr = read_line().split(" ");
// 	fn(num,arr);
// }

// function fn(num,arr){
// 	var list = arr.sort(compare);
// 	function compare(arg1,arg2){
// 		return arg1 - arg2;
// 	}
// 	var min = list[0];
// 	var max = list[arr.length-1];
// 	var profit = [];
// 	for(var i = min; i <= max; i++){
// 		profit[i-min] = 0;
// 		for(var j = 0; j < arr.length; j++){
// 			if(list[j] >= i){
// 				profit[i-min] = profit[i-min] + (list[j]-i);
// 			}
// 		}

// 	}
// 		var maxPro = 0;
// 		var index;
// 	for(var k = i; k <= max; k++){
// 		if(profit[k-min] > maxPro){
// 			maxPro = profit[k-min];
// 			index = k;
// 		}
// 	}
// 	return index;
// }
// var num =[5,4];
// var arr = [2,8,10,7];
// console.log(fn(num,arr));
























































