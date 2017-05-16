//二叉树是由节点组成，定义一个对象node,可以保存数据，
//也可以保存其他节点的链接（left和right）,show()方法用来显示保存在节点中的数据

//Node代码
function Node(data,left,right){
	this.data = data;
	this.left = left;
	this.right = right;
	this.show = show;
}

//插入节点分析
function Node(data,left,right){
	this.data = data;
	this.left = left;
	this.right = right;
	this.show = show;
}

function show(){
	return this.data;
}

function BST(){
	this.root = null;
	this.insert = insert;
	this.inOrder = inOrder;
	this.preOrder = preOrder;
	this.postOrder = postOrder;
}

function insert(data){
	var n = new Node(data,null,null);
	if(this.root == null){
		this.root = n;
	}else{
		var current = this.root;
		var parent;
		while(true){
			parent = current;
			if(data < current.data){
				current = current.left;
				if(current == null){
					parent.left = n;
					break;
				}

			}else{
				current = current.right;
				if(current == null){
					parent.right = n;
					break;
				}
			}

		}
	}

}

//中序遍历
function inOrder(node){
	if(!(node == null)){
		inOrder(node.left);
		console.log(node.show() + " ");
		inOrder(node.right);
	}
}

//前序遍历 根左右
function preOrder(node){
	if(! (node == null)){
		console.log(node.show() + " ");
		preOrder(node.left);
		preOrder(node.right);
	}
}

//后序遍历，左右根
function postOrder(node){
	if(!(node == null)){
		postOrder(node.left);
		postOrder(node.right);
		console.log(node.show() + " ");
	}
}


var nums = new BST();
nums.insert(23);
nums.insert(45);
nums.insert(16);
nums.insert(37);
nums.insert(3);
nums.insert(99);
nums.insert(22);
console.log("Inorder traversal: ");
console.log(nums.root);
inOrder(nums.root);

//查找最小值
function getMin(){
	var current = this.root;
	while(!(current.left == null)){
		current = current.left;
	}
	return current.data;
}

//查找最大值
function getMax(){
	var current = this.root;
	while(!(current.right == null)){
		current = current.right;
	}
	return current.data;
}

//查找给定值
function find(data){
	var current = this.root;
	while (current != null){
		if(current.data == data){
			return current;
		}else if(data < current.data){
			currnt = current.left;

		} else{
			current = current.right;
		}
	}
	return null;
}

//二叉查找树上删除节点
function remove(data){
	root = removeNode(this.root,data);
}

function removeNode(node,data){
	if(node == null){
		return null;
	}
	if(data == node.data){
		//没有子节点的节点
		if(node.left == null && node.right == null){
			return null;
		}
		//没有左子节点的节点
		if(node.left == null){
			return node.right;
		}
		//没有右子节点的节点
		if(node.right == null){
			return node.left;
		}
		//有两个子节点的节点
		var tempNode = getSmallest(node.right);
		node.data = tempNode.data;

	}
}


var num = read_line();
var arr = [];
var list = [];
var 
for(var i = 0; i < n; i++){
	arr[i] = read_line().split(" ");
	arr[i].shift();
}
for(var j = 0; j < num; j++){
	for(var k = 0; k < num-i-1; k++ ){
	    if((a[k][0] >= a[k+1][0])&&(a[k][1] >= a[k+1][1])){
	    	var list = arr[k];
	    	arr[k] = arr[k+1];
	    	arr[k+1] = list;
	    }

	}
}
var count = 0;
for(var i = 0; i < arr.length-1; i++){
	    if((a[i][0] >= a[i+1][0])&&(a[i][1] >= a[i+1][1])){
        count++;

}
}
console.log(count);

function append(arr,item){
	 var list =[];
	 list = arr;
	 list.push(item);
	 return list;
}


















