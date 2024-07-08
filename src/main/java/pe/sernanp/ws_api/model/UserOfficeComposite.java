package pe.sernanp.ws_api.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class UserOfficeComposite implements Serializable {

    @Column(name = "int_usuario", nullable = false)
    private int id;

    @Column(name = "int_rol")
    private int rolId;

    @Column (name= "int_anp", length=100)
    private int officeId;

}
