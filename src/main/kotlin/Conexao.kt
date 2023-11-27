import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate

class Conexao {
    var serverName = "localhost:3306"
    var mydatabase = "trackware"
    var username = "root"
    var password = "Trackware000"
    lateinit var bd:JdbcTemplate
    fun conectar(): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.cj.jdbc.Driver"
        val serverName = this.serverName
        val mydatabase = this.mydatabase
        dataSource.url = "jdbc:mysql://$serverName/$mydatabase"
        dataSource.username = this.username
        dataSource.password = this.password
        bd = JdbcTemplate(dataSource)
        return bd
    }

    fun criarTabela(){
        bd.execute("""       
        CREATE TABLE IF NOT EXIST create table ocorrencias(
            idOcorrencias int primary key auto_increment,
            fkProcesso int,
            fkDispositivo int,
            foreign key (fkDispositivo) references dispositivo(idDispositivo),
            memoria float,
            dtHora datetime default current_timestamp
        );
    """)
    }
}