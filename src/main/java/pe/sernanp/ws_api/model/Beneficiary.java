package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table (name = "t_beneficiario")
public class Beneficiary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_beneficiario_srl_id_seq", sequenceName="t_beneficiario_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_beneficiario", nullable=false, foreignKey = @ForeignKey(name="FK_beneficiario_tipo_beneficia"))
    private ListDetail beneficiaryType;

    @Column (name= "txt_nom", nullable = false, length = 250)
    private String name;

    @Column (name= "txt_num_documento", nullable = false, length = 12)
    private String documentNumber;

    @Column (name= "bol_flg_validado", nullable = false, columnDefinition = "boolean default true")
    private Boolean flagValid;

    @JsonIgnore
    @Column (name= "bol_flg_activo", nullable = false, columnDefinition = "boolean default true")
    private Boolean flagActive;
}
