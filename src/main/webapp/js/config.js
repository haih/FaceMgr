// seajs 配置
//index.html引入sea.js后,其默认base地址是其上一层, 于是正确加载到了本配置文件
seajs.config({
    //基础路径
    // base: '/all-in-one/js/'
    base: './js'
    ,
    //js文件别称
    alias: {
      'jquery': {
          src: 'libs/jquery/jquery.min.js',
          exports: 'jQuery' 
      },
      'juicer': {
          src: 'libs/juicer/juicer-min.js'
      },
      'dialog': {
          src: 'libs/dialog/dialog-min.js'
      }
  }
  ,
    //插件
    plugins: ['shim','text']
    ,
  // 文件编码
    charset: 'utf-8'
    ,
    //预加载
    preload:[
      'libs/jquery/jquery.min'
    ]
    ,
    //路径目录别名
    paths:{
      'form': 'libs/form'
    }
    ,
    //路径中的默认参数
    vars:{
      //模板默认参数
        tpls:'./templates'
    }
});