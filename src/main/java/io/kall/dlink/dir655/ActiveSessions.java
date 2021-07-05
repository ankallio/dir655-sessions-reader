package io.kall.dlink.dir655;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("active_sessions")
public class ActiveSessions {
	
	@XStreamImplicit
	private List<Session> activeSessions;
	
	public List<Session> getActiveSessions() {
		return activeSessions;
	}
	
	public void setActiveSessions(List<Session> activeSessions) {
		this.activeSessions = activeSessions;
	}

	@Override
	public String toString() {
		return "ActiveSessions [activeSessions=" + activeSessions + "]";
	}
	
	
	
	
}
