package com.carbolemons.MojangWebApi;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.UUID;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.BiMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class MojangWebApi {
      public static HashBiMap<String, String> userbase = HashBiMap.create();

    public static String grabRealName(String UUID) {

        //check the usermap if the UUID is already queried
        if(userbase.containsKey(UUID)){
            System.out.println("Grabbing username from hash map");
            //return from the user hashmap
            return userbase.get(UUID);
        }
        // return the fresh username value
        System.out.println("Grabbing username from Mojang Web Api");
        return QueryWebApi(UUID, true);

    }

    public static String grabRealUUID(String NAME){
        if(userbase.containsValue(NAME)){
            System.out.println("Grabbing UUID from hash map");
            //return from the user hashmap
            return userbase.inverse().get(NAME);
        }
        System.out.println("Grabbing UUID from Mojang Web Api");
        return QueryWebApi(NAME, false);
        //return "ignore";

    }

    private static String QueryWebApi(String id, Boolean isUUID){
        if(isUUID) {
            String untrimmedUUID = id;
            try {
                //url to the mojang db
                String mojangUrl = "https://api.mojang.com/user/profiles/" + id.replace("-", "") + "/names";
                //create URL instance thing
                URL WebApi = new URL(mojangUrl);
                //connect and read the data from the website
                BufferedReader in = new BufferedReader(new InputStreamReader(WebApi.openStream()));
                //this obtains the raw text from the mojang api website
                String username = "Popbob";
                String rawInput = in.readLine();
                System.out.println(rawInput);
                in.close();
                if (rawInput != null) {
                    JSONParser parseRaw = new JSONParser();
                    //parse the raw text to a json array (cuz thats what the website returns)
                    JSONArray namesJSON = (JSONArray) parseRaw.parse(rawInput);
                    //obtain the current username (and has some other junk data)
                    JSONObject usernameSlot = (JSONObject) namesJSON.get(namesJSON.size() - 1);
                    username = usernameSlot.get("name").toString();
                    //fully parsed username rn right here, add it to the hash map and return the username as string
                }
                //always close your streams :)
                in.close();
                userbase.put(untrimmedUUID, username);
                return username;
            } catch (java.net.MalformedURLException e) {
                System.out.println("MALIGNED URL, CARBOLEMONS IS DUMB IF YOU ARE READING THIS, BECAUSE, WHAT, IMPOSSIBLE... LITCHERALLLY...");
                return "";
            } catch (java.io.IOException e) {
                System.out.println("uh, something went horribly wrong if you are seeing this in your log.");
                return "";
            } catch (ParseException e) {
                System.out.println("JSON userdata was parsed wrong, shit.");
                return "";
            }
        } else {
            try {
                String mojangUrl = "https://api.mojang.com/users/profiles/minecraft/" + id;
                URL WebApi = new URL(mojangUrl);
                //connect and read the data from the website
                BufferedReader in = new BufferedReader(new InputStreamReader(WebApi.openStream()));
                //this obtains the raw text from the mojang api website
                String trimmedUUID = "00000000000000000000000000000000";
                String UUID = "00000000-0000-0000-0000-000000000000";
                String name = "Popbob";
                String rawInput = in.readLine();
                in.close();
                if (rawInput != null) {
                    JSONParser parseRaw = new JSONParser();
                    JSONObject rawJSON = (JSONObject) parseRaw.parse(rawInput);
                    trimmedUUID = rawJSON.get("id").toString();
                    String __untrimUUID = new String(trimmedUUID);
                    StringBuilder untrimUUID = new StringBuilder(__untrimUUID);
                    untrimUUID.insert(8, '-');
                    untrimUUID.insert(13, '-');
                    untrimUUID.insert(18, '-');
                    untrimUUID.insert(23, '-');
                    UUID = untrimUUID.toString();

                }
                //always close your streams :)
                userbase.put(UUID, id);
                return UUID;
            } catch (java.net.MalformedURLException e) {
                System.out.println("MALIGNED URL, CARBOLEMONS IS DUMB IF YOU ARE READING THIS, BECAUSE, WHAT, IMPOSSIBLE... LITCHERALLLY...");
                return "";
            } catch (java.io.IOException e) {
                System.out.println("uh, something went horribly wrong if you are seeing this in your log.");
                return "";
            } catch (ParseException e) {
                System.out.println("JSON userdata was parsed wrong, shit.");
                return "";
            }
        }
    }

    public static void main(String[] args){
        String UUID = "0c716198-b9cb-4ac3-8216-c5b6c68e0c51";
		String NAME = "Carbolemons";

		System.out.println(grabRealUUID(NAME));		
		System.out.println(grabRealName(UUID));
		UUID realUUID = java.util.UUID.fromString(grabRealUUID(NAME));
		if(realUUID instanceof UUID) System.out.println("IS UUID");
		
    }
    
}
