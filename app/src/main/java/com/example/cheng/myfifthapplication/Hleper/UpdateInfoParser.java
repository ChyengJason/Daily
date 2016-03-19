package com.example.cheng.myfifthapplication.Hleper;

import android.util.Xml;

import com.example.cheng.myfifthapplication.Bean.UpdateInfo;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * Created by cheng on 16-2-9.
 * 应用更新的信息的Parser
 */

public class UpdateInfoParser {

	/**
	 * 获取更新信息
	 * @param is 解析的xml的inputstream
	 * @return updateinfo
	 */
	public static UpdateInfo getUpdataInfo(InputStream is) throws Exception {
		XmlPullParser parser = Xml.newPullParser();
		UpdateInfo info = new UpdateInfo();
		parser.setInput(is, "utf-8");
		int type = parser.getEventType();

		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
				case XmlPullParser.START_TAG:
					if("version".equals(parser.getName())){
						String version = parser.nextText();
						info.setVersion(version);
					}else if("name".equals(parser.getName())){
						String Name = parser.nextText();
						info.setName(Name);
					}else if("url".equals(parser.getName())){
						String url = parser.nextText();
						info.setUrl(url);
					}
					break;
			}
			type = parser.next();
		}
		return info;
	}

}
