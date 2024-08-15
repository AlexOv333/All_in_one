package main
import main.crc32.CRC32Count

object Main extends App {
  val dataString = "50 ff 20 9f 96 2d 00 45 e2 04 70 87 08 00 45 00 00 28 00 4e 40 00 80 06 5d 35 0a 00 01 10 cc 4f c5 ed ee 4f 01 bb 7f 8e f5 e8 6e c3 6d 86 50 10 04 00 cc bb 00 00"
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
    if (bytes(23) == 6 && bytes(14) == 69) { // здесь должно быть не явное указание мест в пакете.
      println("Это поле равно 06 = TCP и размер заголовка = 20 байт, значит поле options пустое и можно считать что мы переходим к tcp после всего IP")
      findTCPCRC(bytes)
    } else {
      println("Это поле не равно 06 != TCP")
    }
  }

  def findTCPCRC(bytes: Seq[Byte]): Unit = {
    val byteArrayTCP: Array[Byte] = Array(bytes(50), bytes(51))
    val crc32 = CRC32Count.calculateCRC32(byteArrayTCP)
    println("CRC32 для данных:" + crc32)
  }
}
/* TCP
ee 4f 01 bb 7f 8e f5 e8 6e c3 6d 86 50 10 04 00
cc bb 00 00
cc bb - checksum*/

