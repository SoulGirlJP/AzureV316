/* RED 1st impact
	New Mastery Book Sale
    Made by Daenerys
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
		qm.sendNext("Tsk, I don't know you. Why don't I know you? My name is #p2080008#, and I specialize in  #e#bMastery Books#n#k. And why do I specialize in Mastery Books? For YOU, my friend, for YOU. But I don't think you appreciate it enough.");
	} else if (status == 1) {
        qm.sendNextPrev("My Mastery Books are special. They can be used on ANY skill, you see?");
    } else if (status == 2) {	  	 
        qm.sendNextPrev("I've had to sell my legs to get these books. But I did it for you. Find me whenever you're ready to purchase a book. I've also sent some to the General Stores in #m100000000# and #m240000000# .");    
    } else if (status == 3) {	
	    qm.sendNextPrev("I think you're suspect by now that these books aren't cheap, but I'm sure you'll find a way to get the mesos. I sure did!");
    } else if (status == 4) {	
		qm.forceStartQuest();
		qm.dispose();
	}
}