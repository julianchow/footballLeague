
public class Match {
	
	Team homeTeam;
	Team awayTeam;
	int homeScore;
	int awayScore;
	
	Match (Team homeTeam, Team awayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
	
	Match (Team homeTeam, Team awayTeam, int homeScore, int awayScore) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		
		homeTeam.games_played++;
		awayTeam.games_played++;
		homeTeam.goals_for = homeTeam.goals_for + homeScore;
		awayTeam.goals_for = awayTeam.goals_for + awayScore;
		homeTeam.goals_against = homeTeam.goals_against + awayScore;
		awayTeam.goals_against = awayTeam.goals_against + homeScore;
		homeTeam.goal_difference = homeTeam.goals_for - homeTeam.goals_against;
		awayTeam.goal_difference = awayTeam.goals_for - awayTeam.goals_against;
		
		if (homeScore > awayScore) {
			homeTeam.games_won++;
			awayTeam.games_lost++;
			homeTeam.points = homeTeam.points + 3;
		}
		
		if (awayScore > homeScore) {
			awayTeam.games_won++;
			homeTeam.games_lost++;
			awayTeam.points = awayTeam.points + 3;
		}
		
		if (homeScore == awayScore) {
			homeTeam.games_drawn++;
			awayTeam.games_drawn++;
			homeTeam.points = homeTeam.points + 1;
			awayTeam.points = awayTeam.points + 1;
		}
		
	}
	
}
