import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class Predictor {

	private JFrame frame;
	private JTextField leagueNameTextField;
	private JTextField numberOfTeamsTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Predictor window = new Predictor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Predictor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//new frame and setup
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//top panel, including league name + number of teams + update
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblLeagueName = new JLabel("League Name: ");
		panel.add(lblLeagueName);
		
		leagueNameTextField = new JTextField();
		panel.add(leagueNameTextField);
		leagueNameTextField.setColumns(10);
		
		JLabel lblNumberOfTeams = new JLabel("Number of Teams: ");
		panel.add(lblNumberOfTeams);
		
		numberOfTeamsTextField = new JTextField();
		panel.add(numberOfTeamsTextField);
		numberOfTeamsTextField.setColumns(2);
		
		
		//set up a split pane - table on left, fixtures in a card panel on right
		JSplitPane splitPane = new JSplitPane();
		splitPane.setLeftComponent(new JPanel());
		splitPane.setRightComponent(new JPanel());
		splitPane.setResizeWeight(0.5);
		
		


		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			//when "Update" is clicked...
			public void actionPerformed(ActionEvent arg0) {
				
				//first, reset old data
				int numberOfTeams;
				int scheduleNumberOfTeams;
				String leagueName;
				Team[] Teams;
				String[] teamNames;
				Match[][] Fixtures;
				Schedule schedule;
				JLabel [][][] teamsListOfFixtures;
				JTextField [][][] scoresListOfFixtures;
				JPanel[][] weekPanels;
				JPanel[] weekLabelPanels;
				JButton btnCalculate;
				Object[][] tableData;
				String bye = "Bye";
				
				//fixturesPanel.removeAll();
				//table.removeAll();
				
				//if both boxes are filled then update
				if (numberOfTeamsTextField.getText().isEmpty() == false || leagueNameTextField.getText().isEmpty() == false) {
				numberOfTeams = Integer.parseInt(numberOfTeamsTextField.getText());
				scheduleNumberOfTeams = numberOfTeams;
				leagueName = leagueNameTextField.getText();
				tableData = new Object[numberOfTeams][9];
				
				//initialise fixtures JScrollPane and put it inside a JSplitPane
				JSplitPane fixturesSplitPane = new JSplitPane();
				fixturesSplitPane.setResizeWeight(1);
				JScrollPane scrollPaneFixtures = new JScrollPane();
				GridLayout fixturesGridLayout = new GridLayout (((scheduleNumberOfTeams-1)*2*((scheduleNumberOfTeams/2)+1))+1,1);
				JPanel fixturesPanel = new JPanel();
				fixturesPanel.setLayout(fixturesGridLayout);
				scrollPaneFixtures.setViewportView(fixturesPanel);
				fixturesSplitPane.setTopComponent(scrollPaneFixtures);
				fixturesSplitPane.setBottomComponent(new JPanel());
				splitPane.setRightComponent(fixturesSplitPane);
				
				
				//table put inside a JScrollPane
				JScrollPane scrollPaneTable = new JScrollPane();
				//table column names set up with DefaultTableModel
				Object[] objectTableColumnNames = {"Team Name", "Games Played", "Wins", "Draws", "Losses", "Goals For", "Goals Against", "Goal Difference", "Points"};
				String[] stringTableColumnNames = {"Team Name", "Games Played", "Wins", "Draws", "Losses", "Goals For", "Goals Against", "Goal Difference", "Points"};
				DefaultTableModel tableModel = new DefaultTableModel(objectTableColumnNames,numberOfTeams);
				JTable table = new JTable(tableModel);
				//putting the table inside the scrollPane
				scrollPaneTable.setViewportView(table);
				splitPane.setLeftComponent(scrollPaneTable);
				
				
				table.revalidate();
				scrollPaneTable.revalidate();
				frame.revalidate();
				
				
				//change title of frame to league name
				frame.setTitle(leagueName);
					
				//create an empty array to store teams + team names
					Teams = new Team[numberOfTeams+1];
					teamNames = new String[numberOfTeams+1];
					for (int x = 0; x < numberOfTeams; x++) {
						teamNames[x] = (String)JOptionPane.showInputDialog(frame, "Enter name of Team " + (x+1), "Team Names", JOptionPane.INFORMATION_MESSAGE);
						Teams[x] = new Team(teamNames[x], (x+1));
					}
					
					//if odd number of teams, add a team called "Bye", change the scheduleNumberOfTeams (number passed to scheduler function)
					//to be even
					if (numberOfTeams % 2 == 1) {
						Teams[numberOfTeams] = new Team(bye, numberOfTeams + 1);
						scheduleNumberOfTeams = numberOfTeams + 1; 
					}
					
					//create 2D array of fixtures - [round][match]
					Fixtures = new Match[(scheduleNumberOfTeams-1)*2][scheduleNumberOfTeams/2];
					schedule = new Schedule(Fixtures, scheduleNumberOfTeams, Teams);
					
						//create an array of teams&scores of fixtures per week to print
						teamsListOfFixtures = new JLabel[(scheduleNumberOfTeams-1)*2][scheduleNumberOfTeams/2][2];
						scoresListOfFixtures = new JTextField[(scheduleNumberOfTeams-1)*2][scheduleNumberOfTeams/2][2];
						for (int week = 0; week < ((scheduleNumberOfTeams-1)*2); week++) {
							for (int game = 0; game < ((scheduleNumberOfTeams/2)); game++) {
									teamsListOfFixtures[week][game][0] = new JLabel(schedule.fixtures[week][game].homeTeam.name);
									teamsListOfFixtures[week][game][1] = new JLabel(schedule.fixtures[week][game].awayTeam.name);
									scoresListOfFixtures[week][game][0] = new JTextField(2);
									scoresListOfFixtures[week][game][1] = new JTextField(2);
									System.out.println("penis2");
							}
						}
						
							//create panels for each week
							//store in weekPanels, an array  for JPanels
							weekPanels = new JPanel[(scheduleNumberOfTeams-1)*2][(scheduleNumberOfTeams/2)];
							weekLabelPanels = new JPanel[(scheduleNumberOfTeams-1)*2];
							
							for (int week = 0; week < ((scheduleNumberOfTeams-1)*2); week++) {
								weekLabelPanels[week] = new JPanel();
								weekLabelPanels[week].add(new JLabel("Week " + (week+1)));
								fixturesPanel.add(weekLabelPanels[week]);
								
								for (int game = 0; game < ((scheduleNumberOfTeams/2)); game++) {
									if (teamsListOfFixtures[week][game][0].getText() == bye ||
											teamsListOfFixtures[week][game][1].getText() == bye) {
										
										//teamsListOfFixtures[week1][game1][0].removeAll();
										//teamsListOfFixtures[week1][game1][1].removeAll();
										
									}
									else {
										System.out.println("dick");
										weekPanels[week][game] = new JPanel();
										weekPanels[week][game].add(teamsListOfFixtures[week][game][0]);
										weekPanels[week][game].add(scoresListOfFixtures[week][game][0]);
										weekPanels[week][game].add(scoresListOfFixtures[week][game][1]);
										weekPanels[week][game].add(teamsListOfFixtures[week][game][1]);
										//add each panel
										fixturesPanel.add(weekPanels[week][game]);
										//TODO stop horizontal scrolling
										//TODO randomise fixtures
										//TODO align fixtures maybe in a grid
									}
								}
							}

							fixturesPanel.revalidate();
							scrollPaneFixtures.revalidate();
							frame.revalidate();
							
							//add a 'calculate' button
							btnCalculate = new JButton("Calculate");
							btnCalculate.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									//reset everything
									for (int x = 0; x < numberOfTeams; x++) {
										Teams[x].games_played = 0;
										Teams[x].games_won = 0;
										Teams[x].games_drawn = 0;
										Teams[x].games_lost = 0;
										Teams[x].goals_for = 0;
										Teams[x].goals_against = 0;
										Teams[x].goal_difference = 0;
										Teams[x].points = 0;
									}
									
									table.removeAll();
									table.revalidate();
									int scheduleNumberOfTeams;
									if (numberOfTeams % 2 == 1) {
										scheduleNumberOfTeams = numberOfTeams + 1; 
									}
									else {
										scheduleNumberOfTeams = numberOfTeams;
									}
									//loop through all games
									//if home and away filled then update
									for (int week = 0; week < ((scheduleNumberOfTeams-1)*2); week++) {
										for (int game = 0; game < ((scheduleNumberOfTeams/2)); game++) {
											if(scoresListOfFixtures[week][game][0].getText().equals("")||
													scoresListOfFixtures[week][game][1].getText().equals("")) {
												System.out.println("penis2222");
												}
												
											
											else {
												System.out.println("penis1");
												schedule.fixtures[week][game] = new Match (schedule.fixtures[week][game].homeTeam, 
												schedule.fixtures[week][game].awayTeam, Integer.parseInt(scoresListOfFixtures[week][game][0].getText()),
												Integer.parseInt(scoresListOfFixtures[week][game][1].getText()));
												
												Arrays.sort(Teams, new TeamComparator());
												for (int x = 0; x < numberOfTeams; x++) {
													tableData[x][0] = Teams[x].name;
													tableData[x][1] = Teams[x].games_played;
													tableData[x][2] = Teams[x].games_won;
													tableData[x][3] = Teams[x].games_drawn;
													tableData[x][4] = Teams[x].games_lost;
													tableData[x][5] = Teams[x].goals_for;
													tableData[x][6] = Teams[x].goals_against;
													tableData[x][7] = Teams[x].goal_difference;
													tableData[x][8] = Teams[x].points;
											}
											}
										
										}
									
									
									}
									table.setModel(new DefaultTableModel(tableData, stringTableColumnNames));
									table.revalidate();
								}
							});
							fixturesSplitPane.setBottomComponent(btnCalculate);
							
							
							frame.revalidate();
//TODO - alignment of boxes
//		- add cardLayout to fixtures
//		- add to table
//		- make it able to decrease number of teams!
						
 				}
				//setLabel("Fill both boxes!");
			}
		});
		panel.add(btnUpdate);		
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
	}

}
