//验证规则字段处理js
define({
	//添加人脸的验证
	addFaceValidate:{
		rules:{
			imgPath:{
				required: true
			},
			gender:{
				required: true
			},
			name:{
				required: true,
			},
			job:{
				required: true,
			},
			department:{
                required: true,
            },
            mobile:{
            	required: true,
            	// rangelength: [0, 20],
            	pattern: '^1[34578]\\d{9}$'
            }
		},
		messages:{
			imgPath:{
				required: '请确认图片已上传'
			},
			gender:{
				required: '请填写性别'
			},
			name:{
				required: '姓名不能为空',
			},
			job:{
				required: '职务不能为空',
			},
			department:{
                required: '单位不能为空',
            },
            mobile:{
            	required: '手机号不能为空',
            	// rangelength: '[0, 20]' ,
            	pattern: '请输入11位手机号码'
            }
		}
	}

});