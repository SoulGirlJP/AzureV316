// LeaderBoard Rankings
// Script : 2192002


importPackage(java.io);
importPackage(java.sql);
importPackage(java.lang);
importPackage(Packages.connections.Database);


var LEVEL_RANK  = 0;
var REBORN_RANK = 1;
var MESO_RANK   = 2;
var SEARCH_RANK = 3;
var damagehit_RANK = 4;
var damage_RANK = 5;
var gRank = 0;
function start()
{
	St = -1;
	action(1, 0, 0);
}

function action(M, T, S)
{
	if(M != 1)
	{
		cm.dispose();
		return;
	}

	if(M == 1)
	St++;
	else
	St--;

	if(St == 0)
	{
		cm.sendSimple("#b\r\n#L"+LEVEL_RANK+"#레벨 랭킹 확인 하기 (내 순위 : "+getMYLevelRank(getIdByName(cm.getPlayer().getName()))+"위)"
			+ "\r\n#L"+REBORN_RANK+"#환생 랭킹 확인 하기 (내 순위 : "+getMYRebornsRank(getIdByName(cm.getPlayer().getName()))+"위)"
			+ "\r\n#L"+MESO_RANK+"#메소 랭킹 확인 하기 (내 순위 : "+getMYMesoRank(getIdByName(cm.getPlayer().getName()))+"위)"
			+ "\r\n#L"+damage_RANK+"#추뎀 랭킹 확인 하기 (내 순위 : "+getMYdamageRank(getIdByName(cm.getPlayer().getName()))+"위)"
			+ "\r\n#L"+damagehit_RANK+"#퍼뎀 랭킹 확인 하기 (내 순위 : "+getMYdamagehitRank(getIdByName(cm.getPlayer().getName()))+"위)"
			+ "\r\n\r\n#r#L"+SEARCH_RANK+"#종합 랭킹 확인 하기");
	}


	else if(St == 1)
	{
		S1 = S;
		Sb = new StringBuilder();
		con = MYSQL.getConnection();
		switch(S1)
		{
			case LEVEL_RANK:
			Con = con.prepareStatement("SELECT * FROM characters where gm = 0 ORDER BY level DESC LIMIT 20").executeQuery();
			while(Con.next())
			{
				getPlayerJobs(Con.getInt("job"));
				getLevelRank();
			}
			if(cm.getPlayer().getGMLevel() != 0)
			{
				cm.sendSimple(Sb.toString());
			}
			else
			{
				cm.sendOk(Sb.toString());
			}
			Con.close();
			con.close();
			break;
			
			case REBORN_RANK:
			Con = con.prepareStatement("SELECT * FROM characters where gm = 0 and reborns > 0 ORDER BY reborns DESC LIMIT 50").executeQuery();
			while(Con.next())
			{
				getPlayerJobs(Con.getInt("job"));
				getRebornsRank();
			}
			if(cm.getPlayer().getGMLevel() != 0)
			{
				cm.sendSimple(Sb.toString());
			}
			else
			{
				cm.sendOk(Sb.toString());
			}
			Con.close();
			con.close();
			break;

			case MESO_RANK:
			Con =  con.prepareStatement("SELECT * FROM characters where gm = 0 ORDER BY meso DESC LIMIT 50").executeQuery();
			while(Con.next())
			{
				getMesoRank();
			}
			if(cm.getPlayer().getGMLevel() > 0)
			{
				cm.sendSimple(Sb.toString());
			}
			else
			{
				cm.sendOk(Sb.toString());
			}
			Con.close();
			con.close();
			break;

			case damagehit_RANK:
			Con = con.prepareStatement("SELECT * FROM characters where gm = 0 and damagehit > 0 ORDER BY damagehit DESC LIMIT 30").executeQuery();
			while(Con.next())
			{
				getPlayerJobs(Con.getInt("job"));
				getdamagehitRank();
			}
			if(cm.getPlayer().getGMLevel() != 0)
			{
				cm.sendSimple(Sb.toString());
			}
			else
			{
				cm.sendOk(Sb.toString());
			}
			Con.close();
			con.close();
			break;

			case damage_RANK:
			Con =  con.prepareStatement("SELECT * FROM characters where gm = 0 ORDER BY damage DESC LIMIT 30").executeQuery();
			while(Con.next())
			{
				getdamageRank();
			}
			if(cm.getPlayer().getGMLevel() > 0)
			{
				cm.sendSimple(Sb.toString());
			}
			else
			{
				cm.sendOk(Sb.toString());
			}
			Con.close();
			con.close();
			break;

			case SEARCH_RANK:
			cm.sendGetText("검색할 캐릭터의 닉네임을 입력해주세요. 일부 캐릭터는 검색이 제한될 수 있습니다.");
			break;

			default:
			cm.dispose();
			break;
		}
	}

	else if(St == 2)
	{
		S2 = S;
		if(S1 != 3)
		{
			getCharInfo(S2);
		}
		else
		{
			if(getIdByName(cm.getText()) == null)
			{
				cm.sendOk("입력한 캐릭터는 존재하지 않습니다.");
				cm.dispose();
				return;
			}
			cm.sendOk(""+targetName(getIdByName(cm.getText()))+" 캐릭터의 랭킹 정보입니다.#b"
				+ "\r\n　레벨 랭킹 : #e"+getMYLevelRank(getIdByName(cm.getText()))+"#n위 (Lv."+targetLevel(getIdByName(cm.getText()))+")"
				+ "\r\n　환생 랭킹 : #e"+getMYRebornsRank(getIdByName(cm.getText()))+"#n위 ("+targetReborns(getIdByName(cm.getText()))+" 번)"
				+ "\r\n　추뎀 랭킹 : #e"+getMYdamageRank(getIdByName(cm.getText()))+"#n위 ("+Comma(targetdamage(getIdByName(cm.getText())))+" 추뎀)"
				+ "\r\n　퍼뎀 랭킹 : #e"+getMYdamagehitRank(getIdByName(cm.getText()))+"#n위 ("+targetdamagehit(getIdByName(cm.getText()))+" 퍼뎀)"
				+ "\r\n　메소 랭킹 : #e"+getMYMesoRank(getIdByName(cm.getText()))+"#n위 ("+Comma(targetMeso(getIdByName(cm.getText())))+" 메소)");
			cm.dispose();
		}
	}
}

