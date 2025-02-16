package de.dungeongame.entities.props;

import de.dungeongame.core.Fireball;
import de.dungeongame.core.ShatterShot;
import de.dungeongame.core.SpellManager;
import de.dungeongame.entities.Player;
import de.dungeongame.entities.StoryChar;
import de.dungeongame.logic.GameManager;
import de.dungeongame.ui.HUD;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.CollisionBox;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.resources.Resources;
import java.util.Collection;

public class InteractableObjects {

  public static String currentMap = "atrium";
  public static long lastDoorInteraction;
  protected static boolean[] Keys = new boolean[4];
  public static String KeyPickUpName;
  public static Boolean pickedUp;
  static final CollisionBox collider = new CollisionBox(20, 20);

  public static void Interact() {
    Collection<Prop> Storage = Game.world().environment().getProps("interactable");
    Collection<Prop> Gateway = Game.world().environment().getProps("door");
    Collection<Prop> Spellcrates = Game.world().environment().getProps("spells");

    for (Prop p : Storage) {

      collider.setLocation(p.getCenter());
      collider.setCollision(false);
      collider.setName("collider");

      // colision Box collider is set to the current Prop p position
      // if collider intersects with the Players bounds, use hit() to change the state of the Prop
      // to destroyed

      if (Player.instance().getCollisionBox().intersects(collider.getCollisionBox())
        && !p.isDead()) {
        p.hit(100);
        Game.audio()
          .playSound(Resources.sounds().get("pickup-1.wav"));

        // spawn the Loot that's in the chest
        Life h = new Life();
        Game.world().environment().add(h);
        h.setLocation(p.getX() - 5, p.getY() - 5);
        p.setCollision(true);
      }
    }
    // Door interaction
    for (Prop pr : Gateway) {

      pr.setCollision(false);
      if (pr.getBoundingBox().intersects(Player.instance().getBoundingBox())
        && Game.time().since(lastDoorInteraction) > 300
        && GameManager.anzahlMonster == 0) {
        if (pr.hasTag("lock")) {
          for (int i = 0; i < Keys.length; i++) {
            if (Keys[i] && pr.hasTag(String.valueOf(i))) {

              pr.removeTag("lock");
              break;
            }
          }

          if (pr.hasTag("lock")) {

            Game.audio()
              .playSound(Resources.sounds().get("door-1.wav"));
            HUD.renderLock = true;
            lastDoorInteraction = Game.loop().getTicks();
            return;
          }
        }

        System.out.println("nach Gateway = " + GameManager.anzahlMonster);

        lastDoorInteraction = Game.loop().getTicks();
        Game.world().environment().remove(Fireball.instance());
        Game.world().environment().remove(ShatterShot.instance());
        SpellManager.state = 0;

        Game.world().loadEnvironment(pr.getName());

        currentMap = pr.getName();
        Game.world().camera().setFocus(Game.world().environment().getCenter());
        GameManager.spawnPlayer(Game.world().environment(), pr);
      }
    }

    for (Prop s : Spellcrates) {

      collider.setLocation(s.getCenter());
      collider.setCollision(false);
      collider.setName("collider");

      if (Player.instance().getCollisionBox().intersects(collider.getCollisionBox())) {
        Spellscroll spell = new Spellscroll(s.getName());
        Game.world().environment().add(spell);
        spell.setLocation(s.getX(), s.getY());

        s.die();
        s.setCollision(true);
      }
    }
    // Tutorial char interaction
    if (Player.instance().getBoundingBox().intersects(StoryChar.instance().getCollisionBox())
      && !StoryChar.instance().getTalking()
      && Game.world().environment().getMap().getName().equals("atrium")) {
      StoryChar.instance().setTalking(true);
      Game.world().environment().remove(Game.world().environment().getProp("marker"));
      StoryChar.instance().speak(Resources.strings().get("storychar.greet"));
      Game.loop().perform(8000, () ->
        StoryChar.instance().speak(Resources.strings().get("storychar.tut1"))
      );
      Game.loop().perform(16000, () ->
        StoryChar.instance().speak(Resources.strings().get("storychar.tut2"))
      );
      Game.loop().perform(24000, () ->
        StoryChar.instance().speak(Resources.strings().get("storychar.tut3"))
      );
      Game.loop().perform(32000, () ->
        StoryChar.instance().speak(Resources.strings().get("storychar.tut4"))
      );
      Game.loop().perform(40000, () -> {
          StoryChar.instance().speak(Resources.strings().get("storychar.tut5"));
          GameManager.spawnEnemy(Game.world().environment());
        }
      );
      Game.loop().perform(48000, () ->
        StoryChar.instance().speak(Resources.strings().get("storychar.tut6"))
      );
      Game.loop().perform(56000, () ->
        StoryChar.instance().speak(Resources.strings().get("storychar.tut7"))
      );
      Game.loop().perform(64000, () -> {
        StoryChar.instance().speak(Resources.strings().get("storychar.tut8"));
        StoryChar.instance().setTalking(false);
      });

    }
  }

  // called by key to render the Message
  public static void PickUpKey(String Name) {
    KeyPickUpName = Name;
    pickedUp = true;
  }
}
