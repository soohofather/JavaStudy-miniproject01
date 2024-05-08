package com.example.miniproject01.genre.Api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GenreData {
    public static void main(String[] args) {
        String apiKey = "a986250901395deffed1ae6e646ae735";  // TMDB에서 발급받은 API 키
        String baseUrl = "https://api.themoviedb.org/3/genre/movie/list";
        String finalUrl = String.format("%s?api_key=%s&language=en-US&page=1", baseUrl, apiKey);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(finalUrl))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {

                String jsonResponse = response.body();

                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonArray genres = jsonObject.getAsJsonArray("genres");

                System.out.println(genres);

//                for (JsonElement genreElement : genres) {
//                    JsonObject genre = genreElement.getAsJsonObject();
//                    int id = genre.get("id").getAsInt();
//                    String name = genre.get("name").getAsString();
//                    System.out.println("ID: " + id + ", Name: " + name);
//                }

            } else {
                System.out.println("Failed to fetch data. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}