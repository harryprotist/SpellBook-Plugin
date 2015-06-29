Spellcraft-Plugin
================

Contents
--------
1. Installation
2. Creating Runes
3. Casting Runes
4. Reference

1 Installation
============

All you need to do for installation is put the .jar in your plugins folder, and put "rune.txt" in your server folder.

2 Creating Runes
==============

What are runes?
---------------

Unlike other magic related minecraft plugins, where runes are either built in or in config files, Spellcraft allows you
to make your own runes by combining lots of small functions. 

These functions are combined in a 'rune,' which is a configuration of blocks that follows a pattern and certain rules, in order
to define a sort of procedure as to what your 'rune' should do.

For example, while a plugin like Runecraft might have you construct a
predefined rune in order to create an item that will send enemies flying, that same feat would be accomplished in Spellcraft
by creating a rune of: 'target sight' (glass) -> 'select entities' (mossy cobble) -> 'apply force upwards' (piston) -> 'apply force forwards' (piston)

What are runes made of?
-----------------------

Runes are made up of lots of 'functions' and 'arguments.' Just like in programming, the function describes what to do, and the
arguments give additional information as to how to do it. (assuming that this is a void function with only side-effects, but that's not important)

In Spellcraft, both functions and arguments are made up of blocks. Each part of the rune will resemble a function block with arguments stacked on top.
Which function each block corresponds to is listed in the Reference section. What each of the arguments means, and the order they go in is also listed
in the Reference section. On some functions, the last argument or two is optional. The arguments are read as their value---that is to say, if the argument
defines a certain aspect of the function, range for example, then a more valueble block will mean a longer range. rune.txt defines the values of all the blocks.
The amount of 'mana' (which is covered in the Casting Runes section) that a function consumes almost always depends on the arguments.
Some functions take no arguments.

As an example of a function with arguments, the 'target sight' function is a common function that has one argument, the range. 
That means that if you want to target where the player is looking, you put down the block that corresponds to target sight (glass),
and then put one block on top of it. If you want very short range, you could put something cheap like dirt or cobble on top, and if you
want maximum range, you would use something valuble like a gold block. 

How are runes structured?
-------------------------

Each rune has a center made of a block (the type doesn't matter), with mossy cobblestone on each side of it. This is the one
element of structure that is common to every rune.

The rest of the rune is structured in sort of 'shells,' which are groups of 4 positions, each of which is supposed to have a function placed
in it. If a position in a shell is blank, the rune will stop. Every shell is on the same y, which is the same y as the center of the rune.
 The first shell is one block outward from the mossy cobble blocks in the center.
Depending on what those blocks are (or more importantly, what arguments they have but I'll get into that in a sec), the next shell might be
two blocks outward from the mossy cobble and one block clockwise. 

Each shell is made up of 4 blocks: one on the +x, -x, +z, and -z. Each shell also has an outwardness and a clockwiseness. If a shell is 1 outward and
1 clockwise, then that is where you place each of the 4 functions in your shell relative to the mossy cobble blocks on their respective sides of the rune. 

Shells can repeat as well, which is sometimes the desired effect and other times
isn't, and can be very dangerous. For example, the third shell could be the same position as the first shell, which would mean that the rune would just repeat the first shell
and everything after it again, until it loops back again and keeps going until it either hits an 'exit' function (which is a very complicated thing that
you shouldn't worry about) or until the source of mana runs out (the reason that this is dangerous is covered more in the Casting Runes section).

In order to determine what the next shell is, look at the last shell that you've built. If the number of arguments on the function that is on the
+x side of the rune is greater than the number of arguments on the -x side of the rune, then the next shell will be one more outward. If the -x has more
arguments than the +x, the next layer is one inward. If the two sides have the same arguments, then the next shell is the same amount outward.
The same rule applies to the z: if the +z has more arguments then the next shell is one more clockwise; if the -z has more, then the next shell is
one more counterclockwise, and if the two have the same amount, then the next shell is the same amount of clockwise.

3 Casting Runes
================

What is mana?
-------------

In order to do anything in Spellcraft, you first need mana. Mana is what powers all runes. When a function in a rune is excecuted, then it consumes an
amount of mana, based on a function. The cost of every function is different; some are linear based on their arguments, some cost the value of their arguments
raised to the third power.

All players have at least 1 mana. If you run out of mana, you die, which is why you often have to be careful when casting expensive runes.
You can check how much mana you have with `/mana` or `/m` .

There are 2 ways to get Mana, and both of them use the same rune. The 'absorbtion rune,' which has its own name because it's so common, is a rune made of
4 absorbtion functions with no arguments. If you'll remember from last section, the next shell of a rune is based on the arguments of the last shell, so
if there are 4 functions with no arguments, the absorbtion rune is just an infinite loop of the same function: the absorbtion fuction.

You might be wondering what the absorbtion function does. It costs 1 mana (+1 mana that every function has to consume), and gives 1 mana to the caster.
This doesn't sound very useful, because surely spending 2 mana to give yourself 1 is a waste of time. That's where the different methods of casting runes.
comes in.

What are the different ways of casting runes?
----------------------------------------------

There are 3 different ways of casting runes, one of which has 2 slightly differnt variations. So we'll just say that there are 4 ways to cast: 'activate and cast,'
'incantation and cast,' 'imbue and use,' 'and perform.'

Activate and cast
-----------------

Activate and cast is the easiest to do, arguably. Right click the center of your rune, and then type `/mana on` or `/m o` (although actually this toggles). It should say
`setting mana to true`. If it doesn't, just keep doing the command until it does. You can leave your mana like this, but I advice running the command again to turn off your
mana after you're done casting a few runes, so you don't accidentaly cast and possibly die while doing something else. 

Once You've got your mana set to true though, the next
step is to actually cast your rune. Sneak and LClick at the same time, and it will cast the rune of the rune that you right clicked the center of most recently. This draws from
your personal mana, so unless you've got plenty of mana, this might kill you. If the rune you clicked loops back on itself, it definitly will kill you.

But that aside, having to walk up to your runes every time you want to cast a rune isn't very convenient, so that leads us to the next method: 'incantation and cast.'

Incantation and cast
--------------------

Now, the only thing different about this method is that instead of clicking runes, you register and then cast incantations.
While standing next to your rune, type `/mana register <DESIRED INCANTATION>` or `/m r <DESIRED INCANTATION`. If the incantation is taken, it won't work.
This will take your rune and store it in a master list that the server keeps, and then by recalling your incantation you can get the rune from anywhere as though you had
clicked the center. However, there's no command to get a list of incantations (otherwise people couldn't have secret incantations), so make sure to remember yours.
Registering an incantation is free and costs no mana, but don't spam it and take all the incantations other people might want.

If you want to cast from an incantation that you registered, then just type `/mana cast <INCANTATION>`, or `/m c <INCANTATION>`. Then just make sure that your mana is set
to true, and do the same Sneak+LClick that you did for the Activate and Cast method. Any player can cast an incantation that was
registered by any other player, so you can share your incantations with other people on the server or even keep a library of all the different incantations people have registered
and their uses.

Imbue and use
-------------

The Imbue and use method is probably the most versatile. By holding and *item* in your hand (a block won't work) and typing `/mana imbue <AMOUNT>` or `/m i <AMOUNT`, you
will take that amount of mana from your own mana and put it in the item. If you were successful, then your item will have some purple lore text, the second line of which
is the amount of mana you put in. You can't put all your mana in an item, but so long as you have at least 1 mana left after, any other amount will work. 

The rune that you were looking at the center of when you did the imbue command is the rune that's bound to your item. Unlike the Activate/Incantation and cast methods, the amount
of mana that you gave the item is now the mana source used when you use the item. This means that if the item runs out of mana, you won't die, which can be useful for testing and for
items which you plan to use regularly. That also means that it can be imbued with an infinite loop, like the absorbtion rune. You might notice that now we've got a way to store mana;
put some in your item, and then use it, and the item's mana will all be consumed and given to the caster, you (although because it's 2:1, you do lose half of the mana you put in).

You can add more mana to the item by looking at the same rune that you originally imbued the item with, and typing the same command. If you
try to imbue an item with a different rune, it will clear both the original rune and all of its mana.

In order to use your item, just LClick with it. It doesn't matter whether you're Sneaking, and it also doesn't matter whether you've got mana set to true or false.
Any player can use an imbued item, and you can just toss your item to other players or store it in a chest.

Perform
-------

Performing a ritual essential but often the least useful way to cast a rune. In order to perform a ritual, place a block in the center of your rune so that it is touched on
all 4 sides by mossy cobble and then look at the center and type `/mana perform` or `/m p`.

Once the ritual is performed, the center block is consumed and the value of the center block multiplied by 10 is used as the rune's mana source. Because this requires no
mana from the player to start out, and doesn't use the player's mana source, it can be used to power infinite loop runes like the absorbtion rune, and is therefore the only
way to gain mana for your personal mana source when you have none.

You can also take advantage of the multiplication of the center blocks value in order to do something which requires a very large amount of mana, such as creating a rare item,
but often it's easier to just accumulate mana through lots of blocks instead of trying to cast an expensive rune with one. Another disadvantage of performing rituals is that
it needs to be done right next to the rune you want to cast.

4 Reference
==========

GLASS			1
---------------
Description: Targets Where the player is looking.

Arguments: Range, determines how far to target.

Cost: Linear with Range.

NETHERRACK		2
---------------
Description: Sets the targeted block on fire if it's air.
Arguments: None.
Cost: Constant.

STONE			3
---------------
Description: Moves the targeted location down by 1.

Arguments: None.

Cost: Constant.

LOG			4
---------------
Description: Moves the targeted location up by 1.

Arguments: None.

Cost: Constant.

SAND			5
---------------
Description: Moves the targeted location away by one.

Arguments: None.

Cost: Constant.

SANDSTONE		6
---------------
Description: Moves the targeted location closer by one.

Arguments: None.

Cost: Constant.

CLAY			7
---------------
Description: Moves the targeted location clockwise/right by one.

Arguments: None.

Cost: Constant.

BRICK			8
---------------
Description: Moves the targeted location countclockwise/left by one.

Arguments: None.

Cost: Constant.

TNT			9
---------------
Description: Creates an explosion at the targeted location.

Arguments: Power, power of the explosion.

Cost: Based on Power squared

FIRE			10
---------------
Description: Creates a fiery explosion at the targeted location.

Arguments: Power, power of the explosion.

Cost: Based on Power squared

WORKBENCH		11
---------------
Description: Creates a block at the targeted location, if air.

Arguments: Value, the value corresponding to the block that will be placed.

Cost: Based on Value cubed

COBBLESTONE		12
---------------
Description: Breaks the block at the targeted location.

Arguments: (optional) Type, the value corresponding to the only type of block that should be broken.

Cost: Linear to the durability of the block broken, or constant if the argument is provided and the block is not the right type.

EMERALD_ORE		13
---------------
Description: Sets memory.

Arguments: Type, the type of block that will have its value replaced by Value for use in the rune; Value, the value that will be stored in Type.

Cost: Constant.

GLOWSTONE		14
---------------
Description: Adds to memory.

Arguments: Type, the type of block that will have its value added to (must have been previously set by Set Memory).

Cost: Constant.

SOUL_SAND		15
---------------
Description: Subtracts from memory.

Arguments: Type, the type of block that will have its value subtracted from (must have been previously set by Set Memory).

Cost: Constant.

REDSTONE_BLOCK		16
---------------
Description: Exits if argument is 0.

Arguments: Value, if it has been set to 0 then the rune will exit.

Cost: Constant.

GOLD_BLOCK		17
---------------
Description: Gives mana from source to caster.

Arguments: None.

Cost: Constant.

GRAVEL			18
---------------
Description: Fires an arrow at the targeted location.

Arguments: Power, determines how powerful the arrow is.

Cost: Linear to Power.

PISTON_BASE		19
---------------
Description: Applies force to selected entities.

Arguments: Direction, one of the Shift Direction functions, specifies direction; Force, the amount of force.

Cost: Linear to Force, multiplied by entities.

MOSSY_COBBLESTONE	20
---------------
Description: Selects entities at targeted location.

Arguments: Radius, radius to select in.

Cost: Based on Radius cubed.

NETHER_BRICK		21
---------------
Description: Apply potion effect to selected entities.

Arguments: Type, a value that corresponds to a potion effect; Duration, the duration.
  * Glass: Speed. Init, per second: 1000, 10.
  * Soul Sand: Slow. Init, per second: 2000, 10.
  * Iron Block: Fast digging. Init, per second: 10000, 10.
  * Dirt: Slow digging. Init, per second: 2000, 10.
  * Log: Increase damage. Init, per second: 10000, 25.
  * Gold Block: Instant healing. Init, per second: 20000, 0.
  * TNT: Instant harming. Init, per second: 40000, 0.
  * Leaves: Jump. Init, per second: 1000, 5.
  * Mossy Cobble: Confusion. Init, per second: 10000, 40.
  * Glowstone: Regeneration. Init, per second: 50000, 100.
  * Obsidian: Resistance. Init, per second: 25000, 25.
  * Netherrack: Fire resistance. Init, per second: 10000, 10.
  * Ice: Water Breathing. Init, per second: 10000, 10.
  * Thin Glass: Invisibility. Init, per second: 9000, 5.
  * Pumpkin: Blindness. Init, per second: 8000, 30.
  * Jack-o-Lantern: Night Vision. Init, per second: 1000, 5.
  * Melon: Hunger. Init, per second: 1000, 10.
  * Clay: Weakness. Init, per second: 3000, 10.
  * Web: Poison. Init, per second: 12000, 10.
  * Ender Stone: Wither. Init, per second: 10000, 100.

Cost: Based on an initial cost plus linear to Duration based on a cost/second, multiplied by entities.

ENDER_STONE		22
---------------
Description: Teleports entities to targeted location.

Arguments: None.

Cost: Based on distance squared from each entity to location.


IRON_BLOCK		23
---------------
Description: Strikes lightning at targeted location.

Arguments: None.

Cost: Constant.
