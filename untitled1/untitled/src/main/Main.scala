package main
import main.crc32.CRC32Count
import main.messageOfICMP.typeOfMessage
//НАДО РАЗОБРАТЬСЯ КАК ДЕЛАТЬ ВЫВОД В ЧЕЛОВЕЧЕСКОМ ВИДЕ А НЕ В ВИДЕ ХЕКСОВ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
object Main extends App {
  val dataString = "50 ff 20 9f 96 2d 00 45 e2 04 70 87 08 00 45 00 00 29 95 cc 40 00 80 06 de e2 0a 00 01 10 6c b1 0e 5f fe 17 01 bb 2e d3 a6 f9 64 0e f3 68 50 10 01 ff fa 9d 00 00 00"//check IP
  val dataString4 = "00 45 e2 04 70 87 00 08 e3 ff fc 04 08 00 45 00 01 58 9a c4 00 00 38 11 7f 65 0a 20 8d 88 0a 20 c5 a3 00 35 c1 80 01 44 7f b8 b4 ee 81 80 00 01 00 01 00 04 00 08 0c 73 61 66 65 62 72 6f 77 73"//UDP
  val dataString2 = "50 ff 20 9f 96 2d 00 45 e2 04 70 87 08 00 45 00 00 3c 3b b6 00 00 80 01 e3 eb 0a 00 01 10 08 08 08 08 08 00 4d 5a 00 01 00 01 61 62 63 64 65 66 67 68 69 6a 6b 6c 6d 6e 6f 70 71 72 73 74 75 76 77 61 62 63 64 65 66 67 68 69"// ICMP
  val dataString3 = "00 45 e2 04 70 87 00 08 e3 ff fc 04 08 00 45 00 05 0a 18 fa 40 00 3a 06 65 46 b9 e2 34 56 0a 20 c5 55 01 bb f7 f1 a0 b8 ab 38 c2 f5 1d 48 50 10 7e 93 85 60 00 00 aa 27 64 84 55 51 d1 71 83 57"//TCP
  val bytes = hexStringToBytes(dataString) //Функция для преобразования строки шестнадцатеричных чисел в последовательность байт.
  //println(s"Количество байт: ${bytes.length}")

  // Вывод массива байт
  //printByteArray(bytes)
  //Вызов объекта парсинга пакетов
  packetParser.parserEthernet(bytes)

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

object Helpers { // вспомогательные программы
  // Функция для разбиения строки на пары символов
  def splitHexPairs(hexString: String): Seq[String] = hexString.split("\\s+").flatMap(_.grouped(2))
  //Функция для преобразования hex строки в массив байт
  def hexToByteArray(hexString: String): Array[Byte] = {
    val hexPairs = hexString.trim.split(" ")
    hexPairs.map(pair => Integer.parseInt(pair, 16).toByte)
  }
}

object packetParser { // Здесь будут итоговые парсеры пакетов, пока что все работает так, чтобы программа определяля лишь часть полей пакетов и на их основе переходила к тому или иному
  def parserEthernet(bytes: Seq[Byte]): Unit = {
    val destinationAdress: Array[Byte] = bytes.slice(0,6).toArray
    //Main.printByteArray(destinationAdress.toSeq)
    val sourceAdress: Array[Byte] = bytes.slice(6,12).toArray
    //Main.printByteArray(sourceAdress.toSeq)
    val index = bytes.indexOfSlice(Seq[Byte](0x08, 0x00))// пока так, скорее всего останется навсегда, потому что может быть после езернета будет что-то другое.
    val isValid = index >= 0 && index < 14
    if (isValid) {
      //println("Это действитльно Ethernet заголовок.")
      parserIP(bytes.drop(14))
    } else {
      println("Это не Ethernet заголовок.")
    }
  }

  def parserIP(bytes: Seq[Byte]): Unit = {
    //код учитывает тот факт что заголовок без поля options
    val version = bytes(0)// & 0xf0 >> 4
    val lengthOfHdr = bytes(0)// & 0x0f * 4
    val typeOfService = bytes(1)
    val totalLength: Array[Byte] = bytes.slice(2,4).toArray
    //Main.printByteArray(totalLength.toSeq)
    val identification: Array[Byte] = bytes.slice(4,6).toArray
    //Main.printByteArray(identification.toSeq)
    //место под флаги IP
    //место под fragment offset
    val timeToLive = bytes(8)
    val typeOfNextProtocol = bytes(9)
    if (bytes(0) == 69) {
      println("Этот заголовок имеет длину 20 byte, что означает, что IP без options")
      typeOfNextProtocol match
        case 6 => parserTCP(bytes.drop(20))// переделать функцию для TCP
        case 1 => parserICMP(bytes.drop(20))//работает прям с текущего элемента, с 34
        case 17 => parserUDP(bytes.drop(20))
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

  def parserICMP(bytes: Seq[Byte]): Unit = {
    val typeOfMessage = bytes(0)
    typeOfMessage match
      case 8 => messageOfICMP.typeOfMessage.echoPingRequest(bytes)
      case 0 => messageOfICMP.typeOfMessage.echoPingReply(bytes)
      case 3 => messageOfICMP.typeOfMessage.destinationHostUnreacheble(bytes)
      case 11 => messageOfICMP.typeOfMessage.timeExcedeedMessage(bytes)
      case 12 => messageOfICMP.typeOfMessage.parameterProblemMessage(bytes)
      case 4 => messageOfICMP.typeOfMessage.sourceQuenchMessage(bytes)
      case 5 => messageOfICMP.typeOfMessage.redirectMessage(bytes)
      case 13 => messageOfICMP.typeOfMessage.timestampReplyMessage(bytes) // вот здесь еще есть case 14, но он идентичен 13, поэтому подумать как это лучше сделать
      case 15 => messageOfICMP.typeOfMessage.informationReplyMessage(bytes) // и здесь есть 16 тип
  }

  def parserUDP(bytes: Seq[Byte]): Unit ={
    val srcPort: Array[Byte] = Array(bytes(0), bytes(1))
    val dstPort: Array[Byte] = Array(bytes(2), bytes(3))
    val lengthUDP: Array[Byte] = Array(bytes(4), bytes(5))
    val byteArrayUDP: Array[Byte] = Array(bytes(6), bytes(7))
    val crc32 = CRC32Count.calculateCRC32(byteArrayUDP)
    println("CRC32 для данных UDP:" + crc32)
    val data = bytes.slice(8, bytes.length)
    println(data)// оформить нормальный вывод, потому что сейчас оно в байтах а не в хексах
  }
  //переделать по-нормальному, сделать в отдельный файл(объект) наверное
  def parserTCP(bytes: Seq[Byte]): Unit = {
    val sourcePort: Array[Byte] = bytes.slice(0,3).toArray
    val destPort: Array[Byte] = bytes.slice(2,4).toArray
    val sequenceNumbber: Array[Byte] = bytes.slice(4,8).toArray
    val acknowledgmentNumber: Array[Byte] = bytes.slice(8,12).toArray
    //место под header length(wireshark) и под флаги с ключами и окном
    val byteArrayTCP: Array[Byte] = Array(bytes(16), bytes(17))
    val crc32 = CRC32Count.calculateCRC32(byteArrayTCP)
    println("CRC32 для данных TCP:" + crc32)
    val urgentPointer: Array[Byte] = bytes.slice(18,20).toArray
    //место под data, но она зависит от поля offset
  }
}
