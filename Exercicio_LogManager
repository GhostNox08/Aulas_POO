import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

enum LogLevel {
    ERROR, WARNING, INFO, DEBUG
}

class LogManager {

    private static LogManager instance;
    private boolean logToFile;
    private String filePath;

    private LogManager() {
        this.logToFile = false;
    }

    public static synchronized LogManager getInstance() {
        if (instance == null) {
            instance = new LogManager();
        }
        return instance;
    }

    public void setLogFile(String filePath) {
        this.filePath = filePath;
        this.logToFile = true;
    }

    public void setConsoleLogging() {
        this.logToFile = false;
    }


    public void log(LogLevel level, String message) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String formattedMessage = String.format("[%s] [%s] %s", timestamp, level, message);

        if (logToFile && filePath != null) {
            writeToFile(formattedMessage);
        } else {
            System.out.println(formattedMessage);
        }
    }

 
    private void writeToFile(String message) {
        try (FileWriter fw = new FileWriter(filePath, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(message);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        LogManager logManager = LogManager.getInstance();

        logManager.setConsoleLogging();
        logManager.log(LogLevel.INFO, "Aplicação iniciada.");
        logManager.log(LogLevel.WARNING, "Uso de memória alto detectado.");
        logManager.log(LogLevel.ERROR, "Falha ao conectar ao banco de dados.");

        logManager.setLogFile("app_log.txt");
        logManager.log(LogLevel.INFO, "Salvando logs em arquivo...");
        logManager.log(LogLevel.DEBUG, "Valor da variável X = 42");


        LogManager outroLog = LogManager.getInstance();
        outroLog.log(LogLevel.INFO, "Verificando instância única.");
    }
}
