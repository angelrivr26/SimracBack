package pe.sernanp.ws_api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pe.sernanp.ws_api.dto.ListDetailDTO;
import pe.sernanp.ws_api.entity.BeneficiaryEntity;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.entity.TokenEntity;
import pe.sernanp.ws_api.model.Beneficiary;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.repository.BeneficiaryRepository;
import pe.sernanp.ws_api.service.BeneficiaryService;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {
    @Autowired
    BeneficiaryRepository _repository;

    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryServiceImpl.class);

    @Value("${spring.jpa.pide.username}")
    private String username;

    @Value("${spring.jpa.pide.password}")
    private String password;
    @Value("${spring.jpa.pide.serviceAuth}")
    private String serviceAuth;

    @Value("${spring.jpa.pide.serviceDni}")
    private String serviceDni;

    @Value("${spring.jpa.pide.serviceRuc}")
    private String serviceRuc;

    public ResponseEntity<Beneficiary> findAll() throws Exception {
        ResponseEntity<Beneficiary> response = new ResponseEntity<Beneficiary>();
        try {
            List<Beneficiary> items = _repository.findAll();
            response.setItems(items);
        } catch (Exception ex) {
            logger.error("Ocurrio un error al listar", ex);
            response.setMessage("Ocurrio un error al listar");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    public ResponseEntity<Beneficiary> findByDocumentNumber(Beneficiary item) throws Exception {
        ResponseEntity<Beneficiary> response = new ResponseEntity<Beneficiary>();
        String documentNumber = item.getDocumentNumber();
        int documentLength = documentNumber.length();

        if (documentLength != 8 && documentLength != 11){
            response.setMessage("Numero de documento incorrecto, debe ser de 8 u 11 dígitos");
            response.setSuccess(false);
            return response;
        }

        try {
            Optional<Beneficiary> _item = _repository.findByDocumentNumber(documentNumber);
            Beneficiary itemNew = new Beneficiary();
            BeneficiaryEntity beneficiaryEntity;
            if (_item.isPresent())
                if (_item.get().getFlagValid()){
                    response.setItem(_item.get());
                    return response;
                }

            //Consume PIDE service
            String uri = (documentLength == 8 ? serviceDni : serviceRuc) + documentNumber;

            try {
                RestTemplate restTemplate = new RestTemplate();


                beneficiaryEntity = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(createHeaders()), BeneficiaryEntity.class).getBody();

                //Validate
                boolean validate = true;

                if (documentLength == 8) {
                    if (!beneficiaryEntity.getCoResultado().equals("0000"))
                        validate = false;
                }
                else {
                    if (beneficiaryEntity.getDdp_estado() == null)
                        validate = false;
                }

                if (!validate) {
                    response.setMessage("El beneficiario con N° documento " + documentNumber + " no existe.");
                    response.setSuccess(false);
                    response.setWarning(true);
                    return response;
                }

                itemNew.setFlagValid(true);
            } catch (Exception ex) {
                if (item.getName() == null || item.getName().length() == 0) {
                    response.setMessage("El servicio PIDE se encuentra temporalmente fuera de servicio, puede registrar un nombre temporal.");
                    response.setSuccess(false);
                    response.setWarning(true);
                    return response;
                }
                beneficiaryEntity = new BeneficiaryEntity();
                if (documentLength == 8)
                    beneficiaryEntity.setFullNombre(item.getName());
                else beneficiaryEntity.setDdp_nombre(item.getName());

                itemNew.setFlagValid(false);
            }

            String documentType = documentLength == 8 ? "DNI" : "RUC";
            ListDetail beneficiaryType = new ListDetail();
            ListDetailDTO temp = _repository.getBeneficiaryTypeId(documentType);
            beneficiaryType.setId(temp.getId());
            beneficiaryType.setName(temp.getName());

            if (_item.isPresent())
                itemNew.setId(_item.get().getId());
            itemNew.setDocumentNumber(documentNumber);
            itemNew.setName(documentLength == 8 ? beneficiaryEntity.getFullNombre().trim() : beneficiaryEntity.getDdp_nombre().trim());
            itemNew.setBeneficiaryType(beneficiaryType);
            itemNew.setFlagActive(true);
            itemNew = _repository.save(itemNew);

            response.setItem(itemNew);
        } catch (Exception ex) {
            response.setMessage("Ocurrio un error al buscar beneficiario.");
            response.setSuccess(false);
            response.setExtra(ex.getMessage());
        }
        return response;
    }

    HttpHeaders createHeaders(){
        return new HttpHeaders() {{
//            String auth = username + ":" + password;
//            byte[] encodedAuth = Base64.encodeBase64(
//                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Bearer " + getToken(); //new String( encodedAuth );
            set( "Authorization", authHeader);
        }};
    }

    public String getToken() {
        TokenEntity token;

        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String jsonData = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
            HttpEntity<String> request = new HttpEntity<>(jsonData,headers);
            String url = serviceAuth;
            token = restTemplate.postForObject(url, request, TokenEntity.class);
        } catch (Exception ex) {
            token = null;
        }
        return token.getToken();
    }
}
