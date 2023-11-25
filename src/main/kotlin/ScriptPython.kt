import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object ScriptPython {

        var PythonExe: List<Process> = listOf()

        fun criarPython():  String {

            val python = """
            import psutil
            usada = psutil.virtual_memory().used
            total = psutil.virtual_memory().total
            mem = (usada / total) * 100
            print(mem)
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