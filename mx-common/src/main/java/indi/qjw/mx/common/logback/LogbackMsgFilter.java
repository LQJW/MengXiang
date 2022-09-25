package indi.qjw.mx.common.logback;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogbackMsgFilter {
    private static final HashSet<String> regxs = new HashSet<>();

    static {
        regxs.add("Connection reset by peer");
        regxs.add("Adjusted frame length exceeds");
    }

    public boolean filter(String msg) {
        for (String regx : regxs) {
            Pattern pattern = Pattern.compile(regx);
            Matcher matcher = pattern.matcher(msg);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }
}
