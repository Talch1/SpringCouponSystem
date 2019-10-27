package com.talch.exeption;

	
	public class LogginEx extends Exception{

		private String message;
		public LogginEx(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public LogginEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
			// TODO Auto-generated constructor stub
		}

		public LogginEx(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		

		public LogginEx(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}

	
}
