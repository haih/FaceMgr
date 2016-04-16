//validate验证扩展

define(function(require,exports,module){
/**
 * 
 * 中文字符验证
 * 		--最大长度验证
 * */ 
jQuery.validator.addMethod( "byteMaxLength" ,  function (value, element, param)  {       
    var  length  =  jQuery.trim(value).length;       
    for ( var  i  =   0 ; i  <  value.length; i ++ ) {       
        if (value.charCodeAt(i)  >   127 ) {       
       length ++ ;       
       }        
   }        
    return   this .optional(element)  ||  ( length  <=  param);       
}); 

/**
 * 
 * 中文字符验证
 * 		--最大长度验证
 * */ 
jQuery.validator.addMethod( "pattern" ,  function (value, element, param)  {       
    
    return   this .optional(element)  ||  new RegExp(param,'ig').test(value);       
});

jQuery.validator.addMethod( "subTimer" ,  function (value, element, param)  {  
  	var startArr = $(param).val().split("-");
  	var endArr = $(element).val().split("-");
    var start = new Date(startArr[0],startArr[1]-1,startArr[2]).getTime();
	var end = new Date(endArr[0],endArr[1]-1,endArr[2]).getTime();
    return   ((end - start) > 29*24*60*60*1000) ? false :true;       
});

})