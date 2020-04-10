package com.phonebookconsole.repositories.xml;

import com.phonebookconsole.exceptions.DataAccessException;
import com.phonebookconsole.interfaces.UsersPersistable;
import com.phonebookconsole.entities.User;
import com.phonebookconsole.tools.xmlHelpers.UserList;
import com.thoughtworks.xstream.XStream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class UsersRepositoryXml implements UsersPersistable {

    private String fileName;
    private XStream xStream;

    public UsersRepositoryXml() {

        this.fileName = "users.xml";
        this.xStream = new XStream();
        xStream.alias("user", User.class);
        xStream.alias("users", UserList.class);
        xStream.addImplicitCollection(UserList.class, "list");

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();

                User initialUser = new User();
                initialUser.setUsername("admin");
                initialUser.setPassword("adminpass");
                initialUser.setFirstName("Administrator");
                initialUser.setLastName("Administrator");
                initialUser.setIsAdmin(true);
                initialUser.setCreatedBy(1);

                this.Add(initialUser);
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
                UserList list = (UserList) xStream.fromXML(content);

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
    public User GetById(int id) {

        User result = null;

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                UserList list = (UserList) xStream.fromXML(content);

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
    public ArrayList<User> GetAll() {

        ArrayList<User> result = new ArrayList<>();

        try {
            File file = new File(fileName);
            if (file.length() != 0) {
                String content = Files.readString(Paths.get(fileName));
                UserList list = (UserList) xStream.fromXML(content);

                result = (ArrayList<User>) list.getList();
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
        item.setCreated(new Date());

        UserList list;

        try {
            File file = new File(fileName);
            if (file.length() != 0) {
                String content = Files.readString(Paths.get(fileName));
                list = (UserList) xStream.fromXML(content);
            } else {
                list = new UserList();
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
    public void Edit(User item) {

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                UserList list = (UserList) xStream.fromXML(content);

                for (int i = 0; i < list.getList().size(); i++) {

                    User current = list.get(i);
                    if (current.getId() == item.getId()) {
                        current.setUsername(item.getUsername());
                        current.setPassword(item.getPassword());
                        current.setFirstName(item.getFirstName());
                        current.setLastName(item.getLastName());
                        current.setIsAdmin(item.getIsAdmin());
                        current.setCreatedBy(item.getCreatedBy());
                        current.setCreated(item.getCreated());
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
    public void Delete(User item) {

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                UserList list = (UserList) xStream.fromXML(content);

                for (int i = 0; i < list.getList().size(); i++) {

                    User current = list.get(i);
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
    public User GetByUsernameAndPassword(String username, String password) {

        User result = null;

        try {
            File file = new File(fileName);
            if (file.length() != 0) {

                String content = Files.readString(Paths.get(fileName));
                UserList list = (UserList) xStream.fromXML(content);

                for (int i = 0; i < list.getList().size(); i++) {

                    User current = list.get(i);
                    if (current.getUsername().equals(username) && current.getPassword().equals(password)) {
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
}
