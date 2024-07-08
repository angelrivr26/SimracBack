package pe.sernanp.ws_api.service;

import pe.sernanp.ws_api.entity.ResponseEntity;
import pe.sernanp.ws_api.model.MRCompetitor;

import java.io.ByteArrayInputStream;

public interface MRCompetitorService {

    ResponseEntity<MRCompetitor> detail(int id);

    ResponseEntity<MRCompetitor> delete(int id);

    ResponseEntity<MRCompetitor> save(MRCompetitor item);

    ResponseEntity<MRCompetitor> update(MRCompetitor item);

    ResponseEntity<MRCompetitor> findByMonitoringRecordId(int id);
    ByteArrayInputStream export(int id) throws Exception;
}
