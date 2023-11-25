import com.github.britooo.looca.api.core.Looca

fun api() {
    val looca = Looca()
    val arquivo = ScriptPython.criarPython()
    val bd = Repositorio()
    bd.iniciar()
    val mac = getMac()
    val comp: List<Computador> = bd.computador(mac)
    val pc = comp[0]
    val processosBloqueados = bd.processosBloqueados(pc)
    val so = looca.sistema.sistemaOperacional
    println(so)

    while (true) {
        if (so == "Windows") {
            val janela = looca.grupoDeJanelas.janelas

            processosBloqueados.forEach { process ->
                janela.forEach {
                    if (it.titulo.lowercase().contains(process)) {
                        println(it)
                        val respostaMem = ScriptPython.executarScript(arquivo)
                        encerrar(it.pid.toString(), so)
                        bd.ocorrencia(process, pc, respostaMem)
                    }
                }
            }
        }else {
            val processos = looca.grupoDeProcessos.processos
            processosBloqueados.forEach { process ->
                processos.forEach {
                    if (it.nome.lowercase() == process) {
                        println(it)
                        val respostaMem = ScriptPython.executarScript(arquivo)
                        encerrar(it.pid.toString(), so)
                        bd.ocorrencia(process, pc, respostaMem)
                    }
                }
            }
        }
    }
}