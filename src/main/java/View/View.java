package View;
import controller.Game;
import model.Team;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class View extends JFrame {
    Team firstTeam ;
    Team secondTeam ;
    ImageIcon favicon = new ImageIcon("favicon.png");

    public View(){
        // Frame
        setTitle("BaseBall Simulator");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(170,170,170));
        setIconImage(favicon.getImage());

        //Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setPreferredSize(new Dimension(1300,150));

        //Label Title
        JLabel titleLabel = new JLabel("BaseBall Simulator");
        titleLabel.setBounds(500,-20,300,100);
        titleLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        titleLabel.setForeground(new Color(37, 59, 148));

        //Select Team Button
        JButton selectTeamsButton = new JButton("Select Teams");
        selectTeamsButton.setBounds(350,60,150,50);
        selectTeamsButton.setFocusable(false);

        // Play Button
        JButton playButton = new JButton("Play ");
        playButton.setBounds(550,60,150,50);
        playButton.setFocusable(false);

        // Reset Button
        JButton restartButton = new JButton("Restart");
        restartButton.setBounds(750,60,150,50);
        restartButton.setFocusable(false);

        // Add components to Top Panel
        topPanel.add(titleLabel);
        topPanel.add(selectTeamsButton);
        topPanel.add(playButton);
        topPanel.add(restartButton);

        //Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);

        //Home Team Label & Players List
        JLabel firstTeamPlayersLabel = new JLabel("Team A : Roster");
        firstTeamPlayersLabel.setBounds(50,0,250,50);
        JTextArea firstTeamPlayers = new JTextArea(30, 15);
        JScrollPane firstTeamPlayersTextArea = new JScrollPane(firstTeamPlayers);
        firstTeamPlayersTextArea.setBounds(15,40,300,500);
        firstTeamPlayers.setBorder(BorderFactory.createEtchedBorder());

        //Away Team Label & Players List
        JLabel secondtTeamPlayersLabel = new JLabel("Team B : Roster");
        secondtTeamPlayersLabel.setBounds(450,0,250,50);
        JTextArea secondTeamPlayers = new JTextArea(30, 15);
        JScrollPane secondTeamPlayersTextArea = new JScrollPane(secondTeamPlayers);
        secondTeamPlayersTextArea.setBounds(400,40,300,500);
        secondTeamPlayers.setBorder(BorderFactory.createEtchedBorder());

        //Text Area Of Game Commentary
        JLabel gameCommentsLabel = new JLabel("Play By Play");
        gameCommentsLabel.setBounds(950,-25,350,100);
        JTextArea gameComments = new JTextArea(30, 15);
        JScrollPane gameCommentsTextArea = new JScrollPane(gameComments);
        gameCommentsTextArea.setBounds(750,40,500,500);
        gameCommentsTextArea.setBorder(BorderFactory.createEtchedBorder());

        // Add Components to Center Panel
        centerPanel.add(firstTeamPlayersLabel);
        centerPanel.add(firstTeamPlayersTextArea);
        centerPanel.add(secondtTeamPlayersLabel);
        centerPanel.add(secondTeamPlayersTextArea);
        centerPanel.add(gameCommentsLabel);
        centerPanel.add(gameCommentsTextArea);

        //Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        JLabel footerLabel = new JLabel("Engim Torino 2021");
        //Add Component to Bottom Panel
        bottomPanel.add(footerLabel);


        // Select Teams Button Event
        selectTeamsButton.addActionListener(e -> {
            //JOption pane
            String[] choices = { "Detroit Tigers", "Houston Astros", "Kansas City Royals", "Los Angeles Dodgers", "Washington Nationals", "New York Mets" };

            String firstTeamSelected =  (String) JOptionPane.showInputDialog(null, "Choose your 1st team", "Select First Team", JOptionPane.QUESTION_MESSAGE,
                    favicon, choices, "New York Mets");
            String secondTeamSelected = (String) JOptionPane.showInputDialog(null, "Choose your 2nd team", "Select Second Team", JOptionPane.QUESTION_MESSAGE,
                    favicon, choices, "New York Mets");

            //First & Second Team
            try {
                switch (firstTeamSelected) {
                    case "Detroit Tigers" -> firstTeam = new Team("Detroit Tigers", 116);
                    case "Houston Astros" -> firstTeam = new Team("Houston Astros", 117);
                    case "Kansas City Royals" -> firstTeam = new Team("Kansas City Royals", 118);
                    case "Los Angeles Dodgers" -> firstTeam = new Team("Los Angeles Dodgers", 119);
                    case "Washington Nationals" -> firstTeam = new Team("Washington Nationals", 120);
                    case "New York Mets" -> firstTeam = new Team("New York Mets", 121);
                    default -> firstTeam = new Team("New York Mets", 121);
                }

                switch (secondTeamSelected) {
                    case "Detroit Tigers" -> secondTeam = new Team("Detroit Tigers", 116);
                    case "Houston Astros" -> secondTeam = new Team("Houston Astros", 117);
                    case "Kansas City Royals" -> secondTeam = new Team("Kansas City Royals", 118);
                    case "Los Angeles Dodgers" -> secondTeam = new Team("Los Angeles Dodgers", 119);
                    case "Washington Nationals" -> secondTeam = new Team("Washington Nationals", 120);
                    case "New York Mets" -> secondTeam = new Team("New York Mets", 121);
                    default -> secondTeam = new Team("New York Mets", 121);
                }
            }catch (Exception error1){
                JOptionPane.showMessageDialog(null,"Something went wrong , Choose your teams again","Error Message",JOptionPane.ERROR_MESSAGE,null);
            }

        });


        // Play Button Event
        playButton.addActionListener(e -> {
            try {
                Game game = new Game();
                //Teams Bench
                HashMap<Integer, ArrayList<String>> firstTeamBench = firstTeam.getTeamBench();
                HashMap<Integer, ArrayList<String>> secondTeamBench = secondTeam.getTeamBench();
                //Game Start
                gameComments.append(game.gameStart(firstTeam, secondTeam));
                firstTeamPlayers.setText(game.printRoster(firstTeam.getName(), firstTeamBench));
                secondTeamPlayers.append(game.printRoster(secondTeam.getName(), secondTeamBench));
                // Set Points for Teams
                ArrayList<Integer> pointsforFirstTeam = new ArrayList<>();
                ArrayList<Integer> pointsForSecondTeam = new ArrayList<>();
                // Game Commentary
                for (int i = 1; i < 10; i++) {
                    gameComments.append("* * * * * * * * * * * * * * * * *");
                    gameComments.append("*  INIZIO PARTE ALTA " + i + "° INNING  *");
                    gameComments.append("* * * * * * * * * * * * * * * * *\n");
                    //game = new Game();
                    gameComments.append(game.playHalfInning(firstTeamBench, secondTeamBench));
                    int pointforFirstTeam = game.getRuns();
                    pointsforFirstTeam.add(pointforFirstTeam);

                    int scoreA = game.calcTotalScore(pointsforFirstTeam);
                    int scoreB = game.calcTotalScore(pointsForSecondTeam);
                    if (scoreA > scoreB)
                        gameComments.append("=====  Il punteggio è " + scoreA + " a " + scoreB + " per i " + firstTeam.getName() + ".  =====\n");
                    else if (scoreA < scoreB)
                        gameComments.append("=====  Il punteggio è " + scoreB + " a " + scoreA + " per i " + secondTeam.getName() + ".  =====\n");
                    else
                        gameComments.append("=====  Il punteggio è di " + scoreA + " pari.  =====\n");

                    gameComments.append("* * * * * * * * * * * * * * * * *");
                    gameComments.append("* INIZIO PARTE BASSA " + i + "° INNING  *");
                    gameComments.append("* * * * * * * * * * * * * * * * *\n");
                    gameComments.append(game.playHalfInning(secondTeamBench, firstTeamBench));
                    int pointforSecondTeam = game.getRuns();
                    pointsForSecondTeam.add(pointforSecondTeam);


                    //mostro punteggio a fine di ogni ripresa
                    scoreA = game.calcTotalScore(pointsforFirstTeam);
                    scoreB = game.calcTotalScore(pointsForSecondTeam);

                    if (scoreA > scoreB)
                        gameComments.append("=====  Il punteggio è " + scoreA + " a " + scoreB + " per i " + firstTeam.getName() + ".  =====\n");
                    else if (scoreA < scoreB)
                        gameComments.append("=====  Il punteggio è " + scoreB + " a " + scoreA + " per i " + secondTeam.getName() + ".  =====\n");
                    else
                        gameComments.append("=====  Il punteggio è di " + scoreA + " pari.  =====\n");
                    //gameComments.append("-------------------------------\n");

                    if (i == 9) {
                        gameComments.append("* * * * * * * * * * * * * * * *");
                        gameComments.append("*  LA PARTITA SI É CONCLUSA!  *");
                        gameComments.append("* * * * * * * * * * * * * * * *");
                    }

                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "Select At Least One Team", "Select Team", JOptionPane.WARNING_MESSAGE);
            }
        });





        // Restart Button Event
        restartButton.addActionListener(e -> {
            firstTeamPlayers.setText(null);
            secondTeamPlayers.setText(null);
            gameComments.setText("");
            firstTeam = null;
            secondTeam = null;
        });








        // Add Panels to Frame , set Visibility & size
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        this.pack();
        setSize(1300, 800);
        setVisible(true);












    }

}