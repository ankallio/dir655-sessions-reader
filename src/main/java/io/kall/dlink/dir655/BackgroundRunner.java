package io.kall.dlink.dir655;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackgroundRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(BackgroundRunner.class);
	
	private final String baseUrl;
	
	private List<Session> lastSessions = new ArrayList<>();
	
	private final Set<SessionsChangedListener> listeners = new HashSet<>();
	
	public BackgroundRunner(String baseUrl, long initialDelay, long delay, TimeUnit unit) {
		this.baseUrl = baseUrl;
		
		ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
		ex.scheduleWithFixedDelay(() -> startQuery(), initialDelay, delay, unit);
	}
	
	private void startQuery() {
		try {
			SessionsRetriever sr = new SessionsRetriever(baseUrl);
			List<Session> sessions = sr.retrieve();
			
			SessionsChangedEvent event = new SessionsChangedEvent(sessions, lastSessions);
			listeners.forEach(listener -> listener.onSessionsChanged(event));
			
			lastSessions = sessions;
			
		} catch (IOException e) {
			logger.error("Session retrieval failed", e);
		}
		
	}
	
	public void addListener(SessionsChangedListener listener) {
		this.listeners.add(listener);
	}
	
	public static interface SessionsChangedListener {
		void onSessionsChanged(SessionsChangedEvent event);
	}
	
	public static class SessionsChangedEvent {
		
		private Collection<Session> sessions;
		private Collection<Session> newSessions;
		private Collection<Session> closedSessions;
		
		public SessionsChangedEvent(Collection<Session> sessions, Collection<Session> lastSessions) {
			this.sessions = Collections.unmodifiableCollection(sessions);
			
			this.newSessions = Collections.unmodifiableCollection(sessions.stream().filter(ses -> {
				return !lastSessions.contains(ses);
			}).collect(Collectors.toList()));
			
			this.closedSessions = Collections.unmodifiableCollection(lastSessions.stream().filter(ses -> {
				return !sessions.contains(ses);
			}).collect(Collectors.toList()));
		}
		
		public Collection<Session> getSessions() {
			return this.sessions;
		};
		
		public Collection<Session> getNewSessions() {
			return newSessions;
		};
		
		public Collection<Session> getClosedSessions() {
			return closedSessions;
		};
	}
}
