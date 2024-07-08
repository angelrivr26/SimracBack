package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table (name = "t_modalidad", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_modalidad_cod_modalidad", columnNames = {"var_cod_modalidad"}),
        @UniqueConstraint(name = "UQ_modalidad_siglas", columnNames = {"var_siglas"})})
public class Modality extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_modalidad_srl_id_seq", sequenceName="t_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo", nullable=false, foreignKey = @ForeignKey(name="FK_modalidad_tipo"))
    private ListDetail type;

    @ManyToOne
    @JoinColumn(name = "int_id_estado", foreignKey = @ForeignKey(name="FK_modalidad_estado"))
    private ListDetail state;

    @Column (name= "var_cod_modalidad", unique = true, nullable=false, length = 12)
    private String code;

    @Column (name= "var_siglas", unique = true, length = 10)//, nullable=false)
    private String acronym;

    @Column (name= "txt_nom_largo", nullable=false, length = 500)
    private String description;

    @Column (name= "txt_base_legal_crea", length = 150)
    private String sustentationDocumentName;

    @Column (name= "txt_base_legal_ruta", length = 1000)
    private String sustentationDocumentId;

    @Column (name= "txt_nom_corto", length = 150)
    private String shortName;

    @Column (name= "txt_nom_corto_titulo", nullable=false, length = 150)
    private String titleEnables;

    @Column (name= "dte_fec_inicio", columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate validFrom;

    @Column (name= "dte_fec_fin", columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate validUntil;

    @Column (name= "bol_flg_activo", nullable=false, columnDefinition = "boolean default true")
    private Boolean flagValidity = true;

    @Column (name= "bol_flg_pda", nullable=false, columnDefinition = "boolean default false")
    private Boolean flagPDA = false;

    @Column (name= "bol_flg_pdv", nullable=false, columnDefinition = "boolean default false")
    private Boolean flagPDV = false;

    @Column (name= "bol_flg_tupa", nullable=false, columnDefinition = "boolean default false")
    private Boolean flagTupa = false;

    @Column (name= "txt_nom_tupa", length = 150)
    private String tupaDescription;

    @Column (name= "bol_flg_borrador", nullable=false, columnDefinition = "boolean default true")
    private Boolean flagDraft = true;

    @Column (name= "bol_flg_no_monetario", nullable=false, columnDefinition = "boolean default false")
    private Boolean isNoMonetary = false;

    //region Internal attribute

//    @Transient
//    @JsonInclude
//    private int requirementCount;

    //endregion Internal attribute
}
