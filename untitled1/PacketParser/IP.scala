package PacketParser
import crc32.CRC32Count 

object IP {
  def parserIP(bytes: Seq[Byte]): Unit = {
    //код учитывает тот факт что заголовок без поля options
    val version = bytes(0)// & 0xf0 >> 4
    val lengthOfHdr = bytes(0)// & 0x0f * 4
    val typeOfService = bytes(1)
    val totalLength: Array[Byte] = bytes.slice(2,4).toArray
    //Main.printByteArray(totalLength.toSeq)
    val identification: Array[Byte] = bytes.slice(4,6).toArray
    //Main.printByteArray(identification.toSeq)
    val fragment_offset: Array[Byte] = bytes.slice(6, 8).toArray
    val reserved = (fragment_offset(1) & (1 << 7)) > 0
    val dont_frag = (fragment_offset(1) & (1 << 6)) > 0
    val more_frags = (fragment_offset(1) & (1 << 5)) > 0 // делает вывод формата true-false, при необходимости(скорее всего) придется менять на байты
    val timeToLive = bytes(8)
    val typeOfNextProtocol = bytes(9)
    if (bytes(0) == 69) {
      println("Этот заголовок имеет длину 20 byte, что означает, что IP без options")
      typeOfNextProtocol match
        case 6 => TCP.parserTCP(bytes.drop(20))// переделать функцию для TCP
        case 1 => ICMP.parserICMP(bytes.drop(20))//работает прям с текущего элемента, с 34
        case 17 => UDP.parserUDP(bytes.drop(20))
        case _ => println("Nothing")
    }
    val byteArrayIP: Array[Byte] = Array(bytes(10), bytes(11))
    val crc32 = CRC32Count.calculateCRC32(byteArrayIP)
    //println("CRC32 для данных IP:" + crc32)
    val sourceAdress: Array[Byte] = bytes.slice(12,16).toArray
    //Main.printByteArray(sourceAdress.toSeq)
    val destAdress: Array[Byte] = bytes.slice(16, 20).toArray
    //Main.printByteArray(destAdress.toSeq)
  }
}
