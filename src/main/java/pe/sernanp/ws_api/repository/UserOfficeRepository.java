package pe.sernanp.ws_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.sernanp.ws_api.dto.UserOfficeI;
import pe.sernanp.ws_api.model.UserOffice;

import java.util.List;

@Repository
public interface UserOfficeRepository extends JpaRepository<UserOffice,Integer> {

    @Modifying
    @Query(value="delete from od.t_usuario_oficina where int_usuario = ?1 and int_rol = ?2", nativeQuery=true)
    int deleteById(int userId, int rolId);

    @Query(value="select * from od.t_usuario_oficina where int_usuario = ?1 and int_rol = ?2", nativeQuery=true)
    List<UserOffice> findByIdAndRolId(int userId, int rolId);

    @Query(value="select uo.int_anp as id, o.nombre as name from od.t_usuario_oficina uo " +
            " inner join ge.v_tbt_anp o on o.anp_id  = uo.int_anp " +
            " where uo.int_usuario = ?1 and uo.int_rol = ?2", nativeQuery=true)
    List<UserOfficeI> detail(int userId, int rolId);
}
