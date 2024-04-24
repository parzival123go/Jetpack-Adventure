<h1>Introduction</h1>
Jetpack Adventure is an exhilarating side-scrolling action game developed by Sanjeev Praboocharan and Richard Rattansingh. This final report encapsulates the journey from conceptualization to implementation of the game, highlighting its features, gameplay mechanics, and an object-oriented model of the actual classes developed for the game.

<h1>Game Concept</h1>
Jetpack Adventure immerses players in an adrenaline-fueled experience, where they control a daring adventurer equipped with a jetpack. Set in a dynamic world filled with obstacles, players must navigate through multiple levels, collecting coins, power-ups and avoiding hazards to achieve the highest score possible.

<h1>Gameplay Overview</h1>
The game combines intuitive controls, engaging mechanics and dynamic level generation to deliver a captivating experience. Players control the character's vertical movement using keyboard input, while horizontal movement is automatic. The primary objective is to progress through levels, avoiding obstacles and collecting coins to increase the score. Health is set to a limit of 5, collisions with obstacles result in a loss of life. Power-ups provide temporary enhancements, adding depth to the gameplay.

<h1>>Implemented Features</h1
Key features include:

<h2>Player Character:</h2> A courageous adventurer with a jetpack, capable of vertical movement controlled by the player.
<h2>Obstacles:</h2> Various obstacles such as lasers and missiles pose challenges to the player, requiring strategic navigation.
<h2>Coins:</h2> Shiny metallic tokens scattered throughout levels serve as the primary collectible item, contributing to the player's score.
<h2>Power-ups:</h2> Temporary enhancements including speed boosts, double coins and invincibility elevate the player character's abilities.
<h2>Dynamic Level Generation:</h2> Levels are dynamically generated to provide unique experiences, ensuring increasing competitiveness.

<h1>Gameplay (How to Play)</h1>

The player begins at level 1 with a set number of lives(5) and a starting coin score and distance traveled of zero.
The character automatically moves horizontally at a constant speed, while the player controls vertical movement via keyboard input. The longer the player presses the spacebar the longer the player character will stay in the air, the maximum height is reached at the top of the screen, the player cannot go above this limit, when the spacebar is released, the player will fall back to the ground.
The objective is to navigate through the levels, avoiding obstacles and collecting coins to increase the player's score.
Colliding with obstacles results in the loss of a life. A life is lost each for a collision with an obstacle (missile/laser). Running out of lives ends the game.
Progressing through the levels is based on covering a specific amount of distance.
Power-ups appear randomly throughout the level and provide temporary advantages to the player when collected. (Speed Boost increases the player character's movement speed along with the ability to be immune to damage from obstacles for a period, Double Coins doubles the value of coins collected for a period and Invincibility results in the player character immune to damage from obstacles for a period of time.)
Each level presents unique challenges, with obstacles becoming more difficult to avoid and navigate around as the player progresses (Obstacles increase in speed).
The primary goal of a player is to vertically maneuver the character, with the use of the spacebar key, from the obstacles, collecting power ups and coins to achieve the highest score possible (Coins collected & Distance traveled).

<h1>>Object Oriented Model</h1


