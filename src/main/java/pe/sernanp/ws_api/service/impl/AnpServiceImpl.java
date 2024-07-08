package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.sernanp.ws_api.dto.AnpDTO;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.Anp;
import pe.sernanp.ws_api.repository.AnpConfigRepository;
import pe.sernanp.ws_api.repository.AnpRepository;
import pe.sernanp.ws_api.service.AnpService;

import java.util.List;


@Service
public class AnpServiceImpl implements AnpService {
    @Autowired
    AnpConfigRepository _repositoryAnpConfig;

    @Autowired
    AnpRepository _repository;

    @Value("${spring.jpa.anp.service}")
    private String anpService;

//    public ResponseEntity<AnpDTO> findAll() throws Exception {
//        Respon<seEntity<AnpDTO> response = new ResponseEntity<AnpDTO>();
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<String> request = new HttpEntity<String>("_", headers);
//
//            RestTemplate restTemplate = new RestTemplate();
//            response = restTemplate.postForObject(anpService, request, ResponseEntity.class);
//        } catch (Exception ex) {
//            response.setMessage("Ocurrio un error al listar");
//            response.setSuccess(false);
//            response.setExtra(ex.getMessage());
//        }
//        return response;
//    }

    public ResponseEntity<AnpDTO> findAll() throws Exception {
        ResponseEntity<AnpDTO> response = new ResponseEntity<AnpDTO>();
        try {
            List<AnpDTO> items = _repository.findAll2();
            response.setItems(items);

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<AnpDTO> listNotRelatedAnpConfig2() throws Exception {
        ResponseEntity<AnpDTO> response = new ResponseEntity<AnpDTO>();
        try {
            List<AnpDTO> items = _repository.listRelatedAnpConfig();
            response.setItems(items);

//            String anpCodes = _repositoryAnpConfig.listRelatedAnpConfigCodes();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<String> request = new HttpEntity<String>(anpCodes, headers);
//
//            RestTemplate restTemplate = new RestTemplate();
//            response = restTemplate.postForObject(anpService, request, ResponseEntity.class);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<AnpDTO> listByModality(int modalityId) throws Exception {
        ResponseEntity<AnpDTO> response = new ResponseEntity<AnpDTO>();
        try {
            List<AnpDTO> items = _repository.listByModality(modalityId);
            response.setItems(items);

        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }
}
