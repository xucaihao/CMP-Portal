package com.cmp.portal.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;

public class ExceptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    public static void dealThrowable(Throwable e) {
        final StackTraceElement traceElement = Thread.currentThread().getStackTrace()[2];
        String method = traceElement.getMethodName().contains("lambda")
                ? traceElement.getMethodName().split("\\$")[1] : traceElement.getMethodName();
        final String log = traceElement.getFileName().replace(".java", "") + "::" + method;
        logger.info("invoke: {}, error: {}", log, e);
        if (e instanceof HttpClientErrorException) {
            String msg = ((HttpClientErrorException) e).getMessage();
            throw new PortalException(msg);
        }
        throw new PortalException(e);
    }
}
