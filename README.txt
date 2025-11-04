# Catan Random Map Generator

A procedural map generator for a Catan-like hexagonal board game.

## Overview

This project generates random maps inspired by the board game Catan with the Seafarers expansion (compliant with the rules). It allows users to configure key parameters such as:

- Number of islands (within practical bounds)
- Number of main islands (starting points for players)
- Weight of main islands (size relative to other islands)
- Number of players (3 to 6), which influences the board size and the number of land/sea tiles

The generator places hexagonal tiles on the board with randomization applied at multiple levels:

- Distribution of land and sea tiles
- Distribution of terrain types and resource tokens
- Placement of number tokens with constraints (e.g., no adjacent 6 or 8 tokens)

After generating the map, the program performs minimal checks and adjustments to ensure the configuration respects game rules.

## Features

- Interactive GUI to adjust generation parameters and visualize the board
- Save/load functionality for maps using a custom `.map` format (editable manually if needed)
- Export the current map as a PNG image
- Configuration file persists user preferences with custom saving path and last used parameters (see Usage)
- Detailed logging of the generation process (visible when running from the command line)

## Usage

- Run the provided JAR file
- Use the settings menu to configure map parameters
- Generate, save, load, and export maps through the interface
- Configuration file automatically saves preferences between sessions
- At first launch, the user is asked to provide a saving path, which will be used as the default. The saving path can be changed manually inside the config file located at the default path (non-editable): `~/.catmap/config` (i.e., `${user.home}/.catmap/config` on your system). Alternatively, deleting the config file will trigger a re-prompt for the saving path on next launch.

## Technical Details

- Developed in Java 14 with Swing for the GUI
- Procedural generation logic balances randomness with rule constraints
- Map files are plain text, describing tiles and their attributes

## Notes

- If the configuration file is missing or corrupted, the program prompts for a save path or falls back to a default path.
- Logging is verbose to aid debugging and understanding of the generation process.
- The folder fileExamples coontains a sample of confing file, .map file where maps are stored and of a printed map.

# Legend

Numbered tiles:
- brown tiles -> argilla
- light green tiles -> wool
- grey tiles -> rock
- dark green tiles -> wood
- light blue -> river

Non-Numbered tiles: 
- blu tiles -> sea
- ocra tiles -> desert


How to launch:
Edit the launcher.vbs file: change the path of the launcher.bat file to the one of your pc. Then launch it.
