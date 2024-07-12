package edu.ncsu.csc.CoffeeMaker;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Uses the @Configuration tag to set up a TestConfig file
 */
@Configuration
@ComponentScan ( { "edu.ncsu.csc.CoffeeMaker" } )
public class TestConfig {
}
