package pe.sernanp.ws_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.sernanp.ws_api.repository.PayFormMPVRepository;
import pe.sernanp.ws_api.service.PayFormMPVService;

@Service
public class PayFormMPVServiceImpl implements PayFormMPVService {
    @Autowired
    PayFormMPVRepository _repository;
}
