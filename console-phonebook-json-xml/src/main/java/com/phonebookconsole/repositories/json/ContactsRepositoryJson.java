package com.phonebookconsole.repositories.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonebookconsole.exceptions.DataAccessException;
import com.phonebookconsole.interfaces.ContactsPersistable;
import com.phonebookconsole.entities.Contact;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ContactsRepositoryJson implements ContactsPersistable {

    private String fileName;

    public ContactsRepositoryJson() {

        this.fileName = "contacts.json";

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
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

            File file = new File(fileName);
            if (file.length() != 0) {
                Object obj = jsonParser.parse(reader);
                JSONArray items = (JSONArray) obj;

                if (items.size() != 0) {
                    JSONObject lastItemJson = (JSONObject) (items.get(items.size() - 1));
                    Contact lastItem = new ObjectMapper().readValue(lastItemJson.toJSONString(), Contact.class);

                    nextId = lastItem.getId();
                }
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }

        return nextId + 1;
    }

    @Override
    public Contact GetById(int id) {

        Contact result = null;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            Object obj = jsonParser.parse(reader);
            JSONArray items = (JSONArray) obj;

            for (int i = 0; i < items.size(); i++) {

                JSONObject current = (JSONObject) (items.get(i));
                Contact currentItem = new ObjectMapper().readValue(current.toJSONString(), Contact.class);
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
    public ArrayList<Contact> GetAll() {

        ArrayList<Contact> result = new ArrayList<Contact>();
        File file = new File(fileName);
        if (file.length() != 0) {
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader(fileName)) {

                Object obj = jsonParser.parse(reader);
                JSONArray items = (JSONArray) obj;
                for (int i = 0; i < items.size(); i++) {
                    JSONObject current = (JSONObject) (items.get(i));
                    Contact currentItem = new ObjectMapper().readValue(current.toJSONString(), Contact.class);
                    result.add(currentItem);
                }
            }
            catch(Exception ex) {
                throw new DataAccessException(ex);
            }
        }

        return result;
    }

    @Override
    public void Add(Contact item) {

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
            addItem.put("userId", item.getUserId());
            addItem.put("firstName", item.getFirstName());
            addItem.put("lastName", item.getLastName());
            addItem.put("email", item.getEmail());

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
    public void Edit(Contact item) {

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            Object obj = jsonParser.parse(reader);
            JSONArray items = (JSONArray) obj;
            for (int i = 0; i < items.size(); i++) {

                JSONObject current = (JSONObject) items.get(i);
                Contact currentItem = new ObjectMapper().readValue(current.toJSONString(), Contact.class);
                if (currentItem.getId() == item.getId()) {
                    current.put("id", item.getId());
                    current.put("userId", item.getUserId());
                    current.put("firstName", item.getFirstName());
                    current.put("lastName", item.getLastName());
                    current.put("email", item.getEmail());
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
    public void Delete(Contact item) {

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            Object obj = jsonParser.parse(reader);
            JSONArray items = (JSONArray) obj;
            for (int i = 0; i < items.size(); i++) {

                JSONObject current = (JSONObject) items.get(i);
                Contact currentItem = new ObjectMapper().readValue(current.toJSONString(), Contact.class);
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
    public ArrayList<Contact> GetAll(int parentId) {

        ArrayList<Contact> result = new ArrayList<Contact>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            File file = new File(fileName);
            if (file.length() != 0) {
                Object obj = jsonParser.parse(reader);
                JSONArray items = (JSONArray) obj;
                for (int i = 0; i < items.size(); i++) {
                    JSONObject current = (JSONObject) (items.get(i));
                    Contact currentItem = new ObjectMapper().readValue(current.toJSONString(), Contact.class);

                    if (currentItem.getUserId() == parentId)
                        result.add(currentItem);
                }
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }

        return result;
    }
}
