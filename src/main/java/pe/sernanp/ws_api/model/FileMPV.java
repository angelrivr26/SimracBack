package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_archivo_mpv")
public class FileMPV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_archivo_mpv_srl_id_seq", sequenceName="t_archivo_mpv_srl_id_seq", allocationSize=1)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "int_id_tramite", foreignKey = @ForeignKey(name="fk_archivo_mpv_tramite"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProcedureMPV procedure;

    @Column(name = "srl_id_archivo", columnDefinition = "numeric (8,0)")
    private Integer srl_id_archivo;

    @Column(name = "fileName", length = 255)
    private String fileName;

    @Column(name = "var_fileNameSave", length = 255)
    private String fileNameSave;

    @Column(name = "var_contentType", length = 255)
    private String contentType;

    @Column(name = "var_tamano", length = 255)
    private String tamano;

    @Column(name = "var_url", length = 255)
    private String url;

    @Column(name = "bol_flg_success", columnDefinition = "boolean")
    private Boolean success;

    @Column(name = "var_message", length = 255)
    private String message;

    @Column(name = "bol_flg_fileNew", columnDefinition = "boolean")
    private Boolean fileNew;

    @Column(name = "int_estado", columnDefinition = "numeric (8,0)")
    private Integer int_estado;

    @Column(name = "id_replace", columnDefinition = "numeric (8,0)")
    private Integer id_replace;

    @Column(name = "int_replace", columnDefinition = "numeric (8,0)")
    private Integer int_replace;

    @Column(name = "var_observacion_file", length = 255)
    private String var_observacion_file;

    @Column(name = "var_ind_ruta_tipo_file", length = 255)
    private String ind_ruta_tipo_file;
}
