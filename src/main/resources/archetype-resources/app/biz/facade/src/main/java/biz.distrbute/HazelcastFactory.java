package ${package}.biz.distrbute;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;


/**
 * 分布式缓存
 * @date 2014年7月12日 下午12:30:11
 * @author lim
 * @version V1.0
 */
public class HazelcastFactory {
	
	private static final String clusterName="${package}.cache.lim.l";
	
	private static final String clusterPassword="hunting";
	
	private static boolean portAutoIncrement = true;
	
	private static boolean multicastEnabled = true;
	
	private static boolean tcpIpEnabled = true;
	
	private static List<String> members = new ArrayList<String>();
	
	private static HazelcastInstance hazelcastInstance = null;
	
	private static Integer port = 5701;
	
	private String clusterDir ;
	
	public void init() throws Exception{
		File sysInfoDir = new File(clusterDir);
		if (!sysInfoDir.exists()) {
            sysInfoDir.mkdirs();
        }
		File localIpFile = new File(sysInfoDir, InetAddress.getLocalHost().getHostAddress());
		if (!localIpFile.exists()){
            localIpFile.mkdirs();
		}
		for (File member : sysInfoDir.listFiles()) {
            HazelcastFactory.members.add(member.getName());
        }
		getDefaultInstance();
	}
	
	private static void getDefaultInstance() {
		Config config = new Config();

        GroupConfig groupConfig = new GroupConfig();
        groupConfig.setName(clusterName);
        groupConfig.setPassword(clusterPassword);

        config.setGroupConfig(groupConfig);

        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setPort(port);
        networkConfig.setPortAutoIncrement(portAutoIncrement);

        JoinConfig join = new JoinConfig();

        MulticastConfig multicastConfig = new MulticastConfig();
        multicastConfig.setEnabled(multicastEnabled);
        join.setMulticastConfig(multicastConfig);

        TcpIpConfig tcpIpConfig = new TcpIpConfig();
        tcpIpConfig.setEnabled(tcpIpEnabled);
        tcpIpConfig.setMembers(members);

        join.setTcpIpConfig(tcpIpConfig);

        networkConfig.setJoin(join);

        config.setNetworkConfig(networkConfig);
		HazelcastFactory.hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }
	
    public static HazelcastInstance getHazelcastInstance() {
        if (HazelcastFactory.hazelcastInstance == null) {
            getDefaultInstance();
        }
        return HazelcastFactory.hazelcastInstance;
    }

	/**
	 * @param clusterDir the clusterDir to set
	 */
	public void setClusterDir(String clusterDir) {
		this.clusterDir = clusterDir;
	}


}
