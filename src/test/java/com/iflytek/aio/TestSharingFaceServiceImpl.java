/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：TestBean.java						
 * 
 * Description：	
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       jyli       2015-7-2下午6:56:32         Create
 */
package com.iflytek.aio;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.iflytek.aio.common.msg.Result;
import com.iflytek.aio.common.util.Pager;
import com.iflytek.aio.entity.SharingFace;
import com.iflytek.aio.mapper.SharingFaceMapper;
import com.iflytek.aio.service.impl.SharingServiceImpl;

/**
 * @author haihu 
 *
 * @create 2015-7-2 下午6:56:32
 *
 * @version 1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath:spring/spring.xml",
                                  "classpath:spring/servlet-context.xml"})
public class TestSharingFaceServiceImpl  {
    @Autowired
    private SharingServiceImpl sharingServiceImpl;
    
    @Autowired
    private SharingFaceMapper sharingFaceMapper;
    
    @Test
    public void testFindPager(){
        Result<Pager<SharingFace>> result = sharingServiceImpl.findPager(new Pager<SharingFace>());
        System.out.println(result.toString());
    }

//    @Test
    public void test_Save(){
        SharingFace sharingFace = new SharingFace();
        sharingFace.setName("haihu");
        sharingFace.setGender("女");
        sharingFace.setDepartment("department");
        sharingFace.setJob("job");
        sharingFace.setMobile("mobile");
        List<String> imgPaths = new ArrayList<String>();
        imgPaths.add("f:/");
        sharingFace.setImgPath(imgPaths);
        sharingServiceImpl.saveSharingFace(sharingFace);
    }
    

//      @Test
      public void test_findSubjectInfo(){
          sharingServiceImpl.findByName("test1");
        //  System.out.println(subjectServiceImpl.findPager(new Pager<Subject>(),subject));
      }
      

}
