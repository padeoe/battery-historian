package com.padeoe.platformtools;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by padeoe on 2017/5/2.
 */
public class BatteryStatsReader {
    private List<Charset> charsets = Arrays.asList(new Charset[]{StandardCharsets.UTF_8, StandardCharsets.UTF_16,
            StandardCharsets.UTF_16BE, StandardCharsets.UTF_16LE, StandardCharsets.ISO_8859_1, StandardCharsets.US_ASCII});
    private File inputFile = new File("batterystats.txt");
    private Charset charset = null;


    public BatteryStats read() throws IOException {
/*
        String topUid;
        String[] uids = Files.readAllLines(inputFile.toPath()).stream().filter(line -> line.indexOf("top=") != -1).toArray(String[]::new);
        if (uids.length > 0) {
            topUid = uids[0];
        } else {
            throw new Exception("inputFile");
        }
        =uids.length > 0 ?:null;
*/

        return null;
    }


    public static String findTopUid(List<String> batterystats) {
        String Uid = null;
        boolean findUid = false;
        for (String temp : batterystats) {
            int start = temp.indexOf("top=");
            if (start == -1) continue;
            int end = temp.indexOf(':', start);
            Uid = temp.substring(start + 4, end);
            findUid = true;
            break;
        }
        if (findUid == false) {
            System.out.println("未找到top UID");
        }
        return Uid;
    }

    private void detectCharSet() {
        boolean anyMatch = charsets.stream().anyMatch(cs -> checkCharset(cs));
        if (!anyMatch) {
            //   throw UnknownCharSetException();
        }
    }

    private boolean checkCharset(Charset charset) {
        boolean charSetRight = detectCharSet(charset);
        if (charSetRight) this.charset = charset;
        return charSetRight;
    }

    private boolean detectCharSet(Charset charset) {
        try {
            return Files.readAllLines(inputFile.toPath(), charset).stream().anyMatch(line -> line.indexOf("top=") != -1);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("start");
        stringBuilder.append((char) 0);
        stringBuilder.append((char) 50);
        System.out.println(stringBuilder.toString());
    }


    public BatteryStats getBaterryStats() throws EnvironmentNotConfiguredException, InterruptedException, StatsInfoNotFoundException {
        ADBTool.ProcessOutput output = getBaterryStatsText();
        String baterryStatsText = output.stdString;
        if (baterryStatsText != null) {
            return parseBaterryStats(baterryStatsText);
        } else {
            throw new EnvironmentNotConfiguredException(output.errorString);
        }
    }

    private BatteryStats parseBaterryStats(String baterryStatsText) throws StatsInfoNotFoundException {
        int topUid = parseTopUid(baterryStatsText);
        //TODO
        return null;
    }

    private int parseTopUid(String baterryStatsText) throws StatsInfoNotFoundException {
        int topUid;
        final String TOPUIDKW = "top=";
        int start = baterryStatsText.indexOf(TOPUIDKW);
        if (start != -1) {
            int end = start + TOPUIDKW.length();
            try {
                topUid = Integer.parseInt(baterryStatsText.substring(start, end));
            } catch (RuntimeException runtimeException) {
                throw new StatsInfoNotFoundException("", runtimeException);
            }
            return topUid;
        }
        throw new StatsInfoNotFoundException("TopUid Not Found");
    }

    private ADBTool.ProcessOutput getBaterryStatsText() throws EnvironmentNotConfiguredException, InterruptedException {
        String[] commands = new String[]{"adb", "shell", "dumpsys", "batterystats"};
        return ADBTool.execute(commands);
    }

    public Map<String, Double> parsePoweruse() {

/*        Map<String,Double> result = new HashMap<>();
        int start,end = 0;
        boolean founded = false;
        Pattern compile = Pattern.compile("(\\w+?)=([\\d\\.]+?) ");

        for(String temp:batterystats){
            int test = 0;
            if(founded == false) {
                test = temp.indexOf(EPS);
                if (test != -1) {
                    founded = true;
                }
                continue;
            }

            test= temp.indexOf(findkey);
            if (test == -1) continue;

            //总电量耗费能力
            start = temp.indexOf(':', test);
            end = temp.indexOf('(',start);
            double total = Double.parseDouble(temp.substring(start + 1, end));
            result.put("total",total);

            Matcher matcher = compile.matcher(temp.substring(end));
            while (matcher.find()) {
                result.put(matcher.group(1), Double.parseDouble(matcher.group(2)));
            }
            break;
        }
        return result;*/
        return null;
    }
}
