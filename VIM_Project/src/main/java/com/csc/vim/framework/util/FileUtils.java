package com.csc.vim.framework.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csc.vim.framework.controller.CorsCommon;


@Service
public class FileUtils {

	private final Logger logger = LoggerFactory.getLogger(CorsCommon.class); 
	
	public boolean deleteFile(File file) {
		boolean result = true;

		if (file.exists()) {
			logger.debug("Deleting file:" + file.getName());
			result &= file.delete();
		}

		return result;
	}
	
	/**
	 * Tranform a type org.springframework.web.multipart.MultipartFile into a java.io.File
	 * @see createInvoices()
	 * @author syongwaiman
	 * @version 1.0
	 * @param multipart
	 * @return java.io.File
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
		//File tmpFile = File.createTempFile(prefix, suffix)
		File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +  multipart.getOriginalFilename());
		multipart.transferTo(tmpFile);
		return tmpFile;
	}
}
