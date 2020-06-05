package com.aha.tech;

import com.aha.tech.service.MessageLogCompensationService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test2")
@SpringBootTest(classes = Application.class)
public class MemberMsgCompensationTest {

    @Autowired
    private MessageLogCompensationService messageLogService;

    @Test
    public void testTargetProperty() {
        messageLogService.messageCompensation();
    }

}

