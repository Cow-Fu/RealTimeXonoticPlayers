package rocks.cow.QueryMasterServer

import org.w3c.dom.Document
import org.xml.sax.SAXException
import rocks.cow.MasterServer.MasterServer

import javax.xml.parsers.ParserConfigurationException
import java.io.IOException

class QueryMasterServer {
    val rawResult: Document
        @Throws(IOException::class, SAXException::class, ParserConfigurationException::class)
        get() = MasterServer.masterServerQuery()
}
