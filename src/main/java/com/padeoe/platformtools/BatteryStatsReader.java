package com.padeoe.platformtools;

import com.padeoe.batterhistorian.pojo.App;
import com.padeoe.batterhistorian.pojo.Device;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by padeoe on 2017/5/2.
 */
public class BatteryStatsReader {
    String deviceSerialNumber;
    String  packageName;
    private String uid;

    public BatteryStatsReader(String deviceSerialNumber, String packageName) {
        this.deviceSerialNumber = deviceSerialNumber;
        this.packageName = packageName;
    }

    public BatteryStats read() throws IOException, StatsInfoNotFoundException, EnvironmentNotConfiguredException, InterruptedException {
        return parseBaterryStats(getBaterryStatsText().stdString);
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
        String topUid = getUid(baterryStatsText);
        return new BatteryStats(parseMmpp(baterryStatsText, topUid), parsePoweruse(baterryStatsText, topUid));
    }

    private String getUid(String baterryStatsText) throws StatsInfoNotFoundException {
        Pattern pattern=Pattern.compile("(\\w*):\""+packageName+"\"");
        Matcher matcher=pattern.matcher(baterryStatsText);
        if(matcher.find()){
            return matcher.group(1);
        }
        throw new StatsInfoNotFoundException("未找到应用uid");
    }
    private String parseTopUid(String baterryStatsText) throws StatsInfoNotFoundException {
        String topUid;
        final String TOPUIDKW = "top=";
        int start = baterryStatsText.indexOf(TOPUIDKW);
        if (start != -1) {
            start = start + TOPUIDKW.length();
            int end = start;
            for (end = start; end < baterryStatsText.length(); end++) {
                if (baterryStatsText.charAt(end) == ':') {
                    topUid = baterryStatsText.substring(start, end);
                    return topUid;
                }
            }
        }
        throw new StatsInfoNotFoundException("TopUid Not Found");
    }

    private ADBTool.ProcessOutput getBaterryStatsText() throws EnvironmentNotConfiguredException, InterruptedException {
        String[] commands = new String[]{"adb", "-s",deviceSerialNumber, "shell","dumpsys", "batterystats"};
        return ADBTool.execute(commands);
    }

    public Map<BatteryStats.MobileComponet, Double> parsePoweruse(String input, String uid) throws StatsInfoNotFoundException {
        Map<BatteryStats.MobileComponet, Double> mobileComponetDoubleMap = new HashMap<>();
        Stream<String> stringStream = Arrays.asList(input.split("\r\r\n\r\r\n")).stream().map(block -> block.trim());
        List<String> collect = stringStream.filter(block -> block.startsWith("Estimated power use (mAh):")).collect(Collectors.toList());
        if (collect.size() > 0) {
            String powerText = collect.get(0);
            if (powerText != null) {
                for (String line : powerText.split("\r\r\n")) {
                    if (line.trim().startsWith("Uid " + uid)) {
                        Pattern pattern = Pattern.compile("(\\w+?)=([\\d\\.]+?) ");
                        Matcher matcher = pattern.matcher(line);
                        while (matcher.find()) {
                            mobileComponetDoubleMap.put(BatteryStats.MobileComponet.fromString(matcher.group(1)), Double.parseDouble(matcher.group(2)));
                        }
                        break;
                    }
                }
            }
        }
        if (mobileComponetDoubleMap.size() > 0) {
            return mobileComponetDoubleMap;
        } else {
            throw new StatsInfoNotFoundException("No Power Use Info");
        }
    }

    public double parseMmpp(String input, String uid) throws StatsInfoNotFoundException {
        double mmpp = -1;
        Stream<String> stringStream = Arrays.asList(input.split("\r\r\n\r\r\n")).stream().map(block -> block.trim());
        List<String> collect = stringStream.filter(block -> block.startsWith("Per-app mobile ms per packet:")).collect(Collectors.toList());
        if (collect.size() > 0) {
            String block = collect.get(0);
            if (block != null) {
                for (String line : block.split("\r\r\n")) {
                    if (line.trim().startsWith("Uid " + uid)) {
                        int start = line.indexOf(": ");
                        int end = line.indexOf(" (");
                        if (line.indexOf(": ") != -1 && line.indexOf(" (") != -1) {
                            try {
                                mmpp = Double.parseDouble(line.substring(start + 2, end));
                            } catch (Exception e) {
                                throw new StatsInfoNotFoundException("mmpp not found");
                            }

                        }
                        break;
                    }
                }
            }
        }

        if (mmpp != -1) {
            return mmpp;
        } else {
            return 0;
        }
    }
}