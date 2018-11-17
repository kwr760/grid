package krt.password.api.test;

import krt.password.api.Password;
import org.junit.Test;

public class TestSuite {
	@Test
	public void test() {
		Password pass = new Password();
		System.out.println("Result:  "  + pass.toString());
	}
}
