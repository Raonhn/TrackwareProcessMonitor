import com.github.britooo.looca.api.core.Looca
import java.util.*

fun api() {
    val looca = Looca()
    val sistema = Sistema()
    val arquivo = ScriptPython.criarPython()
    val sn = Scanner(System.`in`)
    val bd = Repositorio()
    bd.iniciar()
    val mac = getMac()
    val comp: List<Computador> = bd.computador(mac)
    val pc = comp[0]
    val processosBloqueados = bd.processosBloqueados(pc)
    val so = looca.sistema.sistemaOperacional
    val user:List<Usuario> = bd.usuarios(pc)
    println(so)

    while (true) {
        println(
            """Oque deseja fazer?
        |1) Login
        |2) Listar Processos
        |3) Começar monitoramento de Processos
        |4) Exit
    """.trimMargin()
        )

        var acao = (sn.next()).toInt()

        when (acao) {
            1 -> {
                print("Digite seu email: ")
                var email = sn.next()

                print("Digite seu senha: ")
                var senha = sn.next()

                user.forEach {
                    if (it.email == email && it.senha == senha) {
                        sistema.login(it.idUsuario, it.nome, it.email, it.senha, it.cargo)
                    }
                }

            }
            2 -> {
                var processos = looca.grupoDeProcessos.processos
                println("Processos em execução no momento:\n\r")
                processos.forEach{
                    println("""
                Nome: ${it.nome} | Memoria usada: ${it.bytesUtilizados/1024/1024} MB | CPU utilizada: ${"%.1f".format(it.usoCpu)}% | Pid: ${it.pid} 
                
            """.trimIndent())
                }
            }
            3 -> {
                val alerta = ScriptPython.criarPythonAlertas(pc)
                Runtime.getRuntime().addShutdownHook(Thread {
                    println("O monitoramento de processos foi finalizado")
                })
                if (so == "Windows") {
                    while(true) {
                        val janela = looca.grupoDeJanelas.janelas

                        processosBloqueados.forEach { process ->
                            janela.forEach {
                                if (it.titulo.lowercase().contains(process)) {
                                    println(it)
                                    val respostaMem = ScriptPython.executarScript(arquivo)
                                    encerrar(it.pid.toString(), so)
                                    bd.ocorrencia(process, pc, respostaMem)
                                    ScriptPython.executarScript2(alerta)
                                }
                            }
                        }
                    }
                } else {
                    while (true) {
                        val processos = looca.grupoDeProcessos.processos
                        processosBloqueados.forEach { process ->
                            processos.forEach {
                                if (it.nome.lowercase() == process) {
                                    println(it)
                                    val respostaMem = ScriptPython.executarScript(arquivo)
                                    encerrar(it.pid.toString(), so)
                                    bd.ocorrencia(process, pc, respostaMem)
                                    ScriptPython.executarScript2(alerta)
                                }
                            }
                        }
                    }
                }
            }
            4 -> break
        }
    }
}