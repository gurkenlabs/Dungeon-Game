package de.dungeongame.entities.props;

import de.dungeongame.entities.Player;
import de.gurkenlabs.litiengine.attributes.Modification;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.physics.Collision;

@CollisionInfo(collision = true, collisionType = Collision.DYNAMIC)
public class Life extends Loot {

  public Life() {
    super("life");
  }

  @Override
  protected void pickup() {
    Player.instance().getHitPoints().modifyBaseValue(Modification.ADD, 5);
  }
}
