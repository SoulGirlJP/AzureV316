importPackage(Packages.client);
importPackage(java.sql);
importPackage(java.lang);
importPackage(Packages.database);

var Jessica = 0;
var i = 0;

function login(jess) {
var con = MYSQL.getConnection().prepareStatement("SELECT * FROM accounts WHERE name = ?");
con.setString(1,jess);
var eq = con.executeQuery();
while(eq.next()) {
i = 1;
MYSQL.getConnection().prepareStatement("update accounts set loggedin= '0' where name = '" + jess + "'").executeUpdate();
}
return;
}

function start() { Jessica = -1; action(1, 0, 0); }
function action(music, type, girl) { (music == 1) ? Jessica++ : (Jessica--, cm.dispose());
    (Jessica == 0) ? cm.sendGetText("현재 접속중인 오류가 뜨는 아이디를 입력 해 주세요.")
    :(Jessica == 1) ? (login(cm.getText()), (i != 0) ? (cm.sendOk("해제 되었습니다."), cm.dispose())
    : (cm.sendOk("가입된 아이디가 아닙니다."), cm.dispose())) : cm.dispose();
}