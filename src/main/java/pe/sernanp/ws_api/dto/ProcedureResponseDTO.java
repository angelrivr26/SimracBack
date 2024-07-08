package pe.sernanp.ws_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pe.sernanp.ws_api.model.Beneficiary;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.Modality;
import pe.sernanp.ws_api.model.OD;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public interface ProcedureResponseDTO {
     Integer getId();

     default Modality getModality(){
          Modality item = new Modality();
          item.setId(this.getModalityId());
          item.setShortName(this.getModalityName());
          item.setType(new ListDetail());
          item.getType().setId(this.getModalityTypeId());
          item.getType().setName(this.getModalityTypeName());
          return item;
     };

     default OD getOd() {
          OD item = new OD();
          Beneficiary beneficiary = new Beneficiary();
          ListDetail type = new ListDetail();
          ListDetail state = new ListDetail();

          type.setName(this.getOdTypeName());
          state.setName(this.getOdStateName());
          beneficiary.setName(this.getBeneficiaryName());
          item.setId(this.getOdId());
          item.setCode(this.getOdCode());

          return item;
     };

     Integer getProcedure();

     String getAnps();

     String getCodeAnps();

     String getResponsible();

     String getId_tipo_tramite();

     String getId_tipo_doc();

     String getVar_num_doc();

     Integer getInt_num_folios();

     String getVar_persona_firma_doc();

     String getVar_remitente_doc();

     String getId_oficina_recepciona();

     String getVar_persona_recepciona_doc();

     String getVar_correo_noti();

     String getVar_asunto();

     String getVar_uuid();

     String getId_anp_cod();

     String getTcodi();

     String getSrl_ticket();

     String getEstado_ticket();

     String getDes_est_ticket();

     String getId_medio();

     String getVar_declaracion();

     String getAnp_codigos();

     String getInd_noti_conf();

     Boolean getBol_tipo_otorgamiento();

     Integer getSrl_id_usuario();

     Integer getSrl_id_usuario_registra();

     String getVar_cut();

     LocalDate getDateAdmission();

     default LocalDate getDateEndAdmission() {
          return this.getDateAdmission().plusDays(this.getDaysAdmission());
     }

     LocalDate getDateDerivation();

     default LocalDate getDateLimit() {
          return this.getDateAdmission().plusDays(30);
     }

     default ListDetailEntity getTypeEvaluation() {
          return new ListDetailEntity(getTypeEvaluationId(), getTypeEvaluationName());
     };

     default ListDetailEntity getState() {
          return new ListDetailEntity(getStateId(), getStateName());
     };

     default int getSymbolicState() {
          int difference = ((int) ChronoUnit.DAYS.between(this.getDateAdmission(), this.getDateEndAdmission()));
          return difference > 10 ? 3 : (difference > 5 ? 2 : 1);
     }

     @JsonIgnore
     Integer getStateId();
     @JsonIgnore
     String getStateName();

     @JsonIgnore
     Integer getTypeEvaluationId();
     @JsonIgnore
     String getTypeEvaluationName();

     @JsonIgnore
     String getBeneficiaryName();
     @JsonIgnore
     String getOdTypeName();
     @JsonIgnore
     String getOdStateName();

     @JsonIgnore
     Integer getOdId();
     @JsonIgnore
     String getOdCode();

     @JsonIgnore
     String getModalityName();

     @JsonIgnore
     String getModalityTypeName();

     @JsonIgnore
     Integer getModalityId();

     @JsonIgnore
     Integer getModalityTypeId();

     @JsonIgnore
     Integer getDaysAdmission();
}