function getMYLevelRank(i)
{
	var level = 0;
	var con = MYSQL.getConnection();
	var ret = 0;
	var rank = con.prepareStatement("SELECT COUNT(*) FROM characters where gm = 0 and `level` > ?");
	rank.setInt(1, targetLevel(i));
	 eq = rank.executeQuery();
	 if (eq.next())
	{
		ret = eq.getInt("count(*)")+1;
	}
	rank.close();
	eq.close();
	con.close();
	return ret;
}

function getMYRebornsRank(i)
{
	var level = 0;
	var con = MYSQL.getConnection();
	var ret = 0;
	var rank = con.prepareStatement("SELECT COUNT(*) FROM characters where gm = 0 and reborns > 0 and `reborns` > ?");
	rank.setInt(1, targetReborns(i));
	 eq = rank.executeQuery();
	 if (eq.next())
	{
		ret = eq.getInt("count(*)")+1;
	}
	rank.close();
	eq.close();
	con.close();
	return ret;
}

function getMYMesoRank(i)
{
	var level = 0;
	var con = MYSQL.getConnection();
	var ret = 0;
	var rank = con.prepareStatement("SELECT COUNT(*) FROM characters where gm = 0 and `meso` > ?");
	rank.setLong(1, targetMeso(i));
	eq = rank.executeQuery();
	 if (eq.next())
	{
		ret = eq.getInt("count(*)")+1;
	}
	rank.close();
	eq.close();
	con.close();
	return ret;
}

function getMYdamagehitRank(i)
{
	var level = 0;
	var con = MYSQL.getConnection();
	var ret = 0;
	var rank = con.prepareStatement("SELECT COUNT(*) FROM characters where gm = 0 and damagehit > 0 and `damagehit` > ?");
	rank.setInt(1, targetdamagehit(i));
	 eq = rank.executeQuery();
	 if (eq.next())
	{
		ret = eq.getInt("count(*)")+1;
	}
	rank.close();
	eq.close();
	con.close();
	return ret;
}

