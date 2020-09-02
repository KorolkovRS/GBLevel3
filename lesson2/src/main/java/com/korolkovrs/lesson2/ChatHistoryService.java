package com.korolkovrs.lesson2;

import javax.swing.*;
import java.io.*;

public class ChatHistoryService {
    private JTextArea charTextArea;
    private int cash;
    private File file = new File(getClass().getClassLoader().getResource("history.txt").getFile());

    public ChatHistoryService(JTextArea charTextArea) {
        this(charTextArea, 100);
    }

    public ChatHistoryService(JTextArea charTextArea, int cash) {
        this.charTextArea = charTextArea;
        this.cash = cash;
        System.out.println(file);
    }

    public void saveHistory() {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file))){
            writer.write(charTextArea.getText());
            System.out.println("History save");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadHistory() {
        String text = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {


            StringBuilder stringBuilder = new StringBuilder();
            String buff;
            boolean start = false;
            int i = 0;

            while ((buff = reader.readLine()) != null) {
                if (i >= cash) {
                    break;
                }
                if (start && (!buff.isBlank())) {
                    stringBuilder.append(buff + "\n");
                    i++;
                }
                if (buff.startsWith("/authok")) {
                    start = true;
                }
            }
            text = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (text == null) ? "Message history is empty" : text;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
