package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pe.sernanp.ws_api.model.MRComplianceDocument;
import java.util.List;

public interface ComplianceDTO {
     Integer getId();

     Boolean getComplianceSelected();

     String getComment();
     String getActivity();
     String getDescription();
     default ListDetailEntity getComplianteType() {
          return new ListDetailEntity(getComplianceTypeId(), getComplianceTypeName());
     };
     default ListDetailEntity getStageType() {
          return new ListDetailEntity(getStageTypeId(), getStageTypeName());
     };
     String getSource();

     String getFiscalObligation();

     @JsonIgnore
     Integer getComplianceTypeId();
     @JsonIgnore
     String getComplianceTypeName();
     @JsonIgnore
     Integer getStageTypeId();
     @JsonIgnore
     String getStageTypeName();


}
