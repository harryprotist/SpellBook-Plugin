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
    public SpellContext con;
    public SpellReturn(ReturnType type, SpellContext con) {
      this.type = type;
      this.con = con;
    }
  }

  private ArrayList<SpellFunction> program = new ArrayList<SpellFunction>();
  public void add(SpellFunction inst) {
    program.add(inst);
  }
  
  public SpellReturn run(SpellContext con) {

    SpellReturn ret = new SpellReturn(ReturnType.END, con);

    try {
      for (SpellFunction inst : program) {
        if (inst instanceof SpellFunction.SfExit) {
          ret.type = ReturnType.EXIT; 
          break;
        }
        con.mana -= inst.cost(con);
        if (con.mana <= 0) {
          con.player.damage((double)(con.mana * (-1)), con.player);
          if (con.player.getHealth() > 0) {
            inst.run(con);
          }
          con.mana = 0;
          break;
        }
        inst.run(con);
      }
    } catch (Exception e) {
      if (e.getMessage().equals("EXIT")) {
        ret.type = ReturnType.EXIT;
      } else {
        con.player.sendMessage("Error: " + e.getMessage());
        ret.type = ReturnType.ERROR;
      }
    }

    return ret;
  }

}
