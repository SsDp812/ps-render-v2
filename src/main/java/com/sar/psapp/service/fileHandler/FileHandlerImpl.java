package com.sar.psapp.service.fileHandler;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sar.psapp.dto.Card;
import org.springframework.stereotype.Component;

@Component
public class FileHandlerImpl implements FileHandler {

    @Override
    public void csvFileHandler(List<Card> cards) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = "cards_" + timestamp + ".csv";

        String directoryPath = "src/main/resources/files";
        String filePath = directoryPath + "/" + fileName;

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] header = { "Name", "Price", "Bonus", "URL" };
            writer.writeNext(header);

            for (Card card : cards) {
                String[] data = {
                        card.getCardName(),
                        card.getCardPrice().toString(),
                        card.getCardBonus().toString(),
                        card.getUrl()
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            System.out.println("Ошибка создания сsv файла");
        }
    }
}
