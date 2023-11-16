import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

fun isProcessRunning(nomeProcesso: String): Boolean {
    val process = Runtime.getRuntime().exec("tasklist")
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    var ocorrencia = false
    reader.forEachLine {
        if (it.contains(nomeProcesso, true)) {
            ocorrencia = true
        }
    }
    return ocorrencia
}

fun killProcess(processName: String) {
    Runtime.getRuntime().exec("taskkill /F /IM $processName")
}

fun encerrar(processo:String) {

    if (isProcessRunning(processo)) {

        killProcess(processo)

        //Runtime.getRuntime().exec("py pythonIndividual.py")
    }
    Thread.sleep(2000)
}