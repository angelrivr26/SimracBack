package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "t_modalidad_anp_config", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_modalidad_anp_config", columnNames = {"int_id_configanp", "int_id_modalidad", "int_id_tipo_uso"})})
public class ModalityAnpConfig extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_modalidad_anp_config_srl_id_seq", sequenceName="t_modalidad_anp_config_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_configanp", nullable=false, foreignKey = @ForeignKey(name="FK_modalidadanp_configanp"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnpConfig anpConfig;

    @ManyToOne
    @JoinColumn(name = "int_id_modalidad", nullable=false, foreignKey = @ForeignKey(name="FK_modalidad_anp_modalidad"))
    private Modality modality;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_uso", nullable=false, foreignKey = @ForeignKey(name="FK_modalidadanpconfig_tipo_uso"))
    private ListDetail useType;

    @JsonIgnore
    @Column(name= "dte_fec_inicio", nullable=false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate = LocalDate.now();

    @JsonIgnore
    @Column (name= "dte_fec_fin", columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @JsonIgnore
    @Column (name= "bol_flg_activo", nullable=false, columnDefinition = "boolean default true")
    private Boolean flagActive = true;
}
