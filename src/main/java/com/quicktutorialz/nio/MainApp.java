package com.quicktutorialz.nio;

import com.mawashi.nio.jetty.ReactiveJ;

public class MainApp {
  public static void main(String[] args) throws Exception {
      // we start our jetty embedded app through our reactiveJ
      // library
      new ReactiveJ().port(8383)
              .endpoints(new com.quicktutorialz.nio.endpoints.v1.ToDoEndpoints())
              .endpoints(new com.quicktutorialz.nio.endpoints.v2.ToDoEndpoints())
              .start();
  }
}
