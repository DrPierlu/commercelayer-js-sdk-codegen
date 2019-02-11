package io.commercelayer.api.js.sdk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.util.LogUtils;

public class CLJSFileWriter {
	
	private static final Logger logger = LoggerFactory.getLogger(CLJSFileWriter.class);
	

	public void write(CLJSFile jsFile) throws CodegenException {

		if ((jsFile == null) || (jsFile.getPath() == null)) throw new CodegenException("No Javascript file to write");
		
		Writer wr = null;
		
		try {
			wr = Files.newWriter(new File(jsFile.getPath()), Charset.forName("UTF-8"));
			if (wr == null) throw new Exception();
		} catch (Exception e) {
			throw new CodegenException("Unable to write file " + jsFile.getPath());
		}
		

		String autoGenMsg = "// File automatically generated at "
				+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + " by commercelayer-js-sdk-codegen";

		List<String> apiLines = jsFile.getSourceLines();

		if (apiLines.get(0).contains("automatically generated")) {
			apiLines.remove(0);
			apiLines.add(0, autoGenMsg);
		} else {
			apiLines.add(0, autoGenMsg);
			apiLines.add(1, StringUtils.EMPTY);
			apiLines.add(2, StringUtils.EMPTY);
		}

		try (BufferedWriter br = new BufferedWriter(wr)) {
			for (String line : apiLines) {
				br.write(line);
				br.newLine();
			}
		} catch (Exception e) {
			logger.error(LogUtils.printStackTrace(e));
			throw new CodegenException("Error writing output file");
		}

		logger.info("Successfully written file {}", jsFile.getPath());

	}

}
