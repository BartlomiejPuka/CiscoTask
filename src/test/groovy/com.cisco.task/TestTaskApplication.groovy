package com.cisco.task

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql

@SpringBootApplication(exclude = [SecurityAutoConfiguration.class])
@ActiveProfiles("test")
class TestTaskApplication {

}
