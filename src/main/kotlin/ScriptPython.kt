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
        fun criarPythonAlertas():  String {

            val python = """
import smtplib
import email.message
import datetime

corpo = ""${'"'}
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alerta da Trackware - Ocorrência de Processo Bloqueado</title>
</head>
<body style="
    font-family: Arial, sans-serif;
    background-color: #f4f4f4;
    margin: 0;
    padding: 0;">
    <div style="
        max-width: 90%;
        margin: 0 auto;
        padding: 2%;">
        <div style="
            background-color: #6b3e98;
            color: #ffffff;
            text-align: center;
            padding: 3%;">
            <img style="
                max-width: 10%;
                height: auto;
                " src="https://i.imgur.com/NIBLx6a.png" alt="Logotipo da Trackware">
            <h1>Alerta da Trackware</h1>
        </div>
        <div style="
            background-color: #ffffff;
            padding: 3%;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">
            <p style="font-size: 18px; margin-bottom: 20px;">Prezado Cliente,</p>
            <p style="font-size: 18px; margin-bottom: 20px;">Alguém acessou um aplicativo com nome bloqueado!</p>
""${'"'}

end_corpo = ""${'"'}
            <p style="font-size: 18px; margin-bottom: 20px;">Por favor, clique no retângulo abaixo para acessar nosso site e acompanhar o dispositivo.</p>
            <a style="
                display: inline-block;
                background-color: #6b3e98;
                color: #ffffff;
                padding: 2% 4%;
                text-decoration: none;
                border-radius: 5px;" href="#">Acessar Trackware System</a>
        </div>
    </div>
</body>
</html>
""${'"'}

def enviar_email(corpo, end_corpo):
    dia = " as " + datetime.datetime.now().strftime('%d/%m/%Y %H:%M:%S')
    corpo += "<p>" + dia + "</p>"
    mensagem = email.message.Message()
    mensagem['Subject'] = "Alerta"
    mensagem['From'] = 'nathanraoliveira@gmail.com'
    mensagem['To'] = 'alertas-aaaak42km6rgih2za6nkswurre@trackware-workspace.slack.com'
    password = 'empmruelvcjsbreg'
    mensagem.add_header('Content-Type', 'text/html; charset=UTF-8')
    mensagem.set_payload(corpo + end_corpo)

    s = smtplib.SMTP('smtp.gmail.com: 587')
    s.starttls()
    s.login(mensagem['From'], password)
    s.sendmail(mensagem['From'], [mensagem['To']], mensagem.as_string().encode('utf-8'))

enviar_email(corpo, end_corpo)
        """.trimIndent()

            val nomeArquivoPython = "individualAlertaPython.py"
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

        fun executarScript2(arquivo: String) {
            val pythonProcess = Runtime.getRuntime().exec("py $arquivo")
        }
    }