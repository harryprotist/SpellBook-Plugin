package io.github.harryprotist;

import java.util.*;

public class Util {
  /* can't believe I have to write my own String.join */
  public static String join(String delim, Collection<?> col) {
    String s = "";
    Iterator<?> iter = col.iterator();
    if (iter.hasNext()) {
      s += iter.next().toString();
    }
    while (iter.hasNext()) {
      s += delim + iter.next().toString(); 
    }
    return s;
  }
}
