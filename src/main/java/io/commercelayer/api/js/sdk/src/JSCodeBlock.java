package io.commercelayer.api.js.sdk.src;

import java.util.LinkedList;
import java.util.List;

public class JSCodeBlock {
	private int lineIni = -1;
	private int lineEnd = -1;
	private List<String> lines = new LinkedList<>();
	
	public JSCodeBlock() {
		super();
	}
	
	public JSCodeBlock(List<String> lines) {
		this.lines = lines;
	}
	
	public JSCodeBlock(int lineIni, int lineEnd, List<String> lines) {
		this(lines);
		this.lineIni = lineIni;
		this.lineEnd = lineEnd;
	}
	
	public boolean exists() {
		return (this.lineIni != -1) && (this.lineEnd != -1);
	}

	public int getLineIni() {
		return lineIni;
	}

	public void setLineIni(int lineIni) {
		this.lineIni = lineIni;
	}

	public int getLineEnd() {
		return lineEnd;
	}

	public void setLineEnd(int lineEnd) {
		this.lineEnd = lineEnd;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

}
