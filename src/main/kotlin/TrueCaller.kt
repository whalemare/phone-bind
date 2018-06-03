

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * @param get token from here https://tcapi.phphive.info/console
 */
class TrueCaller(private val token: String) : Searchable {

    override fun search(phone: String): String {
        val url = "https://tcapi.phphive.info/$token/search/$phone"
        val obj = URL(url)
        val con = obj.openConnection() as HttpsURLConnection
        // For Handling SSL Handshakes
        if (null == sslSocketFactory) {
            val sc = SSLContext.getInstance("SSL")
            sc.init(null, ALL_TRUSTING_TRUST_MANAGER, java.security.SecureRandom())
            sslSocketFactory = sc.socketFactory
        }
        con.sslSocketFactory = sslSocketFactory!!
        con.hostnameVerifier = ALL_TRUSTING_HOSTNAME_VERIFIER
        //add reuqest header
        con.requestMethod = "GET"
        con.setRequestProperty("X-User", "PHPHive")
        val responseCode = con.responseCode
        val `in` = BufferedReader(
            InputStreamReader(con.inputStream)
        )

        val response = StringBuffer()
        var inputLine = `in`.readLine()
        while (inputLine != null) {
            response.append(inputLine)
            inputLine = `in`.readLine()
        }
        `in`.close()

        //print result
        return response.toString()
    }

    companion object {

        private var sslSocketFactory: SSLSocketFactory? = null

        // For Handling SSL Handshakes
        private val ALL_TRUSTING_TRUST_MANAGER = arrayOf<TrustManager>(object : X509TrustManager {

            @Throws(CertificateException::class)
            override fun checkClientTrusted(arg0: Array<X509Certificate>, arg1: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(arg0: Array<X509Certificate>, arg1: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate>? {
                return null
            }

        })

        private val ALL_TRUSTING_HOSTNAME_VERIFIER = HostnameVerifier { hostname, session -> true }
    }

}