import com.github.britooo.looca.api.core.Looca

fun main(){
    val looca = Looca()
        val processos = looca.grupoDeProcessos.processos
        val janela = looca.grupoDeJanelas.janelas

    val arquivo = ScriptPython.criarPython()

    ScriptPython.executarScript(arquivo)

    Runtime.getRuntime().addShutdownHook(Thread {
        println("O monitoramento foi finalizado")
        ScriptPython.pararScript()
    })

    processos.forEach {
        if (it.nome.lowercase() == "idea64") {
            println(
                """
                              Nome: ${it.nome}
                              Pid: ${it.pid}
                              CPU utilizada: ${"%.1f".format(it.usoCpu)}%
                              Memoria usada: ${it.bytesUtilizados/1024/1024} MB
                              
                              """.trimIndent()
            )

        }
    }
    janela.forEach{
        if (it.titulo.lowercase().contains("nathanindividual")){
            println("""
                ${it.titulo}
            """.trimIndent())
        }
    }
}