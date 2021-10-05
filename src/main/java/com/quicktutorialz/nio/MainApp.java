package com.quicktutorialz.nio;

import com.mawashi.nio.jetty.ReactiveJ;
import com.quicktutorialz.nio.endpoints.v1.ToDoEndpoints;

public class MainApp {
  public static void main(String[] args) throws Exception {
      // we start our jetty embedded app through our reactiveJ
      // library
      new ReactiveJ().port(8383)
              .endpoints(new ToDoEndpoints())
              .start();
  }
}
