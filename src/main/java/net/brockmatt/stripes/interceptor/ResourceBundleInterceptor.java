package net.brockmatt.stripes.interceptor;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Injects our own resource bundle into the context so JSP formatters work correctly with the
 * self-referencing ResourceBundles (see: {@link net.brockmatt.util.ResourceBundleUtil} for more info)
 * <p/>
 * To use in your Stripes web application, you must add this class to the list of interceptors in the Stripes servlet
 * filter configuration:
 * <p/>
 * <filter>
 *   <display-name>Stripes Filter</display-name>
 *   <filter-name>StripesFilter</filter-name>
 *   <filter-class>net.sourceforge.stripes.controller.StripesFilter</filter-class>
 *   ...
 *   <init-param>
 *     <param-name>Interceptor.Classes</param-name>
 *     <param-value>
 *       net.brockmatt.stripes.interceptor.ResourceBundleInterceptor
 *     </param-value>
 *   </init-param>
 *   <init-param>
 *     <param-name>LocalizationBundleFactory.Class</param-name>
 *     <param-value>net.brockmatt.stripes.localization.ReflectiveLocalizationBundleFactory</param-value>
 *   </init-param>
 *   ...
 * </filter>
 */
@Intercepts(LifecycleStage.ResolutionExecution)
public class ResourceBundleInterceptor implements Interceptor {

  public Resolution intercept(ExecutionContext context) throws Exception {
    HttpServletRequest request = context.getActionBeanContext().getRequest();
    Locale locale = request.getLocale();

    ResourceBundle bundle = StripesFilter.getConfiguration().getLocalizationBundleFactory().getErrorMessageBundle(locale);

    Config.set(request, Config.FMT_LOCALIZATION_CONTEXT, new LocalizationContext(bundle, locale));

    return context.proceed();
  }

}