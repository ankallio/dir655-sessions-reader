package io.kall.dlink.dir655;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SessionsRetriever {
	private static final Logger logger = LoggerFactory.getLogger(SessionsRetriever.class);
	
	private final String baseUrl;
	
	private static OkHttpClient client = new OkHttpClient();
	
	/**
	 * 
	 * @param baseUrl Like: http://192.168.0.1
	 */
	public SessionsRetriever(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public List<Session> retrieve() throws IOException {
		Request request = new Request.Builder().get().url(baseUrl).build();
		Response response = client.newCall(request).execute();
		
		String body = response.body().string();
		Pattern saltPattern = Pattern.compile("var salt = \"(.+)\";");
		Matcher saltMatcher = saltPattern.matcher(body);
		Pattern authIdPattern = Pattern.compile("\"&auth_id=(.+?)\";");
		Matcher authIdMatcher = authIdPattern.matcher(body);
		String salt;
		String authId;
		if (saltMatcher.find()) {
			salt = saltMatcher.group(1);
			logger.info("Salt: {}", salt);
		} else {
			logger.info("Didn't find salt");
			throw new RuntimeException();
		}
		if (authIdMatcher.find()) {
			authId = authIdMatcher.group(1);
			logger.info("AuthId: {}", authId);
		} else {
			logger.info("Didn't find authid");
			throw new RuntimeException();
		}
		
		String postLoginUrl = baseUrl + buildPostLogin(salt, "", authId);
		
		request = new Request.Builder().get().url(postLoginUrl).build();
		response = client.newCall(request).execute();
		
		if (response.isSuccessful()) {
			logger.info("Successful postlogin response: {}", response);
			response.close();
		} else {
			logger.info("Error postlogin: {}", response);
			throw new RuntimeException();
		}
		
		String activeSessionsUrl = baseUrl + "/active_sessions.xml";
		
		request = new Request.Builder().get().url(activeSessionsUrl).build();
		response = client.newCall(request).execute();
		
		if (response.isSuccessful()) {
			logger.info("Successful activesession response: {}", response);
		} else {
			logger.info("Error activesession response: {}", response);
			throw new RuntimeException();
		}
		
		String xml = response.body().string();
		response.close();
		
		XStream xstream = new XStream();
		XStream.setupDefaultSecurity(xstream);
		xstream.allowTypes(new Class[] {ActiveSessions.class, Session.class});
		xstream.processAnnotations(ActiveSessions.class);
		
		ActiveSessions activeSessions = (ActiveSessions) xstream.fromXML(xml);
		
		return activeSessions.getActiveSessions();
	}
	
	private static String buildPostLogin(String salt, String password, String authId) {
		
		String authCode = "";
		
		password = StringUtils.trimToEmpty(password);
		
		String padStr = new String(Character.toChars(1));
		
		password = StringUtils.rightPad(password, 16, padStr);
		logger.info("Padded password to: [{}]", password);
		
		String input = salt + password;
		input = StringUtils.rightPad(input, 63, padStr);
		logger.info("Padded input to: [{}]", input);
		
		input += padStr;
		logger.info("Padded input to: [{}]", input);
		
		String hash = DigestUtils.md5Hex(input);
		String loginHash = salt + hash;
		
		String authUrl = "&auth_code=" + authCode + "&auth_id=" + authId;
		
		String url = "/post_login.xml?hash=" + loginHash + authUrl;
		
		return url;
	}
	
}
