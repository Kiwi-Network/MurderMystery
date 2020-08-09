/*
 * MurderMystery - Find the murderer, kill him and survive!
 * Copyright (C) 2020  Plugily Projects - maintained by Tigerpanzer_02, 2Wild4You and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.plajer.murdermystery.handlers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.plajer.murdermystery.Main;
import pl.plajer.murdermystery.arena.Arena;
import pl.plajer.murdermystery.handlers.language.LanguageManager;
import pl.plajer.murdermystery.plajerlair.commonsbox.string.StringFormatUtils;

/**
 * @author Plajer
 * <p>
 * Created at 03.08.2018
 */
@Deprecated //remove static usage
public class ChatManager {

  public static String PLUGIN_PREFIX;
  private static Main plugin;

  public ChatManager(String prefix, Main plugin) {
    PLUGIN_PREFIX = prefix;
    ChatManager.plugin = plugin;
  }

  public static String colorRawMessage(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }

  public static String colorMessage_(String message, Player player) {
      return ChatColor.translateAlternateColorCodes('&', LanguageManager.getLanguageMessage(message,player));
  }

  public static String colorMessage(String message, Player player) {
    String returnString = LanguageManager.getLanguageMessage(message,player);
    if (plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      returnString = PlaceholderAPI.setPlaceholders(player, returnString);
    }
    return colorRawMessage(returnString);
  }

  public static String colorMessage(String message, String lang) {
    return colorRawMessage(Main.lang.getString(lang,message));
  }

  public static void broadcast_(Arena arena, String key) {
    for (Player p : arena.getPlayers()) {
      p.sendMessage(PLUGIN_PREFIX + colorMessage_(key,p));
    }
  }

  public static void broadcast_replace(Arena arena, String key, String a1, String a2) {
    for (Player p : arena.getPlayers()) {
      p.sendMessage(PLUGIN_PREFIX + colorMessage_(key,p).replace(a1,a2));
    }
  }

  public static void broadcast(Arena arena, String message) {
    for (Player p : arena.getPlayers()) {
      p.sendMessage(PLUGIN_PREFIX + message);
    }
  }

  public static String formatMessage(Arena arena, String message, int integer) {
    String returnString = message;
    returnString = StringUtils.replace(returnString, "%NUMBER%", Integer.toString(integer));
    returnString = colorRawMessage(formatPlaceholders(returnString, arena));
    return returnString;
  }

  public static String formatMessage(Arena arena, String message, Player player) {
    String returnString = message;
    returnString = StringUtils.replace(returnString, "%PLAYER%", player.getName());
    returnString = colorRawMessage(formatPlaceholders(returnString, arena));
    if (plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      returnString = PlaceholderAPI.setPlaceholders(player, returnString);
    }
    return returnString;
  }

  private static String formatPlaceholders(String message, Arena arena) {
    String returnString = message;
    returnString = StringUtils.replace(returnString, "%ARENANAME%", arena.getMapName());
    returnString = StringUtils.replace(returnString, "%TIME%", Integer.toString(arena.getTimer()));
    returnString = StringUtils.replace(returnString, "%FORMATTEDTIME%", StringFormatUtils.formatIntoMMSS((arena.getTimer())));
    returnString = StringUtils.replace(returnString, "%PLAYERSIZE%", Integer.toString(arena.getPlayers().size()));
    returnString = StringUtils.replace(returnString, "%MAXPLAYERS%", Integer.toString(arena.getMaximumPlayers()));
    returnString = StringUtils.replace(returnString, "%MINPLAYERS%", Integer.toString(arena.getMinimumPlayers()));
    return returnString;
  }

  public static void broadcastAction(Arena a, Player p, ActionType action) {
    String message;
    switch (action) {
      case JOIN:
        message = formatMessage(a, ChatManager.colorMessage_("In-Game.Messages.Join",p), p);
        break;
      case LEAVE:
        message = formatMessage(a, ChatManager.colorMessage_("In-Game.Messages.Leave",p), p);
        break;
      case DEATH:
        message = formatMessage(a, ChatManager.colorMessage_("In-Game.Messages.Death",p), p);
        break;
      default:
        return; //likely won't ever happen
    }
    for (Player player : a.getPlayers()) {
      player.sendMessage(PLUGIN_PREFIX + message);
    }
  }

  public enum ActionType {
    JOIN, LEAVE, DEATH
  }

}
