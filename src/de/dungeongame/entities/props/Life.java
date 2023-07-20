package de.dungeongame.entities.props;

import de.dungeongame.entities.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.attributes.Modification;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Prop;

@CollisionInfo(collision = false)
@AnimationInfo(spritePrefix = "prop-life")

public class Life extends Prop implements IUpdateable {

  public Life(String spritesheetName) {
    super(spritesheetName);
  }

  @Override
  public void update() {
    if (getBoundingBox().intersects(Player.instance().getCollisionBox())) {
      Game.world().environment().remove(this);
      Player.instance().getHitPoints().modifyBaseValue(Modification.ADD, 5);
    }

  }
}
