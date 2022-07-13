package com.example.eventpoc;

import com.example.eventpoc.common.ExceptionLevel;
import com.example.eventpoc.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Diese Klasse existiert zum Ausprobieren des Codes
 */

@SpringBootTest
public class ExperimentalTest {

    @Autowired
    private UserService userService;

    @Test
    public void experiment1() {



    }

    private void throwException() throws  Exception{
        throw new Exception();
    }

}
