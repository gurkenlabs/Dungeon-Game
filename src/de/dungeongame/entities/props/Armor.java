package de.dungeongame.entities.props;

import de.dungeongame.entities.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.attributes.Modification;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.resources.Resources;

@CollisionInfo(collision = false)
public class Armor extends Prop implements IUpdateable {

  public Armor() {
    super("armor");
  }

  @Override
  public void update() {
    if (getBoundingBox().intersects(Player.instance().getCollisionBox()) && Player.instance()
      .hasArmor()) {
      Game.audio().playSound(Resources.sounds().get("resources/sounds/dragon.wav"));
      Game.world().environment().remove(this);
      Player.instance().getArmor().modifyBaseValue(Modification.ADD, 1);
    }
  }
}
