function enter(pi) {
    if (pi.getQuestStatus(1008) == 1)
        pi.showWZEffect("UI/tutorial.img/22", 1);
    else if (pi.getQuestStatus(1020) == 1)
        pi.showWZEffect("UI/tutorial.img/27", 1);
    return false;
}