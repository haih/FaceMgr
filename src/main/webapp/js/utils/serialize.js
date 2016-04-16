define(function(require,exports,module){
	var standNameExp=/^[_0-9a-zA-Z]+$/i;

	function transformData(key,value){
		// 判断值是不是为空
		if(!value || value ==='') return;

		// 判断key 是不是最简单的情况
		if(standNameExp.test(key)){
			// 如果这个属性还没有被初始化
			if(typeof this[key] ==='undefined'){
				this[key]=value;
			}else if(typeof this[key] === 'string'){
				//判断改属性是不是已经被复制了， 如果已经被赋值了
				var newValue=[];
				newValue.push(this[key]);
				newValue.push(value);
				this[key]=newValue
			}else{
				this[key].push(value);
			}
			return ;
		}
		//  处理时不时直接是一个数组的情况
		var arrayNameExp=/(.*)\[([0-9]*)\]$/i;
		if(arrayNameExp.test(key)){
			// 运算表达式
			arrayNameExp.exec(key);
			// 获取数组的 key 和index
			var arrayKey=RegExp.$1,arrayIndex=parseInt(RegExp.$2)
			if(typeof this[arrayKey] ==='undefined'){
				this[arrayKey]=[];
			}
			
			if(typeof arrayIndex === 'undefined' || arrayIndex === '' || isNaN(arrayIndex)){
				this[arrayKey].push(value);
			}else {
				// 一路检测，直到指定位置天蝎内容
				for(var i=0;i<arrayIndex;i++){
					if(typeof this[arrayKey][i]==='undefined'){
						this[arrayKey][i]='';
					}
					
				}
				this[arrayKey][arrayIndex]=value;
				
			}
			
			
			return;
		}
		
		// 处理多层级情况
		var index=key.indexOf('.'),firstKey=key.substr(0,index);

		if(standNameExp.test(firstKey)){
			if(typeof this[firstKey] === 'undefined'){
				this[firstKey]={};
			}
			arguments.callee.call(this[firstKey],key.substr(index+1),value);
			return ;
		}
		
		arrayNameExp.exec(firstKey);

		var keyName=RegExp.$1,arrIndex=parseInt(RegExp.$2);

		if(typeof this[keyName] === 'undefined'){
			this[keyName]=[];
		}
		var curArr=this[keyName];
		for(var i=0;i<=arrIndex;i++){
			if(typeof this[keyName][i]==='undefined'){
				this[keyName][i]={};
			}
		}
		arguments.callee.call(this[keyName][arrIndex],key.substr(index+1),value);
	}

	// 暴露一个方法
	module.exports=function(arr){
		var result={};
		$.each(arr,function(i,n){
			transformData.call(result,n.name,n.value);
		});
		return result;
	}
})