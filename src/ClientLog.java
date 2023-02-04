import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientLog {
    List<String> operationLog = new ArrayList<>();

    public void log(int productNum, int amount) {
        operationLog.add((productNum + 1) + "," + (amount + 1));
    }

    public void exportAsCSV(File txtFile) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile, true), ',', CSVWriter.NO_QUOTE_CHARACTER)) {
            operationLog.forEach(s -> csvWriter.writeNext(s.split(",")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}