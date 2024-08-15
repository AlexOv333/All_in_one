package messageOfICMP

import crc32.CRC32Count

object typeOfMessage {
  def parameterProblemMessage(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
    val byteArrayICMPChecksum: Array[Byte] = Array(bytes(2), bytes(3))
    val crc32 = CRC32Count.calculateCRC32(byteArrayICMPChecksum)
    println("CRC32 для данных ICMP:" + crc32)
    val pointer = bytes(4) // надо тестить, в rfc скаазно, что поинтер после чексуммы
  }

  def sourceQuenchMessage(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
    val byteArrayICMPChecksum: Array[Byte] = Array(bytes(2), bytes(3))
    val crc32 = CRC32Count.calculateCRC32(byteArrayICMPChecksum)
    println("CRC32 для данных ICMP:" + crc32)
  }

  def redirectMessage(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
    code match
      case 0 => println("Redirect datagrams for the Network")
      case 1 => println("Redirect datagrams for the Host")
      case 2 => println("Redirect datagrams for the Type of Service and Network")
      case 3 => println("Redirect datagrams for the Type of Service and Host")
    val byteArrayICMPChecksum: Array[Byte] = Array(bytes(2), bytes(3))
    val crc32 = CRC32Count.calculateCRC32(byteArrayICMPChecksum)
    println("CRC32 для данных ICMP:" + crc32)
    //не хватает еще gateway по rfc
  }

  def timestampReplyMessage(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
    val byteArrayICMPChecksum: Array[Byte] = Array(bytes(2), bytes(3))
    val crc32 = CRC32Count.calculateCRC32(byteArrayICMPChecksum)
    println("CRC32 для данных ICMP:" + crc32)
    val identifier: Array[Byte] = Array(bytes(4), bytes(5))
    println("ID для ICMP:" + identifier(0) + identifier(1))
    val sequence: Array[Byte] = Array(bytes(6), bytes(7))
    println("Seq для ICMP:" + sequence(0) + sequence(1))
    // проверить еще раз эти поля
  }

  def informationReplyMessage(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
    val byteArrayICMPChecksum: Array[Byte] = Array(bytes(2), bytes(3))
    val crc32 = CRC32Count.calculateCRC32(byteArrayICMPChecksum)
    println("CRC32 для данных ICMP:" + crc32)
    val identifier: Array[Byte] = Array(bytes(4), bytes(5))
    println("ID для ICMP:" + identifier(0) + identifier(1))
    val sequence: Array[Byte] = Array(bytes(6), bytes(7))
    println("Seq для ICMP:" + sequence(0) + sequence(1))
  }

  def timeExcedeedMessage(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
    code match
      case 0 => println("time to live exceeded in transit")
      case 1 => println("fragment reassembly time exceeded") //аналогичная ситуация, надо тестить в wireshark другие поля и дописывать
  }

  def destinationHostUnreacheble(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
    code match
      case 0 => println("net unreachable")
      case 1 => println("host unreachable")
      case 2 => println("protocol unreachable")
      case 3 => println("port unreachable")
      case 4 => println("fragmentation needed and DF set")
      case 5 => println("source route failed")
    val byteArrayICMPChecksum: Array[Byte] = Array(bytes(2), bytes(3))
    val crc32 = CRC32Count.calculateCRC32(byteArrayICMPChecksum)
    println("CRC32 для данных ICMP:" + crc32)
    //Это надо тестить, потому что на данный момент для этого сообщения нет возможности понять размер полей, поэтому пока что как есть!!!!!
  }

  def echoPingRequest(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
    val byteArrayICMPChecksum: Array[Byte] = Array(bytes(2), bytes(3))
    val crc32 = CRC32Count.calculateCRC32(byteArrayICMPChecksum)
    println("CRC32 для данных ICMP:" + crc32)
    val identifier: Array[Byte] = Array(bytes(4), bytes(5))
    println("ID для ICMP:" + identifier(0) + identifier(1))
    val sequence: Array[Byte] = Array(bytes(6), bytes(7))
    println("Seq для ICMP:" + sequence(0) + sequence(1))
  }

  def echoPingReply(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
    val byteArrayICMPChecksum: Array[Byte] = Array(bytes(2), bytes(3))
    val crc32 = CRC32Count.calculateCRC32(byteArrayICMPChecksum)
    println("CRC32 для данных ICMP:" + crc32)
    val identifier: Array[Byte] = Array(bytes(4), bytes(5))
    println("ID для ICMP:" + identifier(0) + identifier(1))
    val sequence: Array[Byte] = Array(bytes(6), bytes(7))
    println("Seq для ICMP:" + sequence(0) + sequence(1))
  }
}
