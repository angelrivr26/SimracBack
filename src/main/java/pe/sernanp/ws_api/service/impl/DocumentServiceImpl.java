package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pe.sernanp.ws_api.dto.DocumentoDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.BaseEntity;
import pe.sernanp.ws_api.service.DocumentService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Value("${spring.jpa.document.alfresco.service}")
    private String alfrescoService;

    @Value("${spring.jpa.document.archivo_digital.service}")
    private String adService;

    private String UPLOAD_METHOD = "/upload/";

    private String GET_METHOD = "/getFile/";

    public DocumentoDTO saveFile (MultipartFile file, boolean isAlfresco, String folderId, String name) throws Exception {
        ResponseEntity response = new ResponseEntity<>();
        DocumentoDTO item = new DocumentoDTO();
        File tempFile = null;
        try {
            String uri = isAlfresco ? alfrescoService : adService;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            tempFile = convert(file, name);
            body.add("file", new FileSystemResource(tempFile));
            if (!isAlfresco)
                body.add("folderId", "2185734"); //reemplazar por folderId enviado
            //body.add("folderId", folderId);

            HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();

            response = restTemplate.postForEntity(uri + UPLOAD_METHOD, requestEntity, ResponseEntity.class).getBody();

            if (response.getSuccess()) {
                LinkedHashMap temp = (LinkedHashMap) response.getItem();
                item.setId(temp.get("id").toString());
                item.setName(temp.get("name").toString());
                item.setIsAlfresco(isAlfresco);
            }
            else {
                item.setSuccess(false);
                item.setMessagge(response.getMessage());
            }

        } catch (Exception ex){
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }
        finally {
            if (tempFile != null)
                tempFile.delete();
        }
        return item;
    }

//    public DocumentoDTO saveFile (MultipartFile file, boolean isAlfresco, String folderId) throws Exception {
//        ResponseEntity response = new ResponseEntity<>();
//        DocumentoDTO item = new DocumentoDTO();
//        File tempFile = null;
//        try {
//            String uri = isAlfresco ? alfrescoService : adService;
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            tempFile = convert(file);
//            body.add("file", new FileSystemResource(tempFile));
//            if (!isAlfresco)
//                body.add("folderId", folderId);
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);
//            RestTemplate restTemplate = new RestTemplate();
//
//            response = restTemplate.postForEntity(uri + DOWNLOAD_METHOD, requestEntity, ResponseEntity.class).getBody();
//
//            if (response.getSuccess()) {
//                LinkedHashMap temp = (LinkedHashMap) response.getItem();
//                item.setId(temp.get("id").toString());
//                item.setName(temp.get("name").toString());
//                item.setIsAlfresco(isAlfresco);
//            }
//            else {
//                item.setSuccess(false);
//                item.setMessagge(response.getMessage());
//            }
//
//        } catch (Exception ex){
//            response.setMessage(ex.getMessage());
//            response.setSuccess(false);
//        }
//        finally {
//            if (tempFile != null)
//                tempFile.delete();
//        }
//        return item;
//    }

//    public static File convert(MultipartFile multipart) throws IllegalStateException, IOException {
//        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+multipart.getOriginalFilename());
//        multipart.transferTo(convFile);
//        return convFile;
//    }

    public static File convert(MultipartFile multipart, String name) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+name);
        multipart.transferTo(convFile);
        return convFile;
    }

    public byte[] getFile(boolean isAlfresco, String fileId) throws Exception {
        ResponseEntity response = new ResponseEntity<>();
        try {
            String uri = (isAlfresco ? alfrescoService : adService) + GET_METHOD + fileId;

            RestTemplate restTemplate = new RestTemplate();
            byte[] file = restTemplate.getForObject(uri, byte[].class);

            return file;
        } catch (Exception ex) {
            return null;
        }
    }
}
