package com._9._ss23.testOtherPakage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testhook {

    @Test
    @DisplayName("git hook 특정경로 test")
    void gitHook_commitTest(){
        Assertions.assertEquals("test", "test1");
    }
}
