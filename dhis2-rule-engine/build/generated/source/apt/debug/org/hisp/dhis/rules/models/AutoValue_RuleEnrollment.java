
package org.hisp.dhis.rules.models;

import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

// Generated by com.google.auto.value.processor.AutoValueProcessor
 final class AutoValue_RuleEnrollment extends RuleEnrollment {

  private final String enrollment;
  private final String programName;
  private final Date incidentDate;
  private final Date enrollmentDate;
  private final RuleEnrollment.Status status;
  private final String organisationUnit;
  private final String organisationUnitCode;
  private final List<RuleAttributeValue> attributeValues;

  AutoValue_RuleEnrollment(
      String enrollment,
      String programName,
      Date incidentDate,
      Date enrollmentDate,
      RuleEnrollment.Status status,
      String organisationUnit,
      @Nullable String organisationUnitCode,
      List<RuleAttributeValue> attributeValues) {
    if (enrollment == null) {
      throw new NullPointerException("Null enrollment");
    }
    this.enrollment = enrollment;
    if (programName == null) {
      throw new NullPointerException("Null programName");
    }
    this.programName = programName;
    if (incidentDate == null) {
      throw new NullPointerException("Null incidentDate");
    }
    this.incidentDate = incidentDate;
    if (enrollmentDate == null) {
      throw new NullPointerException("Null enrollmentDate");
    }
    this.enrollmentDate = enrollmentDate;
    if (status == null) {
      throw new NullPointerException("Null status");
    }
    this.status = status;
    if (organisationUnit == null) {
      throw new NullPointerException("Null organisationUnit");
    }
    this.organisationUnit = organisationUnit;
    this.organisationUnitCode = organisationUnitCode;
    if (attributeValues == null) {
      throw new NullPointerException("Null attributeValues");
    }
    this.attributeValues = attributeValues;
  }

  @Nonnull
  @Override
  public String enrollment() {
    return enrollment;
  }

  @Nonnull
  @Override
  public String programName() {
    return programName;
  }

  @Nonnull
  @Override
  public Date incidentDate() {
    return incidentDate;
  }

  @Nonnull
  @Override
  public Date enrollmentDate() {
    return enrollmentDate;
  }

  @Nonnull
  @Override
  public RuleEnrollment.Status status() {
    return status;
  }

  @Nonnull
  @Override
  public String organisationUnit() {
    return organisationUnit;
  }

  @Nullable
  @Override
  public String organisationUnitCode() {
    return organisationUnitCode;
  }

  @Nonnull
  @Override
  public List<RuleAttributeValue> attributeValues() {
    return attributeValues;
  }

  @Override
  public String toString() {
    return "RuleEnrollment{"
        + "enrollment=" + enrollment + ", "
        + "programName=" + programName + ", "
        + "incidentDate=" + incidentDate + ", "
        + "enrollmentDate=" + enrollmentDate + ", "
        + "status=" + status + ", "
        + "organisationUnit=" + organisationUnit + ", "
        + "organisationUnitCode=" + organisationUnitCode + ", "
        + "attributeValues=" + attributeValues
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof RuleEnrollment) {
      RuleEnrollment that = (RuleEnrollment) o;
      return (this.enrollment.equals(that.enrollment()))
           && (this.programName.equals(that.programName()))
           && (this.incidentDate.equals(that.incidentDate()))
           && (this.enrollmentDate.equals(that.enrollmentDate()))
           && (this.status.equals(that.status()))
           && (this.organisationUnit.equals(that.organisationUnit()))
           && ((this.organisationUnitCode == null) ? (that.organisationUnitCode() == null) : this.organisationUnitCode.equals(that.organisationUnitCode()))
           && (this.attributeValues.equals(that.attributeValues()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.enrollment.hashCode();
    h *= 1000003;
    h ^= this.programName.hashCode();
    h *= 1000003;
    h ^= this.incidentDate.hashCode();
    h *= 1000003;
    h ^= this.enrollmentDate.hashCode();
    h *= 1000003;
    h ^= this.status.hashCode();
    h *= 1000003;
    h ^= this.organisationUnit.hashCode();
    h *= 1000003;
    h ^= (organisationUnitCode == null) ? 0 : this.organisationUnitCode.hashCode();
    h *= 1000003;
    h ^= this.attributeValues.hashCode();
    return h;
  }

}
