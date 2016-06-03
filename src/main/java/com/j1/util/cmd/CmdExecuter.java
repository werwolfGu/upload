package com.j1.util.cmd;

import com.j1.util.FFMpegUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class CmdExecuter
{
  public static void exec(List<String> cmd, FFMpegUtil getter)
  {
    try
    {
      ProcessBuilder builder = new ProcessBuilder(new String[0]);
      builder.command(cmd);
      builder.redirectErrorStream(true);
      Process proc = builder.start();

      BufferedReader stdout = new BufferedReader(new InputStreamReader(proc
        .getInputStream()));
      String line;
      while ((line = stdout.readLine()) != null) {
        if ((getter != null) && 
          (line.contains("Duration:")))
        {
          String arr = line.substring(line.indexOf(":") + 1, line.indexOf(".")).trim();

          String[] tmp = arr.trim().split(":");

          Integer count = Integer.valueOf(Integer.parseInt(tmp[0]) * 60 * 60 + Integer.parseInt(tmp[1]) * 60 + Integer.parseInt(tmp[2]));

          getter.setRuntime(count);
        }

      }

      proc.waitFor();
      stdout.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
