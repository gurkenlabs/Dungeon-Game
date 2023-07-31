package de.dungeongame.entities.props;

import de.dungeongame.entities.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.attributes.Modification;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.resources.Resources;
@CollisionInfo(collision = true, collisionType = Collision.DYNAMIC)
public class Armor extends Loot {

  public Armor() {
    super("armor");
  }

  @Override
  protected void pickup() {
    Game.audio().playSound(Resources.sounds().get("resources/sounds/dragon.wav"));
    Game.world().environment().remove(this);
    Player.instance().getArmor().modifyBaseValue(Modification.ADD, 1);
  }
}
