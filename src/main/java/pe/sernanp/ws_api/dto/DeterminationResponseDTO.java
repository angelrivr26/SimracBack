package pe.sernanp.ws_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeterminationResponseDTO {

    private int id;

    private List<DeterminationDTO> items;
}
