package com.phonebookconsole.repositories.xml;

import com.phonebookconsole.exceptions.DataAccessException;
import com.phonebookconsole.interfaces.PhonesPersistable;
import com.phonebookconsole.entities.Phone;
import com.phonebookconsole.tools.xmlHelpers.PhoneList;
import com.thoughtworks.xstream.XStream;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PhonesRepositoryXml implements PhonesPersistable {

    private String fileName;
    private XStream xStream;

    public PhonesRepositoryXml() {

        this.fileName = "phones.xml";
        this.xStream = new XStream();
        xStream.alias("phone", Phone.class);
        xStream.alias("phones", PhoneList.class);
        xStream.addImplicitCollection(PhoneList.class, "list");

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
                PhoneList list = (PhoneList) xStream.fromXML(content);

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
    public Phone GetById(int id) {

        Phone result = null;

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                PhoneList list = (PhoneList) xStream.fromXML(content);

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
    public ArrayList<Phone> GetAll() {

        ArrayList<Phone> result = new ArrayList<>();

        try {
            File file = new File(fileName);
            if (file.length() != 0) {
                String content = Files.readString(Paths.get(fileName));
                PhoneList list = (PhoneList) xStream.fromXML(content);

                result = (ArrayList<Phone>) list.getList();
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }

        return result;
    }

    @Override
    public void Add(Phone item) {

        item.setId(GetNextId());
        PhoneList list;

        try {
            File file = new File(fileName);

            if (file.length() != 0) {
                String content = Files.readString(Paths.get(fileName));
                list = (PhoneList) xStream.fromXML(content);
            } else {
                list = new PhoneList();
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
    public void Edit(Phone item) {

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                PhoneList list = (PhoneList) xStream.fromXML(content);

                for (int i = 0; i < list.getList().size(); i++) {

                    Phone current = list.get(i);
                    if (current.getId() == item.getId()) {
                        current.setPhone(item.getPhone());
                        current.setContactId(item.getContactId());
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
    public void Delete(Phone item) {

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                PhoneList list = (PhoneList) xStream.fromXML(content);

                for (int i = 0; i < list.getList().size(); i++) {

                    Phone current = list.get(i);
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
    public ArrayList<Phone> GetAll(int parentId) {

        ArrayList<Phone> result = new ArrayList<Phone>();

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                PhoneList list = (PhoneList) xStream.fromXML(content);

                for (int i = 0; i < list.getList().size(); i++) {

                    Phone current = list.get(i);
                    if (current.getContactId() == parentId) {
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
