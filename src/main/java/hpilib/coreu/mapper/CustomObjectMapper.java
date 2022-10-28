package hpilib.coreu.mapper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hpilib.coreu.utils.HpiDateTime;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public class CustomObjectMapper extends ObjectMapper {

  public CustomObjectMapper() {
    this(true, false, false);
  }

  public CustomObjectMapper(boolean nonNull, boolean nonEmpty) {
    this(nonNull, nonEmpty, false);
  }

  public CustomObjectMapper(boolean nonNull, boolean nonEmpty, boolean failOnUnknownProperties) {
    this.registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
            .enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);

    String timezone = HpiDateTime.getDefaultTimezone();
    if (timezone != null && !timezone.isBlank()) {
      this.addOffsetDateTimeUtil(timezone);
    }

    if (nonNull)
      this.setSerializationInclusion(Include.NON_NULL);
    if (nonEmpty)
      this.setSerializationInclusion(Include.NON_EMPTY);
    if (failOnUnknownProperties)
      this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public void addOffsetDateTimeUtil(String zoneId) {
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(OffsetDateTime.class,
            new HpiDateTime.OffsetDateTimeSerializer(ZoneId.of(zoneId)));
    simpleModule.addDeserializer(OffsetDateTime.class,
            new HpiDateTime.OffsetDateTimeDeserializer(ZoneId.of(zoneId)));
    this.registerModule(simpleModule);
  }
}