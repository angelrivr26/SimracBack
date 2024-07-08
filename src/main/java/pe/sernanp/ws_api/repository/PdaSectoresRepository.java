package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.model.PdaSectores;

import java.util.List;

@Repository
public interface PdaSectoresRepository extends JpaRepository<PdaSectores, Integer> {

    @Query(value="SELECT * FROM od.t_pda_sector WHERE int_id_anp = :idAnp ", nativeQuery=true)
    List<PdaSectores> listByIdAnp(int idAnp);

    @Query(value="SELECT * FROM od.t_pda_sector "
            + " WHERE var_anp_cod in (:anpIds) "
            + " AND int_anio between date_part('year', CURRENT_DATE) and date_part('year', CURRENT_DATE) +3", nativeQuery=true)
    List<PdaSectores> listByAnpIds(@Param("anpIds") String[] anpIds);

}
