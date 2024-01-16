package com.roubsite.trans.utils;

public class VideoInfo {
  private String decoder;
  
  private VideoSize size = null;
  
  private int bitRate = -1;
  
  private float frameRate = -1.0F;
  
  public String getDecoder() {
    return this.decoder;
  }
  
  public void setDecoder(String codec) {
    this.decoder = codec;
  }
  
  public VideoSize getSize() {
    return this.size;
  }
  
  public void setSize(VideoSize size) {
    this.size = size;
  }
  
  public float getFrameRate() {
    return this.frameRate;
  }
  
  public void setFrameRate(float frameRate) {
    this.frameRate = frameRate;
  }
  
  public int getBitRate() {
    return this.bitRate;
  }
  
  public void setBitRate(int bitRate) {
    this.bitRate = bitRate;
  }
  
  public String toString() {
    return getClass().getName() + " (decoder=" + this.decoder + ", size=" + this.size + ", bitRate=" + this.bitRate + ", frameRate=" + this.frameRate + ")";
  }
}
