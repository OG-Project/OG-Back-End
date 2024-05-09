package og.net.api.security.HttpRequestConfig;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.*;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final String body;
    private final ServletInputStream inputStream;
    private final BufferedReader reader;

    public CustomHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = request.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        body = stringBuilder.toString();
        inputStream = new DelegatingServletInputStream(new ByteArrayInputStream(body.getBytes()));
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public ServletInputStream getInputStream() {
        return inputStream;
    }

    @Override
    public BufferedReader getReader() {
        return reader;
    }

    private static class DelegatingServletInputStream extends ServletInputStream {
        private final InputStream sourceStream;

        public DelegatingServletInputStream(InputStream sourceStream) {
            this.sourceStream = sourceStream;
        }

        @Override
        public int read() throws IOException {
            return sourceStream.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        // Override other methods of ServletInputStream if necessary
    }
}
