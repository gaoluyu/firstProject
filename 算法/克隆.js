//浅克隆
function clone(p,c){
	var c = c || {};
	for(var i in p){
		c[i] = p[i];
	}
	return c;
}



//深克隆
function deepClone(p,c){
	var c = c || {};
	for(var i in p){
		if(typeOf p[i] === "object"){
			c[i] = (p[i].constructor === Array) ? []:{};
			deepClone(p[i],c[i]);
		}else{
			c[i] = p[i]
		}
	}
	return c;
}

function deepClone(p,c){
	var c = c||{};
	for(var i in p){
		if(typeof p[i] === "object"){
			c[i] = (p[i].constructor === Array)?[]:{};
			deepClone(c[i],p[i]);

		}else{
			c[i] = p[i]
		}
	}
	return c;
}





























