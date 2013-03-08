package cn.edu.scau.robert.test;


import org.junit.Before;
import org.junit.Test;

import cn.edu.scau.robert.util.PublicLib;

public class PublicTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNormal() {
		System.out.println(PublicLib.ifNull("Hello", "other"));
	}
	
	@Test
	public void testNull(){
		String a = null;
		System.out.println(PublicLib.ifNull(a, "other"));
	}
	
	@Test
	public void testNumber(){
		String a = null;
		System.out.println(PublicLib.ifNull(a, 12));
	}

}
