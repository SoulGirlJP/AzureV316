importPackage(Packages.constants);

var status = 0;

function start() {
 status = -1;
 action(1, 0, 0);
}

function action(mode, type, selection) {
 if (mode == -1) {
  cm.dispose();
                 return;
 } else {
  if (mode == 1)
   status++;
  else
   status--;

                if(mode == 0){
                  cm.dispose();
                  return;
                 }
  if (status == 0) {
cm.sendSimple("I'm giving you riding skills #b[Mount Trader]#k Skills."+"#k\r\n#L3#[#bSkill Learning#k] Riding");


   } else if (selection == 3) {
       
       if (cm.getPlayer().getRC() < 99999999999999999) {
           
           cm.sendOk("Its being coded. Please contact Souls in Discord\r\n#rMSG to Souls, CODE FASTER!!!!!");
           cm.dispose();
           
       }
       
       else {
	cm.teachSkill(80001398,1,1);
	cm.teachSkill(80001400,1,1);
	cm.teachSkill(80001404,1,1);
	cm.teachSkill(80001435,1,1);
	cm.teachSkill(80001440,1,1);
	cm.teachSkill(80001503,1,1);
	cm.teachSkill(80001505,1,1);
	cm.teachSkill(80001517,1,1);
	cm.teachSkill(80001531,1,1);
	cm.teachSkill(80001533,1,1);
	cm.teachSkill(80001549,1,1);
	cm.teachSkill(80001552,1,1);
	cm.teachSkill(80001557,1,1);
	cm.teachSkill(80001563,1,1);
	cm.teachSkill(80001336,1,1);
	cm.teachSkill(80001639,1,1);
	cm.teachSkill(80001244,1,1);
	cm.teachSkill(80001707,1,1);
	cm.teachSkill(80001769,1,1);
	cm.teachSkill(80001775,1,1);
	cm.teachSkill(80001257,1,1);
	cm.teachSkill(80001785,1,1);
	cm.teachSkill(80001792,1,1);
	cm.teachSkill(80001811,1,1);
              cm.teachSkill(80001813,1,1);
	cm.teachSkill(80001867,1,1);
	cm.teachSkill(80001923,1,1);
	cm.teachSkill(80001975,1,1);
	cm.teachSkill(80002271,1,1);
	cm.teachSkill(80002270,1,1);
	cm.teachSkill(80001246,1,1);
	cm.teachSkill(80001492,1,1);
	cm.teachSkill(80001084,1,1);
	cm.teachSkill(80002305,1,1);
	cm.teachSkill(80002307,1,1);
	cm.teachSkill(80002314,1,1);
	cm.teachSkill(80002315,1,1);
	cm.teachSkill(80002318,1,1);
	cm.teachSkill(80002319,1,1);
	cm.teachSkill(80002321,1,1);
	cm.teachSkill(80002335,1,1);
	cm.teachSkill(80002345,1,1);
	cm.teachSkill(80002347,1,1);
	cm.teachSkill(80001989,1,1);
              cm.teachSkill(80001990,1,1);
	cm.teachSkill(80001991,1,1);
	cm.teachSkill(80001993,1,1);
	cm.teachSkill(80001995,1,1);
	cm.teachSkill(80001997,1,1);
	cm.teachSkill(80002219,1,1);
	cm.teachSkill(80002220,1,1);
	cm.teachSkill(80002221,1,1);
	cm.teachSkill(80002222,1,1);
	cm.teachSkill(80002223,1,1);
	cm.teachSkill(80002225,1,1);
	cm.teachSkill(80002229,1,1);
	cm.teachSkill(80002234,1,1);
	cm.teachSkill(80002235,1,1);
	cm.teachSkill(80002236,1,1);
	cm.teachSkill(80002238,1,1);
	cm.teachSkill(80002240,1,1);
	cm.teachSkill(80002248,1,1);
              cm.teachSkill(80002250,1,1);
	cm.teachSkill(80002258,1,1);
	cm.teachSkill(80002261,1,1);
	cm.teachSkill(80002262,1,1);
	cm.teachSkill(80002265,1,1);
	cm.teachSkill(80002266,1,1);
	
	cm.sendOk("I've completed all the rides!!");

	cm.dispose();
}
   }
 }
} 
