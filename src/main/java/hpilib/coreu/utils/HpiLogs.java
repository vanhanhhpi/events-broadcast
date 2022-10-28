//package hpilib.coreu.utils;
//
//import com.hpi.backend.adapter.controller.handler.context.RequestInfoContext;
//import hpilib.coreu.utils.utils.ConfigUtils;
//import io.netty.handler.logging.LogLevel;
//import org.jboss.logging.Logger;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//
//@ApplicationScoped
//public class HpiLogs {
//
//
//  @Inject
//  RequestInfoContext requestInfoContext;
//
//  public static final String ANSI_RESET = "\u001B[0m";
//  public static final String ANSI_BLACK = "\u001B[30m";
//  public static final String ANSI_RED = "\u001B[31m";
//  public static final String ANSI_GREEN = "\u001B[32m";
//  public static final String ANSI_YELLOW = "\u001B[33m";
//  public static final String ANSI_BLUE = "\u001B[34m";
//  public static final String ANSI_PURPLE = "\u001B[35m";
//  public static final String ANSI_CYAN = "\u001B[36m";
//  public static final String ANSI_WHITE = "\u001B[37m";
//
//  private final Logger LOG = Logger.getLogger(HpiLogs.class);
//
//  static ConfigUtils.ConfigBO conf = ConfigUtils.get();
//
//  private boolean manualInstance = false;
//  private HpiLogs(){
//  }
//
//  public HpiLogs(String className){
//    manualInstance = true;
//  }
//  public Logger getLogger() {
//    return LOG;
//  }
//  private RequestInfoContext.RequestInfoBO getRequest() {
//    if (requestInfoContext == null) {
//      return new RequestInfoContext.RequestInfoBO() {{
//        // NOT REQUEST SCOPE
//        requestId = "NRSLog";
//      }};
//    }
//    return requestInfoContext.getScope();
//  }
//
//  public String getRequestId() {
//    return "RequestID: " + requestInfoContext.getScope().requestId;
//  }
//  private String message(String str){
//    return "";
//  }
//
//  public void log(String str, LogLevel logLevel) {
//    str = " RID: " + getRequest().requestId + " | " + str;
//    if (logLevel == LogLevel.DEBUG) {
//      LOG.debug(str);
//    }
//    if (logLevel == LogLevel.TRACE) {
//      LOG.trace(str);
//    }
//    if (logLevel == LogLevel.WARN) {
//      LOG.warn(str);
//    }
//    if (logLevel == LogLevel.ERROR) {
//      LOG.error(str);
//    }
//    if (logLevel == LogLevel.INFO) {
//      LOG.info(str);
//    }
//  }
//
//  public void info(String str) {
//    str = printer(Thread.currentThread(), str);
//    LOG.info(str);
//  }
//
//  public void debug(String str) {
//    str = printer(Thread.currentThread(), str);
//    LOG.debug(str);
//  }
//
//  public void trace(String str) {
//    str = printer(Thread.currentThread(), str);
//    LOG.trace(str);
//
//  }
//
//  public void warn(String str) {
//    str = printer(Thread.currentThread(), str);
//    LOG.warn(str);
//
//  }
//
//  public void error(String str) {
//    str = printer(Thread.currentThread(), str);
//    LOG.error(str);
//
//  }
//
//  public void error(String str, Throwable e) {
//    str = printer(Thread.currentThread(), str);
//    LOG.error(str);
//
//  }
//
//  private String printer(Thread thread, String strLog){
//    StackTraceElement[] stackTraceElements = thread.getStackTrace();
//    StackTraceElement caller = stackTraceElements[manualInstance ? 3 : 14];
//    var tmp = manualInstance;
//
//    // new object => stackTraceElements[3];
//    String log = "";
//    if (conf.hpiLog_EnableFilename){
//      log += ANSI_YELLOW + caller.getFileName() + " > " + ANSI_RESET;
//    }
//    if (conf.hpiLog_EnableClassName){
//      var tmpSub = caller.getClassName().split("\\.");
//      String tmpStrPath = "";
//      for (var i = 0 ;i < tmpSub.length - 1; i++){
//        tmpStrPath += (tmpSub[i].length() > 3) ? tmpSub[i].substring(0,3) + "." : tmpSub[i] + ".";
//      }
//      tmpStrPath += tmpSub[tmpSub.length-1];
//      log += ANSI_BLUE + "[" + tmpStrPath + "] " + ANSI_RESET;
//    }
//    if (conf.hpiLog_EnableMethodNameAndLineNumber){
//      log += ANSI_RED + "[" + caller.getMethodName() + ":" + caller.getLineNumber() + "] " + ANSI_RESET;
//    }
//    if (conf.hpiLog_EnablePaymentRequestId){
//      log += ANSI_GREEN +"[RID: " + getRequest().requestId + "] " + ANSI_RESET;
//    }
//    log += "- ";
//    log += strLog;
//    return log;
//  }
//
//}
