package net.duchung.shop_app;

import net.duchung.shop_app.component.JwtUtil;
import net.duchung.shop_app.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopAppApplicationTests {

	@Test
	void contextLoads() {
		JwtUtil jwtUtil = new JwtUtil();

		User user = new User();
		user.setPhoneNumber("123456789");
		user.setPassword("123456789");
		String token = jwtUtil.generateToken(user);
		assert jwtUtil.extractAllClaims(token).get("phoneNumber").equals(user.getPhoneNumber());
	}

}
