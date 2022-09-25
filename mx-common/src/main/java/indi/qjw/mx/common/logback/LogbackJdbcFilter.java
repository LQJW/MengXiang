package indi.qjw.mx.common.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogbackJdbcFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {
        return FilterReply.NEUTRAL;
    }
}
