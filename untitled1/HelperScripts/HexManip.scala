package HelperScripts

object HexManip {
  // Функция для разбиения строки на пары символов
  def splitHexPairs(hexString: String): Seq[String] = hexString.split("\\s+").flatMap(_.grouped(2))

  //Функция для преобразования hex строки в массив байт
  def hexToByteArray(hexString: String): Array[Byte] = {
    val hexPairs = hexString.trim.split(" ")
    hexPairs.map(pair => Integer.parseInt(pair, 16).toByte)
  }

  //Функция вывода массива байтов
  def printByteArray(bytes: Seq[Byte]): Unit = {
    bytes.foreach(byte => print(f"$byte%02X "))
    println()
  }

  // Функция для преобразования строки шестнадцатеричных чисел в последовательность пар байт
  def hexStringToBytes(hexString: String): Seq[Byte] = {
    val hexPairs = HexManip.splitHexPairs(hexString)
    hexPairs.flatMap(pair => HexManip.hexToByteArray(pair))
  }
  
  def doubleDots2StringHex(StringDoubleDots: String): String = StringDoubleDots.split(':').mkString(" ").toLowerCase
  
  def dots2StringHex(StringDots: String): String = StringDots.split('.').map(_.toInt.toHexString).mkString(" ").toLowerCase
  
}
