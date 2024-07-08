package pe.sernanp.ws_api.service;


import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.SRCompetitor;

import java.io.ByteArrayInputStream;

public interface SRCompetitorService {

    ResponseEntity<SRCompetitor> detail(int id);

    ResponseEntity<SRCompetitor> delete(int id);

    ResponseEntity<SRCompetitor> save(SRCompetitor item);

    ResponseEntity<SRCompetitor> update(SRCompetitor item);

    ResponseEntity<SRCompetitor> findBySupervisionRecordId(int id);
    ByteArrayInputStream export(int id) throws Exception;
}
