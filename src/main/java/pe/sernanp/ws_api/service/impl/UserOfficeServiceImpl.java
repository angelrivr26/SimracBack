package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.dto.UserOfficeDTO;
import pe.sernanp.ws_api.dto.UserOfficeI;
import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.UserOffice;
import pe.sernanp.ws_api.model.UserOfficeComposite;
import pe.sernanp.ws_api.repository.UserOfficeRepository;
import pe.sernanp.ws_api.service.UserOfficeService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserOfficeServiceImpl implements UserOfficeService {
    @Autowired
    UserOfficeRepository _repository;

    @Override
    public ResponseEntity<UserOffice> findById(int userId, int rolId) {
        ResponseEntity<UserOffice> response = new ResponseEntity<>();
        try{
            List<UserOffice> item = this._repository.findByIdAndRolId(userId, rolId);
            if(item != null)
                response.setItems(item);
            else{
                response.setMessage("No se existe el registro");
                response.setSuccess(false);
            }
        }catch (Exception ex){
            response.setMessage("Ocurrio un error en el detalle del año");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public ResponseEntity<UserOfficeDTO> save(UserOfficeDTO item) {
        ResponseEntity<UserOfficeDTO> response = new ResponseEntity<>();
        try{
            for (int i = 0; i < item.getOfficeIds().size(); i++) {
                UserOffice item2 = new UserOffice();
                UserOfficeComposite userOffices = new UserOfficeComposite();
                userOffices.setId(item.getId());
                userOffices.setRolId(item.getRolId());
                userOffices.setOfficeId(item.getOfficeIds().get(i));
                item2.setOffices(userOffices);
                this._repository.save(item2);
            }
            response.setMessage("El registro se genero satisfactoriamente");
            response.setItem(item);
        }catch (Exception ex){
            response.setMessage("Ocurrio un error al guardar");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    @Transactional
    public ResponseEntity<UserOffice> delete(int userId, int rolId) {
        ResponseEntity<UserOffice> response = new ResponseEntity<>();
        try{
            this._repository.deleteById(userId, rolId);
            response.setMessage("El registro se elimino satisfactoriamente");
        }catch (Exception ex){
            response.setMessage("Ocurrio un error en el delete del usuario");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public ResponseEntity<UserOfficeI> detail(int userId, int rolId) {
        ResponseEntity<UserOfficeI> response = new ResponseEntity<>();
        try{
            List<UserOfficeI> item = this._repository.detail(userId, rolId);
            if(item != null)
                response.setItems(item);
            else{
                response.setMessage("No se existe el registro");
                response.setSuccess(false);
            }
        }catch (Exception ex){
            response.setMessage("Ocurrio un error en el detalle del año");
            response.setExtra(ex.getMessage());
            response.setSuccess(false);
        }
        return response;
    }
}
