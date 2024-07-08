package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.sernanp.ws_api.model.SRVerifiedFactDocument;

import java.util.List;

public interface VerifiedFactDocumentRepository extends JpaRepository<SRVerifiedFactDocument,Integer> {

    List<SRVerifiedFactDocument> findByVerifiedFactId(int id);
}
