package pl.com.slaw.logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Custom Logger - using sample
 * Logger.getInstance().setLogger(1, "C:/Logs/", "testLogger", 10000000);
 * Logger.getInstance().WriteToLog("Class", "String to log", LogMessagePriority.Info);
*/


public final class Logger
{
	private static Logger instance;
	private static final Object m_oPadLock = new Object();

	private int logLevel; // = Convert.ToInt32(WebConfigurationManager.AppSettings["logLevel"].ToString());
	private String logFilePath; // = WebConfigurationManager.AppSettings["logFilePath"].ToString();
	private String logFileName; // = WebConfigurationManager.AppSettings["logFileName"].ToString();
	private long logFileSize; // = Convert.ToInt64(WebConfigurationManager.AppSettings["logFileSize"].ToString());

	private static final Event<logMessageEventDelegate> logMessageEvent = new Event<logMessageEventDelegate>();

        private static String getCurrentTimeStamp() 
        {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//dd/MM/yyyy
            Date now = new Date();
            String strDate = sdfDate.format(now);
            return strDate;
        }   

	/** 
	 Private constructor to prevent instance creation
	*/
	private Logger()
	{

	}

	public void setLogger(int logLevel, String logFilePath, String logFileName, long logFileSize)
	{
		this.logLevel = logLevel;
		this.logFilePath = logFilePath;
		this.logFileName = logFileName;
		this.logFileSize = logFileSize;


		boolean folderExists = (new File(logFilePath)).isDirectory();
		if (!folderExists)
		{
			(new File(logFilePath)).mkdirs();
		}
	}

	/** 
	 An LogWriter instance that exposes a single instance
        * @return 
	*/
	public static Logger getInstance()
	{
			// If the instance is null then create one and init the Queue               
		synchronized (m_oPadLock)
		{
			if (instance == null)
			{
				instance = new Logger();
			}
			return instance;
		}
	}

	// Write message to log file
	public void WriteToLog(String procName, String message, LogMessagePriority priority)
	{
		// Send log message via event to parent app
		if (logMessageEvent != null)
		{                    
                    logMessageEvent.listeners().forEach((listener) -> {
                        listener.invoke(getCurrentTimeStamp() + "   " + message);
                    });
		}

		// Check if log message should be written to file
		if (priority.getValue() < logLevel)
		{
			return;
		}

		// Create path & filename
		String logPath = logFilePath;
		

		String logFile = logPath + logFileName + ".log";
		String oldLogFile = logPath + logFileName + ".old";
		long logSize = 0;

		// Get log file size
		try
		{
			File f = new File(logFile);
			logSize = f.length();
		}
		catch (java.lang.Exception e)
		{
		}

		// Rollover old log file if size is exceeded
		if (logSize > logFileSize)
		{
			try
			{
				new File(oldLogFile).delete();
				Files.move(Paths.get(logFile), Paths.get(oldLogFile));
			}
			catch (IOException ex)
			{
                            ex.printStackTrace();
                            return;
			}
		}

		// Write to log file
		try
		{
                    String toLog = getCurrentTimeStamp() + " " + procName + ":" + message + "\n";
                     
                    File f = new File(logFile);
                    if(!f.exists()){
                      f.createNewFile();
                    }
                    
                    Files.write(Paths.get(logFile), toLog.getBytes((StandardCharsets.UTF_8)), StandardOpenOption.APPEND);
                                        			

		}
		catch (IOException ex)
		{
                    ex.printStackTrace();
		}
		
	}
}