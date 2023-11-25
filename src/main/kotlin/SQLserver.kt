import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate

class SQLserver {
    var serverName = "52.45.132.234"
    var mydatabase = "trackware"
    var username = "sa"
    var password = "#Gfsptech"
    fun conectar(): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
        val serverName = this.serverName
        val mydatabase = this.mydatabase
        dataSource.url = "jdbc:sqlserver://$serverName;database=$mydatabase;encrypt=false;trustServerCertificate=false"
        dataSource.username = this.username
        dataSource.password = this.password
        return JdbcTemplate(dataSource)
    }
}