function getMYdamageRank(i)
{
	var level = 0;
	var con = MYSQL.getConnection();
	var ret = 0;
	var rank = con.prepareStatement("SELECT COUNT(*) FROM characters where gm = 0 and `damage` > ?");
	rank.setLong(1, targetdamage(i));
	eq = rank.executeQuery();
	 if (eq.next())
	{
		ret = eq.getInt("count(*)")+1;
	}
	rank.close();
	eq.close();
	con.close();
	return ret;
}




function getLevelRank()
{
	gRank++;
	Sb.append("#fn돋움체#")

	if(gRank == 1)
	{
	}
	if(cm.getPlayer().getGMLevel() != 0)
	{
		Sb.append("#L"+Con.getInt("accountid")+"#");
	}
	if(gRank < 10)
	{
		Sb.append("#Cgray#").append("00").append("#b#e").append(""+eval(gRank)+"");
	}
	else if (gRank >= 10 && gRank < 100)
	{
		Sb.append("#Cgray#").append("0").append("#b#e").append(""+eval(gRank)+"");
	}
	else
	{
		Sb.append("#Cgray#").append("#b#e").append(""+eval(gRank)+"");
	}
	Sb.append("위#n#k")
	if(Con.getString("name") == cm.getPlayer().getName())
	{
		Sb.append("　#rLv.").append(eval(Con.getInt("level"))).append("　#e");
		Sb.append(Con.getString("name")).append("#k#n");
	}
	else
	{
		Sb.append("　Lv.").append(eval(Con.getInt("level"))).append("　");
		Sb.append(Con.getString("name"))
	}
	Sb.append(" #Cgray#(").append(job).append(")").append("\r\n");
}

function getRebornsRank()
{
	gRank++;
	Sb.append("#fn돋움체#")
	if(gRank == 1)
	{
	}

	if(cm.getPlayer().getGMLevel() != 0)
	{
		Sb.append("#L"+Con.getInt("accountid")+"#");
	}

	if(gRank < 10)
	{
		Sb.append("#Cgray#").append("00").append("#b#e").append(""+eval(gRank)+"");
	}
	else if (gRank >= 10 && gRank < 100)
	{
		Sb.append("#Cgray#").append("0").append("#b#e").append(""+eval(gRank)+"");
	}
	else
	{
		Sb.append("#Cgray#").append("#b#e").append(""+eval(gRank)+"");
	}
	Sb.append("위#n#k")
	if(Con.getString("name") == cm.getPlayer().getName())
	{
		Sb.append("　#r").append(eval(Con.getInt("reborns"))).append("　#e");
		Sb.append(Con.getString("name")).append("#k#n");
	}
	else
	{
		Sb.append("　").append(eval(Con.getInt("reborns"))).append("　");
		Sb.append(Con.getString("name"))
	}
	Sb.append(" #Cgray#(").append(job).append(")").append("\r\n");
}

function getMesoRank()
{
	gRank++;
	Sb.append("#fn돋움체#")

	if(gRank == 1)
	{
	}

	if(cm.getPlayer().getGMLevel() != 0)
	{
		Sb.append("#L"+Con.getInt("accountid")+"#");
	}

	if(gRank < 10)
	{
		Sb.append("#Cgray#").append("00").append("#b#e").append(""+eval(gRank)+"");
	}
	else if (gRank >= 10 && gRank < 100)
	{
		Sb.append("#Cgray#").append("0").append("#b#e").append(""+eval(gRank)+"");
	}
	else
	{
		Sb.append("#Cgray#").append("#b#e").append(""+eval(gRank)+"");
	}
	Sb.append("위#n#k")
	if(Con.getString("name") == cm.getPlayer().getName())
	{
		Sb.append("　#r#e").append(Comma(eval(Con.getLong("meso")))).append(" 메소　");
		Sb.append(Con.getString("name")).append("#k#n\r\n");
	}
	else
	{
		Sb.append("　").append(Comma(eval(Con.getLong("meso")))).append(" 메소　");
		Sb.append(Con.getString("name")).append("\r\n");
	}
}

