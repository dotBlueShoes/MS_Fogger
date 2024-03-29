# Change Log

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [1.2.0] - ?

### Added
- ?

## [1.1.2] - 2021-05-07

### Added
- Fogger now overrides Dynamic Surroundings fog.
- Few default fog settings.

### Changed
- Few default fog settings.
- Cleared the project a little. Removed some unneeded code.

## [1.1.1] - 2021-03-22

### Added
- Support for constant fog setting (independant from view distance).
- Few new default config values.
- No restricitions to y-level order.
- No performance drawback for specifing non-existing current minecarft instance biomes.
- Possibility to space out array values in config using both spaces and tabs. No new-lines tho. 

### Changed
- Few default config default values.
- Config option "globalFog" name changed to "isFogGlobal".

### Removed
- Mistakenly left code that changed how list 3rd value behaved. yeah sorry.

## [1.1.0] - 2021-03-02

### Added
- Default Fog Setting. Instead of defining every single biome now it is possible to define a default Fog setting.
- Y-Level Dependant Fog Setting. Now u can define from which y-level-up does the fog apply.
- Fog Definitions. A config system to better organize fog settings. Checkout the config and wiki page for more info. 
- Github Wiki page.

### Changed
- Config option "globalFogMinIntensity" & "globalFogMaxIntensity" now works for both global and default fog setting.

### Removed
- Due to the implementation of fog definitions in config the previous "S:Biomes" list is now being removed.

## [1.0.1] - 2021-02-24

### Added

- Config option to set start/end of fog.
- Added github url to mcmod.info.
- Added images to project.

## Changed

- Config default values for both vanilla and biomesOPlenty.

## Removed

- Config default duplicates.

## [1.0.0] - 2021-02-21

### Added
- Moved the whole project to git/github.

- Added config to edit fog biome dependent parameters.

### Removed

- Fog Color support because optifine already does it and it does it better.

  

  

  
