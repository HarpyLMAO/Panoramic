package me.harpylmao.utils;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class Utils {

    public static MessageAction sendGameMessage(String url, String gameName, TextChannel textChannel) {
        return textChannel.sendMessage("Click the button to play: " + gameName)
                .setActionRow(Button.success("button:game:" + gameName,"Click here to join the game").withStyle(ButtonStyle.SUCCESS).withUrl(url).withEmoji(Emoji.fromUnicode("🎮")));
    }
}
