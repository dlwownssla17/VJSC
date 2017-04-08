package model;

import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import db.DBTools;
import route.Server;
import util.DateAndCalendar;

public class CompetitionProcessor {
	private final ScheduledExecutorService scheduler;
	private ScheduledFuture<?> handle;
	private boolean stopped;

	public CompetitionProcessor() {
		this.scheduler = Executors.newScheduledThreadPool(1);
		this.stopped = false;
	}
	
	public void start() {
		Runnable processor = new Runnable() {
			public void run() {
				Calendar calendarToday = Calendar.getInstance();
				int todayYear = calendarToday.get(Calendar.YEAR);
				int todayMonth = calendarToday.get(Calendar.MONTH);
				int todayDay = calendarToday.get(Calendar.DAY_OF_MONTH);
				
				Set<Competition> competitions = DBTools.findAllCompetitions();
				for (Competition competition : competitions) {
					Calendar calendarEndDate = DateAndCalendar.dateToCalendar(competition.getCompetitionEndDate());
					
					if (todayYear > calendarEndDate.get(Calendar.YEAR) ||
						todayMonth > calendarEndDate.get(Calendar.MONTH) ||
						todayDay > calendarEndDate.get(Calendar.DAY_OF_MONTH)) {
						Team teamRed = DBTools.findTeam(competition.getTeamId(CompetitionTeamColor.RED));
						Team teamBlue = DBTools.findTeam(competition.getTeamId(CompetitionTeamColor.BLUE));
						
						if (competition.getStatus()) { // was active
							Server.endCompetition(teamRed, teamBlue, competition, null);
							
							DBTools.updateTeamCompetitionHistories(teamRed);
							DBTools.updateTeamCompetitionId(teamBlue);
							DBTools.updateTeamCompetitionHistories(teamRed);
							DBTools.updateTeamCompetitionId(teamBlue);
							DBTools.updateCompetition(competition);
						} else { // was pending
							Team teamDeclining = competition.joinedCompetition(CompetitionTeamColor.RED) ?
																								teamBlue : teamRed;
							Team teamInviting = teamDeclining == teamRed ? teamBlue : teamRed;
							Server.declineCompetition(teamDeclining, teamInviting, competition);
							
							DBTools.updateTeamCompetitionInvitations(teamDeclining);
							DBTools.updateTeamCompetitionId(teamInviting);
							DBTools.updateCompetition(competition);
						}
					}
				}
			}
		};
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long millisecondsUntilMidnight = calendar.getTimeInMillis() - System.currentTimeMillis();
        
		this.handle = scheduler.scheduleAtFixedRate(processor, millisecondsUntilMidnight,
																		24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
	}
	
	public void stop() {
		if (!this.stopped) {
			this.handle.cancel(false);
			this.stopped = true;
		}
	}
}
