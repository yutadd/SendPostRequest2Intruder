package com.yutadd.SendPostRequestIntruder;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.handler.HttpHandler;
import burp.api.montoya.http.handler.HttpRequestToBeSent;
import burp.api.montoya.http.handler.HttpResponseReceived;
import burp.api.montoya.http.handler.RequestToBeSentAction;
import burp.api.montoya.http.handler.ResponseReceivedAction;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.intruder.Intruder;
import burp.api.montoya.logging.Logging;

public class RequestHandler implements HttpHandler {
	private final Logging logging;
	private final Intruder intruder;
	private static int REQUEST_COUNTER=0;
	private static int RESPONSE_COUNTER=0;
	public RequestHandler(MontoyaApi api) {
        this.logging = api.logging();
        this.intruder=api.intruder();
    }
	@Override
	synchronized public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {
		logging.logToOutput("=====／"+REQUEST_COUNTER+"リクエスト＼=====");
		logging.logToOutput(REQUEST_COUNTER+"番目のリクエストを受信しました");
		logging.logToOutput("- 詳細:");
		logging.logToOutput("  - メソッド: "+requestToBeSent.method());
		logging.logToOutput("  - パラメーター:");
		int i=0;
		for(ParsedHttpParameter parameter:requestToBeSent.parameters()) {
			logging.logToOutput("    - パラメータ"+i+++": ("+parameter.name()+": "+parameter.value()+")");
		}
		logging.logToOutput("=====＼"+REQUEST_COUNTER+"リクエスト／=====");
		REQUEST_COUNTER++;
		//POSTはぜんぶintruderにおくっちゃおーねー
		if( requestToBeSent.method().equals("POST")){
			intruder.sendToIntruder(requestToBeSent);
		}
		return null;
	}

	@Override
	synchronized public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived) {
		logging.logToOutput("=====／"+RESPONSE_COUNTER+"レスポンス＼=====");
		logging.logToOutput(RESPONSE_COUNTER+"番目のレスポンスを受信しました");
		logging.logToOutput("=====＼"+RESPONSE_COUNTER+"レスポンス／=====");
		RESPONSE_COUNTER++;
		return null;
	}

}
