package uk.co.afe.persistance.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import uk.co.afe.model.ErrorCode;
import uk.co.afe.model.exceptions.BaseRuntimeException;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.UUID;

/**
 * Repository of files
 *
 * @author Sergey Teryoshin
 * 28.03.2018 22:37
 */
@Repository
public class DaoFileManager {

    private static final String DEFAULT_ROOT_DIR = "./storage";
    @Value("${files.root}")
    private String root;

    @PostConstruct
    private void init() {
        if (root == null) {
            root = DEFAULT_ROOT_DIR;
        }
        File file = new File(root);
        if (!file.exists()) {
            file.mkdirs();
        }
        root = file.getAbsolutePath();
    }

    /**
     * Save file and get path to file
     */
    public String save(byte[] bytes) {
        String newName = getUniqueName();
        File file = getFile(newName);
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(bytes);
            return newName;
        } catch (IOException e) {
            throw new BaseRuntimeException(ErrorCode.UNKNOWN_ERROR, "Error while save file. Cause: " + e, e);
        }
    }

    /**
     * Get file by path
     */
    public byte[] get(String path) {
        File file = getFile(path);
        if (!file.exists()) {
            return null;
        }
        try (FileInputStream is = new FileInputStream(file)) {
            int available = is.available();
            byte[] body = new byte[available];
            is.read(body);
            return body;
        } catch (IOException e) {
            throw new BaseRuntimeException(ErrorCode.UNKNOWN_ERROR, "Error while reading file. Cause: " + e, e);
        }
    }

    public void remove(String name) {
        File file = getFile(name);
        if (file.exists()) {
            file.delete();
        }
    }

    private String getUniqueName() {
        return UUID.randomUUID().toString();
    }

    private File getFile(String name) {
        return new File(root + "/" + name);
    }
}
