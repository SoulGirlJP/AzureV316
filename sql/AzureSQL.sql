-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.5-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for kms_316
DROP DATABASE IF EXISTS `kms_316`;
CREATE DATABASE IF NOT EXISTS `kms_316` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `kms_316`;

-- Dumping structure for table kms_316.acceptip
DROP TABLE IF EXISTS `acceptip`;
CREATE TABLE IF NOT EXISTS `acceptip` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ip` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.acceptip: ~0 rows (approximately)
DELETE FROM `acceptip`;
/*!40000 ALTER TABLE `acceptip` DISABLE KEYS */;
/*!40000 ALTER TABLE `acceptip` ENABLE KEYS */;

-- Dumping structure for table kms_316.accounts
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `password` varchar(128) NOT NULL DEFAULT '',
  `2ndpassword` varchar(134) DEFAULT NULL,
  `using2ndpassword` tinyint(2) NOT NULL DEFAULT 0,
  `loggedin` tinyint(1) unsigned NOT NULL DEFAULT 0,
  `lastlogin` timestamp NULL DEFAULT NULL,
  `createdat` timestamp NOT NULL DEFAULT current_timestamp(),
  `banned` tinyint(1) NOT NULL DEFAULT 0,
  `banreason` text DEFAULT NULL,
  `gm` tinyint(1) NOT NULL DEFAULT 0,
  `email` tinytext DEFAULT NULL,
  `macs` tinytext DEFAULT NULL,
  `tempban` timestamp NULL DEFAULT NULL,
  `greason` tinyint(4) unsigned DEFAULT NULL,
  `nxCash` int(11) DEFAULT NULL,
  `mPoints` int(11) DEFAULT NULL,
  `gender` int(11) NOT NULL DEFAULT -1,
  `SessionIP` text DEFAULT NULL,
  `ip` text DEFAULT NULL,
  `pin` tinyint(15) unsigned NOT NULL DEFAULT 0,
  `vpoints` int(50) DEFAULT 0,
  `idcode1` int(7) NOT NULL DEFAULT 111111,
  `idcode2` int(7) NOT NULL DEFAULT 1111111,
  `lastconnect` varchar(10) NOT NULL DEFAULT '1999123100',
  `realcash` int(10) unsigned DEFAULT 0,
  `aimkind` int(11) NOT NULL DEFAULT 0,
  `promote` int(10) unsigned DEFAULT 0,
  `chrslot` int(10) unsigned NOT NULL DEFAULT 3,
  `connecterKey` text DEFAULT NULL,
  `allowed` varchar(45) NOT NULL DEFAULT '0',
  `connecterClient` varchar(45) DEFAULT NULL,
  `maincharacter` text DEFAULT NULL,
  `connecterIP` varchar(45) DEFAULT NULL,
  `banby` text DEFAULT NULL,
  `blockgamelimit` int(11) NOT NULL DEFAULT 0,
  `blockgamedays` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `ranking1` (`id`,`banned`,`gm`)
) ENGINE=MyISAM AUTO_INCREMENT=498 DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.accounts: 0 rows
DELETE FROM `accounts`;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` (`id`, `name`, `password`, `2ndpassword`, `using2ndpassword`, `loggedin`, `lastlogin`, `createdat`, `banned`, `banreason`, `gm`, `email`, `macs`, `tempban`, `greason`, `nxCash`, `mPoints`, `gender`, `SessionIP`, `ip`, `pin`, `vpoints`, `idcode1`, `idcode2`, `lastconnect`, `realcash`, `aimkind`, `promote`, `chrslot`, `connecterKey`, `allowed`, `connecterClient`, `maincharacter`, `connecterIP`, `banby`, `blockgamelimit`, `blockgamedays`) VALUES
	(1, 'admin', 'admin', '1111', 1, 2, '2020-09-09 10:41:23', '2020-09-09 10:38:46', 0, NULL, 0, NULL, NULL, NULL, NULL, 0, 0, -1, NULL, NULL, 0, 0, 111111, 1111111, '2020090910', 0, 0, 0, 3, NULL, '0', NULL, NULL, NULL, NULL, 0, 0);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;

-- Dumping structure for table kms_316.acheck
DROP TABLE IF EXISTS `acheck`;
CREATE TABLE IF NOT EXISTS `acheck` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(10) unsigned NOT NULL,
  `keya` varchar(80) NOT NULL,
  `value` varchar(80) NOT NULL,
  `day` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `id` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=4556 DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.acheck: ~0 rows (approximately)
DELETE FROM `acheck`;
/*!40000 ALTER TABLE `acheck` DISABLE KEYS */;
/*!40000 ALTER TABLE `acheck` ENABLE KEYS */;

