package io.chat.java.api.util;

import org.springframework.data.util.Pair;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class WebUtil {
    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";

    private static final String CONTENT_TYPE = "Content-type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private static boolean isAjax(HttpServletRequest request) {
        return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
    }

    public static boolean isAjax(SavedRequest request) {
        return request.getHeaderValues(X_REQUESTED_WITH).contains(XML_HTTP_REQUEST);
    }

    public static boolean isContentTypeJson(SavedRequest request) {
        return request.getHeaderValues(CONTENT_TYPE).contains(CONTENT_TYPE_JSON);
    }

    public static boolean isNotAjax(HttpServletRequest request) {
        return !isAjax(request);
    }

    public static String createRequestUrl(String url, List<Pair> pathParameterList) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(url);

        return builder.buildAndExpand(pathParameterList.stream().collect(Collectors.toMap(x -> x.getFirst().toString(), x -> x.getSecond().toString()))).toString();

    }
}