function getdamagehitRank()
{
	gRank++;
	Sb.append("#fn돋움체#")
	if(gRank == 1)
	{
	}

	if(cm.getPlayer().getGMLevel() != 0)
	{
		Sb.append("#L"+Con.getInt("accountid")+"#");
	}

	if(gRank < 10)
	{
		Sb.append("#Cgray#").append("00").append("#b#e").append(""+eval(gRank)+"");
	}
	else if (gRank >= 10 && gRank < 100)
	{
		Sb.append("#Cgray#").append("0").append("#b#e").append(""+eval(gRank)+"");
	}
	else
	{
		Sb.append("#Cgray#").append("#b#e").append(""+eval(gRank)+"");
	}
	Sb.append("위#n#k")
	if(Con.getString("name") == cm.getPlayer().getName())
	{
		Sb.append("　#r").append(eval(Con.getInt("damagehit"))).append("　#e");
		Sb.append(Con.getString("name")).append("#k#n");
	}
	else
	{
		Sb.append("　").append(eval(Con.getInt("damagehit"))).append("%　 ");
		Sb.append(Con.getString("name"))
	}
	Sb.append(" #Cgray#(").append(job).append(")").append("\r\n");
}

function getdamageRank()
{
	gRank++;
	Sb.append("#fn돋움체#")

	if(gRank == 1)
	{
	}

	if(cm.getPlayer().getGMLevel() != 0)
	{
		Sb.append("#L"+Con.getInt("accountid")+"#");
	}

	if(gRank < 10)
	{
		Sb.append("#Cgray#").append("00").append("#b#e").append(""+eval(gRank)+"");
	}
	else if (gRank >= 10 && gRank < 100)
	{
		Sb.append("#Cgray#").append("0").append("#b#e").append(""+eval(gRank)+"");
	}
	else
	{
		Sb.append("#Cgray#").append("#b#e").append(""+eval(gRank)+"");
	}
	Sb.append("위#n#k")
	if(Con.getString("name") == cm.getPlayer().getName())
	{
		Sb.append("　#r#e").append(Comma(eval(Con.getLong("damage")))).append(" 추뎀　");
		Sb.append(Con.getString("name")).append("#k#n\r\n");
	}
	else
	{
		Sb.append("　").append(Comma(eval(Con.getLong("damage")))).append(" 추뎀　");
		Sb.append(Con.getString("name")).append("\r\n");
	}
}

function getCharInfo(i)
{
	con = MYSQL.getConnection();
	Con = con.prepareStatement("SELECT * FROM characters WHERE accountid = ?");
	Con.setString(1, i);
	Con = Con.executeQuery();
	Sb  = new StringBuilder();
	Ext = false;
	while (Con.next())
	{
		Ext = true;
		getExp = (Con.getInt("level") == 250) ? 0 : eval(Con.getLong("exp"))/Packages.constants.GameConstants.getExpNeededForLevel(eval(Con.getInt("level"))) * 100;
		Sb.append("#fs12#");
		Sb.append("#e닉네임#fs18# #fs12##n: #e#b").append(Con.getString("name")).append("#Cgray##n (생성일자 : ").append(Con.getString("createdate")).append(")#k\r\n");
		Sb.append(" ㄴ#e레벨#n : #b").append(eval(Con.getInt("level"))).append("#k (");
		Sb.append(Comma(eval(Con.getLong("exp")))).append(" / "+Comma(Packages.constants.GameConstants.getExpNeededForLevel(eval(Con.getInt("level"))))+"").append(") ");
		Sb.append("["+getExp.toFixed(2)+"%]\r\n");
		Sb.append(" ㄴ#e스텟#n : #bSTR#k#fs11#(").append((eval(Con.getInt("str")))).append(")  #fs12##bDEX#k#fs11#(").append(eval(Con.getInt("dex")));
		Sb.append(")  #fs12##bINT#k#fs11#(").append(eval(Con.getInt("int"))).append(")  #fs12##bLUK#k#fs11#(").append(eval(Con.getInt("luk"))).append(")#fs12#\r\n");
		Sb.append("#e　　  　 #n#rHP#k[#r").append(Comma(eval(Con.getInt("hp")))).append("#k / #r").append(Comma(eval(Con.getInt("maxhp")))).append("#k]  ");
		Sb.append("#bMP#k[#b").append(Comma(eval(Con.getInt("mp")))).append("#k / #b").append(Comma(eval(Con.getInt("maxmp")))).append("#k]\r\n");
		Sb.append(" ㄴ#e메소#n : #b").append(Comma(eval(Con.getLong("meso"))));
		Sb.append(" ㄴ#e추뎀#n : #g").append(Comma(eval(Con.getLong("damage"))));
		Sb.append(" ㄴ#b#e퍼뎀#n : #r").append(Comma(eval(Con.getInt("damagehit")))).append("%　 ");
		Sb.append("#k\r\n\r\n\r\n");
	}
	if (Ext)
	{
		cm.sendOk(Sb.toString());
	}
	else
	{
		cm.sendOk("없는 캐릭터의 정보가 입력되었습니다. 다시 시도해주세요.");
	}
	Con.close();
	con.close();
    	cm.dispose();
}

