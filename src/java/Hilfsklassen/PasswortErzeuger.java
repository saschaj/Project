/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilfsklassen;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author Mladko
 */
public class PasswortErzeuger {
  
  private SecureRandom random = new SecureRandom();

  public String getNewPasswort() {
    return new BigInteger(40, random).toString(32);
  }
}

