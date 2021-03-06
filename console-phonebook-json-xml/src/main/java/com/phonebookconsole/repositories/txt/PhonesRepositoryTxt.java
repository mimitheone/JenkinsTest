package com.phonebookconsole.repositories.txt;

import com.phonebookconsole.exceptions.DataAccessException;
import com.phonebookconsole.interfaces.PhonesPersistable;
import com.phonebookconsole.entities.Phone;
import com.phonebookconsole.tools.ConfigurationManager;

import java.io.*;
import java.util.ArrayList;

public class PhonesRepositoryTxt implements PhonesPersistable {

    private String fileName;

    public PhonesRepositoryTxt() {

        this.fileName = "phones.txt";

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

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                Phone item = new Phone();
                item.setId(Integer.parseInt(value));

                item.setContactId(Integer.parseInt(bufferedReader.readLine()));
                item.setPhone(bufferedReader.readLine());

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
    public Phone GetById(int id) {

        Phone result = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                Phone item = new Phone();
                item.setId(Integer.parseInt(value));

                item.setContactId(Integer.parseInt(bufferedReader.readLine()));
                item.setPhone(bufferedReader.readLine());

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
    public ArrayList<Phone> GetAll() {

        ArrayList<Phone> result = new ArrayList<Phone>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                Phone item = new Phone();
                item.setId(Integer.parseInt(value));

                item.setContactId(Integer.parseInt(bufferedReader.readLine()));
                item.setPhone(bufferedReader.readLine());

                result.add(item);
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

        try (PrintWriter printWriter  = new PrintWriter(new FileWriter(fileName, true))) {

            printWriter.println(item.getId());

            printWriter.println(item.getContactId());
            printWriter.println(item.getPhone());
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public void Edit(Phone item) {

        String tempFileName = ConfigurationManager.TempFilePrefix() + fileName;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
             PrintWriter printWriter = new PrintWriter(new FileWriter(tempFileName, true))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                Phone tempEntity = new Phone();
                tempEntity.setId(Integer.parseInt(value));

                tempEntity.setContactId(Integer.parseInt(bufferedReader.readLine()));
                tempEntity.setPhone(bufferedReader.readLine());

                if (tempEntity.getId() != item.getId()) {

                    printWriter.println(tempEntity.getId());

                    printWriter.println(tempEntity.getContactId());
                    printWriter.println(tempEntity.getPhone());
                }
                else {

                    printWriter.println(item.getId());

                    printWriter.println(item.getContactId());
                    printWriter.println(item.getPhone());
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
    public void Delete(Phone item) {

        String tempFileName = ConfigurationManager.TempFilePrefix() + fileName;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
             PrintWriter printWriter = new PrintWriter(new FileWriter(tempFileName, true))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                Phone tempEntity = new Phone();
                tempEntity.setId(Integer.parseInt(value));
                tempEntity.setContactId(Integer.parseInt(bufferedReader.readLine()));
                tempEntity.setPhone(bufferedReader.readLine());

                if (tempEntity.getId() != item.getId()) {

                    printWriter.println(tempEntity.getId());
                    printWriter.println(tempEntity.getContactId());
                    printWriter.println(tempEntity.getPhone());
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
    public ArrayList<Phone> GetAll(int parentId) {

        ArrayList<Phone> result = new ArrayList<Phone>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                Phone phone = new Phone();
                phone.setId(Integer.parseInt(value));
                phone.setContactId(Integer.parseInt(bufferedReader.readLine()));
                phone.setPhone(bufferedReader.readLine());

                if (phone.getContactId() == parentId)
                    result.add(phone);
            }
        }
        catch(Exception ex) {
            throw new DataAccessException(ex);
        }

        return result;
    }
}
