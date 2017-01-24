import java.util.Arrays;
import java.util.Scanner;
public class Fixtures {
	public static void main (String args[]) {
		
		//get number of teams
		int numberOfTeams;
		Scanner input_numberOfTeams = new Scanner(System.in);
		System.out.print("Number of teams in league: ");
		numberOfTeams = input_numberOfTeams.nextInt();
		
		//create an empty array to store teams + team names
		Team[] Teams = new Team[numberOfTeams+1];
		String[] teamNames = new String[numberOfTeams+1];
		
		//get name for league
		String leagueName;
		Scanner input_leagueName = new Scanner(System.in);
		System.out.print("League Name: ");
		leagueName = input_leagueName.next();
		
		//get names of teams and create teams to put in array
		for (int x = 0; x < numberOfTeams; x++) {
			System.out.print("Name of Team " + (x+1) + ":");
			teamNames[x] = new Scanner(System.in).nextLine();
			Teams[x] = new Team(teamNames[x], (x+1));
		}
		
		//if odd number of teams, add a team called "Bye"
		if (numberOfTeams % 2 == 1) {
			Teams[numberOfTeams] = new Team("Bye", numberOfTeams);
		}
		
		//create 2D array of fixtures - [round][match]
		Match[][] Fixtures = new Match[(numberOfTeams-1)*2][numberOfTeams/2];
		
		//scheduler function
		//special case for round 1, set up teams 1vs26, 2vs25, 3vs24 etc.
		for (int z = 0; z < numberOfTeams/2; z++) {
			Fixtures[0][z] = new Match(Teams[z], Teams[(numberOfTeams-1)-z]);
		}
		//for rest, base off of the previous one
		for (int y = 1; y < (numberOfTeams - 1)*2 ; y++) {
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
		
		//loop through all fixtures, create prompt
		for (int week = 0; week < ((numberOfTeams-1)*2); week++) {
			System.out.println("Round" + (week+1));
			for (int game = 0; game < numberOfTeams/2; game++) {
				if (Fixtures[week][game].homeTeam.name != "Bye" ||
						Fixtures[week][game].awayTeam.name != "Bye") {
					System.out.print(Fixtures[week][game].homeTeam.name + " vs " + Fixtures[week][game].awayTeam.name);
					Fixtures[week][game] = new Match(Fixtures[week][game].homeTeam, Fixtures[week][game].awayTeam,
							new Scanner(System.in).nextInt(), new Scanner(System.in).nextInt());
				}
			}
		}
		
		
		Arrays.sort(Teams, new TeamComparator());
		for (int loopTeams = 0; loopTeams < numberOfTeams; loopTeams++) {
			System.out.println("Team: " + Teams[loopTeams].name);
			System.out.println("Position: " + (loopTeams+1));
			System.out.println("Games Played: " + Teams[loopTeams].games_played);
			System.out.println("Games Won: " + Teams[loopTeams].games_won);
			System.out.println("Games Drawn: " + Teams[loopTeams].games_drawn);
			System.out.println("Games Lost: " + Teams[loopTeams].games_lost);
			System.out.println("Goals For: " + Teams[loopTeams].goals_for);
			System.out.println("Goals Against: " + Teams[loopTeams].goals_against);
			System.out.println("Goal Difference: " + Teams[loopTeams].goal_difference);
			System.out.println("Points: " + Teams[loopTeams].points);
			System.out.println("\r\n");
		}
	}
}
