package com.sunitkatkar.blogspot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultitenancyDynamicTenantApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testRegex() {
		String uri = "acc.citsmartcloud.com";
		Matcher m = Pattern.compile("([^.]+).citsmartcloud.com").matcher(uri);
		if (m.find()) {
		String tenantid = m.group(1);		
		System.out.println(tenantid);
	}
	}
}
