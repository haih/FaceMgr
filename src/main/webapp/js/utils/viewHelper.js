/*
 *  辅助juicer进行模板渲染的方法
 */

 //请求参数合并
juicer.register('transParam',function(){
	// 判断请求参数
	var adtionParam={};
	var len=arguments.length;
	var i=1;
	while(i < len){
		adtionParam[arguments[i]]=arguments[i+1];
		i=i+2;
	}
	// 如果合并参数为空对象
	if($.isEmptyObject(arguments[0]) && $.isEmptyObject(adtionParam) ) return '';
	
	adtionParam=$.extend(true,{},arguments[0],adtionParam)
	return '?'+decodeURIComponent ($.param(adtionParam));
});

/**
 * 转换json对象json字符串
 * @json  json对象
 * @param 
 *    eg: value|returnValue|keyName
 */
juicer.register('jsonTostring',function(json){
	return JSON.stringify(json) ;
});

/**
 * juicer  trim 时间长度
 * 
 */
juicer.register('reduceTimeLength',function(value){
	return value.substr(0,19)
})



/**
 * 查找数组中 是否包含某个属性
 * @arr  查找数组
 * @param 
 *    eg:
 */
juicer.register('containClass',function(arr,param,returnValue){

	if(!$.isArray(arr)) return '';

	var  isContain =false;;

	$.each(arr,function(i,n){
		return !(isContain=(n.appablecode === param))
	});
	return isContain?returnValue:'';
})


/**
 * 查找数组中 是否包含某个属性
 * @arr  查找数组
 * @param 
 *    eg: value|returnValue|keyName
 */		
juicer.register('ableNames',function(arr){

	if(!$.isArray(arr)) return '';

	var  result=[];

	$.each(arr,function(i,n){
		result.push(n.appablecode);
	});
	return result.join(',');
});


//给数值+1,便于数组遍历展示
juicer.register('addOne',function(value){
	return ++value 
})