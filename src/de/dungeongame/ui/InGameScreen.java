package de.dungeongame.ui;

import de.gurkenlabs.litiengine.gui.screens.GameScreen;


public class InGameScreen extends GameScreen {

  public static final String NAME = "INGAME-SCREEN";
  private HUD hud;

  public InGameScreen() {
    super(NAME);
  }

  @Override
  protected void initializeComponents() {
    this.hud = new HUD();
    this.getComponents().add(this.hud);
  }

}
