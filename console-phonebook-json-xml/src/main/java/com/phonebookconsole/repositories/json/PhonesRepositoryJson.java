package com.phonebookconsole.repositories.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonebookconsole.exceptions.DataAccessException;
import com.phonebookconsole.interfaces.PhonesPersistable;
import com.phonebookconsole.entities.Phone;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;

public class PhonesRepositoryJson implements PhonesPersistable {

    private String fileName;

    public PhonesRepositoryJson() {

        this.fileName = "phones.json";

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
                    Phone lastItem = new ObjectMapper().readValue(lastItemJson.toJSONString(), Phone.class);

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
    public Phone GetById(int id) {

        Phone result = null;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            Object obj = jsonParser.parse(reader);
            JSONArray items = (JSONArray) obj;

            for (int i = 0; i < items.size(); i++) {

                JSONObject current = (JSONObject) (items.get(i));
                Phone currentItem = new ObjectMapper().readValue(current.toJSONString(), Phone.class);
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
    public ArrayList<Phone> GetAll() {

        ArrayList<Phone> result = new ArrayList<Phone>();

        File file = new File(fileName);
        if (file.length() != 0) {
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader(fileName)) {

                Object obj = jsonParser.parse(reader);
                JSONArray items = (JSONArray) obj;

                for (int i = 0; i < items.size(); i++) {
                    JSONObject current = (JSONObject) (items.get(i));
                    Phone currentItem = new ObjectMapper().readValue(current.toJSONString(), Phone.class);
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
    public void Add(Phone item) {

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

            JSONObject newItem = new JSONObject();
            newItem.put("id", item.getId());
            newItem.put("contactId", item.getContactId());
            newItem.put("phone", item.getPhone());

            items.add(newItem);

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
    public void Edit(Phone item) {

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            Object obj = jsonParser.parse(reader);
            JSONArray items = (JSONArray) obj;
            for (int i = 0; i < items.size(); i++) {

                JSONObject editItem = (JSONObject) items.get(i);
                Phone currentPhone = new ObjectMapper().readValue(editItem.toJSONString(), Phone.class);
                if (currentPhone.getId() == item.getId()) {
                    editItem.put("id", item.getId());
                    editItem.put("contactId", item.getContactId());
                    editItem.put("phone", item.getPhone());
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
    public void Delete(Phone item) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            Object obj = jsonParser.parse(reader);
            JSONArray items = (JSONArray) obj;

            for (int i = 0; i < items.size(); i++) {

                JSONObject current = (JSONObject) items.get(i);
                Phone deleteItem = new ObjectMapper().readValue(current.toJSONString(), Phone.class);

                if (deleteItem.getId() == item.getId()) {
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
    public ArrayList<Phone> GetAll(int parentId) {

        ArrayList<Phone> result = new ArrayList<Phone>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {

            File file = new File(fileName);
            if (file.length() != 0) {
                Object obj = jsonParser.parse(reader);
                JSONArray items = (JSONArray) obj;

                for (int i = 0; i < items.size(); i++) {
                    JSONObject current = (JSONObject) (items.get(i));
                    Phone currentItem = new ObjectMapper().readValue(current.toJSONString(), Phone.class);

                    if (currentItem.getContactId() == parentId)
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
