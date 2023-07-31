package de.dungeongame.entities.props;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Prop;

public class Destructible extends Prop {

  public Destructible(String spritesheetName) {
    super(spritesheetName);
  }

  @Override
  public void die() {
    super.die();
    Prop loot = Game.random().choose(new Prop[]{new Life(), new Armor()});
    Game.world().environment().add(loot);
    loot.setLocation(getCenter().getX() - loot.getWidth() / 2d,
      getCenter().getY() - loot.getHeight() / 2d);
  }
}
