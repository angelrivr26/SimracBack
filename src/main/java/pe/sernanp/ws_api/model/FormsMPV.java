package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table (name = "t_formulario")
public class FormsMPV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_formulario_srl_id_seq", sequenceName="t_formulario_srl_id_seq", allocationSize=1)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "int_id_tramite", foreignKey = @ForeignKey(name="fk_formulario_tramite"))
    private ProcedureMPV procedure;

    @Column (name= "var_depa", length = 20)
    private String int_departamento;

    @Column (name= "var_prov", length = 20)
    private String int_provincia;

    @Column (name= "var_dist", length = 20)
    private String int_distrito;

    @Column (name= "int_anio")
    private Integer int_anio;

    @Column (name= "int_meses")
    private Integer int_meses;

    @Column (name= "var_num_res", length = 255)
    private String var_num_res;

    @Column (name= "var_num_res_jef", length = 255)
    private String var_num_res_jef;

    @Column (name= "var_nom", length = 255)
    private String var_nombre;

    @Column (name= "var_desc", columnDefinition = "TEXT")
    private String var_desc;

    @Column (name= "var_plan_sitio", length = 255)
    private String var_plan_sitio;

    @Column (name= "var_inf_tec", length = 255)
    private String var_inf_tec;

    @Column (name= "var_titulo", length = 255)
    private String var_titulo;

    @Column (name= "var_objetivo", length = 1000)
    private String var_objetivo;

    @Column (name= "dte_inicio", length = 15)
    private String dte_inicio;

    @Column (name= "dte_fin", length = 15)
    private String dte_fin;

    @Column (name= "var_proyecto", length = 255)
    private String var_proyecto;

    @Column (name= "var_carta", length = 255)
    private String var_carta;

    @JsonIgnore
    @Column (name= "var_formu", length = 10)
    private String form;
}
