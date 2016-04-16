/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：TestSubjectServiceImpl.java						
 * 
 * Description：	
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年7月3日上午10:04:05         Create
 */
package com.iflytek.aio;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.iflytek.aio.common.msg.Result;
import com.iflytek.aio.entity.Face;
import com.iflytek.aio.service.impl.SubjectServiceImpl;

/**
 * @author haihu 
 *
 * @create 2015年7月3日 上午10:04:05
 *
 * @version 1.0
 * 
 * @description
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath:spring/spring.xml","classpath:spring/servlet-context.xml"})
public class TestSubjectServiceImpl {
    @Autowired
    private SubjectServiceImpl subjectServiceImpl;
//    @Test
//    public void test_subjectService(){
//        Subject subject = new Subject();
//        Long id = new Long(3);
//       // subject.setId(id);
//        subject.setMeetingName("安徽省人大会议1");
//        subject.setParticipateCount(54);
//        subjectServiceImpl.saveSubject(subject);
//        //subjectServiceImpl.updateSubject(subject);
//        
//    }
    
    @Test
    public void test_findSubjectInfo(){
//        Long id = new Long(1);
        subjectServiceImpl.findAllSubjectName();
//        Subject subject = new Subject();
//        subject.setId(id);
//        System.out.println(subjectServiceImpl.findPager(new Pager<Subject>(),subject));
    }
    
    //@Test
    public void findFaceBySubjectId(){
        Long id = new Long(1);
        Result<List<Face>> list = subjectServiceImpl.findFaceBySubjectId(id);
        System.out.println(list.toString());
    }
    
}
