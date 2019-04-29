package cn.zifangsky.spider;

import cn.zifangsky.manager.ProxyIpManager;
import cn.zifangsky.model.ProxyIp;
import cn.zifangsky.model.bo.ProxyIpBO;
import cn.zifangsky.model.bo.ProxyIpBO.CheckIPType;
import cn.zifangsky.mq.producer.CheckIPSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.*;
import java.util.List;

/**
 * 自定义Pipeline处理抓取的数据
 * @author zifangsky
 *
 */
@Slf4j
@Component("proxyIPPipeline")

public class ProxyIPPipeline implements Pipeline {

	@Value("${mq.topicName.checkIP}")
	private String checkIPTopicName;
	
	@Resource(name="checkIPSender")
	private CheckIPSender checkIPSender;

	@Autowired
	ProxyIpManager proxyIpManager;
	/**
	 * 保存数据
	 */
	@Override
	public void process(ResultItems resultItems, Task task) {
		try {
			List<ProxyIp> list = resultItems.get("result");

			if(list != null && list.size() > 0){
				list.forEach(proxyIp -> {
					ProxyIpBO proxyIpBO = new ProxyIpBO();
					proxyIpBO.setId(proxyIp.getId());
					proxyIpBO.setIp(proxyIp.getIp());
					proxyIpBO.setPort(proxyIp.getPort());
					proxyIpBO.setType(proxyIp.getType());
					proxyIpBO.setAddr(proxyIp.getAddr());
					proxyIpBO.setUsed(proxyIp.getUsed());
					proxyIpBO.setOther(proxyIp.getOther());
					proxyIpBO.setCheckType(CheckIPType.ADD);

					//检测任务添加到队列中
	//				checkIPSender.send(checkIPTopicName, proxyIpBO);

					URL tmpURL = null;
					try {
						tmpURL = new URL("https://www.sogou.com");
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					//代理服务器
					InetSocketAddress proxyAddr = new InetSocketAddress(proxyIp.getIp(), Integer.valueOf(proxyIpBO.getPort()));
					Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddr);
					HttpURLConnection conn = null;
					try {
						conn = (HttpURLConnection) tmpURL.openConnection(proxy);
					} catch (IOException e) {
						e.printStackTrace();
					}
					conn.setReadTimeout(5000);
					conn.setConnectTimeout(5000);
					try {
						conn.setRequestMethod("GET");
					} catch (ProtocolException e) {
						e.printStackTrace();
					}

					try {
						if(conn.getResponseCode() == 200){
							log.info("保存ip:{}",proxyIpBO);
							proxyIpManager.insertSelective(proxyIpBO);
						}
					} catch (IOException e) {
//						e.printStackTrace();
					}


				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
