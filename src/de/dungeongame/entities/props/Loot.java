package de.dungeongame.entities.props;

import de.dungeongame.entities.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.physics.Collision;

@CollisionInfo(collision = true, collisionType = Collision.DYNAMIC)
public abstract class Loot extends Prop {

  public Loot(String spritesheetName) {
    super(spritesheetName);
    onCollision(c -> {
      if (c.getInvolvedEntities().contains(Player.instance())) {
        pickup();
        Game.world().environment().remove(this);
      }
    });
  }

  protected abstract void pickup();
}

