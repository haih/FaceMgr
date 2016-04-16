<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>人脸识别管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="./css/style.css">
    <link rel="icon" href="img/ico/lily48.ico" mce_href="img/ico/lily48.ico" type="image/x-icon">
    <link rel="shortcut icon" href="img/ico/lily48.ico" mce_href="img/ico/lily48.ico" type="image/x-icon">
    <!-- <link rel="stylesheet" type="text/css" href="css/outer/webuploader.css"> -->
</head>
<body>
<div class="header">
    <div class="sysIcon"></div>
    <ul class="navbar">
        <li id="themeManage" class="selected">
            <a href="subject/findNameList.do" data-afterAjax="showThemeList">主题管理</a>
        </li>       
        <li id="faceLibrary" >
            <a href="sharing/findPager.do" data-afterAjax="showFaceList">公共库</a>
        </li>
    </ul>
    <a href="logout.do" id="logout" class="logout" data-confirm="您确认要退出吗?"><i class="icon icon-exit"></i></a>
    <span class="welcome">${aio_session_key.accountName}</span>
</div>
<!-- <c:set var="user" value= "${aio_session_key}"/>
<span>${user.accountName}</span> -->
    
<!--这个做什么的?现在知道了吧!全局提示语!-->
<div class="tips-wrap"></div>

<div class="bodyer" id="bodyer">
    <!-- 左侧主题栏 -->
    <div class="topicNavbar">
        <div class="title">
            <i class="icon icon-topic"></i>
            <h2>主题管理</h2>
        </div>
        <div class="addTheme">
            <a href="" class="addNewTheme"><span style="font-size:12px;font-weight: bold;">+</span> 添加主题会议</a>
        </div>
    </div>
    <!-- 右侧主显示区域 -->
    <div class="topicList" id="topicList">
        <div class="operation">     
            <h3></h3>
            <div class="utilBtn">
                <div class="searchWrap">
                    <input placeholder="请输入人员名称">
                    <a>
                        <i class="icon icon-search"></i>
                    </a>
                </div>
                <div class="addOne able pr"  href="defaultEvent">新建
                    <!-- 隐藏窗口 -->
                    <div class="smallChooseList">
                        <ul class="smallUl">
                            <li class="smallLi">
                                <a data-beforeAjax="addSingleParticipants">添加个人</a>
                            </li>
                            <li class="smallLi">
                                <a data-beforeAjax="addNewParticipants">从公共库添加</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="addMore able pr">批量添加
                    <!-- 隐藏窗口 -->
                    <div class="smallChooseList">
                        <ul class="smallUl">
                            <li class="smallLi">
                                <a href="subject/downloadTemplate.do" data-autoEvent="yes">下载模板</a>
                            </li>
                            <li class="smallLi pr" style="overflow:hidden;">
                                <a data-beforeAjax="addFromLocal" class="pr" style="z-index=2">本地批量添加</a>
                                <div id="uploader" class="wu-example" style="position:absoulte;z-index=-1">
                                    <!--用来存放文件信息-->
                                    <div id="thelist" class="uploader-list"></div>
                                    <div class="btns">
                                        <div id="picker">本地批量添加</div>
                                    </div>
                                </div>      
                            </li>
                        </ul>
                    </div>
                </div>
                <a class="addToShare able">加入公共库</a>
                <a class="delete able" href="subject/deleteParticipants.do" data-beforeAjax="deleteFace" data-afterAjax="deleteFaceAfter" data-confirm="确认删除已选择项？">删除</a>
                <img class="codeMap" src="./img/codeMap.png">
            </div>
        </div>
        <div class="list">
            <table class="table">
                <thead>
                    <tr>
                        <td width="45px">选择</td>
                        <td width="70px">照片</td>
                        <td width="72px">姓名</td>
                        <td width="58px">性别</td>
                        <td width="88px">职位</td>
                        <td width="180px">所在单位</td>
                        <td width="130px">手机号码</td>
                        <td width="130px">注册时间</td>
                        <td width="60px">认证/次</td>
                        <td width="60px">失败/次</td>
                        <td width="60px">操作</td>
                    </tr>
                </thead>
                <tbody>
                   
                </tbody>
            </table>
            <div class="pagination">
                <span>共<span style="color:red">322</span>条记录</span>
                &nbsp;&nbsp;
                <span>每页</span>
                <span class="count">10</span>
                <span>条记录</span>
                <div  class="pageControl">
                    <a href="">首页</a>
                    <a href="">上一页</a>
                    <a href="" class="nowPage">1</a>
                    <a href="">2</a>
                    <a href="">3</a>
                    <a href="">4</a>
                    <a href="">...</a>
                    <a href="">10</a>
                    <a href="">下一页</a>
                    <a href="">末页</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="footer">
主办：合肥市人民政府&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;承办：科大讯飞股份有限公司
</div>

<!-- <script src="js/seajs/sea.js" id="seajsnode"  data-config="config" data-main="app" defer async></script> -->
<script src="js/seajs/sea.js" id="seajsnode"></script>
<script type="text/javascript" src="js/libs/jquery/jquery.min.js"></script>
<script src="js/libs/webuploader/webuploader.js"></script>

<script>
// seajs 配置
//index.html引入sea.js后,其默认base地址是其上一层, 于是正确加载到了本配置文件
seajs.config({
    //基础路径
    // base: '/all-in-one/js/'
    base: './js/'
    ,
    //js文件别称
    alias: {
        //框架一级库,dom操作,弹窗与模板渲染
        'jquery': {
            src: 'libs/jquery/jquery.min.js',
            exports: 'jQuery' 
        },
        'juicer': {
            src: 'libs/juicer/juicer-min.js',
            exports:'juicer'
        },
        'dialog': {
            src: 'libs/dialog/dialog-min.js',
            exports:'dialog'
        },
        //框架二级库,form表单处理与验证
        'jquery.validate':{
            // src:'libs/form/jquery.validate.min.js'
            src:'libs/form/jquery.validate.min.js'
        },
        'jquery.form':{
            src:'libs/form/jquery.form.min.js'
        }

    }
    ,
    //插件,转化成cmd模式
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
        tpls:'../../../template'
    }
});
//加载入口模块
seajs.use("app") ;  
</script>
<script type="text/javascript">
    var ctx = '${ctx}' ;
</script>
</body>
</html>