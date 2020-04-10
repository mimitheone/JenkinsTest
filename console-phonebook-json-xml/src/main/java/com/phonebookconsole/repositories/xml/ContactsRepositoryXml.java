package com.phonebookconsole.repositories.xml;

import com.phonebookconsole.exceptions.DataAccessException;
import com.phonebookconsole.interfaces.ContactsPersistable;
import com.phonebookconsole.entities.Contact;
import com.phonebookconsole.tools.xmlHelpers.ContactList;
import com.thoughtworks.xstream.XStream;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ContactsRepositoryXml implements ContactsPersistable {

    private String fileName;
    private XStream xStream;

    public ContactsRepositoryXml() {

        this.fileName = "contacts.xml";
        this.xStream = new XStream();
        xStream.alias("contact", Contact.class);
        xStream.alias("contacts", ContactList.class);
        xStream.addImplicitCollection(ContactList.class, "list");

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

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                ContactList list = (ContactList) xStream.fromXML(content);

                if (list.getList().size() > 0)
                    nextId = list.getList().get(list.getList().size() - 1).getId();
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

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                ContactList list = (ContactList) xStream.fromXML(content);

                for (int i = 0; i < list.getList().size(); i++) {

                    if (list.get(i).getId() == id) {
                        result = list.get(i);
                        break;
                    }
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

        ArrayList<Contact> result = new ArrayList<>();

        try {
            File file = new File(fileName);
            if (file.length() != 0) {
                String content = Files.readString(Paths.get(fileName));
                ContactList list = (ContactList) xStream.fromXML(content);

                result = (ArrayList<Contact>) list.getList();
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }

        return result;
    }

    @Override
    public void Add(Contact item) {

        item.setId(GetNextId());
        ContactList list;

        try {
            File file = new File(fileName);
            if (file.length() != 0) {
                String content = Files.readString(Paths.get(fileName));
                list = (ContactList) xStream.fromXML(content);
            } else {
                list = new ContactList();
            }

            list.add(item);
            String xml = xStream.toXML(list);

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(xml);
            writer.close();
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void Edit(Contact item) {

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                ContactList list = (ContactList) xStream.fromXML(content);

                for (int i = 0; i < list.getList().size(); i++) {

                    Contact current = list.get(i);
                    if (current.getId() == item.getId()) {
                        current.setUserId(item.getUserId());
                        current.setEmail(item.getEmail());
                        current.setFirstName(item.getFirstName());
                        current.setLastName(item.getLastName());
                    }
                }

                String xml = xStream.toXML(list);
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write(xml);
                writer.close();
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void Delete(Contact item) {

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                ContactList list = (ContactList) xStream.fromXML(content);

                for (int i = 0; i < list.getList().size(); i++) {

                    Contact current = list.get(i);
                    if (current.getId() == item.getId()) {
                        list.remove(i);
                        break;
                    }
                }

                String xml = xStream.toXML(list);
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write(xml);
                writer.close();
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public ArrayList<Contact> GetAll(int parentId) {

        ArrayList<Contact> result = new ArrayList<Contact>();

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                ContactList list = (ContactList) xStream.fromXML(content);

                for (int i = 0; i < list.getList().size(); i++) {

                    Contact current = list.get(i);
                    if (current.getUserId() == parentId) {
                        result.add(current);
                    }
                }
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }

        return result;
    }
}
