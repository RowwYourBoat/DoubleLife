# Double Life

### Introduction

If you don't feel like reading, watch the start of [this video](https://www.youtube.com/watch?v=UwFbtE4YS7g&ab_channel=Grian).
Double Life puts a twist on Minecraft Hardcore. It's intended to be played with 10 players or more, on a 700x700 map (vanilla, with a worldborder). This gamemode shouldn't be featured on a 24/7 server. Instead, 3 hour sessions should be hosted, so that all players can be online at once.

At the start of a season, every player gets assigned to a random soulmate. Everyone will have to find out who their soulmate is, because it won't be revealed by the plugin. For the rest of the season, their health will be linked to that player, and so will their amount of lives. Every player starts with three. Being on three lives makes you a green name, being on two makes you a yellow name and being on one makes you a red name. Red names are the only ones who are allowed to kill people.

### Custom Recipes

Because resources are quite limited in such a small world, there are custom recipes to make your life a lot easier. All recipes may be toggled individually within the configuration file.

### Setup

Before joining the server, please configure the plugin within the **config.yml** file. There's no need to restart the server after making changes to this file. Instead, you may run the command **/dl reload**.

Upon finding a good seed, navigate to where you want the center of the worldborder to be. Now, run the command **/dl setup**. This will resize the world border, set the world's spawnpoint at that location, and set the gamerules to their configured values **(config.yml)**.

Now that the server has been set up, the game can begin! Get your friends on, and run the command **/dl randomizepairs** to get started! If you want to spread the players out, use the command **/dl distributeplayers**.

### Configuration

Default values and documentation:
https://github.com/RowwYourBoat/DoubleLife/blob/master/src/main/resources/config.yml

### Permissions

**doublelife.admin -** Grants access to all Double Life related commands.

**doublelife.nopair -** Players with this permission won't be assigned to a pair. Handy for administrators who don't want to participate, or spectators.