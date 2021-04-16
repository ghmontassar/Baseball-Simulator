package controller;

import model.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game {
	int outs = 0;
	int runs = 0;

	public int getRuns() {
		return runs;
	}

	HashMap<Integer, Integer> baseLoad = new HashMap<>();

	public void createField() {
		baseLoad.put(1,0);
		baseLoad.put(2,0);
		baseLoad.put(3,0);
	}


	public String atBatResult(HashMap<Integer, ArrayList<String>> firstTeamBench, HashMap<Integer, ArrayList<String>> secondTeamBench, int count) {


		Random random = new Random();
		int low = 1;
		int high = 9;
		int randomNum = random.nextInt(high-low) + low;

		String result = null;
		int swing = random.nextInt(100);
		double hit = swing + (Double.parseDouble(firstTeamBench.get(count).get(4))/40) - (Integer.parseInt(secondTeamBench.get(randomNum).get(5))*0.0075);

		int position = 0;
		int value = 1;
		if (hit < 20) {
			result = firstTeamBench.get(count).get(1) + " viene eliminato al piatto per stikeout.\n";
			position = -1;
			outs += 1;
		}
		if (hit >= 20 && hit < 50) {
			result = firstTeamBench.get(count).get(1) + " viene eliminato in campo da " +
					secondTeamBench.get(randomNum).get(1) + ".\n";
			position = -1;
			outs += 1;
		}
		if (hit == 50) {
			result = firstTeamBench.get(count).get(1) + " è stato colpito dal lanciatore e avanza in 1^ base.\n";
			position = 1;
		}
		if (hit >= 51 && hit < 58) {
			result = firstTeamBench.get(count).get(1) + " avanza in 1^ su base per ball.\n";
			position = 1;
		}
		if (hit >= 58 && hit < 81) {
			result = firstTeamBench.get(count).get(1) + " batte un singolo su " +
					secondTeamBench.get(randomNum).get(1) + ".\n";
			position = 1;
		}
		if (hit >= 81 && hit < 90) {
			result = firstTeamBench.get(count).get(1) + " batte un doppio su " +
					secondTeamBench.get(randomNum).get(1) + ".\n";
			position = 2;
		}
		if (hit >= 90 && hit < 95) {
			result = firstTeamBench.get(count).get(1) + " batte un triplo su " +
					secondTeamBench.get(randomNum).get(1) + ".\n";
			position = 3;
		}
		if (hit >= 95) {
			result = firstTeamBench.get(count).get(1) + " batte un fuoricampo.\n";
			position = 0;
			baseLoad.clear();
		}
		while(position > 0){
			runs += checkBaseLoad(1, value);
			position--;
			if(value > 0) value--;
		}

		return result;
	}

	int scores = 0;
	private int checkBaseLoad(int position, int value){

		if (baseLoad.get(position) != 0 && position + 1 <= baseLoad.size()) {
			 scores += checkBaseLoad(position + 1, value);
			baseLoad.put(position + 1, baseLoad.get(position));
		} else if(position+1 > baseLoad.size() && (baseLoad.get(baseLoad.size()) > 0)){
			scores = 1;
		}
		if (position == 0)
			scores = baseLoad.size() + 1;
		else if (position == -1)
			scores = 0;
		else
			baseLoad.put(position, value);
		return scores;
	}



	private void clearInning() {
		baseLoad.clear();
		outs = 0;
	}


	private boolean manOnFirst() {
		if (baseLoad.get(1) != 0)
			return true;
		else
			return false;
	}
	private boolean manOnSecond() {
		if (baseLoad.get(2) != 0)
			return true;
		else
			return false;
	}
	private boolean manOnThird() {
		if (baseLoad.get(3) != 0)
			return true;
		else
			return false;
	}

	public String showBaseLoad() {
		String state = "";
		if (!manOnFirst() && !manOnSecond() && !manOnThird())
			state = "Nessun corridore in base.\n";
		else if (manOnFirst() && manOnSecond() && manOnThird())
			state = "Corridori in 1^, 2^ e 3^ base.\n";
		else if (manOnFirst() && !manOnSecond() && manOnThird())
			state = "Corridori in 1^ e 3^ base.\n";
		else if (manOnFirst() && manOnSecond() && !manOnThird())
			state = "Corridori in 1^ e 2^ base.\n";
		else if (manOnFirst() && !manOnSecond() && !manOnThird())
			state = "Corridore in 1^ base.\n";
		else if (!manOnFirst() && manOnSecond() && !manOnThird())
			state = "Corridore in 2^ base.\n";
		else if (!manOnFirst() && !manOnSecond() && manOnThird())
			state = "Corridore in 3^ base.\n";
		else if (!manOnFirst() && manOnSecond() && manOnThird())
			state = "Corridori in 2^ e 3^ base.\n";
		return state;
	}


	public String gameStart(Team firstTeam, Team secondTeam) {
		return "Oggi si affronteranno in campo le squadre dei " + firstTeam.getName() + " e dei " + secondTeam.getName() +
						"!\nAd ospitare l'incontro sul proprio campo saranno i " + secondTeam.getName() + ".\n";
	}



	public String printRoster(String name, Map<Integer, ArrayList<String>> roster) {
		int index = 1;
		String print = name + " roster:\n";
		for (Map.Entry<Integer, ArrayList<String>> entry : roster.entrySet()) {
			print += index + " - " + entry.getValue().get(1) + "\n";
			index ++;
		}
		return print;

	}

	public String playHalfInning(HashMap<Integer, ArrayList<String>> firstTeamBench, HashMap<Integer, ArrayList<String>> secondTeamBench) {
		String result = "";
		createField();
		int count = 1;

		while (outs < 3) {
			result += atBatResult(firstTeamBench, secondTeamBench, count);
			result += "eliminati: " + outs + "\n";
			result += "punti segnati: " + runs + "\n";
			result += baseLoad + "\n";
			result += showBaseLoad() + "\n";
			System.out.print(result);
			//result = "";
			count++;
		}

		return result;
	}


	public int calcTotalScore(ArrayList<Integer> totalRuns) {
		int sum = 0;
		for(int partial : totalRuns)
			sum += partial;
		return sum;
	}



}
