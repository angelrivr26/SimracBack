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
@Table (name = "t_od")
public class OD extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
	@SequenceGenerator(name = "t_od_srl_id_seq", sequenceName="t_od_srl_id_seq", allocationSize=1)
	private int id;

	@ManyToOne
	@JoinColumn(name = "int_id_tipo", foreignKey = @ForeignKey(name="FK_od_tipo"))
	private ListDetail type;

	@ManyToOne
	@JoinColumn(name = "int_id_modalidad", foreignKey = @ForeignKey(name="FK_od_modalidad"))
	private Modality modality;

	@ManyToOne
	@JoinColumn(name = "int_id_estado", foreignKey = @ForeignKey(name="FK_od_estado"))
	private ListDetail state;

	@ManyToOne
	@JoinColumn(name = "int_id_tipo_resolucion", foreignKey = @ForeignKey(name="FK_od_tipo"))
	private ListDetail resolutionType;

	@ManyToOne
	@JoinColumn(name = "int_id_beneficiario", foreignKey = @ForeignKey(name="FK_od_beneficiario"))
	private Beneficiary beneficiary;

	@ManyToOne
	@JoinColumn(name = "int_id_plan_sitio", foreignKey = @ForeignKey(name="fk_od_plan_sitio"))
	private SitePlan sitePlan;

	@ManyToOne
	@JoinColumn(name = "int_id_plan_manejo", foreignKey = @ForeignKey(name="fk_od_plan_manejo"))
	private ManagementPlan managementPlan;

	@Column (name= "var_cod", unique=true, nullable=false, length = 200)
	private String code;

	@Column (name= "var_ids_anp_config", length = 100)
	private String anpConfigIds;

	@Column (name= "var_ids_sector", length = 250)
	private String sectorIds;

	@Column (name= "var_ids_poligono", length = 250)
	private String polygonIds;

	@Column (name= "var_ids_recursos_anp", length = 100)
	private String resourceAnpConfigIds;

	@Column (name= "txt_descripcion", length = 150)
	private String description;

	@Column (name= "dte_fec_inicio", columnDefinition = "DATE")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate startDate;

	@Column (name= "dte_fec_fin", columnDefinition = "DATE")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate endDate;

	@Column (name= "txt_num_titulo", length = 150)
	private String titleNumber;

	@Column (name= "txt_doc_nom_titulo", length = 150)
	private String titleDocumentName;

	@Column (name= "txt_num_titulo_ruta", length = 1000)
	private String titleRouteId;

	@Column (name= "dte_fec_inicio_titulo", columnDefinition = "DATE")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate titleStartDate;

	@Column (name= "dte_fec_fin_titulo", columnDefinition = "DATE")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate titleEndDate;

	@Column (name= "txt_duration", length = 250)
	private String duration;

	@Column (name= "bol_flg_firmado", columnDefinition = "boolean default false")
	private Boolean flagSignature;

	@Column (name= "txt_num_resolucion", length = 255)
	private String resolutionNumber;

	@Column (name= "txt_doc_resolucion", length = 255)
	private String resolutionDocumentName;

	@Column (name= "txt_num_resolucion_ruta", length = 1000)
	private String resolutionRouteId;

	@Column (name= "txt_ruta_digital", length = 1000)
	private String digitalRoute;

	@Column (name= "txt_id_ruta_digital", length = 1000)
	private String digitalRouteId;

	@Column (name= "dte_fec_firma", columnDefinition = "DATE")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate signatureDate;//Fecha emisión

	@Column (name= "bol_flg_borrador", nullable=false, columnDefinition = "boolean default false")
	private Boolean flagDraft;

	@Column (name= "txt_nom_anps", columnDefinition = "TEXT")
	private String anpNames;

	@Column (name= "txt_nom_recursos", columnDefinition = "TEXT")
	private String resourceNames;

	@Column (name= "bol_flg_plan", columnDefinition = "boolean default false")
	private Boolean flagPlan;

	@Column(name = "int_num_beneficiario_hombre", columnDefinition = "numeric (8,0)")
	private Integer maleBeneficiary;

	@Column(name = "int_beneficiario_mujer", columnDefinition = "numeric (8,0)")
	private Integer femaleBeneficiary;

	//region Internal attribute

	@Transient
	private String ubigeo;

	@JsonIgnore
	@Column (name= "bol_flg_migrado", nullable=false, columnDefinition = "boolean default false")
	private Boolean flagMigrate = false;

	//endregion Internal attribute
}