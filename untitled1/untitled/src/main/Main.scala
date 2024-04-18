package main
import main.crc32.CRC32Count

object Main extends App {
  def hexToByteArray(hexString: String): Array[Byte] = {
    val hexPairs = hexString.trim.split(" ")
    hexPairs.map(pair => Integer.parseInt(pair, 16).toByte)
  }

  val dataString = "50 ff 20 9f 96 2d 00 45 e2 04 70 87 08 00 45 00 00 28 00 4e 40 00 80 06 5d 35 0a 00 01 10 cc 4f c5 ed ee 4f 01 bb 7f 8e f5 e8 6e c3 6d 86 50 10 04 00 cc bb 00 00"
  val byteArray = hexToByteArray(dataString)
  val bytes = HexByteConverter.hexStringToBytes(dataString)
  println(s"Количество байт: ${bytes.length}")

  // Вывод массива байт
  HexByteConverter.printByteArray(bytes)
  etherParser.findEndOfEthernet(bytes)

  //println("Массив байтов:")
  //println(byteArray.mkString(" "))

  val crc32 = CRC32Count.calculateCRC32(byteArray)
  println("CRC32 для данных:" + crc32)
}

object Helpers {
  // Функция для разбиения строки на пары символов
  def splitHexPairs(hexString: String): Seq[String] = hexString.split("\\s+").flatMap(_.grouped(2))
}

object HexByteConverter {
  // Функция для преобразования шестнадцатеричного числа в байт
  def hexToByte(hex: String): Byte = Integer.parseInt(hex, 16).toByte

  def printByteArray(bytes: Seq[Byte]): Unit = {
    bytes.foreach(byte => print(f"$byte%02X "))
    println()
  }

  // Функция для преобразования строки шестнадцатеричных чисел в последовательность байт
  def hexStringToBytes(hexString: String): Seq[Byte] = {
    val hexPairs = Helpers.splitHexPairs(hexString)
    hexPairs.map(hexToByte)
  }
}
//чуть-чуть переделать весь функционал проверки
//сейчас костыль блять
object ipParser {
  def findEndOfIP(bytes: Seq[Byte]): Unit ={
    if (bytes(23) == 6 && bytes(14) == 69){ // переделать этот кринж!!!!
      println("Это поле равно 06 = TCP и размер заголовка = 20 байт, значит поле options пустое и можно считать что мы переходим к tcp после всего IP")
      tcpParser.findTCPCRC(bytes)
    } else {
      println("Это поле не равно 06 != TCP")
    }
  }
}

object tcpParser {
  def findTCPCRC(bytes: Seq[Byte]): Unit ={
    val byteArrayTCP: Array[Byte] = Array(bytes(50), bytes(51))
    val crc32 = CRC32Count.calculateCRC32(byteArrayTCP)
    println("CRC32 для данных:" + crc32)
  }
}
object etherParser {
  def findEndOfEthernet(bytes: Seq[Byte]): Unit ={
    val index = bytes.indexOfSlice(Seq[Byte](0x08, 0x00))
    val isValid = index >= 0 && index < 14
    if (isValid) {
      println("Это действитльно Ethernet заголовок.")
      ipParser.findEndOfIP(bytes)
    } else {
      println("Это не Ethernet заголовок.")
    }
  }
}

/*50 ff 20 3e 8e e1 30 9c 23 92 03 ff 08 00 45 00
00 34 80 2d 40 00 40 06 23 42 c0 a8 01 68 ac 40
29 04 bc fc 01 bb 43 85 9c 53 05 a8 01 fb 80 10
0e 4f 51 f1 00 00 01 01 08 0a 14 0b f3 41 7d 6d
54 3a*/
/* TCP
ee 4f 01 bb 7f 8e f5 e8 6e c3 6d 86 50 10 04 00
cc bb 00 00
cc bb - checksum*/

