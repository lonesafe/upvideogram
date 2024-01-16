package com.roubsite.trans.utils;


public abstract class FFMPEGLocator {
    public abstract String getFFMPEGExecutablePath();

    public FFMPEGExecutor createExecutor() {
        return new FFMPEGExecutor(getFFMPEGExecutablePath());
    }
}
