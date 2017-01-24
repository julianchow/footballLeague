import java.util.Random;

public class Schedule {
	Match[][] fixtures;
	public Schedule (Match[][] Fixtures, int numberOfTeams, Team[] Teams) {
	//scheduler function
			//first set up first half, then second half
			//special case for round 1, set up teams 1vs26, 2vs25, 3vs24 etc.
			for (int z = 0; z < numberOfTeams/2; z++) {
				Fixtures[0][z] = new Match(Teams[z], Teams[(numberOfTeams-1)-z]);
			}
			//for rest of first half, base off of round 1
			for (int y = 1; y < (numberOfTeams - 1) ; y++) {
				for (int z = 0; z < numberOfTeams/2; z++) {
					//if the home team is N then freeze it and only change the opponent
					//also reverse home/away, so away team is now N
					if (Fixtures[y-1][z].homeTeam.id == numberOfTeams) {
						if (Fixtures[y-1][z].awayTeam.id + (numberOfTeams/2) > (numberOfTeams-1)){
							Fixtures[y][z] = new Match(Teams[Fixtures[y-1][z].awayTeam.id-1-((numberOfTeams/2)-1)],
									Teams[Fixtures[y-1][z].homeTeam.id-1]);
						}
						else {
							Fixtures[y][z] = new Match(Teams[Fixtures[y-1][z].awayTeam.id-1+(numberOfTeams/2)],
									Teams[Fixtures[y-1][z].homeTeam.id-1]);
						}
					}
					//if the away team is N then freeze it and only change the opponent
					//also reverse home/away, so home team is now N
					else if (Fixtures[y-1][z].awayTeam.id == numberOfTeams) {
						if (Fixtures[y-1][z].homeTeam.id + (numberOfTeams/2) > (numberOfTeams-1)){
							Fixtures[y][z] = new Match(Teams[Fixtures[y-1][z].awayTeam.id-1],
									Teams[Fixtures[y-1][z].homeTeam.id-1-((numberOfTeams/2)-1)]);
						}
						else {
							System.out.println((Fixtures[y-1][z].awayTeam.id));
							Fixtures[y][z] = new Match(Teams[(Fixtures[y-1][z].awayTeam.id)-1],
									Teams[Fixtures[y-1][z].homeTeam.id-1+(numberOfTeams/2)]);
						}
					}
					//if teams were not N
					else {
						//if adding n/2 exceeds n-1 (for both)
						if (Fixtures[y-1][z].homeTeam.id + (numberOfTeams/2) > (numberOfTeams-1) &&
								Fixtures[y-1][z].awayTeam.id + (numberOfTeams/2) > (numberOfTeams-1)) {
							Fixtures[y][z] = new Match(Teams[Fixtures[y-1][z].homeTeam.id-1-((numberOfTeams/2)-1)],
									Teams[Fixtures[y-1][z].awayTeam.id-1-((numberOfTeams/2)-1)]);
						}
						//if adding n/2 exceeds n-1 (for home)
						else if (Fixtures[y-1][z].homeTeam.id + (numberOfTeams/2) > (numberOfTeams-1) &&
								Fixtures[y-1][z].awayTeam.id + (numberOfTeams/2) <= (numberOfTeams-1)) {
							Fixtures[y][z] = new Match(Teams[Fixtures[y-1][z].homeTeam.id-1-((numberOfTeams/2)-1)],
									Teams[Fixtures[y-1][z].awayTeam.id-1+(numberOfTeams/2)]);
						}
						//if adding n/2 exceeds n-1 (for away)
						else if (Fixtures[y-1][z].homeTeam.id + (numberOfTeams/2) <= (numberOfTeams-1) &&
								Fixtures[y-1][z].awayTeam.id + (numberOfTeams/2) > (numberOfTeams-1)) {
							Fixtures[y][z] = new Match(Teams[Fixtures[y-1][z].homeTeam.id-1+(numberOfTeams/2)],
									Teams[Fixtures[y-1][z].awayTeam.id-1-((numberOfTeams/2)-1)]);
						}
						//if adding n/2 does not exceed n-1
						else {
							Fixtures[y][z] = new Match(Teams[Fixtures[y-1][z].homeTeam.id-1+(numberOfTeams/2)],
									Teams[Fixtures[y-1][z].awayTeam.id-1+(numberOfTeams/2)]);
						}
					}
					
				}
			}
			//for second half, just flip first half.
			for (int y = (numberOfTeams-1); y < (numberOfTeams-1)*2; y++) {
				for (int z = 0; z < numberOfTeams/2; z++) {
					Fixtures[y][z] = new Match(Teams[Fixtures[y-(numberOfTeams-1)][z].awayTeam.id-1], Teams[Fixtures[y-(numberOfTeams-1)][z].homeTeam.id-1]);
				}
			}
			
			//shuffle fixtures
			Random rnd = new Random();
			//first half of season
			for (int i = Fixtures.length/2 - 1; i > 0; i--) {
				int index = rnd.nextInt(i+1);
				//simple swap
				Match[] a = Fixtures[index];
				Fixtures[index] = Fixtures[i];
				Fixtures[i] = a;
			}
			//second half of season
			for (int i = Fixtures.length - 1; i > Fixtures.length/2; i--) {
				int index = rnd.nextInt(i+1);
				//simple swap
				Match[] a = Fixtures[index];
				Fixtures[index] = Fixtures[i];
				Fixtures[i] = a;
			}
			
			this.fixtures = Fixtures;
	}

}
