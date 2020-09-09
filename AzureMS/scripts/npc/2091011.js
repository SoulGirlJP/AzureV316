var status = -1;
var s1;
var s2;

function start() {
    status = -1;
    action (1, 0, 0);
}

function charsize() {
	for (i = 1; i <= 63; i++)
	{
		if (cm.getClient().getChannelServer().getMapFactory().getMap(925070000 + (i * 100)).getCharactersSize() > 0)
		{
		return 1;
		}
	}
	return 0;
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        status --;
    }
    if (mode == 1) {
        status++;
    }

    if (status == 0) {
	if (cm.getPlayer().getMapId() == 925020001) {
		cm.sendSimple("우리 사부님은 무릉에서 최고로 강한 분이지. 그런 분에게 네가 도전하겠다고? 나중에 후회하지마. #b\r\n#L0# 무릉 도장에 도전해볼게.#l\r\n#L1# 무릉 도장이 뭐지?#l\r\n#L2# 무릉 도장에서 받을 수 있는 보상을 확인하고 싶어.#l\r\n#L3# 오늘 남은 도전 횟수를 확인하고 싶어.#l\r\n#L4# 무릉 심신 수련관에 입장하고 싶어.#l");
	} else if (cm.getPlayer().getMapId() == 925080000) {
		cm.sendYesNo("심신 수련장을 퇴장하시겠습니까? 사용한 수련권은 복구되지 않습니다.");
	}
    } else if (status == 1) {
	s1 = selection;
	if (cm.getPlayer().getMapId() != 925020001) {
		cm.dispose();
		cm.warp(925020000);
	} else {
		if (selection == 0) {
			cm.dispose();
			if (charsize() > 0) {
				cm.sendOk("이미 누군가가 무릉도장에 도전 중이야.");
			} else if (cm.getPlayer().getParty() != null) {
				cm.sendOk("파티로는 입장할 수 없어! 혼자서 도전하라구! 겁쟁이냐?\r\n\r\n\r\n");
			} else {
				cm.dojowarp(925070100);
				cm.getPlayer().getMap().startMapEffect("제한시간은 15분, 늦지 않게 몬스터를 쓰러트리고 다음 층으로 올라가면 돼!", 5120024, 3000);
			}
		} else if (selection == 1) {
			cm.sendNext("우리 사부님은 무릉에서 가장 강한 분이야. 그런 사부님께서 만드신 곳이 바로 이 무릉 도장이라는 것이지. 무릉 도장은 총 62층과 사부님의 별층으로 이루어진 총 63층의 건물이야. 하나하나 올라가면서 자신을 수련할 수 있어. 물론 너의 실력으로는 끝까지 가기 힘들겠지만.");
		} else if (selection == 2) {
			cm.sendSimple("무릉도장에서 보상을 얻을 수 있는 방법은 두 가지야. 초절정의 실력과 강한 힘으로 상위 랭커가 되거나, 혹은 꾸준한 정진을 통해 얻을 수 있는 포인트를 통한 물물교환.\r\n\r\n#b#L0#랭커 보상에 대해 묻는다.\r\n#L1#참여 보상(포인트)에 대해 묻는다.");
		} else if (selection == 3) {
			cm.dispose();
			cm.sendNext("오늘 무릉도장에는 3번 참여할 수 있어. 그런 건 알아서 세어보라고.");
		} else if (selection == 4) {
			cm.sendYesNo("무릉 심신수련관이 일반인들에게도 개방하도록 결정되었어.\r\n다만, 강하거나 성실한 자만 들어갈 수 있지. 라오 아저씨가 주는 부적을 가져와. 부적이 내재하고 있는 시간에 따라 들여보내주지.\r\n\r\n입장하겠어?\r\n#b(심신수련관은 입장 시 해당 캐릭터의 레벨에 따라 자동으로 경험치를 습득합니다.)");
		}
	}
    } else if (status == 2) {
	s2 = selection;
	if (s1 == 1) {
		cm.sendNextPrev("사부님이 계신 63층을 제외한 각 층엔 메이플 월드의 보스들이 그곳을 지키고 있지. 자세한 사정은 나도 몰라. 사부님만이 아실 뿐.");
	} else if (s2 == 0) {
		cm.sendNext("말 그대로야. 사부님께서 상위권 랭커에게 보상품을 하사하시지. 강함이야말로 우리 무릉도장의 최고 가치니까. 그리고 그 강함에 대한 보상은 당연한거 아니겠어?");
	} else if (s2 == 1) {
		cm.sendNext("너의 무릉도장 참여도에 따라 포인트가 지급될 거야.\r\n\r\n 1. 도전할 때마다 돌파하는 층수에 비례한 포인트 지급\r\n 2. 자신이 속한 랭킹 구간의 지난 주 전체 랭킹 백분율에 따른 포인트 지급\r\n\r\n이 두 가지 기준으로 포인트가 지급될 거야.\r\n\r\n\r\n");
	} else if (s1 == 4) {
		cm.sendOk("심신수련관 입장용 부적을 가지고 있어야 수련관에 입장 가능하다고. 내 옆에 리오 아저씨에게 가 봐.");
	}
    } else if (status == 3) {
	if (s1 == 1) {
		cm.sendNextPrev("1층부터 9층, 11층부터 19층엔 하나의 보스가 등장해. 다음으로 넘어가려면 딱 하나만 처치하면 된다고.");
	} else if (s2 == 0) {
		cm.sendNextPrev("좀 더 공정한 경쟁을 위해 레벨에 따라 랭킹 구간도 달라.\r\n\r\n*입문 : 105 ~ 140 레벨\r\n*숙련 : 141 ~ 180 레벨\r\n*통달 : 181 레벨 이상");
	} else if (s2 == 1) {
		cm.sendNextPrev("층수에 비례한 포인트는 1층당 10 포인트가 기본적으로 지급되고, 10층당 100 포인트가 추가적으로 지급되는 방식이야.\r\n\r\n\r\n");
	}
    } else if (status == 4) {
	if (s1 == 1) {
		cm.sendNextPrev("21층부터 29층엔 하나의 보스와 다섯의 부하들이 등장해. 보스와 부하들을 모두 처치해야 다음 층으로 넘어갈 수 있어.");
	} else if (s2 == 0) {
		cm.dispose();
		cm.sendNextPrev("당연한 얘기지만 랭킹 구간에 따라 보상도 달라.\r\n지금 네가 어느 랭킹 구간에 속해있는지 꼭 확인하라고.\r\n\r\n설마 네가 지나온 랭킹 구간에서 상위 랭커였다고 보상을 달라고 떼쓰진 않겠지?\r\n#b지금 속해있는 랭킹 구간으로 보상을 지급#k한다는 점을 잊지 말라고. 자세한 내용은 무릉도장 순위표에서 확인 해보도록 해.");
	} else if (s2 == 1) {
		cm.sendNextPrev("랭킹 백분율에 따른 포인트는 더 강한 자들이 속한 랭킹 구간일수록, 그리고 더 좋은 결과를 낼수록 더 많이 가져가게 될 거야. 아니꼬우면 강해지라고.\r\n\r\n");
	}
    } else if (status == 5) {
	if (s1 == 1) {
		cm.sendNextPrev("31층부터 39층엔 둘 이상의 보스를 상대해야 해.\r\n\r\n33, 36, 39층을 제외한 층에선 1대1로 다수의 보스들과,\r\n33, 36, 39층에선 한꺼번에 등장하는 다수의 보스들과 겨뤄야하지.");
	} else if (s2 == 1) {
		cm.dispose();
		cm.sendNextPrev("아, 그리고 포인트는 최대 #b10만 포인트#k를 넘게 가지고 있을 순 없어. 재깍재깍 쓰는 습관을 가지라고.\r\n\r\n\r\n");
	}
    } else if (status == 6) {
	if (s1 == 1) {
		cm.sendNextPrev("40층부터는 10층 단위로 메이플 월드의 네임드 보스들이 등장해. 여기선 #r15초#k마다 포션을 사용할 수 있어. 어줍잖은 실력으로 만나면 고생 좀 할 거야.");
	}
    } else if (status == 7) {
	if (s1 == 1) {
		cm.sendNextPrev("41층 이후부터도 #r15초#k마다 포션을 사용할 수 있어. 왜냐고? 그야 네가 들어가보면 알게 되겠지. 흐흐흐...");
	}
    } else if (status == 8) {
	if (s1 == 1) {
		cm.sendNextPrev("41층부터는 다시 하나의 보스들이 등장해. 여기에 더해서 45층과 50층엔 다수의 보스들이 등장하니까 방심하지 말라고.");
	}
    } else if (status == 9) {
	if (s1 == 1) {
		cm.sendNextPrev("51층부터 56층엔 시그너스 기사단장과 시그너스가 기다리고 있을 거야. 네가 알던 온화한 시그너스가 아니라는 건 이미 알고 있겠지?");
	}
    } else if (status == 10) {
	if (s1 == 1) {
		cm.sendNextPrev("57층부터 62층엔 사부님의 제자들이 지키고 있어. 여기까지 올라간다면 정말 대단한 실력을 지녔단 뜻이겠지.");
	}
    } else if (status == 11) {
	if (s1 == 1) {
		cm.sendNextPrev("그들의 실력은 네가 밖에서 알던 것과는 조금 다를 거야...흐흐흐...뭐 알아서 잘 해보라고.");
	}
    } else if (status == 12) {
	if (s1 == 1) {
		cm.dispose();
		cm.sendNextPrev("게다가 사부님의 결계로 인해서 네가 메이플 월드에서 발휘하던 힘의 10분의 1밖에 쓰지 못하니까 들어가서 당황하지마.\r\n알아들었으면 어서 들어가 보라고. 몸이 근질근질하지 않아?");
	}
    }
}