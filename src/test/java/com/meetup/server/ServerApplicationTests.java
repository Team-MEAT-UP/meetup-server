package com.meetup.server;

import com.meetup.server.support.IntegrationTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("postgresql")
@SpringBootTest
class ServerApplicationTests extends IntegrationTestContainer {

	@Test
	void contextLoads() {
	}
}
