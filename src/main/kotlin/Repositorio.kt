import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate

class Repositorio {
    lateinit var bd: JdbcTemplate
    lateinit var server: JdbcTemplate

    fun iniciar(){
        bd = Conexao().conectar()
        server = SQLserver().conectar()
    }
    fun computador(ip:String):List<Computador>{
        try {
            val comp: List<Computador> = server.query(
                """
            select * from dispositivo
                where ip = '$ip' 
        """,
                BeanPropertyRowMapper(Computador::class.java)
            )
            return comp
        } catch(exception:Exception){
            val comp: List<Computador> = bd.query(
                """
            select * from dispositivo
                where ip = '$ip' 
        """,
                BeanPropertyRowMapper(Computador::class.java)
            )
            return comp
        }
    }
    fun usuarios(pc: Computador):List<Usuario>{
        try {
            return bd.query(
                """
            select idUsuario, u.nome, email_Corporativo as email, senha, c.nome as cargo from usuario as u
	            join empresa on u.fkEmpresa = idEmpresa 
		            join dispositivo as d on d.fkEmpresa = idEmpresa
                        join cargo as c on fkCargo = idCargo
			                where u.fkEmpresa = ${pc.fkempresa};
        """,
                BeanPropertyRowMapper(Usuario::class.java)
            )
        } catch (exception: Exception) {
            val ADM = Usuario()
            ADM.email = "admuser000@permit.config"
            ADM.senha = "0000"
            ADM.cargo = "Gerenciador de Sistema para login offline"
            ADM.idUsuario = 0
            return mutableListOf(ADM)
        }
    }
    fun ocorrencia(dado:String ,pc:Computador, cpu:Double){
        try{
            server.update(
                """
            insert into ocorrencias(fkProcesso,fkDispositivo, cpu) values
            ((select idProcesso from processosBloqueados where nome = '$dado'), ${pc.idDispositivo}, $cpu)
            """
            )
        } catch(exception:Exception) {
            try {
                println("Falha ao executar o SQLserver, salvando no bd mysql. erro: $exception")
                bd.update(
                    """
            insert into ocorrencias(fkProcesso,fkDispositivo, cpu) values
            (0, ${pc.idDispositivo}, $cpu)
            """
                )
            } catch (exception:Exception){
                println("Não foi possivel inserir no mysql também. erro: $exception")
            }
        }
    }
    fun processosBloqueados(pc: Computador): List<String> {
        var processos = mutableListOf<String>()
        try {
            val proc = server.query(
                """
                select * from processosBloqueados where fkEmpresa = ${pc.fkempresa}
                """, BeanPropertyRowMapper(Processo::class.java)
            )
            proc.forEach {
                processos += it.nome
            }
        } catch(exception:Exception){
            println("Falha ao executar o select de processos Bloqueados!!")
        }
        return processos
    }
}