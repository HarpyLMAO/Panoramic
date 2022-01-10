package me.harpylmao.audio.guild;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.harpylmao.audio.AudioSendProvider;
import me.harpylmao.audio.MusicManager;
import me.harpylmao.audio.TrackScheduler;
import net.dv8tion.jda.api.entities.Guild;

public class GuildMusicManager {

  private AudioPlayer audioPlayer;
  private TrackScheduler trackScheduler;
  private AudioSendProvider audioSendProvider;

  public GuildMusicManager(MusicManager guildAudioManager, Guild guild) {
    this.audioPlayer = guildAudioManager.getPlayerManager().createPlayer();

    this.trackScheduler =
      new TrackScheduler(this.audioPlayer, guildAudioManager, guild);
    this.audioSendProvider = new AudioSendProvider(audioPlayer);

    this.audioPlayer.addListener(this.trackScheduler);
  }

  public AudioPlayer getAudioPlayer() {
    return audioPlayer;
  }

  public TrackScheduler getTrackScheduler() {
    return trackScheduler;
  }

  public AudioSendProvider getAudioSendProvider() {
    return audioSendProvider;
  }
}