function checkRows(i)
{
	con = MYSQL.getConnection();
	Con = con.prepareStatement("SELECT COUNT(*) FROM characters WHERE name = ?");
	Con.setString(1, i);
	Con = Con.executeQuery();
	Con.next();
	var ret = Con.getInt("count(*)");
	Con.close();
	con.close();
	return ret;
}

function getIdByName(i)
{
	con = MYSQL.getConnection();
	Con = con.prepareStatement("SELECT * FROM characters WHERE name = ?");
	Con.setString(1, i);
	Con = Con.executeQuery();
	Con.next();
	var ret = Con.getInt("id");
	Con.close();
	con.close();
	return ret;
}

function targetName(i)
{
	con = MYSQL.getConnection();
	Con = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
	Con.setString(1, i);
	Con = Con.executeQuery();
	Con.next();
	var ret = Con.getString("name");
	Con.close();
	con.close();
	return ret;
}


function targetLevel(i)
{
	con = MYSQL.getConnection();
	Con = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
	Con.setString(1, i);
	Con = Con.executeQuery();
	Con.next();
	var ret = Con.getString("level");
	Con.close();
	con.close();
	return ret;
}

function targetMeso(i)
{
	con = MYSQL.getConnection();
	Con = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
	Con.setString(1, i);
	Con = Con.executeQuery();
	Con.next();
	var ret = Con.getString("meso");
	Con.close();
	con.close();
	return ret;
}

function targetReborns(i)
{
	con = MYSQL.getConnection();
	Con = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
	Con.setString(1, i);
	Con = Con.executeQuery();
	Con.next();
	var ret = Con.getString("reborns");
	Con.close();
	con.close();
	return ret;
}

function targetdamagehit(i)
{
	con = MYSQL.getConnection();
	Con = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
	Con.setString(1, i);
	Con = Con.executeQuery();
	Con.next();
	var ret = Con.getString("damagehit");
	Con.close();
	con.close();
	return ret;
}

function targetdamage(i)
{
	con = MYSQL.getConnection();
	Con = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
	Con.setString(1, i);
	Con = Con.executeQuery();
	Con.next();
	var ret = Con.getString("damage");
	Con.close();
	con.close();
	return ret;
}

function Comma(i)
{
	var reg = /(^[+-]?\d+)(\d{3})/;
	i+= '';
	while (reg.test(i))
	i = i.replace(reg, '$1' + ',' + '$2');
	return i;
}

