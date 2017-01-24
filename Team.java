import java.util.Comparator;
import java.util.Random;


public class Team {
	String name;
	int id;
	int games_played = 0;
	int games_won = 0;
	int games_drawn = 0;
	int games_lost = 0;
	int goals_for = 0;
	int goals_against = 0;
	int goal_difference = 0;
	int points = 0;
	
	Team (String name, int id) {
		this.name = name;
		this.id = id;
	}	
}

class TeamComparator implements Comparator<Team> {
    @Override
    public int compare(Team a, Team b) {
				//to sort above, return -1
				//to sort below, return 1
				if( a!= null && b!=null) {
					//null check so that NullPointerException does not occur
				//first compare points, then goal difference, then goals for
				//if they are equal then compare the next one
	    			if (b.points - a.points == 0) {
						if (b.goal_difference - a.goal_difference == 0) {
							//if all equal, draw lots by assigning random (either 1 or -1)
							if (b.goals_for - a.goals_for == 0) {
								Random rand = new Random ();
								int base = -1;
								//random either 0 or 1
								int exponent = rand.nextInt(2);
								return (int) Math.pow(base, exponent);
							}
							return b.goals_for - a.goals_for;
						}
						return b.goal_difference - a.goal_difference;
					}
					else {
						return b.points - a.points;
					}
				}
				//if null return 0 to do nothing
				else {
					return 0;
				}
    }
}