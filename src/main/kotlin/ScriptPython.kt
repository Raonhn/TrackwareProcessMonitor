import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object ScriptPython {

        var PythonExe: List<Process> = listOf()

//        val host = "localhost"
//        val user = "testes"
//        val passwd = "12345678"
//        val database = "trackware"

        fun criarPython():  String {

            val python = """
        import psutil
        cpu = psutil.cpu_percent()
        print(cpu)
    """.trimIndent()

            val nomeArquivoPython = "individualPython.py"
            File(nomeArquivoPython).writeText(python)

            return nomeArquivoPython

        }

        fun executarScript(arquivo: String):Double {
            val pythonProcess = Runtime.getRuntime().exec("py $arquivo")
            val Cpu = BufferedReader(InputStreamReader(pythonProcess.inputStream))
            var respostaCpu = 0.0
            Cpu.forEachLine {
                respostaCpu = it.toDouble()
            }
            return respostaCpu
        }
    }