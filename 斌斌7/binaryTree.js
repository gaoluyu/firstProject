// var bt = document.getElementById("button");
var outter = document.querySelector(".outter");
var bt = document.querySelector(".button");
// var bt1 = bt.querySelectorAll("button")[0];
// var bt2 = bt.querySelectorAll("button")[1];
// var bt3 = bt.querySelectorAll("button")[2];
var warning = document.getElementById("alert");
var arr = [];
var toggle = false;
var last;

//给按钮绑定事件
function init(){
	bt.addEventListener("click",order,false);
}

//事件委托，选择遍历方式
function order(){
	var target = event.target;
	if(toggle){
		warning.innerHTML = "演示还没有结束，请等待！";
	}
	if(target.nodeName === "BUTTON"){		
		switch(target.className){
			case "bt preOrder":
			if(!toggle){
		        reset();
				toggle = true;
			    preOrder(outter);
			    showWay();
			 }
			break;
			case "bt inOrder":
			if(!toggle){
		     reset();
			 inOrder(outter);
			 showWay();
			}
			break;
			case "bt postOrder":
			if(!toggle){
			 postOrder(outter);
			 showWay();
			}
			break;
		}
	}
}

//前序遍历 根左右
function preOrder(node){
	if(node){
		arr.push(node);
		preOrder(node.firstElementChild);
		preOrder(node.lastElementChild);
	}
}

//中序遍历 左根右
function inOrder(node){
	if(node){
		inOrder(node.firstElementChild);
		arr.push(node);
		inOrder(node.lastElementChild);
	}
}

//后序遍历 左右根
function postOrder(node){
	if(node){
		postOrder(node.firstElementChild);
		postOrder(node.lastElementChild);
		arr.push(node);
	}
}

//显示遍历过程
function showWay(){
	for(var i = 0; i<arr.length; i++){
		setTimeout(function(i){
			return function (){
				if(i == arr.length-1){
					toggle = false;
					setTimeout(function(){
					warning.innerHTML = "演示已经结束！";
				},1000);
					setTimeout(function(){
						last.style.background = "white";
					},2000);
					setTimeout(function(){
					warning.innerHTML = "";
				},3000);

				}
				if(last){
					last.style.background = "white";
				}
				arr[i].style.background = "red";
				last = arr[i];

			}
		}(i) ,i*1000)
	}
}

//初始化
function reset(){
	arr = [];
	toggle = true;
	if(last){
		last.style.background = "white";
	}
}


init();

















