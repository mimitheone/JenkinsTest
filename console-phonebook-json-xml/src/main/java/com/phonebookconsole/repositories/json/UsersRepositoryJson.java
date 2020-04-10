package com.phonebookconsole.repositories.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonebookconsole.exceptions.DataAccessException;
import com.phonebookconsole.interfaces.UsersPersistable;
import com.phonebookconsole.entities.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class UsersRepositoryJson implements UsersPersistable {

    private String fileName;

    public UsersRepositoryJson() {

        this.fileName = "users.json";

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();

                FileWriter writer = new FileWriter(file);
                JSONArray initialArray = new JSONArray();

                JSONObject initialUser = new JSONObject();
                initialUser.put("id", 1);
                initialUser.put("username", "admin");
                initialUser.put("password", "adminpass");
                initialUser.put("firstName", "Administrator");
                initialUser.put("lastName", "Administrator");
                initialUser.put("isAdmin", true);
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                initialUser.put("created", dateFormat.format(date));
                initialUser.put("createdBy", 1);

                initialArray.add(initialUser);
                writer.write(initialArray.toJSONString());

                writer.flush();
                writer.close();
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    private int GetNextId() {

        int nextId = 0;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            Object obj = jsonParser.parse(reader);
            JSONArray items = (JSONArray) obj;

            if (items.size() != 0) {
                JSONObject lastItemJson = (JSONObject) (items.get(items.size() - 1));
                User lastItem = new ObjectMapper().readValue(lastItemJson.toJSONString(), User.class);

                nextId = lastItem.getId();
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }

        return nextId + 1;
    }

    @Override
    public User GetById(int id) {

        User result = null;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            Object obj = jsonParser.parse(reader);
            JSONArray items = (JSONArray) obj;

            for (int i = 0; i < items.size(); i++) {

                JSONObject current = (JSONObject) (items.get(i));
                User currentItem = new ObjectMapper().readValue(current.toJSONString(), User.class);
                if (currentItem.getId() == id) {
                    result = currentItem;
                    break;
                }
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }

        return result;
    }

    @Override
    public ArrayList<User> GetAll() {

        ArrayList<User> result = new ArrayList<User>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            Object obj = jsonParser.parse(reader);
            JSONArray items = (JSONArray) obj;

            for (int i = 0; i < items.size(); i++) {
                JSONObject current = (JSONObject) (items.get(i));
                User currentItem = new ObjectMapper().readValue(current.toJSONString(), User.class);
                result.add(currentItem);
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }

        return result;
    }

    @Override
    public void Add(User item) {

        item.setId(GetNextId());
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            JSONArray items;
            File file = new File(fileName);
            if (file.length() != 0) {
                Object obj = jsonParser.parse(reader);
                items = (JSONArray) obj;
            } else {
                items = new JSONArray();
            }

            JSONObject addItem = new JSONObject();
            addItem.put("id", item.getId());
            addItem.put("username", item.getUsername());
            addItem.put("password", item.getPassword());
            addItem.put("firstName", item.getFirstName());
            addItem.put("lastName", item.getLastName());
            addItem.put("isAdmin", item.getIsAdmin());

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            addItem.put("created", dateFormat.format(date));
            addItem.put("createdBy", item.getCreatedBy());

            items.add(addItem);

            PrintWriter writer = new PrintWriter(fileName);
            writer.write(items.toJSONString());
            writer.close();
            writer.flush();
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void Edit(User item) {

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            Object obj = jsonParser.parse(reader);
            JSONArray items = (JSONArray) obj;
            for (int i = 0; i < items.size(); i++) {

                JSONObject current = (JSONObject) items.get(i);
                User currentItem = new ObjectMapper().readValue(current.toJSONString(), User.class);

                if (currentItem.getId() == item.getId()) {
                    current.put("id", item.getId());
                    current.put("username", item.getUsername());
                    current.put("password", item.getPassword());
                    current.put("firstName", item.getFirstName());
                    current.put("lastName", item.getLastName());
                    current.put("isAdmin", item.getIsAdmin());
                    Date date = item.getCreated();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                    current.put("created", dateFormat.format(date));
                    current.put("createdBy", item.getCreatedBy());
                }
            }

            PrintWriter writer = new PrintWriter(fileName);
            writer.write(items.toJSONString());
            writer.close();
            writer.flush();
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void Delete(User item) {

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {
            Object obj = jsonParser.parse(reader);
            JSONArray items = (JSONArray) obj;

            for (int i = 0; i < items.size(); i++) {

                JSONObject current = (JSONObject) items.get(i);
                User currentItem = new ObjectMapper().readValue(current.toJSONString(), User.class);

                if (currentItem.getId() == item.getId()) {
                    items.remove(current);
                    break;
                }
            }

            PrintWriter writer = new PrintWriter(fileName);
            writer.write(items.toJSONString());
            writer.close();
            writer.flush();
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public User GetByUsernameAndPassword(String username, String password) {

        User result = null;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            Object obj = jsonParser.parse(reader);
            JSONArray items = (JSONArray) obj;
            for (int i = 0; i < items.size(); i++) {

                JSONObject current = (JSONObject) (items.get(i));
                if (current.get("username").equals(username) &&
                        current.get("password").equals(password)) {
                    result = new ObjectMapper().readValue(current.toJSONString(), User.class);
                    break;
                }
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }

        return result;
    }
}
