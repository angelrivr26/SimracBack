package pe.sernanp.ws_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.sernanp.ws_api.model.MRComplianceDocument;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComplianceEntity {

    private int id;

    //private int monitoringRecord;

    private String source;

    private String odFiscalObligation;

    private boolean complianceSelected;

    private ListDetailEntity complianceType;

    private String comments;
    private ListDetailEntity stageType;
    private String activity;
    private String description;
    private List<MRComplianceDocument> documents;
}
