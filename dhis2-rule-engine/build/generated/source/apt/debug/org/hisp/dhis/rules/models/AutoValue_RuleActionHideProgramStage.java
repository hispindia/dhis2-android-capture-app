
package org.hisp.dhis.rules.models;

import javax.annotation.Nonnull;

// Generated by com.google.auto.value.processor.AutoValueProcessor
 final class AutoValue_RuleActionHideProgramStage extends RuleActionHideProgramStage {

  private final String programStage;

  AutoValue_RuleActionHideProgramStage(
      String programStage) {
    if (programStage == null) {
      throw new NullPointerException("Null programStage");
    }
    this.programStage = programStage;
  }

  @Nonnull
  @Override
  public String programStage() {
    return programStage;
  }

  @Override
  public String toString() {
    return "RuleActionHideProgramStage{"
        + "programStage=" + programStage
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof RuleActionHideProgramStage) {
      RuleActionHideProgramStage that = (RuleActionHideProgramStage) o;
      return (this.programStage.equals(that.programStage()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.programStage.hashCode();
    return h;
  }

}
