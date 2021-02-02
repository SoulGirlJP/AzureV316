// Bot Checker 
// Author @Brandon

importPackage(Packages.constants);

var randomCode = '';
var status = 0;
var codeLength = 6;
var attempts = 0;
var adminNames = ["Brandon"];
// Ghetto but you put the name of GMs here, Better method would be read from SQL

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    }
    else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
		}
        else {
            status--;
		}
	}
		
    if (status == 0) {
        randomCode = '';
        var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        for (var i = 0; i < codeLength; i++ ) {
            randomCode += characters.charAt(Math.floor(Math.random() * characters.length));
        }

       cm.sendGetTextNoESC("This is the automatic bot check please type in the message below\r\n#bAttempts:" + attempts + "\r\n" + "#r" + randomCode);
    }
    else if(status == 1) {
        var answer = cm.getText();
        if(answer == randomCode) {
            cm.sendNextNoESC("You passed the automatic bot check.");
            for(var i = 0; i < adminNames.length; i++) {
                var admins = cm.getChar().getClient().getChannelServer().getPlayerStorage().getCharacterByName(adminNames[i]);
                if(admins != null) {
                    admins.dropMessage(6, cm.getChar().getName() + " has completed the bot check with " + attempts + " attempts.");
                }
            }
            return cm.dispose();
        }
        else {
            status -= 2;
            attempts++;
            // This is where you wanna add auto banning if they pass a certain amount of attempts
            cm.sendNextNoESC("You failed the automatic bot check.");
        }
    }
}	
