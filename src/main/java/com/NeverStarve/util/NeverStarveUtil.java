package com.NeverStarve.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;

public class NeverStarveUtil {

	
	public String blobToBase64(Blob image, String mimeType) {
		StringBuffer strbuffer = new StringBuffer();

		try (InputStream is = image.getBinaryStream(); ByteArrayOutputStream baos = new ByteArrayOutputStream();) {

			int len = 0;
			byte[] b = new byte[81920]; // 512的整數倍
			while ((len = is.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			byte[] ba = baos.toByteArray();

			strbuffer.append("data:" + mimeType + ";base64,");
			Base64.Encoder be = Base64.getEncoder();
			strbuffer.append(new String(be.encode(ba)));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return strbuffer.toString();
	}

}
