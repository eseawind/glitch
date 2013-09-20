package ezbake.glitch.config;

/**
 * <p>
 * Returns the configuration parser that is used for parsing the configuration
 * file to obtain the settings and mappings of the handlers.
 * </p>
 * <p>
 * A system property may be set to specify the {@link ezbake.glitch.config.ConfigurationParser}
 * parser that corresponds to the configuration file. If no system property
 * exists then, by default, the XML configuration parser is used. 
 * </p>
 * <p>
 * The system property name for specifying the configuration parser is <b>
 * glitch.configurationParser</b>. The value is a fully qualified class name of
 * a ConfigurationParser.
 * </p>
 * <p>
 * The system property name for overriding the default configuration file name
 * associated with the parser is <b>glitch.configurationFile</b>.
 * </p>
 * <p>
 * TODO: The implementation of the system properties.
 * </p>
 */
public final class ConfigurationParserFactory {

   /**
    * <p>
    * Returns the configuration parser specified in the system properties. If
    * no system property exists then the default XML configuration parser is
    * returned.
    * </p>
    * 
    * @return The configuration parser specified in the system properties.
    */
   public static ConfigurationParser getParser() {
      
      return new XmlConfigurationParser();
   }

}
