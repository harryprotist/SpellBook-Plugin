package io.github.harryprotist.spellfunction;

import java.lang.annotation.*;
import java.lang.Class;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;

import org.bukkit.*;
import org.bukkit.util.*;
import org.bukkit.material.*;

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

  public abstract int run(Stack<SpellObject> stack, SpellContext con) throws Exception;

  public static class SfNumber extends SpellFunction {
    private Double num;
    public SfNumber(Double num) {
      this.num = num;
    }
    public int run(Stack<SpellObject> stack, SpellContext con) 
    throws Exception {
      stack.push(new SpellObject.SpNumber(num));
      return 1;
    }
  }
  public static class SfString extends SpellFunction {
    private String str;
    public SfString(String str) {
      this.str = str;
    }
    public int run(Stack<SpellObject> stack, SpellContext con)
    throws Exception {
      stack.push(new SpellObject.SpString(str));
      return 1;
    }
  }
  public static class SfSpell extends SpellFunction {
    private Spell spell;
    public SfSpell(Spell spell) {
      this.spell = spell;
    }
    public int run(Stack<SpellObject> stack, SpellContext con)
    throws Exception {
      return spell.run(con).mana;
    }
  }

  @SpellFun(name = "exit")
  public static class SfExit extends SpellFunction {
    public int run(Stack<SpellObject> stack, SpellContext con)
    throws Exception {
      return 0;
    } 
  }

  @SpellFun(name = "dup")
  public static class Dup extends SpellFunction {
    public int run(Stack<SpellObject> stack, SpellContext con)
    throws Exception {
      SpellObject so = stack.pop();
      stack.push(so);
      stack.push(so);
      return 1;
    }
  }
  
  @SpellFun(name = "add")
  public static class Add extends SpellFunction {
    public int run(Stack<SpellObject> stack, SpellContext con)
    throws Exception {
      SpellObject so1 = stack.pop();
      SpellObject so2 = stack.pop();
      System.out.println(so1.getValue() == null);
      if (so1.getType() == SpellObject.Type.NUMBER &&
          so2.getType() == SpellObject.Type.NUMBER) {
        stack.push(new SpellObject.SpNumber((Double)so1.getValue() + (Double)so2.getValue()));
      } else if (so1.getType() == SpellObject.Type.STRING &&
                 so2.getType() == SpellObject.Type.STRING) {
        stack.push(new SpellObject.SpString((String)so1.getValue() + (String)so2.getValue()));
      } else {
        throw new Exception("Add: Improper types");
      }
      return 1;
    }
  }

  @SpellFun(name = "neg")
  public static class Neg extends SpellFunction {
    public int run(Stack<SpellObject> stack, SpellContext con)
    throws Exception {
      SpellObject so = stack.pop();
      if (so.getType() == SpellObject.Type.NUMBER) {
        stack.push(new SpellObject.SpNumber((Double)so.getValue() * (-1.0)));
      } else {
        throw new Exception("Neg: Improper type"); 
      }
      return 1;
    }
  }

  @SpellFun(name = "log")
  public static class Log extends SpellFunction {
    public int run(Stack<SpellObject> stack, SpellContext con)
    throws Exception {
      con.player.sendMessage(stack.pop().getValue().toString());
      return 1;
    }
  } 

  @SpellFun(name = "target")
  public static class Target extends SpellFunction {
    public int run(Stack<SpellObject> stack, SpellContext con)
    throws Exception {
      SpellObject so = stack.pop();
      if (so.getType() == SpellObject.Type.NUMBER) {

        Set<Material> transparent = new HashSet<Material>();
        transparent.add(Material.AIR);

        stack.push( new SpellObject.SpLocation(
          con.player.getTargetBlock(
            transparent,
            ((Double)so.getValue()).intValue()
          ).getLocation()
        ));
        return (new Double((Double)(so.getValue()) / 10.0)).intValue() + 1;
      } else {
        throw new Exception("Target: Improper type");
      }
    }
  } 

  @SpellFun(name = "arrow")
  public static class Arrow extends SpellFunction {
    public int run(Stack<SpellObject> stack, SpellContext con)
    throws Exception {
      SpellObject pow = stack.pop(); 
      SpellObject loc = stack.pop(); 
      if (pow.getType() == SpellObject.Type.NUMBER &&
      loc.getType() == SpellObject.Type.LOCATION) {

        Location pLoc = con.player.getLocation();   
        pLoc.add(0.0, 1.6, 0.0);
        pLoc.add(con.player.getLocation().getDirection().normalize());

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
          (float)((Double)pow.getValue()).doubleValue(),
          spread
        );
        return (new Double(64.0 + (Double)pow.getValue())).intValue();
      } else {
        throw new Exception("Arrow: Improper types");          
      }
    }
  }
}
