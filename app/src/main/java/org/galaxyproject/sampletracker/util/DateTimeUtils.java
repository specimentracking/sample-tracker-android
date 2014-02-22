package org.galaxyproject.sampletracker.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Nullable;

/**
 * Utility class that provides methods and converters for working with dates and times.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    /** Format of server timestamp - millis are omitted as Java can handle only 3-digit millis, server sends 6-digits */
    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final DateFormat FORMAT_TIMESTAMP = new SimpleDateFormat(TIMESTAMP_PATTERN, Locale.US);
    public static final DateFormat FORMAT_DATE_TIME = SimpleDateFormat.getDateTimeInstance();

    @Nullable
    public static String formatAsTimestamp(Date date) {
        return date == null ? null : FORMAT_TIMESTAMP.format(date);
    }

    @Nullable
    public static String formatAsDateTime(Date date) {
        return date == null ? null : FORMAT_DATE_TIME.format(date);
    }
}
