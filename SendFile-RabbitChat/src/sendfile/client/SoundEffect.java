package sendfile.client;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public enum SoundEffect {
    
    MessageReceive("/audio/msg.wav", false), // Ringtone for Chat message receive
    FileSharing("/audio/file.wav", false), // Ringtone for income file
    ErrorSound("/audio/error.wav", false); // Ringtone for error


    private Clip clip;
    private boolean loop;
    
    SoundEffect(String filename, boolean loop){
        try {
            this.loop = loop;
            URL url = this.getClass().getResource(filename);
            AudioInputStream audioIS = AudioSystem.getAudioInputStream(url);
            
            clip = AudioSystem.getClip();
            clip.open(audioIS);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            throw new RuntimeException("Error loading sound: " + e.getMessage(), e);
        }
    }
    
    public void play(){

        if (clip == null) return; // Sound not loaded
        if (clip.isRunning()) clip.stop(); // Stop Audio
        clip.setFramePosition(0); // Reset Audio to the beginning
        if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY); // Check if audio should loop
        clip.start(); // Start playing the sound
    }
    
    public void stop(){
        if (clip != null && clip.isRunning()) clip.stop(); // Stop Audio

    }
    
    public void close(){
        stop(); // Stop audio if running
        clip.close(); // Close the clip
    }
}
