package com.yutadd.SendPostRequestIntruder;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

public class SendPostRequestIntruder implements BurpExtension{

	@Override
	public void initialize(MontoyaApi api) {
		api.extension().setName("SendPostRequestIntruder");
		api.http().registerHttpHandler(new RequestHandler(api));
		api.logging().logToOutput("extensionが有効化されましたよ！");
	}

}
