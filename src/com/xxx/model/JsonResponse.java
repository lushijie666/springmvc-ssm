package com.xxx.model;

public class JsonResponse {

    public static final int RESULT_CODE_SUCCESS = 0;

    public static final int ERROR_CODE = -1;

    private int code;

    private String msg;

    private long count;

    private Object data;

    public JsonResponse() {
    }

    public void setFailed(String errorMessage){
        this.code = ERROR_CODE;
        this.msg = errorMessage;
    }

    public void setFailed(int resultCode, String errorMessage) {
        this.code = resultCode;
        this.msg = errorMessage;
    }

    public void setSuccessed() {
        this.code = RESULT_CODE_SUCCESS;
    }

    public void setSuccessed(Object object) {
        this.code = RESULT_CODE_SUCCESS;
        this.data = object;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
    
    
}