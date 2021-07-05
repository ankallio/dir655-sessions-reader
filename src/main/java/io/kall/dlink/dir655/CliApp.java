package io.kall.dlink.dir655;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class CliApp {
	
	public static void main(String[] args) {
		
		BackgroundRunner backgroundRunner = new BackgroundRunner("http://192.168.0.1", 1, 5, TimeUnit.SECONDS);
		
		backgroundRunner.addListener(event -> {
			
			if (event.getNewSessions().isEmpty()) {
				return;
			}
			
			List<Session> newOut = event.getNewSessions().stream().filter(s -> "Out".equals(s.getDirection())).collect(
					Collectors.toList());
			List<Session> newIn = event.getNewSessions().stream().filter(s -> "In".equals(s.getDirection())).collect(
					Collectors.toList());
			List<Session> newOther = event.getNewSessions().stream().filter(
					s -> !"In".equals(s.getDirection()) && !"Out".equals(s.getDirection())).collect(
							Collectors.toList());
			
			System.out.println(String.format("New internet sessions at %s:", new Date()));
			
			if (!newOut.isEmpty()) {
				System.out.println("OUT:");
				
				newOut.forEach(session -> {
					String ext = StringUtils.rightPad(session.getExternalAddress(), 24);
					String inte = StringUtils.rightPad(session.getInternalAddress(), 24);
					System.out.println(String.format("%s <- %s, %s", ext, inte, session.getProtocol()));
				});
			}
			
			if (!newIn.isEmpty()) {
				System.out.println("IN: ");
				newIn.forEach(session -> {
					String ext = StringUtils.rightPad(session.getExternalAddress(), 24);
					String inte = StringUtils.rightPad(session.getInternalAddress(), 24);
					System.out.println(String.format("%s -> %s, %s", ext, inte, session.getProtocol()));
				});
			}
			
			if (!newOther.isEmpty()) {
				System.out.println("OTHER: ");
				newOther.forEach(session -> {
					String ext = StringUtils.rightPad(session.getExternalAddress(), 24);
					String inte = StringUtils.rightPad(session.getInternalAddress(), 24);
					System.out.println(String.format("%s <-> %s, %s, %s", ext, inte, session.getProtocol(),
							session.getDirection()));
				});
			}
		});
		
	}
	
}
