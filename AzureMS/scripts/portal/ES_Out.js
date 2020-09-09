function enter(pi) {
    //var map = pi.getPlayer().getClient().getChannelServer().getMapFactory().getMap(100000000);
    //var to = ChannelServer.getInstance(pi.getPlayer().getClient().getChannel()).getMapFactory().getMap(100000000);
    if (pi.getPlayer().getClient().getChannel() != 1) {
	pi.getPlayer().crossChannelWarp(pi.getPlayer().getClient(), 100000000, 1);
    }
//pi.getPlayer().changeMap(map, map.getPortal(0));
   //pi.getPlayer().crossChannelWarp(pi.getPlayer().getClient(), 957000000, 2);
}