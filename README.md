# AzureV316
AzureMS v316 KMS, 

i will update it and rewrite it from scratch later on to get a better understanding about everything.

Contributors (growing list)
- Dipi 
- Brandon
- Desc
- Kookiie

---
## Features:
- **Custom NPCs**
  - **Cash NPC**: allows searching for cash equipment via keyword - accessible in lobby (`@town`)
  - **Statted Cash NPC**: Similar to cash NPC, but consumes JCoins - accessible in boss lobby (`@boss`)
  - **Warp NPC** - accessible in lobby (`@town`)
  - **Shops**: Class equipment shops, Lionheart EQ shop, Consumables shop, etc. - accessible in custom menu
  - **Meso Exchange**: buys STs from players for meso. - accessible in lobby (`@town`)
  - **Blue Orb shop** - accessible in lobby (`@town`)
  - **Pet Shop**: (Orchid) sells permanent pet and pet equipment for Purple Orbs. - accessible in lobby (`@town`)
  - **Styler** - accessible in lobby (`@town`)
  - **DPM tester** - accessible in lobby (`@town`)
  - **Jump Quest NPC** - accessible in lobby (`@town`)
  - **DP and VP shop** - accessible in lobby (`@town`)
  - **Item Transfer NPC (Duey)** - accessible in lobby (`@town`)
  - **Cash equipment disposal NPC** - accessible in lobby (`@town`)
  - **And more!**
- **Custom Menu** - accessible via the grave key \`
- **Additonal Damage system**
- **Rebirth System** - with rewards and shop
- **Custom Currency** - Blue and Purple orbs, Justice Coins, etc.
- **Custom Bossing system**
- **Universal Smega**: use a Tilda at the start of a message in the chatbox to send a smega

## Related Features/Additions:
- In Addition to the open source, Azure Team has some other Maplestory (AzureMS) based tools for private servers.
  - ~[Discord Bot](https://github.com/Bratah123/MapleDiscBot)~
  
      ~- Feature Rich Discord Bot that the original v316 AzureMS used for majority of it's server life.~
      ~- Easy to setup discord bot.~
      
    - Deprecated bot due to API changes breaking it's core mechanics
  - [NewDiscBot](https://github.com/TEAM-SPIRIT-Productions/Lapis)
    - Feature Rich Discord Bot that attempts to be SUPER plug-n-play
    - Light and easy to setup bot
  - [Lazuli](https://github.com/TEAM-SPIRIT-Productions/Lazuli)
    - A Python Database API made specifically to work with the current open source v316 AzureMS.
    - Easy to use!

---
## Technical Specs
|  | Target | Tested |
| --- | --- | --- |
| Java SDK Version | Java 8 Update 231 | Java 8 Update 261 |
| IDE | IntelliJ | IntelliJ |
| DB Management System | MariaDB | MySQL 5.7.28 |
| DB Administration Tool | HeidiSQL | MySQL Workbench 8.0 CE with WAMP 3.2 |

Note: Scripts (e.g. NPC scripts) are written in JavaScript.

This source inherits many Odin-like traits.
This source uses a mixture of architectures; for instance, layered architecture where scripts, business logic, and data are kept distinct. Note also, within the business logic, the use of MVC-like concepts.


**Instructions for use:**
1. `Clone` or `Fork` this repository
2. Setup the DB management system (i.e. MariaDB or MySQL Workbench)
    - By default the username may be set to `root` and the password left empty, with SSL disabled. Port should be set to `3306`.
3. Setup the DB administrion tool (i.e. HeidiSQL or MySQL Workbench)
    - Run either one of the SQL script files found in `AzureV316/sql/`
    - It should create a new schema named `kms_316`.
    - Note that this will cause errors if MySQL Workbench is running in safe mode. Follow the error message instructions to disable safe mode.
4. Turn **off** innodb strict mode.
    - This can be achieved by editing the `.ini` file in the install path of the DB management system, and requires a restart
5. Open the project in IntelliJ (or your IDE of choice), and allow the IDE to finish indexing (if applicable).
6. Configure project settings. (IntelliJ: File -> Project structure)
    - Set the project SDK to an appropriate JDK version (see above in tech specs)
    - Ensure that all the required libraries (in `AzureV316/AzureMS/lib/`) are imported.
7. Ensure that the details in **Step 2** are reflected in the source code.
    1. Hit `Shift` twice to bring up the search menu.
    2. Select the first option.
    3. Check the username and password strings in `MYSQL.java` are correct.
8. Navigate to `Azure_316\AzureV316\AzureMS\src\launcher` to reach `Start.java`.
    - Try `Build` and `Run` this file. (HeidiSQL or WAMP should be running in background)
    - Note that the first build/run may take quite a while!
