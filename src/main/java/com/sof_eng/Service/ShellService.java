package com.sof_eng.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import com.sof_eng.Util.JwtTokenUtil;
import com.sof_eng.Util.RandomFreePort;
import com.sof_eng.model.command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@Component
public class ShellService {
    public ShellService() {
    }
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/executeShell")
    public String exec(@RequestBody command cmd, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String script=cmd.getCmdline();
        if(!jwtTokenUtil.validateToken(token))
            return "token invalid";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            Map<String, String> env = processBuilder.environment();
            String pathValue = env.get("PATH");
            String newPathValue = pathValue + ":/home/ubuntu/.local/bin";
            env.put("PATH", newPathValue);
            String command;
            int port = RandomFreePort.findRandomFreePort(39000, 40000);
            if (script.contains("start")) {
                command = script + " " + port;
            } else {
                command = script;
            }
            System.out.println(command);
            System.out.println(System.getProperty("user.dir"));
            processBuilder.command("bash", "-c", command); // Use "bash" to execute the command
            System.out.println(command);
            Process process = processBuilder.start();
            if (command.contains("start")) {
                try {
                    URL url = new URL("http://checkip.amazonaws.com");
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                    String ip = br.readLine();
                    return "http://"+ip+":"+port;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "failed to start otree server";
                }
            }
            process.waitFor(); // Wait for the process to complete
            if(process.exitValue()==0){
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                return sb.toString();
            }else{
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                return sb.toString();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "启动服务失败";
        }
    }

}