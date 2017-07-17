package com.blazemeter.jmeter.http2.sampler;


import static org.junit.Assert.*;
import org.eclipse.jetty.http.HttpField;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.http.control.CacheManager;
import org.apache.jmeter.protocol.http.control.CookieHandler;
import org.apache.jmeter.protocol.http.control.CookieManager;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.protocol.http.util.HTTPConstants;
import org.apache.jmeter.protocol.http.util.HTTPFileArg;
import org.apache.jmeter.protocol.http.util.HTTPFileArgs;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class HTTP2RequestTest {
	
	private HTTP2Connection http2ConnectionMock;
	private HTTP2Request http2Req;
	
	
	@Before
    public void setup() {
        http2ConnectionMock = Mockito.mock(HTTP2Connection.class);
        //http2SampleResultMock = Mockito.mock(HTTP2SampleResult.class);
        //hdrsResponseMock = Mockito.mock(HttpFields.class);
        http2Req = new HTTP2Request();
        http2Req.setThreadName("10");        
        http2Req.setProperty(HTTP2Request.DOMAIN, "www.sprint.com");
    }
	

	 @Test
	 public void sampleTest1() throws Exception {
		 http2Req.testStarted("www.sprint.com");

		 URL url = new URL(http2Req.getProtocol(), "www.sprint.com", 443, "/");
		 
		 HTTP2Connection connection = Mockito.mock(HTTP2Connection.class);
		 
		 Mockito.when(connection.isClosed()).thenReturn(true);
		 Mockito.doNothing().when(connection).connect(Mockito.any(String.class), Mockito.any(Integer.class));
		 Mockito.when(connection.getConnectionId()).thenReturn("10www.sprint.com443");
		 
		 http2Req.setProperty(HTTP2Request.METHOD, "GET");
		 HTTP2SampleResult sampleResult =new HTTP2SampleResult(url, http2Req.getMethod());
		 http2Req.addConnection("10www.sprint.com443", connection);
		 http2Req.setConnection(url, sampleResult);
		 http2Req.setProperty(new BooleanProperty(HTTP2Request.SYNCREQUEST, true));
		 HTTP2SampleResult sample = http2Req.sample(url, "GET", false, 0, http2ConnectionMock, sampleResult);

	 }
	 
	 @Test
	 public void sampleTest2() throws Exception {
		 http2Req.testStarted("www.sprint.com");
		 
		 Arguments args = new Arguments();
         String text = "{\"header\":{\"applicationId\":\"HJS\"},\"initSession\":{}}";
         HTTPArgument arg = new HTTPArgument("", text.replaceAll("\n","\r\n"), false);
         arg.setAlwaysEncoded(false);
         args.addArgument(arg);
         http2Req.setProperty(new TestElementProperty(HTTP2Request.ARGUMENTS, args));

		 URL url = new URL("https", "www.sprint.com", 443, "/apiservices/framework/initSession");
		 
		 HTTP2Connection connection = Mockito.mock(HTTP2Connection.class);
		 
		 Mockito.when(connection.isClosed()).thenReturn(true);
		 Mockito.doNothing().when(connection).connect(Mockito.any(String.class), Mockito.any(Integer.class));
		 Mockito.when(connection.getConnectionId()).thenReturn("10www.sprint.com443");
		 
		 HTTP2SampleResult sampleResult =new HTTP2SampleResult(url, "POST");
		 http2Req.addConnection("10www.sprint.com443", connection);
		 http2Req.setConnection(url, sampleResult);
		 http2Req.setProperty(new BooleanProperty(HTTP2Request.SYNCREQUEST, true));
		 HTTP2SampleResult sample = http2Req.sample(url, "POST", false, 0, http2Req.getConnection(), sampleResult);
		 
	 }
	 
	 
	 @Test
	 public void sampleTest3() throws Exception {
		 URL url = new URL(http2Req.getProtocol(), "www.sprint.com", 443, "/");
		 HTTP2SampleResult sampleRes =new HTTP2SampleResult(url, "GET");
		 
		 // connection is null so sample fails
		 sampleRes = http2Req.sample(url, "GET", false, 0, null, sampleRes); 
		 assertEquals(false, sampleRes.isPendingResponse());
		 assertEquals(false, sampleRes.isSuccessful());
		 
	 }
	 
	 /*
	 @Test
	 public void saveConnectionCookiesTest() throws Exception {
		 URL url = new URL("https", "www.sprint.com", 443, "/");
		 CookieManager cookieM =  new CookieManager();
		 cookieM.testStarted("www.sprint.com");
		 http2Req.setCookieManager(cookieM);
		 HttpFields hdrsResponse= new HttpFields();
		 HttpField h1= new HttpField(HttpHeader.ACCEPT_LANGUAGE, "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3");
		 HttpField h2= new HttpField("set-cookie", "TLTSID=117EB9B2664B1066043AEB724D71EB62; Path=/; Domain=.sprint.com");
		 HttpField h3= new HttpField("set-cookie", "TLTLTUID=117EB9B2664B1066043AEB724D71EB62; Path=/; Domain=.sprint.com; Expires=Tue, 11-Jul-2027 15:10:26 GMT");
		 HttpField h4= new HttpField(HttpHeader.USER_AGENT, "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0");
		 hdrsResponse.add(h1);
		 hdrsResponse.add(h2);
		 hdrsResponse.add(h3);
		 hdrsResponse.add(h4);

		 List<String> hdrs= new ArrayList<String>();
		 hdrs.add("set-cookie: TLTSID=117EB9B2664B1066043AEB724D71EB62; Path=/; Domain=.sprint.com");
		 hdrs.add("set-cookie: TLTUID=117EB9B2664B1066043AEB724D71EB62; Path=/; Domain=.sprint.com; Expires=Tue, 11-Jul-2027 15:10:26 GMT");
		 //Mockito.when(hdrsResponseMock.getValuesList("set-cookie")).thenReturn(hdrs);
		 
		 http2Req.saveConnectionCookies(hdrsResponse, url, http2Req.getCookieManager());
		 
		 String cookieHeader = cookieM.getCookieHeaderForURL(url);
		 System.out.println(cookieHeader);
		 
	 }*/
	 
	 @Test
	 public void getUrlTest() throws Exception {
		 http2Req.setProperty(HTTP2Request.PATH, "/shop/device/list/PHONE");
		 
		 URL urlRes = http2Req.getUrl();
		 URL urlExp = new URL("https", "www.sprint.com", 443, "/shop/device/list/PHONE");
		 
		 assertEquals(urlExp, urlRes);
		 
		 http2Req.setProperty(HTTP2Request.PATH, "https://www.sprint.com/shop/device/list/PHONE");
		 urlRes = http2Req.getUrl();
		 assertEquals(urlExp, urlRes);
		 
		 http2Req.setProperty(HTTP2Request.PATH, "shop/device/list/PHONE");
		 urlRes = http2Req.getUrl();
		 assertEquals(urlExp, urlRes);
		 
		 http2Req.setProperty(HTTP2Request.PORT, "8080");
		 urlRes = http2Req.getUrl();
		 urlExp = new URL("https", "www.sprint.com", 8080, "/shop/device/list/PHONE");
		 assertEquals(urlExp, urlRes);
		    	 
	 }
	 
	 @Test
	 public void getPortTest() throws Exception {
		 http2Req.setProtocol(HTTPConstants.PROTOCOL_HTTPS);
		 int portExp=443;
		 int portRes=http2Req.getPort();
		 assertEquals(portExp, portRes);

		 http2Req.setProtocol(HTTPConstants.PROTOCOL_HTTP);
		 portExp=80;
		 portRes=http2Req.getPort();
		 assertEquals(portExp, portRes);
	    	 
	 }
	 
	 @Test
	 public void setManagersTest() throws Exception {
		 HeaderManager hManExp= new HeaderManager();
		 
		 http2Req.setHeaderManager(hManExp);
		 HeaderManager hManRes = http2Req.getHeaderManager();
		 
		 assertEquals(hManExp, hManRes);
		 
		 CookieManager cManExp= new CookieManager();
		 
		 http2Req.setCookieManager(cManExp);
		 CookieManager cManRes = http2Req.getCookieManager();
		 
		 assertEquals(cManExp, cManRes);
		 
		 CacheManager cachManExp= new CacheManager();
		 
		 http2Req.setCacheManager(cachManExp);
		 CacheManager cachManRes = http2Req.getCacheManager();
		 
		 assertEquals(cachManExp, cachManRes);
	    
		 
	 }
	 
	 @Test
	 public void createPostContentTest() throws Exception {
		 String text = "{\"header\":{\"applicationId\":\"HJS\"},\"initSession\":{}}";
		 
		 
		 DataPostContent dataPostExp = new DataPostContent();
		 dataPostExp.setDataPath("/apiservices/framework/initSession");
		 dataPostExp.setPayload(text.getBytes());
		 		 
		 Arguments args = new Arguments();
         
         HTTPArgument arg = new HTTPArgument("", text.replaceAll("\n","\r\n"), false);
         arg.setAlwaysEncoded(false);
         args.addArgument(arg);
         http2Req.setProperty(new TestElementProperty(HTTP2Request.ARGUMENTS, args));
		 http2Req.setProperty(HTTP2Request.PATH, "/apiservices/framework/initSession");
         DataPostContent dataPostRes = http2Req.createPostContent("POST");
         
         assertEquals(dataPostExp.getDataPath(), dataPostRes.getDataPath());     
         
	 }
	 
	 @Test
	 public void getHTTPFilesTest(){
		 HTTPFileArg[] fileArgsRes = http2Req.getHTTPFiles();
		 assertNotEquals(null, fileArgsRes);
		 HTTPFileArgs fileArgs = new HTTPFileArgs();
		 HTTPFileArg[] fileArgsExp = fileArgs.asArray();
		 http2Req.setProperty(new TestElementProperty("HTTPsampler.Files", fileArgs));
		 fileArgsRes = http2Req.getHTTPFiles();
		 
		 assertEquals(fileArgsExp, fileArgsRes);
		
	 }
	 
	 @Test
	 public void getSendFileAsPostBodyTest(){
		 HTTPFileArgs fileArgs = new HTTPFileArgs();
		 http2Req.setProperty(new TestElementProperty("HTTPsampler.Files", fileArgs));
		 boolean valueRes=http2Req.getSendFileAsPostBody();
		 assertFalse(valueRes);		
	 }
	 
	 @Test
	 public void sampleMainTest () throws Exception {
		 URL urlExp = new URL("https", "www.sprint.com", 443, "/shop/device/list/PHONE");
		 HTTP2SampleResult sampleResult =new HTTP2SampleResult(urlExp, "GET");
		 http2Req.sample();
	 
	 }
	 	 

}
