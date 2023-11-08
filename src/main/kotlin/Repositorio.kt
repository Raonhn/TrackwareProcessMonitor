import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate

class Repositorio {
    lateinit var bd: JdbcTemplate

    fun iniciar(){
        bd = Conexao().conectar()
    }
    fun computador(ip:String):List<Computador>{
        val comp:List<Computador> = bd.query("""
            select * from dispositivo
                where ip = '$ip'    
        """,
            BeanPropertyRowMapper(Computador::class.java)
        )
        return comp
    }
    fun usuarios(pc: Computador):List<Usuario>{
        val user:List<Usuario> = bd.query("""
            select idUsuario, u.nome, email_Corporativo as email, senha, c.nome as cargo from usuario as u
	            join empresa on u.fkEmpresa = idEmpresa 
		            join dispositivo as d on d.fkEmpresa = idEmpresa
                        join cargo as c on fkCargo = idCargo
			                where u.fkEmpresa = ${pc.fkempresa};
        """,
            BeanPropertyRowMapper(Usuario::class.java)
        )
        return user
    }
    fun ocorrencia(dado:String ,pc:Computador){
        val AppAberto = bd.queryForObject(
            """
                select idProcesso from processosBloqueados where nome = '$dado' and fkEmpresa = ${pc.fkempresa}
                """, Int::class.java)
        bd.update(
            """
            insert into ocorrencias(fkProcesso,fkDispositivo) values
            ($AppAberto, ${pc.idDispositivo})
            """
        )
    }
}