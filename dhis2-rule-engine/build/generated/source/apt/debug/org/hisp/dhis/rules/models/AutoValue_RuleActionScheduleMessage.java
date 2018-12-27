
package org.hisp.dhis.rules.models;

import javax.annotation.Nonnull;

// Generated by com.google.auto.value.processor.AutoValueProcessor
 final class AutoValue_RuleActionScheduleMessage extends RuleActionScheduleMessage {

  private final String notification;
  private final String data;

  AutoValue_RuleActionScheduleMessage(
      String notification,
      String data) {
    if (notification == null) {
      throw new NullPointerException("Null notification");
    }
    this.notification = notification;
    if (data == null) {
      throw new NullPointerException("Null data");
    }
    this.data = data;
  }

  @Nonnull
  @Override
  public String notification() {
    return notification;
  }

  @Nonnull
  @Override
  public String data() {
    return data;
  }

  @Override
  public String toString() {
    return "RuleActionScheduleMessage{"
        + "notification=" + notification + ", "
        + "data=" + data
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof RuleActionScheduleMessage) {
      RuleActionScheduleMessage that = (RuleActionScheduleMessage) o;
      return (this.notification.equals(that.notification()))
           && (this.data.equals(that.data()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.notification.hashCode();
    h *= 1000003;
    h ^= this.data.hashCode();
    return h;
  }

}
