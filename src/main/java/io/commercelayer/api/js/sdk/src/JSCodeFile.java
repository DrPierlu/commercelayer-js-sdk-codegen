package io.commercelayer.api.js.sdk.src;

import java.util.LinkedList;
import java.util.List;

public class JSCodeFile {
	
	private String path;
	private List<String> sourceLines;
	
	public JSCodeFile(String path) {
		this.path = path;
	}
	
	public JSCodeFile(String path, List<String> sourceLines) {
		this(path);
		this.sourceLines = sourceLines;
	}

	public String getPath() {
		return path;
	}

	public List<String> getSourceLines() {
		return sourceLines;
	}

	public void setSourceLines(List<String> sourceLines) {
		this.sourceLines = sourceLines;
	}
	
	public void addSourceLine(String line) {
		if (this.sourceLines == null) this.sourceLines = new LinkedList<>();
		this.sourceLines.add(line);
	}
	
	public void addSourceLines(List<String> lines) {
		if (this.sourceLines == null) this.sourceLines = new LinkedList<>();
		this.sourceLines.addAll(lines);
	}

}
