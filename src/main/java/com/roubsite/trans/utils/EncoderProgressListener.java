package com.roubsite.trans.utils;

public interface EncoderProgressListener {
  void sourceInfo(MultimediaInfo paramMultimediaInfo);
  
  void progress(int paramInt);
  
  void message(String paramString);
}
