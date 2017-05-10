var bt = document.getElementById('bt-vali');
var alert = document.getElementById('alert');

function btClick(event){
    var input = document.getElementById("input");
	var target = event.target;
	if(input.value == ""){
		alert.innerHTML = "姓名不能为空";
		alert.style.color = 'red';
		input.style.boder = '2px solid #B5B5B5';
	}else{
		test(input.value);
	}

}

function test(i){
	var excn = /[^\x00-\xff]{1,}/g;
	var exen = /\w{1,}/g;
	var cn = excn.exec(i);
	var en = exen.exec(i);
	console.log(en);
	console.log(cn);
	if(en == null){
		en = '';
	}
	if(cn == null){
		cn = '';
	}
	var len = cn.toString().length*2+en.toString().length;
	if(len>3 && len<17){
		alert.innerHTML = '命名格式正确';
		alert.style.color = 'green';
		input.style.border = '2px solid green';
	}else{
		alert.innerHTML = '长度为4~16个字符';
		alert.style.color = 'red';
		input.style.border = '2px solid red';
	}
	console.log(len);

}


function init(){
	bt.addEventListener('click',btClick,false);
}

init();