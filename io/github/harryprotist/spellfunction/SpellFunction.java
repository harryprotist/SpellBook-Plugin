package io.github.harryprotist.spellfunction;

import java.lang.annotation.*;
import java.lang.Class;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;

import org.bukkit.*;
import org.bukkit.util.*;
import org.bukkit.material.*;
import org.bukkit.block.*;

import io.github.harryprotist.spellfunction.*;
import io.github.harryprotist.*;

public abstract class SpellFunction {
  
  @Retention(RetentionPolicy.RUNTIME)
  private @interface SpellFun {
    public String name();
  }

  public static SpellFunction getFunction(String name) throws Exception {
    for (Class<?> innerClass : SpellFunction.class.getDeclaredClasses()) {
      if (innerClass.isAnnotationPresent(SpellFun.class) &&
      ((SpellFun)innerClass.getAnnotation(SpellFun.class))
      .name().equalsIgnoreCase(name)) {
        return (SpellFunction)innerClass.getConstructor().newInstance();
      }
    }
    throw new Exception("Function Error: Function \"" + name + "\" not found!");
  }

  public abstract void run(SpellContext con) throws Exception;
  public abstract int cost(SpellContext con);

  public static class SfNumber extends SpellFunction {
    private Integer num;
    public SfNumber(Integer num) {
      this.num = num;
    }
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con) throws Exception {
      con.stack.push(new SpellObject.SpNumber(num));
    }
  }
  public static class SfString extends SpellFunction {
    private String str;
    public SfString(String str) {
      this.str = str;
    }
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con)
    throws Exception {
      con.stack.push(new SpellObject.SpString(str));
    }
  }
  public static class SfSpell extends SpellFunction {
    private Spell spell;
    public SfSpell(Spell spell) {
      this.spell = spell;
    }
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con) throws Exception {
      con.stack.push(new SpellObject.SpSpell(spell));
    }
  }

  @SpellFun(name = "exit")
  public static class SfExit extends SpellFunction {
    public int cost(SpellContext con) { return 0; }
    public void run(SpellContext con) throws Exception {} 
  }

  @SpellFun(name = "dup")
  public static class Dup extends SpellFunction {
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con) throws Exception {
      SpellObject so = con.stack.pop();
      con.stack.push(so);
      con.stack.push(so);
    }
  }
  
  @SpellFun(name = "add")
  public static class Add extends SpellFunction {
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con) throws Exception {
      SpellObject so2 = con.stack.pop();
      SpellObject so1 = con.stack.pop();
      if (so1.getType() == SpellObject.Type.NUMBER &&
          so2.getType() == SpellObject.Type.NUMBER) {
        con.stack.push(new SpellObject.SpNumber(
          (Integer)so1.getValue() + (Integer)so2.getValue())
        );
      } else if (so1.getType() == SpellObject.Type.STRING &&
                 so2.getType() == SpellObject.Type.STRING) {
        con.stack.push(new SpellObject.SpString(
          (String)so1.getValue() + (String)so2.getValue())
        );
      } else {
        throw new Exception("Add: Improper types");
      }
    }
  }

  @SpellFun(name = "neg")
  public static class Neg extends SpellFunction {
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con) throws Exception {
      SpellObject so = con.stack.pop();
      if (so.getType() == SpellObject.Type.NUMBER) {
        con.stack.push(new SpellObject.SpNumber((Integer)so.getValue() * (-1)));
      } else {
        throw new Exception("Neg: Improper type"); 
      }
    }
  }

  @SpellFun(name = "log")
  public static class Log extends SpellFunction {
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con) throws Exception {
      con.player.sendMessage(con.stack.pop().getValue().toString());
    }
  } 

  @SpellFun(name = "target")
  public static class Target extends SpellFunction {
    public int cost(SpellContext con) { return 15; }
    public void run(SpellContext con) throws Exception {
      SpellObject so = con.stack.pop();
      if (so.getType() == SpellObject.Type.NUMBER) {

        Set<Material> transparent = new HashSet<Material>();
        transparent.add(Material.AIR);

        con.stack.push( new SpellObject.SpLocation(
          con.player.getTargetBlock(
            transparent,
            ((Integer)so.getValue())
          ).getLocation()
        ));
      } else {
        throw new Exception("Target: Improper type");
      }
    }
  } 

  @SpellFun(name = "arrow")
  public static class Arrow extends SpellFunction {
    public int cost(SpellContext con) {
      return (64 + (Integer)con.stack.peek().getValue());
    }
    public void run(SpellContext con) throws Exception {
      SpellObject pow = con.stack.pop(); 
      SpellObject loc = con.stack.pop(); 
      if (pow.getType() == SpellObject.Type.NUMBER &&
      loc.getType() == SpellObject.Type.LOCATION) {

        Location pLoc = con.player.getLocation();   
        pLoc.add(0.0, 1.6, 0.0);
        pLoc.add(con.player.getLocation().getDirection());

        Location tLoc = ((Location)(loc.getValue())).clone();
        tLoc.add(0.5, 0.5, 0.5);

        Vector dir = new Vector();
        dir.setX(tLoc.getX() - pLoc.getX());
        dir.setY(tLoc.getY() - pLoc.getY());
        dir.setZ(tLoc.getZ() - pLoc.getZ());
        dir.normalize();

        int spread = 3;

        con.player.getWorld().spawnArrow(
          pLoc,
          dir,
          (float)((Integer)pow.getValue() / 10),
          spread
        );
      } else {
        throw new Exception("Arrow: Improper types");          
      }
    }
  }

  @SpellFun(name = "if")
  public static class If extends SpellFunction {
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con) throws Exception {
      SpellObject code = con.stack.pop();
      SpellObject expr = con.stack.pop();

      if (code.getType() == SpellObject.Type.SPELL &&
      expr.getType() == SpellObject.Type.NUMBER &&
      (Integer)(expr.getValue()) != 0 &&
      (((Spell)(code.getValue())).run(con).type
      == Spell.ReturnType.EXIT)) {
        throw new Exception("EXIT");    
      }
    }
  }

  @SpellFun(name = "equals")
  public static class Equals extends SpellFunction {
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con) throws Exception {
      SpellObject so2 = con.stack.pop();
      SpellObject so1 = con.stack.pop();
      if (so1.getValue().equals(so2.getValue())) {
        con.stack.push(new SpellObject.SpNumber(1));
      } else {
        con.stack.push(new SpellObject.SpNumber(0));
      }
    }
  } 

  @SpellFun(name = "shift")
  public static class Shift extends SpellFunction {
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con) throws Exception {
      int dist = (Integer)(con.stack.pop().getValue()); 
      String dir = (String)(con.stack.pop().getValue());
      Location loc = (Location)(con.stack.pop().getValue());
      int forward = 0, right = 0, up = 0;
      switch (dir) {
        case "forward": forward = dist; break;
        case "back": forward = -dist; break;
        case "left": right = -dist; break;
        case "right": right = dist; break;
        case "up": up = dist; break;
        case "down": up = -dist; break;
      }
      double x, z;
      Vector v = con.player.getLocation().getDirection().normalize();
      if (Math.abs(v.getX()) > Math.abs(v.getZ())) {
        if (v.getX() > 0) {
          x = forward;
          z = right;
        } else {
          x = -forward;
          z = -right;
        }
      } else {
        if (v.getZ() > 0) {
          x = -right; 
          z = forward;
        } else {
          x = right;
          z = -forward;
        }
      }
      loc.add(x, up, z);
      con.stack.push(new SpellObject.SpLocation(loc));
    } 
  }

  @SpellFun(name = "break")
  public static class Break extends SpellFunction {
    public int cost(SpellContext con) {
      return con.player.getWorld()
        .getBlockAt((Location)(con.stack.peek().getValue()))
        .getType()
        .getMaxDurability();
    }
    public void run(SpellContext con) {
      Location loc = (Location)(con.stack.pop().getValue());
      Block block = con.player.getWorld().getBlockAt(loc);
      if (block.getType() != Material.BEDROCK) {
        block.breakNaturally(); 
      }
    }
  }

  @SpellFun(name = "loop")
  public static class Loop extends SpellFunction {
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con) throws Exception {
      SpellObject code = con.stack.pop();

      if (code.getType() == SpellObject.Type.SPELL) {
        while (((Spell)(code.getValue())).run(con).type 
          != Spell.ReturnType.EXIT) {}
      }
    }
  }

  @SpellFun(name = "drop")
  public static class Drop extends SpellFunction {
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con) throws Exception {
      con.stack.pop();
    }
  } 

  @SpellFun(name = "swap")
  public static class Swap extends SpellFunction {
    public int cost(SpellContext con) { return 1; }
    public void run(SpellContext con) throws Exception {
      SpellObject a = con.stack.pop();
      SpellObject b = con.stack.pop();
      con.stack.push(a);
      con.stack.push(b);
    }
  } 
}
