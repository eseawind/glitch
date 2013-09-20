package ezbake.glitch.config;

/**
 * <p>
 * Responsible for parsing the settings and mappings stored in a configuration
 * file. The configuration values are stored and returned as an instance of
 * {@link ezbake.glitch.config.Configuration}. Callers should use the
 * {@link #getConfiguration()} method to initiate and receive the configuration
 * as an object. If the caller wishes to override the default configuration
 * file name or the file name identified in the system properties then the
 * {@link #getConfiguration(String)} may be utilized.
 * </p>
 */
public interface ConfigurationParser {
   
   Configuration getConfiguration();
   Configuration getConfiguration(String resouceName);
}
