package de.dungeongame.entities.props;

import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.physics.Collision;

@CollisionInfo(collision = true, collisionType = Collision.DYNAMIC, valign = Valign.MIDDLE, collisionBoxWidth = 10, collisionBoxHeight = 10)
public class Key extends Loot {

  private int opens;
  private String Name;

  public Key(int doorIndex, String unlockedRoom) {
    super("Key");
    this.setSize(64, 64);
    opens = doorIndex;
    Name = unlockedRoom;
  }

  @Override
  protected void pickup() {
    InteractableObjects.Keys[opens] = true;
    InteractableObjects.PickUpKey(Name);
  }
}
