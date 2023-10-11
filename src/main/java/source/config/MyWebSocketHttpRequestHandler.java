package source.config;

import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.WebSocketHttpRequestHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyWebSocketHttpRequestHandler extends WebSocketHttpRequestHandler implements HandlerAdapter {

	public MyWebSocketHttpRequestHandler(WebSocketHandler wsHandler) {
		super(wsHandler);
	}

	@Override
	public boolean supports(Object handler) {
		return handler instanceof MyHandler;
	}

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		super.handleRequest(request, response);
		return null;
	}

	@Override
	public long getLastModified(HttpServletRequest request, Object handler) {
		// TODO Auto-generated method stub
		return 0;
	}

}
