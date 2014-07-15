package me.predatorray.velocli.util;

public class SQLUtils {

    private static final String SIGNED_INT = "^INT(\\s)*(\\(\\d+\\))?((\\s)+SIGNED)?$";
    private static final String UNSIGNED_INT = "^INT(\\s)*(\\(\\d+\\))?(\\s)+UNSIGNED$";
    private static final String SIGNED_BIGINT = "^BIGINT(\\s)*(\\(\\d+\\))?((\\s)+SIGNED)?$";
    private static final String UNSIGNED_BIGINT = "^BIGINT(\\s)*(\\(\\d+\\))?(\\s)+UNSIGNED$";
    private static final String SIGNED_TINYINT = "^TINYINT(\\s)*(\\(\\d+\\))?((\\s)+SIGNED)?$";
    private static final String UNSIGNED_TINYINT = "^TINYINT(\\s)*(\\(\\d+\\))?(\\s)+UNSIGNED$";
    private static final String BOOLEAN = "^(BOOL|BOOLEAN)$";
    private static final String DECIMAL = "^DECIMAL(\\s)*\\(\\d+(,(\\s)*\\d+)?\\)$";
    private static final String CHAR_VARCHAR = "^(CHAR|VARCHAR)(\\s)*(\\(\\d+\\))";
    private static final String TEXT = "^(TEXT|TINYTEXT|MEDIUMTEXT|LONGTEXT)$";
    private static final String DATA_TIME = "^(DATE|TIME|DATETIME)$";

    public String toJavaType(String sqlType) {
        if (sqlType == null) {
            return null;
        }
        String normalized = sqlType.trim().toUpperCase();
        if (normalized.matches(SIGNED_INT)) {
            return "Integer";
        } else if (normalized.matches(UNSIGNED_INT) ||
                normalized.matches(SIGNED_BIGINT) ||
                normalized.matches(UNSIGNED_BIGINT)) {
            return "Long";
        } else if (normalized.matches(SIGNED_TINYINT)) {
            return "Byte";
        } else if (normalized.matches(UNSIGNED_TINYINT)) {
            return "Short";
        } else if (normalized.matches(BOOLEAN)) {
            return "Boolean";
        } else if (normalized.matches(DECIMAL)) {
            return "java.math.BigDecimal";
        } else if (normalized.matches(CHAR_VARCHAR)) {
            return "String";
        } else if (normalized.matches(TEXT)) {
            return "String";
        } else if (normalized.matches(DATA_TIME)) {
            return "java.util.Date";
        }
        return null;
    }

    public String normalizeField(String field) {
        if (field == null) {
            return null;
        }
        String[] parts = field.split("_");
        StringBuilder javaFieldName = new StringBuilder();
        for (int i = 0; i < parts.length; ++i) {
            String part = parts[i];
            if (!part.matches("^[a-z](\\w)*") || i == 0) {
                javaFieldName.append(part);
            } else {
                char[] chars = new char[part.length()];
                part.getChars(0, part.length(), chars, 0);
                chars[0] = (char) (chars[0] + ('A' - 'a'));
                javaFieldName.append(chars);
            }
        }
        return javaFieldName.toString();
    }

    public String normalizeTableName(String tableName) {
        if (tableName == null) {
            return null;
        }

        String nonPrefix = (tableName.startsWith("tb_") ||
                tableName.startsWith("tl_") ||
                tableName.startsWith("td_")) ?
                tableName.substring(3) : tableName;

        String[] parts = nonPrefix.split("_");
        StringBuilder javaFieldName = new StringBuilder();
        for (int i = 0; i < parts.length; ++i) {
            String part = parts[i];
            if (!part.matches("^[a-z](\\w)*")) {
                javaFieldName.append(part);
            } else {
                char[] chars = new char[part.length()];
                part.getChars(0, part.length(), chars, 0);
                chars[0] = (char) (chars[0] + ('A' - 'a'));
                javaFieldName.append(chars);
            }
        }
        return javaFieldName.toString();
    }

    public String capitalizeFirstChar(String str) {
        if (str == null || str.isEmpty() || !str.matches("^[a-z](\\w)*")) {
            return str;
        }

        char[] chars = new char[str.length()];
        str.getChars(0, str.length(), chars, 0);
        chars[0] = (char) (chars[0] + ('A' - 'a'));
        return new String(chars);
    }
}
