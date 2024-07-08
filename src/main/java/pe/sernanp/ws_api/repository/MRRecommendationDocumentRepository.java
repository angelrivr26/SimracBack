package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.MRRecommendation;
import pe.sernanp.ws_api.model.MRRecommendationDocument;

import java.util.List;

@Repository
@Transactional
public interface MRRecommendationDocumentRepository extends JpaRepository<MRRecommendationDocument, Integer> {

    List<MRRecommendationDocument> findByMrRecommendationId(Integer id);
}
