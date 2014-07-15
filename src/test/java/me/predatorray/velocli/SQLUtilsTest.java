package me.predatorray.velocli;

import me.predatorray.velocli.util.SQLUtils;
import org.junit.Assert;
import org.junit.Test;

public class SQLUtilsTest {

    @Test
    public void testNormalizeField() {
        SQLUtils sqlUtils = new SQLUtils();
        Assert.assertEquals("myField", sqlUtils.normalizeField("my_field"));
        Assert.assertEquals("my123Field", sqlUtils.normalizeField("my_123_field"));
        Assert.assertEquals("my123Field", sqlUtils.normalizeField("my_123_field"));
    }

    @Test
    public void testNormalizeTableName() {
        SQLUtils sqlUtils = new SQLUtils();
        Assert.assertEquals("MyField", sqlUtils.normalizeTableName("my_field"));
        Assert.assertEquals("MyField", sqlUtils.normalizeTableName("tb_my_field"));
        Assert.assertEquals("My123Field", sqlUtils.normalizeTableName("tl_my_123_field"));
    }

    @Test
    public void testInt() {
        SQLUtils sqlUtils = new SQLUtils();
        String[] cases = new String[] {
                "int", "INT", "INT(10)", "INT SIGNED", "INT(10) SIGNED",
                "INT(10) SIGNED"
        };
        for (String cs : cases) {
            Assert.assertEquals(String.format("Case %s", cs),
                    "Integer", sqlUtils.toJavaType(cs));
        }
    }

    @Test
    public void testNotInt() {
        SQLUtils sqlUtils = new SQLUtils();
        String[] cases = new String[] {
                "bool", "INT UNSIGNED", "INT(10) UNSIGNED"
        };
        for (String cs : cases) {
            Assert.assertNotEquals(String.format("Case %s", cs),
                    "Integer", sqlUtils.toJavaType(cs));
        }
    }
}
