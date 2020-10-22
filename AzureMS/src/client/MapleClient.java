package client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.script.ScriptEngine;

import connections.Database.MYSQLException;
import client.Character.MapleCharacter;
import client.Character.MapleCharacterUtil;
import client.Community.MapleGuild.MapleGuildCharacter;
import handlers.ChatHandler.MapleMultiChatCharacter;
import client.Community.MapleParty.MaplePartyCharacter;
import client.Community.MapleParty.MaplePartyOperation;
import client.Community.MapleUserTrade;
import client.Stats.PlayerStats;
import constants.ServerConstants;
import constants.Data.ServerType;
import connections.Database.MYSQL;
import launcher.AdminGUI.AdminToolPacket;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import launcher.AdminGUI.AdminToolStart;
import launcher.ServerPortInitialize.CashShopServer;
import launcher.ServerPortInitialize.ChannelServer;
import launcher.ServerPortInitialize.LoginServer;
import launcher.LauncherHandlers.MapleItemHolder;
import launcher.Utility.WorldBroadcasting;
import launcher.Utility.WorldCommunity;
import connections.Packets.LoginPacket;
import connections.Packets.MainPacketCreator;
import connections.Crypto.MapleCrypto;
import connections.Packets.PacketUtility.ReadingMaple;
import launcher.AdminGUI.AdminTool;
import scripting.NPC.NPCScriptManager;
import server.Shops.IMapleCharacterShop;
import tools.FileoutputUtil;
import tools.LoggerChatting;
import tools.Timer.PingTimer;

public class MapleClient {

	public static final transient byte LOGIN_NOTLOGGEDIN = 0, LOGIN_SERVER_TRANSITION = 1, LOGIN_LOGGEDIN = 2,
			LOGIN_WAITING = 3, CASH_SHOP_TRANSITION = 4, LOGIN_CS_LOGGEDIN = 5, CHANGE_CHANNEL = 6;
	public static final int DEFAULT_CHARSLOT = 6;
	public static final String CLIENT_KEY = "CLIENT";
	public final static AttributeKey<MapleClient> CLIENTKEY = AttributeKey.valueOf("mapleclient_netty");
	private transient MapleCrypto send, receive;
	private Channel session;
	private MapleCharacter player;
	private PlayerStats playerstats;
	private int channel = 1, accId = 1, world;
	private boolean loggedIn = false, serverTransition = false;
	private transient Calendar tempban = null;
	private String accountName;
	private transient long lastPong, lastPing;
	private boolean gm;
	private byte greason = 1, gender = -1;
	private int charslots = DEFAULT_CHARSLOT;
	public transient short loginAttempt = 0;
	public boolean pinged = false, isCS = false, isAuction = false, allowLoggin = false;
	private transient List<Integer> allowedChar = new LinkedList<Integer>();
	private transient Set<String> macs = new HashSet<>();
	private transient Map<String, ScriptEngine> engines = new HashMap<String, ScriptEngine>();
	private transient ScheduledFuture<?> idleTask = null;
	private transient String secondPassword, tempIP = "";
	private boolean usingSecondPassword = false;
	private final transient Lock npc_mutex = new ReentrantLock();
	private int idcode1, idcode2;
	private long lastNpcClick = 0;
	public Timer processTimer;
	private String serial = null;
	private final transient Lock mutex = new ReentrantLock(true);
	private final transient Lock mutex2 = new ReentrantLock(true);
	public byte[] isSend = null;
	public byte[] isRecv = null;
	public ServerType serverType = null;
	public byte sessionCount = 1;

	public MapleClient(/* IoSession */Channel session, MapleCrypto send, MapleCrypto receive) {
		this.session = session;
		this.send = send;
		this.receive = receive;
	}

	public void sendPacket(final byte[] data) {
		if (data == null) {
			return;
		}
		session.writeAndFlush(data);
	}

	public final Lock getLock() {
		return mutex;
	}

	public final Lock getLock2() {
		return mutex2;
	}

	public String getIp() {
		return session.remoteAddress().toString().split(":")[0];
	}

	public final /* IoSession */ Channel getSession() {
		return session;
	}

	public void send(byte[] p) {
		getSession().writeAndFlush(p);
	}

	public final MapleCrypto getReceiveCrypto() {
		return receive;
	}

	public final MapleCrypto getSendCrypto() {
		return send;
	}

	public void addChrSlot(int i, int id) {
		setChrSlot(id);
	}

	public int getChrSlot() {
		return 40;
	}

