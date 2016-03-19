package com.example.cheng.myfifthapplication.Bean;

/**
 * 应用更新信息bean
 * Created by cheng on 16-2-9.
 */
public class UpdateInfo {
	private String version;//版本号
	private String name;   //应用名称
	private String url;    //应用的url

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getIntegerVersion(){
		return Integer.parseInt(this.version);
	}
}
