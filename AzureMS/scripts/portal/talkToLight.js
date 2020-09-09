/* RED 1st impact
    Explorer tut
    Made by Daenerys
*/
function enter(pi) {
	if(pi.isAllReactorState(1008010, 0) == false){
		pi.openNpc(10310);
	}else{
		pi.openNpc(10310, "ExplorerTut09");
	}
}
