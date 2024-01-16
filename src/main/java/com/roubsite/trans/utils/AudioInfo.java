package com.roubsite.trans.utils;

public class AudioInfo {
  private String decoder;
  
  private int samplingRate = -1;
  
  private int channels = -1;
  
  private int bitRate = -1;
  
  public String getDecoder() {
    return this.decoder;
  }
  
  public void setDecoder(String format) {
    this.decoder = format;
  }
  
  public int getSamplingRate() {
    return this.samplingRate;
  }
  
  public void setSamplingRate(int samplingRate) {
    this.samplingRate = samplingRate;
  }
  
  public int getChannels() {
    return this.channels;
  }
  
  public void setChannels(int channels) {
    this.channels = channels;
  }
  
  public int getBitRate() {
    return this.bitRate;
  }
  
  public void setBitRate(int bitRate) {
    this.bitRate = bitRate;
  }
  
  public String toString() {
    return getClass().getName() + " (decoder=" + this.decoder + ", samplingRate=" + this.samplingRate + ", channels=" + this.channels + ", bitRate=" + this.bitRate + ")";
  }
}
