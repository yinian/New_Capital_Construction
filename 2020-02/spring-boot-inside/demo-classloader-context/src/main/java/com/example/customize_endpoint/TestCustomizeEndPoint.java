package com.example.customize_endpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

/**
 * @Auther: viagra
 * @Date: 2020/3/11 20:44
 * @Description: https://github.com/wcong/learn-java/blob/master/doc/springboot/CustomizeEndPoint.md
 */
@Configuration
@EnableAutoConfiguration
public class TestCustomizeEndPoint {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(TestCustomizeEndPoint.class);
        application.run(args);

    }

    public static class EndPointAutoConfig{

        private List<MemStatus> status = new ArrayList<MemStatus>();

        @Bean
        public MyEndPoint myEndPoint(){
            return new MyEndPoint(status);
        }

        @Bean
        public MemCollector memCollector(){
            return new MemCollector(status);

        }




    }

    public static class MemStatus {

        public MemStatus(Date date, Map<String, Object> status) {
            this.date = date;
            this.status = status;
        }

        private Date date;

        private Map<String, Object> status;

        public Date getDate() {
            return date;
        }

        public Map<String, Object> getStatus() {
            return status;
        }

    }

    public static class MemCollector {

        private int maxSize = 5;

        private List<MemStatus> status;

        public MemCollector(List<MemStatus> status) {
            this.status = status;
        }

        @Scheduled(cron = "0/5 * *  * * ? ")
        public void collect(){

            Runtime runtime = Runtime.getRuntime();
            // java虚拟机（这个进程）能构从操作系统那里挖到的最大的内存
            Long maxMemory = runtime.maxMemory();
            // 这个方法返回的是java虚拟机现在已经从操作系统那里挖过来的内存大小
            Long totalMemory = runtime.totalMemory();
            // 没有用的内存
            Long freeMemory = runtime.freeMemory();
            Map<String, Object> memoryMap = new HashMap<String, Object>(2, 1);
            Date date = Calendar.getInstance().getTime();
            memoryMap.put("maxMemory", maxMemory);
            memoryMap.put("totalMemory", totalMemory);
            memoryMap.put("freeMemory", freeMemory);
            if (status.size() > maxSize) {
                status.remove(0);
                status.add(new MemStatus(date, memoryMap));
            } else {
                status.add(new MemStatus(date, memoryMap));
            }
        }
    }

    public static class MyEndPoint extends AbstractEndpoint implements Endpoint{

        private List<MemStatus> status;

        public MyEndPoint(List<MemStatus> status){
            super("my",false);
            this.status = status;
        }

        @Override
        public Object invoke() {

            if (status== null || status.isEmpty()){
                return "hello world";
            }

            Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();

            for (MemStatus memStatus : status){
                for (Map.Entry<String,Object> entry : memStatus.status.entrySet()){
                    List<Map<String, Object>> collectList = result.get(entry.getKey());

                    if (collectList == null){
                        collectList = new LinkedList<Map<String,Object>>();
                        result.put(entry.getKey(), collectList);
                    }

                    Map<String,Object> soloCollect = new HashMap<String, Object>();
                    soloCollect.put("data", memStatus.getDate());
                    soloCollect.put(entry.getKey(), entry.getValue());
                    collectList.add(soloCollect);
                }
            }


            return result;
        }
    }

}

