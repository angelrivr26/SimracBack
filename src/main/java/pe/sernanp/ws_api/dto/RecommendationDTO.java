package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public interface RecommendationDTO {
     Integer getId();
     String getSource();

     String getRecommendation();
     LocalDate getAgreedDate();
     String getComment();
     default ListDetailEntity getCompromise() {
          return new ListDetailEntity(getCompromiseId(), getCompromiseName());
     };
     default ListDetailEntity getComplianceType() {
          return new ListDetailEntity(getComplianceTypeId(), getComplianceTypeName());
     };
     default DocumentEntity getDocumentEvaluate() {
          return new DocumentEntity(getEvaluateDocumentName(), getEvaluateDocumentCode(), getEvaluateDocumentSize() == null ? 0 : getEvaluateDocumentSize());
     };
     default ListDetailEntity getStageType() {
          return new ListDetailEntity(getStageTypeId(), getStageTypeName());
     };
     @JsonIgnore
     String getEvaluateDocumentName();
     @JsonIgnore
     String getEvaluateDocumentCode();
     @JsonIgnore
     Long getEvaluateDocumentSize();
     @JsonIgnore
     Integer getComplianceTypeId();
     @JsonIgnore
     String getComplianceTypeName();
     @JsonIgnore
     String getCompromiseName();
     @JsonIgnore
     Integer getCompromiseId();
     @JsonIgnore
     Integer getStageTypeId();
     @JsonIgnore
     String getStageTypeName();
}
