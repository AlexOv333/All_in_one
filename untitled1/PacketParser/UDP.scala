package PacketParser
import crc32.CRC32Count

object UDP {
  def parserUDP(bytes: Seq[Byte]): Unit = {
    val srcPort: Array[Byte] = Array(bytes(0), bytes(1))
    val dstPort: Array[Byte] = Array(bytes(2), bytes(3))
    val lengthUDP: Array[Byte] = Array(bytes(4), bytes(5))
    val byteArrayUDP: Array[Byte] = Array(bytes(6), bytes(7))
    val crc32 = CRC32Count.calculateCRC32(byteArrayUDP)
    println("CRC32 для данных UDP:" + crc32)
    val data = bytes.slice(8, bytes.length)
    println(data) // оформить нормальный вывод, потому что сейчас оно в байтах а не в хексах
  }
}
