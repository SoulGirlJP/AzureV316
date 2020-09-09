var status = -1;

function action(mode, type, selection) {
    cm.dispose();
    cm.openNpc(getNpc(cm.getMapId()));
}

function getNpc(mapid) {
    switch (mapid / 10000000) {
        case 10:
            return 1012000;
        case 26:
            return 9000090;
        default:
            return 1012000;
    }
}