import java.io.File

object ScriptPython {

        var PythonExe: List<Process> = listOf()

        val host = "localhost"
        val user = "testes"
        val passwd = "12345678"
        val database = "trackware"

        fun criarPython():  String {

            val python = """

    """.trimIndent()

            val nomeArquivoPython = "AlertasSlack.py"
            File(nomeArquivoPython).writeText(python)

            return nomeArquivoPython

        }

        fun executarScript(arquivo: String) {
            val pythonProcess = Runtime.getRuntime().exec("py $arquivo")
            PythonExe = listOf(pythonProcess)
        }

        fun pararScript() {
            for (process in PythonExe) {
                process.destroyForcibly()
            }
        }
    }