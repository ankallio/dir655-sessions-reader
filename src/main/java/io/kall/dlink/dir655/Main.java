package io.kall.dlink.dir655;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);	
	
	public static void main(String[] args) throws IOException {
		
		SessionsRetriever retriever = new SessionsRetriever("http://192.168.0.1");
		
		logger.info("Sessions: {}", retriever.retrieve());
		
	}
	
}
