package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class BaseEntity {
    @JsonIgnore
    @Column(updatable = false, name="dte_fec_cre")
    @CreationTimestamp
    private Date fechaCreacion;

    @JsonIgnore
    @Column(updatable = true, name="dte_fec_modi")
    //@UpdateTimestamp
    private Date fechaModificacion;

//    @JsonIgnore
    @Column(updatable = false, name="var_usu_crea", length=100, columnDefinition = "varchar(100) default 'USER_DB'")
    private String usuarioCreacion = "USER_DB";

    @JsonIgnore
    @Column(name="var_usu_modi", length=100)
    private String usuarioModificacion;

    @JsonIgnore
    @Column(name="bol_flg_eliminado", updatable = true, nullable=false, columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Boolean getIsDeleted() { return isDeleted; }

    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
}
