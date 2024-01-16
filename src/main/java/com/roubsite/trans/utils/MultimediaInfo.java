package com.roubsite.trans.utils;

public class MultimediaInfo {
  private String format = null;
  
  private long duration = -1L;
  
  private AudioInfo audio = null;
  
  private VideoInfo video = null;
  
  public String getFormat() {
    return this.format;
  }
  
  public void setFormat(String format) {
    this.format = format;
  }
  
  public long getDuration() {
    return this.duration;
  }
  
  public void setDuration(long duration) {
    this.duration = duration;
  }
  
  public AudioInfo getAudio() {
    return this.audio;
  }
  
  public void setAudio(AudioInfo audio) {
    this.audio = audio;
  }
  
  public VideoInfo getVideo() {
    return this.video;
  }
  
  public void setVideo(VideoInfo video) {
    this.video = video;
  }
  
  public String toString() {
    return getClass().getName() + " (format=" + this.format + ", duration=" + this.duration + ", video=" + this.video + ", audio=" + this.audio + ")";
  }
}
