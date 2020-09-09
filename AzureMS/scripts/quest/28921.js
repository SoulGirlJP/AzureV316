var status = -1;
function start(mode, type, selection) {
    status++;
	if (mode != 1) {
	    if(type == 1 && mode == 0){
		    status -= 2;
		}else{
			qm.dispose();
			return;
		}
	}
	if (status == 0){
		qm.sendSimple("Welcome to Red Leaf High, new kid! Stick around a while and I bet you'll build up lots of Friendships. Not sure how Friendship works? Lemme spill the beans!#b\r\n#L0# What's Friendship? #l\r\n#L1# View current Friendship level#l\r\n#L2# End Conversation #l");
	}else if (status == 1){
		if(selection==0){
			qm.sendOk("Attend Red Leaf High School to gain Friendship with the popular students and build up your reputation as the toughest kid in class! Earning Friendship will also give you access to the Four Pillars of Heaven Cash Shop, where you can purchase Totems with mesos! Remember, you'll need Friendship if you want to unlock the good stuff! ");
			qm.dispose();
		}
		if(selection==1){
			qm.dispose();
			qm.openNpc(9330193);
		}
		if(selection==2){
			qm.dispose();
		}
		//qm.forceStartQuest();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}
