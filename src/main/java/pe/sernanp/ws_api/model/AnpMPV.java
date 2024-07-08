package pe.sernanp.ws_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_anp_mpv")
public class AnpMPV {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srl_id", unique = true, nullable = false)
    @SequenceGenerator(name = "t_anp_mpv_srl_id_seq", sequenceName="t_anp_mpv_srl_id_seq", allocationSize=1)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "int_id_tramite", foreignKey = @ForeignKey(name="fk_anp_mpv_tramite"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProcedureMPV procedure;

    @ManyToOne
    @JoinColumn(name = "int_id_poligono", foreignKey = @ForeignKey(name="fk_anp_mpv_poligono"))
    private Polygon polygon;

    @Column (name= "int_anp_id")
    private Integer anp_id;

    @Column (name= "var_codi", length = 20)
    private String anp_codi;

    @Column (name= "var_name", length = 500)
    private String name;

}
