import java.util.Comparator;


public class PointsComparator implements Comparator<Team> {


		@Override
		public int compare(Team team1, Team team2) {
			
			return team1.points + team2.points;
		}
		
	
	

}
