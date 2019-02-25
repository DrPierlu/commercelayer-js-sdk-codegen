package io.commercelayer.api.js.sdk.gen.common;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

import io.commercelayer.api.codegen.CodegenException;
import io.commercelayer.api.codegen.schema.ApiSchema;
import io.commercelayer.api.js.sdk.src.JSCodeBlock;
import io.commercelayer.api.js.sdk.src.JSCodeFile;
import io.commercelayer.api.util.LogUtils;

public abstract class SDKFileGenerator {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected Params params = null;

	public SDKFileGenerator(Params params) {
		this.params = params;
	}

	public abstract JSCodeFile generate(ApiSchema schema) throws CodegenException;

	protected List<String> readLines(String filePath) throws CodegenException {
		try {
			return Files.readLines(new File(filePath), Charset.forName("UTF-8"));
		} catch (IOException ioe) {
			logger.error(LogUtils.printStackTrace(ioe));
			throw new CodegenException("Error reading file " + filePath);
		}
	}

	protected JSCodeBlock findBlock(String blockId, List<String> lines) {

		int blockIni = 0;
		int blockEnd = 0;
		int index = 0;
		int brackets = 0;

		for (String line : lines) {

			index++;

			if (line.trim().startsWith(blockId))
				blockIni = index;

			for (char c : line.toCharArray()) {
				if (c == '{')
					brackets++;
				else if (c == '}')
					brackets--;
			}

			if ((blockIni > 0) && (index != blockIni) && (brackets == 0)) {
				blockEnd = index;
				break;
			}

		}

		List<String> blockLines = lines.subList(blockIni - 1, blockEnd);

		return new JSCodeBlock(blockIni, blockEnd, blockLines);

	}

	public static final class Params {

		private String jsSourceDir;
		private String jsSourceFile;
		private boolean overwiteOutput;

		public String getJsSourceDir() {
			return jsSourceDir;
		}

		public Params setJsSourceDir(String jsSourceDir) {
			this.jsSourceDir = jsSourceDir;
			return this;
		}

		public String getJsSourceFile() {
			return jsSourceFile;
		}

		public Params setJsSourceFile(String jsSourceFile) {
			this.jsSourceFile = jsSourceFile;
			return this;
		}

		public boolean isOverwiteOutput() {
			return overwiteOutput;
		}

		public Params setOverwiteOutput(boolean overwiteOutput) {
			this.overwiteOutput = overwiteOutput;
			return this;
		}

	}

}
