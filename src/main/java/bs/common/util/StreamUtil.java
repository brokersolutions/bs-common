package bs.common.util;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtil implements Serializable {
	private static final long serialVersionUID = 6358626757246416883L;

	private StreamUtil() {
		throw new IllegalStateException("Utility class");
	}

    public static Stream<String> streamFromEnumeration(final Enumeration<String> enumeration) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(enumeration.asIterator(), Spliterator.ORDERED), false);
    }
    
}