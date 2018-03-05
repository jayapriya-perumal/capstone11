package com.coremedia.blueprint.common.datevalidation;

import java.util.Calendar;

/**
 * Marks a class as being only valid for the period starting from {@link #getValidFrom()}
 * until {@link #getValidTo()}.
 *
 * @see ValidityPeriodValidator
 */
public interface ValidityPeriod {

  /**
   * Returns the valid from date or {@code null} if there is no such constraint
   *
   * @return the valid from date or {@code null} if there is no such constraint
   */
  Calendar getValidFrom();

  /**
   * Returns the valid to date or {@code null} if there is no such constraint
   *
   * @return the valid to date or {@code null} if there is no such constraint
   */
  Calendar getValidTo();

}
