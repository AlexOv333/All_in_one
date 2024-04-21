package main
import main.crc32.CRC32Count

object Main extends App {
  val dataString = "50 ff 20 9f 96 2d 00 45 e2 04 70 87 08 00 45 00 00 3c 3b b6 00 00 80 01 e3 eb 0a 00 01 10 08 08 08 08 08 00 4d 5a 00 01 00 01 61 62 63 64 65 66 67 68 69 6a 6b 6c 6d 6e 6f 70 71 72 73 74 75 76 77 61 62 63 64 65 66 67 68 69"
  val dataString2 = "00 45 e2 04 70 87 00 08 e3 ff fc 04 08 00 45 00 05 0a 18 fa 40 00 3a 06 65 46 b9 e2 34 56 0a 20 c5 55 01 bb f7 f1 a0 b8 ab 38 c2 f5 1d 48 50 10 7e 93 85 60 00 00 aa 27 64 84 55 51 d1 71 83 57"
  val bytes = hexStringToBytes(dataString) //Функция для преобразования строки шестнадцатеричных чисел в последовательность байт.
  println(s"Количество байт: ${bytes.length}")

  // Вывод массива байт
  printByteArray(bytes)
  //Вызов объекта парсинга пакетов и нахождение crc у TCP
  packetParser.findEndOfEthernet(bytes)

  //Функция вывода массива байтов
  def printByteArray(bytes: Seq[Byte]): Unit = {
    bytes.foreach(byte => print(f"$byte%02X "))
    println()
  }

  // Функция для преобразования строки шестнадцатеричных чисел в последовательность пар байт
  def hexStringToBytes(hexString: String): Seq[Byte] = {
    val hexPairs = Helpers.splitHexPairs(hexString)
    hexPairs.flatMap(pair => Helpers.hexToByteArray(pair))
  }
}

object Helpers {
  // Функция для разбиения строки на пары символов
  def splitHexPairs(hexString: String): Seq[String] = hexString.split("\\s+").flatMap(_.grouped(2))
  //Функция для преобразования hex строки в массив байт
  def hexToByteArray(hexString: String): Array[Byte] = {
    val hexPairs = hexString.trim.split(" ")
    hexPairs.map(pair => Integer.parseInt(pair, 16).toByte)
  }
}

object packetParser {
  def findEndOfEthernet(bytes: Seq[Byte]): Unit = {
    val index = bytes.indexOfSlice(Seq[Byte](0x08, 0x00))
    val isValid = index >= 0 && index < 14
    if (isValid) {
      println("Это действитльно Ethernet заголовок.")
      findEndOfIP(bytes)
    } else {
      println("Это не Ethernet заголовок.")
    }
  }

  def findEndOfIP(bytes: Seq[Byte]): Unit = {
  val typeOfNextProtocol = bytes(23)
    if (bytes(14) == 69) {
      println("Этот заголовок имеет длину 20 byte, что означает, что IP без options")
      typeOfNextProtocol match
        case 6 => findTCPCRC(bytes)
        case 1 => parserICMP(bytes.drop(34))//работает прям с текущего элемента, с 34
        case _ => "Nothing"
    }
  }
  def parserICMP(bytes: Seq[Byte]): Unit = {
    val typeOfMessage = bytes(0)
    typeOfMessage match
      case 8 => echoPingRequest(bytes)
      case 0 => echoPingReply(bytes)
      case 3 => destinationHostUnreacheble(bytes)
      case 11 => timeExcedeedMessage(bytes)
      case 12 => parameterProblemMessage(bytes)
      case 4 => sourceQuenchMessage(bytes)
      case 5 => redirectMessage(bytes)
      case 13 => timestampReplyMessage(bytes) // вот здесь еще есть case 14, но он идентичен 13, поэтому подумать как это лучше сделать
      case 15 => informationReplyMessage(byets) // и здесь есть 16 тип
  }
  def parameterProblemMessage(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
  }
  def sourceQuenchMessage(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
  }
  def redirectMessage(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
  }
  def timestampReplyMessage(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
  }
  def informationReplyMessage(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
  }
  def timeExcedeedMessage(bytes: Seq[Byte]): Unit = {
    val code = bytes(1)
    code match
      case 0 => println("time to live exceeded in transit")
      case 1 => println("fragment reassembly time exceeded") //аналогичная ситуация, надо тестить в wireshark другие поля и дописывать
  }
  def destinationHostUnreaceble(bytes: Seq[Byte]): Unit = {
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

  def findTCPCRC(bytes: Seq[Byte]): Unit = {
    val byteArrayTCP: Array[Byte] = Array(bytes(50), bytes(51))
    val crc32 = CRC32Count.calculateCRC32(byteArrayTCP)
    println("CRC32 для данных TCP:" + crc32)
  }
}
/* TCP
ee 4f 01 bb 7f 8e f5 e8 6e c3 6d 86 50 10 04 00
cc bb 00 00
cc bb - checksum*/

//50 ff 20 9f 96 2d 00 45 e2 04 70 87 08 00 45 00 00 3c 3b b6 00 00 80 01 e3 eb 0a 00 01 10 08 08 08 08 08 00 4d 5a 00 01 00 01 61 62 63 64 65 66 67 68 69 6a 6b 6c 6d 6e 6f 70 71 72 73 74 75 76 77 61 62 63 64 65 66 67 68 69

