package org.isma.dirmanager;
import junit.framework.TestCase;

public class PreferencesManagerTest extends TestCase {
    private static final String DEFAULT_VALUE = "defaultValue";
    static final String KEY = "totoKey";


    public void testPutGet() {
        PreferencesManager managerA = new PreferencesManager();
        PreferencesManager managerB = new PreferencesManager();

        managerB.put(KEY, "");
        assertEquals("", managerA.get(KEY, DEFAULT_VALUE));
        assertEquals("", managerB.get(KEY, DEFAULT_VALUE));

        managerA.put(KEY, "totoValue");

        assertEquals("totoValue", managerA.get(KEY, DEFAULT_VALUE));
        assertEquals("totoValue", managerB.get(KEY, DEFAULT_VALUE));
    }


    public void testRemove() {
        PreferencesManager managerA = new PreferencesManager();
        PreferencesManager managerB = new PreferencesManager();

        managerA.put(KEY, "value");
        assertEquals("value", managerA.get(KEY, DEFAULT_VALUE));
        assertEquals("value", managerB.get(KEY, DEFAULT_VALUE));
        managerA.remove(KEY);
        assertEquals(DEFAULT_VALUE, managerA.get(KEY, DEFAULT_VALUE));
        assertEquals(DEFAULT_VALUE, managerB.get(KEY, DEFAULT_VALUE));
    }
}
