package com.serverless

import com.amazonaws.services.lambda.AWSLambdaClientBuilder
import com.amazonaws.services.lambda.model.InvokeRequest
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.apache.logging.log4j.LogManager

class SyncInvokeHandler : RequestHandler<Map<String, Any>, ApiGatewayResponse> {
    override fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        LOG.info("received: ${input.keys}")

        val lambda = AWSLambdaClientBuilder.defaultClient()
        val response = lambda.invoke(
                InvokeRequest()
                        .withFunctionName("retry-sync-lambda-dev-retrySync")
        )

        LOG.info("response: ${response.statusCode}")

        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = HelloResponse("Go Serverless v1.x! Your Kotlin function executed successfully!", input)
            headers = mapOf("X-Powered-By" to "AWS Lambda & serverless")
        }
    }

    companion object {
        private val LOG = LogManager.getLogger(SyncInvokeHandler::class.java)
    }
}
