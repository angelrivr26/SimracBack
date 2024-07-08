package pe.sernanp.ws_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.sernanp.ws_api.dto.SitePlanDTO;
import pe.sernanp.ws_api.model.SitePlan;

import java.util.List;

@Repository
@Transactional
public interface SitePlanRepository extends JpaRepository<SitePlan, Integer> {
    @Query(value="select ps.srl_id as id "
            + " ,ps.txt_cod_anp as anpCode "
            + " ,ps.txt_nom_anp as anpName "
            + " ,ps.txt_nombre as name "
            + " ,ps.txt_resolucion as resolution "
            + " ,ps.bol_flg_vigente as flagValidity "
            + " ,(case when ps.dte_fec_inicio isnull then '' else Cast(EXTRACT('year' from ps.dte_fec_inicio) as varchar) end) as startdate "
            + " ,(case when ps.dte_fec_fin isnull then '' else Cast(EXTRACT('year' from ps.dte_fec_fin) as varchar) end) as enddate "
            + " ,ld.srl_id as instrumentTypeId "
            + " ,ld.txt_nom_corto as instrumentTypeName "
            + " from od.t_plan_sitio as ps"
            + " left join ge.t_listado_detalle as ld on ld.srl_id = ps.int_id_tipo_instrumento "
            + " where case when ?1 = '' then 1 = 1 else ps.txt_cod_anp = ?1 end "
            + " and case when ?2 = 0 then 1 = 1 else ps.int_id_tipo_instrumento = ?2 end "
            + " and case when ?3 = '' then 1 = 1 else ps.txt_resolucion = ?3 end "
            + " and case when ?4 = '' then 1 = 1 else ps.txt_nombre = ?4 end "
            + " and case when ?5 = 2 then 1 = 1 when ?5 = 1 then ps.bol_flg_vigente = TRUE else ps.bol_flg_vigente = FALSE end"
            + " and case when ?6 = 2 then 1 = 1 when ?6 = 1 then ps.bol_flg_borrador = TRUE else ps.bol_flg_borrador = FALSE end "
            + " and ps.bol_flg_eliminado = ?7 "
            + " order by ps.srl_id desc ",
            nativeQuery=true)
    Page<SitePlanDTO> search(String anpCode, int instrumentTypeId, String resolution, String name,
                                   int flagValid, int flagDraft, boolean isDeleted, Pageable page);
    @Query(value="select ps.srl_id as id "
            + " ,ps.txt_cod_anp as anpCode "
            + " ,ps.txt_nom_anp as anpName "
            + " ,ps.txt_nombre as name "
            + " ,ps.txt_resolucion as resolution "
            + " ,ps.bol_flg_vigente as flagValidity "
            + " ,(case when ps.dte_fec_inicio isnull then '' else Cast(EXTRACT('year' from ps.dte_fec_inicio) as varchar) end) as startdate "
            + " ,(case when ps.dte_fec_fin isnull then '' else Cast(EXTRACT('year' from ps.dte_fec_fin) as varchar) end) as enddate "
            + " ,ld.srl_id as instrumentTypeId "
            + " ,ld.txt_nom_corto as instrumentTypeName "
            + " from od.t_plan_sitio as ps"
            + " left join ge.t_listado_detalle as ld on ld.srl_id = ps.int_id_tipo_instrumento "
            + " where case when ?1 = '' then 1 = 1 else ps.txt_cod_anp = ?1 end "
            + " and case when ?2 = 0 then 1 = 1 else ps.int_id_tipo_instrumento = ?2 end "
            + " and case when ?3 = '' then 1 = 1 else ps.txt_resolucion = ?3 end "
            + " and case when ?4 = '' then 1 = 1 else ps.txt_nombre = ?4 end "
            + " and case when ?5 = 2 then 1 = 1 when ?5 = 1 then ps.bol_flg_vigente = TRUE else ps.bol_flg_vigente = FALSE end"
            + " and case when ?6 = 2 then 1 = 1 when ?6 = 1 then ps.bol_flg_borrador = TRUE else ps.bol_flg_borrador = FALSE end "
            + " and ps.bol_flg_eliminado = ?7 ",
            nativeQuery=true)
    List<SitePlanDTO> search2(String anpCode, int instrumentTypeId, String resolution, String name,
                              int flagValid, int flagDraft, boolean isDeleted);

    @Modifying
    @Query(value= "update od.t_plan_sitio set txt_resolucion_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateResolutionRouteDocumentId(int id, String resolutionRouteId);

    @Modifying
    @Query(value= "update od.t_plan_sitio set txt_instrumento_ruta = ?2 where srl_id = ?1",
            nativeQuery=true)
    int updateInstrumentFile(int id, String instrumentFile);

    @Query(value= "select (case"
            + " when txt_resolucion_ruta = ?1 then txt_resolucion "
            + " when txt_instrumento_ruta = ?1 then txt_instrumento "
            + " else '' end) as fileName from od.t_plan_sitio"
            + " where txt_resolucion_ruta = ?1 or txt_instrumento_ruta = ?1 limit 1",
            nativeQuery=true)
    String getFileNameByFileId(String fileId);

    @Query(value= "select string_agg((c_nomb || ' ' || anp_nomb), ', ') from ge.v_tbt_anp "
            + " where anp_codi = any(string_to_array(?1, ','))",
            nativeQuery=true)
    String getAnpNames(String anpCodes);

    @Query(value= "select * from od.t_plan_sitio as ps "
            + " where ps.txt_cod_anp = any(string_to_array(?1, ','))",
            nativeQuery=true)
    List<SitePlan> findByAnpCode(String anpCodes);

    @Query(value= "select * from od.t_plan_sitio as ps "
            + " inner join od.t_ps_sector_turistico as st on st.int_id_plan_sitio = ps.srl_id"
            + " inner join od.t_ps_poligono_otorgamiento as po on po.int_id_sector_turistico = st.srl_id"
            + " inner join od.t_poligono as p on p.srl_id = po.int_id_poligono"
            + " where p.var_codigo = ?1",
            nativeQuery=true)
    List<SitePlan> findByPolygonCode(String polygonCode);

    List<SitePlan> findByIsDeleted(boolean isDeleted);
}
