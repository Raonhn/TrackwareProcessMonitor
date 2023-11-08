import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate

class Conexao {
    fun conectar(): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.cj.jdbc.Driver"
        val serverName = "localhost"
        val mydatabase = "trackware"
        dataSource.url = "jdbc:mysql://$serverName/$mydatabase"
        dataSource.username = "testes"
        dataSource.password = "12345678"
        return JdbcTemplate(dataSource)
        val bd = JdbcTemplate(dataSource)



    }
}