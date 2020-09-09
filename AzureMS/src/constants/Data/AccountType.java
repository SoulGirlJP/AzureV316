package constants.Data;

public enum AccountType {
	PLAYER(0),
	TESTER(1),
	DONATOR(2),
	SUPERDONATOR(3),
	LOWGM(4), 
	GM(5), 
	ADMIN(6);

	private int level;

	private AccountType(int level) {
		this.level = level;
	}

	public int GetLevel() {
		return level;
	}
}
