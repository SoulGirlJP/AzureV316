function act(){
    rm.mapMessage(5, "차원의 균열이 <차원 균열의 조각> 으로 채워졌습니다!");
    rm.changeMusic("Bgm09/TimeAttack");
    rm.spawnMonster(8500000, -410, -400);
    rm.closeMapDoor(220080000, 3600);
    rm.scheduleTimeMoveMap(220080000, 220080001, 3600, true);
    rm.mapMessage(6, "[알림] 보스 레이드 타임아웃매니저 - 파풀라투스 - 작동되었습니다. 행운을 빕니다!");
    rm.mapMessage(5, "[경고] 보스 레이드가 시작되어 현재 채널에 현재 보스 쿨타임이 시작됩니다.");
}