# Theseus

A random escape game generator (3D video game and Minecraft plugin). For **the first version**, I decided to focus on a specific task : generate a random craft enigma in Minecraft in order to escape of a room.

## How to proceed ?

First of all, we need data : all of Minecraft recipes !
I stored all of it into this [file](src/fr/martdel/res/recipes.xml).

Next we build a list of linked recipes and choose a final item which end the game.
To end the program, we collect all of start recipes requirements and give them to the player.
