package hpilib.coreu.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

public class HpiDateTime {
  private HpiDateTime() {}

  private static String defaultTimezone;

  public static ZoneId getZoneId() {
    return ZoneId.of(defaultTimezone);
  }

  public static String getDefaultTimezone() {
    return defaultTimezone;
  }

  public static void setDefaultTimezone(String timezone) {
    defaultTimezone = timezone;
  }

  public static Calendar getCalendar() {
    return Calendar.getInstance(TimeZone.getTimeZone(getZoneId()));
  }

  public static LocalDate today() {
    return now().toLocalDate();
  }

  public static OffsetDateTime now() {
    return OffsetDateTime.now(getZoneId());
  }

  public static OffsetDateTime from(String text) {
    DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(getZoneId());
    return OffsetDateTime.parse(text, dtf);
  }

  public static OffsetDateTime fromTimestamp(Timestamp timestamp) {
    return timestamp.toInstant().atZone(getZoneId()).toOffsetDateTime();
  }

  public static Timestamp toTimestamp(OffsetDateTime dateTime) {
    return Timestamp.valueOf(dateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
  }

  public static String toString(OffsetDateTime dateTime) {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(toTimestamp(dateTime));
  }

  public static String toStringThisZone(OffsetDateTime dateTime) {

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return fmt.format(dateTime);

  }

  public static class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {

    private final DateTimeFormatter ISO_OFFSET_DATE_TIME_FORMATTER;

    public OffsetDateTimeSerializer(ZoneId zoneId) {
      ISO_OFFSET_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(zoneId);
    }

    @Override
    public void serialize(OffsetDateTime value, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
      if (value == null) {
        throw new IOException("OffsetDateTime argument is null.");
      }

      jsonGenerator.writeString(ISO_OFFSET_DATE_TIME_FORMATTER.format(value));
    }
  }

  public static class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {
    private final DateTimeFormatter ISO_OFFSET_DATE_TIME_FORMATTER;

    public OffsetDateTimeDeserializer(ZoneId zoneId) {
      ISO_OFFSET_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(zoneId);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser parser, DeserializationContext ctx)
            throws IOException {
      return OffsetDateTime.parse(parser.getText(), ISO_OFFSET_DATE_TIME_FORMATTER);
    }
  }

}