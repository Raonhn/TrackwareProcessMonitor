import com.github.britooo.looca.api.core.Looca

fun api(){
    val looca = Looca()
        val processos = looca.grupoDeProcessos.processos
        val janela = looca.grupoDeJanelas.janelas
        val arquivo = ScriptPython.criarPython()
    val bd = Repositorio()
    bd.iniciar()
    val ip = looca.rede.parametros.hostName
    val comp:List<Computador> = bd.computador(ip)
    val pc = comp[0]
    val processosBloqueados = bd.processosBloqueados(pc)
    processosBloqueados.forEach {process ->
        var ocorrencia = 0
        janela.forEach{
            if (it.titulo.lowercase().contains(process)){
                println("""
                ${it.pid}
            """.trimIndent())
                val respostaCpu = ScriptPython.executarScript(arquivo)
                encerrar(it.pid.toString())
                bd.ocorrencia(process, pc)
            }
        }
    }

//    processos.forEach {
//        if (it.nome.lowercase() == "chrome") {
//            println(
//                """
//                  Nome: ${it.nome}
//                  Pid: ${it.pid}
//                  CPU utilizada: ${"%.1f".format(it.usoCpu)}%
//                  Memoria usada: ${it.bytesUtilizados/1024/1024} MB
//
//                """.trimIndent()
//            )
//        }
//    }
}