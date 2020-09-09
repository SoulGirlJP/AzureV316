var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 0 && status == 6) {
            qm.sendNext("뭘 망설이시는 건가요? 만약 거대한 폴암이 반응하지 않는대도 전 실망하지 않을 거예요. 어서 가서 거대한 폴암을 만져보세요. 적당한 부분을 #b클릭#k하면 돼요.");
            qm.dispose();
            return;
        }
        if (mode > 0)
            status++;
        else
            status--;
        if (status == 0) {
            qm.sendNext("검은 마법사와 싸운 영웅들... 그들에 대한 정보는 거의 남아있지 않아요. 예언서에도 겨우 영웅이 다섯 명이라는 기록만 있을 뿐, 외모에 관한 단서는 전혀 없어요. 혹시 뭔가 기억나는 건 없으신가요?");
        } else if (status == 1) {
            qm.sendNextPrevS("기억나는 것은 전혀 없는데...",3);
        } else if (status == 2) {
            qm.sendNextPrev("역시 그렇군요. 하긴 검은 마법사의 저주가 그리 호락호락할리 없죠. 하지만 그렇다 해도 당신이 영웅이 확실한 이상 과거의 접점이 어딘가 있을 것 같은데. 뭐가 있을까요? 전투때문인지 무기나 옷도 모두 잃어버리셨고... 아, 그렇지. #b무기#k!");
        } else if (status == 3) {
            qm.sendNextPrevS("무기?",3);
        } else if (status == 4) {
            qm.sendNextPrev("예전에 얼음 속에서 영웅들을 발굴하다가 굉장한 무기를 발견했어요. 아마도 영웅이 사용하던 것으로 추정되어서 마을 중앙에 가져다 놓았죠. 지나다니다가 보지 않으셨나요? #b아주 거대한 폴암#k을...\r\n#i4032372# 이렇게 생긴 건데...");
        } else if (status == 5) {
            qm.sendNextPrevS("그러고 보니 이상할 정도로 거대한 폴암이 마을에 있었지.",3);
        } else if (status == 6) {
            qm.askAcceptDecline("네, 바로 그거예요. 기록에 의하면 영웅의 무기는 주인을 알아본다고 해요. 만약 당신이 거대한 폴암을 사용하던 영웅이라면, 거대한 폴암을 잡았을 때 뭔가 반응이 올 거예요. 어서 가서 #b거대한 폴암을 클릭해 주세요.#k");
        } else if (status == 7) {
            qm.forceStartQuest();
            qm.forceCompleteQuest();
            qm.sendNext("만약 거대한 폴암이 반응한다면 당신은 거대한 폴암을 사용하던 영웅, #b아란#k일 거예요.");
            qm.dispose();
        }
    }
}