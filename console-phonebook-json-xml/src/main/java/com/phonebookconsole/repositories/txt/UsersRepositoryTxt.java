package com.phonebookconsole.repositories.txt;

import com.phonebookconsole.exceptions.DataAccessException;
import com.phonebookconsole.interfaces.UsersPersistable;
import com.phonebookconsole.entities.User;
import com.phonebookconsole.tools.ConfigurationManager;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class UsersRepositoryTxt implements UsersPersistable {

    private String fileName;

    public UsersRepositoryTxt() {

        this.fileName = "users.txt";

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();

                User user = new User();
                user.setUsername("admin");
                user.setPassword("adminpass");
                user.setFirstName("Administrator");
                user.setLastName("Administrator");
                user.setIsAdmin(true);
                user.setCreated(new Date());
                user.setCreatedBy(1);

                Add(user);
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    private int GetNextId() {

        int nextId = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                User item = new User();
                item.setId(Integer.parseInt(value));

                item.setUsername(bufferedReader.readLine());
                item.setPassword(bufferedReader.readLine());
                item.setFirstName(bufferedReader.readLine());
                item.setLastName(bufferedReader.readLine());
                item.setIsAdmin(Boolean.parseBoolean(bufferedReader.readLine()));

                SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                item.setCreated(parser.parse(bufferedReader.readLine()));

                item.setCreatedBy(Integer.parseInt(bufferedReader.readLine()));

                if (nextId < item.getId())
                    nextId = item.getId();
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

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                User item = new User();
                item.setId(Integer.parseInt(value));

                item.setUsername(bufferedReader.readLine());
                item.setPassword(bufferedReader.readLine());
                item.setFirstName(bufferedReader.readLine());
                item.setLastName(bufferedReader.readLine());
                item.setIsAdmin(Boolean.parseBoolean(bufferedReader.readLine()));

                SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                item.setCreated(parser.parse(bufferedReader.readLine()));

                item.setCreatedBy(Integer.parseInt(bufferedReader.readLine()));

                if (item.getId() == id) {
                    result = item;
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

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                User item = new User();
                item.setId(Integer.parseInt(value));

                item.setUsername(bufferedReader.readLine());
                item.setPassword(bufferedReader.readLine());
                item.setFirstName(bufferedReader.readLine());
                item.setLastName(bufferedReader.readLine());
                item.setIsAdmin(Boolean.parseBoolean(bufferedReader.readLine()));

                SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                item.setCreated(parser.parse(bufferedReader.readLine()));

                item.setCreatedBy(Integer.parseInt(bufferedReader.readLine()));

                result.add(item);
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

        try (PrintWriter printWriter  = new PrintWriter(new FileWriter(fileName, true))) {

            printWriter.println(item.getId());

            printWriter.println(item.getUsername());
            printWriter.println(item.getPassword());
            printWriter.println(item.getFirstName());
            printWriter.println(item.getLastName());
            printWriter.println(item.getIsAdmin());

            Date date = item.getCreated();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            printWriter.println(dateFormat.format(date));
            printWriter.println(item.getCreatedBy());
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void Edit(User item) {

        String tempFileName = ConfigurationManager.TempFilePrefix() + fileName;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
             PrintWriter printWriter = new PrintWriter(new FileWriter(tempFileName, true))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                User tempEntity = new User();
                tempEntity.setId(Integer.parseInt(value));

                tempEntity.setUsername(bufferedReader.readLine());
                tempEntity.setPassword(bufferedReader.readLine());
                tempEntity.setFirstName(bufferedReader.readLine());
                tempEntity.setLastName(bufferedReader.readLine());
                tempEntity.setIsAdmin(Boolean.parseBoolean(bufferedReader.readLine()));

                SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                tempEntity.setCreated(parser.parse(bufferedReader.readLine()));

                tempEntity.setCreatedBy(Integer.parseInt(bufferedReader.readLine()));

                if (tempEntity.getId() != item.getId()) {

                    printWriter.println(tempEntity.getId());

                    printWriter.println(tempEntity.getUsername());
                    printWriter.println(tempEntity.getPassword());
                    printWriter.println(tempEntity.getFirstName());
                    printWriter.println(tempEntity.getLastName());
                    printWriter.println(tempEntity.getIsAdmin());

                    Date date = tempEntity.getCreated();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                    printWriter.println(dateFormat.format(date));
                    printWriter.println(tempEntity.getCreatedBy());
                }
                else {

                    printWriter.println(item.getId());

                    printWriter.println(item.getUsername());
                    printWriter.println(item.getPassword());
                    printWriter.println(item.getFirstName());
                    printWriter.println(item.getLastName());
                    printWriter.println(item.getIsAdmin());

                    Date date = item.getCreated();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                    printWriter.println(dateFormat.format(date));
                    printWriter.println(item.getCreatedBy());
                }
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }

        File original = new File(fileName);
        File tempFile = new File(tempFileName);

        original.delete();
        tempFile.renameTo(original);
        tempFile.delete();
    }

    @Override
    public void Delete(User item) {

        String tempFileName = ConfigurationManager.TempFilePrefix() + fileName;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
             PrintWriter printWriter = new PrintWriter(new FileWriter(tempFileName, true))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                User tempEntity = new User();
                tempEntity.setId(Integer.parseInt(value));

                tempEntity.setUsername(bufferedReader.readLine());
                tempEntity.setPassword(bufferedReader.readLine());
                tempEntity.setFirstName(bufferedReader.readLine());
                tempEntity.setLastName(bufferedReader.readLine());
                tempEntity.setIsAdmin(Boolean.parseBoolean(bufferedReader.readLine()));

                SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                tempEntity.setCreated(parser.parse(bufferedReader.readLine()));

                tempEntity.setCreatedBy(Integer.parseInt(bufferedReader.readLine()));

                if (tempEntity.getId() != item.getId()) {

                    printWriter.println(tempEntity.getId());

                    printWriter.println(tempEntity.getUsername());
                    printWriter.println(tempEntity.getPassword());
                    printWriter.println(tempEntity.getFirstName());
                    printWriter.println(tempEntity.getLastName());
                    printWriter.println(tempEntity.getIsAdmin());

                    Date date = tempEntity.getCreated();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                    printWriter.println(dateFormat.format(date));
                    printWriter.println(tempEntity.getCreatedBy());
                }
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }

        File original = new File(fileName);
        File tempFile = new File(tempFileName);

        original.delete();
        tempFile.renameTo(original);
        tempFile.delete();
    }

    @Override
    public User GetByUsernameAndPassword(String username, String password) {

        User result = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                User user = new User();
                user.setId(Integer.parseInt(value));
                user.setUsername(bufferedReader.readLine());
                user.setPassword(bufferedReader.readLine());
                user.setFirstName(bufferedReader.readLine());
                user.setLastName(bufferedReader.readLine());
                user.setIsAdmin(Boolean.parseBoolean(bufferedReader.readLine()));

                SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                user.setCreated(parser.parse(bufferedReader.readLine()));

                user.setCreatedBy(Integer.parseInt(bufferedReader.readLine()));

                if (user.getUsername().compareTo(username) == 0 && user.getPassword().compareTo(password) == 0) {
                    result = user;
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
