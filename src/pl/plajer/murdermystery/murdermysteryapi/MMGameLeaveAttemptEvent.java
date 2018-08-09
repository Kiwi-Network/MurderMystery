/*
 * Murder Mystery is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Murder Mystery is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Murder Mystery.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.plajer.murdermystery.murdermysteryapi;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import pl.plajer.murdermystery.arena.Arena;

/**
 * @author Plajer
 * @since 0.0.3b
 * <p>
 * Called when player is attempting to leave arena.
 */
public class MMGameLeaveAttemptEvent extends MurderMysteryEvent {

  private static final HandlerList HANDLERS = new HandlerList();
  private final Player player;

  public MMGameLeaveAttemptEvent(Player player, Arena targetArena) {
    super(targetArena);
    this.player = player;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

  public Player getPlayer() {
    return player;
  }

  public HandlerList getHandlers() {
    return HANDLERS;
  }

}
