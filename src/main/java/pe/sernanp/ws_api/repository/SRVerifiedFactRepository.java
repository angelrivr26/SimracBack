package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.model.SRVerifiedFact;

import java.util.List;

@Repository
@Transactional
public interface SRVerifiedFactRepository extends JpaRepository<SRVerifiedFact, Integer> {
    List<SRVerifiedFact> findBySupervisionRecordIdAndIsDeleted(int supervisionRecordId, boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_acta_super_hecho_verificado set bol_flg_eliminado = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateIsDeleted(int id, boolean isDeleted);


    @Modifying
    @Query(value= "insert into od.t_acta_super_hecho_verificado(int_id_od_obligacion_fiscal, int_id_incumplimiento, int_id_acta_supervision) "
            + " select t_ood.srl_id, ?3, ?1 "
            + " from od.t_od_obligacion_fiscal as t_ood "
            + " inner join od.t_matriz_obligacion_od_obligacion_fiscal as t_moodf on t_moodf.int_id_od_obligacion_fiscal = t_ood.srl_id "
            + " where int_id_od = ?2",
            nativeQuery=true)
    void generateMassive(int id, int odId, int breachId);
}
