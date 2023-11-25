import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

fun existe(nomeProcesso: String): Boolean {
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

fun killProcess(processName: String, so:String) {
    if(so == "Windows") {
        Runtime.getRuntime().exec("taskkill /F /IM $processName")
    }else{
        try{
            Runtime.getRuntime().exec("kill -7 $processName")
        } catch(exception:Exception){
            println("Não tenho suporte para esse sistema operacional")
        }
    }
}

fun encerrar(processo:String, so:String) {

    if (existe(processo)) {

        killProcess(processo, so)
    }
    Thread.sleep(2000)
}