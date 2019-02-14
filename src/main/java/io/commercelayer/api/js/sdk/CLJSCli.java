package io.commercelayer.api.js.sdk;

public class CLJSCli {

	public static void main(String[] args) {
		
		boolean api = true;
		boolean model = true;
		boolean test = false;
		
		if (args.length > 0) {
			
			api = false;
			model = false;
			test = false;
			
			for (String arg : args) {
				
				if (!arg.startsWith("-")) continue;
				
				String a = arg.substring(1);
				
				if ("a".equals(a) || "-api".equals(a)) api = true;
				else
				if ("m".equals(a) || "-model".equals(a)) model = true;
				else
				if ("t".equals(a) || "-test".equals(a)) test = true;
				
			}
			
		}
		
		new CLJSGenerator().generate(api, model, test);
	}

}
