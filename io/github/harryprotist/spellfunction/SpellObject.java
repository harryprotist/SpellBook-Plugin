package io.github.harryprotist.spellfunction;

import io.github.harryprotist.Spell;

import org.bukkit.Location;

public abstract class SpellObject {
  
  public enum Type {
    NUMBER,
    STRING,
    SPELL,
    LOCATION 
  }

  public abstract Type getType();
  public abstract Object getValue(); 

  public String toString() {
    return getType().toString() + " " + getValue().toString();
  }

  public static class SpNumber extends SpellObject {

    public Type getType() { return Type.NUMBER; }
    public Object getValue() { return this.value; }

    public Object value;
    public SpNumber(Integer val) {
      this.value = (Object)val;
    } 
  }

  public static class SpString extends SpellObject {

    public Type getType() { return Type.STRING; }
    public Object getValue() { return this.value; }

    public Object value;
    public SpString(String val) {
      this.value = (Object)val; 
    }
  }

  public static class SpLocation extends SpellObject {

    public Type getType() { return Type.LOCATION; }
    public Object getValue() { return this.value; }

    public Object value;
    public SpLocation(Location val) {
      this.value = (Object)val; 
    }
  }

  public static class SpSpell extends SpellObject {
    public Type getType() { return Type.SPELL; }
    public Object getValue() { return this.value; }

    public Object value;
    public SpSpell(Spell val) {
      this.value = (Object)val; 
    }
  }
}
