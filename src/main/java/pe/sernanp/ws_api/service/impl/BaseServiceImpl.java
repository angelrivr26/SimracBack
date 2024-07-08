package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import pe.sernanp.ws_api.service.DocumentService;

import java.util.Optional;

public class BaseServiceImpl {
    @Autowired
    DocumentService documentService;

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static String trimEnd(String str, String characters) {
        return str.replaceAll("[" + characters + "]+$", "");
    }

    public static String trimStart(String str, String characters) {
        return str.replaceAll("^[" + characters + "]+", "");
    }

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public byte[] getFile (boolean isAlfresco, String fileId) throws Exception {
        try {
            byte[] file = documentService.getFile(isAlfresco, fileId);
            return file;
        } catch (Exception ex) {
            return null;
        }
    }
}
