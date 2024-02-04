package com.appstate.cisco.presentation.service;

import com.appstate.cisco.presentation.model.entity.PlayerEntity;
import com.appstate.cisco.presentation.model.entity.PlayerKey;
import com.appstate.cisco.presentation.repository.PlayerRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FetchingService extends Thread {
    Gson gson = new Gson();

    @Autowired
    PlayerRepository playerRepository;

    public void run() {
        System.out.println(this.getName() + ": Fetching Thread is running...");
        while(true) {
            try {
                URL url = new URL("http://localhost:3000/");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                // Start a new game
                con.setRequestMethod("POST");
                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) { // success
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    Map<String, String> map = gson.fromJson(response.toString(), Map.class);
                    // collect new gameId
                    String gameId = map.get("gameId");
                    String gameTime = "";
                    double quarter = 0;
                    do {
                        // Start fetching player info for the game state
                        url = new URL("http://localhost:3000/" + gameId + "/players");
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        responseCode = con.getResponseCode();

                        if (responseCode == HttpURLConnection.HTTP_OK) { // success
                            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            response = new StringBuffer();

                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                            in.close();
                            List<Map> players = gson.fromJson(response.toString(), List.class);
                            url = new URL("http://localhost:3000/" + gameId);
                            con = (HttpURLConnection) url.openConnection();
                            con.setRequestMethod("GET");
                            responseCode = con.getResponseCode();
                            Map<String, Object> key = new HashMap<>();
                            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                response = new StringBuffer();

                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();

                                Map info = gson.fromJson(response.toString(), Map.class);
                                gameTime = (String) info.get("time");
                                key.put("gameTime", gameTime);
                                quarter = (double) info.get("quarter");
                                key.put("quarter", quarter);

                            } else {
                                System.out.println("GET game info did not work.");
                            }

                            for (Map player : players) {
                                String name = (String) player.remove("name");
                                int i = name.length();
                                while (i > 0 && Character.isDigit(name.charAt(i - 1))) {
                                    i--;
                                }
                                int number = Integer.parseInt(name.substring(i));
                                key.put("number", number);
                                key.put("side", name.substring(0, 4));
                                key.put("gameId", gameId);

                                PlayerKey playerKey = gson.fromJson(gson.toJson(key), PlayerKey.class);
                                PlayerEntity playerEntity = gson.fromJson(gson.toJson(player), PlayerEntity.class);
                                playerEntity.setKey(playerKey);
                                playerRepository.save(playerEntity);

                            }

                        } else {
                            System.out.println("GET players request did not work.");
                        }
                    Thread.sleep(1000);
                 } while(!gameTime.equals("0:00") && quarter != 4);
                } else{
                    System.out.println("POST request did not work.");
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
