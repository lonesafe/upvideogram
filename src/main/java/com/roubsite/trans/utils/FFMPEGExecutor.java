package com.roubsite.trans.utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class FFMPEGExecutor {
  private static final Log _log = new Log();
  
  private String ffmpegExecutablePath;
  
  private ArrayList<String> args = new ArrayList<String>();
  
  private Process ffmpeg = null;
  
  private ProcessKiller ffmpegKiller = null;
  
  private InputStream inputStream = null;
  
  private OutputStream outputStream = null;
  
  private InputStream errorStream = null;
  
  public FFMPEGExecutor(String ffmpegExecutablePath) {
    this.ffmpegExecutablePath = ffmpegExecutablePath;
  }
  
  public void addArgument(String arg) {
    this.args.add(arg);
  }
  
  public void execute() throws IOException {
    int argsSize = this.args.size();
    String[] cmd = new String[argsSize + 1];
    cmd[0] = this.ffmpegExecutablePath;
    for (int i = 0; i < argsSize; i++)
      cmd[i + 1] = this.args.get(i); 
    if (_log.isDebugEnabled()) {
      StringBuilder sb = new StringBuilder();
      for (String c : cmd) {
        sb.append(c);
        sb.append(' ');
      } 
      _log.debug("About to execute " + sb.toString());
    } 
    Runtime runtime = Runtime.getRuntime();
    this.ffmpeg = runtime.exec(cmd);
    this.ffmpegKiller = new ProcessKiller(this.ffmpeg);
    runtime.addShutdownHook(this.ffmpegKiller);
    this.inputStream = this.ffmpeg.getInputStream();
    this.outputStream = this.ffmpeg.getOutputStream();
    this.errorStream = this.ffmpeg.getErrorStream();
  }
  
  public InputStream getInputStream() {
    return this.inputStream;
  }
  
  public OutputStream getOutputStream() {
    return this.outputStream;
  }
  
  public InputStream getErrorStream() {
    return this.errorStream;
  }
  
  public void destroy() {
    if (this.inputStream != null) {
      try {
        this.inputStream.close();
      } catch (Throwable t) {
        _log.warn("Error closing input stream", t);
      } 
      this.inputStream = null;
    } 
    if (this.outputStream != null) {
      try {
        this.outputStream.close();
      } catch (Throwable t) {
        _log.warn("Error closing output stream", t);
      } 
      this.outputStream = null;
    } 
    if (this.errorStream != null) {
      try {
        this.errorStream.close();
      } catch (Throwable t) {
        _log.warn("Error closing error stream", t);
      } 
      this.errorStream = null;
    } 
    if (this.ffmpeg != null) {
      this.ffmpeg.destroy();
      this.ffmpeg = null;
    } 
    if (this.ffmpegKiller != null) {
      Runtime runtime = Runtime.getRuntime();
      runtime.removeShutdownHook(this.ffmpegKiller);
      this.ffmpegKiller = null;
    } 
  }
}