-- Dumping structure for table kms_316.akeyvalue
DROP TABLE IF EXISTS `akeyvalue`;
CREATE TABLE IF NOT EXISTS `akeyvalue` (
  `aid` int(11) NOT NULL,
  `key` text NOT NULL,
  `value` bigint(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.akeyvalue: ~0 rows (approximately)
DELETE FROM `akeyvalue`;
/*!40000 ALTER TABLE `akeyvalue` DISABLE KEYS */;
/*!40000 ALTER TABLE `akeyvalue` ENABLE KEYS */;

-- Dumping structure for table kms_316.alliances
DROP TABLE IF EXISTS `alliances`;
CREATE TABLE IF NOT EXISTS `alliances` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notice` varchar(101) NOT NULL,
  `name` varchar(45) NOT NULL,
  `guild1` int(10) NOT NULL DEFAULT 0,
  `guild2` int(10) NOT NULL DEFAULT 0,
  `guild3` int(10) NOT NULL DEFAULT 0,
  `guild4` int(10) NOT NULL DEFAULT 0,
  `guild5` int(10) NOT NULL DEFAULT 0,
  `rank1` varchar(45) NOT NULL,
  `rank2` varchar(45) NOT NULL,
  `rank3` varchar(45) NOT NULL,
  `rank4` varchar(45) NOT NULL,
  `rank5` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.alliances: 0 rows
DELETE FROM `alliances`;
/*!40000 ALTER TABLE `alliances` DISABLE KEYS */;
/*!40000 ALTER TABLE `alliances` ENABLE KEYS */;

-- Dumping structure for table kms_316.android
DROP TABLE IF EXISTS `android`;
CREATE TABLE IF NOT EXISTS `android` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemid` int(11) NOT NULL,
  `hair` int(11) NOT NULL,
  `face` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `uniqueid` int(11) NOT NULL,
  `skincolor` int(11) NOT NULL DEFAULT 0,
  `ear` int(11) NOT NULL DEFAULT 0,
  UNIQUE KEY `id` (`id`),
  KEY `uniqueid` (`uniqueid`)
) ENGINE=MyISAM AUTO_INCREMENT=234 DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.android: 0 rows
DELETE FROM `android`;
/*!40000 ALTER TABLE `android` DISABLE KEYS */;
/*!40000 ALTER TABLE `android` ENABLE KEYS */;

-- Dumping structure for table kms_316.attendcheck
DROP TABLE IF EXISTS `attendcheck`;
CREATE TABLE IF NOT EXISTS `attendcheck` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `accid` int(10) unsigned NOT NULL,
  `weekly_check` int(10) unsigned NOT NULL,
  `returnattend` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.attendcheck: ~0 rows (approximately)
DELETE FROM `attendcheck`;
/*!40000 ALTER TABLE `attendcheck` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendcheck` ENABLE KEYS */;

-- Dumping structure for table kms_316.auctionequipment
DROP TABLE IF EXISTS `auctionequipment`;
CREATE TABLE IF NOT EXISTS `auctionequipment` (
  `inventoryequipmentid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` int(10) unsigned NOT NULL DEFAULT 0,
  `upgradeslots` int(11) NOT NULL DEFAULT 0,
  `level` int(11) NOT NULL DEFAULT 0,
  `str` int(11) NOT NULL DEFAULT 0,
  `dex` int(11) NOT NULL DEFAULT 0,
  `int` int(11) NOT NULL DEFAULT 0,
  `luk` int(11) NOT NULL DEFAULT 0,
  `hp` int(11) NOT NULL DEFAULT 0,
  `mp` int(11) NOT NULL DEFAULT 0,
  `watk` int(11) NOT NULL DEFAULT 0,
  `matk` int(11) NOT NULL DEFAULT 0,
  `wdef` int(11) NOT NULL DEFAULT 0,
  `mdef` int(11) NOT NULL DEFAULT 0,
  `acc` int(11) NOT NULL DEFAULT 0,
  `avoid` int(11) NOT NULL DEFAULT 0,
  `hands` int(11) NOT NULL DEFAULT 0,
  `speed` int(11) NOT NULL DEFAULT 0,
  `jump` int(11) NOT NULL DEFAULT 0,
  `ViciousHammer` tinyint(2) NOT NULL DEFAULT 0,
  `itemLevel` tinyint(1) NOT NULL DEFAULT 0,
  `itemEXP` int(11) NOT NULL DEFAULT 0,
  `durability` mediumint(9) NOT NULL DEFAULT -1,
  `enhance` smallint(3) NOT NULL DEFAULT 0,
  `state` tinyint(2) NOT NULL DEFAULT 0,
  `lines` tinyint(2) NOT NULL DEFAULT 0,
  `potential1` int(11) NOT NULL DEFAULT 0,
  `potential2` int(11) NOT NULL DEFAULT 0,
  `potential3` int(11) NOT NULL DEFAULT 0,
  `potential4` int(11) NOT NULL DEFAULT 0,
  `potential5` int(11) NOT NULL DEFAULT 0,
  `potential6` int(11) NOT NULL DEFAULT 0,
  `anvil` int(11) NOT NULL DEFAULT 0,
  `hpR` smallint(3) NOT NULL,
  `mpR` smallint(3) NOT NULL,
  `potential7` int(11) NOT NULL DEFAULT 0,
  `fire` int(11) NOT NULL DEFAULT -1,
  `downlevel` int(11) NOT NULL DEFAULT 0,
  `bossdmg` int(11) NOT NULL DEFAULT 0,
  `alldmgp` int(11) NOT NULL DEFAULT 0,
  `allstatp` int(11) NOT NULL DEFAULT 0,
  `IgnoreWdef` int(11) NOT NULL DEFAULT 0,
  `soulname` int(11) NOT NULL DEFAULT 0,
  `soulenchanter` int(11) NOT NULL DEFAULT 0,
  `soulpotential` int(11) NOT NULL DEFAULT 0,
  `soulskill` int(11) NOT NULL DEFAULT 0,
  `starforce` int(11) NOT NULL DEFAULT 0,
  `itemtrace` int(11) NOT NULL DEFAULT 0,
  `firestat` varchar(128) NOT NULL DEFAULT '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0',
  `arc` smallint(6) NOT NULL DEFAULT 0,
  `arcexp` int(11) NOT NULL DEFAULT 0,
  `arclevel` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`inventoryequipmentid`),
  KEY `inventoryitemid` (`inventoryitemid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.auctionequipment: 0 rows
DELETE FROM `auctionequipment`;
/*!40000 ALTER TABLE `auctionequipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `auctionequipment` ENABLE KEYS */;

-- Dumping structure for table kms_316.auctionitems
DROP TABLE IF EXISTS `auctionitems`;
CREATE TABLE IF NOT EXISTS `auctionitems` (
  `inventoryitemid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` int(2) NOT NULL DEFAULT 0,
  `characterid` int(11) DEFAULT NULL,
  `accountid` int(11) NOT NULL DEFAULT -1,
  `packageId` int(11) NOT NULL DEFAULT -1,
  `itemid` int(11) NOT NULL DEFAULT 0,
  `inventorytype` int(11) NOT NULL DEFAULT 0,
  `position` int(11) NOT NULL DEFAULT 0,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `owner` tinytext DEFAULT NULL,
  `GM_Log` text DEFAULT NULL,
  `uniqueid` int(30) NOT NULL DEFAULT -1,
  `flag` int(2) NOT NULL DEFAULT 0,
  `expiredate` bigint(30) NOT NULL DEFAULT -1,
  `giftFrom` varchar(20) DEFAULT NULL,
  `isCash` int(2) NOT NULL DEFAULT 0,
  `isPet` int(2) NOT NULL DEFAULT 0,
  `isAndroid` int(2) NOT NULL DEFAULT 0,
  `issale` int(11) NOT NULL DEFAULT 0,
  `bid` bigint(30) DEFAULT NULL,
  `meso` bigint(30) NOT NULL,
  `expired` bigint(30) NOT NULL,
  `bargain` tinyint(1) NOT NULL,
  `ownername` varchar(45) NOT NULL,
  `buyer` int(11) DEFAULT NULL,
  `buytime` bigint(30) NOT NULL,
  `starttime` bigint(30) NOT NULL,
  `inventoryid` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`inventoryitemid`),
  KEY `inventoryitems_ibfk_1` (`characterid`),
  KEY `characterid` (`characterid`),
  KEY `inventorytype` (`inventorytype`),
  KEY `characterid_2` (`characterid`,`inventorytype`),
  KEY `uniqueid` (`uniqueid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.auctionitems: 0 rows
DELETE FROM `auctionitems`;
/*!40000 ALTER TABLE `auctionitems` DISABLE KEYS */;
/*!40000 ALTER TABLE `auctionitems` ENABLE KEYS */;

-- Dumping structure for table kms_316.auctions
DROP TABLE IF EXISTS `auctions`;
CREATE TABLE IF NOT EXISTS `auctions` (
  `characterid` int(11) NOT NULL,
  `bid` int(11) NOT NULL,
  `inventoryid` int(11) NOT NULL,
  `status` int(1) NOT NULL,
  PRIMARY KEY (`characterid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table kms_316.auctions: ~0 rows (approximately)
DELETE FROM `auctions`;
/*!40000 ALTER TABLE `auctions` DISABLE KEYS */;
/*!40000 ALTER TABLE `auctions` ENABLE KEYS */;

-- Dumping structure for table kms_316.auth_server_login
DROP TABLE IF EXISTS `auth_server_login`;
CREATE TABLE IF NOT EXISTS `auth_server_login` (
  `loginserverid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(40) NOT NULL DEFAULT '',
  `world` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`loginserverid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.auth_server_login: 0 rows
DELETE FROM `auth_server_login`;
/*!40000 ALTER TABLE `auth_server_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_server_login` ENABLE KEYS */;

-- Dumping structure for table kms_316.bbs_replies
DROP TABLE IF EXISTS `bbs_replies`;
CREATE TABLE IF NOT EXISTS `bbs_replies` (
  `replyid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `threadid` int(10) unsigned NOT NULL,
  `postercid` int(10) unsigned NOT NULL,
  `timestamp` bigint(20) unsigned NOT NULL,
  `content` varchar(26) NOT NULL DEFAULT '',
  PRIMARY KEY (`replyid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.bbs_replies: 0 rows
DELETE FROM `bbs_replies`;
/*!40000 ALTER TABLE `bbs_replies` DISABLE KEYS */;
/*!40000 ALTER TABLE `bbs_replies` ENABLE KEYS */;

-- Dumping structure for table kms_316.bbs_threads
DROP TABLE IF EXISTS `bbs_threads`;
CREATE TABLE IF NOT EXISTS `bbs_threads` (
  `threadid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `postercid` int(10) unsigned NOT NULL,
  `name` varchar(26) NOT NULL DEFAULT '',
  `timestamp` bigint(20) unsigned NOT NULL,
  `icon` smallint(5) unsigned NOT NULL,
  `replycount` smallint(5) unsigned NOT NULL DEFAULT 0,
  `startpost` text NOT NULL,
  `guildid` int(10) unsigned NOT NULL,
  `localthreadid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`threadid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.bbs_threads: 0 rows
DELETE FROM `bbs_threads`;
/*!40000 ALTER TABLE `bbs_threads` DISABLE KEYS */;
/*!40000 ALTER TABLE `bbs_threads` ENABLE KEYS */;

-- Dumping structure for table kms_316.boom
DROP TABLE IF EXISTS `boom`;
CREATE TABLE IF NOT EXISTS `boom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` text DEFAULT NULL,
  `gun` text DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.boom: ~0 rows (approximately)
DELETE FROM `boom`;
/*!40000 ALTER TABLE `boom` DISABLE KEYS */;
/*!40000 ALTER TABLE `boom` ENABLE KEYS */;

-- Dumping structure for table kms_316.boomboom
DROP TABLE IF EXISTS `boomboom`;
CREATE TABLE IF NOT EXISTS `boomboom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create` text DEFAULT NULL,
  `gun` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.boomboom: ~0 rows (approximately)
DELETE FROM `boomboom`;
/*!40000 ALTER TABLE `boomboom` DISABLE KEYS */;
/*!40000 ALTER TABLE `boomboom` ENABLE KEYS */;

-- Dumping structure for table kms_316.bossclear
DROP TABLE IF EXISTS `bossclear`;
CREATE TABLE IF NOT EXISTS `bossclear` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(10) unsigned NOT NULL,
  `cleartime` bigint(20) unsigned NOT NULL,
  `bossname` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table kms_316.bossclear: ~0 rows (approximately)
DELETE FROM `bossclear`;
/*!40000 ALTER TABLE `bossclear` DISABLE KEYS */;
/*!40000 ALTER TABLE `bossclear` ENABLE KEYS */;

-- Dumping structure for table kms_316.bosscooltime
DROP TABLE IF EXISTS `bosscooltime`;
CREATE TABLE IF NOT EXISTS `bosscooltime` (
  `channel` int(11) NOT NULL,
  `map` int(11) NOT NULL,
  `time` bigint(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.bosscooltime: 0 rows
DELETE FROM `bosscooltime`;
/*!40000 ALTER TABLE `bosscooltime` DISABLE KEYS */;
/*!40000 ALTER TABLE `bosscooltime` ENABLE KEYS */;

-- Dumping structure for table kms_316.bosslog
DROP TABLE IF EXISTS `bosslog`;
CREATE TABLE IF NOT EXISTS `bosslog` (
  `bosslogid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int(10) unsigned NOT NULL,
  `bossid` varchar(20) NOT NULL,
  `lastattempt` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`bosslogid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.bosslog: 0 rows
DELETE FROM `bosslog`;
/*!40000 ALTER TABLE `bosslog` DISABLE KEYS */;
/*!40000 ALTER TABLE `bosslog` ENABLE KEYS */;

-- Dumping structure for table kms_316.bossraidtime
DROP TABLE IF EXISTS `bossraidtime`;
CREATE TABLE IF NOT EXISTS `bossraidtime` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `bgin` varchar(45) NOT NULL,
  `ends` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `diff` varchar(45) NOT NULL,
  `time` int(10) NOT NULL,
  `rate` int(10) NOT NULL,
  `pmem` varchar(45) NOT NULL,
  `size` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.bossraidtime: 0 rows
DELETE FROM `bossraidtime`;
/*!40000 ALTER TABLE `bossraidtime` DISABLE KEYS */;
/*!40000 ALTER TABLE `bossraidtime` ENABLE KEYS */;

-- Dumping structure for table kms_316.boss_total_rank
DROP TABLE IF EXISTS `boss_total_rank`;
CREATE TABLE IF NOT EXISTS `boss_total_rank` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `boss` int(11) NOT NULL,
  `bgin` varchar(45) NOT NULL,
  `ends` varchar(45) NOT NULL,
  `time` int(11) NOT NULL,
  `pmem` varchar(45) NOT NULL,
  `size` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.boss_total_rank: ~0 rows (approximately)
DELETE FROM `boss_total_rank`;
/*!40000 ALTER TABLE `boss_total_rank` DISABLE KEYS */;
/*!40000 ALTER TABLE `boss_total_rank` ENABLE KEYS */;

-- Dumping structure for table kms_316.buddies
DROP TABLE IF EXISTS `buddies`;
CREATE TABLE IF NOT EXISTS `buddies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL,
  `buddyid` int(11) NOT NULL,
  `pending` tinyint(4) NOT NULL DEFAULT 0,
  `groupname` varchar(16) NOT NULL DEFAULT '그룹 미지정',
  PRIMARY KEY (`id`),
  KEY `buddies_ibfk_1` (`characterid`)
) ENGINE=MyISAM AUTO_INCREMENT=8434 DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.buddies: 0 rows
DELETE FROM `buddies`;
/*!40000 ALTER TABLE `buddies` DISABLE KEYS */;
/*!40000 ALTER TABLE `buddies` ENABLE KEYS */;

-- Dumping structure for table kms_316.charactercard
DROP TABLE IF EXISTS `charactercard`;
CREATE TABLE IF NOT EXISTS `charactercard` (
  `accountid` int(11) NOT NULL,
  `cardid` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`accountid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table kms_316.charactercard: ~0 rows (approximately)
DELETE FROM `charactercard`;
/*!40000 ALTER TABLE `charactercard` DISABLE KEYS */;
/*!40000 ALTER TABLE `charactercard` ENABLE KEYS */;

-- Dumping structure for table kms_316.characters
DROP TABLE IF EXISTS `characters`;
CREATE TABLE IF NOT EXISTS `characters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountid` int(11) NOT NULL DEFAULT 0,
  `name` varchar(13) NOT NULL DEFAULT '',
  `level` int(3) NOT NULL DEFAULT 0,
  `exp` bigint(20) NOT NULL DEFAULT 0,
  `str` int(11) NOT NULL DEFAULT 0,
  `dex` int(11) NOT NULL DEFAULT 0,
  `luk` int(11) NOT NULL DEFAULT 0,
  `int` int(11) NOT NULL DEFAULT 0,
  `hp` int(11) NOT NULL DEFAULT 0,
  `mp` int(11) NOT NULL DEFAULT 0,
  `maxhp` int(11) NOT NULL DEFAULT 0,
  `maxmp` int(11) NOT NULL DEFAULT 0,
  `meso` bigint(20) NOT NULL DEFAULT 0,
  `hpApUsed` int(11) NOT NULL DEFAULT 0,
  `mpApUsed` int(11) NOT NULL DEFAULT 0,
  `job` int(11) NOT NULL DEFAULT 0,
  `skincolor` int(1) NOT NULL DEFAULT 0,
  `skincolor2` int(1) NOT NULL DEFAULT 0,
  `gender` int(11) NOT NULL DEFAULT 0,
  `gender2` int(11) NOT NULL DEFAULT 0,
  `fame` int(11) NOT NULL DEFAULT 0,
  `hair` int(10) unsigned NOT NULL DEFAULT 0,
  `hair2` int(11) NOT NULL DEFAULT 0,
  `face` int(11) NOT NULL DEFAULT 0,
  `face2` int(11) NOT NULL DEFAULT 0,
  `wp` int(11) NOT NULL DEFAULT 0,
  `askguildid` int(11) NOT NULL DEFAULT 0,
  `ap` int(11) NOT NULL DEFAULT 0,
  `map` int(11) NOT NULL DEFAULT 0,
  `spawnpoint` int(11) NOT NULL DEFAULT 0,
  `gm` int(11) NOT NULL DEFAULT 0,
  `party` int(11) NOT NULL DEFAULT 0,
  `buddyCapacity` int(11) NOT NULL DEFAULT 25,
  `createdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `rank` int(10) unsigned NOT NULL DEFAULT 1,
  `rankMove` int(11) NOT NULL DEFAULT 0,
  `worldRank` int(10) unsigned NOT NULL DEFAULT 1,
  `worldRankMove` int(11) NOT NULL DEFAULT 0,
  `guildid` int(10) unsigned NOT NULL DEFAULT 0,
  `guildrank` int(10) unsigned NOT NULL DEFAULT 5,
  `allianceRank` int(10) unsigned NOT NULL DEFAULT 0,
  `messengerid` int(10) unsigned NOT NULL DEFAULT 0,
  `messengerposition` int(10) unsigned NOT NULL DEFAULT 4,
  `monsterbookcover` int(11) unsigned NOT NULL DEFAULT 0,
  `subcategory` int(11) DEFAULT 0,
  `reborns` int(11) unsigned NOT NULL DEFAULT 0,
  `sp` varchar(255) NOT NULL DEFAULT '0,0,0,0,0,0,0,0,0,0',
  `ambition` int(11) NOT NULL DEFAULT 0,
  `insight` int(11) NOT NULL DEFAULT 0,
  `willpower` int(11) NOT NULL DEFAULT 0,
  `diligence` int(11) NOT NULL DEFAULT 0,
  `empathy` int(11) NOT NULL DEFAULT 0,
  `charm` int(11) NOT NULL DEFAULT 0,
  `innerExp` int(10) unsigned NOT NULL DEFAULT 0,
  `innerLevel` int(10) unsigned NOT NULL DEFAULT 1,
  `artifactPoints` int(11) NOT NULL DEFAULT 0,
  `morphGage` int(11) NOT NULL DEFAULT 0,
  `firstProfession` int(11) NOT NULL DEFAULT 0,
  `secondProfession` int(11) NOT NULL DEFAULT 0,
  `firstProfessionLevel` int(11) NOT NULL DEFAULT 0,
  `secondProfessionLevel` int(11) NOT NULL DEFAULT 0,
  `firstProfessionExp` int(11) NOT NULL DEFAULT 0,
  `secondProfessionExp` int(11) NOT NULL DEFAULT 0,
  `fatigue` int(11) NOT NULL DEFAULT 0,
  `last_command_time` bigint(14) NOT NULL DEFAULT -1,
  `pet_id` varchar(60) NOT NULL DEFAULT '-1,-1,-1',
  `pet_loot` tinyint(1) NOT NULL DEFAULT 0,
  `pet_mp` int(7) NOT NULL DEFAULT 0,
  `pet_hp` int(7) NOT NULL DEFAULT 0,
  `rankpoint` int(11) NOT NULL,
  `gp` int(11) NOT NULL,
  `soul` int(11) NOT NULL,
  `chatban` varchar(45) NOT NULL,
  `betaclothes` int(11) NOT NULL DEFAULT 0,
  `burning` int(11) NOT NULL DEFAULT 0,
  `loginpoint` int(11) NOT NULL DEFAULT 0,
  `coreq` int(11) NOT NULL DEFAULT 0,
  `frozenmobcount` int(4) NOT NULL DEFAULT 0,
  `mesochair` int(10) unsigned NOT NULL DEFAULT 0,
  `TowerChairSetting` int(10) unsigned NOT NULL DEFAULT 0,
  `damage` bigint(20) unsigned DEFAULT 0,
  `damagehit` int(10) unsigned NOT NULL DEFAULT 100,
  `hope` varchar(45) NOT NULL DEFAULT '장래희망 없음',
  `damagehit2` int(10) unsigned NOT NULL DEFAULT 0,
  `tierReborns` int(11) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `accountid` (`accountid`),
  KEY `party` (`party`),
  KEY `ranking1` (`level`,`exp`),
  KEY `ranking2` (`gm`,`job`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.characters: 0 rows
DELETE FROM `characters`;
/*!40000 ALTER TABLE `characters` DISABLE KEYS */;
INSERT INTO `characters` (`id`, `accountid`, `name`, `level`, `exp`, `str`, `dex`, `luk`, `int`, `hp`, `mp`, `maxhp`, `maxmp`, `meso`, `hpApUsed`, `mpApUsed`, `job`, `skincolor`, `skincolor2`, `gender`, `gender2`, `fame`, `hair`, `hair2`, `face`, `face2`, `wp`, `askguildid`, `ap`, `map`, `spawnpoint`, `gm`, `party`, `buddyCapacity`, `createdate`, `rank`, `rankMove`, `worldRank`, `worldRankMove`, `guildid`, `guildrank`, `allianceRank`, `messengerid`, `messengerposition`, `monsterbookcover`, `subcategory`, `reborns`, `sp`, `ambition`, `insight`, `willpower`, `diligence`, `empathy`, `charm`, `innerExp`, `innerLevel`, `artifactPoints`, `morphGage`, `firstProfession`, `secondProfession`, `firstProfessionLevel`, `secondProfessionLevel`, `firstProfessionExp`, `secondProfessionExp`, `fatigue`, `last_command_time`, `pet_id`, `pet_loot`, `pet_mp`, `pet_hp`, `rankpoint`, `gp`, `soul`, `chatban`, `betaclothes`, `burning`, `loginpoint`, `coreq`, `frozenmobcount`, `mesochair`, `TowerChairSetting`, `damage`, `damagehit`, `hope`, `damagehit2`, `tierReborns`) VALUES
	(2, 1, 'Souls', 1, 0, 12, 5, 4, 4, 50, 5, 50, 5, 0, 0, 0, 301, 0, 0, 0, -1, 0, 46026, 0, 27135, 0, 0, 0, 0, 410000002, 0, 0, -1, 50, '2020-09-09 10:41:23', 1, 0, 1, 0, 0, 5, 0, 0, 4, 0, 3, 0, '0,0,0,0,0,0,0,0,0,0', 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, '-1,-1,-1', 0, 0, 0, 3, 0, 0, 'false', 0, 0, 0, 0, 0, 0, 0, 0, 100, '장래희망 없음', 0, 0);
/*!40000 ALTER TABLE `characters` ENABLE KEYS */;

-- Dumping structure for table kms_316.code
DROP TABLE IF EXISTS `code`;
CREATE TABLE IF NOT EXISTS `code` (
  `chrid` int(10) NOT NULL DEFAULT 0,
  `item` int(10) NOT NULL DEFAULT 0,
  `qua` int(10) NOT NULL DEFAULT 0,
  `code` varchar(50) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.code: ~0 rows (approximately)
DELETE FROM `code`;
/*!40000 ALTER TABLE `code` DISABLE KEYS */;
/*!40000 ALTER TABLE `code` ENABLE KEYS */;

-- Dumping structure for table kms_316.color
DROP TABLE IF EXISTS `color`;
CREATE TABLE IF NOT EXISTS `color` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(10) unsigned NOT NULL,
  `color` int(11) NOT NULL DEFAULT 2,
  `selection` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.color: ~0 rows (approximately)
DELETE FROM `color`;
/*!40000 ALTER TABLE `color` DISABLE KEYS */;
/*!40000 ALTER TABLE `color` ENABLE KEYS */;

-- Dumping structure for table kms_316.connectorban
DROP TABLE IF EXISTS `connectorban`;
CREATE TABLE IF NOT EXISTS `connectorban` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ip` text CHARACTER SET euckr DEFAULT NULL,
  `connecterkey` text CHARACTER SET euckr DEFAULT NULL,
  `comment` text CHARACTER SET euckr DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.connectorban: ~0 rows (approximately)
DELETE FROM `connectorban`;
/*!40000 ALTER TABLE `connectorban` DISABLE KEYS */;
/*!40000 ALTER TABLE `connectorban` ENABLE KEYS */;

-- Dumping structure for table kms_316.core
DROP TABLE IF EXISTS `core`;
CREATE TABLE IF NOT EXISTS `core` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `crcid` bigint(11) unsigned NOT NULL,
  `coreid` int(10) unsigned NOT NULL,
  `charid` int(10) unsigned NOT NULL,
  `level` int(10) unsigned NOT NULL,
  `exp` int(10) unsigned NOT NULL,
  `state` int(10) unsigned NOT NULL,
  `skill1` int(10) unsigned NOT NULL,
  `skill2` int(10) unsigned NOT NULL,
  `skill3` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table kms_316.core: ~0 rows (approximately)
DELETE FROM `core`;
/*!40000 ALTER TABLE `core` DISABLE KEYS */;
/*!40000 ALTER TABLE `core` ENABLE KEYS */;

-- Dumping structure for table kms_316.coupon
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE IF NOT EXISTS `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` text DEFAULT NULL,
  `name` text DEFAULT NULL,
  `item` text DEFAULT NULL,
  `itemn` text DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.coupon: ~0 rows (approximately)
DELETE FROM `coupon`;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;

-- Dumping structure for table kms_316.dailygift
DROP TABLE IF EXISTS `dailygift`;
CREATE TABLE IF NOT EXISTS `dailygift` (
  `accountid` int(11) DEFAULT NULL,
  `dailyday` int(11) DEFAULT NULL,
  `dailycount` int(11) DEFAULT NULL,
  `dailyData` varchar(50) DEFAULT '00000000'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.dailygift: ~0 rows (approximately)
DELETE FROM `dailygift`;
/*!40000 ALTER TABLE `dailygift` DISABLE KEYS */;
INSERT INTO `dailygift` (`accountid`, `dailyday`, `dailycount`, `dailyData`) VALUES
	(1, 0, 0, '00000000');
/*!40000 ALTER TABLE `dailygift` ENABLE KEYS */;

-- Dumping structure for table kms_316.dailyquest
DROP TABLE IF EXISTS `dailyquest`;
CREATE TABLE IF NOT EXISTS `dailyquest` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `accid` int(11) NOT NULL,
  `questid` int(11) NOT NULL,
  `data` int(11) NOT NULL,
  `bossid` int(11) NOT NULL,
  `date` int(11) NOT NULL,
  `clear` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.dailyquest: ~0 rows (approximately)
DELETE FROM `dailyquest`;
/*!40000 ALTER TABLE `dailyquest` DISABLE KEYS */;
/*!40000 ALTER TABLE `dailyquest` ENABLE KEYS */;

-- Dumping structure for table kms_316.damagerank
DROP TABLE IF EXISTS `damagerank`;
CREATE TABLE IF NOT EXISTS `damagerank` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `damage` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=792 DEFAULT CHARSET=utf8 PACK_KEYS=1;

-- Dumping data for table kms_316.damagerank: ~0 rows (approximately)
DELETE FROM `damagerank`;
/*!40000 ALTER TABLE `damagerank` DISABLE KEYS */;
/*!40000 ALTER TABLE `damagerank` ENABLE KEYS */;

-- Dumping structure for table kms_316.drop_data
DROP TABLE IF EXISTS `drop_data`;
CREATE TABLE IF NOT EXISTS `drop_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dropperid` int(11) NOT NULL,
  `itemid` int(11) NOT NULL DEFAULT 0,
  `minimum_quantity` int(11) NOT NULL DEFAULT 1,
  `maximum_quantity` int(11) NOT NULL DEFAULT 1,
  `questid` int(11) NOT NULL DEFAULT 0,
  `chance` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `mobid` (`dropperid`)
) ENGINE=MyISAM AUTO_INCREMENT=90552 DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.drop_data: 0 rows
DELETE FROM `drop_data`;
/*!40000 ALTER TABLE `drop_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `drop_data` ENABLE KEYS */;

-- Dumping structure for table kms_316.drop_data_global
DROP TABLE IF EXISTS `drop_data_global`;
CREATE TABLE IF NOT EXISTS `drop_data_global` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `continent` int(11) NOT NULL,
  `dropType` tinyint(1) NOT NULL DEFAULT 0,
  `itemid` int(11) NOT NULL DEFAULT 0,
  `minimum_quantity` int(11) NOT NULL DEFAULT 1,
  `maximum_quantity` int(11) NOT NULL DEFAULT 1,
  `questid` int(11) NOT NULL DEFAULT 0,
  `chance` int(11) NOT NULL DEFAULT 0,
  `comments` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `mobid` (`continent`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.drop_data_global: ~0 rows (approximately)
DELETE FROM `drop_data_global`;
/*!40000 ALTER TABLE `drop_data_global` DISABLE KEYS */;
/*!40000 ALTER TABLE `drop_data_global` ENABLE KEYS */;

-- Dumping structure for table kms_316.drop_data_vana
DROP TABLE IF EXISTS `drop_data_vana`;
CREATE TABLE IF NOT EXISTS `drop_data_vana` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dropperid` int(11) NOT NULL,
  `flags` set('is_mesos') NOT NULL DEFAULT '',
  `itemid` int(11) NOT NULL DEFAULT 0,
  `minimum_quantity` int(11) NOT NULL DEFAULT 1,
  `maximum_quantity` int(11) NOT NULL DEFAULT 1,
  `questid` int(11) NOT NULL DEFAULT 0,
  `chance` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `mobid` (`dropperid`)
) ENGINE=MyISAM AUTO_INCREMENT=9967 DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.drop_data_vana: 0 rows
DELETE FROM `drop_data_vana`;
/*!40000 ALTER TABLE `drop_data_vana` DISABLE KEYS */;
/*!40000 ALTER TABLE `drop_data_vana` ENABLE KEYS */;

-- Dumping structure for table kms_316.dueypackages
DROP TABLE IF EXISTS `dueypackages`;
CREATE TABLE IF NOT EXISTS `dueypackages` (
  `PackageId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `RecieverId` int(10) NOT NULL,
  `SenderName` varchar(13) NOT NULL,
  `Mesos` int(10) unsigned DEFAULT 0,
  `TimeStamp` bigint(20) unsigned DEFAULT NULL,
  `Checked` tinyint(1) unsigned DEFAULT 1,
  `Type` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`PackageId`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.dueypackages: 0 rows
DELETE FROM `dueypackages`;
/*!40000 ALTER TABLE `dueypackages` DISABLE KEYS */;
/*!40000 ALTER TABLE `dueypackages` ENABLE KEYS */;

-- Dumping structure for table kms_316.eventstats
DROP TABLE IF EXISTS `eventstats`;
CREATE TABLE IF NOT EXISTS `eventstats` (
  `eventstatid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `event` varchar(30) NOT NULL,
  `instance` varchar(30) NOT NULL,
  `characterid` int(11) NOT NULL,
  `channel` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`eventstatid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.eventstats: 0 rows
DELETE FROM `eventstats`;
/*!40000 ALTER TABLE `eventstats` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventstats` ENABLE KEYS */;

-- Dumping structure for table kms_316.extendedslots
DROP TABLE IF EXISTS `extendedslots`;
CREATE TABLE IF NOT EXISTS `extendedslots` (
  `index` int(11) NOT NULL,
  `characterid` int(11) NOT NULL,
  `uniqueid` int(11) NOT NULL,
  KEY `index` (`index`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.extendedslots: 0 rows
DELETE FROM `extendedslots`;
/*!40000 ALTER TABLE `extendedslots` DISABLE KEYS */;
/*!40000 ALTER TABLE `extendedslots` ENABLE KEYS */;

-- Dumping structure for table kms_316.famelog
DROP TABLE IF EXISTS `famelog`;
CREATE TABLE IF NOT EXISTS `famelog` (
  `famelogid` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL DEFAULT 0,
  `characterid_to` int(11) NOT NULL DEFAULT 0,
  `when` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`famelogid`),
  KEY `characterid` (`characterid`)
) ENGINE=MyISAM AUTO_INCREMENT=173 DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.famelog: 0 rows
DELETE FROM `famelog`;
/*!40000 ALTER TABLE `famelog` DISABLE KEYS */;
/*!40000 ALTER TABLE `famelog` ENABLE KEYS */;

-- Dumping structure for table kms_316.fireitem
DROP TABLE IF EXISTS `fireitem`;
CREATE TABLE IF NOT EXISTS `fireitem` (
  `value` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uniqueid` int(11) NOT NULL DEFAULT 0,
  `mdef` int(11) NOT NULL DEFAULT 0,
  `mp` int(11) NOT NULL DEFAULT 0,
  `hp` int(11) NOT NULL DEFAULT 0,
  `wdef` int(11) NOT NULL DEFAULT 0,
  `speed` int(11) NOT NULL DEFAULT 0,
  `jump` int(11) NOT NULL DEFAULT 0,
  `fire` int(11) NOT NULL DEFAULT 0,
  `hands` int(11) NOT NULL DEFAULT 0,
  `avoid` int(11) NOT NULL DEFAULT 0,
  `acc` int(11) NOT NULL DEFAULT 0,
  `watk` int(11) NOT NULL DEFAULT 0,
  `matk` int(11) NOT NULL DEFAULT 0,
  `str` int(11) NOT NULL DEFAULT 0,
  `dex` int(11) NOT NULL DEFAULT 0,
  `_int` int(11) NOT NULL DEFAULT 0,
  `luk` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.fireitem: ~0 rows (approximately)
DELETE FROM `fireitem`;
/*!40000 ALTER TABLE `fireitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `fireitem` ENABLE KEYS */;

-- Dumping structure for table kms_316.force
DROP TABLE IF EXISTS `force`;
CREATE TABLE IF NOT EXISTS `force` (
  `forceid` int(11) NOT NULL AUTO_INCREMENT,
  `point` int(11) NOT NULL,
  PRIMARY KEY (`forceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.force: ~0 rows (approximately)
DELETE FROM `force`;
/*!40000 ALTER TABLE `force` DISABLE KEYS */;
/*!40000 ALTER TABLE `force` ENABLE KEYS */;

-- Dumping structure for table kms_316.forcemap
DROP TABLE IF EXISTS `forcemap`;
CREATE TABLE IF NOT EXISTS `forcemap` (
  `mapid` int(11) NOT NULL,
  `mobcount` int(11) NOT NULL,
  `force` int(1) NOT NULL,
  `channel` int(2) NOT NULL,
  `time` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.forcemap: ~0 rows (approximately)
DELETE FROM `forcemap`;
/*!40000 ALTER TABLE `forcemap` DISABLE KEYS */;
/*!40000 ALTER TABLE `forcemap` ENABLE KEYS */;

-- Dumping structure for table kms_316.futurehope
DROP TABLE IF EXISTS `futurehope`;
CREATE TABLE IF NOT EXISTS `futurehope` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(10) unsigned NOT NULL,
  `hope` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2182 DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.futurehope: ~0 rows (approximately)
DELETE FROM `futurehope`;
/*!40000 ALTER TABLE `futurehope` DISABLE KEYS */;
/*!40000 ALTER TABLE `futurehope` ENABLE KEYS */;

-- Dumping structure for table kms_316.game_poll_reply
DROP TABLE IF EXISTS `game_poll_reply`;
CREATE TABLE IF NOT EXISTS `game_poll_reply` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `AccountId` int(10) unsigned NOT NULL,
  `SelectAns` tinyint(5) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kms_316.game_poll_reply: 0 rows
DELETE FROM `game_poll_reply`;
/*!40000 ALTER TABLE `game_poll_reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `game_poll_reply` ENABLE KEYS */;

-- Dumping structure for table kms_316.ggpremium
DROP TABLE IF EXISTS `ggpremium`;
CREATE TABLE IF NOT EXISTS `ggpremium` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `aid` int(11) NOT NULL,
  `limit` bigint(30) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.ggpremium: ~0 rows (approximately)
DELETE FROM `ggpremium`;
/*!40000 ALTER TABLE `ggpremium` DISABLE KEYS */;
/*!40000 ALTER TABLE `ggpremium` ENABLE KEYS */;

-- Dumping structure for table kms_316.guilds
DROP TABLE IF EXISTS `guilds`;
CREATE TABLE IF NOT EXISTS `guilds` (
  `guildid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `leader` int(10) unsigned NOT NULL DEFAULT 0,
  `GP` int(10) unsigned NOT NULL DEFAULT 0,
  `logo` int(10) unsigned DEFAULT NULL,
  `logoColor` smallint(5) unsigned NOT NULL DEFAULT 0,
  `name` varchar(45) NOT NULL,
  `rank1title` varchar(45) NOT NULL DEFAULT '마스터',
  `rank2title` varchar(45) NOT NULL DEFAULT '부마스터',
  `rank3title` varchar(45) NOT NULL DEFAULT '멤버',
  `rank4title` varchar(45) NOT NULL DEFAULT '멤버',
  `rank5title` varchar(45) NOT NULL DEFAULT '멤버',
  `capacity` int(10) unsigned NOT NULL DEFAULT 10,
  `logoBG` int(10) unsigned DEFAULT NULL,
  `logoBGColor` smallint(5) unsigned NOT NULL DEFAULT 0,
  `notice` varchar(101) DEFAULT NULL,
  `signature` int(11) NOT NULL DEFAULT 0,
  `alliance` int(10) unsigned NOT NULL DEFAULT 0,
  `level` int(11) NOT NULL DEFAULT 0,
  `accruedGP` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`guildid`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.guilds: 0 rows
DELETE FROM `guilds`;
/*!40000 ALTER TABLE `guilds` DISABLE KEYS */;
/*!40000 ALTER TABLE `guilds` ENABLE KEYS */;

-- Dumping structure for table kms_316.guildsills
DROP TABLE IF EXISTS `guildsills`;
CREATE TABLE IF NOT EXISTS `guildsills` (
  `gid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `skillid` int(10) unsigned NOT NULL,
  `time` bigint(20) unsigned NOT NULL,
  `buyer` int(10) unsigned NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.guildsills: 0 rows
DELETE FROM `guildsills`;
/*!40000 ALTER TABLE `guildsills` DISABLE KEYS */;
/*!40000 ALTER TABLE `guildsills` ENABLE KEYS */;

-- Dumping structure for table kms_316.guildskills
DROP TABLE IF EXISTS `guildskills`;
CREATE TABLE IF NOT EXISTS `guildskills` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `guildid` int(10) unsigned DEFAULT 0,
  `skillid` int(10) unsigned DEFAULT 0,
  `level` int(10) unsigned DEFAULT 0,
  `timestamp` bigint(20) unsigned DEFAULT 0,
  `purchaser` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.guildskills: ~0 rows (approximately)
DELETE FROM `guildskills`;
/*!40000 ALTER TABLE `guildskills` DISABLE KEYS */;
/*!40000 ALTER TABLE `guildskills` ENABLE KEYS */;

-- Dumping structure for table kms_316.guild_join_requests
DROP TABLE IF EXISTS `guild_join_requests`;
CREATE TABLE IF NOT EXISTS `guild_join_requests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guildid` int(11) NOT NULL DEFAULT 0,
  `job` int(6) NOT NULL DEFAULT 0,
  `level` int(3) NOT NULL DEFAULT 0,
  `name` varchar(14) NOT NULL,
  `request_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `인덱스 2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table kms_316.guild_join_requests: ~0 rows (approximately)
DELETE FROM `guild_join_requests`;
/*!40000 ALTER TABLE `guild_join_requests` DISABLE KEYS */;
/*!40000 ALTER TABLE `guild_join_requests` ENABLE KEYS */;

-- Dumping structure for table kms_316.hiredmerch
DROP TABLE IF EXISTS `hiredmerch`;
CREATE TABLE IF NOT EXISTS `hiredmerch` (
  `PackageId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int(10) unsigned DEFAULT 0,
  `accountid` int(10) unsigned DEFAULT NULL,
  `Mesos` int(10) unsigned DEFAULT 0,
  `time` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`PackageId`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.hiredmerch: 0 rows
DELETE FROM `hiredmerch`;
/*!40000 ALTER TABLE `hiredmerch` DISABLE KEYS */;
/*!40000 ALTER TABLE `hiredmerch` ENABLE KEYS */;

-- Dumping structure for table kms_316.hiredmerchantsaveitems
DROP TABLE IF EXISTS `hiredmerchantsaveitems`;
CREATE TABLE IF NOT EXISTS `hiredmerchantsaveitems` (
  `id` int(11) NOT NULL,
  `merchid` int(11) NOT NULL,
  `uniqueid` int(30) NOT NULL,
  `bundle` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.hiredmerchantsaveitems: 0 rows
DELETE FROM `hiredmerchantsaveitems`;
/*!40000 ALTER TABLE `hiredmerchantsaveitems` DISABLE KEYS */;
/*!40000 ALTER TABLE `hiredmerchantsaveitems` ENABLE KEYS */;

-- Dumping structure for table kms_316.hiredmerchantsaves
DROP TABLE IF EXISTS `hiredmerchantsaves`;
CREATE TABLE IF NOT EXISTS `hiredmerchantsaves` (
  `id` int(20) NOT NULL,
  `desc` varchar(100) NOT NULL,
  `itemId` int(11) NOT NULL,
  `ownerName` varchar(30) NOT NULL,
  `ownerId` int(11) NOT NULL,
  `ownerAccid` int(11) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `map` int(11) NOT NULL,
  `channel` int(11) NOT NULL,
  `start` bigint(30) NOT NULL,
  `meso` int(13) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.hiredmerchantsaves: 0 rows
DELETE FROM `hiredmerchantsaves`;
/*!40000 ALTER TABLE `hiredmerchantsaves` DISABLE KEYS */;
/*!40000 ALTER TABLE `hiredmerchantsaves` ENABLE KEYS */;

-- Dumping structure for table kms_316.hns
DROP TABLE IF EXISTS `hns`;
CREATE TABLE IF NOT EXISTS `hns` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL DEFAULT 0,
  `win` int(11) NOT NULL DEFAULT 0,
  `lose` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.hns: ~0 rows (approximately)
DELETE FROM `hns`;
/*!40000 ALTER TABLE `hns` DISABLE KEYS */;
/*!40000 ALTER TABLE `hns` ENABLE KEYS */;

-- Dumping structure for table kms_316.htsquads
DROP TABLE IF EXISTS `htsquads`;
CREATE TABLE IF NOT EXISTS `htsquads` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `channel` int(10) unsigned NOT NULL,
  `leaderid` int(10) unsigned NOT NULL DEFAULT 0,
  `status` int(10) unsigned NOT NULL DEFAULT 0,
  `members` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.htsquads: 0 rows
DELETE FROM `htsquads`;
/*!40000 ALTER TABLE `htsquads` DISABLE KEYS */;
/*!40000 ALTER TABLE `htsquads` ENABLE KEYS */;

-- Dumping structure for table kms_316.inner_ability_skills
DROP TABLE IF EXISTS `inner_ability_skills`;
CREATE TABLE IF NOT EXISTS `inner_ability_skills` (
  `player_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL,
  `skill_level` int(11) NOT NULL,
  `max_level` int(11) NOT NULL,
  `rank` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.inner_ability_skills: ~0 rows (approximately)
DELETE FROM `inner_ability_skills`;
/*!40000 ALTER TABLE `inner_ability_skills` DISABLE KEYS */;
/*!40000 ALTER TABLE `inner_ability_skills` ENABLE KEYS */;

-- Dumping structure for table kms_316.inventoryequipment
DROP TABLE IF EXISTS `inventoryequipment`;
CREATE TABLE IF NOT EXISTS `inventoryequipment` (
  `inventoryequipmentid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` int(10) unsigned NOT NULL DEFAULT 0,
  `upgradeslots` int(11) NOT NULL DEFAULT 0,
  `level` int(11) NOT NULL DEFAULT 0,
  `str` int(11) NOT NULL DEFAULT 0,
  `dex` int(11) NOT NULL DEFAULT 0,
  `int` int(11) NOT NULL DEFAULT 0,
  `luk` int(11) NOT NULL DEFAULT 0,
  `hp` int(11) NOT NULL DEFAULT 0,
  `mp` int(11) NOT NULL DEFAULT 0,
  `watk` int(11) NOT NULL DEFAULT 0,
  `matk` int(11) NOT NULL DEFAULT 0,
  `wdef` int(11) NOT NULL DEFAULT 0,
  `mdef` int(11) NOT NULL DEFAULT 0,
  `acc` int(11) NOT NULL DEFAULT 0,
  `avoid` int(11) NOT NULL DEFAULT 0,
  `hands` int(11) NOT NULL DEFAULT 0,
  `speed` int(11) NOT NULL DEFAULT 0,
  `jump` int(11) NOT NULL DEFAULT 0,
  `ViciousHammer` tinyint(2) NOT NULL DEFAULT 0,
  `itemLevel` tinyint(1) NOT NULL DEFAULT 0,
  `itemEXP` int(11) NOT NULL DEFAULT 0,
  `durability` mediumint(9) NOT NULL DEFAULT -1,
  `enhance` smallint(3) NOT NULL DEFAULT 0,
  `state` int(11) NOT NULL DEFAULT 0,
  `lines` tinyint(2) NOT NULL DEFAULT 0,
  `potential1` int(11) NOT NULL DEFAULT 0,
  `potential2` int(11) NOT NULL DEFAULT 0,
  `potential3` int(11) NOT NULL DEFAULT 0,
  `potential4` int(11) NOT NULL DEFAULT 0,
  `potential5` int(11) NOT NULL DEFAULT 0,
  `potential6` int(11) NOT NULL DEFAULT 0,
  `anvil` int(11) NOT NULL DEFAULT 0,
  `hpR` smallint(3) NOT NULL,
  `mpR` smallint(3) NOT NULL,
  `potential7` int(11) NOT NULL DEFAULT 0,
  `fire` int(11) NOT NULL DEFAULT -1,
  `downlevel` int(11) NOT NULL DEFAULT 0,
  `bossdmg` int(11) NOT NULL DEFAULT 0,
  `alldmgp` int(11) NOT NULL DEFAULT 0,
  `allstatp` int(11) NOT NULL DEFAULT 0,
  `IgnoreWdef` int(11) NOT NULL DEFAULT 0,
  `soulname` int(11) NOT NULL DEFAULT 0,
  `soulenchanter` int(11) NOT NULL DEFAULT 0,
  `soulpotential` int(11) NOT NULL DEFAULT 0,
  `soulskill` int(11) NOT NULL DEFAULT 0,
  `starforce` int(11) NOT NULL DEFAULT 0,
  `itemtrace` int(11) NOT NULL DEFAULT 0,
  `firestat` varchar(128) NOT NULL DEFAULT '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0',
  `arc` smallint(6) NOT NULL DEFAULT 0,
  `arcexp` int(11) NOT NULL DEFAULT 0,
  `arclevel` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`inventoryequipmentid`),
  KEY `inventoryitemid` (`inventoryitemid`)
) ENGINE=MyISAM AUTO_INCREMENT=45169 DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.inventoryequipment: 0 rows
DELETE FROM `inventoryequipment`;
/*!40000 ALTER TABLE `inventoryequipment` DISABLE KEYS */;
INSERT INTO `inventoryequipment` (`inventoryequipmentid`, `inventoryitemid`, `upgradeslots`, `level`, `str`, `dex`, `int`, `luk`, `hp`, `mp`, `watk`, `matk`, `wdef`, `mdef`, `acc`, `avoid`, `hands`, `speed`, `jump`, `ViciousHammer`, `itemLevel`, `itemEXP`, `durability`, `enhance`, `state`, `lines`, `potential1`, `potential2`, `potential3`, `potential4`, `potential5`, `potential6`, `anvil`, `hpR`, `mpR`, `potential7`, `fire`, `downlevel`, `bossdmg`, `alldmgp`, `allstatp`, `IgnoreWdef`, `soulname`, `soulenchanter`, `soulpotential`, `soulskill`, `starforce`, `itemtrace`, `firestat`, `arc`, `arcexp`, `arclevel`) VALUES
	(45163, 70642150, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 6, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', 0, 0, 0),
	(45164, 70642151, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 6, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', 0, 0, 0),
	(45165, 70642152, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 6, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', 0, 0, 0),
	(45166, 70642153, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 6, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', 0, 0, 0),
	(45167, 70642154, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 6, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', 0, 0, 0),
	(45168, 70642155, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 6, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', 0, 0, 0);
/*!40000 ALTER TABLE `inventoryequipment` ENABLE KEYS */;

-- Dumping structure for table kms_316.inventoryitems
DROP TABLE IF EXISTS `inventoryitems`;
CREATE TABLE IF NOT EXISTS `inventoryitems` (
  `inventoryitemid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` int(2) NOT NULL DEFAULT 0,
  `characterid` int(11) DEFAULT NULL,
  `accountid` int(11) NOT NULL DEFAULT -1,
  `packageId` int(11) NOT NULL DEFAULT -1,
  `itemid` int(11) NOT NULL DEFAULT 0,
  `inventorytype` int(11) NOT NULL DEFAULT 0,
  `position` int(11) NOT NULL DEFAULT 0,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `owner` tinytext DEFAULT NULL,
  `GM_Log` text DEFAULT NULL,
  `uniqueid` int(30) NOT NULL DEFAULT -1,
  `flag` int(2) NOT NULL DEFAULT 0,
  `expiredate` bigint(30) NOT NULL DEFAULT -1,
  `giftFrom` varchar(20) DEFAULT NULL,
  `isCash` int(2) NOT NULL DEFAULT 0,
  `isPet` int(2) NOT NULL DEFAULT 0,
  `isAndroid` int(2) NOT NULL DEFAULT 0,
  `issale` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`inventoryitemid`),
  KEY `inventoryitems_ibfk_1` (`characterid`),
  KEY `characterid` (`characterid`),
  KEY `inventorytype` (`inventorytype`),
  KEY `characterid_2` (`characterid`,`inventorytype`),
  KEY `uniqueid` (`uniqueid`)
) ENGINE=MyISAM AUTO_INCREMENT=70642156 DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.inventoryitems: 0 rows
DELETE FROM `inventoryitems`;
/*!40000 ALTER TABLE `inventoryitems` DISABLE KEYS */;
INSERT INTO `inventoryitems` (`inventoryitemid`, `type`, `characterid`, `accountid`, `packageId`, `itemid`, `inventorytype`, `position`, `quantity`, `owner`, `GM_Log`, `uniqueid`, `flag`, `expiredate`, `giftFrom`, `isCash`, `isPet`, `isAndroid`, `issale`) VALUES
	(70642155, 1, 2, -1, -1, 1073334, -1, -7, 1, '', 'Character Creation Initiator', 5, 0, -1, '', 0, 0, 0, 0),
	(70642154, 1, 2, -1, -1, 1053402, -1, -5, 1, '', 'Character Creation Initiator', 3, 0, -1, '', 0, 0, 0, 0),
	(70642153, 1, 2, -1, -1, 1005313, -1, -1, 1, '', 'Character Creation Initiator', 1, 0, -1, '', 0, 0, 0, 0);
/*!40000 ALTER TABLE `inventoryitems` ENABLE KEYS */;

-- Dumping structure for table kms_316.inventorylog
DROP TABLE IF EXISTS `inventorylog`;
CREATE TABLE IF NOT EXISTS `inventorylog` (
  `inventorylogid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` int(10) unsigned NOT NULL DEFAULT 0,
  `msg` tinytext NOT NULL,
  PRIMARY KEY (`inventorylogid`),
  KEY `inventoryitemid` (`inventoryitemid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.inventorylog: 0 rows
DELETE FROM `inventorylog`;
/*!40000 ALTER TABLE `inventorylog` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventorylog` ENABLE KEYS */;

-- Dumping structure for table kms_316.inventoryslot
DROP TABLE IF EXISTS `inventoryslot`;
CREATE TABLE IF NOT EXISTS `inventoryslot` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int(10) unsigned DEFAULT NULL,
  `equip` int(10) unsigned DEFAULT NULL,
  `use` int(10) unsigned DEFAULT NULL,
  `setup` int(10) unsigned DEFAULT NULL,
  `etc` int(10) unsigned DEFAULT NULL,
  `cash` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=46 DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.inventoryslot: 0 rows
DELETE FROM `inventoryslot`;
/*!40000 ALTER TABLE `inventoryslot` DISABLE KEYS */;
INSERT INTO `inventoryslot` (`id`, `characterid`, `equip`, `use`, `setup`, `etc`, `cash`) VALUES
	(45, 2, 96, 96, 96, 96, 60);
/*!40000 ALTER TABLE `inventoryslot` ENABLE KEYS */;

-- Dumping structure for table kms_316.ipbans
DROP TABLE IF EXISTS `ipbans`;
CREATE TABLE IF NOT EXISTS `ipbans` (
  `ipbanid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(40) NOT NULL DEFAULT '',
  PRIMARY KEY (`ipbanid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.ipbans: 0 rows
DELETE FROM `ipbans`;
/*!40000 ALTER TABLE `ipbans` DISABLE KEYS */;
/*!40000 ALTER TABLE `ipbans` ENABLE KEYS */;

-- Dumping structure for table kms_316.itempot
DROP TABLE IF EXISTS `itempot`;
CREATE TABLE IF NOT EXISTS `itempot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL DEFAULT 0,
  `lifeid` int(11) NOT NULL DEFAULT 0,
  `slot` int(11) NOT NULL DEFAULT 0,
  `level` int(11) NOT NULL DEFAULT 0,
  `status` int(11) NOT NULL DEFAULT 0,
  `fullness` int(8) NOT NULL DEFAULT 0,
  `closeness` int(8) NOT NULL DEFAULT 0,
  `starttime` bigint(20) NOT NULL DEFAULT 0,
  `sleeptime` bigint(20) NOT NULL DEFAULT 0,
  `incCloseLeft` int(8) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.itempot: 0 rows
DELETE FROM `itempot`;
/*!40000 ALTER TABLE `itempot` DISABLE KEYS */;
/*!40000 ALTER TABLE `itempot` ENABLE KEYS */;

-- Dumping structure for table kms_316.keymap
DROP TABLE IF EXISTS `keymap`;
CREATE TABLE IF NOT EXISTS `keymap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL DEFAULT 0,
  `key` int(11) NOT NULL DEFAULT 0,
  `type` int(11) NOT NULL DEFAULT 0,
  `action` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `keymap_ibfk_1` (`characterid`)
) ENGINE=MyISAM AUTO_INCREMENT=279972 DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.keymap: 0 rows
DELETE FROM `keymap`;
/*!40000 ALTER TABLE `keymap` DISABLE KEYS */;
INSERT INTO `keymap` (`id`, `characterid`, `key`, `type`, `action`) VALUES
	(279971, 2, 63, 6, 104),
	(279970, 2, 62, 6, 103),
	(279969, 2, 61, 6, 102),
	(279968, 2, 60, 6, 101),
	(279967, 2, 59, 6, 100),
	(279966, 2, 57, 5, 54),
	(279965, 2, 56, 5, 53),
	(279964, 2, 51, 4, 33),
	(279963, 2, 50, 4, 7),
	(279962, 2, 48, 4, 29),
	(279961, 2, 47, 4, 31),
	(279960, 2, 46, 4, 6),
	(279959, 2, 45, 5, 51),
	(279958, 2, 44, 5, 50),
	(279957, 2, 43, 4, 9),
	(279956, 2, 41, 4, 22),
	(279955, 2, 40, 4, 16),
	(279954, 2, 39, 4, 26),
	(279953, 2, 38, 4, 20),
	(279952, 2, 37, 4, 3),
	(279951, 2, 35, 4, 11),
	(279950, 2, 34, 4, 17),
	(279949, 2, 33, 4, 25),
	(279948, 2, 31, 4, 2),
	(279947, 2, 29, 5, 52),
	(279946, 2, 27, 4, 15),
	(279945, 2, 26, 4, 14),
	(279944, 2, 25, 4, 19),
	(279943, 2, 23, 4, 1),
	(279942, 2, 21, 4, 30),
	(279941, 2, 20, 4, 27),
	(279940, 2, 19, 4, 4),
	(279939, 2, 18, 4, 0),
	(279938, 2, 17, 4, 5),
	(279937, 2, 16, 4, 8),
	(279936, 2, 7, 4, 28),
	(279935, 2, 6, 4, 23),
	(279934, 2, 5, 4, 18),
	(279933, 2, 4, 4, 13),
	(279932, 2, 3, 4, 12),
	(279931, 2, 2, 4, 10),
	(279930, 2, 1, 4, 46),
	(279929, 2, 65, 6, 106),
	(279928, 2, 64, 6, 105);
/*!40000 ALTER TABLE `keymap` ENABLE KEYS */;

-- Dumping structure for table kms_316.keyvalue
DROP TABLE IF EXISTS `keyvalue`;
CREATE TABLE IF NOT EXISTS `keyvalue` (
  `cid` int(11) NOT NULL DEFAULT 0,
  `key` varchar(50) NOT NULL DEFAULT 'null',
  `value` varchar(100) NOT NULL DEFAULT 'null',
  KEY `cid` (`cid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.keyvalue: 0 rows
DELETE FROM `keyvalue`;
/*!40000 ALTER TABLE `keyvalue` DISABLE KEYS */;
INSERT INTO `keyvalue` (`cid`, `key`, `value`) VALUES
	(2, 'Today_Charisma', '0'),
	(2, 'Today_Diligence', '0'),
	(2, 'HeadTitle', '0'),
	(2, 'lastLogout', '1599662759053'),
	(2, 'opengate', 'null'),
	(2, 'Today_Insight', '0'),
	(2, 'Today_Empathy', '0'),
	(2, 'Today_WillPower', '0'),
	(2, 'Today_Charm', '0'),
	(2, 'count', 'null');
/*!40000 ALTER TABLE `keyvalue` ENABLE KEYS */;

-- Dumping structure for table kms_316.keyvalue2
DROP TABLE IF EXISTS `keyvalue2`;
CREATE TABLE IF NOT EXISTS `keyvalue2` (
  `cid` int(11) NOT NULL,
  `key` varchar(100) NOT NULL,
  `value` bigint(100) NOT NULL,
  KEY `cid` (`cid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.keyvalue2: 0 rows
DELETE FROM `keyvalue2`;
/*!40000 ALTER TABLE `keyvalue2` DISABLE KEYS */;
/*!40000 ALTER TABLE `keyvalue2` ENABLE KEYS */;

-- Dumping structure for table kms_316.learnexp
DROP TABLE IF EXISTS `learnexp`;
CREATE TABLE IF NOT EXISTS `learnexp` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `accid` int(11) NOT NULL DEFAULT 0,
  `exp` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.learnexp: ~0 rows (approximately)
DELETE FROM `learnexp`;
/*!40000 ALTER TABLE `learnexp` DISABLE KEYS */;
/*!40000 ALTER TABLE `learnexp` ENABLE KEYS */;

-- Dumping structure for table kms_316.linkskill
DROP TABLE IF EXISTS `linkskill`;
CREATE TABLE IF NOT EXISTS `linkskill` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `accid` int(10) unsigned NOT NULL,
  `realskillid` int(10) unsigned NOT NULL,
  `skillid` int(10) unsigned NOT NULL,
  `linking_cid` int(10) unsigned NOT NULL,
  `linked_cid` int(10) unsigned NOT NULL,
  `skilllevel` int(10) unsigned NOT NULL,
  `time` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=646 DEFAULT CHARSET=utf8;

-- Dumping data for table kms_316.linkskill: ~0 rows (approximately)
DELETE FROM `linkskill`;
/*!40000 ALTER TABLE `linkskill` DISABLE KEYS */;
/*!40000 ALTER TABLE `linkskill` ENABLE KEYS */;

-- Dumping structure for table kms_316.link_skill
DROP TABLE IF EXISTS `link_skill`;
CREATE TABLE IF NOT EXISTS `link_skill` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `skillid` int(10) unsigned NOT NULL,
  `skillLevel` int(10) unsigned NOT NULL,
  `link_cid` int(10) unsigned NOT NULL,
  `cid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.link_skill: ~0 rows (approximately)
DELETE FROM `link_skill`;
/*!40000 ALTER TABLE `link_skill` DISABLE KEYS */;
/*!40000 ALTER TABLE `link_skill` ENABLE KEYS */;

-- Dumping structure for table kms_316.macbans
DROP TABLE IF EXISTS `macbans`;
CREATE TABLE IF NOT EXISTS `macbans` (
  `macbanid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mac` varchar(30) NOT NULL,
  PRIMARY KEY (`macbanid`),
  UNIQUE KEY `mac_2` (`mac`)
) ENGINE=MEMORY DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.macbans: 0 rows
DELETE FROM `macbans`;
/*!40000 ALTER TABLE `macbans` DISABLE KEYS */;
/*!40000 ALTER TABLE `macbans` ENABLE KEYS */;

-- Dumping structure for table kms_316.macfilters
DROP TABLE IF EXISTS `macfilters`;
CREATE TABLE IF NOT EXISTS `macfilters` (
  `macfilterid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `filter` varchar(30) NOT NULL,
  PRIMARY KEY (`macfilterid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.macfilters: 0 rows
DELETE FROM `macfilters`;
/*!40000 ALTER TABLE `macfilters` DISABLE KEYS */;
/*!40000 ALTER TABLE `macfilters` ENABLE KEYS */;

-- Dumping structure for table kms_316.medalranking
DROP TABLE IF EXISTS `medalranking`;
CREATE TABLE IF NOT EXISTS `medalranking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `name` varchar(100) CHARACTER SET euckr NOT NULL,
  `type` varchar(100) CHARACTER SET euckr NOT NULL,
  `value` bigint(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.medalranking: 0 rows
DELETE FROM `medalranking`;
/*!40000 ALTER TABLE `medalranking` DISABLE KEYS */;
/*!40000 ALTER TABLE `medalranking` ENABLE KEYS */;

-- Dumping structure for table kms_316.memo
DROP TABLE IF EXISTS `memo`;
CREATE TABLE IF NOT EXISTS `memo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `title` text NOT NULL,
  `date` varchar(64) DEFAULT NULL,
  `memo` text NOT NULL,
  `reply` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.memo: ~0 rows (approximately)
DELETE FROM `memo`;
/*!40000 ALTER TABLE `memo` DISABLE KEYS */;
/*!40000 ALTER TABLE `memo` ENABLE KEYS */;

-- Dumping structure for table kms_316.meso_market
DROP TABLE IF EXISTS `meso_market`;
CREATE TABLE IF NOT EXISTS `meso_market` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sellname` text DEFAULT NULL,
  `buyname` text DEFAULT NULL,
  `sellmeso` bigint(20) unsigned DEFAULT NULL,
  `sellmesokp` int(10) unsigned DEFAULT NULL,
  `selldate` text DEFAULT NULL,
  `buydate` text DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.meso_market: ~0 rows (approximately)
DELETE FROM `meso_market`;
/*!40000 ALTER TABLE `meso_market` DISABLE KEYS */;
/*!40000 ALTER TABLE `meso_market` ENABLE KEYS */;

-- Dumping structure for table kms_316.monsterbook
DROP TABLE IF EXISTS `monsterbook`;
CREATE TABLE IF NOT EXISTS `monsterbook` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `charid` int(10) unsigned NOT NULL DEFAULT 0,
  `cardid` int(10) unsigned NOT NULL DEFAULT 0,
  `level` tinyint(2) unsigned DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.monsterbook: 0 rows
DELETE FROM `monsterbook`;
/*!40000 ALTER TABLE `monsterbook` DISABLE KEYS */;
/*!40000 ALTER TABLE `monsterbook` ENABLE KEYS */;

-- Dumping structure for table kms_316.mountdata
DROP TABLE IF EXISTS `mountdata`;
CREATE TABLE IF NOT EXISTS `mountdata` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int(10) unsigned DEFAULT NULL,
  `Level` int(10) unsigned NOT NULL DEFAULT 0,
  `Exp` int(10) unsigned NOT NULL DEFAULT 0,
  `Fatigue` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=91 DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.mountdata: 0 rows
DELETE FROM `mountdata`;
/*!40000 ALTER TABLE `mountdata` DISABLE KEYS */;
INSERT INTO `mountdata` (`id`, `characterid`, `Level`, `Exp`, `Fatigue`) VALUES
	(89, 2, 1, 0, 0),
	(90, 2, 1, 0, 0);
/*!40000 ALTER TABLE `mountdata` ENABLE KEYS */;

-- Dumping structure for table kms_316.mulungdojo
DROP TABLE IF EXISTS `mulungdojo`;
CREATE TABLE IF NOT EXISTS `mulungdojo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charid` int(11) NOT NULL DEFAULT 0,
  `stage` tinyint(3) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.mulungdojo: 0 rows
DELETE FROM `mulungdojo`;
/*!40000 ALTER TABLE `mulungdojo` DISABLE KEYS */;
/*!40000 ALTER TABLE `mulungdojo` ENABLE KEYS */;

-- Dumping structure for table kms_316.named
DROP TABLE IF EXISTS `named`;
CREATE TABLE IF NOT EXISTS `named` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` varchar(13) NOT NULL,
  `score` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.named: ~0 rows (approximately)
DELETE FROM `named`;
/*!40000 ALTER TABLE `named` DISABLE KEYS */;
/*!40000 ALTER TABLE `named` ENABLE KEYS */;

-- Dumping structure for table kms_316.named_char
DROP TABLE IF EXISTS `named_char`;
CREATE TABLE IF NOT EXISTS `named_char` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `itemid` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `RC` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `date` varchar(13) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.named_char: ~0 rows (approximately)
DELETE FROM `named_char`;
/*!40000 ALTER TABLE `named_char` DISABLE KEYS */;
/*!40000 ALTER TABLE `named_char` ENABLE KEYS */;

-- Dumping structure for table kms_316.notes
DROP TABLE IF EXISTS `notes`;
CREATE TABLE IF NOT EXISTS `notes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `to` varchar(13) NOT NULL DEFAULT '',
  `from` varchar(13) NOT NULL DEFAULT '',
  `message` text NOT NULL,
  `timestamp` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.notes: 0 rows
DELETE FROM `notes`;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;

-- Dumping structure for table kms_316.nxcode
DROP TABLE IF EXISTS `nxcode`;
CREATE TABLE IF NOT EXISTS `nxcode` (
  `code` varchar(15) NOT NULL,
  `valid` int(11) NOT NULL DEFAULT 1,
  `user` varchar(13) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT 0,
  `item` int(11) NOT NULL DEFAULT 10000,
  PRIMARY KEY (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.nxcode: 0 rows
DELETE FROM `nxcode`;
/*!40000 ALTER TABLE `nxcode` DISABLE KEYS */;
/*!40000 ALTER TABLE `nxcode` ENABLE KEYS */;

-- Dumping structure for table kms_316.oxquiz
DROP TABLE IF EXISTS `oxquiz`;
CREATE TABLE IF NOT EXISTS `oxquiz` (
  `Id` int(5) NOT NULL DEFAULT 0,
  `Question` varchar(500) NOT NULL DEFAULT 'none',
  `Explaination` varchar(500) NOT NULL DEFAULT 'none',
  `Result` varchar(1) NOT NULL DEFAULT 'X',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table kms_316.oxquiz: ~0 rows (approximately)
DELETE FROM `oxquiz`;
/*!40000 ALTER TABLE `oxquiz` DISABLE KEYS */;
/*!40000 ALTER TABLE `oxquiz` ENABLE KEYS */;

-- Dumping structure for table kms_316.party
DROP TABLE IF EXISTS `party`;
CREATE TABLE IF NOT EXISTS `party` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text DEFAULT NULL,
  `title` text DEFAULT NULL,
  `content` text DEFAULT NULL,
  `comment` text DEFAULT NULL,
  `recom` int(11) DEFAULT NULL,
  `recomp` text DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.party: ~0 rows (approximately)
DELETE FROM `party`;
/*!40000 ALTER TABLE `party` DISABLE KEYS */;
/*!40000 ALTER TABLE `party` ENABLE KEYS */;

-- Dumping structure for table kms_316.pets
DROP TABLE IF EXISTS `pets`;
CREATE TABLE IF NOT EXISTS `pets` (
  `uniqueid` int(30) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(13) DEFAULT NULL,
  `level` int(10) unsigned NOT NULL,
  `closeness` int(10) unsigned NOT NULL,
  `fullness` int(10) unsigned NOT NULL,
  `pet_skill` varchar(70) NOT NULL DEFAULT '-1',
  `expiredate` bigint(14) NOT NULL DEFAULT -1,
  `pet_buff` int(8) NOT NULL DEFAULT 0,
  PRIMARY KEY (`uniqueid`)
) ENGINE=MyISAM AUTO_INCREMENT=402637777 DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.pets: 0 rows
DELETE FROM `pets`;
/*!40000 ALTER TABLE `pets` DISABLE KEYS */;
/*!40000 ALTER TABLE `pets` ENABLE KEYS */;

-- Dumping structure for table kms_316.playernpcs
DROP TABLE IF EXISTS `playernpcs`;
CREATE TABLE IF NOT EXISTS `playernpcs` (
  `id` int(11) NOT NULL DEFAULT 0,
  `name` varchar(13) NOT NULL,
  `hair` int(11) NOT NULL,
  `face` int(11) NOT NULL,
  `skin` int(11) NOT NULL,
  `dir` int(11) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `map` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.playernpcs: 0 rows
DELETE FROM `playernpcs`;
/*!40000 ALTER TABLE `playernpcs` DISABLE KEYS */;
/*!40000 ALTER TABLE `playernpcs` ENABLE KEYS */;

-- Dumping structure for table kms_316.playernpcs_equip
DROP TABLE IF EXISTS `playernpcs_equip`;
CREATE TABLE IF NOT EXISTS `playernpcs_equip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `npcid` int(11) NOT NULL,
  `equipid` int(11) NOT NULL,
  `equippos` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.playernpcs_equip: 0 rows
DELETE FROM `playernpcs_equip`;
/*!40000 ALTER TABLE `playernpcs_equip` DISABLE KEYS */;
/*!40000 ALTER TABLE `playernpcs_equip` ENABLE KEYS */;

-- Dumping structure for table kms_316.questactions
DROP TABLE IF EXISTS `questactions`;
CREATE TABLE IF NOT EXISTS `questactions` (
  `questactionid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `questid` int(11) NOT NULL DEFAULT 0,
  `status` int(11) NOT NULL DEFAULT 0,
  `data` blob NOT NULL,
  PRIMARY KEY (`questactionid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.questactions: 0 rows
DELETE FROM `questactions`;
/*!40000 ALTER TABLE `questactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `questactions` ENABLE KEYS */;

-- Dumping structure for table kms_316.questinfo
DROP TABLE IF EXISTS `questinfo`;
CREATE TABLE IF NOT EXISTS `questinfo` (
  `questinfoid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL DEFAULT 0,
  `quest` int(11) NOT NULL DEFAULT 0,
  `data` mediumtext DEFAULT NULL,
  PRIMARY KEY (`questinfoid`),
  KEY `characterid` (`characterid`)
) ENGINE=MyISAM AUTO_INCREMENT=5342 DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.questinfo: 0 rows
DELETE FROM `questinfo`;
/*!40000 ALTER TABLE `questinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `questinfo` ENABLE KEYS */;

-- Dumping structure for table kms_316.questrequirements
DROP TABLE IF EXISTS `questrequirements`;
CREATE TABLE IF NOT EXISTS `questrequirements` (
  `questrequirementid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `questid` int(11) NOT NULL DEFAULT 0,
  `status` int(11) NOT NULL DEFAULT 0,
  `data` blob NOT NULL,
  PRIMARY KEY (`questrequirementid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.questrequirements: 0 rows
DELETE FROM `questrequirements`;
/*!40000 ALTER TABLE `questrequirements` DISABLE KEYS */;
/*!40000 ALTER TABLE `questrequirements` ENABLE KEYS */;

-- Dumping structure for table kms_316.queststatus
DROP TABLE IF EXISTS `queststatus`;
CREATE TABLE IF NOT EXISTS `queststatus` (
  `queststatusid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL DEFAULT 0,
  `quest` int(11) NOT NULL DEFAULT 0,
  `status` int(11) NOT NULL DEFAULT 0,
  `time` int(11) NOT NULL DEFAULT 0,
  `forfeited` int(11) NOT NULL DEFAULT 0,
  `customData` text DEFAULT NULL,
  PRIMARY KEY (`queststatusid`),
  KEY `characterid` (`characterid`)
) ENGINE=MyISAM AUTO_INCREMENT=71403 DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.queststatus: 0 rows
DELETE FROM `queststatus`;
/*!40000 ALTER TABLE `queststatus` DISABLE KEYS */;
INSERT INTO `queststatus` (`queststatusid`, `characterid`, `quest`, `status`, `time`, `forfeited`, `customData`) VALUES
	(71380, 2, 34120, 2, 1599662483, 0, NULL),
	(71381, 2, 33565, 2, 1599662483, 0, NULL),
	(71382, 2, 31851, 2, 1599662483, 0, NULL),
	(71383, 2, 31833, 2, 1599662483, 0, NULL),
	(71384, 2, 3496, 2, 1599662483, 0, NULL),
	(71385, 2, 3470, 2, 1599662483, 0, NULL),
	(71386, 2, 30007, 2, 1599662483, 0, NULL),
	(71387, 2, 3170, 2, 1599662483, 0, NULL),
	(71388, 2, 31179, 2, 1599662483, 0, NULL),
	(71389, 2, 3521, 2, 1599662483, 0, NULL),
	(71390, 2, 31152, 2, 1599662483, 0, NULL),
	(71391, 2, 34015, 2, 1599662483, 0, NULL),
	(71392, 2, 33294, 2, 1599662483, 0, NULL),
	(71393, 2, 34330, 2, 1599662483, 0, NULL),
	(71394, 2, 34585, 2, 1599662483, 0, NULL),
	(71395, 2, 35632, 2, 1599662483, 0, NULL),
	(71396, 2, 35731, 2, 1599662483, 0, NULL),
	(71397, 2, 35815, 2, 1599662483, 0, NULL),
	(71398, 2, 34478, 2, 1599662483, 0, NULL),
	(71399, 2, 34331, 2, 1599662483, 0, NULL),
	(71400, 2, 100114, 2, 1599662483, 0, NULL),
	(71401, 2, 16013, 2, 1599662483, 0, NULL),
	(71402, 2, 123000, 0, 1599662483, 0, NULL);
/*!40000 ALTER TABLE `queststatus` ENABLE KEYS */;

-- Dumping structure for table kms_316.queststatusmobs
DROP TABLE IF EXISTS `queststatusmobs`;
CREATE TABLE IF NOT EXISTS `queststatusmobs` (
  `queststatusmobid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `queststatusid` int(10) unsigned NOT NULL DEFAULT 0,
  `mob` int(11) NOT NULL DEFAULT 0,
  `count` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`queststatusmobid`),
  KEY `queststatusid` (`queststatusid`)
) ENGINE=MyISAM AUTO_INCREMENT=1429 DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.queststatusmobs: 0 rows
DELETE FROM `queststatusmobs`;
/*!40000 ALTER TABLE `queststatusmobs` DISABLE KEYS */;
/*!40000 ALTER TABLE `queststatusmobs` ENABLE KEYS */;

-- Dumping structure for table kms_316.quickslot
DROP TABLE IF EXISTS `quickslot`;
CREATE TABLE IF NOT EXISTS `quickslot` (
  `cid` int(11) NOT NULL,
  `index` int(8) NOT NULL,
  `key` int(8) NOT NULL,
  KEY `cid` (`cid`),
  KEY `cid_2` (`cid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.quickslot: 0 rows
DELETE FROM `quickslot`;
/*!40000 ALTER TABLE `quickslot` DISABLE KEYS */;
/*!40000 ALTER TABLE `quickslot` ENABLE KEYS */;

-- Dumping structure for table kms_316.reactordrops
DROP TABLE IF EXISTS `reactordrops`;
CREATE TABLE IF NOT EXISTS `reactordrops` (
  `reactordropid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reactorid` int(11) NOT NULL,
  `itemid` int(11) NOT NULL,
  `chance` int(11) NOT NULL,
  `questid` int(5) NOT NULL DEFAULT -1,
  PRIMARY KEY (`reactordropid`),
  KEY `reactorid` (`reactorid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr PACK_KEYS=1;

-- Dumping data for table kms_316.reactordrops: 0 rows
DELETE FROM `reactordrops`;
/*!40000 ALTER TABLE `reactordrops` DISABLE KEYS */;
/*!40000 ALTER TABLE `reactordrops` ENABLE KEYS */;

-- Dumping structure for table kms_316.receivedaccount
DROP TABLE IF EXISTS `receivedaccount`;
CREATE TABLE IF NOT EXISTS `receivedaccount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accid` int(11) NOT NULL,
  `itemid` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.receivedaccount: 0 rows
DELETE FROM `receivedaccount`;
/*!40000 ALTER TABLE `receivedaccount` DISABLE KEYS */;
/*!40000 ALTER TABLE `receivedaccount` ENABLE KEYS */;

-- Dumping structure for table kms_316.recom_log
DROP TABLE IF EXISTS `recom_log`;
CREATE TABLE IF NOT EXISTS `recom_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text DEFAULT NULL,
  `recom` text DEFAULT NULL,
  `state` text DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2419 DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.recom_log: ~0 rows (approximately)
DELETE FROM `recom_log`;
/*!40000 ALTER TABLE `recom_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `recom_log` ENABLE KEYS */;

-- Dumping structure for table kms_316.reports
DROP TABLE IF EXISTS `reports`;
CREATE TABLE IF NOT EXISTS `reports` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reporttime` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `reporterid` int(11) NOT NULL,
  `victimid` int(11) NOT NULL,
  `reason` tinyint(4) NOT NULL,
  `chatlog` text NOT NULL,
  `status` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kms_316.reports: 0 rows
DELETE FROM `reports`;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;

-- Dumping structure for table kms_316.rewardsaves
DROP TABLE IF EXISTS `rewardsaves`;
CREATE TABLE IF NOT EXISTS `rewardsaves` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `itemid` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.rewardsaves: 0 rows
DELETE FROM `rewardsaves`;
/*!40000 ALTER TABLE `rewardsaves` DISABLE KEYS */;
/*!40000 ALTER TABLE `rewardsaves` ENABLE KEYS */;

-- Dumping structure for table kms_316.rings
DROP TABLE IF EXISTS `rings`;
CREATE TABLE IF NOT EXISTS `rings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ringId` int(11) NOT NULL DEFAULT 0,
  `itemid` int(11) NOT NULL DEFAULT 0,
  `partnerChrId` int(11) NOT NULL DEFAULT 0,
  `partnerName` varchar(255) NOT NULL,
  `partnerRingId` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.rings: 0 rows
DELETE FROM `rings`;
/*!40000 ALTER TABLE `rings` DISABLE KEYS */;
/*!40000 ALTER TABLE `rings` ENABLE KEYS */;

-- Dumping structure for table kms_316.sakuralogin
DROP TABLE IF EXISTS `sakuralogin`;
CREATE TABLE IF NOT EXISTS `sakuralogin` (
  `no` int(10) NOT NULL AUTO_INCREMENT,
  `id` varchar(255) NOT NULL,
  `time` int(12) NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.sakuralogin: ~0 rows (approximately)
DELETE FROM `sakuralogin`;
/*!40000 ALTER TABLE `sakuralogin` DISABLE KEYS */;
/*!40000 ALTER TABLE `sakuralogin` ENABLE KEYS */;

-- Dumping structure for table kms_316.savedlocations
DROP TABLE IF EXISTS `savedlocations`;
CREATE TABLE IF NOT EXISTS `savedlocations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL,
  `locationtype` int(11) NOT NULL DEFAULT 0,
  `map` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `savedlocations_ibfk_1` (`characterid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.savedlocations: 0 rows
DELETE FROM `savedlocations`;
/*!40000 ALTER TABLE `savedlocations` DISABLE KEYS */;
/*!40000 ALTER TABLE `savedlocations` ENABLE KEYS */;

-- Dumping structure for table kms_316.send
DROP TABLE IF EXISTS `send`;
CREATE TABLE IF NOT EXISTS `send` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text DEFAULT NULL,
  `itemid` text DEFAULT NULL,
  `itemop` text DEFAULT NULL,
  `name2` text DEFAULT NULL,
  `time` text DEFAULT NULL,
  `itime` text DEFAULT NULL,
  `status` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.send: ~0 rows (approximately)
DELETE FROM `send`;
/*!40000 ALTER TABLE `send` DISABLE KEYS */;
/*!40000 ALTER TABLE `send` ENABLE KEYS */;

-- Dumping structure for table kms_316.serialbans
DROP TABLE IF EXISTS `serialbans`;
CREATE TABLE IF NOT EXISTS `serialbans` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `serial` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.serialbans: ~0 rows (approximately)
DELETE FROM `serialbans`;
/*!40000 ALTER TABLE `serialbans` DISABLE KEYS */;
/*!40000 ALTER TABLE `serialbans` ENABLE KEYS */;

-- Dumping structure for table kms_316.shopitems
DROP TABLE IF EXISTS `shopitems`;
CREATE TABLE IF NOT EXISTS `shopitems` (
  `shopitemid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `shopid` int(10) unsigned NOT NULL DEFAULT 0,
  `itemid` int(11) NOT NULL DEFAULT 0,
  `price` bigint(20) NOT NULL DEFAULT 0,
  `position` int(11) NOT NULL DEFAULT 0,
  `pricequantity` int(4) NOT NULL DEFAULT 0,
  `Tab` int(4) NOT NULL DEFAULT 0,
  `quantity` int(11) NOT NULL DEFAULT 1,
  `period` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`shopitemid`),
  KEY `shopid` (`shopid`)
) ENGINE=InnoDB AUTO_INCREMENT=970000866 DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.shopitems: ~0 rows (approximately)
DELETE FROM `shopitems`;
/*!40000 ALTER TABLE `shopitems` DISABLE KEYS */;
/*!40000 ALTER TABLE `shopitems` ENABLE KEYS */;

-- Dumping structure for table kms_316.shops
DROP TABLE IF EXISTS `shops`;
CREATE TABLE IF NOT EXISTS `shops` (
  `shopid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `npcid` int(11) DEFAULT 0,
  PRIMARY KEY (`shopid`)
) ENGINE=MyISAM AUTO_INCREMENT=91100049 DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.shops: 0 rows
DELETE FROM `shops`;
/*!40000 ALTER TABLE `shops` DISABLE KEYS */;
/*!40000 ALTER TABLE `shops` ENABLE KEYS */;

-- Dumping structure for table kms_316.skillmacros
DROP TABLE IF EXISTS `skillmacros`;
CREATE TABLE IF NOT EXISTS `skillmacros` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) NOT NULL DEFAULT 0,
  `position` tinyint(1) NOT NULL DEFAULT 0,
  `skill1` int(11) NOT NULL DEFAULT 0,
  `skill2` int(11) NOT NULL DEFAULT 0,
  `skill3` int(11) NOT NULL DEFAULT 0,
  `name` varchar(13) DEFAULT NULL,
  `shout` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.skillmacros: 0 rows
DELETE FROM `skillmacros`;
/*!40000 ALTER TABLE `skillmacros` DISABLE KEYS */;
/*!40000 ALTER TABLE `skillmacros` ENABLE KEYS */;

-- Dumping structure for table kms_316.skills
DROP TABLE IF EXISTS `skills`;
CREATE TABLE IF NOT EXISTS `skills` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `skillid` int(11) NOT NULL DEFAULT 0,
  `characterid` int(11) NOT NULL DEFAULT 0,
  `skilllevel` int(11) NOT NULL DEFAULT 0,
  `masterlevel` int(11) NOT NULL DEFAULT 0,
  `expiration` bigint(30) NOT NULL DEFAULT -1,
  PRIMARY KEY (`id`),
  KEY `skills_ibfk_1` (`characterid`)
) ENGINE=MyISAM AUTO_INCREMENT=576174 DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.skills: 0 rows
DELETE FROM `skills`;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;

-- Dumping structure for table kms_316.skills_cooldowns
DROP TABLE IF EXISTS `skills_cooldowns`;
CREATE TABLE IF NOT EXISTS `skills_cooldowns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `charid` int(11) NOT NULL,
  `SkillID` int(11) NOT NULL,
  `length` bigint(20) unsigned NOT NULL,
  `StartTime` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=798 DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.skills_cooldowns: 0 rows
DELETE FROM `skills_cooldowns`;
/*!40000 ALTER TABLE `skills_cooldowns` DISABLE KEYS */;
/*!40000 ALTER TABLE `skills_cooldowns` ENABLE KEYS */;

-- Dumping structure for table kms_316.spawn
DROP TABLE IF EXISTS `spawn`;
CREATE TABLE IF NOT EXISTS `spawn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lifeid` int(11) NOT NULL,
  `rx0` int(11) NOT NULL,
  `rx1` int(11) NOT NULL,
  `cy` int(11) NOT NULL,
  `fh` int(11) NOT NULL,
  `type` varchar(11) NOT NULL,
  `dir` int(11) NOT NULL,
  `mapid` int(11) NOT NULL,
  `mobTime` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=643 DEFAULT CHARSET=latin1;

-- Dumping data for table kms_316.spawn: 0 rows
DELETE FROM `spawn`;
/*!40000 ALTER TABLE `spawn` DISABLE KEYS */;
/*!40000 ALTER TABLE `spawn` ENABLE KEYS */;

-- Dumping structure for table kms_316.spawns_profession
DROP TABLE IF EXISTS `spawns_profession`;
CREATE TABLE IF NOT EXISTS `spawns_profession` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idd` int(11) NOT NULL,
  `f` int(11) NOT NULL,
  `fh` int(11) NOT NULL,
  `type` varchar(1) NOT NULL,
  `cy` int(11) NOT NULL,
  `rx0` int(11) NOT NULL,
  `rx1` int(11) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `mobtime` int(11) DEFAULT 1000,
  `mid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.spawns_profession: 0 rows
DELETE FROM `spawns_profession`;
/*!40000 ALTER TABLE `spawns_profession` DISABLE KEYS */;
/*!40000 ALTER TABLE `spawns_profession` ENABLE KEYS */;

-- Dumping structure for table kms_316.steelskills
DROP TABLE IF EXISTS `steelskills`;
CREATE TABLE IF NOT EXISTS `steelskills` (
  `cid` int(11) NOT NULL,
  `skillid` int(11) NOT NULL,
  `skilllevel` int(7) NOT NULL,
  `index` tinyint(1) NOT NULL,
  `slot` tinyint(1) NOT NULL,
  `equipped` tinyint(1) NOT NULL DEFAULT 0,
  KEY `cid` (`cid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.steelskills: 0 rows
DELETE FROM `steelskills`;
/*!40000 ALTER TABLE `steelskills` DISABLE KEYS */;
/*!40000 ALTER TABLE `steelskills` ENABLE KEYS */;

-- Dumping structure for table kms_316.storages
DROP TABLE IF EXISTS `storages`;
CREATE TABLE IF NOT EXISTS `storages` (
  `storageid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `accountid` int(11) NOT NULL DEFAULT 0,
  `slots` int(11) NOT NULL DEFAULT 0,
  `meso` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`storageid`),
  KEY `accountid` (`accountid`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.storages: 0 rows
DELETE FROM `storages`;
/*!40000 ALTER TABLE `storages` DISABLE KEYS */;
INSERT INTO `storages` (`storageid`, `accountid`, `slots`, `meso`) VALUES
	(7, 1, 4, 0);
/*!40000 ALTER TABLE `storages` ENABLE KEYS */;

-- Dumping structure for table kms_316.trade_cm
DROP TABLE IF EXISTS `trade_cm`;
CREATE TABLE IF NOT EXISTS `trade_cm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chr_name` text DEFAULT NULL,
  `from_name` text DEFAULT NULL,
  `cash` int(11) DEFAULT NULL,
  `meso` text DEFAULT NULL,
  `confirm` text DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr ROW_FORMAT=DYNAMIC;

-- Dumping data for table kms_316.trade_cm: ~0 rows (approximately)
DELETE FROM `trade_cm`;
/*!40000 ALTER TABLE `trade_cm` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade_cm` ENABLE KEYS */;

-- Dumping structure for table kms_316.trocklocations
DROP TABLE IF EXISTS `trocklocations`;
CREATE TABLE IF NOT EXISTS `trocklocations` (
  `trockid` int(11) NOT NULL AUTO_INCREMENT,
  `characterid` int(11) DEFAULT NULL,
  `mapid` int(11) DEFAULT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`trockid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- Dumping data for table kms_316.trocklocations: 0 rows
DELETE FROM `trocklocations`;
/*!40000 ALTER TABLE `trocklocations` DISABLE KEYS */;
/*!40000 ALTER TABLE `trocklocations` ENABLE KEYS */;

-- Dumping structure for table kms_316.website_events
DROP TABLE IF EXISTS `website_events`;
CREATE TABLE IF NOT EXISTS `website_events` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `author` varchar(16) NOT NULL,
  `date` varchar(32) NOT NULL,
  `type` varchar(100) NOT NULL,
  `status` varchar(32) NOT NULL,
  `content` text NOT NULL,
  `views` int(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kms_316.website_events: 0 rows
DELETE FROM `website_events`;
/*!40000 ALTER TABLE `website_events` DISABLE KEYS */;
/*!40000 ALTER TABLE `website_events` ENABLE KEYS */;

-- Dumping structure for table kms_316.website_news
DROP TABLE IF EXISTS `website_news`;
CREATE TABLE IF NOT EXISTS `website_news` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `author` varchar(16) NOT NULL,
  `date` varchar(32) NOT NULL,
  `type` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `views` int(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kms_316.website_news: 0 rows
DELETE FROM `website_news`;
/*!40000 ALTER TABLE `website_news` DISABLE KEYS */;
/*!40000 ALTER TABLE `website_news` ENABLE KEYS */;

-- Dumping structure for table kms_316.wishlist
DROP TABLE IF EXISTS `wishlist`;
CREATE TABLE IF NOT EXISTS `wishlist` (
  `characterid` int(11) NOT NULL,
  `sn` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=euckr ROW_FORMAT=FIXED;

-- Dumping data for table kms_316.wishlist: 0 rows
DELETE FROM `wishlist`;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
