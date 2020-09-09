var DEBUG = false;
var HIDDEN_ITEM = 4031325, MINE_ITEM = 5021013, EXPLODED_ITEM = 5120009;
var Modes = [
    //MAX COL = 7
    //MAX ROW = 7 (Without scrolling)
    //Difficulty Name, Rows, Cols, Number of Mines
    ["#g쉬움                 #k", 5, 5, 4],
    ["#b중간                 #k", 6, 6, 8],
    ["#r어려움             #k", 7, 7, 15]
];

/* Don't touch these variables below unless you know what you're doing */
var status;
var Board, Revealed;
var VALUE_REVEALED = 1, VALUE_HIDDEN = 0;
var ModeSelected = -1, StartTime = -1, NumCols = -1,
NumRows = -1, NumMines = -1, LeftToBeRevealed = -1;
var Neighbors = [
    //dX ,dY
    [-1, -1], [-1, 0], [-1, 1], [0, -1],
    [0, 1],   [1, -1], [1, 0],  [1, 1]
];
var Numbers = [
3990009, 3990000, 3990001, 3990002, 3990003,
3990004, 3990005, 3990006, 3990007, 3990008
];
/* Don't touch these variables above unless you know what you're doing */

function start() {
    status = -1;
    action(1, 0, 0);
}
function Header() {
    return "#d============ 지뢰찾기 ============#k\r\n\r\n";
}
function ConstructModeSelectionString() {
    var ret = "";
    for (var i = 0;i < Modes.length;i++) {
        ret += "#L"+i+"#"+Modes[i][0]+" ("+Modes[i][1]+"x"+Modes[i][2]+") 폭탄: "+Modes[i][3]+"#l\r\n";
    }
    return ret;
}
function InitializeBoard() {
    NumCols = Modes[ModeSelected][1];
    NumRows = Modes[ModeSelected][2]
    NumMines = Modes[ModeSelected][3];
    LeftToBeRevealed = NumCols * NumRows - NumMines;
    Board = new Array(NumCols);
    for (var i = 0;i < NumCols;i++) {
        Board[i] = new Array(NumRows);
    }
    Revealed = new Array(NumCols);
    for (i = 0;i < NumCols;i++) {
        Revealed[i] = new Array(NumRows);
        for (var j = 0;j < NumRows;j++) {
            Revealed[i][j] = VALUE_HIDDEN;
        }
    }
    InitializeMines();
    InitializeMineCounts();
    InitializeBlankSpaces();
}
function InitializeMines() {
    var AllPosition = new Array(NumRows * NumCols);
    for (var i = 0;i < NumCols;i++) {
        for (var j = 0;j < NumRows;j++) {
            AllPosition[(i * NumCols) + j] = [i,j];
        }
    }
    Shuffle(AllPosition);
    for (i = 0;i < NumMines && AllPosition.length > 0;i++) {
        var NewMine = AllPosition.pop();
        Board[NewMine[0]][NewMine[1]] = MINE_ITEM;
    }
}
function InitializeMineCounts() {
    for (var r = 0;r < NumCols;r++) {
        for (var c = 0;c < NumRows;c++) {
            if (Board[r][c] != MINE_ITEM) {
                var Count = 0;
                for (var k = 0;k < Neighbors.length;k++) {
                    var NewR = r + Neighbors[k][0];
                    var NewC = c + Neighbors[k][1]
                    if (ValidPosition(NewR, NewC) && Board[NewR][NewC] == MINE_ITEM) {
                        Count++;
                    }
                }
                Board[r][c] = Numbers[Count];
            }
        }
    }
}
function InitializeBlankSpaces() {
    for (var i = 0;i < NumCols;i++) {
        for (var j = 0;j < NumRows;j++) {
            if (Board[i][j] == null) {
                Board[i][j] = HIDDEN_ITEM;
            }
        }
    }
}
function ConstructBoardString() {
    var ret = "#d찾아야할 숫자 개수->#k#e#k " + LeftToBeRevealed + "\r\n";
    var num = 0;
    for (var i = 0;i < NumCols;i++) {
        for (var j = 0;j < NumRows;j++) {
            if (Revealed[i][j] == VALUE_REVEALED) {
                ret += "#L"+num+"##v"+Board[i][j]+"##l";
            } else {
                if (DEBUG && Board[i][j] == MINE_ITEM) {
                    ret += "#L"+num+"##v"+MINE_ITEM+"##l";
                } else {
                    ret += "#L"+num+"##v"+HIDDEN_ITEM+"##l";
                }
            }
            num++;
        }
        ret += "\r\n";
    }
    return ret;
}
function ConstructEndBoardString() {
    var ret = "";
    var num = 0;
    for (var i = 0;i < NumCols;i++) {
        for (var j = 0;j < NumRows;j++) {
            if (Revealed[i][j] == VALUE_REVEALED) {
                if (Board[i][j] == MINE_ITEM) {
                    ret += "#v"+EXPLODED_ITEM+"#";
                } else {
                    ret += "#v"+Board[i][j]+"#";
                }
            } else {
                if (Board[i][j] == MINE_ITEM) {
                    ret += "#v"+MINE_ITEM+"#";
                } else {
                    ret += "#v"+HIDDEN_ITEM+"#";
                }
            }
            num++;
        }
        ret += "\r\n";
    }
    return ret;
}
function Shuffle(arr) {
    var i = arr.length;
    if (i == 0)
        return;
    while (--i) {
        var j = Math.floor( Math.random() * ( i + 1 ) );
        var tempi = arr[i];
        var tempj = arr[j];
        arr[i] = tempj;
        arr[j] = tempi;
    }
}
function ValidPosition(r, c) {
    return r >= 0 && r < NumRows && c >= 0 && c < NumCols;
}
function GetPositionFromSelection(sel) {
    return [(sel / NumCols) | 0, (sel % NumCols) | 0];
}
function IsGameOver() {
    return LeftToBeRevealed == 0;
}
function GetGameTime(End) {
    var Diff = End - StartTime;
    var MilliSec = (Diff % 1000) | 0;
    Diff /= 1000;
    var Min = (Diff / 60) | 0;
    var Sec = (Diff % 60) | 0;
    return ((Min < 10) ? "0" + Min : Min) + ":" + ((Sec < 10) ? "0" + Sec : Sec)
        + "." + ((MilliSec < 100) ? "0" + ((MilliSec < 10) ? "0" + MilliSec : MilliSec) : MilliSec);

}
function action(mode, type, selection) {
    if (mode == -1 || mode == 0)
        cm.dispose();
    else {
        if (status != 2)
            status++;

        if (status == 0) {
            cm.sendSimple(Header()+"난이도를 선택해주세요.\r\n"+ConstructModeSelectionString())
        } else if (status == 1) {
            if (selection < 0 || selection > Modes.length) { //Packet Edits
                cm.dispose();
                return;
            }
            ModeSelected = selection;
            InitializeBoard();
            cm.sendSimple(Header() + ConstructBoardString());
        } else if (status == 2) {
            if (StartTime == -1) {
                StartTime = (new Date()).getTime();
            }
            var SendStr = Header();
            var Move = GetPositionFromSelection(selection);
            if (!ValidPosition(Move[0], Move[1])) {
                cm.sendOk("PE Detected ...");
                return;
            }
            if (Revealed[Move[0]][Move[1]] == VALUE_REVEALED) { //Already went there
                SendStr += "You have already moved in that space ...\r\n\r\n" + ConstructBoardString();
                cm.sendSimple(SendStr);
                return;
            }
            Revealed[Move[0]][Move[1]] = VALUE_REVEALED;
            LeftToBeRevealed--;
            SendStr += ConstructBoardString();
            if (Board[Move[0]][Move[1]] == MINE_ITEM) {
                //Lose
                cm.sendOk(Header() + ConstructEndBoardString() + "\r\n게임에서 패배하였습니다!\r\n게임진행시간 : " + GetGameTime((new Date()).getTime()));
                cm.dispose();
            } else {
                if (IsGameOver()) {
                    cm.sendOk(Header() + ConstructEndBoardString() + "\r\n게임에서 승리하였습니다!\r\n게임진행시간 : " + GetGameTime((new Date()).getTime()));
		    cm.gainMeso(500000);
                    cm.dispose();
                } else {
                    cm.sendSimple(SendStr);
                }
            }
        }
    }
}