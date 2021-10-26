package bs.common.util;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import lombok.val;

public class RandomUtil implements Serializable {
	private static final long serialVersionUID = 7326494501133697791L;

	private RandomUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	private static Random rand = new Random();
	
    public static Integer randomInteger(final int min, final int max) {
        val splittableRandom = new SplittableRandom();
        return splittableRandom.nextInt(min, max);
    }

    public static IntStream randomInteger(final Long streamSize, final int min, final int max) {
        val splittableRandom = new SplittableRandom();
        return splittableRandom.ints(streamSize, min, max);
    }
    
    public static String randomBigInteger() {
        return new BigInteger(64, new SecureRandom()).toString();
    }
    
    public static Integer secureRandom() {
        val secureRandom = new SecureRandom();
        return secureRandom.nextInt();
    }

    //##### Thread local random
    public static Integer threadLocalRandom() {
        return ThreadLocalRandom.current().nextInt();
    }

    //##### Thread local random from zero
    public static Integer threadLocalRandom(final int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }

    //##### Thread local random in a range
    public static Integer threadLocalRandom(final int min, final int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static String randomString(final List<String> source) {
        if(!source.isEmpty()) {
            val randomIndex = rand.nextInt(source.size());
            return source.get(randomIndex);
        }
        return null;
    }

    public static List<String> randomSeries(final List<String> source, final int randomSeriesLength) {
        if(!source.isEmpty()) {
            Collections.shuffle(source);
            return source.subList(0, randomSeriesLength);
        }
        return new ArrayList<>();
    }
    
    public static <T> T random(final List<T> source) {
        if(!source.isEmpty()) {
            val randomIndex = rand.nextInt(source.size());
            return source.get(randomIndex);
        }
        return null;
    }
}