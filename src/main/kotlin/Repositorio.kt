import org.springframework.jdbc.core.JdbcTemplate

class Repositorio {
    lateinit var bd: JdbcTemplate

    fun iniciar(){
        bd = Conexao().conectar()
    }
}