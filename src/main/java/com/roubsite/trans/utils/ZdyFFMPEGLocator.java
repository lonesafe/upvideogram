package com.roubsite.trans.utils;


public class ZdyFFMPEGLocator extends FFMPEGLocator {
	private static final String FFMPEG_PATH = "/opt/trans";

	@Override
	public String getFFMPEGExecutablePath() {
		return FFMPEG_PATH;
	}
}
