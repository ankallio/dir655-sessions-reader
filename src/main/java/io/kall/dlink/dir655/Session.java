package io.kall.dlink.dir655;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("session")
public class Session {
	
	@XStreamAlias("priority")
	private Integer priority;
	@XStreamAlias("tcp_state")
	private String tcpState;
	@XStreamAlias("time_left")
	private Integer timeLeft;
	@XStreamAlias("protocol")
	private String protocol;
	@XStreamAlias("direction")
	private String direction;
	@XStreamAlias("nat_address_and_port")
	private String natAddress;
	@XStreamAlias("internal_address_and_port")
	private String internalAddress;
	@XStreamAlias("external_address_and_port")
	private String externalAddress;
	@XStreamAlias("counter")
	private String counter;
	
	public Integer getPriority() {
		return priority;
	}
	
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public String getTcpState() {
		return tcpState;
	}
	
	public void setTcpState(String tcpState) {
		this.tcpState = tcpState;
	}
	
	public Integer getTimeLeft() {
		return timeLeft;
	}
	
	public void setTimeLeft(Integer timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	public String getProtocol() {
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String getNatAddress() {
		return natAddress;
	}
	
	public void setNatAddress(String natAddress) {
		this.natAddress = natAddress;
	}
	
	public String getInternalAddress() {
		return internalAddress;
	}
	
	public void setInternalAddress(String internalAddress) {
		this.internalAddress = internalAddress;
	}
	
	public String getExternalAddress() {
		return externalAddress;
	}
	
	public void setExternalAddress(String externalAddress) {
		this.externalAddress = externalAddress;
	}
	
	public String getCounter() {
		return counter;
	}
	
	public void setCounter(String counter) {
		this.counter = counter;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((externalAddress == null) ? 0 : externalAddress.hashCode());
		result = prime * result + ((internalAddress == null) ? 0 : internalAddress.hashCode());
		result = prime * result + ((natAddress == null) ? 0 : natAddress.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
		result = prime * result + ((tcpState == null) ? 0 : tcpState.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		if (externalAddress == null) {
			if (other.externalAddress != null)
				return false;
		} else if (!externalAddress.equals(other.externalAddress))
			return false;
		if (internalAddress == null) {
			if (other.internalAddress != null)
				return false;
		} else if (!internalAddress.equals(other.internalAddress))
			return false;
		if (natAddress == null) {
			if (other.natAddress != null)
				return false;
		} else if (!natAddress.equals(other.natAddress))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (protocol == null) {
			if (other.protocol != null)
				return false;
		} else if (!protocol.equals(other.protocol))
			return false;
		if (tcpState == null) {
			if (other.tcpState != null)
				return false;
		} else if (!tcpState.equals(other.tcpState))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Session [protocol=" + protocol + ", direction=" + direction + ", natAddress=" + natAddress
				+ ", internalAddress=" + internalAddress + ", externalAddress=" + externalAddress + "]";
	}
	
	
}
