package com.roubsite.trans.utils;

import java.io.Serializable;

public class VideoAttributes implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public static final String DIRECT_STREAM_COPY = "copy";
  
  private String codec = null;
  
  private String tag = null;
  
  private Integer bitRate = null;
  
  private Integer frameRate = null;
  
  private VideoSize size = null;
  
  private boolean faststart = false;
  
  public X264_PROFILE getX264Profile() {
    return this.x264Profile;
  }
  
  public void setX264Profile(X264_PROFILE x264Profile) {
    this.x264Profile = x264Profile;
  }
  
  public enum X264_PROFILE {
    BASELINE("baseline"),
    MAIN("main"),
    HIGH("high"),
    HIGH10("high10"),
    HIGH422("high422"),
    HIGH444("high444");
    
    private String modeName;
    
    X264_PROFILE(String modeName) {
      this.modeName = modeName;
    }
    
    public String getModeName() {
      return this.modeName;
    }
  }
  
  private X264_PROFILE x264Profile = null;
  
  String getCodec() {
    return this.codec;
  }
  
  public void setCodec(String codec) {
    this.codec = codec;
  }
  
  String getTag() {
    return this.tag;
  }
  
  public void setTag(String tag) {
    this.tag = tag;
  }
  
  Integer getBitRate() {
    return this.bitRate;
  }
  
  public void setBitRate(Integer bitRate) {
    this.bitRate = bitRate;
  }
  
  Integer getFrameRate() {
    return this.frameRate;
  }
  
  public void setFrameRate(Integer frameRate) {
    this.frameRate = frameRate;
  }
  
  VideoSize getSize() {
    return this.size;
  }
  
  public void setSize(VideoSize size) {
    this.size = size;
  }
  
  public boolean isFaststart() {
    return this.faststart;
  }
  
  public void setFaststart(boolean faststart) {
    this.faststart = faststart;
  }
  
  public String toString() {
    return getClass().getName() + "(codec=" + this.codec + ", bitRate=" + this.bitRate + ", frameRate=" + this.frameRate + ", size=" + this.size + ", faststart=" + this.faststart + ")";
  }
}
