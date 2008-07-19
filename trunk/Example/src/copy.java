import java.io.*;

public class copy{
public static void main(String[] args)  throws IOException {
  InputStream in=System.in;
  OutputStream out=System.out;
  
  byte[] buf = new byte[4096];
  int len = in.read(buf);
  while (len != -1) {
    out.write(buf, 0, len);
    len = in.read(buf);
  }
}
}