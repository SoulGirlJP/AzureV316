# AzureV316
AzureMS v316 KMS, 

i will update it and rewrite it from scratch later on to get a better understanding about everything.

Contributors :
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
- In addition to the server source code repository, the Azure team also has other AzureMS-based tools.

  - **[Lapis](https://github.com/TEAM-SPIRIT-Productions/Lapis)**  
    - Feature-rich Discord bot that attempts to be SUPER plug-n-play  
    - Lightweight and easy to set-up *(see [Wiki](https://github.com/TEAM-SPIRIT-Productions/Lapis/wiki/General-Flow))*  
    - Built on Lazuli (see below)!  
  - **[Lazuli](https://github.com/TEAM-SPIRIT-Productions/Lazuli)**  
    - A Python-based API for connecting to AzureMS-based servers.  
    - Easy to use; complete with [example code](https://github.com/TEAM-SPIRIT-Productions/Lazuli/wiki/Sample-Code-Fragments#loading-a-database)!  

---
## Quick Start Reference:  
### ***See our [Wiki](https://github.com/SoulGirlJP/AzureV316/wiki/Setup) for a more detailed guide (with screenshots)!***
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
