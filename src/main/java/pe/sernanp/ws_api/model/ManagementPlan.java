package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "t_plan_manejo")
public class ManagementPlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_plan_manejo_srl_id_seq", sequenceName="t_plan_manejo_srl_id_seq", allocationSize=1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "int_id_tipo_instrumento", foreignKey = @ForeignKey(name="FK_plan_manejo_tipo_instrument"))
    private ListDetail instrumentType;

    @Column (name= "txt_cod_anp", length = 50)
    private String anpCode;

    @Column (name= "txt_nom_anp", length = 500)
    private String anpName;

    @Column (name= "txt_nombre", length = 150)
    private String name;

    @Column (name= "dte_fec_inicio", columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @Column (name= "dte_fec_fin", columnDefinition = "DATE")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @Column (name= "bol_flg_vigente", columnDefinition = "boolean default true")
    private Boolean flagValid;

    @Column (name= "txt_ruta_digital", length = 1000)
    private String digitalRoute;

    @Column (name= "txt_id_ruta_digital", length = 1000)
    private String digitalRouteId;

    @Column (name= "txt_resolucion", length = 150)
    private String resolution;

    @Column (name= "txt_resolucion_ruta", length = 1000)
    private String resolutionRouteId;

    @Column (name= "txt_pm_archivo", length = 150)
    private String managementPlanFile;

    @Column (name= "txt_pm_archivo_ruta", length = 1000)
    private String managementPlanFileId;

    @Column (name= "txt_pm_informe_compatibilidad", length = 150)
    private String compatibilityReportFile;

    @Column (name= "txt_pm_informe_compatibilidad_ruta", length = 1000)
    private String compatibilityReportFileId;

    @Column (name= "bol_flg_borrador", nullable=false, columnDefinition = "boolean default false")
    private Boolean flagDraft;

    @Transient
    private int resourceTypeId;

    @Transient
    private int resourceId;

}