function getPlayerJobs(i)
{
	switch(i)
	{
		case 100: job = "검사"; break;
		case 200: job = "마법사"; break;
		case 300: job = "궁수"; break;
		case 400: job = "도적"; break;
		case 500: job = "해적"; break;
		case 110: job = "파이터"; break;
		case 111: job = "크루세이더"; break;
		case 112: job = "히어로"; break;
		case 120: job = "페이지"; break;
		case 121: job = "나이트"; break;
		case 122: job = "팔라딘"; break;
		case 130: job = "스피어맨"; break;
		case 131: job = "버서커"; break;
		case 132: job = "다크나이트"; break;
		case 210: job = "위자드(불, 독)"; break;
		case 211: job = "메이지(불, 독)"; break;
		case 212: job = "아크메이지(불,독)"; break;
		case 220: job = "위자드(썬, 콜)"; break;
		case 221: job = "메이지(썬, 콜)"; break;
		case 222: job = "아크메이지(썬,콜)"; break;
		case 230: job = "클레릭"; break;
		case 231: job = "프리스트"; break;
		case 232: job = "비숍"; break;
		case 310: job = "헌터"; break;
		case 311: job = "레인저"; break;
		case 312: job = "보우마스터";  break;
		case 320: job = "사수"; break;
		case 321: job = "저격수"; break;
		case 322: job = "신궁"; break;
                        case 330: job = "에인션트 아처"; break;
                        case 331: job = "체이서"; break;
                        case 332: job = "패스파인더"; break;
		case 410: job = "어쌔신"; break;
		case 411: job = "허밋"; break;
		case 412: job = "나이트로드";  break;
		case 420: job = "시프"; break;
		case 421: job = "시프마스터"; break;
		case 422: job = "섀도어"; break;
		case 430: job = "세미듀어러"; break;
		case 431: job = "듀어러"; break;
		case 432: job = "듀얼마스터"; break;
		case 433: job = "슬레셔"; break;
		case 434: job = "듀얼블레이더";  break;
		case 510: job = "인파이터"; break;
		case 511: job = "버커니어"; break;
		case 512: job = "바이퍼"; break;
		case 520: job = "건슬링거"; break;
		case 521: job = "발키리"; break;
		case 522: job = "캡틴"; break;
		case 530: job = "캐논슈터"; break;
		case 531: job = "캐논블래스터"; break;
		case 532: job = "캐논마스터"; break;

		case 1100: case 1110: case 1111: case 1112: job = "소울마스터";  break;
		case 1200: case 1210: case 1211: case 1212: job = "플레임위자드"; break;
		case 1300: case 1310: case 1311: case 1312: job = "윈드브레이커"; break;
		case 1400: case 1410: case 1411: case 1412: job = "나이트워커"; break;
		case 1500: case 1510: case 1511: case 1512: job = "스트라이커"; break;

		case 2100: case 2110: case 2111: case 2112: job = "아란"; break;
		case 2200: case 2210: case 2211: case 2212: case 2213:
		case 2214: case 2215: case 2216: case 2217: case 2218: job = "에반"; break;
		case 2300: case 2310: case 2311: case 2312: job = "메르세데스"; break;
		case 2400: case 2410: case 2411: case 2412: job = "팬텀";  break;
		case 2700: case 2710: case 2711: case 2712: job = "루미너스"; break;
		case 2500: case 2510: case 2511: case 2512: job = "은월"; break;

		case 3100: case 3110: case 3111: case 3112: job = "데몬슬레이어"; break;
		case 3101: case 3120: case 3121: case 3122: job = "데몬어벤져"; break;
		case 3200: case 3210: case 3211: case 3212: job = "배틀메이지"; break;
		case 3300: case 3310: case 3311: case 3312: job = "와일드헌터"; break;
		case 3500: case 3512: case 3511: case 3512: job = "메카닉"; break;
		case 3600: case 3610: case 3611: case 3612: job = "제논"; break;
		case 3700: case 3710: case 3711: case 3712: job = "블래스터"; break;
	
		case 5100: case 5110: case 5111: case 5112: job = "미하일"; break;
		case 6100: case 6110: case 6111: case 6112: job = "카이저"; break;
		case 6500: case 6510: case 6511: case 6512: job = "엔젤릭버스터"; break;

		case 14200: case 14210: case 14211: case 14212: job = "키네시스"; break;	
		default: "초보자"; break;
	}
}