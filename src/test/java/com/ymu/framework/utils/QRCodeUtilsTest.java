package com.ymu.framework.utils;

import org.junit.Test;

public class QRCodeUtilsTest {

    @Test
    public void encodeTest() {
        //放到html图片标签上显示
       //<img src="data:image/jpeg;base64,iVBORw0KGgoAAAANSUhEUgAAACEAAAAhAQAAAAB/n//CAAAAlElEQVR42mP4DwQNDJjkB0Ee9gaG7xf/fm9g+BLyVxxIBq0FkfF7geT3a7JA8Q+iIUA1/3/XA9V/lpvWDhQvDK9vYPjUxwQk/5Rs3d7A8PeugTlQPL0jHSgy8VU+UMQwdH4Dw8cpE8KBej+mlwPNkWAFqv9+Zbk80PxwA34gGdmYDiKfvAeKX40Cqvwg0JWOzZ1gEgCLqmr6QwYA4AAAAABJRU5ErkJggg==" alt="" class="codeimg" style="width: 100%">

        String contents = "15010400003%A01-231-1806000306%1%1%1%180710%S00370_00001";
        String str = QRCodeUtil.encode(contents,40,40);
        System.out.println(">>>>" + str);

    }
}
