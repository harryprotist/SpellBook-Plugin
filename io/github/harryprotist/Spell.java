package io.github.harryprotist;

import java.util.Stack;
import java.util.ArrayList;

import org.bukkit.*;

import io.github.harryprotist.SpellBook;
import io.github.harryprotist.spellfunction.SpellFunction;
import io.github.harryprotist.spellfunction.SpellObject;
import io.github.harryprotist.spellfunction.SpellContext;

public class Spell {

  public enum ReturnType {
    END, EXIT, ERROR
  }

  public static class SpellReturn {
    public ReturnType type;
    public int mana;
    public SpellReturn(ReturnType type, int mana) {
      this.type = type;
      this.mana = mana;
    }
  }

  private ArrayList<SpellFunction> program = new ArrayList<SpellFunction>();
  public void add(SpellFunction inst) {
    program.add(inst);
  }
  
  public SpellReturn run(SpellContext con) {

    Stack<SpellObject> stack = new Stack<SpellObject>();
    int mana = 0;

    try {
      for (SpellFunction inst : program) {
        if (inst instanceof SpellFunction.SfExit) {
          return new SpellReturn(ReturnType.EXIT, mana);
        }
        mana += inst.run(stack, con);    
      }
    } catch (Exception e) {
      con.player.sendMessage("Error: " + e.getMessage());
      return new SpellReturn(ReturnType.ERROR, mana);
    }
    return new SpellReturn(ReturnType.END, mana);
  }

}