	public final void setChrSlot(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("UPDATE accounts SET chrslot = ? WHERE id = ?");
			ps.setInt(1, (getChrSlot() + 1));
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public Map<Integer, Integer> getCharacterCard() {
		Map<Integer, Integer> chrcard = new HashMap<Integer, Integer>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT * FROM charactercard WHERE accountid = ?");
			ps.setInt(1, accId);
			rs = ps.executeQuery();
			while (rs.next()) {
				chrcard.put(rs.getInt("position"), rs.getInt("cardid"));
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			throw new MYSQLException("error getChrcard", e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return chrcard;
	}

	public void setCharacterCard(Map<Integer, Integer> card) {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psu = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("DELETE FROM charactercard WHERE accountid = ?");
			ps.setInt(1, accId);
			ps.executeUpdate();
			ps.close();
			for (Entry<Integer, Integer> cardlist : card.entrySet()) {
				psu = con.prepareStatement("INSERT INTO charactercard (accountid, cardid, position) VALUES (?, ?, ?)");
				psu.setInt(1, accId);
				psu.setInt(2, cardlist.getValue());
				psu.setInt(3, cardlist.getKey());
				psu.executeUpdate();
				psu.close();
			}
			con.close();
		} catch (SQLException e) {
			throw new MYSQLException("error setChrcard", e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (psu != null) {
					psu.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public final Lock getNPCLock() {
		return npc_mutex;
	}

	public MapleCharacter getPlayer() {
		return player;
	}

	public PlayerStats getPlayerStat() {
		return playerstats;
	}

	public void setPlayer(MapleCharacter player) {
		this.player = player;
	}

	public void createdChar(final int id) {
		allowedChar.add(id);
	}

	public final boolean login_Auth(final int id) {
		return allowedChar.contains(id);
	}

	public final List<MapleCharacter> loadCharacters() { // TODO make this less costly zZz
		final List<MapleCharacter> chars = new LinkedList<MapleCharacter>();

		for (final CharNameAndId cni : loadCharactersInternal()) {
			final MapleCharacter chr = MapleCharacter.loadCharFromDB(cni.id, this, false);
			chars.add(chr);
			allowedChar.add(chr.getId());
		}
		return chars;
	}

	public List<String> loadCharacterNames() {
		List<String> chars = new LinkedList<String>();
		for (CharNameAndId cni : loadCharactersInternal()) {
			chars.add(cni.name);
		}
		return chars;
	}

	private List<CharNameAndId> loadCharactersInternal() {
		long t = System.currentTimeMillis();
		List<CharNameAndId> chars = new LinkedList<CharNameAndId>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT id, name FROM characters WHERE accountid = ?");
			ps.setInt(1, accId);

			rs = ps.executeQuery();
			while (rs.next()) {
				chars.add(new CharNameAndId(rs.getString("name"), rs.getInt("id")));
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("error loading characters internal" + e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		if (ServerConstants.isLocal) {
			System.out.println("Load Characters Internal time : " + (System.currentTimeMillis() - t) + "ms");
		}

		return chars;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	private Calendar getTempBanCalendar(ResultSet rs) throws SQLException {
		Calendar lTempban = Calendar.getInstance();
		if (rs.getLong("tempban") == 0) { // basically if timestamp in db is 0000-00-00
			lTempban.setTimeInMillis(0);
			return lTempban;
		}
		Calendar today = Calendar.getInstance();
		lTempban.setTimeInMillis(rs.getTimestamp("tempban").getTime());
		if (today.getTimeInMillis() < lTempban.getTimeInMillis()) {
			return lTempban;
		}

		lTempban.setTimeInMillis(0);
		return lTempban;
	}

	public Calendar getTempBanCalendar() {
		return tempban;
	}

	public byte getBanReason() {
		return greason;
	}

	public boolean hasBannedIP() {
		boolean ret = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT COUNT(*) FROM ipbans WHERE ? LIKE CONCAT(ip, '%')");
			ps.setString(1, getIp());
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0) {
				ret = true;
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException ex) {
			System.err.println("Error checking ip bans" + ex);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return ret;
	}

	public boolean hasSerialBan() {
		boolean r = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT * FROM serialbans WHERE serial = ?");
			ps.setString(1, serial);
			rs = ps.executeQuery();
			if (rs.next()) {
				r = true;
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return r;
	}

	public boolean hasBannedMac() {
		if (macs.isEmpty()) {
			return false;
		}
		boolean ret = false;
		int i = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = MYSQL.getConnection();
			StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM macbans WHERE mac IN (");
			for (i = 0; i < macs.size(); i++) {
				sql.append("?");
				if (i != macs.size() - 1) {
					sql.append(", ");
				}
			}
			sql.append(")");
			ps = con.prepareStatement(sql.toString());
			i = 0;
			for (String mac : macs) {
				i++;
				ps.setString(i, mac);
			}
			rs = ps.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0) {
				ret = true;
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException ex) {
			System.err.println("Error checking mac bans" + ex);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return ret;
	}

	private void loadMacsIfNescessary() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (macs.isEmpty()) {
				con = MYSQL.getConnection();
				ps = con.prepareStatement("SELECT macs FROM accounts WHERE id = ?");
				ps.setInt(1, accId);
				rs = ps.executeQuery();
				if (rs.next()) {
					String[] macData = rs.getString("macs").split(", ");
					for (String mac : macData) {
						if (!mac.equals("")) {
							macs.add(mac);
						}
					}
				} else {
					rs.close();
					ps.close();
					con.close();
					throw new RuntimeException("No valid account associated with this client.");
				}
				rs.close();
				ps.close();
				con.close();
			}
		} catch (SQLException e) {
			System.err.println("Error banning MACs" + e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public void banMacs() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			loadMacsIfNescessary();
			List<String> filtered = new LinkedList<String>();
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT filter FROM macfilters");
			rs = ps.executeQuery();
			while (rs.next()) {
				filtered.add(rs.getString("filter"));
			}
			rs.close();
			ps.close();

			ps = con.prepareStatement("INSERT INTO macbans (mac) VALUES (?)");
			for (String mac : macs) {
				boolean matched = false;
				for (String filter : filtered) {
					if (mac.matches(filter)) {
						matched = true;
						break;
					}
				}
				if (!matched) {
					ps.setString(1, mac);
					try {
						ps.executeUpdate();
					} catch (SQLException e) {
						// can fail because of UNIQUE key, we dont care
					}
				}
			}
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("Error banning MACs" + e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	/**
	 * Returns 0 on success, a state to be used for
	 * {@link MaplePacketCreator#getLoginFailed(int)} otherwise.
	 *
	 * @param success
	 * @return The state of the login.
	 */
	public int finishLogin() {
		synchronized (MapleClient.class) {
			final byte state = getLoginState();
			if (state > MapleClient.LOGIN_NOTLOGGEDIN && state != MapleClient.LOGIN_WAITING) { // already loggedin
				loggedIn = false;
				return 7;
			}
			updateLoginState(MapleClient.LOGIN_LOGGEDIN, null);
		}
		return 0;
	}

	public void loadAuthData() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement(
					"SELECT idcode1, idcode2, 2ndpassword, using2ndpassword FROM accounts WHERE id = ?");
			ps.setInt(1, this.accId);
			rs = ps.executeQuery();
			if (rs.next()) {
				idcode1 = rs.getInt("idcode1");
				idcode2 = rs.getInt("idcode2");
				secondPassword = rs.getString("2ndpassword");
				usingSecondPassword = rs.getByte("using2ndpassword") == 1;
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			if (!ServerConstants.realese) {
				e.printStackTrace();
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public String getPassword(String login) {
		String password = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT * FROM accounts WHERE name = ?");
			ps.setString(1, login);
			rs = ps.executeQuery();
			if (rs.next()) {
				password = rs.getString("password");
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return password;
	}

	public int login(String login, String pwd, boolean ipMacBanned) {
		int loginok = 5;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT * FROM accounts WHERE name = ?");
			ps.setString(1, login);
			rs = ps.executeQuery();

			if (rs.next()) {
				final int banned = rs.getInt("banned");
				final String password = rs.getString("password");

				accId = rs.getInt("id");
				secondPassword = rs.getString("2ndpassword");
				gm = rs.getInt("gm") > 0;
				greason = rs.getByte("greason");
				tempban = getTempBanCalendar(rs);
				gender = rs.getByte("gender");
				idcode1 = rs.getInt("idcode1");
				idcode2 = rs.getInt("idcode2");
				usingSecondPassword = rs.getByte("using2ndpassword") == 1;
				ps.close();

				if (banned > 0) {
					loginok = 3;
				} else {
					if (banned == -1) {
						unban();
					}
					byte loginstate = getLoginState();

					if (loginstate > MapleClient.LOGIN_NOTLOGGEDIN) { // already loggedin
						loggedIn = false;
						loginok = 7;

					} else if (pwd.equals(password)) {
						if (ServerConstants.isDev == true) { // Change if in dev
							if (gm) {
								loggedIn = true;
								loginok = 0;
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
								Calendar c = Calendar.getInstance();
								int year = c.get(Calendar.YEAR);
								if(year > 2560)
								{
									c.add(Calendar.YEAR, -543);
								}
								String dateTime = sdf.format(c.getTime());
								updateLastConnection(dateTime);
							} else {
								loggedIn = false;
								loginok = 4;
							}
						} else {
							loggedIn = true;
							loginok = 0;
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
							Calendar c = Calendar.getInstance();
							int year = c.get(Calendar.YEAR);
							if(year > 2560)
							{
								c.add(Calendar.YEAR, -543);
							}
							String dateTime = sdf.format(c.getTime());
							updateLastConnection(dateTime);

						}
					} else {
						loggedIn = false;
						loginok = 4;
					}
				}
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("ERROR" + e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return loginok;
	}

	public boolean CheckSecondPassword(String in) {
		boolean allow = false;

		if (in.equals(secondPassword)) {
			allow = true;
		}
		return allow;
	}

	/**
	 * Gets the special server IP if the client matches a certain subnet.
	 *
	 * @param subnetInfo      A <code>Properties</code> instance containing all the
	 *                        subnet info.
	 * @param clientIPAddress The IP address of the client as a dotted quad.
	 * @param channel         The requested channel to match with the subnet.
	 * @return <code>0.0.0.0</code> if no subnet matched, or the IP if the subnet
	 *         matched.
	 */
	public static String getChannelServerIPFromSubnet(String clientIPAddress, int channel) {
		return ServerConstants.Host;
	}

	private void unban() {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("UPDATE accounts SET banned = 0 and banreason = '' WHERE id = ?");
			ps.setInt(1, accId);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while unbanning" + e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public byte unban(String charname) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT accountid from characters where name = ?");
			ps.setString(1, charname);

			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return -1;
			}
			final int accid = rs.getInt(1);
			rs.close();
			ps.close();

			ps = con.prepareStatement("UPDATE accounts SET banned = 0 and banreason = '' WHERE id = ?");
			ps.setInt(1, accid);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while unbanning" + e);
			return -2;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return 0;
	}

	public void updateMacs(String macData) {
		Connection con = null;
		PreparedStatement ps = null;
		macs.addAll(Arrays.asList(macData.split(", ")));
		StringBuilder newMacData = new StringBuilder();
		Iterator<String> iter = macs.iterator();
		while (iter.hasNext()) {
			newMacData.append(iter.next());
			if (iter.hasNext()) {
				newMacData.append(", ");
			}
		}
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("UPDATE accounts SET macs = ? WHERE id = ?");
			ps.setString(1, newMacData.toString());
			ps.setInt(2, accId);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("Error saving MACs" + e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public void setAccID(int id) {
		this.accId = id;
	}

	public int getAccID(String accountName) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT * FROM accounts WHERE name = ?");
			ps.setString(1, accountName);
			rs = ps.executeQuery();
			if (rs.next()) {
				this.accId = rs.getInt("id");
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException ex) {
			if (!ServerConstants.realese) {
				ex.printStackTrace();
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return this.accId;
	}

	public int getAccID() {
		return this.accId;
	}

	public final void updateLoginState(final int newstate, final String SessionID) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("UPDATE accounts SET loggedin = ?, SessionIP = ?, lastlogin = ? WHERE id = ?");
			ps.setInt(1, newstate);
			ps.setString(2, serial);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			if(year > 2560)
			{
				c.add(Calendar.YEAR, -543);
			}
			String dateTime = sdf.format(c.getTime());
			ps.setString(3, dateTime);
			ps.setInt(4, getAccID());
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("error updating login state new state: " + e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		if (newstate == MapleClient.LOGIN_NOTLOGGEDIN || newstate == MapleClient.LOGIN_WAITING) {
			loggedIn = false;
			serverTransition = false;
		} else {
			serverTransition = (newstate == MapleClient.LOGIN_SERVER_TRANSITION
					|| newstate == MapleClient.CHANGE_CHANNEL);
			loggedIn = !serverTransition;
		}
	}

	public final void updateLoginState(final String accountName) { // TODO hide?
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("UPDATE accounts SET loggedin = ? WHERE name = ?");
			ps.setInt(1, 0);
			ps.setString(2, accountName);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("error updating login state" + e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		loggedIn = false;
		serverTransition = false;
	}

	public final void updateLastConnection(String time) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("UPDATE accounts SET lastconnect = ? WHERE id = ?");
			ps.setString(1, time);
			ps.setInt(2, accId);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			if (!ServerConstants.realese) {
				e.printStackTrace();
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public final int getLastConnection() {
		Connection connect = null;
		PreparedStatement query = null;
		ResultSet result = null;
		try {
			connect = MYSQL.getConnection();
			query = connect.prepareStatement("SELECT lastconnect FROM accounts WHERE id = ?");
			query.setInt(1, accId);
			result = query.executeQuery();
			if (result.next()) {
				return Integer.parseInt(result.getString("lastconnect"));
			}
		} catch (SQLException e) {
			if (!ServerConstants.realese) {
				e.printStackTrace();
			}
		} finally {
			try {
				if (query != null) {
					query.close();
				}
				if (result != null) {
					result.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (SQLException e) {
			}
		}
		return 2012010101;
	}

	public final void updateSecondPassword() {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("UPDATE `accounts` SET `2ndpassword` = ?, `using2ndpassword` = ? WHERE id = ?");
			ps.setString(1, secondPassword);
			ps.setByte(2, (byte) (usingSecondPassword ? 1 : 0));
			ps.setInt(3, accId);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("error updating login state" + e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public void updateIDCodes(int id1, int id2) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("UPDATE accounts SET idcode1 = ?, idcode2 = ? WHERE id = ?");
			ps.setInt(1, id1);
			ps.setInt(2, id2);
			ps.setInt(3, accId);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			if (!ServerConstants.realese) {
				e.printStackTrace();
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public boolean isUsing2ndPassword() {
		return usingSecondPassword;
	}

	public int getIDCode1() {
		return idcode1;
	}

	public int getIDCode2() {
		return idcode2;
	}

	public final byte getLoginState() { // TODO hide?
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT loggedin, lastlogin FROM accounts WHERE id = ?");
			ps.setInt(1, getAccID());
			rs = ps.executeQuery();
			if (!rs.next()) {
				ps.close();
				rs.close();
				con.close();
				throw new MYSQLException("Everything sucks ID : " + getAccID());
			}
			byte state = rs.getByte("loggedin");

			if (state == MapleClient.LOGIN_SERVER_TRANSITION || state == MapleClient.CHANGE_CHANNEL) {
				if (rs.getTimestamp("lastlogin").getTime() + 20000 < System.currentTimeMillis()) { // connecting to
																									// chanserver
																									// timeout
					state = MapleClient.LOGIN_NOTLOGGEDIN;
					updateLoginState(state, null);
				}
			}
			rs.close();
			ps.close();
			con.close();
			if (state == MapleClient.LOGIN_LOGGEDIN) {
				loggedIn = true;
			} else {
				loggedIn = false;
			}
			return state;
		} catch (SQLException e) {
			loggedIn = false;
			throw new MYSQLException("error getting login state", e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public static int isValidAccount(String name) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT day FROM accounts WHERE name = ?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (!rs.next()) {
				rs.close();
				ps.close();
				con.close();
				return 0;
			}
			int state = rs.getInt("day");
			rs.close();
			ps.close();
			con.close();
			return state;
		} catch (SQLException e) {
			throw new MYSQLException("Query error. Could not retrieve available days.", e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public final void removalTask() {
		try {
			if (!player.getAllBuffs().isEmpty()) {
				player.cancelAllBuffs_();
			}
			if (!player.getAllDiseases().isEmpty()) {
				player.cancelAllDebuffs();
			}
			if (player.getTrade() != null) {
				MapleUserTrade.cancelTrade(player.getTrade());
			}
			NPCScriptManager.getInstance().dispose(this);

			final IMapleCharacterShop shop = player.getPlayerShop();
			if (shop != null) {
				shop.removeVisitor(player);
				if (shop.isOwner(player)) {
					shop.setOpen(true);
				}
			}
			if (player.getMap() != null) {
				player.getMap().removePlayer(player);
			}
			if (player.getEventInstance() != null) {
				player.getEventInstance().playerDisconnected(player, player.getId());
			}
			this.processTimer.cancel();
		} catch (final Throwable e) {
		}
	}

	public final void disconnect(final boolean RemoveInChannelServer, final boolean fromCS) {
		if (player != null && isLoggedIn()) {
			getSession().writeAndFlush(MainPacketCreator.serverMessage(""));
			removalTask();
			MapleItemHolder.registerInv(player.getId(), player.getInventorys());
			AdminToolStart.동접제거(player.getName());
			AdminToolStart.접속자수.setText(String.valueOf((int) (Integer.parseInt(AdminToolStart.접속자수.getText()) - 1)));
			// WorldAuction.save(); //Save auction hall when in-game ends
			player.saveToDB(true, fromCS);
			if (!fromCS) {
				final ChannelServer ch = ChannelServer.getInstance(channel);
				try {
					if (player.getMessenger() != null) {
						WorldCommunity.leaveMessenger(player.getMessenger().getId(),
								new MapleMultiChatCharacter(player));
						player.setMessenger(null);
					}
					if (player.getParty() != null) {
						final MaplePartyCharacter chrp = new MaplePartyCharacter(player);
						chrp.setOnline(false);
						if (player.getParty().getExpedition() != null) {
							player.getParty().getExpedition().broadcastMessage(
									MainPacketCreator.updateExpedition(true, player.getParty().getExpedition()));
						}
						WorldCommunity.updateParty(player.getParty().getId(), MaplePartyOperation.LOG_ONOFF, chrp);
					}
					if (!serverTransition && isLoggedIn()) {
						WorldBroadcasting.loggedOff(player.getName(), player.getId(), channel,
								player.getBuddylist().getBuddyIds());
					} else { // Change channel
						WorldBroadcasting.loggedOn(player.getName(), player.getId(), channel,
								player.getBuddylist().getBuddyIds());
					}
					if (player.getGuildId() > 0) {
						ChannelServer.setGuildMemberOnline(player.getMGC(), false, -1);
					}
				} catch (final Exception e) {
					if (player != null) {
						player.setMessenger(null);
					}
					if (!ServerConstants.realese) {
						e.printStackTrace();
					}
					System.err.println(getLogMessage(this, "ERROR") + e);
				} finally {
					if (RemoveInChannelServer && ch != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
						Calendar c = Calendar.getInstance();
						int year = c.get(Calendar.YEAR);
						if(year > 2560)
						{
							c.add(Calendar.YEAR, -543);
						}
						String dateTime = sdf.format(c.getTime());
						updateLastConnection(dateTime);
						ch.removePlayer(player);
						AdminTool.broadcastMessage(AdminToolPacket.Info());
					}
					player = null;
				}
			} else {
				final CashShopServer cs = CashShopServer.getInstance();
				try {
					if (player.getParty() != null) {
						final MaplePartyCharacter chrp = new MaplePartyCharacter(player);
						chrp.setOnline(false);
						WorldCommunity.updateParty(player.getParty().getId(), MaplePartyOperation.LOG_ONOFF, chrp);
					}
					if (!serverTransition && isLoggedIn()) {
						WorldBroadcasting.loggedOff(player.getName(), player.getId(), channel,
								player.getBuddylist().getBuddyIds());
					} else { // Change channel
						WorldBroadcasting.loggedOn(player.getName(), player.getId(), channel,
								player.getBuddylist().getBuddyIds());
					}
					if (player.getGuildId() > 0) {
						ChannelServer.setGuildMemberOnline(player.getMGC(), false, -1);
					}

				} catch (final Exception e) {
					player.setMessenger(null);
					if (!ServerConstants.realese) {
						e.printStackTrace();
					}
					System.err.println(getLogMessage(this, "ERROR") + e);
				} finally {
					if (RemoveInChannelServer && cs != null) {
						cs.getPlayerStorage().deregisterPlayer(player);
					}
					player = null;
				}
			}
		}
		if (!serverTransition && isLoggedIn()) {
			updateLoginState(MapleClient.LOGIN_NOTLOGGEDIN, null);
		}
		engines.clear();
	}

	public final String getSessionIPAddress() {
		return getIp().split("/")[1];
	}

	public final boolean CheckIPAddress() {
		try {
			final Connection con = MYSQL.getConnection();
			final PreparedStatement ps = con.prepareStatement("SELECT SessionIP FROM accounts WHERE id = ?");
			ps.setInt(1, this.accId);
			final ResultSet rs = ps.executeQuery();

			boolean canlogin = false;

			if (rs.next()) {
				final String sessionIP = rs.getString("SessionIP");

				if (sessionIP != null) { // Probably a login proced skipper?
					canlogin = getSessionIPAddress().equals(sessionIP.split(":")[0]);
				}
			}
			rs.close();
			ps.close();
			con.close();
			return canlogin;
		} catch (final SQLException e) {
			System.out.println("Failed in checking IP address for client.");
		}
		return false;
	}

	public final int getChannel() {
		return channel;
	}

	public final ChannelServer getChannelServer() {
		return ChannelServer.getInstance(channel);
	}

	public final int getLatency() {
		return (int) (lastPong - lastPing);
	}

	public final boolean deleteCharacter(final int cid) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement(
					"SELECT id, guildid, guildrank, name, alliancerank FROM characters WHERE id = ? AND accountid = ?");
			ps.setInt(1, cid);
			ps.setInt(2, accId);
			rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();
				ps.close();
				con.close();
				return false;
			}
			if (rs.getInt("guildid") > 0) { // is in a guild when deleted
				final MapleGuildCharacter mgc = new MapleGuildCharacter(cid, (short) 0, rs.getString("name"), (byte) -1,
						0, rs.getInt("guildrank"), rs.getInt("guildid"), false, rs.getInt("alliancerank"));
				LoginServer.getInstance().deleteGuildCharacter(mgc);
			}
			LoggerChatting.writeLog(LoggerChatting.dcLog,
					LoggerChatting.getDeleteLog("삭제", rs.getString("name"), accountName));
			rs.close();
			ps.close();

			ps = con.prepareStatement("DELETE FROM characters WHERE id = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM skills WHERE characterid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM hiredmerch WHERE characterid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM mountdata WHERE characterid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM monsterbook WHERE charid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM keyvalue WHERE cid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM keyvalue2 WHERE cid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM inventoryitems WHERE characterid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM `inner_ability_skills` WHERE player_id = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM `inventoryslot` WHERE characterid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM `keymap` WHERE characterid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM `questinfo` WHERE characterid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM `queststatus` WHERE characterid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM `quickslot` WHERE cid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM `skillmacros` WHERE characterid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM `skills_cooldowns` WHERE charid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM `steelskills` WHERE cid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("DELETE FROM `trocklocations` WHERE characterid = ?");
			ps.setInt(1, cid);
			ps.executeUpdate();
			ps.close();
			con.close();
			return true;
		} catch (final SQLException e) {
			System.err.println("DeleteChar error" + e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return false;
	}

	public void setGender(byte i) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("UPDATE accounts SET gender = ? WHERE id = ?");
			ps.setInt(1, i);
			ps.setInt(2, accId);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public final byte getGender() {
		return gender == -1 ? 0 : gender;
	}

	public final byte getRGender() {
		return gender;
	}

	public final String getSecondPassword() {
		return secondPassword;
	}

	public final void setSecondPassword(final String secondPassword) {
		this.secondPassword = secondPassword;
		this.usingSecondPassword = secondPassword != null;
		this.updateSecondPassword();
	}

	public final String getAccountName() {
		return accountName;
	}

	public final void setAccountName(final String accountName) {
		this.accountName = accountName;
	}

	public final void setChannel(final int channel) {
		this.channel = channel;
	}

	public final int getWorld() {
		return world;
	}

	public final void setWorld(final int world) {
		this.world = world;
	}

	public final long getLastPong() {
		return lastPong;
	}

	public String getTempIP() {
		return tempIP;
	}

	public final void pongReceived(ReadingMaple slea) {
		lastPong = System.currentTimeMillis();

		if (slea == null) {
			return;
		}

		if (ServerConstants.AHT_VERSION >= 100) {
			String g = getIp();
			if (getPlayer() != null) {
				g += "_" + getPlayer().getName();
			}

			if (slea.available() == 0 || slea.available() < 4) {
				FileoutputUtil.logToFile(FileoutputUtil.AHT_Log,
						g + ": AHT self-packet checking failed (not enough data).");
				session.close();
				return;
			}

			boolean closeSession = false;

			int ahtVersion = slea.readInt();

			if (ServerConstants.AHT_VERSION > ahtVersion) {
				FileoutputUtil.logToFile(FileoutputUtil.AHT_Log,
						g + ": Old version of AHT detected. (Version " + ahtVersion + ")");
				session.close();
				return;
			}

			int dwSeed = slea.readInt();
			int dwSeed2 = slea.readInt();
			int dwSeed3 = slea.readInt();

			long dwChk1 = (long) ((slea.readInt() ^ dwSeed) & 0xFFFFFFFFL);
			long dwChk2 = (long) ((slea.readInt() ^ dwSeed2) & 0xFFFFFFFFL);
			long dwChk3 = (long) ((slea.readInt() ^ dwSeed3) & 0xFFFFFFFFL);

			if (dwChk1 != 0xBAADF00DL || dwChk2 != 0xCCCCCCCCL || dwChk3 != 0xDEADC0DEL) {
				FileoutputUtil.logToFile(FileoutputUtil.AHT_Log,
						g + ": Wrong AHT Hash value, " + dwChk1 + "," + dwChk2 + "," + dwChk3);

				closeSession = true;
			}

			long dAtkBase = slea.readLong();

			if (dAtkBase != 0x69002e00320077L) {
				FileoutputUtil.logToFile(FileoutputUtil.AHT_Log, g + ": Wrong ATK Base: " + dAtkBase);
			}

			long dwCrcVal = (long) ((slea.readInt() ^ 0xF0C47ADA ^ dwSeed3) & 0xFFFFFFFFL);

			if (dwCrcVal != ServerConstants.SKILL_FILE_CRC) {
				FileoutputUtil.logToFile(FileoutputUtil.AHT_Log, g + ": Wrong skill hash: [" + dwCrcVal + "]");

				closeSession = true;
			}

			long nErrorCode = (long) ((slea.readInt() ^ dwSeed2) & 0xFFFFFFFFL);

			if (nErrorCode != 0) {
				String strErrorMsg = "";

				switch ((int) nErrorCode) {
				case 0:
					strErrorMsg = "NO_ERROR";
					break;
				case 1:
					strErrorMsg = "ERROR_CODE_MEMORY_HASH";
					break;
				case 2:
					strErrorMsg = "ERROR_CODE_TIME_CHECK";
					break;
				case 3:
					strErrorMsg = "ERROR_CODE_MAIN_CHECK";
					break;
				case 4:
					strErrorMsg = "ERROR_CODE_DEBUG_CHECK";
					break;
				case 5:
					strErrorMsg = "ERROR_CODE_AH_HASH";
					break;
				case 6:
					strErrorMsg = "ERROR_CODE_IAT_HASH";
					break;
				case 7:
					strErrorMsg = "ERROR_CODE_WINDOW_CHECK";
					break;
				default:
					strErrorMsg = "UNK_ERROR";
					break;
				}

				FileoutputUtil.logToFile(FileoutputUtil.AHT_Log,
						g + ": Detected Hack! [" + strErrorMsg + "(" + nErrorCode + ")]");

				closeSession = true;
			}

			serial = slea.readMapleAsciiString();
			if (hasSerialBan()) {
				FileoutputUtil.logToFile(FileoutputUtil.AHT_Log, g + ": Serial banned! [" + serial + "]");

				closeSession = true;
			}

			if (closeSession) {
				if (getPlayer() != null && getPlayer().isGM()) {
					return;
				}

				session.close();
			}
		}
	}

	public static final String getLogMessage(final MapleClient cfor, final String message) {
		return getLogMessage(cfor, message, new Object[0]);
	}

	public static final String getLogMessage(final MapleCharacter cfor, final String message) {
		return getLogMessage(cfor == null ? null : cfor.getClient(), message);
	}

	public static final String getLogMessage(final MapleCharacter cfor, final String message, final Object... parms) {
		return getLogMessage(cfor == null ? null : cfor.getClient(), message, parms);
	}

	public static final String getLogMessage(final MapleClient cfor, final String message, final Object... parms) {
		final StringBuilder builder = new StringBuilder();
		if (cfor != null) {
			if (cfor.getPlayer() != null) {
				builder.append("<");
				builder.append(MapleCharacterUtil.makeMapleReadable(cfor.getPlayer().getName()));
				builder.append(" (캐릭터식별코드: ");
				builder.append(cfor.getPlayer().getId());
				builder.append(")> ");
			}
			if (cfor.getAccountName() != null) {
				builder.append("(계정: ");
				builder.append(cfor.getAccountName());
				builder.append(") ");
			}
		}
		builder.append(message);
		int start;
		for (final Object parm : parms) {
			start = builder.indexOf("{}");
			builder.replace(start, start + 2, parm.toString());
		}
		return builder.toString();
	}

	public static final int findAccIdForCharacterName(final String charName) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT accountid FROM characters WHERE name = ?");
			ps.setString(1, charName);
			rs = ps.executeQuery();

			int ret = -1;
			if (rs.next()) {
				ret = rs.getInt("accountid");
			}
			rs.close();
			ps.close();
			con.close();
			return ret;
		} catch (final SQLException e) {
			System.err.println("findAccIdForCharacterName SQL error");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return -1;
	}

	public final Set<String> getMacs() {
		return Collections.unmodifiableSet(macs);
	}

	public final boolean isGm() {
		return gm;
	}

	public final void setScriptEngine(final String name, final ScriptEngine e) {
		engines.put(name, e);
	}

	public final void removeScriptEngine(final String name) {
		engines.remove(name);
	}

	public final ScheduledFuture<?> getIdleTask() {
		return idleTask;
	}

	public final void setIdleTask(final ScheduledFuture<?> idleTask) {
		this.idleTask = idleTask;
	}

	public void removeClickedNPC() {
		lastNpcClick = 0;
	}

	protected static final class CharNameAndId {

		public final String name;
		public final int id;

		public CharNameAndId(final String name, final int id) {
			super();
			this.name = name;
			this.id = id;
		}
	}

	public int getCharacterSlots() {
		if (isGm()) {
			return 15;
		}
		if (charslots != DEFAULT_CHARSLOT) {
			return charslots; // save a sql
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT * FROM character_slots WHERE accid = ? AND worldid = ?");
			ps.setInt(1, accId);
			ps.setInt(2, world);
			rs = ps.executeQuery();
			if (rs.next()) {
				charslots = rs.getInt("charslots");
			} else {
				PreparedStatement psu = con
						.prepareStatement("INSERT INTO character_slots (accid, worldid, charslots) VALUES (?, ?, ?)");
				psu.setInt(1, accId);
				psu.setInt(2, world);
				psu.setInt(3, charslots);
				psu.executeUpdate();
				psu.close();
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}

		return charslots;
	}

	public boolean gainCharacterSlot() {
		if (getCharacterSlots() >= 15) {
			return false;
		}
		charslots++;

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("UPDATE character_slots SET charslots = ? WHERE worldid = ? AND accid = ?");
			ps.setInt(1, charslots);
			ps.setInt(2, world);
			ps.setInt(3, accId);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return true;
	}

	public boolean setBurningCharacter(int accid, int charid) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT * FROM characters WHERE accountid = ? AND id = ?");
			ps.setInt(1, accid);
			ps.setInt(2, charid);
			rs = ps.executeQuery();
			if (!rs.next()) {
				rs.close();
				ps.close();
				con.close();
				return false;
			}
			ps = con.prepareStatement("UPDATE characters SET burning = ? WHERE accountid = ? AND id = ?");
			ps.setByte(1, (byte) 1);
			ps.setInt(2, accid);
			ps.setInt(3, charid);
			ps.executeUpdate();
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			if (!ServerConstants.realese) {
				e.printStackTrace();
			}
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return true;
	}

	public final void sendPing() {
		lastPing = System.currentTimeMillis();
		session.writeAndFlush(LoginPacket.getPing());
		PingTimer.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				sendPing();
			}
		}, 60000); // note: idletime gets added to this too
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String t) {
		this.serial = t;
	}

	public boolean canClickNPC() {
		return lastNpcClick + 500 < System.currentTimeMillis();
	}

	public void setClickedNPC() {
		lastNpcClick = System.currentTimeMillis();
	}

	public void setClickedNPC(int i) {
		lastNpcClick = System.currentTimeMillis() - i;
	}

	public int getNameChangeValue() {
		int a = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
			ps.setInt(1, accId);
			rs = ps.executeQuery();
			if (rs.next()) {
				a = rs.getInt("aimkind");
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
		return a;
	}

	public final void setNameChangeValue(int value, int id) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("UPDATE accounts SET aimkind = ? WHERE id = ?");
			ps.setInt(1, value);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public final void setCharName(String name, int id) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = MYSQL.getConnection();
			ps = con.prepareStatement("UPDATE characters SET name = ? WHERE id = ?");
			ps.setString(1, name);
			ps.setInt(2, id);
			ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println("[ERROR] There was a problem closing the connection.  " + ex);
			}
		}
	}

	public final ScriptEngine getScriptEngine(final String name) {
		return engines.get(name);
	}
}