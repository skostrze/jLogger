package pl.com.slaw.logger;


@FunctionalInterface
public interface logMessageEventDelegate
{
    void invoke(String logMessage);
}