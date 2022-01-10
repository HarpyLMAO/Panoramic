package me.harpylmao.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Cooldown {

  @JsonProperty
  private UUID uniqueId = UUID.randomUUID();

  @JsonProperty
  private long start = System.currentTimeMillis();

  @JsonProperty
  private long expire;

  @JsonProperty
  private boolean notified;

  public Cooldown(long duration) {
    this.expire = this.start + duration;

    if (duration == 0) {
      this.notified = true;
    }
  }

  @JsonIgnore
  public long getPassed() {
    return System.currentTimeMillis() - this.start;
  }

  @JsonIgnore
  public long getRemaining() {
    return this.expire - System.currentTimeMillis();
  }

  @JsonIgnore
  public boolean hasExpired() {
    return System.currentTimeMillis() - this.expire >= 0;
  }

  @JsonIgnore
  public String getTimeLeft() {
    if (this.getRemaining() >= 60_000) {
      return TimeUtil.millisToRoundedTime(this.getRemaining());
    } else {
      return TimeUtil.millisToSeconds(this.getRemaining());
    }
  }
}
