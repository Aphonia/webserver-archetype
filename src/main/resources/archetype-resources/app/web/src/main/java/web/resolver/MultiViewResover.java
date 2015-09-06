package ${package}.web.resolver;

import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

/**
 * 视图解析器
 * @date 2014年8月19日 上午9:45:39
 * @author lim
 * @email qq79474256.cool@163.com
 * @version V1.0
 */
public class MultiViewResover extends ContentNegotiatingViewResolver implements ViewResolver {

	private Map<String, Object> resolvers;
	
	private final String JSON = "json";
	
	protected ContentNegotiationManagerFactoryBean cnManagerFactoryBean = new ContentNegotiationManagerFactoryBean();
	
	protected void initServletContext(ServletContext servletContext) {
//		super.initServletContext(servletContext);
		this.cnManagerFactoryBean.setServletContext(servletContext);
	}
	

	/**
	 * 重写视图解析
	 * @param viewName
	 * @param locale
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.view.ContentNegotiatingViewResolver#resolveViewName(java.lang.String, java.util.Locale)
	 */
	@Override
	public View resolveViewName(String viewName, Locale locale)
			throws Exception {
		//设置默认velocity解析
		String suffix = "vm";
		//判断是否json解析方式
		if(JSON.equals(viewName)){
			suffix = viewName;
		}
		Object resolver = resolvers.get(suffix);
		//扩展视图解析器
		if (resolver != null) {
			if (resolver instanceof View) {
				return (View) resolver;
			} else if (resolver instanceof ViewResolver) {
				return ((ViewResolver) resolver).resolveViewName(viewName,locale);
			}else{
				throw new RuntimeException("can not find view resolve '" + viewName + "'");
			}
		}else{
			throw new RuntimeException("this resolvers map is null");
		}
	}

	public Map<String, Object> getResolvers() {
		return resolvers;
	}

	public void setResolvers(Map<String, Object> resolvers) {
		this.resolvers = resolvers;
	}

}
