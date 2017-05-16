//方法一，递归思想 
function fn(n){
	if(n == 1 || n == 2){
		return 1;
	}else{
		return fn(n-1)+ fn(n-2);
	}
}
fn(10);
//方法二，非递归，保留历史数组
function fn(n){
	var arr = [1,1];

	if(n == 1 || n == 2){
		return 1
	}else{
		for(var i = 2; i < n; i++ ){
			arr.push(arr[i-1] + arr[i-2]);
		}
		return arr[n-1];
	}
}
fn(10);
//方法三，非递归，动态规划
function fn(n){
	var before = 1,
	    after = 1;
	var sum;
	if(n ==1 || n ==2){
		return 1
	}else{
		for(var i = 2; i<n; i++){
			sum = before + after;
			before = after;
			after = sum;
		}
	 return sum;
	}
}

fn(10);