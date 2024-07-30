package com.etpl.bbps.common;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;
public class RequestIdGenerator {


  private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private static final SecureRandom RANDOM = new SecureRandom();
  private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final int DEFAULT_LENGTH = 35;

	 
  public static String generateRequestId(int length) {
      SecureRandom random = new SecureRandom();
      StringBuilder sb = new StringBuilder(length);
      for (int i = 0; i < length; i++) {
          int index = random.nextInt(CHARACTERS.length());
          sb.append(CHARACTERS.charAt(index));
      }
      return sb.toString();
  }
}
