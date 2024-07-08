package pe.sernanp.ws_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.MRRecommendationDocument;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationEntity {

    private int id;
    private ListDetailEntity compromise;
    private String recommendation;
    private LocalDate agreedDate;
    private DocumentEntity documentEvaluate;
    private String source;
    private String comment;
    private ListDetailEntity complianceType;
    private ListDetailEntity stageType;
    private List<MRRecommendationDocument> documents;
}
