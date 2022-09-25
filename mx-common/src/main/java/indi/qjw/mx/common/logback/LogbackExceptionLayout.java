package indi.qjw.mx.common.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;
import com.alibaba.fastjson.JSONObject;
import indi.qjw.mx.common.util.Utils;

public class LogbackExceptionLayout extends LayoutBase<ILoggingEvent> {
    @Override
    public String doLayout(ILoggingEvent event) {
        try {
            IThrowableProxy throwableProxy = event.getThrowableProxy();
            if (throwableProxy == null) {
                return "";
            }
            String threadName = event.getThreadName();
            String loggerName = event.getLoggerName();
            StackTraceElement[] stackTraces = event.getCallerData();
            int time = (int) (event.getTimeStamp() / 1000);
            String msg = throwableProxy.getMessage();
            String clazz = throwableProxy.getClassName();

            LogbackMsgFilter filter = new LogbackMsgFilter();
            boolean isFilter = filter.filter(msg);
            if (isFilter) {
                return "";
            }
            String trace = Utils.stackToString(stackTraces);
            JSONObject json = new JSONObject();
            json.put("threadName", threadName);
            json.put("loggerName", loggerName);
            json.put("clazz", clazz);
            json.put("msg", msg);
            json.put("trace", trace);
            json.put("time", time);

            String jsonString = json.toJSONString();
            return jsonString + CoreConstants.LINE_SEPARATOR;
        } catch (Exception e) {
            return "";
        }
    }


}
