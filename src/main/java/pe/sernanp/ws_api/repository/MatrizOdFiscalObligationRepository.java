package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.MatrizOdFiscalObligation;

@Transactional
@Repository
public interface MatrizOdFiscalObligationRepository extends JpaRepository<MatrizOdFiscalObligation, Integer> {
    @Modifying
    @Query(value = "insert into od.t_matriz_obligacion_od_obligacion_fiscal(int_id_matriz_obligacion, int_id_od_obligacion_fiscal) "
            + " values (?1, ?2);",
            nativeQuery = true)
    void save2(int matrizObligationId, int odFiscalObligationId);

    @Modifying
    @Query(value = "delete from od.t_matriz_obligacion_od_obligacion_fiscal "
            + " where int_id_matriz_obligacion = ?1",
            nativeQuery = true)
    void deleteByMatrizObligation(int matrizObligationId);
}
