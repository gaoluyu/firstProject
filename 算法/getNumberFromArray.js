//取出对象中的所有的数字
var list = [];
function deepClone(arr){
	var temp;
	for(var i in arr){
		if( arr[i] instanceof (Array)){
			 deepClone(arr[i]);
		}else{
			list.push(arr[i]);
		}
	}
	return list;
}
var a = [[1,2,3],[[[4,5],6],7,8],[9,10]];
console.log(deepClone(a));
























