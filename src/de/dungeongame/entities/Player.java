package de.dungeongame.entities;

import de.dungeongame.core.SpellManager;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.attributes.Modification;
import de.gurkenlabs.litiengine.attributes.RangeAttribute;
import de.gurkenlabs.litiengine.entities.CollisionBox;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.CombatInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.physics.IMovementController;

@CollisionInfo(collisionBoxWidth = 21, collisionBoxHeight = 5, collision = true)
@MovementInfo(velocity = 80)
@EntityInfo(width = 32, height = 32)
@CombatInfo(hitpoints = 10)

public class Player extends Creature implements IUpdateable {

  private RangeAttribute<Integer> armor;
  static CollisionBox c = new CollisionBox(10, 10);
  private static Player instance;


  private Player() {
    super("Hero");
    this.armor = new RangeAttribute<>(10, 0, 0);
  }

  public static Player instance() {
    if (instance == null) {
      instance = new Player();

    }
    return instance;

  }

  public RangeAttribute getArmor() {
    return armor;
  }

  public boolean hasArmor() {
    return armor.get() > 0;
  }

  public void damage(int damage) {
    if (this.hasArmor()) {
      int damager = damage;
      if (armor.get() >= damager) {
        armor.modifyBaseValue(Modification.SUBTRACT, damager);
      } else {
        damager = damager - armor.get();
        armor.modifyBaseValue(Modification.SET, 0);
        this.hit(damager);
      }
    } else {
      this.hit(damage);
    }
  }


  @Override
  public void update() {
    c.setCollision(false);
    c.setLocation(Player.instance().getLocation());

    if (this.getHitPoints().getRelativeCurrentValue() == 0) {

      this.die();
    }
    if (SpellManager.FireballCooldown > 0) {
      SpellManager.FireballCooldown--;
    }
    if (SpellManager.ShatterShotCooldown > 0) {
      SpellManager.ShatterShotCooldown--;
    }
  }

  @Override
  protected IMovementController createMovementController() {

    return new KeyboardEntityController<>(this);
  }
}
