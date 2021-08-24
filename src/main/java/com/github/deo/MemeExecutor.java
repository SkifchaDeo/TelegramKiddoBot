package com.github.deo;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendPhoto;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MemeExecutor implements CommandExecutor<SendPhoto> {

    @Override
    public AbstractSendRequest<SendPhoto> execute(Message message, String text) {
        try {
            URL url = new URL("https://meme-api.herokuapp.com/gimme");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader inside = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = inside.readLine()) != null) {
                content.append(inputLine);
            }
            inside.close();
            con.disconnect();
            return new SendPhoto(message.chat().id(), String.valueOf(new JSONObject(content.toString()).getString("url")));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
