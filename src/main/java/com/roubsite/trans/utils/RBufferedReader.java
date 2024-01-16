package com.roubsite.trans.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class RBufferedReader extends BufferedReader {
  private ArrayList<String> lines = new ArrayList<String>();
  
  public RBufferedReader(Reader in) {
    super(in);
  }
  
  public String readLine() throws IOException {
    if (this.lines.size() > 0)
      return this.lines.remove(0); 
    return super.readLine();
  }
  
  public void reinsertLine(String line) {
    this.lines.add(0, line);
  }
}
