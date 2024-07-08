package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.MonitoringRecord;

import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public interface DocumentationRequestDTO {
     int getId();
     String getDescriptionDocument();
     String getComment();
     String getRecommendation();
     default ListDetailEntity getCompromise() {
          return new ListDetailEntity(getCompromiseId(), getCompromiseName());
     };

     LocalDate getAgreedDate();
     default ListDetailEntity getState(){
         return new ListDetailEntity(getStateId(),getStateName());
     }
     default ListDetailEntity getMonetary(){
          return new ListDetailEntity(getMonetaryId(),getMonetaryName());
     }
     default ListDetailEntity getResponsable(){
          return new ListDetailEntity(getResponsableId(),getResponsableName());
     }
     default DocumentEntity getDocument() {
          return new DocumentEntity(getDocumentName(), getDocumentCode(), getDocumentSize() == null ? 0 : getDocumentSize());
     };
     default ListDetailEntity getStageType() {
          return new ListDetailEntity(getStageTypeId(), getStageTypeName());
     };
     @JsonIgnore
     String getDocumentName();
     @JsonIgnore
     String getDocumentCode();
     @JsonIgnore
     Long getDocumentSize();
     @JsonIgnore
     String getCompromiseName();
     @JsonIgnore
     Integer getCompromiseId();
     @JsonIgnore
     String getStateName();
     @JsonIgnore
     Integer getStateId();
     @JsonIgnore
     String getResponsableName();
     @JsonIgnore
     Integer getResponsableId();
     @JsonIgnore
     String getMonetaryName();
     @JsonIgnore
     Integer getMonetaryId();
     @JsonIgnore
     Integer getStageTypeId();
     @JsonIgnore
     String getStageTypeName();
}
