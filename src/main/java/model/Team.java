package model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Team {
    private String name;
    private int id;

    public Team(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<Integer, ArrayList<String>> getTeamBench() {

        HashMap<Integer, ArrayList<String>> teamBench = new HashMap<>();
        HashMap<Integer, ArrayList<String>> teamInformation = new HashMap<>();

        HttpResponse<String> response = Unirest.get("https://mlb-data.p.rapidapi.com/json/named.roster_40.bam?team_id='" + this.id + "'")
                .header("x-rapidapi-key", "939a723f0bmsh737fc233329b316p1f619ejsn86a43790ce71")
                .header("x-rapidapi-host", "mlb-data.p.rapidapi.com")
                .asString();

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(response.getBody());
        String jsonString = gson.toJson(jsonElement);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject team = jsonObject.getJSONObject("roster_40");
        JSONObject information = team.getJSONObject("queryResults");
        JSONArray players = (JSONArray) information.get("row");


        for (int i = 0; i < players.length(); i++) {
            ArrayList<String> playerDetails = new ArrayList<>();

            String playerId = players.getJSONObject(i).getString("player_id");
            String playerName = players.getJSONObject(i).getString("name_display_first_last");
            String teamName = players.getJSONObject(i).getString("team_name");
            String primaryPosition = players.getJSONObject(i).getString("primary_position");
            playerDetails.add(playerId);
            playerDetails.add(playerName);
            playerDetails.add(teamName);
            playerDetails.add(primaryPosition);
            teamInformation.put( teamInformation.size() + 1 , playerDetails);
        };

        for(int id : teamInformation.keySet()) {
            String playerId = teamInformation.get(id).get(0);
            HttpResponse<String> response2 = Unirest.get("https://mlb-data.p.rapidapi.com/json/named.sport_hitting_tm.bam?season='2017'&player_id='" + playerId + "'&league_list_id='mlb'&game_type='R'")
                    .header("x-rapidapi-key", "939a723f0bmsh737fc233329b316p1f619ejsn86a43790ce71")
                    .header("x-rapidapi-host", "mlb-data.p.rapidapi.com")
                    .asString();

            Gson playerGson = new Gson();
            JsonParser playerJsonParser = new JsonParser();
            JsonElement playerJsonElement = playerJsonParser.parse(response2.getBody());
            String playerJsonString = playerGson.toJson(playerJsonElement);
            JSONObject playerJsonObject = new JSONObject(playerJsonString);
            JSONObject allInformation = playerJsonObject.getJSONObject("sport_hitting_tm");
            JSONObject playerResult = allInformation.getJSONObject("queryResults");
            String totalSize = playerResult.getString("totalSize");
            if(totalSize.equals("1")) {
                JSONObject player = playerResult.getJSONObject("row");
                String playerAVG = player.getString("avg");
                String playerAB = player.getString("ab");
                if(!playerAVG.equals(".---") && !playerAVG.equals(".000")) {
                    teamInformation.get(id).add(playerAVG);

                    if (Integer.parseInt(playerAB) < 700) {

                        Random random = new Random();
                        int low = 700;
                        int high = 1000;
                        int randomNum = random.nextInt(high-low) + low;
                        int newAB = Integer.parseInt(playerAB) + randomNum;
                        playerAB = (newAB < 1000) ? String.valueOf(newAB) : String.valueOf(1000);
                    }
                    teamInformation.get(id).add(playerAB);
                }
            }
        }

        for(int id : teamInformation.keySet()) {
            if(teamInformation.get(id).size() > 4)
                teamBench.put(teamBench.size() + 1, teamInformation.get(id));
        }
        return teamBench;
    }
}
