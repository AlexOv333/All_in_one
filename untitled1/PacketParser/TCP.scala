package PacketParser
import HelperScripts.HexManip
import crc32.CRC32Count

object TCP {
  def parserTCP(bytes: Seq[Byte]): Unit = {
    val sourcePort: Array[Byte] = bytes.slice(0,3).toArray
    val destPort: Array[Byte] = bytes.slice(2,4).toArray
    val sequenceNumbber: Array[Byte] = bytes.slice(4,8).toArray
    val acknowledgmentNumber: Array[Byte] = bytes.slice(8,12).toArray
    val offset = (bytes(12) & 0xf0) >> 4
    val reserved = ((bytes(12) & 0x0e) >> 1) > 0
    val nonce = (bytes(12) & 1) > 0
    val cwr = (bytes(13) & (1 << 7)) > 0
    val ecn = (bytes(13) & (1 << 6)) > 0
    val urgent = (bytes(13) & (1 << 5)) > 0
    val ack = (bytes(13) & (1 << 4)) > 0
    val push = (bytes(13) & (1 << 3)) > 0
    val reset = (bytes(13) & (1 << 2)) > 0
    val syn = (bytes(13) & (1 << 1)) > 0
    val fin = (bytes(13) & 1) > 0
    val window: Array[Byte] = bytes.slice(14, 16).toArray
    //место под header length(wireshark) и под флаги с ключами и окном
    val byteArrayTCP: Array[Byte] = Array(bytes(16), bytes(17))
    val crc32 = CRC32Count.calculateCRC32(byteArrayTCP)
    println("CRC32 для данных TCP:" + crc32)
    val urgentPointer: Array[Byte] = bytes.slice(18,20).toArray
    val data = bytes.slice((offset * 4) + 0, bytes.length)
    HexManip.printByteArray(data)
  }
}
