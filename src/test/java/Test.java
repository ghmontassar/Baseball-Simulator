import controller.Game;
import model.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Team firstTeam ;
        Team secondTeam  ;
        //scelta delle squadre da parte dell'utente
        Scanner input = new Scanner(System.in);
        System.out.println("Scegli (con i numeri da 1 a 6) la squadra di casa...\n1. Detroit Tigers\n2. Houston Astros\n3. Kansas City Royals\n4. Los Angeles Dodgers\n5. Washington Nationals\n6. New York Mets");
        String choice = input.nextLine();
        switch (choice) {
            case "1":
                 firstTeam = new Team("Detroit Tigers", 116);
            break;
            case "2":
                 firstTeam = new Team("Houston Astros", 117);
                break;
            case "3":
                 firstTeam = new Team("Kansas City Royals", 118);
                break;
            case "4":
                 firstTeam = new Team("Los Angeles Dodgers", 119);
                break;
            case "5":
                 firstTeam = new Team("Washington Nationals", 120);
                break;
            case "6":
                 firstTeam = new Team("New York Mets", 121);
                break;
            default:
                firstTeam = new Team("New York Mets", 121);
        }
        System.out.println("Scegli (con i numeri da 1 a 6) la squadra ospite...\n1. Detroit Tigers\n2. Houston Astros\n3. Kansas City Royals\n4. Los Angeles Dodgers\n5. Washington Nationals\n6. New York Mets");
         choice = input.nextLine();
        switch (choice) {
            case "1":
                secondTeam = new Team("Detroit Tigers", 116);
                break;
            case "2":
                secondTeam = new Team("Houston Astros", 117);
                break;
            case "3":
                secondTeam = new Team("Kansas City Royals", 118);
                break;
            case "4":
                secondTeam = new Team("Los Angeles Dodgers", 119);
                break;
            case "5":
                secondTeam = new Team("Washington Nationals", 120);
                break;
            case "6":
                secondTeam = new Team("New York Mets", 121);
                break;
            default:
                secondTeam = new Team("New York Mets", 121);
        }

        //creazione delle panchine delle squadre scelte
        HashMap<Integer, ArrayList<String>> firstTeamBench = firstTeam.getTeamBench();
        HashMap<Integer, ArrayList<String>> secondTeamBench = secondTeam.getTeamBench();

        //inizio della partita
        Game prova = new Game();
        System.out.println(prova.gameStart(firstTeam, secondTeam));
        System.out.println(prova.printRoster(firstTeam.getName(), firstTeamBench));
        System.out.println(prova.printRoster(secondTeam.getName(), secondTeamBench));


        ArrayList<Integer> pointsforFirstTeam = new ArrayList<>();
        ArrayList<Integer> pointsForSecondTeam = new ArrayList<>();
        for(int i = 1; i < 10; i++) {
            System.out.println("* * * * * * * * * * * * * * * * *");
            System.out.println("*  INIZIO PARTE ALTA " + i + "° INNING  *");
            System.out.println("* * * * * * * * * * * * * * * * *\n");
            Game game = new Game();
            System.out.println(game.playHalfInning(firstTeamBench, secondTeamBench));
            int pointforFirstTeam = game.getRuns();
            pointsforFirstTeam.add(pointforFirstTeam);

            int scoreA = game.calcTotalScore(pointsforFirstTeam);
            int scoreB = game.calcTotalScore(pointsForSecondTeam);
            if (scoreA > scoreB)
                System.out.println("=====  Il punteggio è " + scoreA + " a " + scoreB + " per i " + firstTeam.getName() + ".  =====\n");
            else if (scoreA < scoreB)
                System.out.println("=====  Il punteggio è " + scoreB + " a " + scoreA + " per i " + secondTeam.getName() + ".  =====\n");
            else
                System.out.println("=====  Il punteggio è di " + scoreA + " pari.  =====\n");

            System.out.println("* * * * * * * * * * * * * * * * *");
            System.out.println("* INIZIO PARTE BASSA " + i + "° INNING  *");
            System.out.println("* * * * * * * * * * * * * * * * *\n");
            game = new Game();
            System.out.println(game.playHalfInning(secondTeamBench, firstTeamBench));
            int pointforSecondTeam = game.getRuns();
            pointsForSecondTeam.add(pointforSecondTeam);


            //mostro punteggio a fine di ogni ripresa
             scoreA = game.calcTotalScore(pointsforFirstTeam);
             scoreB = game.calcTotalScore(pointsForSecondTeam);

            if (scoreA > scoreB)
                System.out.println("=====  Il punteggio è " + scoreA + " a " + scoreB + " per i " + firstTeam.getName() + ".  =====\n");
            else if (scoreA < scoreB)
                System.out.println("=====  Il punteggio è " + scoreB + " a " + scoreA + " per i " + secondTeam.getName() + ".  =====\n");
            else
                System.out.println("=====  Il punteggio è di " + scoreA + " pari.  =====\n");
            //System.out.println("-------------------------------\n");

            if (i == 9) {
                System.out.println("* * * * * * * * * * * * * * * *");
                System.out.println("*  LA PARTITA SI É CONCLUSA!  *");
                System.out.println("* * * * * * * * * * * * * * * *");
            }

        }




    }
}
