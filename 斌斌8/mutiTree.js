// var bt = document.getElementById("button");
var outter = document.querySelector(".outter");
var temp = outter;
var bt = document.querySelector(".button");
// var bt1 = bt.querySelectorAll("button")[0];
// var bt2 = bt.querySelectorAll("button")[1];
// var bt3 = bt.querySelectorAll("button")[2];
var warning = document.getElementById("alert");
// var search = document.querySelector(".search");
var input = document.querySelector("#input");
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

			case "bt BFS":
			if(!toggle){
				reset();
				BFS(temp);
				showWay();
			}
			break;
			case "bt DFS":
			if(!toggle){
				reset();
				DFS(temp);
				showWay();
			}
			break;
			case "bt search":
			console.log(toggle);
			if(!toggle){
				reset();
				BFS(temp);
				console.log(arr);
				search(input.value);
			}

		}
	}
}

//深度优先搜索 dfs
function DFS(node){
	if(node){
		arr.push(node);
		if(node.children.length > 0){
		  var len = node.children.length;
		  for(var j = 0; j < len; j++){
		    DFS(node.children[j]);
		  }

	    }
	}
}

//广度优先搜索 BFS
var index = 0;
function BFS(node){
	if(node){
		arr.push(node);
		if(node.className == "outter"){
			node = arr[index++];

		    BFS(node.firstElementChild);
		}else{
		 BFS(node.nextElementSibling);
		 node = arr[index++];
		 BFS(node.firstElementChild);
	    }
	}
}

//搜索
function search(value){
	if(value){
		var ST = [];
		console.log(arr.length);
		for(var i = 0; i < arr.length; i++){
			console.log(arr[i]);
			ST[i] = setTimeout(function(t){
				return function(){
					console.log(arr[t].firstChild.nodeValue);
					console.log(value);
					if(arr[t].firstChild.nodeValue.trim() === value){
						warning.innerHTML = "已经找到！";
						setTimeout(function(){
							warning.innerHTML = "";
							arr[t].style.background = "white";
							input.value = "";
						},2000);
						toggle = false;
						for(var k = 0; k< ST.length; k++){
						clearTimeout(ST[k]);
					  }
					}
					if(t == arr.length-1){
						warning.innerHTML = "没有搜索到！";
						input.value = "";
						toggle = false;
						setTimeout(function(){
							arr[t].style.background = "white";
							warning.innerHTML = "";
						},1000);
					}
					if(last){
				        last.style.background = "white";
			        }
			        arr[t].style.background = "red";
			        last = arr[t];
				}
			}(i),i*500);
			
		}

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
		}(i) ,i*500)
	}
}

//初始化
function reset(){
	arr = [];
	toggle = true;
	if(last){
		last.style.background = "white";
	}
    temp = outter;
    index = 0;
}
init();


//深度优先遍历的非递归写法
function dfs(node){
	var stack = [];
	if(node){
		stack.push(node);
		while(stack.length > 0){
			var item = stack.pop();
			arr.push(item);
			var len = item.children.length;
			for(var i =len-1 ; i >=0; i--){
				stack.push(item.children[i]);
			}
		}
	}
}

//广度优先遍历的非递归写法
function bfs(node){
	var queue = [];
	if(node){
		queue.push(node);
		while(queue.length > 0){
			var item = queue.shift();
			arr.push(item);
			for(var i = 0; i < item.children.length;i++){
				queue.push(item.children[i]);
			}
		}
	}
}








