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

import java.io.IOException;
import java.util.List;

import org.apache.tools.zip.ZipOutputStream;
import org.eclipse.jdt.internal.core.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.iflytek.aio.common.util.FaceExcel;
import com.iflytek.aio.entity.Face;
import com.iflytek.aio.entity.Subject;
import com.iflytek.aio.mapper.FaceMapper;
import com.iflytek.aio.service.impl.FaceServiceImpl;

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
public class TestFaceServiceImpl  {
    @Autowired
    private FaceServiceImpl faceServiceImpl;
    
    @Autowired
    private FaceMapper faceMapper;
    
//    @Test
//    public void testFindPager(){
//        Result<Pager<Face>> result = faceServiceImpl.findPager(new Pager<Face>(),null);
//        System.out.println(result.toString());
//    }

//    @Test
//    public void testBean(){
//        //faceServiceImpl.addFaceInfoList(null);
//    }
    
     // @Test
//      public void testUpdateAuditStatus(){
//          String status = "nopass";
//          List<Long> ids= new ArrayList<Long>(); 
//          for (int i = 1; i < 3; i++) {
//            Long id = new Long(i);
//            ids.add(id);
//        }
//          Result<String> result = faceServiceImpl.updateAuditStatus(ids, status);
//          System.out.println(result.toString());
//      }

    //  @Test
      public void test_findSubjectInfo(){
          Long id = new Long(1);
//          subjectServiceImpl.findAllSubjectName();
          Subject subject = new Subject();
          subject.setId(id);
        //  System.out.println(subjectServiceImpl.findPager(new Pager<Subject>(),subject));
      }
      
     // @Test
      public void test_addFaceInfoList(){
          faceServiceImpl.addFaceInfoList();
          Assert.isNotNull("test");
      }
      
      //@Test
      public void test_downloadFaceInfo(){
          List<Face> list = faceMapper.findAll();
          ZipOutputStream zos = null;
//          HttpServletResponse response = new HttpServletResponse();
//          zos = new ZipOutputStream(new OutputStream());
//          zos.setEncoding("gbk");
          try {
            FaceExcel.exportFaceList(list, null);
            //zos.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
          System.out.println(list.toString());
      }

}
