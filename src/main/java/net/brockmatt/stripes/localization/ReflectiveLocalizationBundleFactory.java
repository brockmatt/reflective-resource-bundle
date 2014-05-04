package net.brockmatt.stripes.localization;

import net.brockmatt.util.ResourceBundleUtil;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.localization.LocalizationBundleFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Based on Stripes {@link net.sourceforge.stripes.localization.DefaultLocalizationBundleFactory}
 * with the addition of our own custom utility class that translates and caches reflective
 * ResourceBundles (see {@link ResourceBundleUtil} for more).
 */
public class ReflectiveLocalizationBundleFactory implements LocalizationBundleFactory {

  // Name of the default resource bundle for Stripes
  public static final String BUNDLE_NAME = "StripesResources";

  // The configuration parameter for changing the default error message resource bundle.
  public static final String ERROR_MESSAGE_BUNDLE = "LocalizationBundleFactory.ErrorMessageBundle";

  // The configuration parameter for changing the default field name resource bundle.
  public static final String FIELD_NAME_BUNDLE = "LocalizationBundleFactory.FieldNameBundle";

  private String errorBundleName;
  private String fieldBundleName;
  private ResourceBundleUtil resourceBundleUtil;

  public void init(Configuration configuration) {
    this.errorBundleName = configuration.getBootstrapPropertyResolver().getProperty(ERROR_MESSAGE_BUNDLE);
    if (this.errorBundleName == null) {
      this.errorBundleName = BUNDLE_NAME;
    }

    this.fieldBundleName = configuration.getBootstrapPropertyResolver().getProperty(FIELD_NAME_BUNDLE);
    if (this.fieldBundleName == null) {
      this.fieldBundleName = BUNDLE_NAME;
    }

    this.resourceBundleUtil = new ResourceBundleUtil();
  }

  public ResourceBundle getErrorMessageBundle(Locale locale) throws MissingResourceException {
    try {
      return resourceBundleUtil.getResourceBundle(this.errorBundleName, locale);
    } catch (MissingResourceException mre) {
      MissingResourceException mre2 = new MissingResourceException(
        "Could not find the form field resource bundle needed by Stripes. This " +
          "almost certainly means that a properties file called '" +
          this.errorBundleName + ".properties' could not be found in the classpath. " +
          "This properties file is needed to lookup form field names. Please " +
          "ensure the file exists in WEB-INF/classes or elsewhere in your classpath.",
        this.errorBundleName, "");
      mre2.setStackTrace(mre.getStackTrace());
      throw mre2;
    }
  }

  public ResourceBundle getFormFieldBundle(Locale locale) throws MissingResourceException {
    try {
      return resourceBundleUtil.getResourceBundle(this.fieldBundleName, locale);
    } catch (MissingResourceException mre) {
      MissingResourceException mre2 = new MissingResourceException(
        "Could not find the form field resource bundle needed by Stripes. This " +
          "almost certainly means that a properties file called '" +
          this.fieldBundleName + ".properties' could not be found in the classpath. " +
          "This properties file is needed to lookup form field names. Please " +
          "ensure the file exists in WEB-INF/classes or elsewhere in your classpath.",
        this.fieldBundleName, "");
      mre2.setStackTrace(mre.getStackTrace());
      throw mre2;
    }
  }

  public String getErrorBundleName() {
    return errorBundleName;
  }

  public String getFieldBundleName() {
    return fieldBundleName;
  }

  public ResourceBundleUtil getResourceBundleUtil() {
    return resourceBundleUtil;
  }

}