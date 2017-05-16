//方法一，indexOf IE不支持
function removeDuplicatedItem(arr){
	var list = [];
	var len = arr.length;
	for(var i = 0; i < len; i++){
		if(list.indexOf(arr[i]) == -1){
			list.push(arr[i]);
		}
	}
	return list;
}


//方法二 利用oject对象保存数组值
function removeDuplicatedItem(arr){
	var tem = {};
	var list = [];
	var len = arr.length;
	for(var i = 0; i < len; i++){
		console.log(tem.arr[i]);
		if(!(tem.arr[i])){
			tem.arr[i] = 1;
			list.push(arr[i]);
		}
	}
	return list;
}
 var arr = [0,0,0,1,3,47,7,5,2,3,7,'vcs','ds','ds'];
removeDuplicatedItem(arr);
 //方法三 利用排序再去重的方法，将相同的元素放到一起
 function removeDuplicatedItem(arr){
 	arr.sort(compare);
 	function compare(a,b){
 		return a-b;
 	}
 	var base = arr[0];
 	var list = [];
 	var len = arr.length;
 	list.push(arr[0]);
 	for(var i = 0; i < len; i++){
 		if(arr[i] != base){
 			list.push(arr[i]);
 			base = arr[i];
 		}
 	}
 	return list;
 }


var arr = [0,0,0,1,3,47,7,5,2,3,7,'vcs','ds','ds'];
removeDuplicatedItem(arr);




