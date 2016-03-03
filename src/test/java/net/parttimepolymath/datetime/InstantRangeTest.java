package net.parttimepolymath.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class InstantRangeTest {
    private Instant from;
    private Instant to;
    private InstantRange instance;

    @Before
    public void setUp() throws Exception {
        from = Instant.now().minus(10, ChronoUnit.HOURS);
        to = Instant.now();
        instance = new InstantRange(from, to, ChronoUnit.HOURS);
    }

    @Test
    public void testConstructor() {
        assertEquals(from.truncatedTo(ChronoUnit.HOURS), instance.getFrom());
        assertEquals(to.truncatedTo(ChronoUnit.HOURS), instance.getTo());
        assertEquals(ChronoUnit.HOURS, instance.getInterval());
    }

    @Test
    public void testReverseConstructor() {
        instance = new InstantRange(to, from, ChronoUnit.HOURS);
        assertEquals(from.truncatedTo(ChronoUnit.HOURS), instance.getFrom());
        assertEquals(to.truncatedTo(ChronoUnit.HOURS), instance.getTo());
        assertEquals(ChronoUnit.HOURS, instance.getInterval());
    }

    @Test
    public void testNullRange() {
        instance = new InstantRange(null, null, ChronoUnit.HOURS);
        assertNotNull(instance.getFrom());
        assertNotNull(instance.getTo());
        assertTrue(instance.getFrom().isBefore(instance.getTo()));
    }

    @Test
    public void testNullUnit() {
        instance = new InstantRange(from, to, null);
        assertNotNull(instance.getFrom());
        assertNotNull(instance.getTo());
        assertEquals(ChronoUnit.HOURS, instance.getInterval());
    }

    @Test
    public void testAllNull() {
        instance = new InstantRange(null, null, null);
        assertNotNull(instance.getFrom());
        assertNotNull(instance.getTo());
        assertTrue(instance.getFrom().isBefore(instance.getTo()));
        assertEquals(ChronoUnit.HOURS, instance.getInterval());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(instance.isEmpty());

        instance = new InstantRange(from, from, ChronoUnit.HOURS);
        assertTrue(instance.isEmpty());
    }

    @Test
    public void testToString() {
        assertEquals(String.format("InstantRange [fromInstant=%s, toInstant=%s, interval=%s]", from.truncatedTo(ChronoUnit.HOURS),
                to.truncatedTo(ChronoUnit.HOURS), ChronoUnit.HOURS), instance.toString());
    }

    @Test
    public void testGetIterator() {
        assertNotNull(instance.iterator());
    }

    @Test
    public void testSize() {
        assertEquals(11, instance.size());

        // this should be 2 because we have distinct beginning and end of range
        instance = new InstantRange(null, null, ChronoUnit.MINUTES);
        assertEquals(2, instance.size());

        // this should be 1 because we will have a single tick.
        instance = new InstantRange(from, from, ChronoUnit.MINUTES);
        assertEquals(1, instance.size());
    }

    @Test
    public void testIterator() {
        int count = 0;
        for (Instant instant : instance) {
            assertNotNull(instant);
            count++;
        }
        // 11 because we are going from "10 hours ago" up to "current hour". Clocks are not always intuitive.
        assertEquals(11, count);
    }

    @Test
    public void testToArray() {
        Object[] result = instance.toArray();
        assertNotNull(result);
        assertEquals(11, result.length);
        for (int i = 0; i < result.length; i++) {
            assertNotNull(result[i]);
            assertTrue(result[i] instanceof Instant);
        }
    }

    @Test
    public void testToArrayAlternate() {
        Instant[] result = instance.toArray(new Instant[0]);
        assertNotNull(result);
        assertEquals(11, result.length);
        for (int i = 0; i < result.length; i++) {
            assertNotNull(result[i]);
        }
    }

    @Test
    public void testToArrayAlternateTwo() {
        Instant[] result = instance.toArray(new Instant[20]);
        assertNotNull(result);
        assertEquals(20, result.length);
        assertNull(result[12]);
    }

    @Test
    public void testHash() {
        InstantRange other = new InstantRange(from, to, ChronoUnit.HOURS);
        assertTrue(instance.hashCode() == other.hashCode());

        other = new InstantRange(from, Instant.now(), ChronoUnit.MILLIS);
        assertFalse(instance.hashCode() == other.hashCode());
    }

    @Test
    public void testEquals() {
        assertFalse(instance.equals(null));
        assertFalse(instance.equals("bottle of wine"));
        assertTrue(instance.equals(instance));

        assertTrue(instance.equals(new InstantRange(from, to, ChronoUnit.HOURS)));
        assertFalse(instance.equals(new InstantRange(null, null, null)));
    }

    @Test
    public void testContains() {
        assertFalse(instance.contains(null));
        assertTrue(instance.contains(from.plus(1, ChronoUnit.HOURS).truncatedTo(ChronoUnit.HOURS)));
        assertFalse(instance.contains(to.plus(1, ChronoUnit.HOURS).truncatedTo(ChronoUnit.HOURS)));
        assertFalse(instance.contains("smelly fish"));
        assertTrue(instance.contains(to.truncatedTo(ChronoUnit.HOURS)));
        assertTrue(instance.contains(from.truncatedTo(ChronoUnit.HOURS)));
    }

    @Test
    public void testContainsAll() {
        assertFalse(instance.containsAll(null));

        assertTrue(instance.containsAll(Arrays.asList(from.plus(1, ChronoUnit.HOURS).truncatedTo(ChronoUnit.HOURS),
                to.truncatedTo(ChronoUnit.HOURS), from.truncatedTo(ChronoUnit.HOURS))));

        assertFalse(instance.containsAll(Arrays.asList(Instant.now().plusSeconds(500), from.plusSeconds(500),
                to.truncatedTo(ChronoUnit.HOURS), from.truncatedTo(ChronoUnit.HOURS))));
    }

    @Test
    public void testIteratorEmptyRange() {
        int count = 0;
        for (Instant instant : new InstantRange(from, from, ChronoUnit.DAYS)) {
            assertNotNull(instant);
            count++;
        }
        assertEquals(1, count);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        instance.clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        instance.add(Instant.now());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() {
        instance.addAll(Arrays.asList(Instant.now()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        instance.remove(Instant.now());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() {
        instance.removeAll(Arrays.asList(Instant.now()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() {
        instance.retainAll(Arrays.asList(Instant.now()));
    }
}
