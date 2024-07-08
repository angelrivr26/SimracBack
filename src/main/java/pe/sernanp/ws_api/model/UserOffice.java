package pe.sernanp.ws_api.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "t_usuario_oficina")
public class UserOffice extends BaseEntity {

    @EmbeddedId
    private UserOfficeComposite offices;

}

