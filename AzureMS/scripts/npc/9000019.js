/*
Production: venids@nate.com
Blog: www.blog.naver.com/sha_adm

Use: Rock Paper Scissors Gambling

This script file cannot be modified or distributed without permission
*/

var cchoice; 
var choice; 
var Frock = "#fUI/UIWindow.img/RpsGame/Frock#"; 
var Fpaper = "#fUI/UIWindow.img/RpsGame/Fpaper#"; 
var Fscissor = "#fUI/UIWindow.img/RpsGame/Fscissor#"; 
var rock = "#fUI/UIWindow.img/RpsGame/rock#"; 
var paper = "#fUI/UIWindow.img/RpsGame/paper#"; 
var scissor = "#fUI/UIWindow.img/RpsGame/scissor#"; 
var win = "　　　#fUI/UIWindow.img/RpsGame/win#"; 
var lose = "　　　#fUI/UIWindow.img/RpsGame/lose#"; 
var draw = "　　　#fUI/UIWindow.img/RpsGame/draw#"; 
var money = 0;
var gamestatus = false;
var isdraw = false;


function start(){
status = -1;
action(1,0,0);
}

function action(mode,type,selection){
if(mode != 1) {
cm.dispose();
}
if(mode == 1)
status ++;
else
cm.dispose();

if(status == 0){
if(cm.getMeso() >= 30000000)
cm.sendSimple("#r#eWould you like to try rock, paper, scissors??\r\n(30 million mesos per round)\r\n@2x if you win@#k#n\r\n\r\n#L1#"+rock+"#l#L2#"+scissor+"#l#L3#"+paper+"#l");
else{
cm.sendOk("There seems to be a lack of meso. Rock, paper, scissors costs 30 million per plate.");
cm.dispose();
}
} else if (status == 1){
if(selection == 1)
choice = "rock";
else if (selection == 2)
choice = "scissor";
else if (selection == 3)
choice = "paper";
var rand = Math.floor(Math.random() * 3);
if(rand == 0)
cchoice = "Frock";
else if (rand == 1)
cchoice = "Fpaper";
else if (rand == 2)
cchoice = "Fscissor";
else
cchoice = "Fscissor";

//Rock, paper, scissors start
if(choice == "rock"){
choice = "#fUI/UIWindow.img/RpsGame/rock#"; 
if(cchoice == "Fscissor"){
money = 60000000
cchoice = "#fUI/UIWindow.img/RpsGame/Fscissor#"; 
gamestatus = true;
isdraw = false;
}else if (cchoice == "Fpaper"){
gamestatus = false;
isdraw = false;
cchoice = "#fUI/UIWindow.img/RpsGame/Fpaper#"; 
}else if (cchoice == "Frock"){
isdraw = true;
gamestatus = true;
cchoice = "#fUI/UIWindow.img/RpsGame/Frock#"; 
}

} else if(choice == "scissor"){
choice = "#fUI/UIWindow.img/RpsGame/scissor#"; 
if(cchoice == "Fpaper"){
money = 60000000
cchoice = "#fUI/UIWindow.img/RpsGame/Fpaper#"; 
gamestatus = true;
isdraw = false;
}else if (cchoice == "Frock"){
gamestatus = false;
isdraw = false;
cchoice = "#fUI/UIWindow.img/RpsGame/Frock#"; 
}else if (cchoice == "Fscissor"){
isdraw = true;
gamestatus = true;
cchoice = "#fUI/UIWindow.img/RpsGame/Fscissor#"; 
}

} else if(choice == "paper"){
choice = "#fUI/UIWindow.img/RpsGame/paper#"; 
if(cchoice == "Frock"){
money = 30000000
cchoice = "#fUI/UIWindow.img/RpsGame/Frock#"; 
gamestatus = true;
isdraw = false;
}else if (cchoice == "Fscissor"){
gamestatus = false;
isdraw = false;
cchoice = "#fUI/UIWindow.img/RpsGame/Fscissor#"; 
}else if (cchoice == "Fpaper"){
isdraw = true;
gamestatus = true;
cchoice = "#fUI/UIWindow.img/RpsGame/Fpaper#"; 
}
}
cm.gainMeso(-30000000);
if(gamestatus == true){
 if(isdraw == true){
 cm.sendOk("  Opponent"+(cchoice)+"vs"+(choice)+"Myself\r\n"+draw);
 cm.dispose();
 } else {
 cm.sendOk("  Opponent"+cchoice+"vs"+choice+"Myself\r\n"+win);
 cm.gainMeso(money);
 cm.dispose();
 }
}else if (gamestatus == false){
 cm.sendOk("  Opponent"+cchoice+"vs"+choice+"Myself\r\n"+lose);
 cm.dispose();
}

}

}