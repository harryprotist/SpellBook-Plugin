package io.github.harryprotist;

import java.util.regex.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;

import io.github.harryprotist.spellfunction.SpellFunction;

public class Book {

  private static SpellFunction parseLine(String code) throws Exception {
    /* functions start with letters */
    if (Pattern.matches("^[a-zA-Z].*", code)) {
      return (SpellFunction.getFunction(code));

    /* numbers start with digits (obviously) */
    } else if (Pattern.matches("^\\d.*", code)) {
      return (new SpellFunction.SfNumber(new Integer("0" + code)));

    /* strings start with quotes */
    } else if (Pattern.matches("^\".*", code)) {
      return (new SpellFunction.SfString(code.replaceAll("\"","")));
    
    /* otherwise syntax error */
    } else {
      throw new Exception("Parser Error in \"" + code + "\"!"); 
    }
  }
  private static Spell parseLines(String[] lines) throws Exception {
    Spell program = new Spell();
    for (int i = 0, j = 0; i < lines.length; i++) {

      if (lines[i].length() > 0 && lines[i].equals("open")) {
        for (j = i; j < lines.length; j++) {
          if (lines[j].equals("close")) break;
        }
        if (j == i) throw new Exception("Unclosed \'open\'");
        program.add(new SpellFunction.SfSpell(
          parseLines(Arrays.copyOfRange(lines, (i + 1), j))
        ));
        i = (j + 1);
      } else {
        program.add(parseLine(lines[i].trim()));
      }
    } 
    return program;
  }
  
  public static Spell parse(String code) throws Exception {
    String[] lines = code.split("\\s*[:;]\\s*");
    for (int i = 0; i < lines.length; i++) {
      lines[i] = lines[i].trim()
        .replaceAll("[^\\p{ASCII}]", "")
        .replaceAll("^[\\s0]*","")
        .trim();
    }
    return parseLines(lines);
  }
}
