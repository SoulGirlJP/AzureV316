/* @Author Lerk
 * 
 * 2111000.js: Zakum Party Quest Chest - summons 3 "Mimics"
*/

function act(){
    rm.playerMessage(5, "숨어있던 몬스터가 등장했습니다.");
    rm.spawnMonster(9300004,3);
}