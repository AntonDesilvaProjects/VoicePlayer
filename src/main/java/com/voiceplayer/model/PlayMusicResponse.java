package com.voiceplayer.model;

public class PlayMusicResponse {
    private AudioFile audioFile;
    private double speed;

    public AudioFile getAudioFile() {
        return audioFile;
    }

    public PlayMusicResponse setAudioFile(AudioFile audioFile) {
        this.audioFile = audioFile;
        return this;
    }

    public double getSpeed() {
        return speed;
    }

    public PlayMusicResponse setSpeed(double speed) {
        this.speed = speed;
        return this;
    }
}
