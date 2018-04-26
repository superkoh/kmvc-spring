package com.github.superkoh.mvc.web.request;

import static org.junit.Assert.*;

import lombok.Data;
import org.junit.Test;

public class DTOTransferableTest {

  @Test
  public void toDTO() {
    A a = new A();
    a.setA("abc");
    B b = a.toDTO();
    assertEquals(a.getA(), b.getA());
  }

  @Data
  private static class A implements DTOTransferable<B> {

    private String a;
  }


  @Data
  private static class B {

    private String a;
  }
}