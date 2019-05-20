package com.koose

import com.amazonaws.xray.AWSXRay
import com.amazonaws.xray.AWSXRayRecorderBuilder
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter
import com.amazonaws.xray.plugins.ECSPlugin
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.Filter
import com.amazonaws.xray.strategy.sampling.LocalizedSamplingStrategy




@Configuration
class WebConfig {

    init {
        val builder = AWSXRayRecorderBuilder.standard().withPlugin(ECSPlugin())
        val ruleFile = WebConfig::class.java.getResource("/aws-xray-sampling-rules.json")
        builder.withSamplingStrategy(LocalizedSamplingStrategy(ruleFile))
        AWSXRay.setGlobalRecorder(builder.build())
    }

    @Value("\${version}")
    private val version = ""

    @Bean
    fun TracingFilter(): Filter {
        return AWSXRayServletFilter("Koose-$version")
    }
}