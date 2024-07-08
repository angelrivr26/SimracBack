package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.dto.UserOfficeDTO;
import pe.sernanp.ws_api.dto.UserOfficeI;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.UserOffice;

public interface UserOfficeService {
    ResponseEntity<UserOffice> findById(int userId, int rolId);

    ResponseEntity<UserOfficeDTO> save(UserOfficeDTO item);

    ResponseEntity<UserOffice> delete(int userId, int rolId);

    ResponseEntity<UserOfficeI> detail(int userId, int rolId);
}
