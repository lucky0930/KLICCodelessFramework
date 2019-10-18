package com.test.automation.common.framework;

import org.testng.SkipException;

public class NoAssertionsException extends SkipException{
	private static final long serialVersionUID = 4106881831179669577L;

	public NoAssertionsException(String skipMessage) {
	    super(skipMessage);
	}
	protected NoAssertionsException(String skipMessage, Throwable cause) {
		super(skipMessage, cause);
		// TODO Auto-generated constructor stub
	}

	  /**
	   * Flag if the current exception marks a skipped method (<tt>true</tt>)
	   * or a failure (<tt>false</tt>). By default Subclasses should override this method
	   * in order to provide smarter behavior.
	   *
	   * @return <tt>true</tt> if the method should be considered a skip,
	   *    <tt>false</tt> if the method should be considered failed. If not
	   *    overwritten it returns <tt>true</tt>
	   */
	  public boolean isSkip() {
	    return false;
	  }
}
