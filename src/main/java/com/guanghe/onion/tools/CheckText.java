package com.guanghe.onion.tools;

import java.io.*;
import java.nio.charset.Charset;

/**
 *  * check repetitive text
 *  
 */
public class CheckText {

    private static final BufferedReader br =null;
    private static final BufferedReader cbr =null;

    public static String check(String s1, String compare) {
        System.out.println("======Start Search!=======");
        long startTime = System.currentTimeMillis();
        StringBuffer result=new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s1.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
            BufferedReader cbr = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(compare.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
            cbr.mark(90000000);

            String lineText = null;
            while ((lineText = br.readLine()) != null) {
                String searchString = lineText.trim();
                search(searchString, cbr, result);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("======Process Over!=======");
            System.out.println("Time Spending:" + ((endTime - startTime) / 1000D) + "s");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (cbr != null) {
                        try {
                            cbr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return result.toString();
    }

    public static void search(String searchText, BufferedReader comparedReader, StringBuffer sb)
            throws IOException {

        if (searchText == null||"".equals(searchText)) {
            return;
        }
        String lineText = null;
        int lineNum = 1;
        while ((lineText = comparedReader.readLine()) != null) {
            String comparedLine = lineText.trim();
            if (searchText.equals(comparedLine)) {
                return;
            }
            lineNum++;
        }
        sb.append("-- error--:"+searchText+"\n");
        comparedReader.reset();
//        return;
    }


}