package pe.sernanp.ws_api.dto;

import lombok.Data;
import pe.sernanp.ws_api.model.BaseEntity;

import java.util.List;

@Data
public class UserOfficeDTO extends BaseEntity {

    private int id;

    private int rolId;

    private int officeId;

    private List<Integer> officeIds;
}
