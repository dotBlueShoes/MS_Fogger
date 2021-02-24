## MS_Fogger          
[![GitHub license](https://img.shields.io/github/license/dotBlueShoes/MS_Fogger.svg)](https://github.com/dotBlueShoes/MS_Fogger/blob/master/LICENSE.txt)
[![GitHub tag](https://img.shields.io/github/tag/dotBlueShoes/MS_Fogger.svg)](https://github.com/dotBlueShoes/MS_Fogger/tags)

[![CurseForge downloads](http://cf.way2muchnoise.eu/full_449209_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/fogger)

**Fogger** is a modification for **minecraft** currently for version **1.12.2** only. The mod in it's current state cant offer much however it will give you more control over the fog then vanilla. It Gives you the ability to change where the fog starts and where it ends. *This setting can be changed for every biome.* 

It is also possible to apply a "globalFog" that works biome independent.

### How it works

You can switch from using biome dependant fog to global fog via config. Same goes for defining new and changing their values.

Simply edit the config file. First you'll need to run the modification at least once so the config file generates.
Find the 'config' folder in your minecraft instance and search for file called "fogger.cfg".
Then open the file with any text-editor like "notepad".

To add a biome to the "S:Biomes" list enter a new line and write the values in the folowing syntax. Remember to include all the needed values and watch out for typos.

{ Mod/Vanilla:BiomeName } { WhereTheFogStarts } { WhereTheFogEnds }

 - *Note that when the biome is not being defined in the list and the globalFog ain't set to true then the vanilla fog will be applied.*

 - *Make sure u're not defining the biome already as this will slow down the mod.*

 - *It is recommended to not over specify biomes. Simply specify the biomes you're using in your current **minecraft** instance. If u're not using **Biomes'OPlenty** biomes feel free to remove them.* 

 - *For comparision the vanilla fog values are* 0.75 *for fog to start and* 1.00 *for fog to end.*

### Mod Compatibility

I am not aware of any incompatibility. this mod works as intended with **Optifine**, **Phosphor**, **HardcoreDarkness**, **SereneSeasons**, let me know via curseforge comment section if theres a conflict with any more known mod.

*It does work with shaders. However **Optifine** gives u an option over fast fog and fancy fog and for some reason some shaders are locked on fast fog setting.*

### Fog Colors & Optifine Recommendation

It is highly **Recommended** to use this mod alongside **Optifine** and ***ResourcePacks** that support biome dependent fog colors introduced by **optifine mcpacher***. **Optifine** does Colors so well that i don't plan to implement that logic myself into the mod.

***There will be a link in future to such resourcepack crated by myself.***

### Soon

- I plan to expend this idea and give possibility to change these values depending on the current world weather, time and player y-level position.
