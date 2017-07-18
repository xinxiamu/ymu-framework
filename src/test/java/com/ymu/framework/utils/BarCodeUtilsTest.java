package com.ymu.framework.utils;

import org.junit.Assert;
import org.junit.Test;

public class BarCodeUtilsTest {

	@Test
	public void encodeTest() {
		String contents = "6923450657713";
		BarCodeUtils.encode(contents, 105, 50, "F:/bb.png");
	}

	@Test
	public void dcodeTest() {
		String imgPath = "F:/bb.png";
		String barCentent = BarCodeUtils.decode(imgPath);
		Assert.assertEquals("6923450657713", barCentent);
	}

}
