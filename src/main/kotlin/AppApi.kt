import com.github.britooo.looca.api.core.Looca

fun api() {
    val looca = Looca()
    val arquivo = ScriptPython.criarPython()
    val bd = Repositorio()
    bd.iniciar()
    val ip = looca.rede.parametros.hostName
    val comp: List<Computador> = bd.computador(ip)
    val pc = comp[0]
    val processosBloqueados = bd.processosBloqueados(pc)

    while (true) {
//        val processos = looca.grupoDeProcessos.processos
        val janela = looca.grupoDeJanelas.janelas

        processosBloqueados.forEach { process ->
            janela.forEach {
                if (it.titulo.lowercase().contains(process)) {
                    println(it)
                    val respostaMem = ScriptPython.executarScript(arquivo)
                    encerrar(it.pid.toString())
                    bd.ocorrencia(process, pc, respostaMem)
                }
            }
//        processos.forEach {
//            if (it.nome.lowercase() == process) {
//                println(
//                    """
//                  Nome: ${it.nome}
//                  Pid: ${it.pid}
//                  CPU utilizada: ${"%.1f".format(it.usoCpu)}%
//                  Memoria usada: ${it.bytesUtilizados / 1024 / 1024} MB
//
//                """.trimIndent()
//                )
//            }
//        }
        }
    }
}