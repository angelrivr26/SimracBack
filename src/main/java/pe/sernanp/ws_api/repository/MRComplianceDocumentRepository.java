package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.sernanp.ws_api.model.MRComplianceDocument;
import pe.sernanp.ws_api.model.SRVerifiedFactDocument;

import java.util.List;

public interface MRComplianceDocumentRepository extends JpaRepository<MRComplianceDocument,Integer> {

    List<MRComplianceDocument> findByMrComplianceId(int id);
}
