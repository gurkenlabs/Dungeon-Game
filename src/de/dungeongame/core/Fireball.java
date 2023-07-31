package de.dungeongame.core;

import de.dungeongame.entities.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.CombatInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.entities.Prop;


@CollisionInfo(collisionBoxWidth = 10, collisionBoxHeight = 10, collision = true)
@MovementInfo(velocity = 60)
@EntityInfo(width = 35, height = 41)
@CombatInfo(hitpoints = 1)
public class Fireball extends Creature implements IUpdateable {

  private static Fireball instance;

  private int moves;

  private Fireball() {
    super("Feuerball");
    this.setAngle(Player.instance().getAngle());
    Game.physics().move(this, (int) this.getAngle(), 1000);
    moves = 0;
    onCollision(c -> {
      c.getInvolvedEntities().forEach(e -> {
        if (e instanceof Prop prop && !prop.isDead()) {
          prop.hit(50);
        } else if (e instanceof Creature creature && !creature.isDead()) {
          creature.hit(50);
        }
      });
      die();
    });
  }

  public static Fireball instance() {
    if (instance == null) {
      instance = new Fireball();
    }
    return instance;
  }

  @Override
  public void die() {
    super.die();
    Game.world().environment().remove(this);
    moves = 0;
    SpellManager.state = 0;
  }

  @Override
  public void update() {
    if (!Game.physics().collides(this) && moves < 50) {
      Game.physics().move(this, getFacingDirection(), 3);
      moves++;
    } else {
      die();
    }
  }
}


