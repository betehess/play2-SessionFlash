package test

import play.api.test._
import play.api.test.Helpers._
import org.specs2.mutable._

// remark: you can't run both at once, you need to choose...  I don't
// really know how to test applications with different
// application.context because the first to be executed gets to
// initialize the corresponding objects

// this one is ok, but that's unlucky :-)
class SlashContext extends FunctionalSpec("/")

// with an application.context different from /, the corresponding
// cookies don't get deleted
class FooContext extends FunctionalSpec("/foo")

abstract class FunctionalSpec(context: String) extends Specification {

  System.setProperty("application.context", context)

  val prefix = if (context == "/") "" else context

  "an Application" should {
    
    "play well with Session scope" in {
      running(TestServer(9001), HTMLUNIT) { browser =>

        browser.goTo("http://localhost:9001" + prefix + "/connected")
        browser.pageSource must contain("Oops")
        browser.getCookie("PLAY_SESSION") must equalTo(null)

        browser.goTo("http://localhost:9001" + prefix + "/connect/foobar")
        browser.getCookie("PLAY_SESSION").getValue must not be('empty)

        browser.goTo("http://localhost:9001" + prefix + "/connected")
        browser.pageSource must equalTo("foobar")
        browser.getCookie("PLAY_SESSION").getValue must not be('empty)

        browser.goTo("http://localhost:9001" + prefix + "/disconnect")
        browser.getCookie("PLAY_SESSION") must equalTo(null)

      }
    }

    "play well with Flash scope" in {
      running(TestServer(9001), HTMLUNIT) { browser =>

        browser.goTo("http://localhost:9001" + prefix + "/getFlash/foo")
        browser.pageSource must contain("Oops")
        browser.getCookie("PLAY_FLASH") must equalTo(null)

        browser.goTo("http://localhost:9001" + prefix + "/setFlash/foo/bar")
        browser.getCookie("PLAY_FLASH").getValue must not be('empty)

        browser.goTo("http://localhost:9001" + prefix + "/getFlash/foo")
        browser.pageSource must equalTo("bar")
        browser.getCookie("PLAY_FLASH") must equalTo(null)

        browser.goTo("http://localhost:9001" + prefix + "/getFlash/foo")
        browser.pageSource must contain("Oops")
        browser.getCookie("PLAY_FLASH") must equalTo(null)

      }
    }


  }
  
}
