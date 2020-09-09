var status = -1;

 

function start() {
　　　　status = -1;
　　　　action (1, 0, 0);
}

 

function action(mode, type, selection) {
　　　　if (mode != 1) {
　　　　　　　　cm.dispose();
　　　　　　　　return;
　　　　}
　　　　if (mode == 1) {
　　　　　　　　status++;
　　　　}
　　　　if (status == 0) {
　　　　　　　　cm.sendGetText("#b鯵球験 紳虞昔 拭辞 莞革績聖 設公竺舛馬舎陥檎 郊蚊球軒為柔艦陥.#k");
　　　　} else if (status == 1) {
　　　　　　　　var name = cm.getText();
　　　　if (name.length() > 2 && name.length() < 6)
　　　　{
　　　　　　　　cm.setName(name);
　　　　　　　　cm.sendOk("#b" + name + "#k生稽 莞革績 痕井戚 刃戟 鞠醸柔艦陥.");
　　　　　　　　cm.dispose();
　　　　}
　　　　else
　　　　{
　　　　　　　　cm.sendOk("#b2越切 戚馬 暁澗 6越切 戚雌 脊艦陥.#k");
　　　　　　　　cm.dispose();
　　　　　　　　}
　　　　}
}
