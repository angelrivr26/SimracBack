package pe.sernanp.ws_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.sernanp.ws_api.model.ListDetail;
import pe.sernanp.ws_api.model.ODFiscalObligation;
import pe.sernanp.ws_api.model.SupervisionRecord;
import pe.sernanp.ws_api.model.SRVerifiedFactDocument;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifiedFactDTO {

    private int id;

    private SupervisionRecord supervisionRecord;

    private ODFiscalObligation odFiscalObligation;

    private ListDetail breach;

    private String voluntaryCorrection;

    private String administrativeMeasure;

    private String description;

    private List<SRVerifiedFactDocument> documents;
}
