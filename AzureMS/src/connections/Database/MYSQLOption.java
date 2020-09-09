package connections.Database;

import constants.ServerConstants;

public class MYSQLOption {

    public static final boolean isAdmin = true;
    public static final String MySQLURL = "jdbc:mariadb://localhost:3306/kms_316?autoReconnect=true&characterEncoding=euckr&maxReconnects=100&CgonnectionTimeout=30000";
    public static final String MySQLUSER = ServerConstants.dbUser;
    public static final String MySQLPASS = ServerConstants.dbPassword;

    public static int MySQLMINCONNECTION = 100;
    public static int MySQLMAXCONNECTION = 2100000000;
    public static int innodb_lock_wait_timeout = 100;
    public static int open_files_limit = 32000;
    public static int max_connections = 10000;
    public static int wait_timeout = 1000;
}
