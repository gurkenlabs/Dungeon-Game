package de.dungeongame.entities;

public interface IterableEnum<E extends Enum<E>> {

  int ordinal();

  default E next() {
    E[] ies = this.getAllValues();
    return ies[Math.floorMod(this.ordinal() + 1, ies.length)];
  }

  default E previous() {
    E[] ies = this.getAllValues();
    return ies[Math.floorMod(this.ordinal() - 1, ies.length)];
  }

  @SuppressWarnings("unchecked")
  private E[] getAllValues() {
    IterableEnum<E>[] ies = this.getClass().getEnumConstants();
    return (E[]) ies;
  }

}
