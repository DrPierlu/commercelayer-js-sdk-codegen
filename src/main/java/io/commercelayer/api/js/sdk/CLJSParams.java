package io.commercelayer.api.js.sdk;

public class CLJSParams {

	private String jsSourceFile;
	private boolean overwiteOutput;

	public String getJsSourceFile() {
		return jsSourceFile;
	}

	public CLJSParams setJsSourceFile(String jsSourceFile) {
		this.jsSourceFile = jsSourceFile;
		return this;
	}

	public boolean isOverwiteOutput() {
		return overwiteOutput;
	}

	public CLJSParams setOverwiteOutput(boolean overwiteOutput) {
		this.overwiteOutput = overwiteOutput;
		return this;
	}

}
